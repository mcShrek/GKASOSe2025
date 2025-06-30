package org.example;

import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BFSTest {

    @Test
    void testFindShortestPath() { //Kürzesten Weg finden
        Graph graph = GraphReader.dataReader("/Users/ben/Documents/HAW/S3/GKA/examples_gka/graph07.gka");

        List<String> path = BreadthFirstSearch.bfs(graph, "v1", "v8");
        assertNotNull(path);
        assertEquals(3, path.size());
        assertEquals(List.of("v1", "v7", "v8"), path);
    }
    @Test
    void testNullIfNoPath() { // Kein Pfad zwischen Start und Endknoten
        Graph graph = GraphReader.dataReader("/Users/ben/Documents/HAW/S3/GKA/examples_gka/graph01.gka");
        List<String> path = BreadthFirstSearch.bfs(graph, "a", "m");
        assertNull(path);

    }
    @Test
    void testNullIfNoNodes() { // Einer der beiden übergebenen Knoten existiert nicht
        Graph graph = GraphReader.dataReader("/Users/ben/Documents/HAW/S3/GKA/examples_gka/graph02.gka");
        List<String> path = BreadthFirstSearch.bfs(graph, "a", "m");
        assertNull(path);
    }
    @Test
    void testSingleNodeIfSame() { // Start und Zielknoten sind identisch
        Graph graph = GraphReader.dataReader("/Users/ben/Documents/HAW/S3/GKA/examples_gka/graph01.gka");
        List<String> path = BreadthFirstSearch.bfs(graph, "a", "a");
        assertNotNull(path);
        assertEquals(1, path.size());
        assertEquals(List.of("a"), path);
    }

    @Test
    void testNullIfWrongDirection() { // Gerichtete Kante in die falsche Richtung
        Graph graph = GraphReader.dataReader("/Users/ben/Documents/HAW/S3/GKA/examples_gka/graph06.gka");
        List<String> path = BreadthFirstSearch.bfs(graph, "12", "11");
        assertNull(path);
    }
    @Test
    void testLongPath() { // Langer Pfad zwischen Start und Zielknoten
        Graph graph = GraphReader.dataReader("/Users/ben/Documents/HAW/S3/GKA/examples_gka/graph14.gka");
        List<String> path = BreadthFirstSearch.bfs(graph, "1", "1000");
        assertNotNull(path);
        assertEquals(1000, path.size());
    }

    @Test
    void testWorksWithCycle() { // Zyklus im Pfad
        Graph graph = GraphReader.dataReader("/Users/ben/Documents/HAW/S3/GKA/examples_gka/graph15.gka");
        List<String> path1 = BreadthFirstSearch.bfs(graph, "A", "J");
        List<String> path2 = BreadthFirstSearch.bfs(graph, "A", "A");
        assertNotNull(path1);
        assertNotNull(path2);
        assertEquals(10, path1.size());
        assertEquals(1, path2.size());
    }

}
