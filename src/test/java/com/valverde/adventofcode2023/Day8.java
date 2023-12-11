package com.valverde.adventofcode2023;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 extends AbstractTask {

    @BeforeEach
    void setup() {
    }

    @Test
    void solution1() {
//        final List<String> lines = readStringLines("inputTest.txt");
        final List<String> lines = readStringLines("input8.txt");

        List<String> moves = stringToCharactersList(lines.get(0));
        List<Node> tmpNodes = lines
                .subList(2, lines.size())
                .stream()
                .map(l -> {
                    String[] split = l.split(" = ");
                    return new Node(split[0]);
                })
                .toList();

        List<Node> nodes = lines
                .subList(2, lines.size())
                .stream()
                .map(l -> {
                    String[] split = l.split(" = ");
                    String[] targetNodes = split[1].replace("(", "")
                            .replace(")", "")
                            .replace(" ", "")
                            .split(",");

                    Node node = findByValue(tmpNodes, split[0]);
                    Node left = findByValue(tmpNodes, targetNodes[0]);
                    Node right = findByValue(tmpNodes, targetNodes[1]);
                    node.left = left;
                    node.right = right;
                    return node;
                })
                .toList();


        boolean foundTarget = false;
        BigInteger index = BigInteger.ZERO;
        BigInteger movesSize = BigInteger.valueOf(moves.size());
        Node currentNode = findByValue(nodes, "AAA");
        Node targetNode = findByValue(nodes, "ZZZ");
        do {
            BigInteger mod = index.mod(movesSize);
            String move = moves.get(mod.intValue());
            if (move.equals("R")) {
                currentNode = currentNode.right;
            } else {
                currentNode =  currentNode.left;
            }
            if (currentNode.equals(targetNode)) {
                foundTarget = true;
            }
            if (index.mod(BigInteger.valueOf(10000)).equals(BigInteger.ZERO)) {
                System.out.println(index);
            }
            index = index.add(BigInteger.ONE);
        } while (!foundTarget);


        int result = 0;
        System.out.println("Task1: "+index);

    }

    private Node findByValue(List<Node> nodes, String value) {
        return nodes
                .stream()
                .filter(n -> n.value.equals(value))
                .findFirst()
                .get();
    }

    private List<Node> findAllEndingWith(List<Node> nodes, String ending) {
        return nodes
                .stream()
                .filter(n -> n.value.endsWith(ending))
                .toList();
    }

    private List<Node> findAllEndingWithAndNotEqTo(List<Node> nodes, String ending, String notEq) {
        return nodes
                .stream()
                .filter(n -> n.value.endsWith(ending) && !n.value.endsWith(notEq))
                .toList();
    }

    @Test
    void solution2() {
//        final List<String> lines = readStringLines("inputTest.txt");
        final List<String> lines = readStringLines("input8.txt");

        List<String> moves = stringToCharactersList(lines.get(0));
        List<Node> tmpNodes = lines
                .subList(2, lines.size())
                .stream()
                .map(l -> {
                    String[] split = l.split(" = ");
                    return new Node(split[0]);
                })
                .toList();

        List<Node> nodes = lines
                .subList(2, lines.size())
                .stream()
                .map(l -> {
                    String[] split = l.split(" = ");
                    String[] targetNodes = split[1].replace("(", "")
                            .replace(")", "")
                            .replace(" ", "")
                            .split(",");

                    Node node = findByValue(tmpNodes, split[0]);
                    Node left = findByValue(tmpNodes, targetNodes[0]);
                    Node right = findByValue(tmpNodes, targetNodes[1]);
                    node.left = left;
                    node.right = right;
                    return node;
                })
                .toList();

        for (Node n : nodes) {
            if (n.value == null || n.left == null || n.right == null) {
                throw new RuntimeException(n.toString());
            }
        }

        List<Node> currentNodes = findAllEndingWith(nodes, "A");
        List<Node> startingNodes = List.copyOf(currentNodes);

//        findCycle(startingNodes.get(1), moves);
        List<Cycle> cycles = startingNodes
                .stream()
                .map(n -> findCycle(n, moves))
                .toList();
        boolean solutionFound = false;
        BigInteger solution = null;
        BigInteger cycleCounter = BigInteger.ZERO;


        List<BigInteger> currentTargets = new ArrayList<>();

        for (Cycle c : cycles) {
            BigInteger targetInCycleIndex = BigInteger.valueOf(c.targetIndexes.get(0));
            currentTargets.add(targetInCycleIndex);
        }

        while (!solutionFound) {
            int minIndex = indexOfMin(currentTargets);
            Cycle cycle = cycles.get(minIndex);
            BigInteger value = currentTargets.get(minIndex);
            currentTargets.set(minIndex, value.add(BigInteger.valueOf(cycle.cycleLength)));
            if (allValuesAreTheSame(currentTargets)) {
                solutionFound = true;
                System.out.println("FOUND: "+value.add(BigInteger.valueOf(cycle.cycleLength)));
            }
        }
//        Map<BigInteger, List<String>> targetMap = new HashMap<>();
//
//        while (!solutionFound) {
//            BigInteger minTargetIndex = null;
//            for (Cycle c: cycles) {
//                for (Integer index : c.targetIndexes) {
//                    BigInteger targetInCycleIndex = BigInteger.valueOf(index)
//                            .subtract(BigInteger.valueOf(c.cycleStart));
//                    BigInteger targetIndex = BigInteger.valueOf(c.cycleStart)
//                            .add(BigInteger.valueOf(c.cycleLength).multiply(cycleCounter))
//                            .add(targetInCycleIndex);
//                    if (!targetMap.containsKey(targetIndex)) {
//                        targetMap.put(targetIndex, new ArrayList<>());
//                    }
//                    targetMap.get(targetIndex).add(c.start);
//                    if (targetMap.get(targetIndex).size() == cycles.size()) {
//                        solutionFound = true;
//                        solution = targetIndex;
//                    }
//                    if (minTargetIndex == null || targetIndex.compareTo(minTargetIndex) < 0) {
//                        minTargetIndex = targetIndex;
//                    }
//                }
//            }
//            cycleCounter = cycleCounter.add(BigInteger.ONE);
//            if (cycleCounter.mod(BigInteger.valueOf(1000)).equals(BigInteger.ZERO)) {
//                System.out.println("Cycle: "+cycleCounter);
//                int sizeBefore = targetMap.size();
//                List<BigInteger> keysToRemove = new ArrayList<>();
//                for (BigInteger i : targetMap.keySet()) {
//                    if (i.compareTo(minTargetIndex) < 0) {
//                        keysToRemove.add(i);
//                    }
//                }
//                for (BigInteger i : keysToRemove) {
//                    targetMap.remove(i);
//                }
//                System.out.println("Size: "+targetMap.size()+"/"+sizeBefore);
//            }
//        }
//        System.out.println("Task2: "+solution);
    }

    private boolean allValuesAreTheSame(List<BigInteger> values) {
        return values
                .stream()
                .allMatch(e -> e.equals(values.get(0)));
    }

    private int indexOfMin(List<BigInteger> values) {
        int index = -1;
        BigInteger min = null;
        int i = 0;
        for (BigInteger value : values) {
            if (min == null || value.compareTo(min) < 0) {
                min = value;
                index = i;
            }
            i++;
        }
        return index;
    }

    private Cycle findCycle(Node startNode, List<String> moves) {
        Node currentNode = startNode;
        BigInteger index = BigInteger.ZERO;
        BigInteger movesSize = BigInteger.valueOf(moves.size());
        Map<String, BigInteger> stateMap = new HashMap<>();
        stateMap.put("%s%s".formatted(currentNode.value, BigInteger.ZERO), index);
        List<Integer> targetFounds = new ArrayList<>();
        while (true) {
            BigInteger mod = index.mod(movesSize);
            String move = moves.get(mod.intValue());
            if (move.equals("R")) {
                currentNode = currentNode.right;
            } else if(move.equals("L")) {
                currentNode = currentNode.left;
            }
            index = index.add(BigInteger.ONE);
            String key = "%s%s".formatted(currentNode.value, index.mod(movesSize));
            if (stateMap.containsKey(key)) {
                return new Cycle(
                        startNode.value,
                        currentNode.value,
                        stateMap.get(key).intValue(),
                        index.subtract(stateMap.get(key)).intValue(),
                        targetFounds
                );
            }
            stateMap.put(key, index);
            if (currentNode.value.endsWith("Z")) {
                targetFounds.add(index.intValue());
            }
        }
    }

    private boolean allNodesEndsWith(List<Node> nodes, String end) {
        int matching = 0;
        for (Node n : nodes) {
            if (n.value.endsWith(end)) {
                matching++;
            }
        }
        if (matching > 2) {
            System.out.println("matching: "+matching);
        } else if  (matching == nodes.size() - 1) {
            System.out.println("-1: "+nodes);
        }
        return matching == nodes.size();
    }


    static class Cycle {
        private final String start;
        private final String value;
        private final int cycleStart;
        private final int cycleLength;
        private final List<Integer> targetIndexes;

        public Cycle(String start, String value, int cycleStart, int cycleLength, List<Integer> targetIndexes) {
            this.start = start;
            this.value = value;
            this.cycleStart = cycleStart;
            this.cycleLength = cycleLength;
            this.targetIndexes = targetIndexes;
        }
    }

    static class Node {
        private final String value;
        private Node left;
        private Node right;

        public Node(String value) {
            this.value = value;
        }

        public Node(String value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value='" + value + '\'' +
                    ", left='" + left.value + '\'' +
                    ", right='" + right.value + '\'' +
                    '}';
        }
    }
}
