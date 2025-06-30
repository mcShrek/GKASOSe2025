package org.example;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;



public class FleuryAlgorithm {

    public static List<String[]> fleury(Graph graph){

        List<String[]> circle = new ArrayList<>();
        Graph gCopy = copyGraph(graph);

        Node start = getStartNode(gCopy);

        getPath(start, gCopy, circle);

        return circle;
    }

    private static void getPath(Node u, Graph g,List<String[]> circle ){
        List<Edge> edges = new ArrayList<>(u.edges().toList()); // aktuelle Kanten

        for (Edge e : edges) {
            if (g.getEdge(e.getId()) == null) continue;

            Node v = e.getOpposite(u);
            if (isValidNextEdge(u, v, g)) {

                g.removeEdge(e.getId()); // sicher über ID
                circle.add(new String[]{u.getId(), v.getId()});
                getPath(v, g, circle);
                break;
                }
            }
        }
    private static boolean isValidNextEdge(Node u, Node v, Graph g){
//        if (u.getDegree() == 1) return true;
//
//        int count1 = countReachable(u, g);
//
//        Edge e = g.getEdge(getEdgeId(u, v));
//        g.removeEdge(e);
//
//        int count2 = countReachable(u, g);
//
//        g.addEdge(getEdgeId(u, v), u, v, false); // wiederherstellen
//
//        return count1 == count2;
        if (u.getDegree() == 1) return true; // nur eine Wahl

        int count1 = countReachable(u, g);

        // Temporär entfernen
        Edge targetEdge = null;
        for (Edge edge : u.edges().toList()) {
            if (edge.getOpposite(u).equals(v)) {
                targetEdge = edge;
                break;
            }
        }

        if (targetEdge == null) return false;

        g.removeEdge(targetEdge);
        int count2 = countReachable(u, g);
        g.addEdge(targetEdge.getId(), u.getId(), v.getId(), false); // wiederherstellen

        return count1 == count2;
    }
    private static int countReachable(Node start, Graph g) {
        Set<Node> visited = new HashSet<>();
        dfs(start, visited);
        return visited.size();
    }

    private static void dfs(Node node, Set<Node> visited) {
        if (visited.contains(node)) return;
        visited.add(node);

        for (Edge edge : node.edges().toList()) {
            Node neighbor = edge.getOpposite(node);
            dfs(neighbor, visited);
        }
    }

    private static Graph copyGraph(Graph graph) {
        Graph copy = new SingleGraph("Copy");
        copy.setStrict(false);

        for (Node n : graph.nodes().toList()) {
            copy.addNode(n.getId());
        }

        for (Edge e : graph.edges().toList()) {
            String u = e.getNode0().getId();
            String v = e.getNode1().getId();
            String id = getEdgeId(e.getNode0(), e.getNode1());
            if (copy.getEdge(id) == null) {
                copy.addEdge(id, u, v, false);
            }
        }

        return copy;
    }
    private static String getEdgeId(Node a, Node b) {

        return a.getId() + "-" + b.getId();
    }

    private static Node getStartNode(Graph gCopy){
        for(Node node : gCopy){
            if(node.getDegree()% 2!= 0){ //falls eulerpfad, muss er an einen ungeraden punkt starten
                return node;
            }
        }
        return gCopy.getNode(0);
    }
    public static void printPath(List<String[]> path) {
        for (String[] step : path) {
            if (step.length == 2) {
                System.out.println(step[0] + " -> " + step[1]);
            }
        }
    }
}
