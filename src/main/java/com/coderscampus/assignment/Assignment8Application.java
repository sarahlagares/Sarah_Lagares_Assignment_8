package com.coderscampus.assignment;

import java.util.List;

public class Assignment8Application {
    public static void main(String[] args) {

        FileService fileService = new FileService();
        List<Integer> allNumbers = fileService.getAllNumbers();
        fileService.countAndPrintoutNumbers(allNumbers);
    }
}
