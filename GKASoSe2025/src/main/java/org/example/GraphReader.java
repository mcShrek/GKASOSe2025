package org.example;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphReader {






    public static Graph dataReader(String dateiPfad){
        Graph graph = new SingleGraph("graph"); // erstellt neuen Graphen

        // Stylesheet für die Visualisierung
        graph.setAttribute("ui.stylesheet",
                "node { text-size: 14px; text-color: black; text-alignment: at-right; } " +
                        "edge { text-size: 12px; text-alignment: along; }"
        );
         //liest zeilenweise die datei und übergibt sie an Graphreader mit graphen und gibt den veränderten graphen zurück
        try (BufferedReader reader = new BufferedReader(new FileReader(dateiPfad))) {
            String line;
            while ((line = reader.readLine()) != null) {
                graphReader(line, graph);
            }
        } catch (IOException e) {
            System.out.println("Lesen der Datei hat icht funktioniert: " + e.getMessage());
        }
        return graph;
    }




    public static void graphReader(String line, Graph graph){ // braucht line um diese weiterzuverarbeiten und Graph um zuändern


        Pattern pattern = Pattern.compile(
                "^\\s*([\\wäöüÄÖÜß\\-]+)" + //erste zeile muss Buchstabe, Ziffer oder zeichen bestehen
                        "(?:\\s*(--|->)\\s*([\\wäöüÄÖÜß\\-]+))?" + // dritte und zweite Gruppe zusammen aber Optional.
                        "(?:\\(([^)]+)\\))?\\s*" +
                        "(?::\\s*(\\d+(?:\\.\\d+)?))?" + // Optional gewicht des Kantens
                        "\\s*;?\\s*$"
        );
        Matcher matcher = pattern.matcher(line); //prüft ob der regex passt

        //fehlermeldung falls ungültiger regex
        if(!matcher.matches()){
            System.out.println( line + " ist im ungültigen Format");
            return;
        }




        //deklariert die Teile der Zeile
        String node1 = matcher.group(1);
        String direction = matcher.group(2);
        String node2 = matcher.group(3);
        String edgeLabel = matcher.group(4);
        String weight = matcher.group(5);

        //prüft ob node 1 existiert, falls nicht wird ein neuer Knoten hinzugeügt
        if (graph.getNode(node1) == null) {
            graph.addNode(node1).setAttribute("ui.label", node1);
        }
        //falls nur ein einzelner knoten wird der knoten optional beschriftet und
        if (direction == null || node2 == null) {
            if (edgeLabel != null) {
                Node node = graph.getNode(node1);
                if(node != null){
                    node.setAttribute("ui.label", edgeLabel);
                }
            }
            return;
        }

        // zweiter Knoten wird hinzugefügt
        if (graph.getNode(node2) == null) {
            graph.addNode(node2).setAttribute("ui.label", node2);
        }


        boolean whichGraph = direction.equals("->"); //prüft ob gerichtet oder nicht
        String edgeNum = node1+ direction + node2; // erstellt eine eindeutige nummer

        // fügt gerichteten kanten hinzu
        if(whichGraph) {

            if (graph.getEdge(edgeNum) == null) {

                graph.addEdge(edgeNum, node1, node2, true);

            }
        } else  {//fügt ungerichten kannten hinzu
            if (!edgeExists(graph, node1, node2)) {
                graph.addEdge(edgeNum, node1, node2, false);
            } else{// geht zurüch falls ein ungerichteter kannten schon existiert
                return;
            }
        }
        //beschriftet das gewicht oder label für kante
        Edge edge = graph.getEdge(edgeNum);
        if(edge!=null) {
            if (edgeLabel != null) {
                edge.setAttribute("ui.label", edgeLabel);
            }
            if (weight != null) {
                edge.setAttribute("ui.label", weight);
            }
        }
    }
     // prüft ob kante bereits existiert
    public static boolean edgeExists(Graph graph, String node1, String node2) {

        return graph.getEdge(node1 + "--" + node2) != null ||
                graph.getEdge(node2 + "--" + node1) != null;
    }
}

