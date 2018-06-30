package com.katier.firstStep;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Panel extends JFrame{
    Graph graph=new Graph(new int[0][0]);
    JPanel panelForMatr = new JPanel();
    List<DraggedButton> verts = new ArrayList<>();
    JLabel[][] labels;
    DraggedPanel panel = new DraggedPanel(graph);
    Panel(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(null);
        add(panel);
        JMenuBar jToolBar = new JMenuBar();
        JMenu modifyTree = new JMenu("Modify tree");
        JMenuItem newTree = new JMenuItem("New Tree");
        JMenuItem addEdges = new JMenuItem("Add Edges");
        JMenuItem moveVert = new JMenuItem("Move Vertexes");
        newTree.addActionListener(actionEvent -> {
            New dialog = new New(2,12,"Choose number of vertexes");
            dialog.setBounds(50,50,100,100);
            dialog.pack();
            dialog.setVisible(true);
            if(dialog.getFigure()==0) return;
            int vertexes = dialog.getFigure();
            deletVertexesInPanel();
            deleteVertexesFromArray();
            addVertexesInArray(vertexes);
            addVertexesInPanel();
        });
        moveVert.addActionListener(actionEvent -> {
            panel.fixButtons(false);
        });
        addEdges.addActionListener(actionEvent -> {
            panel.fixButtons(true);
            //drawLine
            // get matrix
        });
        JMenu solvetion = new JMenu("Solvetion");
        JMenuItem solve = new JMenuItem("Solve");
        JMenuItem next = new JMenuItem("Next");
        JMenuItem back = new JMenuItem("Back");
        solve.addActionListener(actionEvent -> {
            //deikstra
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
        panelForMatr.setPreferredSize(new Dimension(600,700));

        //
            panelForMatr.setLayout(new GridLayout(2,2));


        //
        JPanel tmp=new JPanel();
        tmp.setPreferredSize(new Dimension(400,700));
        tmp.setBackground(Color.WHITE);
        tmp.add(panelForMatr);
        panelForMatr.setBackground(Color.WHITE);
        add(tmp,BorderLayout.EAST) ;
        setSize(1200,700);
        setVisible(true);

    }
    void setMatr(int kol){
        labels = new JLabel[kol][kol];
        panelForMatr.removeAll();
        panelForMatr.setPreferredSize(new Dimension(30*kol,30*kol));
        panelForMatr.setLayout(new GridLayout(kol,kol));
        for (int i = 0; i < kol; i++)
        for(int j=0;j<kol;j++){
            JLabel l = new JLabel("X");
            l.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            l.setVerticalAlignment(SwingConstants.CENTER);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            labels[i][j]=l;
            panelForMatr.add(l);
        }
    }

    void addVertexesInArray(int vertexes){
        remove(panel);
        setMatr(vertexes);
        graph=new Graph(new int[vertexes][vertexes]);
        panel=new DraggedPanel(graph);
        panel.setPreferredSize(new Dimension(600,700));
        add(panel,BorderLayout.WEST);
        panel.addEdgeAddListener((i,j)->{
            Integer weight=New.ask(1,10000,"Choose weight of edge");
            if(weight==null) {
                //panel.updateUI();
                return;
            }
            graph.setEggeWeight(i,j,weight);
            labels[i][j].setText(weight.toString());
        });
    }
    void addVertexesInPanel(){
        for(JButton jb:verts){
            jb.setBackground(Color.BLUE);
            panel.add(jb);
        }
        panel.updateUI();
    }
    void deletVertexesInPanel(){
        for(JButton jb:verts)
            panel.remove(jb);
        panel.updateUI();
    }
    void deleteVertexesFromArray(){
       verts.clear();
    }

}

class MyFrame  {
    public static void main(String[] args) {
        new Panel();
    }
}