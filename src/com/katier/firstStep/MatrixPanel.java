package com.katier.firstStep;

import javax.swing.*;
import java.awt.*;

public class MatrixPanel extends JPanel {
    public static final int SIZE=20;
    private JLabel[][] labels;
    private Graph graph;
    public MatrixPanel(){
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(SIZE*14, SIZE*14));
        setLayout(new GridLayout(2, 2));
        setBackground(Color.WHITE);
    }
    public  void setListeners(){
        graph.addChangeListeners(v->onUpdate());
    }
    public void setGraph(Graph graph) {
        setLayout(new BorderLayout());
        this.graph=graph;
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
                labels[i][j].setForeground(Color.BLACK);
                labels[i][j].setBackground(Color.WHITE);
                //if(graph.getEdgeState(i,j) == Graph.EdgeState.SELECTED){
                    for (int k = 0; k < graph.getV(); k++) {
                        labels[graph.step-1][k].setOpaque(true);
                        labels[k][graph.step-1].setOpaque(true);
                        labels[graph.step-1][k].setBackground(new Color(Graph.EdgeState.SELECTED.getColor()));
                        labels[k][graph.step-1].setBackground(new Color(Graph.EdgeState.SELECTED.getColor()));
                    }

               // }
                if(0 == graph.getMatrixCell(i,j).weight){
                    labels[i][j].setText("∞");
                }
                else {
                    labels[i][j].setText(
                            Integer.toString(graph.getMatrixCell(i, j).weight) + "/"
                                    +s.charAt(graph.getMatrixCell(i,j).prev)
                    );
                }
            }
        }
    }
