package org.example;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EulerTest {


    @Test
    void setUpSmall(){

        Graph eulergraph = new SingleGraph("EG1");


        eulergraph.addNode("A");
        eulergraph.addNode("B");
        eulergraph.addNode("C");

        eulergraph.addEdge("AB", "A", "B");
        eulergraph.addEdge("BC", "B", "C");
        eulergraph.addEdge("CA", "C", "A");

        List<String[]> path = FleuryAlgorithm.fleury(eulergraph);
        assertEquals(3, path.size(), "Eulerkreis sollte 3 Kanten enthalten");
        assertTrue(path.size() == eulergraph.getEdgeCount(), "Pfad sollte alle Kanten enthalten");

//        Graph nonEulergraph = new SingleGraph("NEG1");
//
//
//
//        eulergraph.addNode("A");
//        eulergraph.addNode("B");
//        eulergraph.addNode("C");
//
//        eulergraph.addEdge("AB", "A", "B");
//        eulergraph.addEdge("BC", "B", "C");

    }

    public void testSmallEulergraphF(){
        //List<String[]> path = FleuryAlgorithm.fleury(eulergraph);
    }

    public void testSmallEulergraphH(){
        //asserTrue(H)
    }
    public void testSmallNonEulergraphF(){
        //assertFalse(F)
    }
    public void testSmallNonEulergraphH(){
        //assertFalse(F)
    }

    public void testMediumlEulergraphF(){
        // assertTrue(Fleury)
    }

    public void testMediumlEulergraphH(){
        //asserTrue(H)
    }
    public void testMediumNonEulergraphF(){
        //assertFalse(F)
    }
    public void testMediumNonEulergraphH(){
        //assertFalse(F)
    }
    public void testBiglEulergraphF(){
        // assertTrue(Fleury)
    }

    public void testBiglEulergraphH(){
        //asserTrue(H)
    }
    public void testBigNonEulergraphF(){
        //assertFalse(F)
    }
    public void testBigNonEulergraphH(){
        //assertFalse(F)
    }

}
