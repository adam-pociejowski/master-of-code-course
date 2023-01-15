package com.valverde.adventofcode2022;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractTask {

    Set<String> stringToCharactersSet(String string) {
        Set<String> set = new HashSet<>();
        for (int i = 0 ; i < string.length(); i++) {
            set.add(String.valueOf(string.charAt(i)));
        }
        return set;
    }

    List<String> readStringLines(final String fileName) {
        final File file = new File("input/adventofcode2022/%s".formatted(fileName));
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
        final File file = new File("input/adventofcode2022/%s".formatted(fileName));
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
