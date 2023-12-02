package com.valverde.adventofcode2023;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Day2 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
        final List<String> lines = readStringLines("input2.txt");
        List<SubSet> subSets = lines
                .stream()
                .map(l -> {
                    String[] split1 = l.split(": ");
                    int id = Integer.parseInt(split1[0].replace("Game ", ""));
                    String[] subsetsSplit = split1[1].replace(" ", "").split(";");
                    SubSet subSet = new SubSet(id, 0, 0, 0);
                    for (String s : subsetsSplit) {
                        String[] elements = s.split(",");
                        for (String e : elements) {
                            if (e.contains("blue")) {
                                int blue = Integer.parseInt(e.replace("blue", ""));
                                if (subSet.blue < blue) {
                                    subSet.blue = blue;
                                }
                            } else if (e.contains("red")) {
                                int red = Integer.parseInt(e.replace("red", ""));
                                if (subSet.red < red) {
                                    subSet.red = red;
                                }
                            } else {
                                int green = Integer.parseInt(e.replace("green", ""));
                                if (subSet.green < green) {
                                    subSet.green = green;
                                }
                            }
                        }
                    }
                    return subSet;
                })
                .toList();

        int reqRed = 12;
        int reqGreen = 13;
        int reqBlue = 14;
        int sum = 0;
        for (SubSet subSet : subSets) {
            if (subSet.blue <= reqBlue && subSet.red <= reqRed && subSet.green <= reqGreen) {
                sum += subSet.id;
            }
        }

        System.out.println("task1: " + sum);
    }

    @Test
    void solution2() {
        final List<String> lines = readStringLines("input2.txt");
        List<SubSet> subSets = lines
                .stream()
                .map(l -> {
                    String[] split1 = l.split(": ");
                    int id = Integer.parseInt(split1[0].replace("Game ", ""));
                    String[] subsetsSplit = split1[1].replace(" ", "").split(";");
                    SubSet subSet = new SubSet(id, 0, 0, 0);
                    for (String s : subsetsSplit) {
                        String[] elements = s.split(",");
                        for (String e : elements) {
                            if (e.contains("blue")) {
                                int blue = Integer.parseInt(e.replace("blue", ""));
                                if (subSet.blue < blue) {
                                    subSet.blue = blue;
                                }
                            } else if (e.contains("red")) {
                                int red = Integer.parseInt(e.replace("red", ""));
                                if (subSet.red < red) {
                                    subSet.red = red;
                                }
                            } else {
                                int green = Integer.parseInt(e.replace("green", ""));
                                if (subSet.green < green) {
                                    subSet.green = green;
                                }
                            }
                        }
                    }
                    return subSet;
                })
                .toList();

        int sum = 0;
        for (SubSet subSet : subSets) {
            int pow = subSet.blue * subSet.red * subSet.green;
            sum += pow;
        }

        System.out.println("task2: " + sum);
    }

    private static class SubSet {
        private final int id;
        private int red;
        private int blue;
        private int green;

        public SubSet(int id, int red, int blue, int green) {
            this.id = id;
            this.red = red;
            this.blue = blue;
            this.green = green;
        }
    }
}
