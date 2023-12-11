package com.valverde.adventofcode2023;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day11 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
        final List<String> lines = readStringLines("input11.txt");

        final List<String> expandedRows = new ArrayList<>();
        for (String l : lines) {
            expandedRows.add(l);
            if (!l.contains("#")) {
                expandedRows.add(l);
            }
        }

        final List<String> expandedSpace = new ArrayList<>(expandedRows);
        int expandingOnIndex = 0;
        for (int x = 0; x < expandedRows.get(0).length(); x++) {
            if (!containsGalaxyInColumn(expandedRows, x)) {
                for (int y = 0; y < expandedRows.size(); y++) {
                    String row = expandedSpace.get(y);
                    String updated = row.substring(0, expandingOnIndex) + "." + row.substring(expandingOnIndex);
                    expandedSpace.set(y, updated);
                }
                expandingOnIndex++;
            }
            expandingOnIndex++;
        }

        List<Galaxy> galaxies = getGalaxies(expandedSpace);
        int result = 0;
        for (Galaxy g1 : galaxies) {
            for (Galaxy g2 : galaxies) {
                if (g1.num < g2.num) {
                    long distance = Math.abs(g1.x - g2.x) + Math.abs(g1.y - g2.y);
                    result += distance;
                }
            }
        }
        System.out.println("Task1: "+result);

    }

    private int expandedRowsOrColsBetween(List<Integer> list, long from, long to) {
        int i = 0;
        for (Integer num : list) {
            if (num >= from && num <= to) {
                i++;
            }
        }
        return i;
    }

    private boolean containsGalaxyInColumn(List<String> data, int index) {
        return data
                .stream()
                .map(s -> s.charAt(index))
                .anyMatch(s -> s == '#');
    }

    @Test
    void solution2() {
        final List<String> lines = readStringLines("input11.txt");
        List<Integer> expandedRows = new ArrayList<>();
        int i = 0;
        for (String l : lines) {
            if (!l.contains("#")) {
                expandedRows.add(i);
            }
            i++;
        }

        List<Integer> expandedColumns = new ArrayList<>();
        for (int x = 0; x < lines.get(0).length(); x++) {
            if (!containsGalaxyInColumn(lines, x)) {
                expandedColumns.add(x);
            }
        }

        List<Galaxy> galaxies = getGalaxies(lines);

        BigInteger result = BigInteger.ZERO;
        for (Galaxy g1 : galaxies) {
            for (Galaxy g2 : galaxies) {
                if (g1.num < g2.num) {
                    long distance = Math.abs(g1.x - g2.x) + Math.abs(g1.y - g2.y);
                    int rowsExtended = expandedRowsOrColsBetween(expandedRows, Math.min(g1.y, g2.y), Math.max(g1.y, g2.y));
                    int colsExtended = expandedRowsOrColsBetween(expandedColumns, Math.min(g1.x, g2.x), Math.max(g1.x, g2.x));
                    BigInteger extendedSpace = BigInteger.valueOf(rowsExtended).add(BigInteger.valueOf(colsExtended));
                    BigInteger extendedSpaceDistance = extendedSpace.multiply(BigInteger.valueOf(999999));
                    result = result.add(BigInteger.valueOf(distance).add(extendedSpaceDistance));
                }
            }
        }
        System.out.println("Task2: "+result);
    }

    private List<Galaxy> getGalaxies(List<String> lines) {
        int y = 0;
        List<Galaxy> galaxies = new ArrayList<>();
        for (String l : lines) {
            int x = 0;
            for (String c : stringToCharactersList(l)) {
                if (c.equals("#")) {
                    galaxies.add(new Galaxy(x, y, galaxies.size() + 1));
                }
                x++;
            }
            y++;
        }
        return galaxies;
    }

    class Galaxy {
        private long x;
        private long y;
        private long num;

        public Galaxy(long x, long y, long num) {
            this.x = x;
            this.y = y;
            this.num = num;
        }

        @Override
        public String toString() {
            return "Galaxy{" +
                    "x=" + x +
                    ", y=" + y +
                    ", num=" + num +
                    '}';
        }
    }
}
