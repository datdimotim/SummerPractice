package com.katier.firstStep;

import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.util.function.Consumer;

import static com.katier.firstStep.Utils.*;

public class MainWindow extends JFrame{

    public MainWindow(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initUI();
        setSize(800,600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    private void initUI() {

        DraggedPanel draggedPanel=new DraggedPanel(new Graph(new int[][]{
                                                                {0,1,0,0},
                                                                {1,0,1,1},
                                                                {1,0,0,1},
                                                                {0,1,1,1}
        }));
        add(draggedPanel);
    }
}

enum EdgeState{
    NORMAL,SELECTED,WIN,LOSE
}

enum VertexState{
    NORMAL,ACTIVE,PROCESSED,NEIGHBOR
}


class Utils{
    public static int[][] deepCopy(int[][] m){
        int[][] r=new int[m.length][];
        for(int i=0;i<m.length;i++)r[i]=m[i].clone();
        return r;
    }
}