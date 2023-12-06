package com.valverde.adventofcode2023;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

public class Day6 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
//        final List<String> lines = readStringLines("inputTest.txt");
        final List<String> lines = readStringLines("input6.txt");

        List<Integer> times = Stream.of(lines
                .get(0)
                .replace("Time:", "")
                .replaceAll(" +", " ")
                .split(" "))
                .filter(s -> !s.equals(""))
                .map(Integer::parseInt)
                .toList();
        List<Integer> distances = Stream.of(lines
                .get(1)
                .replace("Distance:", "")
                .replaceAll(" +", " ")
                .split(" "))
                .filter(s -> !s.equals(""))
                .map(Integer::parseInt)
                .toList();


        int result = 1;
        for (int i = 0; i < times.size(); i++) {
            int record = distances.get(i);
            int time = times.get(i);
            int beatenTimes = 0;
            for (int t = 1; t < time; t++) {
                int leftTime = time - t;
                int distance = leftTime * t;
                if (distance > record) {
                    beatenTimes++;
                }
            }
//            if (beatenTimes > 0) {
//                result *= beatenTimes;
//            }
            result *= beatenTimes;
        }

        System.out.println("Task1: " + result);

    }

    @Test
    void solution2() {
//        final List<String> lines = readStringLines("inputTest.txt");
        final List<String> lines = readStringLines("input6.txt");

        Long time = Long.valueOf(lines
                .get(0)
                .replaceAll("Time: +", "")
                .replaceAll(" +", ""));
        Long record = Long.valueOf(lines
                .get(1)
                .replaceAll("Distance: +", "")
                .replaceAll(" +", ""));


        int result = 0;
        for (Long t = 1L; t < time; t++) {
            Long leftTime = time - t;
            Long distance = leftTime * t;
            if (distance > record) {
                result++;
            }
        }
        System.out.println("Task2: " + result);
    }
}
