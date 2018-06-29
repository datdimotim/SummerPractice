package com.katier.firstStep;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Panel extends JFrame{
    List<DraggedButton> verts = new ArrayList<>();
    DraggedPanel panel = new DraggedPanel(new int[0][0]);
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
        setSize(500,500);
        setVisible(true);

    }
    void addVertexesInArray(int vertexes){
        remove(panel);
        panel=new DraggedPanel(new int[vertexes][vertexes]);
        add(panel);
        panel.addEdgeAddListener((i,j)->{
            Integer weight=New.ask(1,10000,"Choose weight of edge");
            if(weight==null) {
                //panel.updateUI();
                return;
            }
            panel.updateMatrix(i,j,weight);
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