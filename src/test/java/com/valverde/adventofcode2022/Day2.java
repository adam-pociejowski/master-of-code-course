package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
A for Rock, B for Paper, and C for Scissors.
X for Rock, Y for Paper, and Z for Scissors.

(1 for Rock, 2 for Paper, and 3 for Scissors)
(0 if you lost, 3 if the round was a draw, and 6 if you won).
 */

public class Day2 extends AbstractTask {
    Map<String, String> winningMap = new HashMap<>();
    Map<String, String> losingMap = new HashMap<>();
    Map<String, Integer> scoreMap = new HashMap<>();
    Map<String, String> equalMap = new HashMap<>();

    @BeforeEach
    void setup() {
        winningMap.put("A", "Z");
        winningMap.put("B", "X");
        winningMap.put("C", "Y");
        winningMap.put("X", "C");
        winningMap.put("Y", "A");
        winningMap.put("Z", "B");

        losingMap.put("A", "Y");
        losingMap.put("B", "Z");
        losingMap.put("C", "X");

        equalMap.put("A", "X");
        equalMap.put("B", "Y");
        equalMap.put("C", "Z");

        scoreMap.put("X", 1);
        scoreMap.put("Y", 2);
        scoreMap.put("Z", 3);
    }

    @Test
    void solution1() {
        final List<Round> rounds = getRounds();
        long score = 0;
        for (Round round : rounds) {
            if (winningMap.get(round.me).equals(round.opponent)) {
                System.out.println("WIN: "+round.opponent+" "+round.me);
                score += 6;
            } else if (winningMap.get(round.opponent).equals(round.me)) { //LOSE
                System.out.println("LOSE: "+round.opponent+" "+round.me);
                score += 0;
            } else if (round.me.equals(equalMap.get(round.opponent))) { //DRAW
                System.out.println("DRAW: "+round.opponent+" "+round.me);
                score += 3;
            } else {
                System.out.println("BUG: "+round.opponent+" "+round.me);
            }
            score += scoreMap.get(round.me);
        }
        System.out.println("SCORE: "+score);
    }

    @Test
    void solution2() {
        final List<Round> rounds = getRounds();
        long score = 0;
        for (Round round : rounds) {
            String meSelected = "";
            if (round.me.equals("X")) { //lose
                meSelected = winningMap.get(round.opponent);
            } else if (round.me.equals("Y")) { //draw
                meSelected = equalMap.get(round.opponent);
                score += 3;
            } else { //win
                meSelected = losingMap.get(round.opponent);
                score += 6;
            }
            score += scoreMap.get(meSelected);
        }

        System.out.println("SCORE: "+score);
    }

    private List<Round> getRounds() {
        final List<String> lines = readStringLines("input2.txt");
        return lines
                .stream()
                .map(line -> {
                    String[] s = line.split(" ");
                    return new Round(s[0], s[1]);
                })
                .toList();
    }

    static class Round {
        String opponent;
        String me;

        public Round(String opponent, String me) {
            this.opponent = opponent;
            this.me = me;
        }
    }
}
