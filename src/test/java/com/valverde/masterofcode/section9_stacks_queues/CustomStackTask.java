package com.valverde.masterofcode.section9_stacks_queues;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CustomStackTask {

    @Test
    void shouldAppendMethodWork() {
        CustomStack<String> stack = new CustomStack<>();
        stack.push("A");
        stack.push("B");
        stack.push("C");
        assertEquals(3, stack.size());
        assertEquals("C", stack.peek());
        assertEquals("C", stack.pop());
        assertEquals(2, stack.size());
        assertEquals("B", stack.peek());
        assertEquals("B", stack.pop());
        assertEquals(1, stack.size());
        assertEquals("A", stack.peek());
        assertEquals("A", stack.pop());
        assertEquals(0, stack.size());
        assertNull(stack.peek());
        assertNull(stack.pop());
    }

    class CustomStack<T> {
        private int size = 0;
        private Node<T> head;
        private Node<T> tail;

        int size() {
            return size;
        }

        T peek() {
            return size == 0 ? null : head.value;
        }

        T pop() {
            if (size == 0) {
                return null;
            }
            if (head.equals(tail)) {
                tail = null;
            }
            T value = head.value;
            head = head.next;
            size--;
            return value;
        }

        void push(T item) {
            Node<T> entry = new Node<>(item);
            if (head == null) {
                tail = entry;
            } else {
                entry.next = head;
            }
            head = entry;
            size++;
        }
    }

    static class Node<T> {
        private Node<T> next;
        private final T value;

        public Node(T value) {
            this.value = value;
        }
    }
}
