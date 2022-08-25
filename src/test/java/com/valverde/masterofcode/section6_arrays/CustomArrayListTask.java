package com.valverde.masterofcode.section6_arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomArrayListTask {

    @Test
    void shouldAddCorrectlyItems() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("a");
        list.add("b");
        assertEquals(2, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
    }

    @Test
    void shouldExtendCorrectlyArray() {
        CustomArrayList<String> list = new CustomArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i+"");
        }
        assertEquals(100, list.size());
        assertEquals("0", list.get(0));
        assertEquals("50", list.get(50));
        assertEquals("99", list.get(99));
    }

    @Test
    void shouldDeleteCorrectlyItems() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.delete(2);
        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
        assertEquals("d", list.get(2));
        list.delete(0);
        assertEquals(2, list.size());
        assertEquals("b", list.get(0));
        assertEquals("d", list.get(1));
    }

    static class CustomArrayList<T> {
        private Object[] data;
        private int currentSize = 0;

        CustomArrayList() {
            data = new Object[2];
        }

        T get(int index) {
            return (T) data[index];
        }

        void add(T value) {
            if (currentSize + 1 > data.length) {
                extendData();
            }
            data[currentSize] = value;
            currentSize++;
        }

        void delete(int index) {
            if (index < size() && index >= 0) {
                Object[] tmpData = new Object[data.length];
                System.arraycopy(data, 0, tmpData, 0, index);
                System.arraycopy(data, index + 1, tmpData, index, data.length - index - 1);
                data = tmpData;
                currentSize--;
            }
        }

        int size() {
            return currentSize;
        }

        private void extendData() {
            Object[] tmpData = new Object[data.length*2];
            System.arraycopy(data, 0, tmpData, 0, data.length);
            data = tmpData;
        }
    }
}
