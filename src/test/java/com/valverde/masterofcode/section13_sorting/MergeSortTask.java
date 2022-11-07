package com.valverde.masterofcode.section13_sorting;

import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public class MergeSortTask extends BaseSortTask {

    @BeforeEach
    void setup() {
        sortable = new MergeSort();
    }

    class MergeSort implements Sortable {
        public void sort(final List<Integer> list) {
            final List<Integer> sorted = mergeSort(list);
            list.clear();
            list.addAll(sorted);
        }

        private List<Integer> mergeSort(final List<Integer> list) {
            if (list.size() <= 1) {
                comparisonCounter++;
                return list;
            }
            return merge(
                mergeSort(list.subList(0, list.size()/2)),
                mergeSort(list.subList(list.size()/2, list.size()))
            );
        }

        private List<Integer> merge(final List<Integer> sortedList1, final List<Integer> sortedList2) {
            final List<Integer> sortedList = new ArrayList<>();
            int index1 = 0;
            int index2 = 0;
            while (index1 < sortedList1.size() && index2 < sortedList2.size()) {
                comparisonCounter++;
                operationsCounter++;
                if (sortedList1.get(index1) < sortedList2.get(index2)) {
                    operationsCounter++;
                    sortedList.add(sortedList1.get(index1));
                    index1++;
                } else {
                    sortedList.add(sortedList2.get(index2));
                    index2++;
                }
            }
            comparisonCounter++;
            operationsCounter++;
            if (index1 == sortedList1.size()) {
                sortedList.addAll(sortedList2.subList(index2, sortedList2.size()));
            } else {
                sortedList.addAll(sortedList1.subList(index1, sortedList1.size()));
            }
            return sortedList;
        }
    }

}
