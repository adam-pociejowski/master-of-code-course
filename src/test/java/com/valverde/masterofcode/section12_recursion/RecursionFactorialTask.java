package com.valverde.masterofcode.section12_recursion;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecursionFactorialTask {

    @Test
    public void testFactorial() {
        assertEquals(1, factorial(1));
        assertEquals(2, factorial(2));
        assertEquals(6, factorial(3));
        assertEquals(24, factorial(4));
        assertEquals(120, factorial(5));
    }

    int factorial(final int number) {
        if (number == 1) {
            return 1;
        }
        return number * factorial(number - 1);
    }
}
