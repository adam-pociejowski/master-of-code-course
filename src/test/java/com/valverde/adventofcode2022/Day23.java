package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Day23 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution2() {
        final List<String> lines = readStringLines("input23.txt");
        char[][] grid = getGrid(lines);
        Set<Point> elvesLocations = getElvesLocations(grid);
        Queue<List<String>> directionsQueue = new ArrayDeque<>(Arrays.asList(
                Arrays.asList("N", "NE", "NW"),
                Arrays.asList("S", "SE", "SW"),
                Arrays.asList("W", "NW", "SW"),
                Arrays.asList("E", "NE", "SE")));
        int result = 0;
        for (int i = 1; i <= 1000; i++) {
            Map<Point, List<Point>> newLocationPropositions = new HashMap<>(); // (newLocation, oldLocations)
            for (Point p : elvesLocations) {
                if (!hasInAnyDirections(elvesLocations, p, Arrays.asList("N", "NE", "NW", "S", "SE", "SW", "E", "W"))) {
                    continue;
                }
                ArrayDeque<List<String>> queue = new ArrayDeque<>(directionsQueue);
                boolean looking = true;
                while (!queue.isEmpty() && looking) {
                    List<String> directions = queue.poll();
                    if (!hasInAnyDirections(elvesLocations, p, directions)) {
                        looking = false;
                        Point newPointProposition = moveInDirection(p, directions.get(0));
                        if (!newLocationPropositions.containsKey(newPointProposition)) {
                            newLocationPropositions.put(newPointProposition, new ArrayList<>(Collections.singletonList(p)));
                        } else {
                            newLocationPropositions.get(newPointProposition).add(p);
                        }
                    }
                }
            }
            int moves = 0;
            for (Map.Entry<Point, List<Point>> entry : newLocationPropositions.entrySet()) {
                if (entry.getValue().size() == 1) {
                    elvesLocations.remove(entry.getValue().get(0));
                    elvesLocations.add(entry.getKey());
                    moves++;
                }
            }
            if (moves == 0) {
                result = i;
                break;
            }
            List<String> first = directionsQueue.poll();
            directionsQueue.add(first);
        }
        System.out.println("SOLUTION 2: " + result);
    }

    @Test
    void solution1() {
        final List<String> lines = readStringLines("input23.txt");
        char[][] grid = getGrid(lines);
        Set<Point> elvesLocations = getElvesLocations(grid);
        Queue<List<String>> directionsQueue = new ArrayDeque<>(Arrays.asList(
                Arrays.asList("N", "NE", "NW"),
                Arrays.asList("S", "SE", "SW"),
                Arrays.asList("W", "NW", "SW"),
                Arrays.asList("E", "NE", "SE")));
        for (int i = 1; i <= 10; i++) {
            Map<Point, List<Point>> newLocationPropositions = new HashMap<>(); // (newLocation, oldLocations)
            for (Point p : elvesLocations) {
                if (!hasInAnyDirections(elvesLocations, p, Arrays.asList("N", "NE", "NW", "S", "SE", "SW", "E", "W"))) {
                    continue;
                }
                ArrayDeque<List<String>> queue = new ArrayDeque<>(directionsQueue);
                boolean looking = true;
                while (!queue.isEmpty() && looking) {
                    List<String> directions = queue.poll();
                    if (!hasInAnyDirections(elvesLocations, p, directions)) {
                        looking = false;
                        Point newPointProposition = moveInDirection(p, directions.get(0));
                        if (!newLocationPropositions.containsKey(newPointProposition)) {
                            newLocationPropositions.put(newPointProposition, new ArrayList<>(Collections.singletonList(p)));
                        } else {
                            newLocationPropositions.get(newPointProposition).add(p);
                        }
                    }
                }
            }
            for (Map.Entry<Point, List<Point>> entry : newLocationPropositions.entrySet()) {
                if (entry.getValue().size() == 1) {
                    elvesLocations.remove(entry.getValue().get(0));
                    elvesLocations.add(entry.getKey());
                }
            }
            List<String> first = directionsQueue.poll();
            directionsQueue.add(first);
        }
        int result = countEmpty(elvesLocations);
        System.out.println("SOLUTION 1: " + result);
    }

    private int countEmpty(Set<Point> points) {
        MaxMin maxMin = getMaxMin(points);
        int width = maxMin.maxX - maxMin.minX + 1;
        int height = maxMin.maxY - maxMin.minY + 1;
        return (width * height) - points.size();
    }

    private void print(Set<Point> points) {
        MaxMin maxMin = getMaxMin(points);
        for (int y = maxMin.minY; y <= maxMin.maxY; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = maxMin.minX; x <= maxMin.maxX; x++) {
                if (points.contains(new Point(x, y))) {
                    sb.append("#");
                } else {
                    sb.append(".");
                }
            }
            System.out.println(sb.toString());
        }
    }

    private MaxMin getMaxMin(Set<Point> points) {
        MaxMin maxMin = new MaxMin(-1000, 1000, -1000, 1000);
        for (Point e : points) {
            if (e.x > maxMin.maxX) {
                maxMin.maxX = e.x;
            }
            if (e.x < maxMin.minX) {
                maxMin.minX = e.x;
            }
            if (e.y > maxMin.maxY) {
                maxMin.maxY = e.y;
            }
            if (e.y < maxMin.minY) {
                maxMin.minY = e.y;
            }
        }
        return maxMin;
    }

    class MaxMin {
        int maxX;
        int minX;
        int maxY;
        int minY;

        public MaxMin(int maxX, int minX, int maxY, int minY) {
            this.maxX = maxX;
            this.minX = minX;
            this.maxY = maxY;
            this.minY = minY;
        }
    }

    private Point moveInDirection(Point p, String direction) {
        return switch (direction) {
            case "N" -> new Point(p.x, p.y - 1);
            case "S" -> new Point(p.x, p.y + 1);
            case "W" -> new Point(p.x - 1, p.y);
            case "E" -> new Point(p.x + 1, p.y);
            default -> throw new RuntimeException();
        };
    }

    private boolean hasInAnyDirections(Set<Point> points, Point p, List<String> directions) {
        for (String direction : directions) {
            if (hasInDirection(points, p, direction)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasInDirection(Set<Point> points, Point p, String direction) {
        return switch (direction) {
            case "N" -> points.contains(new Point(p.x, p.y - 1));
            case "NE" -> points.contains(new Point(p.x + 1, p.y - 1));
            case "NW" -> points.contains(new Point(p.x - 1, p.y - 1));
            case "S" -> points.contains(new Point(p.x, p.y + 1));
            case "SE" -> points.contains(new Point(p.x + 1, p.y + 1));
            case "SW" -> points.contains(new Point(p.x - 1, p.y + 1));
            case "W" -> points.contains(new Point(p.x - 1, p.y));
            case "E" -> points.contains(new Point(p.x + 1, p.y));
            default -> throw new RuntimeException();
        };
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

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    private Set<Point> getElvesLocations(char[][] grid) {
        Set<Point> points = new HashSet<>();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == '#') {
                    points.add(new Point(x, y));
                }
            }
        }
        return points;
    }

    private char[][] getGrid(List<String> lines) {
        char[][] grid = new char[lines.size()][lines.get(0).length()];
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < grid[0].length; x++) {
                grid[y][x] = line.charAt(x);
            }
        }
        return grid;
    }
}
