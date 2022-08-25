package com.valverde.masterofcode.section6_arrays;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContainsDuplicateTask {

    @Test
    void test1() {
        int[] nums = {0, 1, 0, 3, 12};
        boolean b = containsDuplicate(nums);
        assertTrue(b);
    }

    @Test
    void test2() {
        int[] nums = {1,2,3,4};
        boolean b = containsDuplicate(nums);
        assertFalse(b);
    }

    @Test
    void test3() {
        int[] nums = {1,1,1,3,3,4,3,2,4,2};
        boolean b = containsDuplicate(nums);
        assertTrue(b);
    }

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i : nums) {
            if (set.contains(i)) {
                return true;
            }
            set.add(i);
        }
        return false;
    }
}
