package com.katier.firstStep;

import javax.swing.*;
import java.util.ArrayList;

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

        DraggedPanel draggedPanel=new DraggedPanel(new int[][]{
                                                                {0,1,0,0},
                                                                {1,0,1,1},
                                                                {1,0,0,1},
                                                                {0,1,1,1}
        });
        add(draggedPanel);
    }
}

class GraphView{
    VertexAttr[] vertexAttrs;
    static class VertexAttr{
        boolean isVisited;
        boolean isCurrent;
    }
}

class GraphInput{
    int[][] matrix;
}

interface Deicstra{
    ArrayList<GraphView> deicstra(GraphInput graphInput, int startPoint);
}