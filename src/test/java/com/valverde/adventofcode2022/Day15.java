package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day15 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
        List<Sensor> sensors = getSensors();
        Set<Beacon> beacons = sensors
                .stream()
                .map(s -> s.closestBeacon)
                .collect(Collectors.toSet());
        int result = getNumberOfPositionsWhereBeaconIsNotPresentInRow(sensors, beacons, 2000000);
        System.out.println("SOLUTION 1: " + result);
    }

    @Test
    void solution2() {
        List<Sensor> sensors = getSensors();
        int maxValue = 4000000;
        int lookingX = -1;
        int lookingY = -1;
        for (int y = 0; y <= maxValue; y++) {
            List<Integer> possiblePositions = getNumberOfPositionsWhereBeaconCanBePresentInRow(
                    sensors,
                    y,
                    0,
                    maxValue);
            if (!possiblePositions.isEmpty()) {
                lookingX = possiblePositions.get(0);
                lookingY = y;
            }
        }
        BigInteger result = BigInteger.valueOf(lookingX).multiply(BigInteger.valueOf(4000000)).add(BigInteger.valueOf(lookingY));
        System.out.println("SOLUTION 2: " + result);
    }

    private List<Integer> getNumberOfPositionsWhereBeaconCanBePresentInRow(List<Sensor> sensors, int y, int minX, int maxX) {
        List<Integer> positions = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            final int currentX = x;
            List<Sensor> sensorsInRange = sensors
                    .stream()
                    .filter(s -> s.inInRange(currentX, y))
                    .toList();
            if (!sensorsInRange.isEmpty()) {
                x = getMaxXInRow(sensorsInRange, y);
            }
            if (sensorsInRange.isEmpty()) {
                positions.add(currentX);
            }
        }
        return positions;
    }

    private int getNumberOfPositionsWhereBeaconIsNotPresentInRow(List<Sensor> sensors, Set<Beacon> beacons, int y) {
        int minX = getMinX(sensors);
        int maxX = getMaxX(sensors);
        int foundPositions = 0;
        for (int x = minX; x <= maxX; x++) {
            final int currentX = x;
            Optional<Beacon> beacon = beacons
                    .stream()
                    .filter(b -> b.x == currentX && b.y == y)
                    .findFirst();
            if (beacon.isEmpty()) {
                List<Sensor> sensorsInRange = sensors
                        .stream()
                        .filter(s -> s.inInRange(currentX, y))
                        .toList();
                if (!sensorsInRange.isEmpty()) {
                    foundPositions++;
                }
            }
        }
        return foundPositions;
    }

    private List<Sensor> getSensors() {
        return readStringLines("input15.txt")
                .stream()
                .map(line -> {
                    String s = line
                            .replace("Sensor at ", "")
                            .replace(" closest beacon is at ", "")
                            .replaceAll(" ", "");
                    String[] split = s.split(":");
                    String[] sensorMatches = Pattern.compile("(-?[0-9]+)")
                            .matcher(split[0])
                            .results()
                            .map(MatchResult::group)
                            .toArray(String[]::new);
                    String[] beaconMatches = Pattern.compile("(-?[0-9]+)")
                            .matcher(split[1])
                            .results()
                            .map(MatchResult::group)
                            .toArray(String[]::new);
                    return new Sensor(
                            Integer.parseInt(sensorMatches[0]),
                            Integer.parseInt(sensorMatches[1]),
                            new Beacon(Integer.parseInt(beaconMatches[0]), Integer.parseInt(beaconMatches[1]))
                    );
                })
                .toList();
    }

    private int getMaxXInRow(List<Sensor> sensors, int y) {
        return sensors
                .stream()
                .map(s -> {
                    int distanceToRow = s.distanceTo(s.x, y);
                    return s.x + (s.beaconDistance - distanceToRow);
                })
                .max(Integer::compareTo)
                .orElseThrow();
    }

    private int getMinX(List<Sensor> sensors) {
        return sensors
                .stream()
                .map(s -> s.x - s.beaconDistance)
                .min(Integer::compareTo)
                .orElseThrow();
    }

    private int getMaxX(List<Sensor> sensors) {
        return sensors
                .stream()
                .map(s -> s.x + s.beaconDistance)
                .max(Integer::compareTo)
                .orElseThrow();
    }

    class Sensor {
        int x;
        int y;
        Beacon closestBeacon;
        int beaconDistance;

        public Sensor(int x, int y, Beacon closestBeacon) {
            this.x = x;
            this.y = y;
            this.closestBeacon = closestBeacon;
            beaconDistance = Math.abs(x - closestBeacon.x) + Math.abs(y - closestBeacon.y);
        }

        int distanceTo(int x, int y) {
            return Math.abs(this.x - x) + Math.abs(this.y - y);
        }

        boolean inInRange(int x, int y) {
            int distance = Math.abs(this.x - x) + Math.abs(this.y - y);
            return distance <= beaconDistance;
        }
    }

    class Beacon {
        int x;
        int y;

        public Beacon(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Beacon beacon = (Beacon) o;
            return x == beacon.x && y == beacon.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
