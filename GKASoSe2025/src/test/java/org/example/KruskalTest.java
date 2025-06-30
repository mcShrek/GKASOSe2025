package org.example;


import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KruskalTest {

    @BeforeAll
    static void init() {
        System.setProperty("org.graphstream.ui", "swing");
    }

    @Test
    void testCorrectMST() {//Testet anhand eines simplen Graphen, ob der erzeugte Graph die richtige Anzahl an Kanten hat
        Graph graph = new SingleGraph("TestGraph");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("AB", "A", "B").setAttribute("weight", 1.0);
        graph.addEdge("BC", "B", "C").setAttribute("weight", 2.0);
        graph.addEdge("AC", "A", "C").setAttribute("weight", 3.0);

        Graph mst = KruskalAlgorithm.findMinimumSpanningTree(graph);
        assertEquals(2, mst.getEdgeCount());
    }

    @Test
    void testMSTEdgeCount() {//Testet anhand eines randomisierten Graphen, ob der erzeugte Graph die richtige Anzahl an Kanten hat
        Graph graph = GraphGenerator.generateRandomGraph("RandomGraph", 10, 20);
        Graph mst = KruskalAlgorithm.findMinimumSpanningTree(graph);

        assertEquals(9,mst.getEdgeCount());
    }

    @Test
    void testTotalWeightOfMST() { //Testet, ob der erzeugte Graph das richtige Gesamtgewicht für den erzeugte Graphen berechnet
        Graph graph = new SingleGraph("TestGraph");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("AB", "A", "B").setAttribute("weight", 1.0);
        graph.addEdge("BC", "B", "C").setAttribute("weight", 2.0);
        graph.addEdge("AC", "A", "C").setAttribute("weight", 3.0);

        Graph mst = KruskalAlgorithm.findMinimumSpanningTree(graph);

        double totalWeight = 0.0;
        for (Edge edge : mst.edges().toList()) {
            totalWeight += edge.getAttribute("weight", Double.class);
        }
        assertEquals(3.0, totalWeight);
    }
    @Test
    void testWorksWithCycle() { //Testet, ob der erzeugte Graph das richtige Gesamtgewicht für den erzeugte Graphen berechnet
        Graph graph = new SingleGraph("TestGraph");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("AB", "A", "B").setAttribute("weight", 1.0);
        graph.addEdge("BC", "B", "C").setAttribute("weight", 2.0);
        graph.addEdge("CA", "C", "A").setAttribute("weight", 3.0);

        Graph mst = KruskalAlgorithm.findMinimumSpanningTree(graph);

        assertEquals(2, mst.getEdgeCount());
    }

    @Test
    void testEmptyGraph() { //Testet, ob der Algorithmus auch funktioniert, wenn der übergebene Graph leer ist
        Graph graph = new SingleGraph("EmptyGraph");

        Graph mst = KruskalAlgorithm.findMinimumSpanningTree(graph);

        assertEquals(0, mst.getEdgeCount());

        double totalWeight = 0.0;
        for (Edge edge : mst.edges().toList()) {
            totalWeight += edge.getAttribute("weight", Double.class);
        }
        assertEquals(0.0, totalWeight);
    }

    @Test
    void testDisconnectedGraph() {
        Graph graph = new SingleGraph("DisconnectedGraph");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");

        graph.addEdge("AB", "A", "B").setAttribute("weight", 1.0);
        graph.addEdge("BC", "B", "C").setAttribute("weight", 2.0);
        graph.addEdge("AC", "A", "C").setAttribute("weight", 3.0);
        graph.addEdge("DE", "D", "E").setAttribute("weight", 1.0);
        graph.addEdge("EF", "E", "F").setAttribute("weight", 2.0);
        graph.addEdge("DF", "D", "F").setAttribute("weight", 3.0);

        Graph mst = KruskalAlgorithm.findMinimumSpanningTree(graph);

        assertEquals(7, mst.getNodeCount());
        assertEquals(4, mst.getEdgeCount());
        assertNotNull(mst.getNode("G"));


    }
    @Test
    void testSingleNode() {

        Graph graph = new SingleGraph("SingleNodeGraph");

        graph.addNode("A");

        Graph mst = KruskalAlgorithm.findMinimumSpanningTree(graph);

        assertEquals(1, mst.getNodeCount());
        assertEquals(0, mst.getEdgeCount());
    }

//    @Test
//    void testPerformanceWithBigGraph() {
//        Graph g2 = GraphGenerator.generateRandomGraph("Graph2", 50000, 52000);
//        Graph mst = KruskalAlgorithm.findMinimumSpanningTree(g2);
//
//        assertEquals(49999, mst.getEdgeCount());
//    }


}