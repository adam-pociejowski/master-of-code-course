package com.valverde.masterofcode.section9_stacks_queues;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CustomQueueTask {

    @Test
    void shouldAppendMethodWork() {
        CustomQueue<String> queue = new CustomQueue<>();
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        assertEquals(3, queue.size());
        assertEquals("A", queue.peek());
        assertEquals("A", queue.dequeue());
        assertEquals(2, queue.size());
        assertEquals("B", queue.peek());
        assertEquals("B", queue.dequeue());
        assertEquals(1, queue.size());
        assertEquals("C", queue.peek());
        assertEquals("C", queue.dequeue());
        assertEquals(0, queue.size());
        assertNull(queue.peek());
        assertNull(queue.dequeue());
        queue.enqueue("D");
        assertEquals(1, queue.size());
        assertEquals("D", queue.dequeue());
    }

    static class CustomQueue<T> {
        private int size = 0;
        private Node<T> head;
        private Node<T> tail;

        int size() {
            return size;
        }

        T peek() {
            return size == 0 ? null : head.value;
        }

        T dequeue() {
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

        void enqueue(T item) {
            Node<T> entry = new Node<>(item);
            if (head == null) {
                head = entry;
            } else {
                tail.next = entry;
            }
            tail = entry;
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
