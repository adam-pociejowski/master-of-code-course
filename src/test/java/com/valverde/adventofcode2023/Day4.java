package com.valverde.adventofcode2023;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

public class Day4 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
        final List<String> lines = readStringLines("input4.txt");
        List<List<Integer>> winning = new ArrayList<>();
        List<List<Integer>> yours = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(":")[1]
                    .substring(1)
                    .split("\\|");

            List<Integer> winingLine = new ArrayList<>();
            for (String s : split[0].split(" ")) {
                if (!Objects.equals(s, "")) {
                    winingLine.add(Integer.parseInt(s));
                }
            }

            List<Integer> yourLine = new ArrayList<>();
            for (String s : split[1].split(" ")) {
                if (!Objects.equals(s, "")) {
                    yourLine.add(Integer.parseInt(s));
                }
            }

            winning.add(winingLine);
            yours.add(yourLine);
        }

        int result = 0;
        for (int cardNum = 0; cardNum < winning.size(); cardNum++) {
            List<Integer> winingLine = winning.get(cardNum);
            List<Integer> yourLine = yours.get(cardNum);
            int lineResult = 0;
            for (Integer your : yourLine) {
                if (winingLine.contains(your)) {
                    if (lineResult == 0) {
                        lineResult = 1;
                    } else {
                        lineResult *= 2;
                    }
                }
            }
            result += lineResult;
        }

        System.out.println("Task1: " + result);

    }

    @Test
    void solution2() {
        final List<String> lines = readStringLines("input4.txt");
        List<List<Integer>> winning = new ArrayList<>();
        List<Card> yours = new ArrayList<>();
        int index = 0;
        for (String line : lines) {
            String[] split = line.split(":")[1]
                    .substring(1)
                    .split("\\|");

            List<Integer> winingLine = new ArrayList<>();
            for (String s : split[0].split(" ")) {
                if (!Objects.equals(s, "")) {
                    winingLine.add(Integer.parseInt(s));
                }
            }

            List<Integer> yourLine = new ArrayList<>();
            for (String s : split[1].split(" ")) {
                if (!Objects.equals(s, "")) {
                    yourLine.add(Integer.parseInt(s));
                }
            }

            winning.add(winingLine);
            yours.add(new Card(yourLine, index++));
        }

        Map<Integer, Integer> cardsCounter = new HashMap<>();
        for (int i = 0; i < yours.size(); i++) {
            cardsCounter.put(i, 1);
        }

        int result = getResult(winning, yours, cardsCounter);
        System.out.println("Task2: " + result);
    }

    private int getResult(List<List<Integer>> winning, List<Card> yours, Map<Integer, Integer> cardsCounter) {
        int result = 0;
        int currentCard = 0;
        while (currentCard < yours.size()) {
            Card card = yours.get(currentCard);
            cardsCounter.put(currentCard, cardsCounter.get(currentCard) - 1);
            result++;

            int matched = numOfWinningCards(card, winning);
            for (int i = 1; i <= matched; i++) {
                cardsCounter.put(currentCard + i, cardsCounter.get(currentCard + i) + 1);
            }

            while (cardsCounter.get(currentCard) == 0) {
                currentCard++;
                if (currentCard >= yours.size()) {
                    return result;
                }
            }
        }
        return result;
    }

    private int numOfWinningCards(Card card, List<List<Integer>> winning) {
        int matching = 0;
        List<Integer> winingLine = winning.get(card.num);
        for (Integer your : card.numbers) {
            if (winingLine.contains(your)) {
                matching++;
            }
        }
        return matching;
    }

    static class Card {
        private final List<Integer> numbers;
        private final int num;

        public Card(List<Integer> numbers, int num) {
            this.numbers = numbers;
            this.num = num;
        }

        @Override
        public String toString() {
            return "Card{" +
                    "numbers=" + numbers +
                    ", num=" + num +
                    '}';
        }
    }
}
