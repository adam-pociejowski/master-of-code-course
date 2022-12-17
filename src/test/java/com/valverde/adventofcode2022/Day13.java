package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

public class Day13 extends AbstractTask {

    @BeforeEach
    void setup() { }

    @Test
    void solution1() {
        List<ListPair> pairs = getListPairs();
        int result = 0;
        for (int i = 0; i < pairs.size(); i++) {
            ListPair listPair = pairs.get(i);
            int compare = compare(listPair.left, listPair.right);
            if (compare == 0) {
                System.out.printf("ERROR: Index: %s - (%s  %s)  compare == 0", i, listPair.left, listPair.right);
            } else if (compare == 1) {
                result += (i + 1);
            }
        }
        System.out.println("SOLUTION 1: "+result);
    }

    @Test
    void solution2() {
        List<ListPair> pairs = getListPairs();
        Object divider1 = parseString("[[2]]", true);
        Object divider2 = parseString("[[6]]", true);
        pairs.add(new ListPair(divider1, divider2));
        List<Object> lines = pairs
                .stream()
                .map(p -> Arrays.asList(p.left, p.right))
                .flatMap(Collection::stream)
                .sorted((Object a, Object b) -> compare(b, a))
                .toList();
        int divider1Index = lines.indexOf(divider1) + 1;
        int divider2Index = lines.indexOf(divider2) + 1;
        int result = divider1Index * divider2Index;
        System.out.println("SOLUTION 2: "+result);
    }

    private int compare(Object left, Object right) {
        if (left instanceof Integer && right instanceof Integer) {
            return Integer.compare((int)right, (int)left);
        }
        return compareList(left, right);
    }

    private int compareList(Object left, Object right) {
        List<Object> leftList;
        List<Object> rightList;
        if (left instanceof Integer) {
            leftList = Collections.singletonList(left);
        } else {
            leftList = (List<Object>)left;
        }
        if (right instanceof Integer) {
            rightList = Collections.singletonList(right);
        } else {
            rightList = (List<Object>)right;
        }
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex < leftList.size() && rightIndex < rightList.size()) {
            Object leftElement = leftList.get(leftIndex++);
            Object rightElement = rightList.get(rightIndex++);
            int compare = compare(leftElement, rightElement);
            if (compare != 0) {
                return compare;
            }
        }
        return Integer.compare(rightList.size(), leftList.size());
    }

    private List<ListPair> getListPairs() {
        final List<String> lines = readStringLines("input13.txt");
        List<ListPair> pairs = new ArrayList<>();
        for (int i = 0; i < lines.size(); i+=3) {
            List<Object> left = (List<Object>)parseString(lines.get(i), true);
            List<Object> right = (List<Object>)parseString(lines.get(i + 1), true);
            pairs.add(new ListPair(left, right));
        }
        return pairs;
    }

    private Object parseString(String string, boolean root) {
        if (!string.contains(",")) { //single element
            if (string.startsWith("[")) {
                if (string.length() == 2) { //empty list
                    return new ArrayList<>();
                } else { // empty list in list
                    List<Object> list = new ArrayList<>();
                    list.add(parseString(string.substring(1, string.length() - 1), false));
                    return list;
                }
            } else { //single number
                return Integer.parseInt(string);
            }
        }
        List<String> parts = splitByComma(string);
        List<Object> list = new ArrayList<>();
        for (String part: parts) {
            if (part.startsWith("[")) {
                if (root) {
                    return parseString(part.substring(1, part.length() - 1), false);
                } else if (part.equals("[]")) {
                    list.add(new ArrayList<>());
                } else {
                    list.add(parseString(part.substring(1, part.length() - 1), false));
                }
            } else {
                list.add(parseString(part, false));
            }
        }
        return list;
    }

    private List<String> splitByComma(String string) {
        List<String> split = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c >= '0' && c <= '9') {
                StringBuilder builder = new StringBuilder();
                builder.append(c);
                while (i < string.length() - 1) {
                    c = string.charAt(++i);
                    if (c == ',') {
                        break;
                    }
                    builder.append(c);
                }
                split.add(builder.toString());
            } else if (string.charAt(i) == '[') {
                StringBuilder builder = new StringBuilder();
                int openBrackets = 1;
                builder.append(c);
                do {
                    c = string.charAt(++i);
                    if (c == '[') {
                        openBrackets++;
                    } else if (c == ']') {
                        openBrackets--;
                    }
                    builder.append(c);
                } while (openBrackets > 0);
                split.add(builder.toString());
            }
        }
        return split;
    }

    static class ListPair {
        Object left;
        Object right;

        public ListPair(Object left, Object right) {
            this.left = left;
            this.right = right;
        }
    }
}
