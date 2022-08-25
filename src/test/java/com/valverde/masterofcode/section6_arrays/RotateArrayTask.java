package com.valverde.masterofcode.section6_arrays;

import com.valverde.masterofcode.BaseTest;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RotateArrayTask extends BaseTest {

    @Test
    void test1() {
        int[] nums = {1,2,3,4,5,6,7};
        rotate2(nums, 3);
        assertIntArraysValues(new int[] {5,6,7,1,2,3,4}, nums);
    }

    @Test
    void test2() {
        int[] nums = {-1,-100,3,99};
        rotate(nums, 2);
        assertIntArraysValues(new int[] {3,99,-1,-100}, nums);
    }

    public void rotate2(int[] nums, int k) {
        k = k % nums.length;
        Map<Integer,Integer> indexesMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            indexesMap.put((i + k) % nums.length, nums[i]);
            if (indexesMap.containsKey(i)) {
                nums[i] = indexesMap.get(i);
            } else {
                nums[i] = nums[getIndex(i - k, nums.length)];
            }
        }
    }

    private int getIndex(int i, int length) {
        if (i >= 0) {
            return i;
        }
        return length + i;
    }
    public void rotate(int[] nums, int k) {
        Set<Integer> indexesMet = new HashSet<>();
        int curIndex = 0;
        int savedValue = nums[curIndex];
        int counter = 0;
        while (indexesMet.size() < nums.length) {
            int indexToSwap = (curIndex + k) % nums.length;
            int tmp = savedValue;
            savedValue = nums[indexToSwap];
            nums[indexToSwap] = tmp;
            indexesMet.add(curIndex);
            curIndex = indexToSwap;
            counter++;
            if (indexesMet.contains(curIndex)) {
                curIndex = (curIndex + 1) % nums.length;
                savedValue = nums[curIndex];
            }
        }
        System.out.println(counter);
    }
}
