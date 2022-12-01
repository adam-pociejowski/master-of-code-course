package com.valverde.leetcode;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Given a string s, find the length of the longest substring without repeating characters.
 */
public class _3_LongestSubstringWithoutRepeat {

    @Test
    void test1() {
        assertEquals(3, lengthOfLongestSubstring("abcabcbb"));
    }

    @Test
    void test2() {
        assertEquals(1, lengthOfLongestSubstring("bbbbb"));
    }

    @Test
    void test3() {
        assertEquals(3, lengthOfLongestSubstring("pwwkew"));
    }

    @Test
    void test4() {
        assertEquals(1, lengthOfLongestSubstring(" "));
    }

    @Test
    void test5() {
        assertEquals(2, lengthOfLongestSubstring("aab"));
    }

    @Test
    void test6() {
        assertEquals(3, lengthOfLongestSubstring("dvdf"));
    }

    @Test
    void test7() {
        assertEquals(3, lengthOfLongestSubstring("aabaab!bb"));
    }

    public int lengthOfLongestSubstring(String s) {
        Map<String, Integer> substringMap = new HashMap<>();
        int substringStartIndex = 0;
        int maxLength = 0;
        for (int i = 0; i < s.length(); i++) {
            String character = String.valueOf(s.charAt(i));
            Integer index = substringMap.get(character);
            if (index != null && index >= substringStartIndex) {
                int substringLength = i - substringStartIndex;
                if (substringLength > maxLength) {
                    maxLength = substringLength;
                }
                substringStartIndex = index + 1;
            }
            substringMap.put(character, i);
        }
        int lastSubstringLength = s.length() - substringStartIndex;
        if (lastSubstringLength > maxLength) {
            maxLength = lastSubstringLength;
        }
        return maxLength;
    }

    public int lengthOfLongestSubstring2(String s) {
        List<String> substring = new ArrayList<>();
        int maxLength = 0;
        for (int i = 0; i < s.length(); i++) {
            String character = String.valueOf(s.charAt(i));
            int index = substring.indexOf(character);
            if (index > -1) {
                if (substring.size() > maxLength) {
                    maxLength = substring.size();
                }
                substring = substring.subList(index + 1, substring.size());
            }
            substring.add(character);
        }

        if (substring.size() > maxLength) {
            maxLength = substring.size();
        }
        return maxLength;
    }
}
