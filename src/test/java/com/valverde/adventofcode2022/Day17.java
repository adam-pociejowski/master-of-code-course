package com.valverde.adventofcode2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.function.Function;

public class Day17 extends AbstractTask {
    int removedFloors;
    final List<Function<Integer, MovingObject>> definitions = Arrays.asList(
            this::minusObject,
            this::plusObject,
            this::lObject,
            this::iObject,
            this::squareObject);

    @BeforeEach
    void setup() {
        removedFloors = 0;
    }

    @Test
    void solution1() {
        final String jets = readStringLines("input17.txt").get(0);
        final List<Boolean[]> savedFloors = new ArrayList<>();
        long index = 0L;
        MovingObject object;
        int simulationCounter = 0;
        while (index < 2022) {
            boolean moving = true;
            object = definitions.get((int)index % definitions.size()).apply(savedFloors.size() + 3);
            while (moving) {
                char jet = jets.charAt(simulationCounter % jets.length());
                simulationCounter++;
                if (jet == '<') {
                    object = moveLeft(object, savedFloors);
                } else {
                    object = moveRight(object, savedFloors);
                }
                int bottomIndexBeforeFall = object.bottomIndex;
                object = fallDown(object, savedFloors);
                if (object.bottomIndex == bottomIndexBeforeFall) {
                    moving = false;
                }
            }
            index++;
        }
        System.out.println("SOLUTION 1: "+savedFloors.size());
    }

    @Test
    void solution2() {
        final String jets = readStringLines("input17.txt").get(0);
        List<Boolean[]> savedFloors = new ArrayList<>();
        Map<String, List<Long>> cycleMap = new HashMap<>();
        Map<Long, Long> floorsSizeMap = new HashMap<>();
        long index = 0L;
        MovingObject object = null;
        int simulationCounter = 0;
        while (index < 100000L) {
            boolean moving = true;
            object = definitions.get((int)index % definitions.size()).apply(savedFloors.size() + 3);
            while (moving) {
                char jet = jets.charAt(simulationCounter % jets.length());
                simulationCounter++;
                if (jet == '<') {
                    object = moveLeft(object, savedFloors);
                } else {
                    object = moveRight(object, savedFloors);
                }
                int bottomIndexBeforeFall = object.bottomIndex;
                object = fallDown(object, savedFloors);
                if (object.bottomIndex == bottomIndexBeforeFall) {
                    floorsSizeMap.put(index, (long)savedFloors.size());
                    moving = false;
                    if (savedFloors.size() > 0) {
                        Pattern pattern = new Pattern(
                                simulationCounter % jets.length(),
                                (int) index % definitions.size(),
                                savedFloors.get(savedFloors.size() - 1));
                        if (cycleMap.containsKey(pattern.toString())) {
                            cycleMap.get(pattern.toString()).add(index);
                        } else {
                            cycleMap.put(pattern.toString(), new ArrayList<>(Collections.singletonList(index)));
                        }
                    }
                }
            }
            index++;
        }
        List<Map.Entry<String, List<Long>>> sortedEntries = cycleMap
                .entrySet()
                .stream()
                .sorted((Map.Entry<String, List<Long>> e1, Map.Entry<String, List<Long>> e2) ->
                        Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .toList();
        Map.Entry<String, List<Long>> entry = sortedEntries.get(0);
        List<Long> values = entry.getValue();
        long cycleSize = values.get(2) - values.get(1);
        long floorsInCycle = floorsSizeMap.get(values.get(2)) - floorsSizeMap.get(values.get(1));
        long iterations = 1000000000000L;
        long iterationsWithoutFirstCycle = iterations - values.get(0);
        long cycles = iterationsWithoutFirstCycle / cycleSize;
        long iterationsAfterCycle = iterationsWithoutFirstCycle % cycleSize;
        long floorsInAllCycles = cycles * floorsInCycle;
        long floorsAfterCycle = floorsSizeMap.get(values.get(1) + iterationsAfterCycle) - floorsSizeMap.get(values.get(1));
        long allFloors = floorsSizeMap.get(values.get(0)) + floorsInAllCycles + floorsAfterCycle - 1;
        System.out.println("SOLUTION 1: "+allFloors);
    }

    private MovingObject fallDown(MovingObject o, List<Boolean[]> savedFloors) {
        if (canMove(o.object, o.bottomIndex - 1, savedFloors)) {
            o.bottomIndex--;
        } else {
            stopObject(o, savedFloors);
            o.object = Collections.emptyList();
        }
        return o;
    }

    private MovingObject moveRight(MovingObject o, List<Boolean[]> savedFloors) {
        for (Boolean[] row : o.object) {
            if (row[row.length - 1]) {
                return o;
            }
        }
        List<Boolean[]> shifted = shiftHorizontally(o, o.object.get(0).length - 1);
        if (canMove(shifted, o.bottomIndex, savedFloors)) {
            o.object = shifted;
        }
        return o;
    }

    private MovingObject moveLeft(MovingObject o, List<Boolean[]> savedFloors) {
        for (Boolean[] row : o.object) {
            if (row[0]) {
                return o;
            }
        }
        List<Boolean[]> shifted = shiftHorizontally(o, 1);
        if (canMove(shifted, o.bottomIndex, savedFloors)) {
            o.object = shifted;
        }
        return o;
    }

    private void stopObject(MovingObject o, List<Boolean[]> savedFloors) {
        for (int i = 0; i < o.object.size(); i++) {
            Boolean[] row = o.object.get(i);
            if (savedFloors.size() <= o.bottomIndex + i) {
                savedFloors.add(row);
            } else {
                for (int j = 0; j < row.length; j++) {
                    if (row[j]) {
                        savedFloors.get(o.bottomIndex + i)[j] = true;
                    }
                }
            }
        }
    }

    private boolean areFloorsCompleted(Boolean[] floor1, Boolean[] floor2) {
        for (int i = 0; i < floor1.length; i++) {
            if (!floor1[i] && !floor2[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean canMove(List<Boolean[]> row, int bottomIndex, List<Boolean[]> savedFloors) {
        if (savedFloors.size() < bottomIndex) {
            return true;
        } else if (bottomIndex < 0) {
            return false;
        }
        for (int i = bottomIndex; i < savedFloors.size(); i++) {
            int objectIndex = i - bottomIndex;
            if (objectIndex < row.size()) {
                Boolean[] objectRow = row.get(objectIndex);
                Boolean[] floorRow = savedFloors.get(i);
                for (int j = 0; j < objectRow.length; j++) {
                    if (objectRow[j] && floorRow[j]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void print(List<Boolean[]> savedFloors, MovingObject o) {
        for (int i = 0; i < o.object.size(); i++) { //object
            printFloor(o.object.get(i));
        }
        for (int i = savedFloors.size(); i < o.bottomIndex; i++) { //empty space
            printFloor(emptyFloor());
        }
        for (int i = savedFloors.size() -1; i >= 0; i--) { //saved floors
            printFloor(savedFloors.get(i));
        }
        System.out.println("---------\n");
    }

    private void printFloor(Boolean[] floor) {
        System.out.print("|");
        for (Boolean b: floor) {
            if (b) {
                System.out.print("#");
            } else {
                System.out.print(".");
            }
        }
        System.out.println("|");
    }

    private List<Boolean[]> shiftHorizontally(MovingObject o, int shift) {
        List<Boolean[]> shiftedList = new ArrayList<>();
        for (Boolean[] row : o.object) {
            Boolean[] shifted = new Boolean[row.length];
            for(int i = 0; i < row.length; i++) {
                shifted[i] = row[(shift + i) % row.length];
            }
            shiftedList.add(shifted);
        }
        return shiftedList;
    }

    private MovingObject minusObject(int bottomIndex) {
        final List<Boolean[]> lines = new ArrayList<>();
        lines.add(new Boolean[]{false, false, true, true, true, true, false});
        return new MovingObject(lines, bottomIndex);
    }

    private MovingObject plusObject(int bottomIndex) {
        final List<Boolean[]> lines = new ArrayList<>();
        lines.add(new Boolean[]{false, false, false, true, false, false, false});
        lines.add(new Boolean[]{false, false, true, true, true, false, false});
        lines.add(new Boolean[]{false, false, false, true, false, false, false});
        return new MovingObject(lines, bottomIndex);
    }

    private MovingObject lObject(int bottomIndex) {
        final List<Boolean[]> lines = new ArrayList<>();
        lines.add(new Boolean[]{false, false, true, true, true, false, false});
        lines.add(new Boolean[]{false, false, false, false, true, false, false});
        lines.add(new Boolean[]{false, false, false, false, true, false, false});
        return new MovingObject(lines, bottomIndex);
    }

    private MovingObject iObject(int bottomIndex) {
        final List<Boolean[]> lines = new ArrayList<>();
        lines.add(new Boolean[]{false, false, true, false, false, false, false});
        lines.add(new Boolean[]{false, false, true, false, false, false, false});
        lines.add(new Boolean[]{false, false, true, false, false, false, false});
        lines.add(new Boolean[]{false, false, true, false, false, false, false});
        return new MovingObject(lines, bottomIndex);
    }

    private MovingObject squareObject(int bottomIndex) {
        final List<Boolean[]> lines = new ArrayList<>();
        lines.add(new Boolean[]{false, false, true, true, false, false, false});
        lines.add(new Boolean[]{false, false, true, true, false, false, false});
        return new MovingObject(lines, bottomIndex);
    }

    private Boolean[] emptyFloor() {
        return new Boolean[] {false, false, false, false, false, false, false};
    }

    class Pattern {
        int jetIndex;
        int pieceIndex;
        Boolean[] topFloor;

        public Pattern(int jetIndex, int pieceIndex, Boolean[] topFloor) {
            this.jetIndex = jetIndex;
            this.pieceIndex = pieceIndex;
            this.topFloor = topFloor;
        }

        @Override
        public String toString() {
            return "Pattern{" +
                    "jetIndex=" + jetIndex +
                    ", pieceIndex=" + pieceIndex +
                    ", topFloor=" + Arrays.toString(topFloor) +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pattern pattern = (Pattern) o;
            return jetIndex == pattern.jetIndex && pieceIndex == pattern.pieceIndex && areArraysEqual(topFloor, pattern.topFloor);
        }

        private boolean areArraysEqual(Boolean[] topFloor, Boolean[] topFloor2) {
            for (int i = 0; i < topFloor.length; i++) {
                if (topFloor[i].booleanValue() != topFloor2[i].booleanValue())
                    return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(jetIndex, pieceIndex);
            result = 31 * result + Arrays.hashCode(topFloor);
            return result;
        }
    }

    class MovingObject {
        List<Boolean[]> object;
        int bottomIndex;

        public MovingObject(List<Boolean[]> object, int bottomIndex) {
            this.object = object;
            this.bottomIndex = bottomIndex;
        }
    }
}
