package com.vavlerde.masterofcode.section6_arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReverseString {

    @Test
    void shouldCreateReversedString() {
        String string = "ABCD";
        String stringReversed = "DCBA";
        String result = reverseString(string);
        assertEquals(stringReversed, result);
    }

    private String reverseString(final String string) {
        StringBuilder reversed = new StringBuilder();
        for (int i = string.length() -1; i >= 0; i--) {
            reversed.append(string.charAt(i));
        }
        return reversed.toString();
    }
}
