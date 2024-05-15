package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class FileService {

    ExecutorService executorService = Executors.newCachedThreadPool();;
    Assignment8 assignment = new Assignment8();

    public List<Integer> getAllNumbers() {
        List<CompletableFuture<List<Integer>>> numberTaskFutures = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            CompletableFuture<List<Integer>> numberTask =
                    CompletableFuture.supplyAsync(() -> assignment.getNumbers(), executorService);
            numberTaskFutures.add(numberTask);
        }
        CompletableFuture<Void> allNumberTasksCompleted = CompletableFuture.allOf(numberTaskFutures.toArray(new CompletableFuture[0]));
        allNumberTasksCompleted.join();
        List<Integer> allNumbers = numberTaskFutures.stream()
                .flatMap(numberTask -> numberTask.join().stream())
                .collect(Collectors.toList());

        executorService.shutdown();
        return allNumbers;
    }

    public void countAndPrintoutNumbers(List<Integer> allNumbers) {
        Map<Integer, Long> countMap = allNumbers.stream()
                .collect(Collectors.groupingByConcurrent(
                        uniqueNumber -> uniqueNumber, Collectors.counting()));

        String output = countMap.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(", "));

        System.out.println(output);

    }
}
