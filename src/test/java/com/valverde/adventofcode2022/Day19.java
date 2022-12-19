package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Day19 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution2() {
        final List<String> lines = readStringLines("inputTest.txt");
        int result = 0;
        System.out.println("SOLUTION 2: " + result);
    }

    @Test
    void solution1() {
        List<BluePrint> bluePrints = getBluePrints("inputTest.txt");
        for (BluePrint bluePrint : bluePrints) {
            Simulation simulation = new Simulation(bluePrint);
            for (int i = 0; i < 24; i++) {
                int oreRobots = simulation.oreRobots;
                int clayRobots = simulation.clayRobots;
                int obsidianRobots = simulation.obsidianRobots;
                int geodeRobots = simulation.geodeRobots;
                simulation.buildGeodeRobot();
                simulation.buildObsidianRobot();
                simulation.buildClayRobot();
                simulation.buildOreRobot();
                simulation.ore += oreRobots;
                simulation.clay += clayRobots;
                simulation.obsidian += obsidianRobots;
                simulation.openedGeodes += geodeRobots;
                System.out.printf("%s: %s\n", (i + 1), simulation);
            }
        }
        int result = bluePrints.size();
        System.out.println("SOLUTION 1: " + result);
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

    static class Simulation {
        int ore = 0;
        int clay = 0;
        int obsidian = 0;
        int openedGeodes = 0;
        BluePrint bluePrint;
        int oreRobots = 1;
        int clayRobots = 0;
        int obsidianRobots = 0;
        int geodeRobots = 0;

        public Simulation(BluePrint bluePrint) {
            this.bluePrint = bluePrint;
        }

        void buildOreRobot() {
            if (ore >= bluePrint.oreRobot.ore) {
                System.out.println("Building ore robot");
                ore -= bluePrint.oreRobot.ore;
                oreRobots++;
            }
        }

        void buildClayRobot() {
            if (ore >= bluePrint.clayRobot.ore) {
                System.out.println("Building clay robot");
                ore -= bluePrint.clayRobot.ore;
                clayRobots++;
            }
        }

        void buildObsidianRobot() {
            if (ore >= bluePrint.obsidianRobot.ore && clay >= bluePrint.obsidianRobot.clay) {
                System.out.println("Building obsidian robot");
                ore -= bluePrint.obsidianRobot.ore;
                clay -= bluePrint.obsidianRobot.clay;
                obsidianRobots++;
            }
        }

        void buildGeodeRobot() {
            if (ore >= bluePrint.geodeRobot.ore && obsidian >= bluePrint.geodeRobot.obsidian) {
                System.out.println("Building geode robot");
                ore -= bluePrint.geodeRobot.ore;
                obsidian -= bluePrint.geodeRobot.obsidian;
                geodeRobots++;
            }
        }

        @Override
        public String toString() {
            return "Simulation{" +
                    "ore=" + ore +
                    ", clay=" + clay +
                    ", obsidian=" + obsidian +
                    ", openedGeodes=" + openedGeodes +
                    ", oreRobots=" + oreRobots +
                    ", clayRobots=" + clayRobots +
                    ", obsidianRobots=" + obsidianRobots +
                    ", geodeRobots=" + geodeRobots +
                    '}';
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
