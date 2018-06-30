package com.katier.firstStep;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private Graph graph = new Graph(new int[0][0]);
    private final MatrixPanel panelForMatr = new MatrixPanel();
    private final DraggedPanel panel = new DraggedPanel();

    private Main() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initMenu();

        setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(600, 700));
        add(panel, BorderLayout.WEST);

        initMatrix();

        setSize(1200, 700);
        setVisible(true);
    }

    private void initMatrix(){
        JPanel tmp = new JPanel();
        tmp.setPreferredSize(new Dimension(400, 700));
        tmp.setBackground(Color.WHITE);
        tmp.add(panelForMatr);
        add(tmp, BorderLayout.EAST);
    }

    private void initMenu(){
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
        setJMenuBar(jToolBar);
    }

    private void addVertexesInPanel(int vertexes) {
        graph = new Graph(new int[vertexes][vertexes]);
        panel.setGraph(graph);
        panelForMatr.setGraph(graph);
        panel.setEdgeListener((i, j) -> {
            Integer weight = New.ask(1, 99, "Choose weight of edge");
            if (weight == null) return;
            graph.setEggeWeight(i, j, weight);
        });
    }
    public static void main(String[] args) {
        new Main();
    }
}

