package com.valverde.masterofcode.section13_sorting;

import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public class QuickSortTask extends BaseSortTask {

    @BeforeEach
    void setup() {
        sortable = new QuickSort();
    }

    class QuickSort implements Sortable {
        public void sort(final List<Integer> list) {
            final List<Integer> sortedList = quickSort(list);
            list.clear();
            list.addAll(sortedList);
        }

        private List<Integer> quickSort(final List<Integer> list) {
            if (list.size() <= 1) {
                comparisonCounter++;
                return list;
            }
            int pivotIndex = list.size() - 1;
            int index = 0;
            while (index < pivotIndex) {
                comparisonCounter++;
                if (list.get(index) > list.get(pivotIndex)) {
                    swapWithPivot(list, pivotIndex, index);
                    pivotIndex--;
                } else {
                    index++;
                }
            }
            return merge(
                    quickSort(list.subList(0, pivotIndex)),
                    list.get(pivotIndex),
                    quickSort(list.subList(pivotIndex + 1, list.size()))
            );
        }

        private void swapWithPivot(List<Integer> list, int pivotIndex, int i) {
            operationsCounter++;
            if (pivotIndex - 1 == i) {
                int tmp = list.get(pivotIndex);
                list.set(pivotIndex, list.get(i));
                list.set(i, tmp);
            } else {
                int tmp = list.get(pivotIndex - 1);
                list.set(pivotIndex - 1, list.get(pivotIndex));
                list.set(pivotIndex, list.get(i));
                list.set(i, tmp);
            }
        }

        private List<Integer> merge(final List<Integer> sortedLeftList, final int pivot, final List<Integer> sortedRightList) {
            operationsCounter++;
            final List<Integer> sortedList = new ArrayList<>(sortedLeftList);
            sortedList.add(pivot);
            sortedList.addAll(sortedRightList);
            return sortedList;
        }
    }
}
