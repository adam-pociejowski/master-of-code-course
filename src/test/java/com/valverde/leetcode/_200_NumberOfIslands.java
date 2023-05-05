package com.valverde.leetcode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class _200_NumberOfIslands {

    @Test
    void test1() {
        char[][] expected = {
                new char[]{'1', '1', '1', '1', '0'},
                new char[]{'1', '1', '0', '1', '0'},
                new char[]{'1', '1', '0', '0', '0'},
                new char[]{'0', '0', '0', '0', '0'},
        };
        assertEquals(1, numIslands(expected));
    }

    @Test
    void test2() {
        char[][] expected = {
                new char[]{'1', '1', '0', '0', '0'},
                new char[]{'1', '1', '0', '0', '0'},
                new char[]{'0', '0', '1', '0', '0'},
                new char[]{'0', '0', '0', '1', '1'},
        };
        assertEquals(3, numIslands(expected));
    }

    @Test
    void test3() {
        char[][] expected = {
                new char[]{'1', '1', '1'},
                new char[]{'0', '1', '0'},
                new char[]{'1', '1', '1'},
        };
        assertEquals(1, numIslands(expected));
    }

    public int numIslands(char[][] grid) {
        int islandsCount = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == '1' && !visited[y][x]) {
                    islandsCount++;
                    bfs(grid, x, y, visited);
                }
            }
        }
        return islandsCount;
    }

    private void bfs(char[][] grid, int x, int y, boolean[][] visited) {
        visited[y][x] = true;
        if (x + 1 < grid[0].length && !visited[y][x + 1] && grid[y][x + 1] == '1') { // go right
            bfs(grid, x + 1, y, visited);
        }
        if (x - 1 >= 0 && !visited[y][x - 1] && grid[y][x - 1] == '1') { // go left
            bfs(grid, x - 1, y, visited);
        }
        if (y + 1 < grid.length && !visited[y + 1][x] && grid[y + 1][x] == '1') { // go down
            bfs(grid, x, y + 1, visited);
        }
        if (y - 1 >= 0 && !visited[y - 1][x] && grid[y - 1][x] == '1') { // go up
            bfs(grid, x, y - 1, visited);
        }
    }
}
