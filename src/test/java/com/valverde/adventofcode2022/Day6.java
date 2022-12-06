package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
        final List<String> lines = readStringLines("input6.txt");
        String line = lines.get(0);
        int processedCharacters = getProcessedCharacters(line, 4);
        System.out.println("SOLUTION 1: "+processedCharacters);
    }

    @Test
    void solution2() {
        final List<String> lines = readStringLines("input6.txt");
        String line = lines.get(0);
        int processedCharacters = getProcessedCharacters(line, 14);
        System.out.println("SOLUTION 2: "+processedCharacters);

    }

    private int getProcessedCharacters(final String line, final int distinctCharacters) {
        for (int i = 0; i < line.length(); i++) {
            String substring = line.substring(i, i + distinctCharacters);
            Set<String> set = stringToCharactersSet(substring);
            if (set.size() == distinctCharacters) {
                return i + distinctCharacters;
            }
        }
        return -1;
    }
}
