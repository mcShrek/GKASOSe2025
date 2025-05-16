package org.example;


import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        String dateiPfad = "C:\\Users\\HBW\\Downloads\\examples_gka\\graph02.gka";  // Pfad zur Datei


        Graph graph = GraphReader.dataReader(dateiPfad);

        graph.display();


    }



}