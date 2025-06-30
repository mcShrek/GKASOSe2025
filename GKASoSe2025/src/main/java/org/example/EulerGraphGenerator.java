package org.example;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

public class EulerGraphGenerator {


        public static Graph generateRandomGraph(String graphId, long numNodes, long numEdges) {
            // Bedingungen prüfen
            if (numNodes - 1 > numEdges) {
                throw new IllegalArgumentException("Zu wenige Kanten");
            }
            if (numEdges > (numNodes * (numNodes - 1)) / 2) {
                throw new IllegalArgumentException("Zu viele Kanten");
            }
            if (numNodes < 3) {
                throw new IllegalArgumentException("Mindestens 3 Knoten");
            }
            if (numEdges % 2 != 0){
                throw new IllegalArgumentException("Die Kantenanzahl muss gerade sein");
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
                List<Node> oddDegreeNodes = new ArrayList<>();
                for (Node node : graph) {
                    if (node.getDegree() % 2 != 0) {
                        oddDegreeNodes.add(node);
                    }
                }


                Node u;
                Node v;
                if (oddDegreeNodes.size() >= 2) {
                    // Füge gezielt zwei Knoten mit ungeradem Grad zusammen
                    u = oddDegreeNodes.get(rand.nextInt(oddDegreeNodes.size()));
                    do {
                        v = oddDegreeNodes.get(rand.nextInt(oddDegreeNodes.size()));
                    } while (u == v || graph.getEdge(getEdgeId(u, v)) != null);
                } else {
                    // Füge zufällige gültige neue Kante ein
                    do {
                        u = graph.getNode(rand.nextInt((int)numNodes));
                        v = graph.getNode(rand.nextInt((int) numNodes));
                    } while (u == v || graph.getEdge(getEdgeId(u, v)) != null);
                }

                String edgeId = getEdgeId(u, v);
                Edge edge = graph.addEdge(edgeId, u, v, false);
                edge.setAttribute("weight", generateRandomWeight(rand));
                existingEdges.add(edgeId);


            }

            return graph;
        }

        private static double generateRandomWeight(Random rand) {

            return Math.round((rand.nextDouble() * 1000 + 1) * 10.0) / 10.0;
        }
        private static String getEdgeId(Node a, Node b) {
        int id1 = Integer.parseInt(a.getId());
        int id2 = Integer.parseInt(b.getId());
        return Math.min(id1, id2) + "-" + Math.max(id1, id2);
        }
    }

