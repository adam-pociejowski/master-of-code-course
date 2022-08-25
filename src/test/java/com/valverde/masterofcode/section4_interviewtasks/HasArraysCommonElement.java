package com.valverde.masterofcode.section4_interviewtasks;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HasArraysCommonElement {

    /*
    Write function that checks if 2 lists has any common element
     */
    @Test
    void shouldReturnTrue_whenListsAreEqual() {
        String[] arr1 = {"a", "b", "c"};
        String[] arr2 = {"a", "b", "c"};
        assertTrue(bruteForceSolution(arr1, arr2));
        assertTrue(hashSetSolution(arr1, arr2));
    }

    @Test
    void shouldReturnFalse_whenListsHasNoCommonElements() {
        String[] arr1 = {"a", "b", "c", "c"};
        String[] arr2 = {"d", "e", "f"};
        assertFalse(bruteForceSolution(arr1, arr2));
        assertFalse(hashSetSolution(arr1, arr2));
    }

    @Test
    void shouldReturnTrue_whenListsHasOneCommonElement() {
        String[] arr1 = {"a", "b", "c"};
        String[] arr2 = {"c", "e", "f"};
        assertTrue(bruteForceSolution(arr1, arr2));
        assertTrue(hashSetSolution(arr1, arr2));
    }

    // O(n*m)
    private boolean bruteForceSolution(final String[] arr1, final String[] arr2) {
        for (String e1 : arr1) {
            for (String e2 : arr2) {
                if (e1.equals(e2)) {
                    return true;
                }
            }
        }
        return false;
    }

    // O(n+m)
    private boolean hashSetSolution(final String[] arr1, final String[] arr2) {
        final HashSet<String> arr1Set = new HashSet<>(Arrays.asList(arr1));
        for (String e : arr2) {
            if (arr1Set.contains(e)) {
                return true;
            }
        }
        return false;
    }
}
