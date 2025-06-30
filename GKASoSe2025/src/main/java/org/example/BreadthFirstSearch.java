package org.example;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;


public class BreadthFirstSearch {

    /**
     *
     * @param graph eingelesener Graph
     * @param startId Startknoten
     * @param endId Zielknoten
     * @return Liste von Knoten, die den Pfad von Startknoten zu Zielknoten zeigen
     */
    public static List<String> bfs(Graph graph, String startId, String endId) {

        Node start = graph.getNode(startId); //Startknoten
        Node end = graph.getNode(endId); // Zielknoten

        if (start == null || end == null) { //Existenz der übergebenen Knoten prüfen
            System.out.println("Start oder Zielknoten existiert nicht.");
            return null;
        }
        if (start.equals(end)) { //Start- und Endknoten sind identisch
            return List.of(startId);
        }

        Queue<Node> queue = new LinkedList<>(); // Warteschlange für noch nicht besuchte Knoten
        Set<Node> visited = new HashSet<>(); // Sammlung der bereits besuchten Knoten
        Map<Node, Node> parent = new HashMap<>(); // Sammlung der Knoten und ihrer Nachbarknoten. Key: Nachbar; Value: Aktueller Knoten

        queue.add(start); // Startknoten als erstes Element der Queue hinzufügen
        visited.add(start); // Startknoten als besucht markieren

        while (!queue.isEmpty()) { // Loop läuft, bis sich keine Knoten mehr in der Queue befinden
            Node current = queue.poll(); //Aktueller Knoten wird aus der Queue gezogen

            if (current.equals(end)) { //Abbruchbedingung, falls der aktuelle Knoten der Zielknoten ist
                break;
            }

            for (Node neighbor : getNeighbors(current)) { //Iterieren durch die Nachbarn des aktuellen Knotens
                if (!visited.contains(neighbor)) { //Nachbarknoten wird zu queue, visited und parent hinzugefügt, falls noch nicht in visited
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        if (!parent.containsKey(end)) { //Überprüfen, ob Zielknoten gefunden wurde
            System.out.println("Kein Pfad gefunden.");
            return null;
        }

        List<String> path = new LinkedList<>(); // Pfad zum Ziel erstellen
        Node current = end;
        while (current != null) { //Pfad von hinten nach vorne mithilfe der Parent Map rekonstruieren
            path.addFirst(current.getId());
            current = parent.get(current);
        }

        return path; //Pfad übergeben
    }

    // Hilfsmethode: Gibt alle „ausgehenden“ Nachbarn zurück
    private static List<Node> getNeighbors(Node node) {
        List<Node> neighborList = new ArrayList<>(); // Liste von Nachbarn erstellen

        for (int i = 0; i < node.getDegree(); i++) { //Über alle Kanten des untersuchten Knotens iterieren
            Edge edge = node.getEdge(i);
            if (edge.isDirected()) { //1. Fall: gerichtete Kante
                if (edge.getSourceNode().equals(node)) { // Nur ausgehende Kante berücksichtigen
                    neighborList.add(edge.getTargetNode()); //Zielknoten der gerichteten Kante zur Nachbarliste hinzufügen
                }
            } else { //2. Fall: ungerichtete Kante
                neighborList.add(edge.getOpposite(node)); // gegenüberliegenden Knoten zur Nachbarliste hinzufügen
            }
        }

        return neighborList; //Vollständige Nachbarliste übergeben
    }
}
