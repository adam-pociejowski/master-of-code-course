package com.valverde.masterofcode.section14_tree_search;

import com.valverde.masterofcode.section10_trees.BinarySearchTree;
import com.valverde.masterofcode.section10_trees.Node;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BreadthFirstSearchTask {

    @Test
    void doBfsSearchRecursive() {
        final BinarySearchTree tree = new BinarySearchTree();
        tree.insert(9);
        tree.insert(4);
        tree.insert(6);
        tree.insert(20);
        tree.insert(170);
        tree.insert(15);
        tree.insert(1);
        final List<Node> nodes = bfsRecursive(tree);
        assertSearchResults(nodes);
    }

    @Test
    void doBfsSearchIterative() {
        final BinarySearchTree tree = new BinarySearchTree();
        tree.insert(9);
        tree.insert(4);
        tree.insert(6);
        tree.insert(20);
        tree.insert(170);
        tree.insert(15);
        tree.insert(1);
        final List<Node> nodes = bfsIterative(tree);
        assertSearchResults(nodes);
    }

    private void assertSearchResults(List<Node> nodes) {
        assertEquals(7, nodes.size());
        assertEquals(9, nodes.get(0).getValue());
        assertEquals(4, nodes.get(1).getValue());
        assertEquals(20, nodes.get(2).getValue());
        assertEquals(1, nodes.get(3).getValue());
        assertEquals(6, nodes.get(4).getValue());
        assertEquals(15, nodes.get(5).getValue());
        assertEquals(170, nodes.get(6).getValue());
    }

    private List<Node> bfsRecursive(final BinarySearchTree tree) {
        final Node root = tree.getRoot();
        return bfsSearchRecursive(Collections.singletonList(root), new ArrayList<>());
    }

    private List<Node> bfsIterative(final BinarySearchTree tree) {
        final Node root = tree.getRoot();
        return bfsSearchIterative(root);
    }

    private List<Node> bfsSearchIterative(final Node root) {
        final List<Node> results = new ArrayList<>();
        final Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            final Node node = queue.poll();
            results.add(node);
            if (Objects.nonNull(node.getLeft())) {
                queue.add(node.getLeft());
            }
            if (Objects.nonNull(node.getRight())) {
                queue.add(node.getRight());
            }
        }
        return results;
    }

    private List<Node> bfsSearchRecursive(final List<Node> queue, final List<Node> results) {
        if (queue.isEmpty()) {
            return results;
        }
        final List<Node> newQueue = new ArrayList<>();
        queue
                .forEach(node -> {
                    results.add(node);
                    if (Objects.nonNull(node.getLeft())) {
                        newQueue.add(node.getLeft());
                    }
                    if (Objects.nonNull(node.getRight())) {
                        newQueue.add(node.getRight());
                    }
                });
        return bfsSearchRecursive(newQueue, results);
    }
}
