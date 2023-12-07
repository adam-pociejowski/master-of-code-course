package com.valverde.adventofcode2023;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7 extends AbstractTask {

    private Map<String, Integer> cardScoreMap = null;
    private Map<String, Integer> cardScoreMap2 = null;

    @BeforeEach
    void setup() {
        cardScoreMap = new HashMap<>();
        cardScoreMap.put("2", 0);
        cardScoreMap.put("3", 1);
        cardScoreMap.put("4", 2);
        cardScoreMap.put("5", 3);
        cardScoreMap.put("6", 4);
        cardScoreMap.put("7", 5);
        cardScoreMap.put("8", 6);
        cardScoreMap.put("9", 7);
        cardScoreMap.put("T", 8);
        cardScoreMap.put("J", 9);
        cardScoreMap.put("Q", 10);
        cardScoreMap.put("K", 11);
        cardScoreMap.put("A", 12);

        cardScoreMap2 = new HashMap<>();
        cardScoreMap2.put("J", 0);
        cardScoreMap2.put("2", 1);
        cardScoreMap2.put("3", 2);
        cardScoreMap2.put("4", 3);
        cardScoreMap2.put("5", 4);
        cardScoreMap2.put("6", 5);
        cardScoreMap2.put("7", 6);
        cardScoreMap2.put("8", 7);
        cardScoreMap2.put("9", 8);
        cardScoreMap2.put("T", 9);
        cardScoreMap2.put("Q", 10);
        cardScoreMap2.put("K", 11);
        cardScoreMap2.put("A", 12);
    }

    @Test
    void solution1() {
        final List<String> lines = readStringLines("input7.txt");
        List<Row> rows = lines
                .stream()
                .map(l -> {
                    String[] s = l.split(" ");
                    List<String> cards = stringToCharactersList(s[0]);
                    Long bid = Long.valueOf(s[1]);
                    return new Row(cards, bid);
                })
                .sorted((Row r1, Row r2) -> {
                    int r1Score = handScore(r1.cardsMap);
                    int r2Score = handScore(r2.cardsMap);
                    if (r1Score > r2Score) {
                        return 1;
                    } else if (r1Score < r2Score) {
                        return -1;
                    }
                    for (int i = 0; i < 5; i++) {
                        if (cardScoreMap.get(r1.cards.get(i)) > cardScoreMap.get(r2.cards.get(i))) {
                            return 1;
                        } else if (cardScoreMap.get(r1.cards.get(i)) < cardScoreMap.get(r2.cards.get(i))) {
                            return -1;
                        }
                    }
                    return 0;
                })
                .toList();

        int result = 0;
        int rank = 1;
        for (Row row : rows) {
            result += row.bid * rank++;
        }
        System.out.println("Task1: "+result);
    }

    private int handScore( Map<String, Integer> map) {
        int maxCount = 0;
        for (int i : map.values()) {
            if (i > maxCount) {
                maxCount = i;
            }
        }

        if (maxCount == 5) {
            return 7;
        } else if (maxCount == 4) {
            return 6;
        } else if (maxCount == 3 && map.containsValue(2)) {
            return 5;
        } else if (maxCount == 3) {
            return 4;
        } else if (hasTwoPairs(map)) {
            return 3;
        } else if (maxCount == 2) {
            return 2;
        } else if (map.size() == 5) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean hasTwoPairs(Map<String, Integer> map) {
        int pairs = 0;
        for (int i : map.values()) {
            if (i == 2) {
                pairs++;
            }
        }
        return pairs == 2;
    }

    private Map<String, Integer> toCardsCountMap(List<String> cards) {
        Map<String, Integer> map = new HashMap<>();
        for (String s : cards) {
            if (!map.containsKey(s)) {
                map.put(s, 1);
            } else {
                map.put(s, map.get(s) + 1);
            }
        }
        return map;
    }

    @Test
    void solution2() {
        final List<String> lines = readStringLines("input7.txt");

        List<Row> rows = lines
                .stream()
                .map(l -> {
                    String[] s = l.split(" ");
                    List<String> cards = stringToCharactersList(s[0]);
                    Long bid = Long.valueOf(s[1]);
                    return new Row(cards, bid);
                })
                .sorted((Row r1, Row r2) -> {
                    int r1Score = handScore2(r1.cardsMap);
                    int r2Score = handScore2(r2.cardsMap);
                    if (r1Score > r2Score) {
                        return 1;
                    } else if (r1Score < r2Score) {
                        return -1;
                    }
                    for (int i = 0; i < 5; i++) {
                        if (cardScoreMap2.get(r1.cards.get(i)) > cardScoreMap2.get(r2.cards.get(i))) {
                            return 1;
                        } else if (cardScoreMap2.get(r1.cards.get(i)) < cardScoreMap2.get(r2.cards.get(i))) {
                            return -1;
                        }
                    }
                    return 0;
                })
                .toList();

        int result = 0;
        int rank = 1;
        for (Row row : rows) {
            result += row.bid * rank++;
        }
        System.out.println("Task2: "+result);
    }

    private int handScore2( Map<String, Integer> map) {
        int maxOtherThanJokerCount = 0;
        int jokerCount = map.getOrDefault("J", 0);
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            if (e.getValue() > maxOtherThanJokerCount && !e.getKey().equals("J")) {
                maxOtherThanJokerCount = e.getValue();
            }
        }
        int maxCount = jokerCount + maxOtherThanJokerCount;
        if (maxCount == 5) {
            return 7;
        } else if (maxCount == 4) {
            return 6;
        } else if (canBeFull(map)) {
            return 5;
        } else if (maxCount == 3) {
            return 4;
        } else if (hasTwoPairs(map)) {
            return 3;
        } else if (maxCount == 2) {
            return 2;
        } else if (map.size() == 5) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean canBeFull(Map<String, Integer> cardsMap) {
        int jokerCount = cardsMap.getOrDefault("J", 0);
        if (jokerCount == 0) {
            return cardsMap.containsValue(3) && cardsMap.containsValue(2);
        } else if (jokerCount == 1) {
            return hasTwoPairs(cardsMap) || (cardsMap.containsValue(3) && cardsMap.containsValue(1));
        }
        return false;
    }

    class Row {
        private List<String> cards;
        private Long bid;
        private Map<String, Integer> cardsMap;

        public Row(List<String> cards, Long bid) {
            this.cardsMap = toCardsCountMap(cards);
            this.cards = cards;
            this.bid = bid;
        }

        @Override
        public String toString() {
            return "Row{" +
                    "cards=" + cards +
                    '}';
        }
    }
}
