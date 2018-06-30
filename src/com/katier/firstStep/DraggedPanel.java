package com.katier.firstStep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.BiConsumer;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class DraggedPanel extends JPanel {
    public static final int SIZE_OF_BUTTON=50;
    private final DraggedButton[] buttons;
    private final int[] line={0,0,0,0};
    private boolean needDraw=false;
    private boolean fixed=false;

    private final Graph graph;

    public DraggedPanel(Graph graph){
        this.graph=graph;
        buttons=new DraggedButton[graph.getV()];
        graph.addChangeListeners((v)->updateUI());

        //setBackground(new Color(1023));
        setLayout(null);

        ComponentListener buttonMoveListener=new ComponentAdapter(){
            @Override
            public void componentMoved(ComponentEvent e) {
                updateUI();
            }
        };

        for(int i=0;i<graph.getV();i++){
            buttons[i]=new DraggedButton();
            buttons[i].addComponentListener(buttonMoveListener);
            setBoundsToButtons(i);
            char[] c={(char) (i+'A')};
            buttons[i].setText(new String(c));
            add(buttons[i]);
        }
    }

    public void fixButtons(boolean isFixed){
        this.fixed=isFixed;
        for(DraggedButton db:buttons)db.fixButton(isFixed);
    }

    public void addEdgeAddListener(BiConsumer<Integer,Integer> listener){
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
                    if(entered[0] !=-1&& dowend[0] != entered[0])listener.accept(dowend[0],entered[0]);
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
                public void mouseMoved(MouseEvent mouseEvent) {

                }
            });
        }
    }

    private void setBoundsToButtons(int i){
        DraggedButton b = buttons[i];
        if(i<6)b.setBounds(i*70+50,50,50,50);
        else b.setBounds((i-6)*70+50,150,50,50);
    }
    private Point [] getPath(int i,int j) {
        DraggedButton a=buttons[i];
        DraggedButton b=buttons[j];

        if(graph.getEdgeWeigth(j,i)==0){
          return new Point[]{new Point(a.getCenter().x,a.getCenter().y),
                    new Point(b.getCenter().x,b.getCenter().y)};
        } else
        if(graph.getEdgeWeigth(j,i)<graph.getEdgeWeigth(i,j) || (graph.getEdgeWeigth(j,i)==graph.getEdgeWeigth(i,j) &&i>j)){
            if(Math.abs((b.getCenter().y)-a.getCenter().y)< SIZE_OF_BUTTON/2){
                return new Point[] {new Point(a.getCenter().x+SIZE_OF_BUTTON/2,a.getCenter().y+SIZE_OF_BUTTON/6),
                        new Point(b.getCenter().x+SIZE_OF_BUTTON/2,b.getCenter().y+SIZE_OF_BUTTON/6)};
            } else
                return new Point[]{new Point(a.getCenter().x+SIZE_OF_BUTTON/2,a.getCenter().y),
                        new Point(b.getCenter().x+SIZE_OF_BUTTON/2,b.getCenter().y)};
        }
        else {
            if(Math.abs(b.getCenter().y-a.getCenter().y)< SIZE_OF_BUTTON/2){
                return new Point[] {new Point(a.getCenter().x-SIZE_OF_BUTTON/2,a.getCenter().y-SIZE_OF_BUTTON/6),
                        new Point(b.getCenter().x-SIZE_OF_BUTTON/2,b.getCenter().y-SIZE_OF_BUTTON/6)};
            } else
               return new Point[] {new Point(a.getCenter().x - SIZE_OF_BUTTON / 2, a.getCenter().y),
                        new Point(b.getCenter().x - SIZE_OF_BUTTON / 2, b.getCenter().y )};
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i=0;i<graph.getV();i++){
            for(int j=0;j<graph.getV();j++){
                if(graph.getEdgeWeigth(i,j)==0)continue;
                Point [] points = getPath(i,j);
                drawStrelka(g,points[0],points[1],graph.getEdgeWeigth(i,j));
            }
        }

        if(!needDraw)return;
        drawStrelka(g,new Point(line[0],line[1]),new Point(line[2],line[3]),null);
    }



    private void drawStrelka(Graphics g, Point start, Point finish, Integer weight){
        if(start.equals(finish))return;
        g.setColor(Color.GREEN);
        g.drawLine(start.x,start.y,finish.x,finish.y);
        //
        int tx = finish.x;
        int ty = finish.y;
        finish.x = (start.x+finish.x)/2;
        finish.y = (start.y+finish.y)/2;
        final double size= 30;
        final double norm=sqrt(pow(start.x-finish.x,2)+pow(start.y-finish.y,2));
        Point startFictive=new Point((int)((start.x-finish.x)/norm*size)+finish.x,((int)((start.y-finish.y)/norm*size))+finish.y);
        Point center=new Point((startFictive.x+finish.x)/2,(startFictive.y+finish.y)/2);
        Point finishTranslated=new Point(finish.x-center.x,finish.y-center.y);
        Point rp=new Point(finishTranslated.y,-finishTranslated.x);
        Point lp=new Point(-rp.x,-rp.y);
        Point lpTranslated=new Point(lp.x+center.x,lp.y+center.y);
        Point rpTranslated=new Point(rp.x+center.x,rp.y+center.y);
        //g.drawLine(lpTranslated.x,lpTranslated.y,rpTranslated.x,rpTranslated.y);
        g.drawLine(lpTranslated.x,lpTranslated.y,finish.x,finish.y);
        g.drawLine(rpTranslated.x,rpTranslated.y,finish.x,finish.y);
        if(weight==null)return;
        g.setColor(Color.BLUE);
        g.drawString(weight.toString(),(start.x+tx)/2,(start.y+ty)/2);
    }
}
