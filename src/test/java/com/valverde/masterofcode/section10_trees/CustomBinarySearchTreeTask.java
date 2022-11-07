package com.valverde.masterofcode.section10_trees;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomBinarySearchTreeTask {

    @Test
    void shouldInsertValues() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(9);
        tree.insert(4);
        tree.insert(6);
        tree.insert(20);
        tree.insert(170);
        tree.insert(15);
        tree.insert(1);
        assertNotNull(tree.lookup(20));
        assertNotNull(tree.lookup(15));
        assertNotNull(tree.lookup(9));
        assertNull(tree.lookup(7));
        assertNull(tree.lookup(0));
        assertTrue(tree.remove(9));
        assertFalse(tree.remove(9));
        assertNotNull(tree.lookup(20));
        assertNotNull(tree.lookup(15));
        assertNull(tree.lookup(9));
        assertNull(tree.lookup(7));
        assertNull(tree.lookup(0));
    }
}
