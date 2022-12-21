package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.util.*;

public class Day21 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution2() {
        Map<String, BigInteger> answeredMonkeys = new HashMap<>();
        Queue<MonkeyOperation> notAnswered = new ArrayDeque<>();
        readStringLines("input21.txt")
                .forEach(l -> {
                    String[] split = l.split(": ");
                    String name = split[0];
                    String[] operations = split[1].split(" ");
                    if (operations.length == 1) {
                        answeredMonkeys.put(name, new BigInteger(operations[0]));
                    } else {
                        notAnswered.add(new MonkeyOperation(name, operations[0], operations[1], operations[2]));
                    }
                });
        BigInteger result = null;
        for (BigInteger i = new BigInteger("3876027138194"); i.compareTo(new BigInteger("3876027598195")) < 0; i = i.add(BigInteger.ONE)) {
            boolean correctNumber = findCorrectNumber(i, answeredMonkeys, notAnswered);
            if (correctNumber) {
                result = i;
                break;
            }
        }
        System.out.println("SOLUTION 2: " + result);
        //3876027196185
    }

    private boolean findCorrectNumber(BigInteger input, Map<String, BigInteger> answered, Queue<MonkeyOperation> notAnswered) {
        final Queue<MonkeyOperation> notAnsweredQueue = new ArrayDeque<>(notAnswered);
        final Map<String, BigInteger> answeredMonkeys = new HashMap<>(answered);
        answeredMonkeys.put("humn", input);
        int i = 0;
        while (!notAnsweredQueue.isEmpty()) {
            MonkeyOperation o = notAnsweredQueue.poll();
            if (o.name.equals("root") && (answeredMonkeys.containsKey(o.monkey1) && answeredMonkeys.containsKey(o.monkey2))) {
                BigInteger monkey1Number = answeredMonkeys.get(o.monkey1);
                BigInteger monkey2Number = answeredMonkeys.get(o.monkey2);
                System.out.printf("%s: %s == %s\n", input, monkey1Number, monkey2Number);
            }
            if (answeredMonkeys.containsKey(o.monkey1) && answeredMonkeys.containsKey(o.monkey2)) {
                BigInteger monkey1Number = answeredMonkeys.get(o.monkey1);
                BigInteger monkey2Number = answeredMonkeys.get(o.monkey2);
                if (o.name.equals("root")) {
                    return monkey1Number.equals(monkey2Number);
                } else if (o.operation.equals("+")) {
                    answeredMonkeys.put(o.name, monkey1Number.add(monkey2Number));
                } else if (o.operation.equals("-")) {
                    answeredMonkeys.put(o.name, monkey1Number.subtract(monkey2Number));
                } else if (o.operation.equals("*")) {
                    answeredMonkeys.put(o.name, monkey1Number.multiply(monkey2Number));
                } else if (o.operation.equals("/")) {
                    answeredMonkeys.put(o.name, monkey1Number.divide(monkey2Number));
                }
            } else {
                notAnsweredQueue.add(o);
            }
        }
        return false;
    }

    @Test
    void solution1() {
        Map<String, BigInteger> answeredMonkeys = new HashMap<>();
        Queue<MonkeyOperation> notAnswered = new ArrayDeque<>();
        readStringLines("input21.txt")
                .forEach(l -> {
                    String[] split = l.split(": ");
                    String name = split[0];
                    String[] operations = split[1].split(" ");
                    if (operations.length == 1) {
                        answeredMonkeys.put(name, new BigInteger(operations[0]));
                    } else {
                        notAnswered.add(new MonkeyOperation(name, operations[0], operations[1], operations[2]));
                    }
                });
        int i = 0;
        while (!answeredMonkeys.containsKey("root") || !notAnswered.isEmpty()) {
            MonkeyOperation o = notAnswered.poll();
            if (answeredMonkeys.containsKey(o.monkey1) && answeredMonkeys.containsKey(o.monkey2)) {
                BigInteger monkey1Number = answeredMonkeys.get(o.monkey1);
                BigInteger monkey2Number = answeredMonkeys.get(o.monkey2);
                if (o.operation.equals("+")) {
                    answeredMonkeys.put(o.name, monkey1Number.add(monkey2Number));
                } else if (o.operation.equals("-")) {
                    answeredMonkeys.put(o.name, monkey1Number.subtract(monkey2Number));
                } else if (o.operation.equals("*")) {
                    answeredMonkeys.put(o.name, monkey1Number.multiply(monkey2Number));
                } else if (o.operation.equals("/")) {
                    answeredMonkeys.put(o.name, monkey1Number.divide(monkey2Number));
                }
            } else {
                notAnswered.add(o);
            }
        }
        BigInteger result = answeredMonkeys.get("root");
        System.out.println("SOLUTION 1: " + result);
        //62386792426088
    }

    static class MonkeyOperation {
        String name;
        String monkey1;
        String operation;
        String monkey2;

        public MonkeyOperation(String name, String monkey1, String operation, String monkey2) {
            this.name = name;
            this.monkey1 = monkey1;
            this.operation = operation;
            this.monkey2 = monkey2;
        }

        @Override
        public String toString() {
            return "%s = %s %s %s".formatted(name, monkey1, operation, monkey2);
        }
    }
}
