package com.katier.firstStep;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static java.lang.Math.pow;

public class Main extends JFrame {
    private Graph graph = new Graph(0);
    private final MatrixPanel panelForMatr = new MatrixPanel();
    private final MatrixPanel panelForFinalMatr = new MatrixPanel();
    private final DraggedPanel panel = new DraggedPanel();
    private int step=0;
    JPanel t3 = new JPanel(new BorderLayout());
    private Main() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initMenu();

        setLayout(new BorderLayout());

        panel.setEdgeListener((i, j) -> {
            Integer weight = New.ask(1, 99, "Choose weight of edge");
            if (weight == null) return;
            graph.setEdgeWeight(i, j, weight);
        });
        panel.setPreferredSize(new Dimension(600, 700));
        panel.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.BLACK));
        add(panel, BorderLayout.WEST);

        initButtons();

        initMatrix();

        pack();
        //setSize(1200, 700);
        setVisible(true);
    }

    private void initButtons(){
    }

    private void initMatrix(){
        JPanel t = new JPanel();
        t.setPreferredSize(new Dimension(50,700));
        t.setBackground(Color.WHITE);
        JPanel tmp = new JPanel();
        tmp.setLayout(new BoxLayout(tmp,BoxLayout.Y_AXIS));
        tmp.setPreferredSize(new Dimension(600, 700));
        tmp.setBackground(Color.WHITE);
        JPanel t1 = new JPanel();
        t1.setPreferredSize(new Dimension(600,30));
        t1.setBackground(Color.WHITE);
        tmp.add(t1);
        tmp.add(panelForMatr);
        t3.setPreferredSize(new Dimension(600,20));
        t3.setBackground(Color.WHITE);
        tmp.add(t3);
        tmp.add(panelForFinalMatr);
        JPanel t2 = new JPanel();
        t2.setPreferredSize(new Dimension(600,50));
        t2.setBackground(Color.WHITE);
        tmp.add(t2);
        add(t,BorderLayout.EAST);
        add(tmp);
        //setLayout(new BorderLayout());
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
        JMenu solution = new JMenu("Solution");
        JMenuItem solve = new JMenuItem("Solve");
        JMenuItem next = new JMenuItem("Next");
        JMenuItem back = new JMenuItem("Back");
        solve.addActionListener(actionEvent -> {
            //floyd-warshall
           Graph g = new Graph(graph.getV());
           panelForFinalMatr.setGraph(g);
           panelForFinalMatr.setListeners();
            g.setMatr(graph.weights);
           g.setStepOfAlgorithm(g.getV());

        });
        next.addActionListener(actionEvent -> {
            //next in Array
            //if(!graph.setStepOfAlgorithm(step))return;
            if(step==graph.getV())return;
            step++;
            System.out.println(step);
            graph.setStepOfAlgorithm(step);
        });
        back.addActionListener(actionEvent -> {
            //back in Array
            if(step==1)return;
            step--;
            graph.setStepOfAlgorithm(step);
        });
        solution.add(solve);
        solution.add(next);
        solution.add(back);
        jToolBar.add(modifyTree);
        jToolBar.add(solution);
        modifyTree.add(newTree);
        modifyTree.add(addEdges);
        modifyTree.add(moveVert);
        setJMenuBar(jToolBar);
    }

    private void addVertexesInPanel(int vertexes) {
        graph = new Graph(vertexes);
        step=1;
        panel.setGraph(graph);
        panelForMatr.setGraph(graph);
        panelForMatr.setListeners();
        panelForFinalMatr.setGraph(graph);
        JLabel label = new JLabel("Final Matrix:");
        label.setPreferredSize(new Dimension(100, 20));
        label.setForeground(Color.BLUE);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        t3.add(label, BorderLayout.WEST);

    }
    public static void main(String[] args) {
        new Main();
    }
}

