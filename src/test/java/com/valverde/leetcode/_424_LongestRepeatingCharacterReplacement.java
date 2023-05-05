package com.valverde.leetcode;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
You are given a string s and an integer k. You can choose any character of the string and change it to any other uppercase English character. You can perform this operation at most k times.

Return the length of the longest substring containing the same letter you can get after performing the above operations.
 */
public class _424_LongestRepeatingCharacterReplacement {

    @Test
    void test1() {
        assertEquals(4, characterReplacement("ABAB", 2));
    }

    @Test
    void test2() {
        assertEquals(4, characterReplacement("AABABBA", 1));
    }

    @Test
    void test3() {
        assertEquals(4, characterReplacement("ABBB", 2));
    }


    public int characterReplacement(String s, int k) {
        int longest = 0;
        for (int i = 0; i < s.length(); i++) {
            Result result = getLongestSubstringWithExceptions(s.substring(i), k);
            int longestSubstringWithExceptions = Math.min(result.size + k - result.exceptions, s.length());
            if (longestSubstringWithExceptions > longest) {
                longest = longestSubstringWithExceptions;
            }
        }
        return longest;
    }

    private Result getLongestSubstringWithExceptions(String s, int k) {
        int size = 1;
        int exceptions = 0;
        char character = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            char current = s.charAt(i);
            if (character != current && exceptions == k) {
                return new Result(size, exceptions);
            } else if (character != current) {
                exceptions++;
            }
            size++;
        }
        return new Result(size, exceptions);
    }

    static class Result {
        int size;
        int exceptions;

        public Result(int size, int exceptions) {
            this.size = size;
            this.exceptions = exceptions;
        }
    }
}
