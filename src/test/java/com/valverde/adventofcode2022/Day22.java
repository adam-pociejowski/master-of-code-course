package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Day22 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    /*
      1
    234
      56
     */
    @Test
    void solution2() {
        final List<String> lines = readStringLines("inputTest.txt");
//        final List<String> lines = readStringLines("input22.txt");
        Integer width = lines.subList(0, lines.size() - 2)
                .stream()
                .map(String::length)
                .max(Comparator.naturalOrder())
                .orElseThrow();
        char[][] grid = getGrid(lines, width);
        System.out.printf("width: %s, height: %s\n", width, lines.size() - 2);

        int result = 0;
        System.out.println("SOLUTION 2: " + result);
    }

    @Test
    void solution1() {
        final List<String> lines = readStringLines("input22.txt");
        Integer width = lines.subList(0, lines.size() - 2)
                .stream()
                .map(String::length)
                .max(Comparator.naturalOrder())
                .orElseThrow();
        char[][] grid = getGrid(lines, width);
        Queue<String> commands = getCommands(lines);
        State state = getStartPosition(grid);
        List<String> directions = Arrays.asList("R", "D", "L", "U"); //U (up), R (right), D (down), L (left)
        int iteration = 0;
        while (!commands.isEmpty()) {
            String command = commands.poll();
            System.out.printf("%s: %s - %s\n", ++iteration, command, state);
            if (command.equals("L")) {
                int directionIndex = directions.indexOf(state.orientation) - 1 < 0 ?
                        directions.size() - 1 :
                        directions.indexOf(state.orientation) - 1;
                state.orientation = directions.get(directionIndex);
            } else if (command.equals("R")) {
                int directionIndex = directions.indexOf(state.orientation) + 1 >= directions.size() ?
                        0 :
                        directions.indexOf(state.orientation) + 1;
                state.orientation = directions.get(directionIndex);
            } else {
                int steps = Integer.parseInt(command);
                if (state.orientation.equals("U")) {
                    moveUp(grid, steps, state);
                } else if (state.orientation.equals("R")) {
                    moveRight(grid, steps, state);
                } else if (state.orientation.equals("D")) {
                    moveDown(grid, steps, state);
                } else {
                    moveLeft(grid, steps, state);
                }
            }
        }
        int result = ((state.y + 1) * 1000) + ((state.x + 1) * 4) + directions.indexOf(state.orientation);
        //3590
        System.out.println("SOLUTION 1: " + result);
    }

    private void moveRight(char[][] grid, int steps, State state) {
        boolean canMove = true;
        while (steps > 0 && canMove) {
            if (state.x + 1 >= grid[0].length || grid[state.y][state.x + 1] == ' ') { //end of map
                int newX = 0;
                while (canMove && newX < grid[0].length) {
                    if (grid[state.y][newX] == '.') {
                        state.x = newX;
                        break;
                    } else if (grid[state.y][newX] == '#') {
                        canMove = false;
                    } else {
                        newX++;
                    }
                }
            } else if (grid[state.y][state.x + 1] == '.') { // can move
                state.x++;
            } else { // blockade of road
                canMove = false;
            }
            steps--;
        }
    }

    private void moveLeft(char[][] grid, int steps, State state) {
        boolean canMove = true;
        while (steps > 0 && canMove) {
            if (state.x - 1 < 0 || grid[state.y][state.x - 1] == ' ') { //end of map
                int newX = grid[0].length - 1;
                while (canMove && newX >= 0) {
                    if (grid[state.y][newX] == '.') {
                        state.x = newX;
                        break;
                    } else if (grid[state.y][newX] == '#') {
                        canMove = false;
                    } else {
                        newX--;
                    }
                }
            } else if (grid[state.y][state.x - 1] == '.') { // can move
                state.x--;
            } else { // blockade of road
                canMove = false;
            }
            steps--;
        }
    }

    private void moveUp(char[][] grid, int steps, State state) {
        boolean canMove = true;
        while (steps > 0 && canMove) {
            if (state.y - 1 < 0 || grid[state.y - 1][state.x] == ' ') { //end of map
                int newY = grid.length - 1;
                while (canMove && newY >= 0) {
                    if (grid[newY][state.x] == '.') {
                        state.y = newY;
                        break;
                    } else if (grid[newY][state.x] == '#') {
                        canMove = false;
                    } else {
                        newY--;
                    }
                }
            } else if (grid[state.y - 1][state.x] == '.') { // can move
                state.y--;
            } else { // blockade of road
                canMove = false;
            }
            steps--;
        }
    }

    private void moveDown(char[][] grid, int steps, State state) {
        boolean canMove = true;
        while (steps > 0 && canMove) {
            if (state.y + 1 >= grid.length || grid[state.y + 1][state.x] == ' ') { //end of map
                int newY = 0;
                while (canMove && newY < grid.length) {
                    if (grid[newY][state.x] == '.') {
                        state.y = newY;
                        break;
                    } else if (grid[newY][state.x] == '#') {
                        canMove = false;
                    } else {
                        newY++;
                    }
                }
            } else if (grid[state.y + 1][state.x] == '.') { // can move
                state.y++;
            } else { // blockade of road
                canMove = false;
            }
            steps--;
        }
    }

    private State getStartPosition(char[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == '.') {
                    return new State(x, y);
                }
            }
        }
        throw new RuntimeException();
    }

    private char[][] getGrid(List<String> lines, Integer width) {
        char[][] grid = new char[lines.size() - 2][width];
        for (int y = 0; y < lines.size() - 2; y++) {
            String line = lines.get(y);
            for (int x = 0; x < width; x++) {
                if (x >= line.length()) {
                    grid[y][x] = ' ';
                } else {
                    grid[y][x] = line.charAt(x);
                }
            }
        }
        return grid;
    }

    private Queue<String> getCommands(List<String> lines) {
        Queue<String> commands = new ArrayDeque<>();
        StringBuilder currentCommand = new StringBuilder();
        for (int i = 0; i < lines.get(lines.size() - 1).length(); i++) {
            char c = lines.get(lines.size() - 1).charAt(i);
            if (c == 'R' || c == 'L') {
                if (currentCommand.length() > 0) {
                    commands.add(currentCommand.toString());
                    currentCommand = new StringBuilder();
                }
                commands.add(String.valueOf(c));
            } else {
                currentCommand.append(c);
            }
        }
        if (currentCommand.length() > 0) {
            commands.add(currentCommand.toString());
        }
        return commands;
    }


    static class State {
        int x;
        int y;
        String orientation = "R";

        public State(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(x=" + x +", y=" + y +", orientation='" + orientation+")";
        }
    }
}
