package org.example;

import org.graphstream.graph.Graph;

public class Main {

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");


       // Graph graph = GraphReader.dataReader("C:\\Users\\HBW\\Downloads\\examples_gka\\graph03.gka"); // Graphen einpflegen

        Graph graph = GraphReader.dataReader("C:\\Users\\HBW\\Downloads\\examples_gka\\graph13.gka");

        //   graph.display(); // Graphen anzeigen

//        List<String> pfad = BreadthFirstSearch.bfs(graph, "a", "e"); // Breadth First Algorithmus über Graphen laufen lassen
//
//        if (pfad != null) { // Ergebnisse ausgeben
//            System.out.println("Kürzester Pfad: " + pfad);
//            System.out.println("Kantenanzahl: " + (pfad.size() - 1));
//        }
        PrimAlgorhitm.PAM(graph);
    }
}



