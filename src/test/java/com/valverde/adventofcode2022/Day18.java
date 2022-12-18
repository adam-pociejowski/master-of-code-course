package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;

public class Day18 extends AbstractTask {
    int size = 20;
    Set<Cube> visited = new HashSet<>();

    @BeforeEach
    void setup() {}

    @Test
    void solution1() {
        final List<Cube> cubes = getCubes();
        Set<Side> exposedSides = getSides(cubes)
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        System.out.println("SOLUTION 1: "+exposedSides.size());
    }

    @Test
    void solution2() {
        final List<Cube> cubes = getCubes();
        final boolean[][][] grid = new boolean[size][size][size];
        cubes.forEach(c -> grid[c.x][c.y][c.z] = true);
        final List<Cube> trappedAirCubes = getTrappedAirCubes(grid);
        final Set<Side> trappedSides = getSides(trappedAirCubes)
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        final Set<Side> exposedSides = getSides(cubes)
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        final int result = exposedSides.size() - trappedSides.size();
        System.out.println("SOLUTION 2: "+result);
    }

    private List<Cube> getTrappedAirCubes(boolean[][][] grid) {
        List<Cube> trappedCubes = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    if (!grid[x][y][z]) {
                        visited = new HashSet<>();
                        boolean escaped = tryToEscape(grid, x, y, z);
                        if (!escaped) {
                            trappedCubes.add(new Cube(x, y, z));
                        }
                    }
                }
            }
        }
        return trappedCubes;
    }

    private boolean tryToEscape(boolean[][][] grid, int x, int y, int z) {
        if (x <= 0 || x >= size - 1) {
            return true;
        } else if (y <= 0 || y >= size - 1) {
            return true;
        } else if (z <= 0 || z >= size - 1) {
            return true;
        }
        List<Cube> possibleMoves = getPossibleMoves(grid, x, y, z)
                .stream()
                .filter(c -> !visited.contains(c))
                .collect(Collectors.toList());
        for (Cube c : possibleMoves) {
            visited.add(c);
            boolean escaped = tryToEscape(grid, c.x, c.y, c.z);
            if (escaped) {
                return true;
            }
        }
        return false;
    }

    private List<Cube> getPossibleMoves(boolean[][][] grid, int x, int y, int z) {
        List<Cube> cubes = new ArrayList<>();
        if (!grid[x + 1][y][z]) {
            cubes.add(new Cube(x + 1, y, z));
        }
        if (!grid[x - 1][y][z]) {
            cubes.add(new Cube(x - 1, y, z));
        }
        if (!grid[x][y + 1][z]) {
            cubes.add(new Cube(x, y + 1, z));
        }
        if (!grid[x][y - 1][z]) {
            cubes.add(new Cube(x, y - 1, z));
        }
        if (!grid[x][y][z + 1]) {
            cubes.add(new Cube(x, y, z + 1));
        }
        if (!grid[x][y][z - 1]) {
            cubes.add(new Cube(x, y, z - 1));
        }
        return cubes;
    }

    private Map<Side, Integer> getSides(List<Cube> cubes) {
        final Map<Side, Integer> sides = new HashMap<>();
        cubes.forEach(c -> {
            Side bottom = getSide(c.x, c.y, c.z, c.x + 1, c.y + 1, c.z);
            Side front = getSide(c.x, c.y, c.z, c.x, c.y + 1, c.z + 1);
            Side left = getSide(c.x, c.y, c.z, c.x + 1, c.y, c.z + 1);
            Side right = getSide(c.x, c.y + 1, c.z, c.x + 1, c.y + 1, c.z + 1);
            Side rear = getSide(c.x + 1, c.y, c.z, c.x + 1, c.y + 1, c.z + 1);
            Side top = getSide(c.x, c.y, c.z + 1, c.x + 1, c.y + 1, c.z + 1);
            incrementOccurrence(sides, bottom);
            incrementOccurrence(sides, front);
            incrementOccurrence(sides, left);
            incrementOccurrence(sides, right);
            incrementOccurrence(sides, rear);
            incrementOccurrence(sides, top);
        });
        return sides;
    }

    private void incrementOccurrence(Map<Side, Integer> sides, Side key) {
        if (sides.containsKey(key)) {
            Integer count = sides.get(key);
            sides.put(key, count + 1);
        } else {
            sides.put(key, 1);
        }
    }

    private Side getSide(int x1, int y1, int z1, int x2, int y2, int z2) {
        return new Side(x1, y1, z1, x2, y2, z2);
    }

    private List<Cube> getCubes() {
        return readStringLines("input18.txt")
                .stream()
                .map(l -> {
                    String[] split = l.split(",");
                    return new Cube(
                            Integer.parseInt(split[0]),
                            Integer.parseInt(split[1]),
                            Integer.parseInt(split[2])
                    );
                })
                .toList();
    }

    static class Cube {
        int x;
        int y;
        int z;

        public Cube(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return "Cube{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cube cube = (Cube) o;
            return x == cube.x && y == cube.y && z == cube.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }

    static class Side {
        int x1;
        int y1;
        int z1;
        int x2;
        int y2;
        int z2;
        String type;

        public Side(int x1, int y1, int z1, int x2, int y2, int z2) {
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;
            if (x1 < x2 && y1 < y2) {
                type = "Z";
            } else if (x1 < x2 && z1 < z2) {
                type = "Y";
            } else {
                type = "X";
            }
        }

        @Override
        public String toString() {
            return "(%s,%s,%s) -> (%s,%s,%s)".formatted(x1, y1, z1, x2, y2, z2);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Side side = (Side) o;
            return x1 == side.x1 && y1 == side.y1 && z1 == side.z1 && x2 == side.x2 && y2 == side.y2 && z2 == side.z2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x1, y1, z1, x2, y2, z2);
        }
    }
}
