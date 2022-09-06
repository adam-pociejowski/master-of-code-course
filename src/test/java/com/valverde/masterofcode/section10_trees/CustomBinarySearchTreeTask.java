package com.valverde.masterofcode.section10_trees;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomBinarySearchTreeTask {

    @Test
    void shouldInsertValues() {
        CustomBinarySearchTree tree = new CustomBinarySearchTree();
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

    static class CustomBinarySearchTree {
        Node root;

        boolean remove(int value) {
            return removeRecursive(null, root, value);
        }

        private boolean removeRecursive(Node parent, Node node, int value) {
            if (node == null) {
                return false;
            } else if (value < node.value) {
                return removeRecursive(node, node.left, value);
            } else if (value > node.value) {
                return removeRecursive(node, node.right, value);
            }
            removeNode(parent, node);
            return true;
        }

        private void removeNode(Node parent, Node node) {
            if (parent == null) {
                removeRootNode(node);
            } else if (node.left != null && node.right != null) {
                parent.left = node.left;
                Node mostLeftLeafOnRightSide = findMostLeftLeafNode(node.right);
                mostLeftLeafOnRightSide.left = node.left.right;
                node.left.right = node.right;
            } else if (node.left != null) {
                parent.left = node.left;
            } else if (node.right != null) {
                parent.left = node.right;
            }
        }

        private void removeRootNode(Node node) {
            if (node.left != null && node.right != null) {
                root = node.left;
                Node mostLeftLeafOnRightSide = findMostLeftLeafNode(node.right);
                mostLeftLeafOnRightSide.left = root.right;
                root.right = node.right;
            } else if (node.left == null) {
                root = node.right;
            } else {
                root = node.left;
            }
        }

        private Node findMostLeftLeafNode(Node node) {
            if (node.left != null) {
                return findMostLeftLeafNode(node.left);
            }
            return node;
        }

        Node lookup(int value) {
            return lookupRecursive(root, value);
        }

        void insert(int value) {
            if (root == null) {
                root = new Node(value);
            } else {
                insertRecursive(root, value);
            }
        }

        private Node lookupRecursive(Node node, int value) {
            if (node == null) {
                return null;
            } else if (value == node.value) {
                return node;
            } else if (value < node.value) {
                return lookupRecursive(node.left, value);
            } else {
                return lookupRecursive(node.right, value);
            }
        }

        void insertRecursive(Node node, int value) {
            if (value < node.value) {
                insertLeftRecursive(node, node.left, value);
            } else {
                insertRightRecursive(node, node.right, value);
            }
        }

        private void insertLeftRecursive(Node parent, Node node, int value) {
            if (node == null) {
                parent.left = new Node(value);
            }  else {
                insertRecursive(node, value);
            }
        }

        private void insertRightRecursive(Node parent, Node node, int value) {
            if (node == null) {
                parent.right = new Node(value);
            } else {
                insertRecursive(node, value);
            }
        }

        static class Node {
            int value;
            Node left;
            Node right;

            public Node(int value) {
                this.value = value;
            }
        }
    }
}
