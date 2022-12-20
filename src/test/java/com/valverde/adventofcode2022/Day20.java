package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Day20 extends AbstractTask {

    @BeforeEach
    void setup() { }

    @Test
    void solution2() {
        final List<Integer> numbers = readStringLines("input20.txt")
                .stream()
                .map(Integer::parseInt)
                .toList();
        List<EncryptedNumber> encryptedNumbers = new ArrayList<>();
        List<EncryptedNumber> originalNumbers = new ArrayList<>();
        long decryptionKey = 811589153;
        for (int i = 0; i < numbers.size(); i++) {
            EncryptedNumber encryptedNumber = new EncryptedNumber(numbers.get(i) * decryptionKey, i);
            EncryptedNumber originalNumber = new EncryptedNumber(numbers.get(i), i, encryptedNumber);
            encryptedNumbers.add(encryptedNumber);
            originalNumbers.add(originalNumber);
        }
        for (int mixIteration = 0; mixIteration < 10; mixIteration++) {
            Queue<EncryptedNumber> queue = new ArrayDeque<>(originalNumbers);
            int i = 0;
            while (!queue.isEmpty()) {
                EncryptedNumber originalNumber = queue.poll();
                EncryptedNumber encryptedNumber = originalNumber.encryptedNumber;
                if (encryptedNumber.number != 0) {
                    long indexBeforeMoving = encryptedNumbers.indexOf(encryptedNumber);
                    encryptedNumbers.remove(encryptedNumber);
                    long indexAfterMoving = indexBeforeMoving + encryptedNumber.number > 0 ?
                            (indexBeforeMoving + encryptedNumber.number) % encryptedNumbers.size() :
                            encryptedNumber.number < 0 ?
                                    ((indexBeforeMoving + encryptedNumber.number) % encryptedNumbers.size()) + encryptedNumbers.size() :
                                    encryptedNumbers.size();
                    encryptedNumbers.add((int)indexAfterMoving, encryptedNumber);
                }
            }
        }
        EncryptedNumber zeroNumber = encryptedNumbers
                .stream()
                .filter(n -> n.number == 0)
                .findFirst()
                .orElseThrow();
        int index = encryptedNumbers.indexOf(zeroNumber);
        EncryptedNumber number1 = encryptedNumbers.get((index + 1000) % encryptedNumbers.size());
        EncryptedNumber number2 = encryptedNumbers.get((index + 2000) % encryptedNumbers.size());
        EncryptedNumber number3 = encryptedNumbers.get((index + 3000) % encryptedNumbers.size());
        long result = number1.number + number2.number + number3.number;
        System.out.printf("SOLUTION 2: %s, numbers: [%s, %s, %s]\n", result, number1, number2, number3);
    }

    @Test
    void solution1() {
        final List<Integer> numbers = readStringLines("input20.txt")
                .stream()
                .map(Integer::parseInt)
                .toList();
        List<EncryptedNumber> encryptedNumbers = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            encryptedNumbers.add(new EncryptedNumber(numbers.get(i), i));
        }
        Queue<EncryptedNumber> queue = new ArrayDeque<>(encryptedNumbers);
        while (!queue.isEmpty()) {
            EncryptedNumber encryptedNumber = queue.poll();
            if (encryptedNumber.number != 0) {
                long indexBeforeMoving = encryptedNumbers.indexOf(encryptedNumber);
                encryptedNumbers.remove(encryptedNumber);
                long indexAfterMoving = indexBeforeMoving + encryptedNumber.number > 0 ?
                        (indexBeforeMoving + encryptedNumber.number) % encryptedNumbers.size() :
                        encryptedNumber.number < 0 ?
                                ((indexBeforeMoving + encryptedNumber.number) % encryptedNumbers.size()) + encryptedNumbers.size() :
                                encryptedNumbers.size();
                encryptedNumbers.add((int)indexAfterMoving, encryptedNumber);
            }
        }
        EncryptedNumber zeroNumber = encryptedNumbers
                .stream()
                .filter(n -> n.number == 0)
                .findFirst()
                .orElseThrow();
        int index = encryptedNumbers.indexOf(zeroNumber);
        EncryptedNumber number1 = encryptedNumbers.get((index + 1000) % encryptedNumbers.size());
        EncryptedNumber number2 = encryptedNumbers.get((index + 2000) % encryptedNumbers.size());
        EncryptedNumber number3 = encryptedNumbers.get((index + 3000) % encryptedNumbers.size());
        long result = number1.number + number2.number + number3.number;
        System.out.printf("SOLUTION 1: %s, numbers: [%s, %s, %s]\n", result, number1, number2, number3);
    }

    static class EncryptedNumber {
        long number;
        long startPosition;
        EncryptedNumber encryptedNumber;

        public EncryptedNumber(long number, long startPosition) {
            this.number = number;
            this.startPosition = startPosition;
        }

        public EncryptedNumber(long number, long startPosition, EncryptedNumber encryptedNumber) {
            this.number = number;
            this.startPosition = startPosition;
            this.encryptedNumber = encryptedNumber;
        }

        @Override
        public String toString() {
            return number+"";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EncryptedNumber that = (EncryptedNumber) o;
            return number == that.number && startPosition == that.startPosition;
        }

        @Override
        public int hashCode() {
            return Objects.hash(number, startPosition);
        }
    }
}
