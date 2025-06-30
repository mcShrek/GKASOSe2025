package org.example;



import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComparingTest {


    @BeforeAll
    static void init() {
        System.setProperty("org.graphstream.ui", "swing");
    }

    @Test
    void testAlgorithmsOnSmallGraph() {
        Graph graph = GraphGenerator.generateRandomGraph("SmallGraph", 20, 20);
        Graph mstKruskal = KruskalAlgorithm.findMinimumSpanningTree(graph);
        Graph mstPrim = PrimAlgorithm.pam(graph);

        assertEquals(mstKruskal.getEdgeCount(), mstPrim.getEdgeCount(), 0.01);
        assertEquals(calculateTotalWeight(mstKruskal), calculateTotalWeight(mstPrim), 0.01);
    }
    @Test
    void testAlgorithmsOnMediumGraph() {
        Graph graph = GraphGenerator.generateRandomGraph("MediumGraph", 20000, 22000);
        Graph mstKruskal = KruskalAlgorithm.findMinimumSpanningTree(graph);
        Graph mstPrim = PrimAlgorithm.pam(graph);

        assertEquals(mstKruskal.getEdgeCount(), mstPrim.getEdgeCount(), 0.01);
        assertEquals(calculateTotalWeight(mstKruskal), calculateTotalWeight(mstPrim), 0.01);
    }
//    @Test
//    void testAlgorithmsOnBigGraph() {
//        Graph graph = GraphGenerator.generateRandomGraph("MediumGraph", 220000, 20000);
//        Graph mstKruskal = KruskalAlgorithm.findMinimumSpanningTree(graph);
//        Graph mstPrim = PrimAlgorithm.pam(graph);
//
//        assertEquals(mstKruskal.getEdgeCount(), mstPrim.getEdgeCount(), 0.01);
//        assertEquals(calculateTotalWeight(mstKruskal), calculateTotalWeight(mstPrim), 0.01);
//    }



    private double calculateTotalWeight(Graph graph) {
        double total = 0.0;
        for (Edge edge : graph.edges().toList()) {
            total += edge.getAttribute("weight", Double.class);
        }
        return total;
    }
}
