package org.example;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

public class PrimAlgorithm {


    //Graph und startKnoten werden übergeben
    public static Graph pam(Graph graph) {

        long startTime = System.nanoTime();

        Graph graphT = new SingleGraph("Tgraph");//Ergebnisgraph

        Set<String> T = new HashSet<>(); //Alle Knoten, die schon im Gerüst sind

        Map<String, Double> weight = new HashMap<>(); //

        Map<String, String> parent = new HashMap<>(); //speichert Vorgängerknoten


        double totalWeight = 0.0;



        PriorityQueue<String> nodesNotInT = new PriorityQueue<>(Comparator.comparingDouble(weight::get));

        String startNode = graph.nodes().iterator().next().getId(); // Irgendein Startknoten

        //Knoten werden den Graphen, Gewichtsklasse und Elternklasse hinzugefügt
        for (Node node : graph.nodes().toList()) {
            String id = node.getId();
            weight.put(id, Double.POSITIVE_INFINITY); //Knotengewicht ist noch nicht bekannt
            parent.put(id, null); //Eltern knoten auch nicht
            graphT.addNode(id).setAttribute("ui.label", id); // Knoten den Graphen hinzufügen
        }


        weight.put(startNode, 0.0); //Startknoten mit Gewicht 0, wird dann als erster aus der Queue geholt

        nodesNotInT.addAll(weight.keySet()); // Alle knoten werden hinzugefügt

        //Solange es noch Knoten gibt noch nicht in T sind
        while (!nodesNotInT.isEmpty()) {
            String cheapestNodeID = nodesNotInT.poll(); //Knoten mit kleinster Gewichtung wird entnommen
            T.add(cheapestNodeID);// Wird T hinzugefügt
            Node cheapestNode = graph.getNode(cheapestNodeID);

            //durchgehen der NachbarKnoten vom CheapestNode
            for (Edge edge : cheapestNode.edges().toList()) {
                Node neighborNode = edge.getOpposite(cheapestNode);
                String neighborNodeId = neighborNode.getId();

                //prüfen ob dieser schon im Gerüst ist
                if (!T.contains(neighborNodeId)) {
                    double weightOfEdge = edge.getAttribute("weight", Double.class);

                    //prüft ob Kantengewichtung günstiger ist
                    if (weightOfEdge < weight.get(neighborNodeId)) {
                        weight.put(neighborNodeId, weightOfEdge);
                        parent.put(neighborNodeId, cheapestNodeID);

                        //Queue updaten
                        nodesNotInT.remove(neighborNodeId);
                        nodesNotInT.add(neighborNodeId);
                    }
                }
            }

        }
        //prüfe die Verbindung
        for (String nodeID : parent.keySet()) {
            String otherNodeID = parent.get(nodeID);

            if (otherNodeID != null) {
                String edgeId = otherNodeID + "-" + nodeID;

                //Kante holen
                Edge edge = graph.getNode(otherNodeID).getEdgeBetween(graph.getNode(nodeID));

                if (edge != null) {
                    double gewicht = edge.getAttribute("weight", Double.class);
                    totalWeight += gewicht; //gesamtgewicht berechnen

                    Edge e = graphT.addEdge(edgeId, otherNodeID, nodeID, false);
                    e.setAttribute("weight", gewicht);
                    e.setAttribute("ui.label", gewicht);
                }
            }


        }

        //Benötigte Zeit anzeigen
        long endTime = System.nanoTime();
        long durationNs = endTime - startTime;
        double durationSec = durationNs / 1_000_000_000.0;
        System.out.println("Dauer(Prim): " + durationSec + " Sekunden");

        System.out.println(totalWeight);

        //Graphen visualisieren
        graphT.setAttribute("ui.stylesheet",
                "node { text-size: 20px; text-color: black; text-alignment: at-right; } " +
                        "edge { text-size: 16px; text-alignment: along; }"
        );
        graphT.display();

        return graphT;
    }
}