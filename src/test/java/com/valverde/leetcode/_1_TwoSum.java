package com.valverde.leetcode;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
You may assume that each input would have exactly one solution, and you may not use the same element twice.
You can return the answer in any order.
 */
public class _1_TwoSum {

    @Test
    void test1() {
//        int[] indices = twoSum(new int[]{2, 7, 11, 15}, 9);
        int[] indices = twoSum2(new int[]{2, 7, 11, 15}, 9);
        assertEquals(0, indices[0]);
        assertEquals(1, indices[1]);
    }

    @Test
    void test2() {
        int[] indices = twoSum2(new int[]{3, 2, 4}, 6);
        assertEquals(1, indices[0]);
        assertEquals(2, indices[1]);
    }

    @Test
    void test3() {
        int[] indices = twoSum2(new int[]{3, 3}, 6);
        assertEquals(0, indices[0]);
        assertEquals(1, indices[1]);
    }

    int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> complements = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer complementIndex = complements.get(nums[i]);
            if (complementIndex != null) {
                return new int[]{complementIndex, i};
            }
            complements.put(target - nums[i], i);
        }
        return null;
    }

}
