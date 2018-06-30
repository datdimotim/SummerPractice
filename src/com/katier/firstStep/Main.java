package com.katier.firstStep;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private Graph graph = new Graph(new int[0][0]);
    private JPanel panelForMatr = new JPanel();
    private JLabel[][] labels;
    private DraggedPanel panel = new DraggedPanel(graph);

    Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        moveVert.addActionListener(actionEvent -> {
            panel.fixButtons(false);
        });
        addEdges.addActionListener(actionEvent -> {
            panel.fixButtons(true);
        });
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
        panelForMatr.setBackground(Color.WHITE);
        panelForMatr.setPreferredSize(new Dimension(600, 700));
        panelForMatr.setLayout(new GridLayout(2, 2));
        JPanel tmp = new JPanel();
        tmp.setPreferredSize(new Dimension(400, 700));
        tmp.setBackground(Color.WHITE);
        tmp.add(panelForMatr);
        panelForMatr.setBackground(Color.WHITE);
        add(tmp, BorderLayout.EAST);
        setSize(1200, 700);
        setVisible(true);

    }

    private void setMatr(int kol) {
        labels = new JLabel[kol][kol];
        panelForMatr.removeAll();
        panelForMatr.setPreferredSize(new Dimension(30 * (kol + 1), 30 * (kol + 1)));
        panelForMatr.setLayout(new GridLayout(kol + 1, kol + 1));
        panelForMatr.add(new JLabel(""));
        for (int i = 0; i < kol; i++) {
            char[] c = {(char) (i + 'A')};
            JLabel l = new JLabel(new String(c));
            l.setVerticalAlignment(SwingConstants.CENTER);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            panelForMatr.add(l);
        }
        for (int i = 0; i < kol; i++) {

            char[] c = {(char) (i + 'A')};
            JLabel k = new JLabel(new String(c));
            k.setVerticalAlignment(SwingConstants.CENTER);
            k.setHorizontalAlignment(SwingConstants.CENTER);
            panelForMatr.add(k);

            for (int j = 0; j < kol; j++) {
                JLabel l = new JLabel("X");
                l.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                l.setVerticalAlignment(SwingConstants.CENTER);
                l.setHorizontalAlignment(SwingConstants.CENTER);
                labels[i][j] = l;
                panelForMatr.add(l);
            }
        }
    }

    private void addVertexesInPanel(int vertexes) {
        remove(panel);
        setMatr(vertexes);
        graph = new Graph(new int[vertexes][vertexes]);
        panel = new DraggedPanel(graph);
        panel.setPreferredSize(new Dimension(600, 700));
        add(panel, BorderLayout.WEST);
        panel.addEdgeAddListener((i, j) -> {
            Integer weight = New.ask(1, 10000, "Choose weight of edge");
            if (weight == null) return;
            graph.setEggeWeight(i, j, weight);
            labels[i][j].setText(weight.toString());
        });
    }

    public static void main(String[] args) {
        new Main();
    }
}

