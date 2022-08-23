package com.vavlerde.masterofcode.section4_interviewtasks;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExistInArrayPairWithGivenSum {

    /*
    Write function that checks if in array existing pair of given sum
     */
    @Test
    void shouldReturnTrue_whenExistsPairWithGivenSum() {
        int[] values = {2, 4, 5, 7, 8};
        assertTrue(bruteForceSolution(values, 11));
        assertTrue(hashSetSolution(values, 11));
    }

    @Test
    void shouldReturnTrue_whenExistsPairWithGivenSum2() {
        int[] values = {2, 4, 5, 4, 8};
        assertTrue(bruteForceSolution(values, 8));
        assertTrue(hashSetSolution(values, 8));
    }

    @Test
    void shouldReturnFalse_whenNotExistsPairWithGivenSum() {
        int[] values = {2, 4, 5, 1, 8};
        assertFalse(bruteForceSolution(values, 8));
        assertFalse(hashSetSolution(values, 8));
    }

    @Test
    void shouldReturnFalse_whenListHasOnlyOneItem() {
        int[] values = {8};
        assertFalse(bruteForceSolution(values, 8));
        assertFalse(hashSetSolution(values, 8));
    }

    // O(n^2)
    private boolean bruteForceSolution(final int[] values, final int sum) {
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length; j++) {
                if (i > j && values[i] + values[j] == sum) {
                    return true;
                }
            }
        }
        return false;
    }

    // O(n)
    private boolean hashSetSolution(final int[] values, final int sum) {
        HashSet<Integer> alreadyFoundValues = new HashSet<>();
        for (int v : values) {
            if (alreadyFoundValues.contains(sum - v)) {
                return true;
            }
            alreadyFoundValues.add(v);
        }
        return false;
    }

}
