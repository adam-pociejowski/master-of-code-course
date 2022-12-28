package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Day25 extends AbstractTask {
    Map<String, BigInteger> valuesMap = Map.of(
            "=", new BigInteger("-2"),
            "-", new BigInteger("-1"),
            "0", new BigInteger("0"),
            "1", new BigInteger("1"),
            "2", new BigInteger("2"));
    Map<BigInteger, String> valuesMapReverse = Map.of(
            new BigInteger("-2"), "=",
            new BigInteger("-1"), "-",
            new BigInteger("0"), "0",
            new BigInteger("1"), "1",
            new BigInteger("2"), "2",
            new BigInteger("3"), "1=",
            new BigInteger("4"), "1-",
            new BigInteger("5"), "10");

    @BeforeEach
    void setup() {}

    @Test
    void solution2() {
        final List<String> lines = readStringLines("inputTest.txt");
        int result = 0;
        System.out.println("SOLUTION 2: " + result);
    }

    @Test
    void solution1() {
//        final List<String> lines = readStringLines("inputTest.txt");
        final List<String> lines = readStringLines("input25.txt");
        BigInteger normalSum = getNormalNumbersSum(lines);
        System.out.println("SOLUTION 1: " + snafuNumber(normalSum));
    }

    private String snafuNumber(BigInteger normalNumber) {
        StringBuilder snafuNumberBuilder = new StringBuilder();
        BigInteger value = normalNumber;
        boolean hasRest = false;
        while (value.compareTo(BigInteger.ZERO) > 0) {
            BigInteger mod = value.mod(BigInteger.valueOf(5));
            value = value.divide(BigInteger.valueOf(5));
            mod = hasRest ? mod.add(BigInteger.ONE) : mod;
            String snafuDigit = valuesMapReverse.get(mod);
            snafuNumberBuilder.append(snafuDigit.charAt(snafuDigit.length() - 1));
            hasRest = snafuDigit.length() != 1;
        }
        if (hasRest) {
            snafuNumberBuilder.append("1");
        }
        return snafuNumberBuilder.reverse().toString();
    }

    private BigInteger getNormalNumbersSum(List<String> lines) {
        List<BigInteger> normalNumbers = new ArrayList<>();
        for (String line : lines) {
            BigInteger sum = BigInteger.ZERO;
            for (int i = 0; i < line.length(); i++) {
                int pos = line.length() - i - 1;
                BigInteger value = valuesMap.get(String.valueOf(line.charAt(i)));
                BigInteger baseValue = getBaseValue(pos);
                sum = sum.add(value.multiply(baseValue));
            }
            normalNumbers.add(sum);
        }
        return normalNumbers
                .stream()
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    BigInteger getBaseValue(int position) {
        return BigInteger.valueOf(5).pow(position);
    }
}
