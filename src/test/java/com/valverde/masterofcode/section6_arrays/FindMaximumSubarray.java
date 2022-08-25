package com.valverde.masterofcode.section6_arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindMaximumSubarray {

    @Test
    void shouldFindMaxSubarray() {
        int[] array = new int[] {-2,1,-3,4,-1,2,1,-5,4};
        int sum = findMaxSubarray(array);
        assertEquals(6, sum);
    }

    @Test
    void shouldFindMaxSubarray2() {
        int[] array = new int[] {1};
        int sum = findMaxSubarray(array);
        assertEquals(1, sum);
    }

    @Test
    void shouldFindMaxSubarray3() {
        int[] array = new int[] {5,4,-1,7,8};
        int sum = findMaxSubarray(array);
        assertEquals(23, sum);
    }

    @Test
    void shouldFindMaxSubarray4() {
        int[] array = new int[] {1,2,-100,1,4};
        int sum = findMaxSubarray(array);
        assertEquals(5, sum);
    }

    @Test
    void shouldFindMaxSubarray5() {
        int[] array = new int[] {5,7,-2,3};
        int sum = findMaxSubarray(array);
        assertEquals(13, sum);
    }

    private int findMaxSubarray(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int maxSum = array[0];
        int currentSum = array[0];
        for (int i = 1; i < array.length; i++) {
            int currentElement = array[i];
            if (currentSum < 0 && currentElement > currentSum) {
                currentSum = currentElement;
            } else {
                currentSum += currentElement;
            }
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
        }
        return maxSum;
    }
}
