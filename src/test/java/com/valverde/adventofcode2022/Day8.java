package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class Day8 extends AbstractTask {

    @BeforeEach
    void setup() { }

    @Test
    void solution1() {
        int[][] grid = getGrid();
        int result = 0;
        for (int x = 1; x < grid[0].length - 1; x++) {
            for (int y = 1; y < grid.length - 1; y++) {
                if (isVisible(x, y, grid)) {
                    result++;
                }
            }
        }
        result += grid.length * 2;
        result += (grid[0].length - 2) * 2;
        System.out.println("SOLUTION 1: "+result);
    }

    @Test
    void solution2() {
        int[][] grid = getGrid();
        int result = 0;
        for (int x = 0; x < grid[0].length; x++) {
            for (int y = 0; y < grid.length; y++) {
                int score = calcVisibleTreesScore(x, y, grid);
                if (score > result) {
                    result = score;
                }
            }
        }
        System.out.println("SOLUTION 2: "+result);
    }

    private int calcVisibleTreesScore(int x, int y, int[][] grid) {
        int left = lookToLeft(x, y, grid);
        int right = lookToRight(x, y, grid);
        int top = lookToTop(x, y, grid);
        int bottom = lookToBottom(x, y, grid);
        return left * right * top * bottom;
    }

    private int lookToBottom(int x, int y, int[][] grid) {
        int value = grid[x][y];
        int amount = 0;
        for (int i = y - 1; i >= 0; i--) {
            amount++;
            if (grid[x][i] >= value) {
                break;
            }
        }
        return amount;
    }

    private int lookToTop(int x, int y, int[][] grid) {
        int value = grid[x][y];
        int amount = 0;
        for (int i = y + 1; i < grid.length; i++) {
            amount++;
            if (grid[x][i] >= value) {
                break;
            }
        }
        return amount;
    }

    private int lookToRight(int x, int y, int[][] grid) {
        int value = grid[x][y];
        int amount = 0;
        for (int i = x + 1; i < grid[y].length; i++) {
            amount++;
            if (grid[i][y] >= value) {
                break;
            }
        }
        return amount;
    }

    private int lookToLeft(int x, int y, int[][] grid) {
        int value = grid[x][y];
        int amount = 0;
        for (int i = x - 1; i >= 0; i--) {
            amount++;
            if (grid[i][y] >= value) {
                break;
            }
        }
        return amount;
    }

    private boolean isVisible(int x, int y, int[][] grid) {
        if (isVisibleFromLeft(x, y, grid)) {
            return true;
        } else if (isVisibleFromRight(x, y, grid)) {
            return true;
        } else if (isVisibleFromTop(x, y, grid)) {
            return true;
        } else if (isVisibleFromBottom(x, y, grid)) {
            return true;
        }
        return false;
    }

    private boolean isVisibleFromBottom(int x, int y, int[][] grid) {
        int value = grid[x][y];
        for (int i = y + 1; i < grid[x].length; i++) {
            if (grid[x][i] >= value) {
                return false;
            }
        }
        return true;
    }

    private boolean isVisibleFromTop(int x, int y, int[][] grid) {
        int value = grid[x][y];
        for (int i = 0; i < y; i++) {
            if (grid[x][i] >= value) {
                return false;
            }
        }
        return true;
    }

    private boolean isVisibleFromRight(int x, int y, int[][] grid) {
        int value = grid[x][y];
        for (int i = x + 1; i < grid[y].length; i++) {
            if (grid[i][y] >= value) {
                return false;
            }
        }
        return true;
    }

    private boolean isVisibleFromLeft(int x, int y, int[][] grid) {
        int value = grid[x][y];
        for (int i = 0; i < x; i++) {
            if (grid[i][y] >= value) {
                return false;
            }
        }
        return true;
    }

    private int[][] getGrid() {
        final List<String> lines = readStringLines("input8.txt");
        int width = lines.get(0).length();
        int height = lines.size();
        int[][] grid = new int[width][height];
        for (int y = 0; y < height; y++) {
            final String line = lines.get(y);
            int[] gridLine = new int[width];
            for (int x = 0; x < line.length(); x++) {
                gridLine[x] = Integer.parseInt(String.valueOf(line.charAt(x)));
            }
            grid[y] = gridLine;
        }
        return grid;
    }
}
