package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Day7 extends AbstractTask {

    @BeforeEach
    void setup() {}

    @Test
    void solution1() {
        Node root = generateDiskTree();
        long allSize = updateDirectoriesSize(root);
        long sizeLesserThan = sumDirectoriesWithLesserSizeThan(root,100000);
        System.out.println("SOLUTION 1: "+sizeLesserThan);
    }

    @Test
    void solution2() {
        long fullDiskSpace = 70000000;
        long requiredFreeSpace = 30000000;
        Node root = generateDiskTree();
        long allSize = updateDirectoriesSize(root);
        long freeSpace = fullDiskSpace - allSize;
        long spaceToRecover = requiredFreeSpace - freeSpace;
        long lowestSizeDirToRemove = findLowestSizeDirToRemove(root, spaceToRecover);
        System.out.println("SOLUTION 2: "+lowestSizeDirToRemove);
    }

    private long findLowestSizeDirToRemove(Node node, long requiredSpace) {
        if (!node.isDirectory) {
            return Long.MAX_VALUE;
        } else if (node.size >= requiredSpace) {
            List<Node> childrenDirs = node
                    .children
                    .stream()
                    .filter(n -> n.isDirectory)
                    .toList();
            long lowestSize = node.size;
            for (Node n : childrenDirs) {
                long lowestSizeDirToRemove = findLowestSizeDirToRemove(n, requiredSpace);
                if (lowestSizeDirToRemove < lowestSize) {
                    lowestSize = lowestSizeDirToRemove;
                }
            }
            return lowestSize;
        }
        return Long.MAX_VALUE;
    }

    private Node generateDiskTree() {
        List<String> lines = readStringLines("input7.txt");
        lines = lines.subList(1, lines.size());
        Node root = new Node("/", true);
        generateFilesTree(root, prepareCommandsStack(lines));
        return root;
    }

    private Stack<String> prepareCommandsStack(List<String> lines) {
        Stack<String> stack = new Stack<>();
        Collections.reverse(lines);
        lines.forEach(stack::push);
        return stack;
    }

    private long sumDirectoriesWithLesserSizeThan(Node node, long limit) {
        if (node.isDirectory && node.size <= limit) {
            long sum = 0;
            for (Node n : node.children) {
                sum += sumDirectoriesWithLesserSizeThan(n, limit);
            }
            return sum + node.size;
        } else if (node.isDirectory) {
            long sum = 0;
            for (Node n : node.children) {
                sum += sumDirectoriesWithLesserSizeThan(n, limit);
            }
            return sum;
        } else {
            return 0;
        }
    }

    private long updateDirectoriesSize(Node node) {
        if (!node.isDirectory) {
            return node.size;
        }
        long sum = 0;
        for (Node n : node.children) {
            sum += updateDirectoriesSize(n);
        }
        node.size = sum;
        return sum;
    }

    private void generateFilesTree(Node currentNode, Stack<String> stack) {
        while (!stack.isEmpty()) {
            String line = stack.pop();
            if (line.startsWith("$ ls")) {
                while (!stack.isEmpty() && !stack.peek().startsWith("$")) {
                    String lsOutput = stack.pop();
                    if (lsOutput.startsWith("dir")) {
                        String[] split = lsOutput.split(" ");
                        currentNode.children.add(new Node(
                                currentNode.path + split[1] + "/",
                                new ArrayList<>(),
                                currentNode));
                    } else {
                        String[] split = lsOutput.split(" ");
                        currentNode.children.add(new Node(
                                currentNode.path + split[1],
                                currentNode,
                                Long.parseLong(split[0])));
                    }
                }
            } else if (line.startsWith("$ cd ..")) {
                currentNode = currentNode.parent;
            } else {
                String[] split = line.split(" ");
                final String lookingDir = "%s%s/".formatted(currentNode.path, split[2]);
                currentNode = currentNode
                        .children
                        .stream()
                        .filter(c -> {
                            return c.path.equals(lookingDir);
                        })
                        .findFirst()
                        .orElseThrow();
            }
        }
    }




    class Node {
        List<Node> children;
        Node parent;
        long size;
        boolean isDirectory;
        String path;

        public Node(String path, List<Node> children, Node parent) {
            this.path = path;
            this.children = children;
            this.parent = parent;
            this.size = 0;
            this.isDirectory = true;
        }

        public Node(String path, Node parent, long size) {
            this.path = path;
            this.children = new ArrayList<>();
            this.parent = parent;
            this.size = size;
            this.isDirectory = false;
        }

        public Node(String path, boolean isDirectory) {
            this.path = path;
            this.isDirectory = isDirectory;
            this.children = new ArrayList<>();
        }
    }
}
