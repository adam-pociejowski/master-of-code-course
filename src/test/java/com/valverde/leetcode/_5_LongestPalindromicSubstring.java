package com.valverde.leetcode;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Given a string s, return the longest palindromic substring in s.
 */
public class _5_LongestPalindromicSubstring {

    @Test
    void test1() {
        assertThat(longestPalindrome("babad"), Matchers.either(Matchers.is("aba")).or(Matchers.is("bab")));
    }

    @Test
    void test2() {
        assertEquals("bb", longestPalindrome("cbbd"));
    }

    @Test
    void test3() {
        assertEquals("aaaa", longestPalindrome("aaaa"));
    }

    @Test
    void test4() {
        assertEquals("bb", longestPalindrome("bb"));
    }

    @Test
    void test5() {
        assertEquals("tattarrattat", longestPalindrome("tattarrattat"));
    }

    @Test
    void test6() {
        assertEquals("aaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjkkkkkkkkkkllllllllllmmmmmmmmmmnnnnnnnnnnooooooooooppppppppppqqqqqqqqqqrrrrrrrrrrssssssssssttttttttttuuuuuuuuuuvvvvvvvvvvwwwwwwwwwwxxxxxxxxxxyyyyyyyyyyzzzzzzzzzzyyyyyyyyyyxxxxxxxxxxwwwwwwwwwwvvvvvvvvvvuuuuuuuuuuttttttttttssssssssssrrrrrrrrrrqqqqqqqqqqppppppppppoooooooooonnnnnnnnnnmmmmmmmmmmllllllllllkkkkkkkkkkjjjjjjjjjjiiiiiiiiiihhhhhhhhhhggggggggggffffffffffeeeeeeeeeeddddddddddccccccccccbbbbbbbbbbaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjkkkkkkkkkkllllllllllmmmmmmmmmmnnnnnnnnnnooooooooooppppppppppqqqqqqqqqqrrrrrrrrrrssssssssssttttttttttuuuuuuuuuuvvvvvvvvvvwwwwwwwwwwxxxxxxxxxxyyyyyyyyyyzzzzzzzzzzyyyyyyyyyyxxxxxxxxxxwwwwwwwwwwvvvvvvvvvvuuuuuuuuuuttttttttttssssssssssrrrrrrrrrrqqqqqqqqqqppppppppppoooooooooonnnnnnnnnnmmmmmmmmmmllllllllllkkkkkkkkkkjjjjjjjjjjiiiiiiiiiihhhhhhhhhhggggggggggffffffffffeeeeeeeeeeddddddddddccccccccccbbbbbbbbbbaaaa",
                longestPalindrome("aaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjkkkkkkkkkkllllllllllmmmmmmmmmmnnnnnnnnnnooooooooooppppppppppqqqqqqqqqqrrrrrrrrrrssssssssssttttttttttuuuuuuuuuuvvvvvvvvvvwwwwwwwwwwxxxxxxxxxxyyyyyyyyyyzzzzzzzzzzyyyyyyyyyyxxxxxxxxxxwwwwwwwwwwvvvvvvvvvvuuuuuuuuuuttttttttttssssssssssrrrrrrrrrrqqqqqqqqqqppppppppppoooooooooonnnnnnnnnnmmmmmmmmmmllllllllllkkkkkkkkkkjjjjjjjjjjiiiiiiiiiihhhhhhhhhhggggggggggffffffffffeeeeeeeeeeddddddddddccccccccccbbbbbbbbbbaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjkkkkkkkkkkllllllllllmmmmmmmmmmnnnnnnnnnnooooooooooppppppppppqqqqqqqqqqrrrrrrrrrrssssssssssttttttttttuuuuuuuuuuvvvvvvvvvvwwwwwwwwwwxxxxxxxxxxyyyyyyyyyyzzzzzzzzzzyyyyyyyyyyxxxxxxxxxxwwwwwwwwwwvvvvvvvvvvuuuuuuuuuuttttttttttssssssssssrrrrrrrrrrqqqqqqqqqqppppppppppoooooooooonnnnnnnnnnmmmmmmmmmmllllllllllkkkkkkkkkkjjjjjjjjjjiiiiiiiiiihhhhhhhhhhggggggggggffffffffffeeeeeeeeeeddddddddddccccccccccbbbbbbbbbbaaaa"));
    }
    /*
    aaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjkkkkkkkkkkllllllllllmmmmmmmmmmnnnnnnnnnnooooooooooppppppppppqqqqqqqqqqrrrrrrrrrrssssssssssttttttttttuuuuuuuuuuvvvvvvvvvvwwwwwwwwwwxxxxxxxxxxyyyyyyyyyyzzzzzzzzzzyyyyyyyyyyxxxxxxxxxxwwwwwwwwwwvvvvvvvvvvuuuuuuuuuuttttttttttssssssssssrrrrrrrrrrqqqqqqqqqqppppppppppoooooooooonnnnnnnnnnmmmmmmmmmmllllllllllkkkkkkkkkkjjjjjjjjjjiiiiiiiiiihhhhhhhhhhggggggggggffffffffffeeeeeeeeeeddddddddddccccccccccbbbbbbbbbbaaaa
    aaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjkkkkkkkkkkllllllllllmmmmmmmmmmnnnnnnnnnnooooooooooppppppppppqqqqqqqqqqrrrrrrrrrrssssssssssttttttttttuuuuuuuuuuvvvvvvvvvvwwwwwwwwwwxxxxxxxxxxyyyyyyyyyyzzzzzzzzzzyyyyyyyyyyxxxxxxxxxxwwwwwwwwwwvvvvvvvvvvuuuuuuuuuuttttttttttssssssssssrrrrrrrrrrqqqqqqqqqqppppppppppoooooooooonnnnnnnnnnmmmmmmmmmmllllllllllkkkkkkkkkkjjjjjjjjjjiiiiiiiiiihhhhhhhhhhggggggggggffffffffffeeeeeeeeeeddddddddddccccccccccbbbbbbbbbbaaaa
     */

    public String longestPalindrome(String s) {
        String longestPalindrome = "";
        for (int i = 0; i < s.length(); i++) {
            // Even palindrome
            int left = i;
            int right = i;
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                if (right - left + 1 > longestPalindrome.length()) {
                    longestPalindrome = s.substring(left, right + 1);
                }
                right++;
                left--;
            }

            // Odd palindrome
            left = i;
            right = i + 1;
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                if (right - left + 1 > longestPalindrome.length()) {
                    longestPalindrome = s.substring(left, right + 1);
                }
                right++;
                left--;
            }
        }
        return longestPalindrome;
    }

    public String longestPalindromeRecursive(String s) {
        String longestPalindrome = "";
        for (int i = 0; i < s.length(); i++) {
            String palindromeCenter = String.valueOf(s.charAt(i));
            String palindrome = expandPalindrome(s, palindromeCenter, i - 1, i + 1, new HashSet<>(Set.of(palindromeCenter)));
            if (palindrome.length() > longestPalindrome.length()) {
                longestPalindrome = palindrome;
            }
        }
        return longestPalindrome;
    }

    public String expandPalindrome(String s, String palindrome, int leftIndex, int rightIndex, Set<String> characters) {
        if (leftIndex < 0 || rightIndex >= s.length()) {
            return expandOfLastCharacterPalindrome(s, palindrome, leftIndex, rightIndex, characters);
        }
        String left = String.valueOf(s.charAt(leftIndex));
        String right = String.valueOf(s.charAt(rightIndex));
        if (left.equals(right)) {
            if ((leftIndex - 1 >= 0) && (rightIndex + 1 <= s.length())) {
                characters.add(left);
                characters.add(right);
                return expandPalindrome(s, "%s%s%s".formatted(left, palindrome, right), leftIndex - 1, rightIndex + 1, characters);
            } else {
                return "%s%s%s".formatted(left, palindrome, right);
            }
        } else if (characters.size() == 1) {
            String palindromeCharacter = String.valueOf(palindrome.charAt(0));
            if (palindromeCharacter.equals(left)) {
                if (leftIndex - 1 >= 0) {
                    return expandPalindrome(s, "%s%s".formatted(palindromeCharacter, palindrome), leftIndex - 1, rightIndex, characters);
                }
                return "%s%s".formatted(palindromeCharacter, palindrome);
            } else if (palindromeCharacter.equals(right)) {
                if (rightIndex + 1 < s.length()) {
                    return expandPalindrome(s, "%s%s".formatted(palindromeCharacter, palindrome), leftIndex, rightIndex + 1, characters);
                }
                return "%s%s".formatted(palindromeCharacter, palindrome);
            }
        }
        return palindrome;
    }

    private String expandOfLastCharacterPalindrome(String s, String palindrome, int leftIndex, int rightIndex, Set<String> characters) {
        if (characters.size() == 1) {
            String character = null;
            if (leftIndex >= 0) {
                character = String.valueOf(s.charAt(leftIndex));
            } else if (rightIndex < s.length()) {
                character = String.valueOf(s.charAt(rightIndex));
            }

            if (character != null) {
                String palindromeCharacter = String.valueOf(palindrome.charAt(0));
                if (palindromeCharacter.equals(character)) {
                    return "%s%s".formatted(palindrome, character);
                }
            }
        }
        return palindrome;
    }

    public String longestPalindrome2(String s) {
        if (isPalindrome(s)) {
            return s;
        }
        String s1 = longestPalindrome2(s.substring(0, s.length() - 1));
        String s2 = longestPalindrome2(s.substring(1));
        return s1.length() > s2.length() ? s1 : s2;
    }

    private boolean isPalindrome(String s) {
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - 1 -i)) {
                return false;
            }
        }
        return true;
    }
}

/*
Koci??ta brytyjskie niebieskie, miot pa??dziernik 2022
2 000 z??
Prywatne

Nazwa hodowli: Koci Zak??tek
Nazwa stowarzyszenia (zwi??zku): Canis e Catus
Numer REGON stowarzyszenia: 242875930



Witam,
23 pa??dziernika w naszej domowej hodowli kot??w brytyjskich "Koci Zak??tek" przysz??y na ??wiat 4 koci??ta brytyjskie kr??tkow??ose - 3 kotki oraz 1 kocurek. Wszystkie s?? koloru niebieskiego.

Nasze koty traktowane s?? jak cz??onkowie rodziny, wychowywane w domowej, rodzinnej atmosferze - dzi??ki czemu s?? zadbane, towarzyskie i ch??tne do zabawy.

- Rodzice przebadani pod k??tem FIV i FELV (wyniki ujemne)
- Maluchy b??d?? nauczone korzystania z kuwety i drapaka
- Kotki w dniu odbioru b??d?? zachipowane, dwukrotnie zaszczepione i odrobaczone
- Otrzymaj?? ksi????eczk?? zdrowia, rodow??d oraz wyprawk??

Kotki b??d?? gotowe do zmiany domu w drugiej po??owie stycznia. Na dw??ch ostatnich zdj??ciach znajduj?? si?? rodzice koci??t.

Wi??cej zdj???? i informacji zamieszczamy na Facebooku oraz naszej stronie internetowej.

Facebook: Koci Zak??tek - Hodowla Kot??w Brytyjskich
Strona internetowa: kocizakatek.pociejowski.pl/news

Serdecznie zapraszamy do rezerwacji koci??t.

*Zosta??y trzy kotki, kocurek zarezerwowany
 */