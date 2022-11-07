package com.valverde.masterofcode.section13_sorting;

import org.junit.jupiter.api.BeforeEach;
import java.util.List;

public class BubbleSortTask extends BaseSortTask {

    @BeforeEach
    void setup() {
        sortable = new BubbleSort();
    }

    class BubbleSort implements Sortable {
        public void sort(final List<Integer> list) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.size() - 1; j++) {
                    comparisonCounter++;
                    if (list.get(j) > list.get(j + 1)) {
                        operationsCounter++;
                        var tmp = list.get(j);
                        list.set(j, list.get(j + 1));
                        list.set(j + 1, tmp);
                    }
                }
            }
        }
    }
}
