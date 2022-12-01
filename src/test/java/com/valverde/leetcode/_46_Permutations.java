package com.valverde.leetcode;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.
 */
public class _46_Permutations {

    @Test
    void test1() {
        final List<List<Integer>> permutations = Arrays.asList(
                Arrays.asList(0, 1),
                Arrays.asList(1, 0)
        );
        assertPermutations(permutations, permute(new int[]{0, 1}));
    }

    @Test
    void test2() {
        final List<List<Integer>> permutations = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(1, 3, 2),
                Arrays.asList(2, 1, 3),
                Arrays.asList(2, 3, 1),
                Arrays.asList(3, 1, 2),
                Arrays.asList(3, 2, 1)
        );
        assertPermutations(permutations, permute(new int[]{1, 2, 3}));
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length == 1) {
            List<List<Integer>> list = new ArrayList<>();
            List<Integer> perm = new ArrayList<>();
            perm.add(nums[0]);
            list.add(perm);
            return list;
        }
        for (int i = 0; i < nums.length; i++) {
            int[] subArray = new int[nums.length - 1];
            System.arraycopy(nums, 0, subArray, 0, i);
            System.arraycopy(nums, i + 1, subArray, i,  nums.length - i - 1);
            List<List<Integer>> permutations = permute(subArray);
            for (List<Integer> perm : permutations) {
                perm.add(nums[i]);
            }
            result.addAll(permutations);
        }
        return result;
    }

    private void assertPermutations(final List<List<Integer>> expected, final List<List<Integer>> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            List<Integer> expectedPermutation = expected.get(i);
            actual
                    .stream()
                    .filter(a -> a.equals(expectedPermutation))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("Not found %s".formatted(expectedPermutation)));
        }
    }
}
