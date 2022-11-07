package com.valverde.masterofcode.section13_sorting;

import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public class InsertionSortTask extends BaseSortTask {

    @BeforeEach
    void setup() {
        sortable = new InsertionSort();
    }

    class InsertionSort implements Sortable {
        public void sort(List<Integer> list) {
            final List<Integer> sortedList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (sortedList.isEmpty()) {
                    comparisonCounter++;
                    operationsCounter++;
                    sortedList.add(list.get(i));
                } else {
                    boolean addedInsideList = false;
                    for (int j = 0; j < sortedList.size(); j++) {
                        comparisonCounter++;
                        if (list.get(i) < sortedList.get(j)) {
                            operationsCounter++;
                            sortedList.add(j, list.get(i));
                            addedInsideList = true;
                            break;
                        }
                    }
                    comparisonCounter++;
                    if (!addedInsideList) {
                        operationsCounter++;
                        sortedList.add(list.get(i));
                    }
                }
            }
            list.clear();
            list.addAll(sortedList);
        }
    }
}
