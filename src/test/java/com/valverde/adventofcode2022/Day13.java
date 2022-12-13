package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Day13 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
        List<Pair> pairs = getPairs();
        int result = 0;
        for (Pair pair : pairs) {
            int compare = compareList(pair.left, pair.right);
            if (compare > 0) {
                result += pairs.indexOf(pair) + 1;
            } else if (compare == 0) {
                System.out.printf("Pair %s: [%s]   [%s]  (%s)\n", pairs.indexOf(pair), pair.left, pair.right, compare);
            }
            System.out.printf("Pair %s: [%s]   [%s]  (%s)\n", pairs.indexOf(pair), pair.left, pair.right, compare);
        }
        System.out.println("SOLUTION 1: "+result);
    }

    private int compareList(String left, String right) {
        if (left.length() == 0 && right.length() == 0) {
            return 0;
        } else if (left.length() == 0) {
            return 1;
        } else if (right.length() == 0) {
            return -1;
        } else if (left.length() == 1 || right.length() == 1) {
            Integer leftNumber = getFirstNumber(left);
            Integer rightNumber = getFirstNumber(right);
            if (Objects.isNull(leftNumber) && Objects.isNull(rightNumber)) {
                return 0;
            } else if (Objects.isNull(leftNumber)) {
                return -1;
            } else if (Objects.isNull(rightNumber)) {
                return 1;
            }
            return rightNumber.compareTo(leftNumber);
        }
        List<String> leftSubLists = splitList(left);
        List<String> rightSubLists = splitList(right);
        Iterator<String> leftIterator = leftSubLists.iterator();
        Iterator<String> rightIterator = rightSubLists.iterator();
        while (leftIterator.hasNext() && rightIterator.hasNext()) {
            String leftSubList = leftIterator.next();
            String rightSubList = rightIterator.next();
            int compare = compareList(leftSubList, rightSubList);
            if (compare != 0) {
                return compare;
            }
        }
        if (leftIterator.hasNext()) {
            return -1;
        } else if (rightIterator.hasNext()) {
            return 1;
        }
        return 0;
    }

    private Integer getFirstNumber(final String string) {
        String removedBrackets = string
                .replaceAll("\\[", "")
                .replaceAll("]", "");
        String numberString = removedBrackets.split(",")[0];
        return numberString.equals("") ? null : Integer.parseInt(numberString);
    }

    private List<String> splitList(String list) {
        int index = 0;
        List<String> subLists = new ArrayList<>();
        while (index < list.length()) {
            char c = list.charAt(index);
            if (c == '[') {
                int startIndex = index;
                int openBrackets = 1;
                int closeBrackets = 0;
                while (openBrackets > closeBrackets) {
                    index++;
                    char character = list.charAt(index);
                    if (character == '[') {
                        openBrackets++;
                    } else if (character == ']') {
                        closeBrackets++;
                    }
                }
                subLists.add(list.substring(startIndex + 1, index));
            } else if (c > '0' && c <= '9') {
                subLists.add(list.substring(index, index + 1));
            }
            index++;
        }
        return subLists;
    }

    @Test
    void solution2() {
        final List<String> lines = readStringLines("input9.txt");
        int result = 0;
        System.out.println("SOLUTION 2: "+result);
    }

    private List<Pair> getPairs() {
        final List<String> lines = readStringLines("input13.txt");
        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < lines.size(); i+=3) {
            pairs.add(new Pair(
                    lines.get(i).substring(1, lines.get(i).length() - 1),
                    lines.get(i + 1).substring(1, lines.get(i + 1).length() - 1)));
        }
        return pairs;
    }

    class Pair {
        String left;
        String right;

        public Pair(String left, String right) {
            this.left = left;
            this.right = right;
        }
    }
}
