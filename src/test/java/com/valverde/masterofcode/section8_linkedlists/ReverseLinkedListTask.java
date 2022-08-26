package com.valverde.masterofcode.section8_linkedlists;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReverseLinkedListTask {

    @Test
    void shouldCreateReversedLinkedList() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.append("A");
        list.append("B");
        list.append("C");
        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
        CustomLinkedList<String> reversed = list.reverse();
        assertEquals(3, reversed.size());
        assertEquals("C", reversed.get(0));
        assertEquals("B", reversed.get(1));
        assertEquals("A", reversed.get(2));
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    void shouldReverseInPlaceLinkedList() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.append("A");
        list.append("B");
        list.append("C");
        list.append("D");
        assertEquals(4, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
        assertEquals("D", list.get(3));
        list.reverseInPlace();
        assertEquals(4, list.size());
        assertEquals("D", list.get(0));
        assertEquals("C", list.get(1));
        assertEquals("B", list.get(2));
        assertEquals("A", list.get(3));
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

        void reverseInPlace() {
            if (size <= 1) {
                return;
            }
            ListEntry<T> entry = head.next;
            while (entry != null) {
                ListEntry<T> nextEntry = entry.next;
                entry.next = head;
                head = entry;
                entry = nextEntry;
            }
        }

        CustomLinkedList<T> reverse() {
            if (size == 0) {
                return new CustomLinkedList<>();
            }
            CustomLinkedList<T> list = new CustomLinkedList<>();
            ListEntry<T> entry = head;
            do {
                list.prepend(entry.value);
                entry = entry.next;
            } while (entry != null);
            return list;
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
                entry.next = nextEntry;
                size++;
            }
        }

        void append(T item) {
            ListEntry<T> entry = new ListEntry<>(item);
            if (tail == null) {
                head = entry;
            } else {
                tail.next = entry;
            }
            tail = entry;
            size++;
        }

        void prepend(T item) {
            ListEntry<T> entry = new ListEntry<>(item);
            if (head == null) {
                tail = entry;
            } else {
                entry.next = head;
            }
            head = entry;
            size++;
        }
    }

    class ListEntry<T> {
        private ListEntry<T> next;
        private T value;

        public ListEntry(T value) {
            this.value = value;
        }
    }
}
