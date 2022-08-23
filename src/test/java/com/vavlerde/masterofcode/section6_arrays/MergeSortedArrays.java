package com.vavlerde.masterofcode.section6_arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MergeSortedArrays {

    @Test
    void shouldMergeSortedArrays() {
        int[] array1 = new int[] {1, 2, 3};
        int[] array2 = new int[] {4, 5};
        int[] mergedArray = mergeArrays(array1, array2);
        assertEquals(1, mergedArray[0]);
        assertEquals(2, mergedArray[1]);
        assertEquals(3, mergedArray[2]);
        assertEquals(4, mergedArray[3]);
        assertEquals(5, mergedArray[4]);
    }

    @Test
    void shouldMergeSortedArrays2() {
        int[] array1 = new int[] {1, 3, 5};
        int[] array2 = new int[] {2, 3, 4};
        int[] mergedArray = mergeArrays(array1, array2);
        assertEquals(1, mergedArray[0]);
        assertEquals(2, mergedArray[1]);
        assertEquals(3, mergedArray[2]);
        assertEquals(3, mergedArray[3]);
        assertEquals(4, mergedArray[4]);
        assertEquals(5, mergedArray[5]);
    }

    int[] mergeArrays(final int[] array1, final int[] array2) {
        if (array1.length == 0) {
            return array2;
        }
        if (array2.length == 0) {
            return array1;
        }
        int[] merged = new int[array1.length + array2.length];
        int arr1Index = 0;
        int arr2Index = 0;
        while (arr1Index < array1.length && arr2Index < array2.length) {
            if (array1[arr1Index] < array2[arr2Index]) {
                merged[arr1Index + arr2Index] = array1[arr1Index];
                arr1Index++;
            } else {
                merged[arr1Index + arr2Index] = array2[arr2Index];
                arr2Index++;
            }
        }
        if (arr1Index < array1.length) {
            System.arraycopy(array1, arr1Index, merged, arr1Index + arr2Index, array1.length - arr1Index);
        } else if (arr2Index < array2.length) {
            System.arraycopy(array2, arr2Index, merged, arr1Index + arr2Index, array2.length - arr2Index);
        }
        return merged;
    }
}
