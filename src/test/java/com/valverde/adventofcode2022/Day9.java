package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

public class Day9 extends AbstractTask {

    @BeforeEach
    void setup() { }

    @Test
    void solution1() {
        List<Command> commands = getCommands();
        Set<Location> visitedLocations = new HashSet<>();
        Location headLocation = new Location(0 , 0);
        Location tailLocation = new Location(0 , 0);
        visitedLocations.add(tailLocation.copyOf());
        for (Command command : commands) {
            headLocation = move(headLocation, command);
            if (areNotAdjective(headLocation, tailLocation)) {
                tailLocation = moveTowardsHead(tailLocation, headLocation);
                visitedLocations.add(tailLocation.copyOf());
            }
        }
        int result = visitedLocations.size();
        System.out.println("SOLUTION 1: "+result);
    }

    @Test
    void solution2() {
        List<Command> commands = getCommands();
        Set<Location> visitedLocations = new HashSet<>();
        Location headLocation = new Location(0 , 0);
        Location currentLocation = headLocation;
        for (int i = 0; i < 9; i++) {
            Location location = currentLocation.copy();
            currentLocation.prev = location;
            location.next = currentLocation;
            currentLocation = location;
        }
        currentLocation.tail = true;
        visitedLocations.add(currentLocation.copy());
        for (Command command : commands) {
            headLocation = move(headLocation, command);
            Location following = headLocation;
            Location follower = headLocation.prev;
            while (follower != null) {
                if (areNotAdjective(follower, following)) {
                    follower = moveTowardsHead(follower, following);
                    following.prev = follower;
                    if (follower.tail) {
                        visitedLocations.add(follower.copy());
                    }
                }
                following = follower;
                follower = following.prev;
            }
        }
        int result = visitedLocations.size();
        System.out.println("SOLUTION 2: "+result);
    }

    private List<Command> getCommands() {
        final List<String> lines = readStringLines("input9.txt");
        return lines
                .stream()
                .map(l -> {
                    String[] split = l.split(" ");
                    int count = Integer.parseInt(split[1]);
                    List<Command> cm = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        cm.add(new Command(split[0], count));
                    }
                    return cm;
                })
                .flatMap(Collection::stream)
                .toList();
    }

    private Location moveTowardsHead(Location tailLocation, Location headLocation) {
        if (tailLocation.y == headLocation.y) { //same row
            return move(tailLocation, new Command(tailLocation.x > headLocation.x ? "L" : "R", 1));
        } else if (tailLocation.x == headLocation.x) { //same column
            return move(tailLocation, new Command(tailLocation.y > headLocation.y ? "D" : "U", 1));
        } else { //diagonal
            tailLocation = move(tailLocation, new Command(tailLocation.x > headLocation.x ? "L" : "R", 1));
            tailLocation = move(tailLocation, new Command(tailLocation.y > headLocation.y ? "D" : "U", 1));
        }
        return tailLocation;
    }

    private Location move(Location location, Command command) {
        return switch (command.direction) {
            case "U" -> new Location(location.x, location.y + 1, location.next, location.prev, location.tail);
            case "D" -> new Location(location.x, location.y - 1, location.next, location.prev, location.tail);
            case "L" -> new Location(location.x - 1, location.y, location.next, location.prev, location.tail);
            case "R" -> new Location(location.x + 1, location.y, location.next, location.prev, location.tail);
            default -> throw new IllegalArgumentException();
        };
    }

    private boolean areNotAdjective(Location l1, Location l2) {
        if (Math.abs(l1.x - l2.x) <= 1 && Math.abs(l1.y - l2.y) <= 1) {
            return false;
        }
        return true;
    }

    class Location {
        int x;
        int y;
        Location next;
        Location prev;
        boolean tail;

        public Location(int x, int y, Location next, Location prev, boolean tail) {
            this.x = x;
            this.y = y;
            this.next = next;
            this.prev = prev;
            this.tail = tail;
        }

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Location copy() {
            return new Location(x, y, next, prev, tail);
        }

        public Location copyOf() {
            return new Location(x, y);
        }

        @Override
        public String toString() {
            return "%s,%s".formatted(x, y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Location pair = (Location) o;
            return x == pair.x && y == pair.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    class Command {
        String direction;
        int count;

        public Command(String direction, int count) {
            this.direction = direction;
            this.count = count;
        }
    }
}
