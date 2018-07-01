package com.katier.firstStep;

import javax.swing.*;
import java.awt.*;

public class MatrixPanel extends JPanel {
    public static final int SIZE=50;
    private JLabel[][] labels;
    private Graph graph;
    MatrixPanel(){
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(SIZE*14, SIZE*14));
        setLayout(new GridLayout(2, 2));
        setBackground(Color.WHITE);
    }
    public void setGraph(Graph graph) {
        this.graph=graph;
        graph.addChangeListeners(v->onUpdate());
        int kol= graph.getV();
        labels = new JLabel[kol][kol];
        removeAll();
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
    private void onUpdate(){
        String s="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < graph.getV(); i++)
            for (int j = 0; j < graph.getV(); j++) {
                if(0 == graph.getMatrixCell(i,j).weight) continue;
                labels[i][j].setText(
                        Integer.toString(graph.getMatrixCell(i, j).weight) + "/"
                                +s.charAt(graph.getMatrixCell(i,j).prev)
                );
            }
        }
    }
