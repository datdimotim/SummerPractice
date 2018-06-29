package com.katier.firstStep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class DraggedButton extends JButton {
    private boolean isFixed=false;
    public DraggedButton(){
        MouseListener mouseListener=new MouseListener();
        addMouseMotionListener(mouseListener);
        addMouseListener(mouseListener);

        setBackground(Color.orange);
        setContentAreaFilled(false);
    }
    public void fixButton(boolean isFixed){
        this.isFixed=isFixed;
    }
    public Point getCenter(){
        return new Point(
                getBounds().x+getBounds().width/2,
                getBounds().y+getBounds().height/2
        );
    }

    private class MouseListener extends MouseAdapter {
        private Point offset=new Point();
        @Override
        public void mousePressed(MouseEvent e) {
            if(isFixed)return;
            offset.x=e.getX();
            offset.y=e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(isFixed)return;
            int x=getBounds().x+e.getX()-offset.x;
            int y=getBounds().y+e.getY()-offset.y;
            if(x<0)x=0;
            if(y<0)y=0;
            if(x>getParent().getWidth()-getWidth())x=getParent().getWidth()-getWidth();
            if(y>getParent().getHeight()-getHeight())y=getParent().getHeight()-getHeight();
            setBounds(x, y, getWidth(), getHeight());
        }
    }

    private static final int STROKE_SIZE=2;
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) g.setColor(Color.gray);
        else g.setColor(getBackground());
        ((Graphics2D)g).setStroke(new BasicStroke(STROKE_SIZE));
        g.fillOval(STROKE_SIZE, STROKE_SIZE, getSize().width - 2*STROKE_SIZE, getSize().height - 2*STROKE_SIZE);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(Color.darkGray);
        g.drawOval(STROKE_SIZE, STROKE_SIZE, getSize().width - 2*STROKE_SIZE, getSize().height - 2*STROKE_SIZE);
    }

    public boolean contains(int x, int y) {
        return new Ellipse2D.Float(0, 0, getWidth(), getHeight()).contains(x, y);
    }
}