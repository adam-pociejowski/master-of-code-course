package com.valverde.masterofcode.section13_sorting;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BaseSortTask {
    protected Sortable sortable;
    protected int comparisonCounter = 0;
    protected int operationsCounter = 0;
    private static int comparisonCounterSum = 0;
    private static int operationsCounterSum = 0;

    @BeforeAll
    static void setupBeforeAll() {
        comparisonCounterSum = 0;
        operationsCounterSum = 0;
    }

    @AfterAll
    static void setupAfterAll() {
        System.out.printf("Comparisons: %s, Operations: %s%n%n", comparisonCounterSum, operationsCounterSum);
    }

    @Test
    public void sortEmptyList() {
        final List<Integer> list = listOf();
        final List<Integer> expected = new ArrayList<>(list);
        sortable.sort(list);
        assertLists(expected, list);
    }

    @Test
    public void sortSortedList() {
        final List<Integer> list = listOf(1, 2, 3, 4, 5, 6, 7, 8);
        final List<Integer> expected = new ArrayList<>(list);
        sortable.sort(list);
        assertLists(expected, list);
    }

    @Test
    public void sortReversedList() {
        final List<Integer> list = listOf(9, 8, 7, 6, 5, 4, 3, 2, 1);
        final List<Integer> expected = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9);
        sortable.sort(list);
        assertLists(expected, list);
    }

    @Test
    public void sortListWithDuplicates() {
        final List<Integer> list = listOf(9, 8, 2, 6, 5, 4, 5, 2, 1);
        final List<Integer> expected = listOf(1, 2, 2, 4, 5, 5, 6, 8, 9);
        sortable.sort(list);
        assertLists(expected, list);
    }

    @Test
    public void sortRandomList() {
        final List<Integer> list = listOf(9, 95, 5, 10, 45, 8, 37, 62, 84, 29);
        final List<Integer> expected = listOf(5, 8, 9, 10, 29, 37, 45, 62, 84, 95);
        sortable.sort(list);
        assertLists(expected, list);
    }

    private List<Integer> listOf(final Integer ...values) {
        return new ArrayList<>(Arrays.asList(values));
    }

    private void assertLists(final List<Integer> expected, final List<Integer> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i), "index: %s, excepted: %s, actual: %s".formatted(i, expected.get(i), actual.get(i)));
        }
        final String invokingMethodName = StackWalker
                .getInstance()
                .walk(frames -> frames
                        .toList()
                        .stream()
                        .map(StackWalker.StackFrame::getMethodName))
                .toList()
                .get(1);
        comparisonCounterSum += comparisonCounter;
        operationsCounterSum += operationsCounter;
        System.out.printf("%s:%s - comparisons: %s, operations: %s%n", this.getClass().getSimpleName(), invokingMethodName, comparisonCounter, operationsCounter);
        comparisonCounter = 0;
        operationsCounter = 0;
    }
}

interface Sortable {
    void sort(List<Integer> list);
}
