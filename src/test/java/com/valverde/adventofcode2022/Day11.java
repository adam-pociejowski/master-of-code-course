package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.*;

public class Day11 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
        final List<Monkey> monkeys = getMonkeys(readStringLines("input11.txt"));
        for (int i = 0; i < 20; i++) {
            for (int monkeyIndex = 0; monkeyIndex < monkeys.size(); monkeyIndex++) {
                Monkey monkey = monkeys.get(monkeyIndex);
                Iterator<BigInteger> itemsIterator = monkey.items.iterator();
                while (itemsIterator.hasNext()) {
                    BigInteger item = itemsIterator.next();
                    monkey.inspected++;
                    BigInteger newItem = makeOperation(monkey, item);
                    itemsIterator.remove();
                    newItem = newItem.divide(BigInteger.valueOf(3));
                    if (newItem.mod(BigInteger.valueOf(monkey.divisible)).equals(BigInteger.ZERO)) {
                        monkeys.get(monkey.successMonkey).items.add(newItem);
                    } else {
                        monkeys.get(monkey.failMonkey).items.add(newItem);
                    }
                }
            }
        }
        List<Long> sorted = monkeys
                .stream()
                .map(m -> m.inspected)
                .sorted(Comparator.reverseOrder())
                .toList();
        long result = sorted.get(0) * sorted.get(1);
        System.out.println("SOLUTION 1: " + result);
    }

    @Test
    void solution2() {
        final List<Monkey> monkeys = getMonkeys(readStringLines("input11.txt"));
        final BigInteger primeFactor = monkeys
                .stream()
                .map(m -> BigInteger.valueOf(m.divisible))
                .reduce(BigInteger.ONE, BigInteger::multiply);
        for (int i = 0; i < 10000; i++) {
            for (Monkey monkey : monkeys) {
                Iterator<BigInteger> itemsIterator = monkey.items.iterator();
                while (itemsIterator.hasNext()) {
                    BigInteger item = itemsIterator.next();
                    monkey.inspected++;
                    BigInteger newItem = makeOperation(monkey, item);
                    newItem = newItem.mod(primeFactor);
                    itemsIterator.remove();
                    if (newItem.mod(BigInteger.valueOf(monkey.divisible)).equals(BigInteger.ZERO)) {
                        monkeys.get(monkey.successMonkey).items.add(newItem);
                    } else {
                        monkeys.get(monkey.failMonkey).items.add(newItem);
                    }
                }
            }
        }
        List<Long> sorted = monkeys
                .stream()
                .map(m -> m.inspected)
                .sorted(Comparator.reverseOrder())
                .toList();
        long result = sorted.get(0) * sorted.get(1);
        System.out.println("SOLUTION 2: " + result);
    }

    private BigInteger makeOperation(Monkey monkey, BigInteger item) {
        BigInteger operationValue = monkey.operationValueString.equals("old") ? item : monkey.operationValue;
        if (monkey.operation.equals("+")) {
            return item.add(operationValue);
        } else {
            return item.multiply(operationValue);
        }
    }

    private List<Monkey> getMonkeys(List<String> lines) {
        List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 7) {
            List<BigInteger> items = new ArrayList<>(
                    Arrays
                            .stream(lines.get(i + 1)
                                    .replace("Starting items:", "")
                                    .replaceAll(" ", "")
                                    .split(","))
                            .map(BigInteger::new)
                            .toList());
            String[] line2 = lines.get(i + 2)
                    .replace("  Operation: new = old ", "")
                    .split(" ");
            int divisible = Integer.parseInt(lines.get(i + 3)
                    .replace("  Test: divisible by ", ""));
            int successMonkey = Integer.parseInt(lines.get(i + 4)
                    .replace("    If true: throw to monkey ", ""));
            int failMonkey = Integer.parseInt(lines.get(i + 5)
                    .replace("    If false: throw to monkey ", ""));
            monkeys.add(new Monkey(
                    items,
                    line2[0],
                    line2[1],
                    divisible,
                    successMonkey,
                    failMonkey
            ));
        }
        return monkeys;
    }

    static class Monkey {
        List<BigInteger> items;
        Long inspected = 0L;
        String operation;
        String operationValueString;
        BigInteger operationValue;
        int divisible;
        int successMonkey;
        int failMonkey;

        public Monkey(List<BigInteger> items, String operation, String operationValue, int divisible, int successMonkey, int failMonkey) {
            this.items = items;
            this.operation = operation;
            this.operationValueString = operationValue;
            if (!operationValueString.equals("old")) {
                this.operationValue = new BigInteger(operationValue);
            }
            this.divisible = divisible;
            this.successMonkey = successMonkey;
            this.failMonkey = failMonkey;
        }
    }
}
