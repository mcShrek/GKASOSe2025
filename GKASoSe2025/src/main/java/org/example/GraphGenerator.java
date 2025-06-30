package org.example;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GraphGenerator {


    public static Graph generateRandomGraph(String graphId, long numNodes, long numEdges) {
        // Bedingungen prüfen
        if (numNodes - 1 > numEdges) {
            throw new IllegalArgumentException("Zu wenige Kanten");
        }
        if (numEdges > (numNodes * (numNodes - 1)) / 2) {
            throw new IllegalArgumentException("Zu viele Kanten");
        }

        Graph graph = new SingleGraph(graphId);
        graph.setStrict(false);

        // Knoten hinzufügen
        for (long i = 0; i < numNodes; i++) {
            Node node = graph.addNode(String.valueOf(i));
            node.setAttribute("ui.label", node.getId());
        }

        Random rand = new Random();
        Set<String> existingEdges = new HashSet<>();

        // Erst numNodes-1 Kanten für Konnektivität
        for (long i = 0; i < numNodes - 1; i++) {
            String startNode = String.valueOf(i);
            String endNode = String.valueOf(i + 1);
            String edgeId = startNode + "-" + endNode;

            double weight = generateRandomWeight(rand);
            Edge edge = graph.addEdge(edgeId, startNode, endNode, false);
            edge.setAttribute("weight", weight);
            edge.setAttribute("ui.label", weight);
            existingEdges.add(edgeId);
        }

        // Weitere zufällige Kanten bis numEdges
        while (graph.getEdgeCount() < numEdges) {
            int startNode = rand.nextInt((int) numNodes);  // cast zu int
            int endNode = rand.nextInt((int) numNodes);
            if (startNode == endNode) continue;

            String edgeId = Math.min(startNode, endNode) + "-" + Math.max(startNode, endNode);
            if (existingEdges.contains(edgeId)) continue;

            double weight = generateRandomWeight(rand);
            Edge edge = graph.addEdge(edgeId, String.valueOf(startNode), String.valueOf(endNode), false);
            edge.setAttribute("weight", weight);
            edge.setAttribute("ui.label", weight);
            existingEdges.add(edgeId);
        }

        return graph;
    }

    private static double generateRandomWeight(Random rand) {

        return Math.round((rand.nextDouble() * 1000 + 1) * 10.0) / 10.0;
    }
}