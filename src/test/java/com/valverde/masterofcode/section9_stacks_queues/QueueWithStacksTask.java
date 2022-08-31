package com.valverde.masterofcode.section9_stacks_queues;

import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class QueueWithStacksTask {

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
        Stack<T> stack1 = new Stack<>();
        Stack<T> stack2 = new Stack<>();


        int size() {
            return stack1.size();
        }

        T peek() {
            return stack1.size() == 0 ? null : stack1.peek();
        }

        T dequeue() {
            return stack1.size() == 0 ? null : stack1.pop();
        }

        void enqueue(T item) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
            stack1.push(item);
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
        }
    }
}
