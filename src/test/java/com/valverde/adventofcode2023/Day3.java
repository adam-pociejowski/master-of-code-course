package com.valverde.adventofcode2023;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
        final List<String> lines = readStringLines("input3.txt");
        List<List<Number>> numbers = getNumbers(lines);
        List<List<Operator>> operators = getOperators(lines);
        int sum = 0;
        int rowIndex = 0;
        for (List<Number> row : numbers) {
            for (Number n : row) {
                if (isAdjactedToAsterix(rowIndex, n, operators)) {
                    sum += n.value;
                }
            }
            rowIndex++;
        }

        System.out.println("Task1: "+sum);
    }

    private List<List<Operator>> getOperators(List<String> lines) {
        String asterixRegex = "[^\\.\\d]+";
        Pattern asterixPattern = Pattern.compile(asterixRegex);
        List<List<Operator>> operators = new ArrayList<>();
        int index = 0;
        for (String line : lines) {
            Matcher matcher = asterixPattern.matcher(line);
            List<Operator> lineOperators = new ArrayList<>();
            operators.add(lineOperators);
            while (matcher.find()) {
                lineOperators.add(new Operator(matcher.start(), matcher.group()));
            }
            index++;
        }
        return operators;
    }

    private List<List<Number>> getNumbers(List<String> lines) {
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        List<List<Number>> numbers = new ArrayList<>();
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            List<Number> lineNumbers = new ArrayList<>();
            numbers.add(lineNumbers);
            while (matcher.find()) {
                lineNumbers.add(new Number(matcher.start(), matcher.end() - 1, Integer.parseInt(matcher.group())));
            }
        }
        return numbers;
    }

    private boolean isAdjactedToAsterix(int row, Number n, List<List<Operator>> operators) {
        int startRow = row > 0 ? row - 1 : row;
        int endRow = row < operators.size() - 1 ? row + 1 : row;

        for (int i = startRow; i <= endRow; i++) {
            List<Operator> lineOperators = operators.get(i);
            for (Operator o : lineOperators) {
                if (o.index >= n.start - 1 && o.index <= n.end + 1) {
                    return true;
                }
            }
        }
        return false;
    }

    @Test
    void solution2() {
        final List<String> lines = readStringLines("input3.txt");
        List<List<Number>> numbers = getNumbers(lines);
        List<List<Operator>> operators = getOperators(lines)
                .stream()
                .map(list -> list
                .stream()
                .filter(o -> o.asterix.equals("*")).toList())
                .toList();

        int rowIndex = 0;
        int sum = 0;
        for (List<Operator> row : operators) {
            for (Operator o : row) {
                Optional<Integer> integer = areTwoPartsAdjactedToAsterix(rowIndex, o, numbers);
                if (integer.isPresent()) {
                    sum += integer.get();
                }
            }
            rowIndex++;
        }

        System.out.println("Task1: "+sum);

    }

    private Optional<Integer> areTwoPartsAdjactedToAsterix(int row, Operator o, List<List<Number>> numbers) {
        int startRow = row > 0 ? row - 1 : row;
        int endRow = row < numbers.size() - 1 ? row + 1 : row;

        List<Number> adjacted = new ArrayList<>();
        for (int i = startRow; i <= endRow; i++) {
            List<Number> lineNumbers = numbers.get(i);
            for (Number n : lineNumbers) {
                if (o.index >= n.start - 1 && o.index <= n.end + 1) {
                    adjacted.add(n);
                }
            }
        }
        if (adjacted.size() == 2) {
            return Optional.of(adjacted.get(0).value * adjacted.get(1).value);
        }
        return Optional.empty();
    }

    class Operator {
        private int index;
        private String asterix;

        public int getIndex() {
            return index;
        }

        public String getAsterix() {
            return asterix;
        }

        public Operator(int index, String asterix) {
            this.index = index;
            this.asterix = asterix;
        }
    }

    class Number {
        private int start;
        private int end;
        private int value;
        private String asterix;

        public Number(int start, int end, int value) {
            this.start = start;
            this.end = end;
            this.value = value;
        }

        public Number(int start, int end, String asterix) {
            this.start = start;
            this.end = end;
            this.asterix = asterix;
        }
    }
}
