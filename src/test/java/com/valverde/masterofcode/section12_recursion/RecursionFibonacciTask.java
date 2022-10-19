package com.valverde.masterofcode.section12_recursion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecursionFibonacciTask {
    static int operations = 0;

    @Test
    public void testFibonacci() {
        assertEquals(0, fibonacci(1));
        assertEquals(1, fibonacci(2));
        assertEquals(1, fibonacci(3));
        assertEquals(2, fibonacci(4));
        assertEquals(3, fibonacci(5));
        assertEquals(5, fibonacci(6));
        assertEquals(8, fibonacci(7));
    }

    @Test
    public void testFibonacciIterative() {
        assertEquals(0, fibonacciIterative(1));
        assertEquals(1, fibonacciIterative(2));
        assertEquals(1, fibonacciIterative(3));
        assertEquals(2, fibonacciIterative(4));
        assertEquals(3, fibonacciIterative(5));
        assertEquals(5, fibonacciIterative(6));
        assertEquals(8, fibonacciIterative(7));
    }

    @Test
    public void testFibonacciIterative2() {
        int n = 20;
        operations = 0;
        fibonacciIterative(n);
        System.out.printf("Fibonacci iterative, operations: %s, for %s%n", operations, n);
        operations = 0;
        fibonacci(n);
        System.out.printf("Fibonacci recursive, operations: %s, for %s%n", operations, n);
    }

    int fibonacci(final int number) {
        if (number <= 2) {
            operations++;
            return number - 1;
        }
        operations++;
        return fibonacci(number - 1) + fibonacci(number - 2);
    }

    int fibonacciIterative(final int number) {
        if (number <= 2) {
            operations++;
            return number - 1;
        }
        var prevOfPrev = 0;
        var prev = 1;
        for (int i = 4; i <= number; i++) {
            operations++;
            var tmp = prev;
            prev = prevOfPrev + tmp;
            prevOfPrev = tmp;
        }
        return prevOfPrev + prev;
    }
}
