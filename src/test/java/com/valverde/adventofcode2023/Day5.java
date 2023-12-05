package com.valverde.adventofcode2023;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

public class Day5 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
//        final List<String> lines = readStringLines("inputTest.txt");
        final List<String> lines = readStringLines("input5.txt");
        List<Long> seeds = getSeeds(lines.get(0)
                .replace("seeds: ", ""));

        List<Maps> mapsList = getMaps(lines);
        Long result = seeds
                .stream()
                .map(seed -> {
                    Long location = mapTo(seed, "location", mapsList);
                    System.out.println(seed + "->" + location);
                    return location;
                })
                .min(Long::compareTo)
                .get();
        System.out.println("Task1: "+result);

    }

    private List<Maps> getMaps(List<String> lines) {
        List<Maps> mapsList = new ArrayList<>();
        Maps currentMap = null;
        for (int i = 2; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains("map")) {
                String replaced = line.replace(" map:", "");
                String[] split = replaced.split("-to-");
                currentMap = new Maps(split[0], split[1], new ArrayList<>());
                mapsList.add(currentMap);
            } else if (!line.equals("")) {
                List<Long> numbers = getSeeds(line);
                currentMap.intervals.add(new Interval(
                        numbers.get(1),
                        numbers.get(1) + numbers.get(2) - 1,
                        numbers.get(0) - numbers.get(1)));
            }
        }
        return mapsList;
    }

    private List<Long> getSeeds(String replace) {
        return Stream
                .of(replace.split(" "))
                .map(Long::valueOf)
                .toList();
    }

    private Long mapTo(Long seed, String targetCategory, List<Maps> mapsList) {
        Long value = seed;
        for (Maps maps : mapsList) {
            value = mapByInterval(value, maps);
            if (maps.target.equals(targetCategory)) {
                break;
            }
        }
        return value;
    }

    private Long mapByInterval(Long source, Maps maps) {
        for (Interval interval : maps.intervals) {
            if (source >= interval.start && source <= interval.end) {
                return source + interval.destinationPadding;
            }
        }
        return source;
    }

    @Test
    void solution2() {
//        final List<String> lines = readStringLines("inputTest.txt");
        final List<String> lines = readStringLines("input5.txt");
        List<Long> numbers = getSeeds(lines.get(0)
                .replace("seeds: ", ""));
        List<Maps> mapsList = getMaps(lines);
        Long result = null;
        for (int i = 0; i < numbers.size(); i += 2) {
            Long start = numbers.get(i);
            Long range = numbers.get(i + 1);
            System.out.println("Start: "+start+", range: "+range);
            for (Long l = start; l < start + range; l++) {
                Long location = mapTo(l, "location", mapsList);
                if (result == null || location < result) {
                    result = location;
                }
            }
        }

        System.out.println("Task2: "+result);
    }

    static class Maps {
        private final String source;
        private final String target;
        private final List<Interval> intervals;

        public Maps(String source, String target, List<Interval> intervals) {
            this.source = source;
            this.target = target;
            this.intervals = intervals;
        }

        @Override
        public String toString() {
            return "Maps{" +
                    "source='" + source + '\'' +
                    ", target='" + target + '\'' +
                    '}';
        }
    }

    static class Interval {
        private final Long start;
        private final Long end;
        private final Long destinationPadding;

        public Interval(Long start, Long end, Long destinationPadding) {
            this.start = start;
            this.end = end;
            this.destinationPadding = destinationPadding;
        }
    }
}
