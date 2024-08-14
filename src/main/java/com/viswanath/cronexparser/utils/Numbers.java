package com.viswanath.cronexparser.utils;

import java.util.ArrayList;
import java.util.List;

public class Numbers {
    public static List<Integer> list(int number) {
        return list(number, number);
    }

    public static List<Integer> list(int start, int end) {
        return list(start, end, 1);
    }

    public static List<Integer> list(int start, int end, int step) {
        List<Integer> numbers = new ArrayList<>();
        for(int i = start; i <= end; i += step) {
            numbers.add(i);
        }
        return numbers;
    }

    public static String printable(List<Integer> numbers) {
        StringBuilder stringBuilder = new StringBuilder();
        numbers.forEach(number -> stringBuilder.append(String.format(" %d", number)));
        return stringBuilder.toString().trim();
    }
}
