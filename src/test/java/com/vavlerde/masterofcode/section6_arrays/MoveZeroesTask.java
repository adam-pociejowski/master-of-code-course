package com.vavlerde.masterofcode.section6_arrays;

import com.vavlerde.masterofcode.BaseTest;
import org.junit.jupiter.api.Test;

public class MoveZeroesTask extends BaseTest {

    @Test
    void test1() {
        int[] nums = {0, 1, 0, 3, 12};
        moveZeroes(nums);
        assertIntArraysValues(new int[] { 1, 3, 12, 0, 0}, nums);
    }

    @Test
    void test2() {
        int[] nums = {0};
        moveZeroes(nums);
        assertIntArraysValues(new int[] {0}, nums);
    }

    public void moveZeroes(int[] nums) {
        int foundZeros = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                foundZeros++;
            } else if (foundZeros > 0) {
                int tmp = nums[i];
                nums[i] = 0;
                nums[i - foundZeros] = tmp;
            }
        }
    }
}
