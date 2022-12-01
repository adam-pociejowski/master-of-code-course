package com.valverde.leetcode;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Given an integer array nums, find a subarray that has the largest product, and return the product.
The test cases are generated so that the answer will fit in a 32-bit integer.
 */
public class _152_MaximumProductSubArray {

    @Test
    void test1() {
        assertEquals(6, maxProduct(new int[]{2,3,-2,4}));
    }

    @Test
    void test2() {
        assertEquals(0, maxProduct(new int[]{-2,0,-1}));
    }

    @Test
    void test3() {
        assertEquals(2, maxProduct(new int[]{-2,1,-1}));
    }

    @Test
    void test4() {
        assertEquals(2, maxProduct(new int[]{0, 2}));
    }

    @Test
    void test5() {
        assertEquals(24, maxProduct(new int[]{-2,3,-4}));
    }

    @Test
    void test6() {
        assertEquals(2, maxProduct(new int[]{-5,0,2}));
    }

    @Test
    void test7() {
        assertEquals(24, maxProduct(new int[]{2,-5,-2,-4,3}));
    }

    public int maxProduct(int[] nums) {
        int highestProduct = Arrays
                .stream(nums)
                .max()
                .getAsInt();
        int maxProduct = 1;
        int minProduct = 1;
        for (int num : nums) {
            if (num == 0) {
                maxProduct = 1;
                minProduct = 1;
                if (num > highestProduct) {
                    highestProduct = num;
                }
            } else {
                int newMaxProduct = Math.max(
                        Math.max(maxProduct * num, minProduct * num),
                        num);
                minProduct = Math.min(
                        Math.min(minProduct * num, maxProduct * num),
                        num);
                maxProduct = newMaxProduct;
                highestProduct = Math.max(highestProduct, maxProduct);
            }
        }
        return highestProduct;
    }
}
