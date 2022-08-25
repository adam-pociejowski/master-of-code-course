package com.valverde.masterofcode.section7_hashtable;

import com.valverde.masterofcode.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomHashTableTask extends BaseTest {

    @Test
    void shouldAddFewEntries() {
        CustomHashTable<String, Integer> hashTable = new CustomHashTable<>(5);
        hashTable.put("POLAND", 38);
        hashTable.put("GERMANY", 80);
        hashTable.put("USA", 300);
        hashTable.put("RUSSIA", 160);
        hashTable.put("SWEDEN", 15);
        assertEquals(38, hashTable.get("POLAND"));
        assertEquals(300, hashTable.get("USA"));
        assertEquals(15, hashTable.get("SWEDEN"));
        assertEquals(80, hashTable.get("GERMANY"));
        assertEquals(160, hashTable.get("RUSSIA"));
    }

    class CustomHashTable<K, V> {
        private int size;
        private CustomEntry<K, V>[] entries;

        public CustomHashTable(int size) {
            this.size = size;
            entries = new CustomEntry[size];
        }

        void put(K key, V value) {
            int index = Math.abs(key.hashCode()) % size;
            if (entries[index] == null) {
                entries[index] = new CustomEntry<>(key, value);
            } else {
                CustomEntry<K, V> firstEntry = entries[index];
                entries[index] = new CustomEntry<>(key, value);
                entries[index].next = firstEntry;
            }
        }

        V get(K key) {
            int index = Math.abs(key.hashCode()) % size;
            if (entries[index] == null) {
                return null;
            }
            CustomEntry<K, V> entry = entries[index];
            while (entry != null) {
                if (entry.key.equals(key)) {
                    return entry.value;
                }
                entry = entry.next;
            }
            return null;
        }
    }

    class CustomEntry<K, V> {
        private V value;
        private K key;
        private CustomEntry<K, V> next;

        public CustomEntry(K key, V value) {
            this.value = value;
            this.key = key;
        }
    }
}
