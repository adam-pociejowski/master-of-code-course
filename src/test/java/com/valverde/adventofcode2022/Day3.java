package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Day3 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
        final List<String> lines = readStringLines("input3.txt");
        int sum = 0;
        for (String line : lines) {
            String part1 = line.substring(0, line.length() / 2);
            String part2 = line.substring(line.length() / 2);
            Set<Integer> part1Set = getPrioritySet(part1);
            Set<Integer> part2Set = getPrioritySet(part2);
            sum += findCommonSetsValue(part1Set, part2Set);
        }
        System.out.println("SOLUTION 1: "+sum);
    }

    @Test
    void solution2() {
        final List<String> lines = readStringLines("input3.txt");
        int sum = 0;
        for (int i = 0; i < lines.size(); i += 3) {
            String line1 = lines.get(i);
            String line2 = lines.get(i + 1);
            String line3 = lines.get(i + 2);
            Map<Integer, Integer> charactersMap = new HashMap<>();
            appendCharactersToMap(line1, charactersMap);
            appendCharactersToMap(line2, charactersMap);
            appendCharactersToMap(line3, charactersMap);
            for (Map.Entry<Integer, Integer> entry : charactersMap.entrySet()) {
                if (entry.getValue() == 3) {
                    sum += entry.getKey();
                    break;
                }
            }
        }
        System.out.println("SOLUTION 2: "+sum);
    }

    private void appendCharactersToMap(final String line, final Map<Integer, Integer> charactersMap) {
        Set<Integer> prioritySet = getPrioritySet(line);
        for (int priority : prioritySet) {
            if (!charactersMap.containsKey(priority)) {
                charactersMap.put(priority, 0);
            }
            charactersMap.put(priority, charactersMap.get(priority) + 1);
        }
    }

    private int findCommonSetsValue(final Set<Integer> set1, final Set<Integer> set2) {
        for (int element : set1) {
            if (set2.contains(element)) {
                return element;
            }
        }
        throw new RuntimeException("");
    }

    private Set<Integer> getPrioritySet(String part) {
        Set<Integer> partSet = new HashSet<>();
        for (int i = 0 ; i < part.length(); i++) {
            int priority = getPriority(part.charAt(i));
            partSet.add(priority);
        }
        return partSet;
    }

    private int getPriority(char c) {
        int priority;
        if (c >= 97 && c <= 122) {
            priority = c - 96;
        } else {
            priority = c - 38;
        }
        return priority;
    }
}
