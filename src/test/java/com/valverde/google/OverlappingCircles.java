package com.valverde.google;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OverlappingCircles {

    @Test
    void test1() {
        assertTrue(isGroup(Arrays.asList(new Circle(0, 0, 2), new Circle(2, 2, 1))));
        assertTrue(isGroupRecursive(Arrays.asList(new Circle(0, 0, 2), new Circle(2, 2, 1))));
    }

    @Test
    void test2() {
        assertFalse(isGroup(Arrays.asList(new Circle(0, 0, 2), new Circle(2, 3, 1))));
        assertFalse(isGroupRecursive(Arrays.asList(new Circle(0, 0, 2), new Circle(2, 3, 1))));
    }

    @Test
    void test3() {
        assertTrue(isGroup(Arrays.asList(new Circle(0, 0, 2), new Circle(4, 0, 1), new Circle(2, 0, 2))));
        assertTrue(isGroupRecursive(Arrays.asList(new Circle(0, 0, 2), new Circle(4, 0, 1), new Circle(2, 0, 2))));
    }

    @Test
    void test4() {
        assertFalse(isGroup(Arrays.asList(new Circle(0, 0, 2), new Circle(10, 0, 1), new Circle(2, 0, 2), new Circle(11, 0, 2))));
        assertFalse(isGroupRecursive(Arrays.asList(new Circle(0, 0, 2), new Circle(10, 0, 1), new Circle(2, 0, 2), new Circle(11, 0, 2))));
    }

    public boolean isGroupRecursive(List<Circle> circles) {
        Set<Circle> group = new HashSet<>();
        group.add(circles.get(0));
        addRecursive(circles.get(0), circles, group);
        return group.size() == circles.size();
    }

    private void addRecursive(Circle c, List<Circle> notVisited, Set<Circle> group) {
        for (Circle c1 : notVisited) {
            if (!group.contains(c1) && isOverlapping(c, c1)) {
                group.add(c1);
                addRecursive(c1, notVisited, group);
            }
        }
    }

    public boolean isGroup(List<Circle> circles) {
        List<Circle> remainingCircles = new ArrayList<>();
        List<Circle> group = new ArrayList<>();
        group.add(circles.get(0));
        for (int i = 1; i < circles.size(); i++) {
            Circle c = circles.get(i);
            remainingCircles.add(c);
        }
        boolean foundElements;
        do {
            foundElements = false;
            for (Circle cg : new ArrayList<>(group)) {
                Iterator<Circle> citerator = remainingCircles.iterator();
                while (citerator.hasNext()) {
                    Circle cr = citerator.next();
                    if (isOverlapping(cr, cg)) {
                        citerator.remove();
                        group.add(cr);
                        foundElements = true;
                    }
                }
            }
        } while (!remainingCircles.isEmpty() && foundElements);

        return remainingCircles.isEmpty();
    }

    private boolean isOverlapping(Circle c1, Circle c2) {
        int maxDistance = c1.r + c2.r;
        double distance = Math.sqrt(Math.pow(c2.x - c1.x, 2) + Math.pow(c2.y - c1.y, 2));
        return distance <= maxDistance;
    }

    class Circle {
        private final int x;
        private final int y;
        private final int r;

        Circle(int x, int y, int r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        @Override
        public String toString() {
            return "Circle{" +
                    "x=" + x +
                    ", y=" + y +
                    ", r=" + r +
                    '}';
        }
    }
}
