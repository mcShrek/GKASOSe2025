package org.example;


import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrimTest {

    @BeforeAll
    static void init() {
        System.setProperty("org.graphstream.ui", "swing");
    }

    Graph graph1 = GraphGenerator.generateRandomGraph("PrimGraph", 6, 7);
    Graph prim = PrimAlgorithm.pam(graph1);

    //testet ob der Prim die richtige Anzahl an Kanten hat
    @Test
    void testPrimEdgeCount(){
        Graph prim = PrimAlgorithm.pam(graph1);
        assertEquals(graph1.getNodeCount()-1,prim.getEdgeCount());
    }

    //testet, ob es dieselbe Anzahl an Knoten gibt
    @Test
    void testNodeCount(){
         Graph prim = PrimAlgorithm.pam(graph1);
        assertEquals(graph1.getNodeCount(),prim.getNodeCount());
    }

    //testet, ob es isolierte Knoten gibt
    @Test
    void noSingleNodes(){
        for (Node node : prim) {
            assertTrue(node.getDegree() > 0);
        }
    }

    Graph graph2 = GraphGenerator.generateRandomGraph("PrimGraph", 100, 200);
    Graph prim2 = PrimAlgorithm.pam(graph2);

    //testet, ob der Prim die richtige Anzahl an Kanten hat
    @Test
    void testPrimEdgeCount2(){
        Graph prim = PrimAlgorithm.pam(graph2);
        assertEquals(graph2.getNodeCount()-1,prim2.getEdgeCount());
    }
    //testet, ob es dieselbe Anzahl an Knoten gibt
    @Test
    void testNodeCount2(){
        Graph prim = PrimAlgorithm.pam(graph2);
        assertEquals(graph2.getNodeCount(),prim2.getNodeCount());
    }

    //testet, ob es isolierte Knoten gibt
    @Test
    void noSingleNodes2(){
        for (Node node : prim2) {
            assertTrue(node.getDegree() > 0);
        }
    }

}