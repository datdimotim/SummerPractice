package com.katier.firstStep;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private Graph graph = new Graph(new int[0][0]);
    private MatrixPanel panelForMatr = new MatrixPanel();
    private DraggedPanel panel = new DraggedPanel(graph);

    private Main() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.setLayout(null);
        add(panel);
        JMenuBar jToolBar = new JMenuBar();
        JMenu modifyTree = new JMenu("Modify tree");
        JMenuItem newTree = new JMenuItem("New Tree");
        JMenuItem addEdges = new JMenuItem("Add Edges");
        JMenuItem moveVert = new JMenuItem("Move Vertexes");
        newTree.addActionListener(actionEvent -> {
            Integer k = New.ask(2, 12, "Choose number of vertexes");
            if (k == null) return;
            int vertexes = k;
            addVertexesInPanel(vertexes);
        });
        moveVert.addActionListener(actionEvent -> panel.fixButtons(false));
        addEdges.addActionListener(actionEvent -> panel.fixButtons(true));
        JMenu solvetion = new JMenu("Solution");
        JMenuItem solve = new JMenuItem("Solve");
        JMenuItem next = new JMenuItem("Next");
        JMenuItem back = new JMenuItem("Back");
        solve.addActionListener(actionEvent -> {
            //floyd-warshall
        });
        next.addActionListener(actionEvent -> {
            //next in Array
        });
        back.addActionListener(actionEvent -> {
            //back in Array
        });
        solvetion.add(solve);
        solvetion.add(next);
        solvetion.add(back);
        jToolBar.add(modifyTree);
        jToolBar.add(solvetion);
        modifyTree.add(newTree);
        modifyTree.add(addEdges);
        modifyTree.add(moveVert);
        this.setJMenuBar(jToolBar);
        setLayout(new BorderLayout());
        JPanel tmp = new JPanel();
        tmp.setPreferredSize(new Dimension(400, 700));
        tmp.setBackground(Color.WHITE);
        tmp.add(panelForMatr);
        add(tmp, BorderLayout.EAST);
        setSize(1200, 700);
        setVisible(true);

    }


    private void addVertexesInPanel(int vertexes) {
        remove(panel);
        graph = new Graph(new int[vertexes][vertexes]);
        panel = new DraggedPanel(graph);
        panelForMatr.newGraph(graph);
        panel.setPreferredSize(new Dimension(600, 700));
        add(panel, BorderLayout.WEST);
        panel.addEdgeAddListener((i, j) -> {
            Integer weight = New.ask(1, 10000, "Choose weight of edge");
            if (weight == null) return;
            graph.setEggeWeight(i, j, weight);
            //labels[i][j].setText(weight.toString());
        });
    }
    public static void main(String[] args) {
        new Main();
    }
}

