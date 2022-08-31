package com.valverde.masterofcode.section9_stacks_queues;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CustomStackArrayTask {

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

    static class CustomStack<T> {
        private final ArrayList<T> array = new ArrayList<T>();

        int size() {
            return array.size();
        }

        T peek() {
            return array.size() == 0 ? null : array.get(0);
        }

        T pop() {
            if (array.size() == 0) {
                return null;
            }
            T value = array.get(0);
            array.remove(0);
            return value;
        }

        void push(T item) {
            array.add(0, item);
        }
    }
}
