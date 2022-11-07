package com.valverde.masterofcode.section14_tree_search;

import com.valverde.masterofcode.section10_trees.BinarySearchTree;
import com.valverde.masterofcode.section10_trees.Node;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepthSearchFirstTask {

    @Test
    void doDfsSearchRecursiveInOrder() {
        final BinarySearchTree tree = new BinarySearchTree();
        tree.insert(9);
        tree.insert(4);
        tree.insert(6);
        tree.insert(20);
        tree.insert(170);
        tree.insert(15);
        tree.insert(1);
        final List<Node> nodes = dfsRecursiveInOrder(tree.getRoot());
        assertEquals(7, nodes.size());
        assertEquals(1, nodes.get(0).getValue());
        assertEquals(4, nodes.get(1).getValue());
        assertEquals(6, nodes.get(2).getValue());
        assertEquals(9, nodes.get(3).getValue());
        assertEquals(15, nodes.get(4).getValue());
        assertEquals(20, nodes.get(5).getValue());
        assertEquals(170, nodes.get(6).getValue());
    }

    @Test
    void doDfsSearchRecursivePreOrder() {
        final BinarySearchTree tree = new BinarySearchTree();
        tree.insert(9);
        tree.insert(4);
        tree.insert(6);
        tree.insert(20);
        tree.insert(170);
        tree.insert(15);
        tree.insert(1);
        final List<Node> nodes = dfsRecursivePreOrder(tree.getRoot());
        assertEquals(7, nodes.size());
        assertEquals(9, nodes.get(0).getValue());
        assertEquals(4, nodes.get(1).getValue());
        assertEquals(1, nodes.get(2).getValue());
        assertEquals(6, nodes.get(3).getValue());
        assertEquals(20, nodes.get(4).getValue());
        assertEquals(15, nodes.get(5).getValue());
        assertEquals(170, nodes.get(6).getValue());
    }

    @Test
    void doDfsSearchRecursivePostOrder() {
        final BinarySearchTree tree = new BinarySearchTree();
        tree.insert(9);
        tree.insert(4);
        tree.insert(6);
        tree.insert(20);
        tree.insert(170);
        tree.insert(15);
        tree.insert(1);
        final List<Node> nodes = dfsRecursivePostOrder(tree.getRoot());
        assertEquals(7, nodes.size());
        assertEquals(1, nodes.get(0).getValue());
        assertEquals(6, nodes.get(1).getValue());
        assertEquals(4, nodes.get(2).getValue());
        assertEquals(15, nodes.get(3).getValue());
        assertEquals(170, nodes.get(4).getValue());
        assertEquals(20, nodes.get(5).getValue());
        assertEquals(9, nodes.get(6).getValue());
    }

    private List<Node> dfsRecursiveInOrder(final Node node) {
        if (Objects.isNull(node.getLeft())) {
            return new ArrayList<>(Collections.singletonList(node));
        }
        final List<Node> nodes = dfsRecursiveInOrder(node.getLeft());
        nodes.add(node);
        nodes.addAll(dfsRecursiveInOrder(node.getRight()));
        return nodes;
    }

    private List<Node> dfsRecursivePreOrder(final Node node) {
        if (Objects.isNull(node.getLeft())) {
            return new ArrayList<>(Collections.singletonList(node));
        }
        final List<Node> nodes = new ArrayList<>();
        nodes.add(node);
        nodes.addAll(dfsRecursivePreOrder(node.getLeft()));
        nodes.addAll(dfsRecursivePreOrder(node.getRight()));
        return nodes;
    }

    private List<Node> dfsRecursivePostOrder(final Node node) {
        if (Objects.isNull(node.getLeft())) {
            return new ArrayList<>(Collections.singletonList(node));
        }
        final List<Node> nodes = new ArrayList<>();
        nodes.addAll(dfsRecursivePostOrder(node.getLeft()));
        nodes.addAll(dfsRecursivePostOrder(node.getRight()));
        nodes.add(node);
        return nodes;
    }
}
