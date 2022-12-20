package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Day19 extends AbstractTask {

    @BeforeEach
    void setup() {}

    @Test
    void solution2() {
        List<BluePrint> bluePrints = getBluePrints("input19.txt");
        int result = 1;
        for (BluePrint bluePrint : bluePrints.subList(0, 3)) {
            State best = null;
            final Queue<State> queue = new ArrayDeque<>();
            final Set<State> seen = new HashSet<>();
            queue.add(new State(bluePrint));
            long iterations = 0;
            int depth = 0;
            while (!queue.isEmpty() && iterations < 1000000000) {
                State state = queue.poll();
                if (state.time > 32) {
                    continue;
                }
                if (seen.contains(state)) {
                    continue;
                }
                if (state.time > depth) {
                    depth = state.time;
                    seen.removeIf(next -> next.time < state.time);
                    System.out.println("DEPTH: "+depth+", SEEN: "+seen.size()+", QUEUE: "+queue.size()+", BEST: "+best);
                }
                seen.add(state);
                iterations++;
                if (Objects.isNull(best) || state.openedGeodes > best.openedGeodes) {
                    best = state;
                }
                if (state.openedGeodes + 1 < best.openedGeodes ||
                        (depth > 25 && state.clayRobots < 3)  ||
                        (depth > 25 && state.obsidianRobots < 3)  ||
                        (depth > 25 && state.oreRobots < 2)) { // heuristic to min number of states (out of memory)
                    continue;
                }
                queue.add(state.doNothing());
                if (state.ore >= state.bluePrint.oreRobot.ore) {
                    queue.add(state.buildOreRobot());
                }
                if (state.ore >= state.bluePrint.clayRobot.ore) {
                    queue.add(state.buildClayRobot());
                }
                if (state.ore >= state.bluePrint.obsidianRobot.ore && state.clay >= state.bluePrint.obsidianRobot.clay) {
                    queue.add(state.buildObsidianRobot());
                }
                if (state.ore >= state.bluePrint.geodeRobot.ore && state.obsidian >= state.bluePrint.geodeRobot.obsidian) {
                    queue.add(state.buildGeodeRobot());
                }
            }
            System.out.printf("%s: %s\n", iterations, best);
            result *= best.openedGeodes;
        }
        System.out.println("SOLUTION 2: " + result);
        //3080
    }

    @Test
    void solution1() {
        List<BluePrint> bluePrints = getBluePrints("input19.txt");
        int result = 0;
        for (BluePrint bluePrint : bluePrints) {
            State best = null;
            final Queue<State> queue = new ArrayDeque<>();
            final Set<State> seen = new HashSet<>();
            queue.add(new State(bluePrint));
            long iterations = 0;
            while (!queue.isEmpty() && iterations < 500000000) {
                State state = queue.poll();
                if (state.time > 24) {
                    continue;
                }
                if (seen.contains(state)) {
                    continue;
                }
                if (queue.size() > 20000000) {
                    continue;
                }
                seen.add(state);
                iterations++;
                if (Objects.isNull(best) || state.openedGeodes > best.openedGeodes) {
                    best = state;
                    System.out.printf("%s: %s\n", iterations, state);
                }
                queue.add(state.doNothing());
                if (state.ore >= state.bluePrint.oreRobot.ore) {
                    queue.add(state.buildOreRobot());
                }
                if (state.ore >= state.bluePrint.clayRobot.ore) {
                    queue.add(state.buildClayRobot());
                }
                if (state.ore >= state.bluePrint.obsidianRobot.ore && state.clay >= state.bluePrint.obsidianRobot.clay) {
                    queue.add(state.buildObsidianRobot());
                }
                if (state.ore >= state.bluePrint.geodeRobot.ore && state.obsidian >= state.bluePrint.geodeRobot.obsidian) {
                    queue.add(state.buildGeodeRobot());
                }
            }
            result += (bluePrints.indexOf(bluePrint) + 1) * best.openedGeodes;
        }
        System.out.println("SOLUTION 1: " + result);
        //1147
    }

    private List<BluePrint> getBluePrints(String input) {
        return readStringLines(input)
                .stream()
                .map(l -> {
                    String[] split = l
                            .replaceAll("Blueprint [0-9]+: ", "")
                            .replaceAll("Each [a-z]+ robot costs ", "")
                            .replaceAll(" ", "")
                            .replaceAll("and", ",")
                            .replaceAll("ore|clay|obsidian", "")
                            .split("\\.");
                    String[] obsidianRobotSplit = split[2].split(",");
                    String[] geodeRobotSplit = split[3].split(",");
                    return new BluePrint(
                            new OreRobot(Integer.parseInt(split[0])),
                            new ClayRobot(Integer.parseInt(split[1])),
                            new ObsidianRobot(Integer.parseInt(obsidianRobotSplit[0]), Integer.parseInt(obsidianRobotSplit[1])),
                            new GeodeRobot(Integer.parseInt(geodeRobotSplit[0]), Integer.parseInt(geodeRobotSplit[1])));
                })
                .toList();
    }

    static class State {
        int ore = 0;
        int clay = 0;
        int obsidian = 0;
        int openedGeodes = 0;
        int oreRobots = 1;
        int clayRobots = 0;
        int obsidianRobots = 0;
        int geodeRobots = 0;
        int time = 0;
        BluePrint bluePrint;

        public State(BluePrint bluePrint) {
            this.bluePrint = bluePrint;
        }

        public State(int ore, int clay, int obsidian, int openedGeodes, int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots, int time, BluePrint bluePrint) {
            this.ore = ore;
            this.clay = clay;
            this.obsidian = obsidian;
            this.openedGeodes = openedGeodes;
            this.oreRobots = oreRobots;
            this.clayRobots = clayRobots;
            this.obsidianRobots = obsidianRobots;
            this.geodeRobots = geodeRobots;
            this.time = time;
            this.bluePrint = bluePrint;
        }

        State doNothing() {
            return new State(
                    ore + oreRobots,
                    clay + clayRobots,
                    obsidian + obsidianRobots,
                    openedGeodes + geodeRobots,
                    oreRobots,
                    clayRobots,
                    obsidianRobots,
                    geodeRobots,
                    time + 1,
                    bluePrint);
        }

        State buildOreRobot() {
            return new State(
                    ore - bluePrint.oreRobot.ore + oreRobots,
                    clay + clayRobots,
                    obsidian + obsidianRobots,
                    openedGeodes + geodeRobots,
                    oreRobots + 1,
                    clayRobots,
                    obsidianRobots,
                    geodeRobots,
                    time + 1,
                    bluePrint);

        }

        State buildClayRobot() {
            return new State(
                    ore - bluePrint.clayRobot.ore + oreRobots,
                    clay + clayRobots,
                    obsidian + obsidianRobots,
                    openedGeodes + geodeRobots,
                    oreRobots,
                    clayRobots + 1,
                    obsidianRobots,
                    geodeRobots,
                    time + 1,
                    bluePrint);
        }

        State buildObsidianRobot() {
            return new State(
                    ore - bluePrint.obsidianRobot.ore + oreRobots,
                    clay - bluePrint.obsidianRobot.clay + clayRobots,
                    obsidian + obsidianRobots,
                    openedGeodes + geodeRobots,
                    oreRobots,
                    clayRobots,
                    obsidianRobots + 1,
                    geodeRobots,
                    time + 1,
                    bluePrint);
        }

        State buildGeodeRobot() {
            return new State(
                    ore - bluePrint.geodeRobot.ore + oreRobots,
                    clay + clayRobots,
                    obsidian - bluePrint.geodeRobot.obsidian + obsidianRobots,
                    openedGeodes + geodeRobots,
                    oreRobots,
                    clayRobots,
                    obsidianRobots,
                    geodeRobots + 1,
                    time + 1,
                    bluePrint);
        }

        @Override
        public String toString() {
            return "ore=" + ore +
                    ", clay=" + clay +
                    ", obsidian=" + obsidian +
                    ", openedGeodes=" + openedGeodes +
                    ", oreRobots=" + oreRobots +
                    ", clayRobots=" + clayRobots +
                    ", obsidianRobots=" + obsidianRobots +
                    ", geodeRobots=" + geodeRobots +
                    ", time=" + time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State that = (State) o;
            return ore == that.ore && clay == that.clay && obsidian == that.obsidian && openedGeodes == that.openedGeodes && oreRobots == that.oreRobots && clayRobots == that.clayRobots && obsidianRobots == that.obsidianRobots && geodeRobots == that.geodeRobots;
        }

        @Override
        public int hashCode() {
            return Objects.hash(ore, clay, obsidian, openedGeodes, oreRobots, clayRobots, obsidianRobots, geodeRobots);
        }
    }

    static class BluePrint {
        OreRobot oreRobot;
        ClayRobot clayRobot;
        ObsidianRobot obsidianRobot;
        GeodeRobot geodeRobot;

        public BluePrint(OreRobot oreRobot, ClayRobot clayRobot, ObsidianRobot obsidianRobot, GeodeRobot geodeRobot) {
            this.oreRobot = oreRobot;
            this.clayRobot = clayRobot;
            this.obsidianRobot = obsidianRobot;
            this.geodeRobot = geodeRobot;
        }
    }

    static class OreRobot {
        int ore;

        public OreRobot(int ore) {
            this.ore = ore;
        }
    }

    static class ClayRobot {
        int ore;

        public ClayRobot(int ore) {
            this.ore = ore;
        }
    }

    static class ObsidianRobot {
        int ore;
        int clay;

        public ObsidianRobot(int ore, int clay) {
            this.ore = ore;
            this.clay = clay;
        }
    }

    static class GeodeRobot {
        int ore;
        int obsidian;

        public GeodeRobot(int ore, int obsidian) {
            this.ore = ore;
            this.obsidian = obsidian;
        }
    }
}
