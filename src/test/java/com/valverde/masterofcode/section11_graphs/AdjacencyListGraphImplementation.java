package com.valverde.masterofcode.section11_graphs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraphImplementation {

    @Test
    public void createGraph() {
        final AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex("0");
        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");
        graph.addVertex("5");
        graph.addVertex("6");
        graph.addEdge("0", "1");
        graph.addEdge("0", "2");
        graph.addEdge("1", "2");
        graph.addEdge("1", "3");
        graph.addEdge("2", "4");
        graph.addEdge("3", "4");
        graph.addEdge("4", "5");
        graph.addEdge("5", "6");
        graph.showConnections();
    }

    static class AdjacencyListGraph {
        final Map<String, List<String>> graphMap = new HashMap<>();

        void addVertex(final String vertex) {
            if (!graphMap.containsKey(vertex)) {
                graphMap.put(vertex, new ArrayList<>());
            }
        }

        void addEdge(final String vertex1, final String vertex2) {
            if (graphMap.containsKey(vertex1) && graphMap.containsKey(vertex2)) {
                graphMap.get(vertex1).add(vertex2);
                graphMap.get(vertex2).add(vertex1);
            }
        }

        void showConnections() {
            graphMap.forEach((key, value) -> {
                System.out.printf("[%s] -> ", key);
                value.forEach((String v) -> System.out.printf("| %s ", v));
                System.out.println("|");
            });
        }
    }
}
