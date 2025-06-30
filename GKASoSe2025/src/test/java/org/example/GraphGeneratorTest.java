package org.example;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
public class GraphGeneratorTest {

    @Test
    void testCreatesSmallRandomGraph() {
        Graph graph = new SingleGraph("SimpleGraph");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");

        graph.addEdge("AB", "A", "B");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("AD", "A", "D");
        graph.addEdge("CD", "C", "D");


        Graph randomGraph = GraphGenerator.generateRandomGraph("RandomGraph", 4,4);

        assertEquals(graph.getEdgeCount(), randomGraph.getEdgeCount());
        assertEquals(graph.getNodeCount(), randomGraph.getNodeCount());
    }

    @Test
    void testCreatesBigRandomGraph() {

        int nodes = 30000;
        int edges = 50000;

        long start = System.currentTimeMillis();
        Graph randomGraph = GraphGenerator.generateRandomGraph("RandomGraph", nodes, edges);
        long duration = System.currentTimeMillis() - start;

        assertEquals(nodes, randomGraph.getNodeCount());
        assertEquals(edges, randomGraph.getEdgeCount());
        assertTrue(duration < 10_000, "Graph generation took too long: " + duration + "ms");
    }

    @Test
    void testEmptyGraph() {
        Graph randomGraph = GraphGenerator.generateRandomGraph("RandomGraph", 0, 0);
        assertEquals(0, randomGraph.getEdgeCount());
        assertEquals(0, randomGraph.getNodeCount());
    }

    @Test
    void testTooFewEdgesThrowsException() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> GraphGenerator.generateRandomGraph("TooFew", 10, 5)
        );
        assertEquals("Zu wenige Kanten", thrown.getMessage());
    }

    @Test
    void testTooManyEdgesThrowsException() {
        long nodes = 5;
        long maxEdges = (nodes * (nodes - 1)) / 2;
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> GraphGenerator.generateRandomGraph("TooMany", nodes, maxEdges + 1)
        );
        assertEquals("Zu viele Kanten", thrown.getMessage());
    }

    @Test
    void testGraphWithMinimumEdges() {
        Graph graph = GraphGenerator.generateRandomGraph("MinEdges", 5, 4);
        assertEquals(5, graph.getNodeCount());
        assertEquals(4, graph.getEdgeCount());
    }

    @Test
    void testNoSelfLoops() {
        Graph graph = GraphGenerator.generateRandomGraph("NoLoops", 10, 15);
        graph.edges().forEach(e -> assertNotEquals(
                e.getNode0().getId(), e.getNode1().getId(),
                "Self-loop detected at edge: " + e.getId())
        );
    }

    @Test
    void testNoDuplicateEdges() {
        Graph graph = GraphGenerator.generateRandomGraph("NoDuplicates", 10, 15);
        Set<String> uniqueEdges = new HashSet<>();
        graph.edges().forEach(edge -> {
            String id = edge.getId();
            assertTrue(uniqueEdges.add(id), "Duplicate edge ID: " + id);
        });
    }

}
