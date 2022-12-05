package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Day6 extends AbstractTask {

    @BeforeEach
    void setup() {}

    @Test
    void solution1() {
        final List<String> lines = readStringLines("input6.txt");
        final List<MovedCrate> movedCrates = parseCommands(lines);
        final List<Stack<String>> stacks = parseStacks(lines);
        for (MovedCrate movedCrate : movedCrates) {
            for (int i = 0; i < movedCrate.amount; i++) {
                String crate = stacks.get(movedCrate.from - 1).pop();
                stacks.get(movedCrate.to - 1).push(crate);
            }
        }
        System.out.println("SOLUTION 1: "+getSolution(stacks));
    }

    @Test
    void solution2() {
        final List<String> lines = readStringLines("input6.txt");
        final List<MovedCrate> movedCrates = parseCommands(lines);
        final List<Stack<String>> stacks = parseStacks(lines);
        for (MovedCrate movedCrate : movedCrates) {
            Stack<String> movingStack = new Stack<>();
            for (int i = 0; i < movedCrate.amount; i++) {
                String crate = stacks.get(movedCrate.from - 1).pop();
                movingStack.push(crate);
            }
            while (!movingStack.isEmpty()) {
                stacks.get(movedCrate.to - 1).push(movingStack.pop());
            }
        }
        System.out.println("SOLUTION 2: "+getSolution(stacks));
    }

    private StringBuilder getSolution(List<Stack<String>> stacks) {
        StringBuilder result = new StringBuilder();
        for (Stack<String> stack : stacks) {
            String topCharacter = stack.pop();
            result.append(topCharacter);
        }
        return result;
    }

    private List<Stack<String>> parseStacks(List<String> lines) {
        final List<Stack<String>> stacks = new ArrayList<>();
        int emptyLineIndex = lines.indexOf("");
        String[] stackNumbers = lines
                .get(emptyLineIndex - 1)
                .replace(" 1", "1")
                .replace("   ", " ")
                .replace(" ", ",")
                .split(",");
        for (int i = 0; i < stackNumbers.length; i++) {
            stacks.add(new Stack<>());
        }

        for (int i = emptyLineIndex - 2; i >= 0; i--) {
            String line = lines.get(i);
            for (int j = 0; j < stacks.size(); j++) {
                int index = j * 4 + 1;
                if (index < line.length()) {
                    String character = String.valueOf(line.charAt(index));
                    if (!character.equals(" ")) {
                        stacks.get(j).push(character);
                    }
                }
            }
        }
        return stacks;
    }

    private List<MovedCrate> parseCommands(List<String> lines) {
        return lines
                .stream()
                .filter(line -> line.startsWith("move"))
                .map(line -> line
                        .replace("move ", "")
                        .replace(" from ", ",")
                        .replace(" to ", ","))
                .map(line -> {
                    String[] split = line.split(",");
                    return new MovedCrate(
                            Integer.parseInt(split[0]),
                            Integer.parseInt(split[1]),
                            Integer.parseInt(split[2]));
                })
                .toList();
    }

    static class MovedCrate {
        int amount;
        int from;
        int to;

        public MovedCrate(int amount, int from, int to) {
            this.amount = amount;
            this.from = from;
            this.to = to;
        }
    }
}