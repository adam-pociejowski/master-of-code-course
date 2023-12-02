package com.valverde.adventofcode2023;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Day1 extends AbstractTask {

    @BeforeEach
    void setup() {}

    @Test
    void solution1() {
        final List<String> lines = readStringLines("input1.txt");
        List<ArrayList<Integer>> arrayLists = lines
                .stream()
                .map(l -> {
                    ArrayList<Integer> objects = new ArrayList<>();
                    for (int i = 0; i < l.length(); i++) {
                        if (l.charAt(i) >= 48 && l.charAt(i) < 58) {
                            objects.add((int) l.charAt(i) - 48);
                        }
                    }
                    return objects;
                })
                .toList();

        int sum = 0;
        for (ArrayList<Integer> numbers : arrayLists) {
            Integer first = numbers.get(0);
            Integer last = numbers.get(numbers.size() - 1);
            int i = Integer.parseInt("%s%s".formatted(first, last));
            sum += i;
        }
        System.out.println("task1: " + sum);
    }

    @Test
    void solution2() {
        final List<String> lines = readStringLines("input1.txt");

        List<ArrayList<Integer>> arrayLists = lines
                .stream()
                .map(l -> {
                    ArrayList<Integer> objects = new ArrayList<>();
                    int lastNumIndex = 0;
                    for (int i = 0; i < l.length(); i++) {
                        if (isDigit(l.charAt(i))) {
                            objects.add((int) l.charAt(i) - 48);
                            lastNumIndex = i;
                            continue;
                        }
                        String substring = l.substring(lastNumIndex, i + 1);
                        Optional<Integer> stringDigit = getStringDigit(substring);
                        if (stringDigit.isPresent()) {
                            objects.add(stringDigit.get());
                            lastNumIndex = i;
                        }

                    }
                    return objects;
                })
                .toList();

        int sum = 0;
        for (ArrayList<Integer> numbers : arrayLists) {
            Integer first = numbers.get(0);
            Integer last = numbers.get(numbers.size() - 1);
            int i = Integer.parseInt("%s%s".formatted(first, last));
            sum += i;
        }
        System.out.println("task2: " + sum);
    }

    private Optional<Integer> getStringDigit(String substring) {
        for (Map.Entry<String, Integer> e : wordsToDigit.entrySet()) {
            if (substring.contains(e.getKey())) {
                return Optional.of(e.getValue());
            }
        }
        return Optional.empty();
    }

    boolean isDigit(char c) {
        return c >= 48 && c < 58;
    }
}
