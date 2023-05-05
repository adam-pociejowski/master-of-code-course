package com.valverde.leetcode;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Given an integer numRows, return the first numRows of Pascal's triangle.
In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:
 */
public class _118_PascalsTriangle extends BaseTest {

    @Test
    void test1() {
        int[][] expected = {
                new int[]{1}
        };
        assertEquals(toListOfLists(expected), generate(1));
    }

    @Test
    void test2() {
        int[][] expected = {
                new int[]{1},
                new int[]{1, 1}
        };
        assertEquals(toListOfLists(expected), generate(2));
    }

    @Test
    void test3() {
        int[][] expected = {
                new int[]{1},
                new int[]{1, 1},
                new int[]{1, 2, 1}
        };
        assertEquals(toListOfLists(expected), generate(3));
    }

    @Test
    void test4() {
        int[][] expected = {
                new int[]{1},
                new int[]{1, 1},
                new int[]{1, 2, 1},
                new int[]{1, 3, 3, 1},
                new int[]{1, 4, 6, 4, 1},
        };
        assertEquals(toListOfLists(expected), generate(5));
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> rows = new ArrayList<>();
        rows.add(Collections.singletonList(1));
        if (numRows == 1) {
            return rows;
        }
        rows.add(Arrays.asList(1, 1));
        if (numRows == 2) {
            return rows;
        }
        for (int i = 3; i <= numRows; i++) {
            rows.add(nextRow(rows.get(rows.size() - 1)));
        }
        return rows;
    }

    private List<Integer> nextRow(List<Integer> lastRow) {
        List<Integer> row = new ArrayList<>();
        row.add(1);
        for (int i = 1; i < lastRow.size(); i++) {
            row.add(lastRow.get(i-1) + lastRow.get(i));
        }
        row.add(1);
        return row;
    }
}
