package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Day10 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
        final List<Command> commands = getCommands();
        int register = 1;
        List<Integer> registerInTime = new ArrayList<>();
        registerInTime.add(register);
        for (Command command : commands) {
            if (command.cmd.equals("noop")) {
                registerInTime.add(register);
            } else {
                registerInTime.add(register);
                register += command.value;
                registerInTime.add(register);
            }
        }

        int _20th = registerInTime.get(19) * 20;
        int _60th = registerInTime.get(59) * 60;
        int _100th = registerInTime.get(99) * 100;
        int _140th = registerInTime.get(139) * 140;
        int _180th = registerInTime.get(179) * 180;
        int _220th = registerInTime.get(219) * 220;
        int result = _20th + _60th + _100th + _140th + _180th + _220th;
        System.out.println("SOLUTION 1: "+result);
    }

    @Test
    void solution2() {
        final List<Command> commands = getCommands();
        int register = 1;
        int cycle = 0;
        List<String> renderedLines = new ArrayList<>();
        String currentLine = "";
        for (Command command : commands) {
            if (command.cmd.equals("noop")) {
                currentLine = draw(currentLine, register, cycle);
                cycle++;
                currentLine = finishLineIfEnd(cycle, renderedLines, currentLine);
            } else {
                currentLine = draw(currentLine, register, cycle);
                cycle++;
                currentLine = finishLineIfEnd(cycle, renderedLines, currentLine);
                currentLine = draw(currentLine, register, cycle);
                cycle++;
                currentLine = finishLineIfEnd(cycle, renderedLines, currentLine);
                register += command.value;
            }
        }
        System.out.println("SOLUTION 2: EKRHEPUZ");
        renderedLines.forEach(System.out::println);
    }

    private String finishLineIfEnd(int cycle, List<String> renderedLines, String currentLine) {
        if (cycle % 40 == 0) {
            renderedLines.add(currentLine);
            currentLine = "";
        }
        return currentLine;
    }

    private String draw(String line, int register, int cycle) {
        int position = cycle % 40;
        if (position >= register - 1 && position <= register + 1) {
            return line + "#";
        }
        return line + "_";
    }

    private List<Command> getCommands() {
        return readStringLines("input10.txt")
                .stream()
                .map(l -> {
                    if (l.equals("noop")) {
                        return new Command("noop");
                    }
                    String[] s = l.split(" ");
                    return new Command(s[0], Integer.parseInt(s[1]));
                })
                .toList();
    }

    static class Command {
        String cmd;
        int value;

        public Command(String cmd, int value) {
            this.cmd = cmd;
            this.value = value;
        }

        public Command(String cmd) {
            this.cmd = cmd;
        }
    }
}
