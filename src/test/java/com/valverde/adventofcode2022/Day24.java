package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class Day24 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution2() {
        List<String> lines = readStringLines("input24.txt");
//        List<String> lines = readStringLines("inputTest.txt");
        Set<WindState> windStates = new HashSet<>();
        int maxY = lines.size() - 1;
        int maxX = lines.get(0).length() - 1;
        for (int y = 1; y < maxY; y++) {
            String line = lines.get(y);
            for (int x = 1; x < maxX; x++) {
                if (line.charAt(x) != '.') {
                    windStates.add(new WindState(x, y, line.charAt(x)));
                }
            }
        }
        Set<PlayerState> seenPositions = new HashSet<>();
        Queue<PlayerState> queue = new ArrayDeque<>();
        final List<String> definedGoals = Arrays.asList(
                "%s,%s".formatted(maxX - 1, maxY),
                "%s,%s".formatted(1, 0),
                "%s,%s".formatted(maxX - 1, maxY));
        queue.add(new PlayerState(1, 0, 0, new ArrayList<>(), new ArrayList<>(), definedGoals));
        int currentStep = -1;
        Set<String> windLocations = windLocationsSet(windStates);
        while (!queue.isEmpty()) {
            PlayerState state = queue.poll();
            if (seenPositions.contains(state)) {
                continue;
            }
            if (state.reachedAllGoals()) {
                System.out.printf("FOUND SOLUTION: %s\n", state.toString());
                currentStep = state.step;
                break;
            }
            if (state.step > currentStep) {
                seenPositions = new HashSet<>();
                currentStep = state.step;
                simulateWindStates(windStates, maxX, maxY);
                windLocations = windLocationsSet(windStates);
//                System.out.printf("STEP: %s, QUEUE: %s, WIND_STATES: %s\n", currentStep, queue.size(), windStates.size());
            }
            seenPositions.add(state);

            if (!windLocations.contains("%s,%s".formatted(state.x, state.y))) { //stay in place
                PlayerState newState = new PlayerState(state.x, state.y, state.step + 1, state.history, state.reachedGoals, definedGoals);
                queue.add(newState);
//                validateState(maxX, maxY, newState, windStates, windLocations);
            }
            if ((state.x + 1 < maxX && state.y > 0) && !windLocations.contains("%s,%s".formatted(state.x + 1, state.y))) { //move right
                PlayerState newState = new PlayerState(state.x + 1, state.y, state.step + 1, state.history, state.reachedGoals, definedGoals);
                queue.add(newState);
//                validateState(maxX, maxY, newState, windStates, windLocations);
            }
            if ((state.x - 1 > 0 && (state.y > 0 && state.y < maxY)) && !windLocations.contains("%s,%s".formatted(state.x - 1, state.y))) { //move left
                PlayerState newState = new PlayerState(state.x - 1, state.y, state.step + 1, state.history, state.reachedGoals, definedGoals);
                queue.add(newState);
//                validateState(maxX, maxY, newState, windStates, windLocations);
            }
            if ((state.y - 1 > 0 || (state.y - 1 == 0 && state.x == 1)) && !windLocations.contains("%s,%s".formatted(state.x, state.y - 1))) { //move up
                PlayerState newState = new PlayerState(state.x, state.y - 1, state.step + 1, state.history, state.reachedGoals, definedGoals);
                queue.add(newState);
//                validateState(maxX, maxY, newState, windStates, windLocations);
            }
            if ((state.y + 1 < maxY || (state.y + 1 == maxY && state.x == maxX - 1)) && !windLocations.contains("%s,%s".formatted(state.x, state.y + 1))) { //move down
                PlayerState newState = new PlayerState(state.x, state.y + 1, state.step + 1, state.history, state.reachedGoals, definedGoals);
                queue.add(newState);
//                validateState(maxX, maxY, newState, windStates, windLocations);
            }
        }

        int result = currentStep;
        //689 -> my result
        // > 700
        System.out.println("SOLUTION 2: " + result);
    }

    private void validateState(int maxX, int maxY, PlayerState state, Set<WindState> windStates, Set<String> windLocations) {
        if (state.x < 1 || state.x >= maxX) {
            throw new RuntimeException();
        }
        if (state.y < 0 || state.y > maxY) {
            throw new RuntimeException();
        }
        if (state.y == 0 && state.x != 1) {
            throw new RuntimeException();
        }
        if (state.y == maxY && state.x != maxX - 1) {
            throw new RuntimeException(state.toString());
        }
//        String previousState = state.history.get(state.history.size() - 2);
//        String[] split = previousState.replace("(", "").replace(")", "").split(",");
//        int lastX = Integer.parseInt(split[0]);
//        int lastY = Integer.parseInt(split[1]);
//        int distance = Math.abs(state.x - lastX) + Math.abs(state.y - lastY);
//        if (distance > 1) {
//            throw new RuntimeException("%s".formatted(state));
//        }

        List<WindState> collisions = windStates
                .stream()
                .filter(w -> w.x == state.x && w.y == state.y)
                .toList();
        if (windLocations.contains(("%s,%s".formatted(state.x, state.y)))) {
            throw new RuntimeException("%s: %s".formatted(state, collisions));
        }
        if (!collisions.isEmpty()) {
            throw new RuntimeException("%s: %s".formatted(state, collisions));
        }
    }

    @Test
    void solution1() {
//        List<String> lines = readStringLines("input24.txt");
        List<String> lines = readStringLines("inputTest.txt");
        Set<WindState> windStates = new HashSet<>();
        int maxY = lines.size() - 1;
        int maxX = lines.get(0).length() - 1;
        for (int y = 1; y < maxY; y++) {
            String line = lines.get(y);
            for (int x = 1; x < maxX; x++) {
                if (line.charAt(x) != '.') {
                    windStates.add(new WindState(x, y, line.charAt(x)));
                }
            }
        }
        Set<PlayerState> seenPositions = new HashSet<>();
        Queue<PlayerState> queue = new ArrayDeque<>();
        queue.add(new PlayerState(1, 0, 0, new ArrayList<>()));
        int currentStep = -1;
        Set<String> windLocations = windLocationsSet(windStates);
        while (!queue.isEmpty()) {
            PlayerState state = queue.poll();
            if (seenPositions.contains(state)) {
                continue;
            }
            if (state.x == maxX - 1 && state.y == maxY) {
                System.out.printf("FOUND SOLUTION: %s\n", state.toString());
                break;
            }
            if (state.step > currentStep) {
                seenPositions = new HashSet<>();
                currentStep = state.step;
                simulateWindStates(windStates, maxX, maxY);
                windLocations = windLocationsSet(windStates);
//                System.out.printf("STEP: %s, QUEUE: %s, WIND_STATES: %s\n", currentStep, queue.size(), windStates.size());
//                drawMap(windStates, maxX, maxY);
            }
            seenPositions.add(state);

            if (!windLocations.contains("%s,%s".formatted(state.x, state.y))) { //stay in place
                queue.add(new PlayerState(state.x, state.y, state.step + 1, state.history));
            }
            if ((state.x + 1 < maxX && state.y > 0) && !windLocations.contains("%s,%s".formatted(state.x + 1, state.y))) { //move right
                queue.add(new PlayerState(state.x + 1, state.y, state.step + 1, state.history));
            }
            if ((state.x - 1 > 0 && state.y > 0) && !windLocations.contains("%s,%s".formatted(state.x - 1, state.y))) { //move left
                queue.add(new PlayerState(state.x - 1, state.y, state.step + 1, state.history));
            }
            if ((state.y - 1 > 0 || (state.y - 1 == 0 && state.x == 1)) && !windLocations.contains("%s,%s".formatted(state.x, state.y - 1))) { //move up
                queue.add(new PlayerState(state.x, state.y - 1, state.step + 1, state.history));
            }
            if ((state.y + 1 < maxY || (state.y + 1 == maxY && state.x == maxX - 1)) && !windLocations.contains("%s,%s".formatted(state.x, state.y + 1))) { //move down
                queue.add(new PlayerState(state.x, state.y + 1, state.step + 1, state.history));
            }
        }

        int result = currentStep;
        System.out.println("SOLUTION 1: " + result);
    }

    private void drawMap(Set<WindState> windStates, int maxX, int maxY) {
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                if (x == 0 || x == maxX) {
                    System.out.print("#");
                } else if ((y == 0 && x == 1) || (y == maxY && x == maxX - 1)) {
                    System.out.print(".");
                } else if (y == 0 || y == maxY) {
                    System.out.print("#");
                } else {
                    final int xFinal = x;
                    final int yFinal = y;
                    List<WindState> filteredWindStates = windStates
                            .stream()
                            .filter(w -> w.x == xFinal && w.y == yFinal)
                            .toList();
                    if (filteredWindStates.isEmpty()) {
                        System.out.print(".");
                    } else if (filteredWindStates.size() == 1) {
                        System.out.print(filteredWindStates.get(0).type);
                    } else {
                        System.out.print(filteredWindStates.size());
                    }
                }
            }
            System.out.println();
        }
    }

    private void simulateWindStates(Set<WindState> windStates, int maxX, int maxY) {
        for (WindState state : windStates) {
            state.simulateStep(maxX, maxY);
        }
    }

    private Set<String> windLocationsSet(Set<WindState> windStates) {
        return windStates
                .stream()
                .map(w -> "%s,%s".formatted(w.x, w.y))
                .collect(Collectors.toSet());
    }

    static class PlayerState {
        int x;
        int y;
        int step;
        List<String> history = new ArrayList<>();
        List<String> reachedGoals;

        public PlayerState(int x, int y, int step, List<String> history) {
            this.x = x;
            this.y = y;
            this.step = step;
            this.reachedGoals = new ArrayList<>();
            this.history.addAll(history);
            this.history.add(this.toString());
        }

        public PlayerState(int x, int y, int step, List<String> history, List<String> reachedGoals, List<String> definedGoals) {
            this.x = x;
            this.y = y;
            this.step = step;
            this.reachedGoals = new ArrayList<>(reachedGoals);
            if (definedGoals.get(reachedGoals.size()).equals("%s,%s".formatted(x, y))) {
                this.reachedGoals.add("%s,%s".formatted(x, y));
                if (this.reachedGoals.size() >= 2) {
//                    System.out.printf("%s -> reach goal: %s\n", this.toString(), this.reachedGoals.size());
                }
            }
//            if (this.reachedGoals.size() >= 1) {
//                System.out.println(toString());
//            }
            this.history.addAll(history);
            this.history.add(this.toString());
        }

        public boolean reachedAllGoals() {
            return this.reachedGoals.size() == 3;
        }

        public void printHistory() {
            history
                    .forEach(s -> System.out.println(s));
        }

        @Override
        public String toString() {
            return "(%s,%s), %s".formatted(x, y, step);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlayerState that = (PlayerState) o;
            return x == that.x && y == that.y && step == that.step && reachedGoals.size() == that.reachedGoals.size();
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, step, reachedGoals.size());
        }
    }

    static class WindState {
        int x;
        int y;
        char type;

        public WindState(int x, int y, char type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        public void simulateStep(int maxX, int maxY) {
            if (type == '>') {
                if (x < maxX - 1) {
                    x++;
                } else {
                    x = 1;
                }
            } else if (type == '<') {
                if (x > 1) {
                    x--;
                } else {
                    x = maxX - 1;
                }
            } else if (type == '^') {
                if (y > 1) {
                    y--;
                } else {
                    y = maxY - 1;
                }
            } else if (type == 'v') {
                if (y < maxY - 1) {
                    y++;
                } else {
                    y = 1;
                }
            } else {
                throw new RuntimeException("Type: %s not supported".formatted(type));
            }
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + "), " + type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WindState windState = (WindState) o;
            return x == windState.x && y == windState.y && type == windState.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, type);
        }
    }
}
