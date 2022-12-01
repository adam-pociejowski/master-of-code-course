package com.valverde.leetcode;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Given an integer array nums, return the length of the longest strictly increasing subsequence.
 */
public class _300_LongestIncreasingSubsequence {

    @Test
    void test1() {
        assertEquals(4, lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
    }

    @Test
    void test2() {
        assertEquals(4, lengthOfLIS(new int[]{0, 1, 0, 3, 2, 3}));
    }

    @Test
    void test3() {
        assertEquals(1, lengthOfLIS(new int[]{7, 7, 7, 7, 7, 7, 7}));
    }

    @Test
    void test4() {
        assertEquals(5, lengthOfLIS(new int[]{0, 1, 0, 4, 5, 2, 3, 5}));
    }

    public int lengthOfLIS(int[] nums) {
        List<Integer> queue = new ArrayList<>();
        for (int num : nums) {
            int index = findIndexOfLowerValue(queue, num);
            if (queue.size() > index + 1) {
                queue.set(index + 1, num);
            } else {
                queue.add(index + 1, num);
            }
        }
        return queue.size();
    }

    private int findIndexOfLowerValue(List<Integer> queue, int num) {
        int index = queue.size() - 1;
        while (index >= 0) {
            int value = queue.get(index);
            if (value < num) {
                return index;
            }
            index--;
        }
        return -1;
    }

    public int lengthOfLISRecursive(int[] nums) {
        final Map<Integer, Integer> cache = new HashMap<>();
        int maxLength = 0;
        for (int i = nums.length -1; i >= 0; i--) {
            int length = lengthOfLISRecursive(nums, i, cache);
            cache.put(i, length);
            maxLength = Math.max(maxLength, length);
        }
        return maxLength;
    }


    public int lengthOfLISRecursive(int[] nums, int index, final Map<Integer, Integer> cache) {
        if (index == nums.length - 1) {
            return 1;
        } else if (cache.containsKey(index)) {
            return cache.get(index);
        }

        int maxLength = 1;
        for (int i = index + 1; i < nums.length; i++) {
            if (nums[index] < nums[i]) {
                maxLength = Math.max(maxLength, lengthOfLISRecursive(nums, i, cache) + 1);
            }
        }
        return maxLength;
    }
}
