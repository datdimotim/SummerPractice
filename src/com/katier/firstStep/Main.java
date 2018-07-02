package com.katier.firstStep;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private Graph graph = new Graph(0);
    private final MatrixPanel panelForMatr = new MatrixPanel();
    private final MatrixPanel panelForFinalMatr = new MatrixPanel();
    private final DraggedPanel panel = new DraggedPanel();
    private int step=0;
    JPanel tmp;
    JPanel t3 = new JPanel(new BorderLayout());
    private Main() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initMenu();

        setLayout(new BorderLayout());

        panel.setEdgeListener((i, j) -> {
            Integer weight = New.ask(1, 99, "Choose weight of edge");
            if (weight == null) return;
            graph.setEdgeWeight(i, j, weight);

            Graph g = new Graph(graph.getV());
            panelForFinalMatr.setGraph(g);
            g.setStepOfAlgorithm(-1);
        });
        panel.setPreferredSize(new Dimension(600, 700));
        panel.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.BLACK));
        add(panel, BorderLayout.WEST);

        initButtons();

        initMatrix();

        setResizable(false);
        pack();
        setVisible(true);
    }

    private void initButtons(){
    }

    private void initMatrix(){
        JPanel t = new JPanel();
        t.setPreferredSize(new Dimension(50,700));
        t.setBackground(Color.WHITE);
        tmp = new JPanel();
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
    }

    private void initMenu(){
        JMenuBar jToolBar = new JMenuBar();
        JMenu modifyTree = new JMenu("Modify tree");
        JMenuItem newTree = new JMenuItem("New Graph");
        JMenuItem addEdges = new JMenuItem("Add Edges");
        JMenuItem moveVert = new JMenuItem("Move Vertexes");
        JMenuItem addVert = new JMenuItem("Add Vertex");
        newTree.addActionListener(actionEvent -> {
            Integer k = New.ask(2, 12, "Choose number of vertexes");
            if (k == null) return;
            int vertexes = k;
            remove(tmp);
            Graph g = new Graph(k);
            panelForFinalMatr.setGraph(g);
            initMatrix();
            addVertexesInPanel(vertexes);
        });
        moveVert.addActionListener(actionEvent -> panel.fixButtons(false));
        addEdges.addActionListener(actionEvent -> panel.fixButtons(true));
        addVert.addActionListener(actionEvent->{
            if(graph.getV()==0)return;
            if(graph.getV()==12){
                JOptionPane.showMessageDialog(null,"Too many vertexes");
                return;
            }
            graph=graph.getLarge();
            step=0;
            panelForMatr.setGraph(graph);
            panel.setGraph(graph);
            Graph g = new Graph(graph.getV());
            panelForFinalMatr.setGraph(g);
            g.setStepOfAlgorithm(-1);
        });
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
           g.setStepOfAlgorithm(g.getV()*g.getV()*g.getV()-1);

        });
        next.addActionListener(actionEvent -> {
            if(!graph.setStepOfAlgorithm(step)){
                JOptionPane.showMessageDialog(null,"This is already last step");
                return;
            }
            step++;
            graph.setStepOfAlgorithm(step);
        });
        back.addActionListener(actionEvent -> {
            if(step==0){
                JOptionPane.showMessageDialog(null,"This is already 0 step");
                return;
            }
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
        modifyTree.add(addVert);
        modifyTree.add(moveVert);
        setJMenuBar(jToolBar);
    }

    private void addVertexesInPanel(int vertexes) {
        graph = new Graph(vertexes);
        step=0;
        panel.setGraph(graph);
        panelForMatr.setGraph(graph);
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

