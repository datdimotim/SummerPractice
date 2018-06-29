package com.katier.firstStep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

    public static void main(String[] args) {
        new Pane();
    }
}

class Pane extends JFrame{
    boolean state = true;
    int cx,cy;
    boolean flag ;
    JPanel panel = new JPanel();
    Pane(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
        panel.setLayout(null);
        add(panel);
        setButtons();
    }
    void setButtons(){
        JButton b = new JButton();
        b.setFocusable(false);
        b.setBackground(Color.BLUE);
        b.setBounds(185,75,50,50);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setEnabled(false);
        b.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cx = e.getX();
                cy= e.getY();
                flag=true;
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                flag=false;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                //if(!flag)return;
                int dx = e.getX() - cx;
                int dy = e.getY()-cy;
                b.setBounds(b.getX()+dx,b.getY()+dy,50,50);
                //panel.updateUI();
            }
        });
        panel.add(b);
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //if(b.hasFocus())
                //b.setBounds(e.getX(),e.getY(),50,50);
                //panel.updateUI();
            }
        });
        JButton g = new JButton();
        g.setBounds(0,0,50,50);
        g.addActionListener(actionEvent -> {
            if(state==true){
                b.setBounds(300,300,200,200);
                state=false;
            }
            else {
                b.setBounds(185,75,85,30);
                state=true;
            }
        });
        panel.add(g);
    }
}
