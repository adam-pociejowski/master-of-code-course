package com.valverde.masterofcode;

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
}
