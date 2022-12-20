package com.valverde.adventofcode2022;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;

public class Day16 extends AbstractTask {

    @Test
    void solution2() {
        final List<String> lines = readStringLines("input16.txt");
        Map<String, Node> nodes = getNodes(lines);
        addConnections(lines, nodes);
        Deque<State> queue = new ArrayDeque<>();
        State startState = new State(0, 0, nodes.get("AA"), nodes.get("AA"), new HashSet<>());
        State best = startState;
        queue.add(startState);
        int depth = 0;
        Set<State> seen = new HashSet<>();
        int minutes = 26;
        while (!queue.isEmpty()) {
            State state = queue.poll();
            if (state.time > minutes ||
                    seen.contains(state) ||
                    (depth > 10 && state.score < 1400) ||
                    (depth > 15 && state.score < 2000) ||
                    (depth > 20 && state.score < 2300)) {
                continue;
            }
            if (state.time > depth) {
                seen = new HashSet<>();
                depth = state.time;
                System.out.printf("################### DEPTH: %s, BEST: %s, QUEUE: %s\n", depth, best, queue.size());
            }
            seen.add(state);
            if (state.score > best.score) {
                best = state;
                System.out.printf("FOUND BETTER SCORE, DEPTH: %s, BEST: %s\n", depth, best);
            }
            if (!state.opened.contains(state.currentNode.name) && state.currentNode.rate > 0 &&
                    !state.opened.contains(state.currentNodeElephant.name) && state.currentNodeElephant.rate > 0 &&
                        !state.currentNode.equals(state.currentNodeElephant)) { //both open nodes
                HashSet<String> opened = new HashSet<>(state.opened);
                opened.add(state.currentNode.name);
                opened.add(state.currentNodeElephant.name);
                queue.add(new State(
                        state.time + 1,
                        state.score + (state.currentNode.rate * (minutes - state.time - 1)) + (state.currentNodeElephant.rate * (minutes - state.time - 1)),
                        state.currentNode,
                        state.currentNodeElephant,
                        opened));
            }
            if (!state.opened.contains(state.currentNode.name) && state.currentNode.rate > 0) { //me open elephant moving
                for (Node n : state.currentNodeElephant.connections) {
                    HashSet<String> opened = new HashSet<>(state.opened);
                    opened.add(state.currentNode.name);
                    queue.add(new State(
                            state.time + 1,
                            state.score + (state.currentNode.rate * (minutes - state.time - 1)),
                            state.currentNode,
                            n,
                            opened));
                }
            }
            if (!state.opened.contains(state.currentNodeElephant.name) && state.currentNodeElephant.rate > 0) { //me moving, elephant open
                for (Node n : state.currentNode.connections) {
                    HashSet<String> opened = new HashSet<>(state.opened);
                    opened.add(state.currentNodeElephant.name);
                    queue.add(new State(
                            state.time + 1,
                            state.score + (state.currentNodeElephant.rate * (minutes - state.time - 1)),
                            n,
                            state.currentNodeElephant,
                            opened));
                }
            }
            for (Node eN : state.currentNodeElephant.connections) { //me moving, elephant moving
                for (Node n : state.currentNode.connections) {
                    queue.add(new State(
                            state.time + 1,
                            state.score,
                            n,
                            eN,
                            new HashSet<>(state.opened)));
                }
            }
        }

        int result = best.score;
        System.out.println("SOLUTION 2: " + result);
    }

    @Test
    void solution1() {
        final List<String> lines = readStringLines("input16.txt");
        Map<String, Node> nodes = getNodes(lines);
        addConnections(lines, nodes);
        Deque<State> queue = new ArrayDeque<>();
        State startState = new State(0, 0, nodes.get("AA"), new HashSet<>());
        State best = startState;
        queue.add(startState);
        int depth = 0;
        Set<State> seen = new HashSet<>();
        int minutes = 30;
        while (!queue.isEmpty()) {
            State state = queue.poll();
            if (state.time > minutes ||
                    seen.contains(state) ||
                    (depth > 15 && state.score < 1000)) {
                continue;
            }
            if (state.time > depth) {
                seen = new HashSet<>();
                depth = state.time;
                System.out.printf("################### DEPTH: %s, BEST: %s, QUEUE: %s\n", depth, best, queue.size());
            }
            seen.add(state);
            if (state.score > best.score) {
                best = state;
                System.out.printf("FOUND BETTER SCORE, DEPTH: %s, BEST: %s\n", depth, best);
            }
            if (!state.opened.contains(state.currentNode.name) && state.currentNode.rate > 0) { //open node
                HashSet<String> opened = new HashSet<>(state.opened);
                opened.add(state.currentNode.name);
                queue.add(new State(
                        state.time + 1,
                        state.score + (state.currentNode.rate * (minutes - state.time - 1)),
                        state.currentNode,
                        opened));
            }
            for (Node n : state.currentNode.connections) { //visit node
                queue.add(new State(
                        state.time + 1,
                        state.score,
                        n,
                        new HashSet<>(state.opened)));
            }
        }

        int result = best.score;
        System.out.println("SOLUTION 1: " + result);
    }

    private Map<String, Node> getNodes(List<String> lines) {
        Map<String, Node> nodes = lines
                .stream()
                .map(l -> {
                    String[] words = l.split(" ");
                    String rate = words[4].split("=")[1].replace(";", "");
                    return new Node(words[1], Integer.parseInt(rate));
                })
                .collect(Collectors.toMap(Node::getName, a -> a));
        return nodes;
    }

    private void addConnections(List<String> lines, Map<String, Node> nodes) {
        lines.forEach(l -> {
            String currentNode = l.split(" ")[1];
            String[] nodeNames = l.split(";")[1]
                    .replaceAll(" tunnels lead to valves ", "")
                    .replaceAll(" tunnel leads to valve ", "")
                    .replaceAll(" ", "")
                    .split(",");
            Node node = nodes.get(currentNode);
            for (String name : nodeNames) {
                Node n = nodes.get(name);
                node.connections.add(n);
            }
        });
    }

    static class State {
        int time;
        int score;
        Node currentNode;
        Node currentNodeElephant;
        Set<String> opened;

        public State(int time, int score, Node currentNode, Node currentNodeElephant, Set<String> opened) {
            this.time = time;
            this.score = score;
            this.currentNode = currentNode;
            this.currentNodeElephant = currentNodeElephant;
            this.opened = opened;
        }

        public State(int time, int score, Node currentNode, Set<String> opened) {
            this.time = time;
            this.score = score;
            this.currentNode = currentNode;
            this.opened = opened;
        }

        @Override
        public String toString() {
            return "score="+score+", opened=" + opened;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return time == state.time && score == state.score && Objects.equals(currentNode, state.currentNode) && Objects.equals(currentNodeElephant, state.currentNodeElephant) && Objects.equals(opened, state.opened);
        }

        @Override
        public int hashCode() {
            return Objects.hash(time, score, currentNode, currentNodeElephant, opened);
        }
    }

    static class Node {
        String name;
        int rate;
        Set<Node> connections = new HashSet<>();


        public Node(String name, int rate) {
            this.rate = rate;
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
