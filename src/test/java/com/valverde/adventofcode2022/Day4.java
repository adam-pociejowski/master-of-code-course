package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Day4 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
        final List<String> lines = readStringLines("input4.txt");
        List<Pair> pairs = lines
                .stream()
                .map(l -> {
                    String[] split = l.split(",");
                    String[] assigmentSplit1 = split[0].split("-");
                    String[] assigmentSplit2 = split[1].split("-");
                    return new Pair(
                            new Assigment(Integer.parseInt(assigmentSplit1[0]), Integer.parseInt(assigmentSplit1[1])),
                            new Assigment(Integer.parseInt(assigmentSplit2[0]), Integer.parseInt(assigmentSplit2[1])));
                })
                .toList();
        int result = 0;
        for (Pair p : pairs) {
            if (p.assigment1.sections.containsAll(p.assigment2.sections) || p.assigment2.sections.containsAll(p.assigment1.sections)) {
                result++;
            }
        }
        System.out.println("SOLUTION 1: "+result);

    }

    @Test
    void solution2() {
        final List<String> lines = readStringLines("input4.txt");
        List<Pair> pairs = lines
                .stream()
                .map(l -> {
                    String[] split = l.split(",");
                    String[] assigmentSplit1 = split[0].split("-");
                    String[] assigmentSplit2 = split[1].split("-");
                    return new Pair(
                            new Assigment(Integer.parseInt(assigmentSplit1[0]), Integer.parseInt(assigmentSplit1[1])),
                            new Assigment(Integer.parseInt(assigmentSplit2[0]), Integer.parseInt(assigmentSplit2[1])));
                })
                .toList();
        int result = 0;
        for (Pair p : pairs) {
            Integer a1Start = p.assigment1.sections.get(0);
            Integer a1End = p.assigment1.sections.get(p.assigment1.sections.size() - 1);
            Integer a2Start = p.assigment2.sections.get(0);
            Integer a2End = p.assigment2.sections.get(p.assigment2.sections.size() - 1);
            if (a1End >= a2Start && a1Start <= a2End) {
                result++;
            }
        }
        System.out.println("SOLUTION 2: "+result);

    }

    class Pair {
        Assigment assigment1;
        Assigment assigment2;

        public Pair(Assigment assigment1, Assigment assigment2) {
            this.assigment1 = assigment1;
            this.assigment2 = assigment2;
        }
    }

    class Assigment {
        List<Integer> sections;

        public Assigment(int from, int to) {
            sections = new ArrayList<>();
            for (int i = from; i <= to; i++) {
                sections.add(i);
            }
        }
    }
}
