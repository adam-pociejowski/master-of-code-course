package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

public class Day12 extends AbstractTask {

    @BeforeEach
    void setup() {}

    @Test
    void solution1() {
        final List<String> lines = readStringLines("input12.txt");
        HeightMap heightMap = getHeightMap(lines);
        Map<Point, Integer> distances = bfsForDestination(heightMap);
        int result = distances.get(heightMap.destination);
        System.out.println("SOLUTION 1: "+result);
    }

    @Test
    void solution2() {
        final List<String> lines = readStringLines("input12.txt");
        HeightMap heightMap = getHeightMap(lines);
        List<Point> startingPoints = getAllStartingPoints(heightMap.map);
        Integer result = startingPoints
                .stream()
                .map(s -> HeightMap.fromHeightMap(heightMap, s))
                .map(h -> {
                    Map<Point, Integer> distancesMap = bfsForDestination(h);
                    return distancesMap.get(h.destination);
                })
                .filter(Objects::nonNull)
                .min(Integer::compare)
                .orElseThrow();
        System.out.println("SOLUTION 2: "+result);
    }

    private List<Point> getAllStartingPoints(int[][] heightMap) {
        List<Point> startingPoints = new ArrayList<>();
        for (int x = 0; x < heightMap[0].length; x++) {
            for (int y = 0; y < heightMap.length; y++) {
                if (heightMap[y][x] == 'a') {
                    startingPoints.add(new Point(x, y));
                }
            }
        }
        return startingPoints;
    }

    Map<Point, Integer> bfsForDestination(HeightMap heightMap) {
        final Set<Point> visited = new HashSet<>();
        final Queue<Point> queue = new ArrayDeque<>();
        final Map<Point, Integer> distances = new HashMap<>();
        distances.put(heightMap.currentPosition, 0);
        queue.add(heightMap.currentPosition);
        while (!queue.isEmpty()) {
            final Point point = queue.poll();
            visited.add(point);
            final List<Point> neighbors = getUnvisitedNeighbors(heightMap, point)
                    .stream()
                    .filter(n -> !visited.contains(n))
                    .filter(n -> !queue.contains(n))
                    .filter(n -> {
                        int neighborHeight = heightMap.map[n.y][n.x];
                        int pointHeight = heightMap.map[point.y][point.x];
                        return pointHeight + 1 >= neighborHeight;
                    })
                    .toList();
            final Integer currentDistance = distances.get(point);
            neighbors.forEach(n -> {
                Integer distance = distances.get(n);
                if (distance == null || distance > currentDistance + 1) {
                    distances.put(n, currentDistance + 1);
                }
                queue.add(n);
            });
        }
        return distances;
    }

    private List<Point> getUnvisitedNeighbors(HeightMap heightMap, Point point) {
        ArrayList<Point> neighbours = new ArrayList<>();
        if (point.x > 0) { //LEFT
            neighbours.add(new Point(point.x - 1, point.y));
        }
        if (point.x < heightMap.map[0].length - 1) { //RIGHT
            neighbours.add(new Point(point.x + 1, point.y));
        }
        if (point.y > 0) { //TOP
            neighbours.add(new Point(point.x, point.y - 1));
        }
        if (point.y < heightMap.map.length - 1) { //BOTTOM
            neighbours.add(new Point(point.x, point.y + 1));
        }
        return neighbours;
    }

    private HeightMap getHeightMap(List<String> lines) {
        int width = lines.get(0).length();
        int height = lines.size();
        int[][] heightMap = new int[height][width];
        Point currentPosition = null;
        Point destination = null;
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                heightMap[y][x] = c;
                if (c == 'S') {
                    heightMap[y][x] = 'a';
                    currentPosition = new Point(x, y);
                } else if (c == 'E') {
                    heightMap[y][x] = 'z' + 1;
                    destination = new Point(x, y);
                }
            }
        }
        return new HeightMap(heightMap, currentPosition, destination);
    }

    static class HeightMap {
        int[][] map;
        Point currentPosition;
        Point destination;

        public static HeightMap fromHeightMap(HeightMap heightMap, Point currentPosition) {
            return new HeightMap(heightMap.map, currentPosition, heightMap.destination);
        }

        public HeightMap(int[][] map, Point currentPosition, Point destination) {
            this.map = map;
            this.currentPosition = currentPosition;
            this.destination = destination;
        }
    }

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
