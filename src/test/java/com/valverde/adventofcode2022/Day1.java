package com.valverde.adventofcode2022;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Day1 extends AbstractTask {

    @Test
    void solution1() {
        final List<List<Integer>> calories = readLinesSeparatedByEmptyLine("input1.txt", Integer.class);
        final List<Integer> partialSums = calories
                .stream()
                .map(c -> c
                        .stream()
                        .reduce(Integer::sum)
                        .orElseThrow())
                .toList();
        final Integer max = partialSums
                .stream()
                .mapToInt(v -> v)
                .max()
                .orElseThrow();
        System.out.printf("PART_1: %s%n", max);
        System.out.printf("PART_2: %s%n", getSumOfThreeGreatest(partialSums));
    }

    private int getSumOfThreeGreatest(List<Integer> partialSums) {
        final List<Integer> sorted = partialSums
                .stream()
                .sorted((f1, f2) -> Integer.compare(f2, f1))
                .toList();
        int sum = 0;
        Iterator<Integer> iterator = sorted.iterator();
        int index = 0;
        while (iterator.hasNext() && index < 3) {
            sum += iterator.next();
            index++;
        }
        return sum;
    }
}
