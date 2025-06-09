package org.example;

import org.graphstream.graph.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphReaderTest {

    // test ob Visualisierung bei gerichteten Kannten funktioniert
    private static final String filePath = "C:\\Users\\HBW\\Downloads\\examples_gka\\graph01.gka";

    @Test
    void testNodeCount() { // testet die Anzahl der Knoten in der ersten Datei, auch einzelne Knoten werden erzeugt
        Graph graph = GraphReader.dataReader(filePath);
        assertEquals(14, graph.getNodeCount());
    }

    @Test
    void testEdgeCount() { // testet die Anzahl an Kanten bei gerichteten Graphen
        Graph graph = GraphReader.dataReader(filePath);
        assertEquals(39, graph.getEdgeCount());
    }

    @Test
    void testSelfLoopExists() { // testet ob Schleifen existieren
        Graph graph = GraphReader.dataReader(filePath);
        assertNotNull(graph.getEdge("g->g"));

    }


    private static final String filePath2 = "C:\\Users\\HBW\\Downloads\\examples_gka\\graph02.gka";


    @Test
    void testNodeCountUndirected() { // testet die Anzahl der Knoten bei Ungerichteten Kante
        Graph graph = GraphReader.dataReader(filePath2);
        assertEquals(11, graph.getNodeCount());
    }

    @Test
    void testEdgeCountUndirected() {// testet die Anzahl an Kanten bei Undirekten Kanten
        Graph graph = GraphReader.dataReader(filePath2);
        assertEquals(34, graph.getEdgeCount());
    }

    @Test
    void testSelfLoopExistsUndirected() { // testet ob Schleifen existieren
        Graph graph = GraphReader.dataReader(filePath2);
        assertNotNull(graph.getEdge("e--e"));

    }


    @Test
    void testUndirectedEdges() { // testet auf existens einer bestimmten Kante
        Graph graph = GraphReader.dataReader(filePath2);
        Edge edge = graph.getEdge("a--b");
        if (edge == null) {
            edge = graph.getEdge("b--a");
        }
        assertNotNull(edge, "Ungerichtete Kante zwischen a und b sollte existieren.");
        assertFalse(edge.isDirected(), "Die Kante sollte ungerichtet sein.");
    }

    private static final String filePath3 = "C:\\Users\\HBW\\Downloads\\examples_gka\\graph03.gka";

    @Test
    void testUndirectedEdgeWithWeight() {// testen ob das Gewicht bei ungerichteten Kanten richtig übermittel wird
        Graph graph = GraphReader.dataReader(filePath3);
        Edge edge = graph.getEdge("Hamburg--Walsrode");
        if (edge == null) edge = graph.getEdge("Walsrode--Hamburg"); // falls umgekehrt gespeichert
        assertNotNull(edge, "Kante C--D sollte existieren");
        assertEquals(101, Integer.parseInt((String) edge.getAttribute("ui.label")));

    }

    private static final String filePath4 = "C:\\Users\\HBW\\Downloads\\examples_gka\\graph06.gka";

    @Test
    void testDirectedEdgeWithWeight() { // testen ob das Label bei gerichteten Kanten richtig übermittel wird
        Graph graph = GraphReader.dataReader(filePath4);
        Edge edge = graph.getEdge("1->5");
        assertNotNull(edge, "Kante 1 -> 5 sollte existieren");
        assertEquals("a", edge.getAttribute("ui.label"));

    }
}
