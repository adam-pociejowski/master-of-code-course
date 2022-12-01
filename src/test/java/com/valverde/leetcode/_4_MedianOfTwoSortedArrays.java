package com.valverde.leetcode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
The overall run time complexity should be O(log (m+n)).
 */
public class _4_MedianOfTwoSortedArrays {

    @Test
    void test1() {
        assertEquals(2.0, findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
    }

    @Test
    void test2() {
        assertEquals(2.5, findMedianSortedArrays(new int[]{1, 2}, new int[]{3,4}));
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] merged = new int[nums1.length + nums2.length];
        int index1 = 0;
        int index2 = 0;
        while (index1 < nums1.length || index2 < nums2.length) {
            if (index1 < nums1.length && index2 < nums2.length) {
                if (nums1[index1] > nums2[index2]) {
                    merged[index1 + index2] = nums2[index2];
                    index2++;
                } else {
                    merged[index1 + index2] = nums1[index1];
                    index1++;
                }
            } else if (index1 < nums1.length) {
                merged[index1 + index2] = nums1[index1];
                index1++;
            } else {
                merged[index1 + index2] = nums2[index2];
                index2++;
            }
        }
        return merged.length % 2 == 0 ?
                (merged[merged.length / 2 - 1] + merged[merged.length / 2]) / 2.0 :
                merged[merged.length / 2];
    }
}
