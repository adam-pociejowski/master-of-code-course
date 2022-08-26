package com.valverde.masterofcode.section8_linkedlists;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomDoubleLinkedListTask {

    @Test
    void shouldAppendMethodWork() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.append("A");
        list.append("B");
        list.append("C");
        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    void shouldPrependMethodWork() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.prepend("A");
        list.prepend("B");
        list.prepend("C");
        assertEquals(3, list.size());
        assertEquals("C", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("A", list.get(2));
    }

    @Test
    void shouldInsertMethodWork() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.append("A");
        list.append("B");
        list.append("C");
        assertEquals(3, list.size());
        list.insert(1, "D");
        assertEquals(4, list.size());
        assertEquals("D", list.get(1));
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(2));
        assertEquals("C", list.get(3));
        list.insert(4, "E");
        assertEquals("E", list.get(4));
        assertEquals(5, list.size());
        list.insert(0, "F");
        assertEquals("F", list.get(0));
        assertEquals(6, list.size());
    }

    @Test
    void shouldDeleteMethodWork() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.append("A");
        list.append("B");
        list.append("C");
        assertEquals(3, list.size());
        list.delete(1);
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));
        list.prepend("E");
        list.append("F");
        assertEquals(4, list.size());
        list.delete(3);
        assertEquals(3, list.size());
        assertEquals("C", list.get(2));
        list.delete(0);
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        list.delete(0);
        list.delete(0);
        assertEquals(0, list.size());
    }


    class CustomLinkedList<T> {
        private int size = 0;
        private ListEntry<T> head;
        private ListEntry<T> tail;

        ListEntry<T> getEntry(int index) {
            if (index >= size) {
                return null;
            }
            ListEntry<T> entry = head;
            for (int i = 0; i < index; i++) {
                entry = entry.next;
            }
            return entry;
        }
        T get(int index) {
            ListEntry<T> entry = getEntry(index);
            return entry != null ? entry.value : null;
        }

        int size() {
            return size;
        }

        void delete(int index) {
            if (index >= size) {
                throw new RuntimeException("Cannot insert out of bound element");
            }
            if (index == 0 && size == 1) {
                head = null;
                tail = null;
            } else if (index == 0) {
                head = head.next;
            } else if (index == size - 1) {
                tail = getEntry(index - 1);
            } else {
                ListEntry<T> entry = getEntry(index - 1);
                entry.next = entry.next.next;
            }
            size--;
        }

        void insert(int index, T value) {
            if (index > size) {
                throw new RuntimeException("Cannot insert out of bound element");
            } else if (index == 0) {
                prepend(value);
            } else if (index == size) {
                append(value);
            } else {
                ListEntry<T> entry = new ListEntry<>(value);
                ListEntry<T> prevEntry = getEntry(index - 1);
                ListEntry<T> nextEntry = prevEntry.next;
                prevEntry.next = entry;
                entry.prev = prevEntry;
                entry.next = nextEntry;
                nextEntry.prev = entry;
                size++;
            }
        }

        void append(T item) {
            ListEntry<T> entry = new ListEntry<>(item);
            if (tail == null) {
                head = entry;
            } else {
                tail.next = entry;
                entry.prev = tail;
            }
            tail = entry;
            size++;
        }

        void prepend(T item) {
            ListEntry<T> entry = new ListEntry<>(item);
            if (head == null) {
                tail = entry;
            } else {
                head.prev = entry;
                entry.next = head;
            }
            head = entry;
            size++;
        }
    }

    class ListEntry<T> {
        private ListEntry<T> next;
        private ListEntry<T> prev;
        private T value;

        public ListEntry(T value) {
            this.value = value;
        }
    }
}
