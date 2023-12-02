package com.valverde.adventofcode2023;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

public abstract class AbstractTask {
    public Map<String, Integer> wordsToDigit = Map.of(
            "one", 1,
            "two", 2,
            "three", 3,
            "four", 4,
            "five", 5,
            "six", 6,
            "seven", 7,
            "eight", 8,
            "nine", 9);

    public Map<Integer, String> digitsToWords = Map.of(
            1, "one",
            2, "two",
            3, "three",
            4, "four",
            5, "five",
            6, "six",
            7, "seven",
            8, "eight",
            9, "nine");

    Set<String> stringToCharactersSet(String string) {
        Set<String> set = new HashSet<>();
        for (int i = 0 ; i < string.length(); i++) {
            set.add(String.valueOf(string.charAt(i)));
        }
        return set;
    }

    List<String> readStringLines(final String fileName) {
        final File file = new File("input/adventofcode2023/%s".formatted(fileName));
        try {
            return new ArrayList<>(Files
                    .readAllLines(file.toPath(), Charset.defaultCharset()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    <T> List<List<T>> readLinesSeparatedByEmptyLine(final String fileName, final Class<T> clazz) {
        final List<String> lines = readStringLines(fileName);
        final List<List<T>> groups = new ArrayList<>();
        List<T> currentGroup = new ArrayList<>();
        for (String line : lines) {
            if (line.equals("")) {
                groups.add(currentGroup);
                currentGroup = new ArrayList<>();
            } else {
                if (clazz.equals(Integer.class)) {
                    Integer i = Integer.parseInt(line);
                    currentGroup.add((T) i);
                } else {
                    currentGroup.add((T) line);
                }
            }
        }
        groups.add(currentGroup);
        return groups;
    }

    List<Integer> readIntLines(final String fileName) {
        final File file = new File("input/adventofcode2023/%s".formatted(fileName));
        try {
            return new ArrayList<>(Files
                    .readAllLines(file.toPath(), Charset.defaultCharset()))
                    .stream()
                    .map(v -> Integer.parseInt(v))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
