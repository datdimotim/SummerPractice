package com.katier.firstStep;

import javax.swing.*;
import java.awt.*;

public class MatrixPanel extends JPanel {
    private JLabel[][] labels;
    Graph graph;
    MatrixPanel(){
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 700));
        setLayout(new GridLayout(2, 2));
        setBackground(Color.WHITE);
    }
    public void newGraph(Graph graph) {
        this.graph=graph;
        graph.addChangeListeners(v->onUpdate());
        int kol= graph.getV();
        labels = new JLabel[kol][kol];
        removeAll();
        setPreferredSize(new Dimension(30 * (kol + 1), 30 * (kol + 1)));
        setLayout(new GridLayout(kol + 1, kol + 1));
        add(new JLabel(""));
        for (int i = 0; i < kol; i++) {
            char[] c = {(char) (i + 'A')};
            JLabel l = new JLabel(new String(c));
            l.setVerticalAlignment(SwingConstants.CENTER);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            add(l);
        }
        for (int i = 0; i < kol; i++) {

            char[] c = {(char) (i + 'A')};
            JLabel k = new JLabel(new String(c));
            k.setVerticalAlignment(SwingConstants.CENTER);
            k.setHorizontalAlignment(SwingConstants.CENTER);
            add(k);

            for (int j = 0; j < kol; j++) {
                JLabel l = new JLabel("∞");
                l.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                l.setVerticalAlignment(SwingConstants.CENTER);
                l.setHorizontalAlignment(SwingConstants.CENTER);
                labels[i][j] = l;
                add(l);
            }
        }
        onUpdate();
    }
    void onUpdate(){
        for (int i = 0; i < graph.getV(); i++)
            for (int j = 0; j < graph.getV(); j++) {
                if(0 == graph.getEdgeWeigth(i,j)) continue;
                labels[i][j].setText(new Integer(graph.getEdgeWeigth(i,j)).toString());
            }
        }
    }
