package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Day14 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
        List<EdgePoint> edges = getEdgePoints();
        MaxMin maxMin = getMaxMin(edges);
        char[][] grid = prepareGrid(maxMin);
        int horizontalShift = maxMin.minX;
        addStones(edges, grid, horizontalShift);
        boolean done = false;
        int result = 0;
        while (!done) {
            boolean sandMoving = true;
            int sandX = 500 - horizontalShift;
            int sandY = 0;
            while (sandMoving) {
                if (sandY + 1 >= grid.length) {
                    sandMoving = false;
                    done = true;
                } else if (grid[sandY + 1][sandX] == '.') {
                    sandY++;
                } else if (sandX - 1 < 0) {
                    sandMoving = false;
                    done = true;
                } else if (grid[sandY + 1][sandX - 1] == '.') {
                    sandY++;
                    sandX--;
                } else if (sandX + 1 >= grid[0].length) {
                    sandMoving = false;
                    done = true;
                } else if (grid[sandY + 1][sandX + 1] == '.') {
                    sandY++;
                    sandX++;
                } else {
                    grid[sandY][sandX] = 'o';
                    sandMoving = false;
                    result++;
                }
            }
        }
        System.out.println("SOLUTION 1: "+result);
    }

    @Test
    void solution2() {
        List<EdgePoint> edges = getEdgePoints();
        MaxMin maxMin = getMaxMin(edges);
        char[][] grid = prepareGrid2(maxMin);
        int horizontalShift = 500 - grid[0].length / 2;
        addStones(edges, grid, horizontalShift);
        boolean done = false;
        int result = 0;
        while (!done) {
            boolean sandMoving = true;
            int sandX = 500 - horizontalShift;
            int sandY = 0;
            if (grid[sandY][sandX] != '.') {
                done = true;
                sandMoving = false;
            }
            while (sandMoving) {
                if (sandY + 1 >= grid.length) {
                    sandMoving = false;
                    done = true;
                } else if (grid[sandY + 1][sandX] == '.') {
                    sandY++;
                } else if (sandX - 1 < 0) {
                    sandMoving = false;
                    done = true;
                } else if (grid[sandY + 1][sandX - 1] == '.') {
                    sandY++;
                    sandX--;
                } else if (sandX + 1 >= grid[0].length) {
                    sandMoving = false;
                    done = true;
                } else if (grid[sandY + 1][sandX + 1] == '.') {
                    sandY++;
                    sandX++;
                } else {
                    grid[sandY][sandX] = 'o';
                    sandMoving = false;
                    result++;
                }
            }
        }
        System.out.println("SOLUTION 2: "+result);
    }

    private void printGrid(char[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
    }

    private List<EdgePoint> getEdgePoints() {
        final List<String> lines = readStringLines("input14.txt");
        List<EdgePoint> edges = lines
                .stream()
                .map(line -> {
                    String l = line.replaceAll(" ", "");
                    String[] split = l.split("->");
                    EdgePoint nextPoint = null;
                    ArrayList<EdgePoint> edgePoints = new ArrayList<>();
                    for (int i = split.length - 1; i >= 0; i--) {
                        String[] points = split[i].split(",");
                        EdgePoint edgePoint = new EdgePoint(
                                Integer.parseInt(points[0]),
                                Integer.parseInt(points[1]),
                                nextPoint);
                        edgePoints.add(edgePoint);
                        nextPoint = edgePoint;
                    }
                    return nextPoint;
                })
                .toList();
        return edges;
    }

    private MaxMin getMaxMin(List<EdgePoint> edges) {
        MaxMin maxMin = new MaxMin(0, 1000, 0, 1000);
        for (EdgePoint edgePoint : edges) {
            EdgePoint e = edgePoint;
            while (e != null) {
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
                e = e.next;
            }
        }
        return maxMin;
    }

    private char[][] prepareGrid2(MaxMin maxMin) {
        int width = Math.max(maxMin.maxX - maxMin.minX + 1, 2*(maxMin.maxY + 3));
        char[][] grid = new char[maxMin.maxY + 3][width];
        for (int x = 0; x < grid[0].length; x++) {
            for (int y = 0; y < grid.length; y++) {
                if (y == grid.length -1) {
                    grid[y][x] = '#';
                } else {
                    grid[y][x] = '.';
                }
            }
        }
        return grid;
    }

    private char[][] prepareGrid(MaxMin maxMin) {
        char[][] grid = new char[maxMin.maxY + 1][maxMin.maxX - maxMin.minX + 1];
        for (int x = 0; x < grid[0].length; x++) {
            for (int y = 0; y < grid.length; y++) {
                grid[y][x] = '.';
            }
        }
        return grid;
    }

    private void addStones(List<EdgePoint> edges, char[][] grid, int horizontalShift) {
        for (EdgePoint edgePoint : edges) {
            EdgePoint e = edgePoint;
            while (e.next != null) {
                if (e.x == e.next.x) { //vertical
                    if (e.y > e.next.y) {
                        for (int y = e.y; y >= e.next.y; y--) {
                            grid[y][e.x - horizontalShift] = '#';
                        }
                    } else {
                        for (int y = e.y; y <= e.next.y; y++) {
                            grid[y][e.x - horizontalShift] = '#';
                        }
                    }
                } else { //horizontal
                    if (e.x > e.next.x) {
                        for (int x = e.x; x >= e.next.x; x--) {
                            grid[e.y][x - horizontalShift] = '#';
                        }
                    } else {
                        for (int x = e.x; x <= e.next.x; x++) {
                            grid[e.y][x - horizontalShift] = '#';
                        }
                    }
                }
                e = e.next;
            }
        }
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

    class EdgePoint {
        int x;
        int y;
        EdgePoint next;

        public EdgePoint(int x, int y, EdgePoint next) {
            this.x = x;
            this.y = y;
            this.next = next;
        }
    }
}
