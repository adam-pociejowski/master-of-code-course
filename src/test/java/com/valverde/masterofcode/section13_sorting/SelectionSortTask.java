package com.valverde.masterofcode.section13_sorting;

import org.junit.jupiter.api.BeforeEach;
import java.util.List;

public class SelectionSortTask extends BaseSortTask {

    @BeforeEach
    void setup() {
        sortable = new SelectionSort();
    }

    class SelectionSort implements Sortable {
        public void sort(final List<Integer> list) {
            for (int i = 0; i < list.size(); i++) {
                int smallestIndex = i;
                for (int j = i + 1; j < list.size(); j++) {
                    comparisonCounter++;
                    if (list.get(j) < list.get(smallestIndex)) {
                        smallestIndex = j;
                    }
                }
                comparisonCounter++;
                if (smallestIndex > i) {
                    operationsCounter++;
                    var tmp = list.get(smallestIndex);
                    list.set(smallestIndex, list.get(i));
                    list.set(i, tmp);
                }
            }
        }
    }
}
