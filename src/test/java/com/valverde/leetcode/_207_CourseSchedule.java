package com.valverde.leetcode;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi
first if you want to take course ai.

For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
Return true if you can finish all courses. Otherwise, return false.
 */
public class _207_CourseSchedule {

    @Test
    void test1() {
        assertTrue(canFinish(2, new int[][]{
                new int[] {1, 0}
        }));
    }

    @Test
    void test2() {
        assertFalse(canFinish(2, new int[][]{
                new int[] {1, 0},
                new int[] {0, 1},
        }));
    }

    @Test
    void test3() {
        assertTrue(canFinish(1, new int[][]{}));
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        final Map<Integer, List<Integer>> prerequisitesMap = preparePrerequisitesMap(numCourses, prerequisites);
        final Set<Integer> checked = new HashSet<>();
        for (int i = 0; i < numCourses; i++) {
            boolean completed = completeCourse(prerequisitesMap, checked, i);
            if (!completed) {
                return false;
            }
        }
        return true;
    }

    private boolean completeCourse(Map<Integer, List<Integer>> prerequisitesMap, Set<Integer> checked, int index) {
        if (prerequisitesMap.get(index).isEmpty()) {
            return true;
        } else if (checked.contains(index)) {
            return false;
        }
        checked.add(index);
        Iterator<Integer> iterator = prerequisitesMap.get(index).iterator();
        while (iterator.hasNext()) {
            Integer i = iterator.next();
            boolean canBeCompleted = completeCourse(prerequisitesMap, checked, i);
            checked.add(i);
            if (canBeCompleted) {
                iterator.remove();
            }
        }
        return prerequisitesMap.get(index).isEmpty();
    }

    private Map<Integer, List<Integer>> preparePrerequisitesMap(int numCourses, int[][] prerequisites) {
        final Map<Integer, List<Integer>> prerequisitesMap = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            prerequisitesMap.put(i, new ArrayList<>());
        }
        for (int[] prerequisite : prerequisites) {
            prerequisitesMap.get(prerequisite[0]).add(prerequisite[1]);
        }
        return prerequisitesMap;
    }
}
