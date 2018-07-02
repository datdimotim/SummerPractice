package com.katier.firstStep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.BiConsumer;

public class DraggedPanel extends JPanel {
    public static final int SIZE_OF_BUTTON=50;
    private DraggedButton[] buttons;
    private final int[] line={0,0,0,0};
    private boolean needDraw=false;
    private boolean fixed=false;
    private BiConsumer<Integer,Integer> edgeListener=null;
    private Graph graph=new Graph(0);

    public DraggedPanel(){
        setBackground(Color.WHITE);
        setLayout(null);
    }

    public void setGraph(Graph graph){
        removeAll();
        fixed=false;
        needDraw=false;
        this.graph=graph;
        graph.addChangeListeners((v)->updateUI());

        ComponentListener buttonMoveListener=new ComponentAdapter(){
            @Override
            public void componentMoved(ComponentEvent e) {
                updateUI();
            }
        };

        buttons=new DraggedButton[graph.getV()];
        for(int i=0;i<graph.getV();i++){
            buttons[i]=new DraggedButton();
            buttons[i].addComponentListener(buttonMoveListener);
            setBoundsToButtons(i);
            char[] c={(char) (i+'A')};
            buttons[i].setText(new String(c));
            add(buttons[i]);
        }
        setButtonListeners();
    }

    public void fixButtons(boolean isFixed){
        this.fixed=isFixed;
        if(buttons==null)return;
        for(DraggedButton db:buttons){
            db.fixButton(isFixed);
        }
    }

    public void setEdgeListener(BiConsumer<Integer,Integer> listener){
        edgeListener=listener;
    }

    private void setButtonListeners(){
        final int[] dowend = {-1};
        final int[] entered = {-1};
        for(int i=0;i<graph.getV();i++){
            final int ii=i;
            buttons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    if(!fixed)return;
                    dowend[0] =ii;
                    needDraw=true;
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                    if(!fixed)return;
                    if(entered[0] !=-1&& dowend[0] != entered[0])onEdgeAdded(dowend[0],entered[0]);
                    dowend[0] =-1;
                    needDraw=false;
                    updateUI();
                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                    if(!fixed)return;
                    entered[0] =ii;
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    if(!fixed)return;
                    entered[0] =-1;
                }
            });

            buttons[i].addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent mouseEvent) {
                    if(!fixed)return;
                    line[0]=buttons[ii].getCenter().x;
                    line[1]=buttons[ii].getCenter().y;
                    line[2]=buttons[ii].getX()+mouseEvent.getX();
                    line[3]=buttons[ii].getY()+mouseEvent.getY();
                    updateUI();
                }

                @Override
                public void mouseMoved(MouseEvent mouseEvent) { }
            });
        }
    }

    private void onEdgeAdded(int i, int j){
        if(edgeListener!=null)edgeListener.accept(i,j);
    }

    private void setBoundsToButtons(int i){
        DraggedButton b = buttons[i];
        if(i<6)b.setBounds(i*70+SIZE_OF_BUTTON,SIZE_OF_BUTTON,SIZE_OF_BUTTON,SIZE_OF_BUTTON);
        else b.setBounds((i-6)*70+SIZE_OF_BUTTON,150,SIZE_OF_BUTTON,SIZE_OF_BUTTON);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i=0;i<graph.getV();i++){
            for(int j=i;j<graph.getV();j++){
                Point a=buttons[i].getCenter();
                Point b=buttons[j].getCenter();
                int wab=graph.getEdgeWeight(i,j);
                int wba=graph.getEdgeWeight(j,i);
                if(graph.getMatrixCell(j,i).weight!=0) wba = graph.getMatrixCell(j,i).weight;
                if(graph.getMatrixCell(i,j).weight!=0) wab = graph.getMatrixCell(i,j).weight;
                int abColor=graph.getEdgeState(i,j).getColor();
                int baColor=graph.getEdgeState(j,i).getColor();
                //int abColor=Color.BLACK.getRGB();
                //int baColor=Color.BLACK.getRGB();
                if(wab != 0 && wba != 0)
                    Arrows.drawParallelArrows(g,a,b,wab,wba,abColor,baColor);

                else if(wab != 0 ) Arrows.drawConnectedArrow(g,a,b,wab,abColor);
                     else if(wba != 0) Arrows.drawConnectedArrow(g,b,a,wba,baColor);
            }
        }

        if(needDraw) Arrows.drawFloatingArrow(g,new Point(line[0],line[1]),new Point(line[2],line[3]));
    }
}
