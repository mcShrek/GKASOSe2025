package org.example;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

public class KruskalAlgorithm {

    public static Graph findMinimumSpanningTree(Graph originalGraph) {
        long startTime = System.nanoTime(); // Startzeitpunkt um Dauer zu berechnen

        Graph mstGraph = new SingleGraph("MST"); // Ergebnisgraph

        // Alle Knoten in Ergebnisgraph einfügen
        for (Node node : originalGraph) {
            Node newNode = mstGraph.addNode(node.getId());
            newNode.setAttribute("ui.label", node.getId());
        }

        //Liste aller Kanten
        List<Edge> allEdges = new ArrayList<>(originalGraph.edges().toList());

        // Liste aller Kanten aufsteigend nach Gewicht sortieren
        allEdges.sort(Comparator.comparingDouble(e -> e.getAttribute("weight", Double.class)));

        List<Edge> mstEdges = new ArrayList<>(); //Liste, die alle benötigten Kanten sammelt

        double totalWeight = 0.0; // Zähler für das Gesamtgewicht aller benötigten Kanten

        for (Edge edge : allEdges) { //Über die sortierten Kanten iterieren
            String startNode = edge.getNode0().getId();
            String targetNode = edge.getNode1().getId();

            if (!createsCycle(mstEdges, startNode, targetNode)) { // neu hinzugefügter Knoten darf keinen Zyklus erzeugen
                mstEdges.add(edge); //Neuen Knoten zu MST hinzufügen
                double weight = edge.getAttribute("weight", Double.class); //Kantengewicht
                totalWeight += weight; //Kantengewicht zu Gesamtgewicht hinzufügen

                String edgeId = edge.getId();
                Edge newEdge = mstGraph.addEdge(edgeId, startNode, targetNode, false); //Neue Kante in Ergebnisgraph erzeugen
                newEdge.setAttribute("weight", weight);
                newEdge.setAttribute("ui.label", weight);
            }

            // Abbruch, wenn maximale Kantenanzahl erreicht
            if (mstEdges.size() == originalGraph.getNodeCount() - 1) {
                break;
            }
        }

        //Gesamtgewicht ausgeben
        System.out.println("Gesamtgewicht des MST:" + totalWeight);

        //Graphen visualisieren
        mstGraph.setAttribute("ui.stylesheet",
                "node { text-size: 20px; text-color: black; text-alignment: at-right; } " +
                        "edge { text-size: 16px; text-alignment: along; }"
        );
        mstGraph.display();


        //Benötigte Zeit anzeigen
        long endTime = System.nanoTime();
        long durationNs = endTime - startTime;
        double durationSec = durationNs / 1_000_000_000.0;
        System.out.println("Dauer(Kruskal): " + durationSec + " Sekunden");

        return mstGraph; //Graph übergeben

    }

    //Hilfsmethode zur Erkennung von Zyklen
    private static boolean createsCycle(List<Edge> currentMSTEdges, String startingNode, String targetedNode) {
        Map<String, Set<String>> neighbors = new HashMap<>();

        for (Edge edge : currentMSTEdges) {
            String startNode = edge.getNode0().getId();
            String targetNode = edge.getNode1().getId();
            neighbors.computeIfAbsent(startNode, k -> new HashSet<>()).add(targetNode);
            neighbors.computeIfAbsent(targetNode, k -> new HashSet<>()).add(startNode);
        }

        return bfs(startingNode, targetedNode, neighbors);
    }

    //Breadth-First-Search um zu überprüfen, ob es bereits einen Weg zwischen Start und Zielknoten gibt
    private static boolean bfs(String start, String target, Map<String, Set<String>> neighbors) {

        if (!neighbors.containsKey(start)) return false;

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(target)) return true;

            for (String neighbor : neighbors.getOrDefault(current, Set.of())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return false;
    }
}