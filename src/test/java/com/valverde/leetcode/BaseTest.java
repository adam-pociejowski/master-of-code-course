package com.valverde.leetcode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BaseTest {

    protected  <T> void assertArraysValues(final T[] expected,
                                           final T[] actual) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    protected  void assertIntArraysValues(final int[] expected,
                                          final int[] actual) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    protected List<List<Integer>> toListOfLists(final int[][] from) {
        List<List<Integer>> rows = new ArrayList<>();
        for (int y = 0; y < from.length; y++) {
            List<Integer> row = new ArrayList<>();
            for (int x = 0; x < from[y].length; x++) {
                row.add(from[y][x]);
            }
            rows.add(row);
        }
        return rows;
    }
}
