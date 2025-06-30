
package org.example;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        //Graph graph = GraphReader.dataReader("/Users/ben/Documents/HAW/S3/GKA/examples_gka/graph13.gka"); // Graphen einpflegen
        //graph.display(); // Graphen anzeigen

        /*
        List<String> pfad = BreadthFirstSearch.bfs(graph, "A", "J"); // Breadth First Algorithmus über Graphen laufen lassen

        if (pfad != null) { // Ergebnisse ausgeben
            System.out.println("Kürzester Pfad: " + pfad);
            System.out.println("Kantenanzahl: " + (pfad.size() - 1));
        }
        */


        Graph g0 = GraphGenerator.generateRandomGraph("Graph0", 20000, 20000); // Graph mit Kruskal-Berechnungsdauer 44 Sekunden
        //PrimAlgorithm.pam(g0);
        //KruskalAlgorithm.findMinimumSpanningTree(g0);

        Graph g1 = GraphGenerator.generateRandomGraph("Graph1", 25000, 30000); // Graph mit Kruskal-Berechnungsdauer 2 min 40 Sekunden
        //PrimAlgorithm.pam(g1);
        //KruskalAlgorithm.findMinimumSpanningTree(g1);

        Graph g2 = GraphGenerator.generateRandomGraph("Graph2", 50000, 49999); // Graph mit Kruskal-Berechnungsdauer 9 min 25 Sekunden
        //KruskalAlgorithm.findMinimumSpanningTree(g2);
        //PrimAlgorithm.pam(g2);

        Graph g3 = GraphGenerator.generateRandomGraph("Graph3", 20, 50); // Kleiner Graph zum schnellen Testen
        //g3.display();
       // PrimAlgorithm.pam(g3);
       // KruskalAlgorithm.findMinimumSpanningTree(g3);

        Graph g4= GraphReader.dataReader("C:\\Users\\HBW\\Downloads\\examples_gka3\\graph_e3.gka");
        g4.display();
        FleuryAlgorithm.printPath(FleuryAlgorithm.fleury(g4));
    }
}
