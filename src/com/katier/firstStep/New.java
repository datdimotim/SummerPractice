package com.katier.firstStep;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class New extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea figureText;
    private JLabel warnLable;
    private String figure="";
    private int figureInt=0;
    private int minFigure,maxFigure;

    private boolean canceled =false;

    public New(int minFigure,int maxFigure,String warn) {
        this.minFigure = minFigure;
        this.maxFigure = maxFigure;
        warnLable.setText(warn);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                canceled=true;
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                canceled =true;
                onCancel();
            }
        });
    }


    private void onOK() {
        figure = figureText.getText();
        try {
            figureInt = getFigureInt();
            warnLable.setText("Good!");
            warnLable.setForeground(Color.GREEN);
            Timer timer = new Timer(500, actionEvent -> dispose());
            timer.start();
        }
        catch (NumberFormatException ex){
            warnLable.setText("Illegal input!");
            warnLable.setForeground(Color.RED);
        }
        catch (TooBigFigureExeption ex){
            warnLable.setText("Too big figure!");
            warnLable.setForeground(Color.RED);
        }
        catch (TooSmallFigureException ex){
            warnLable.setText("Too small figure!");
            warnLable.setForeground(Color.RED);
        }
    }

    private int getFigureInt() throws TooBigFigureExeption, TooSmallFigureException {
        int f = Integer.parseInt(figure);
        if(f>maxFigure) throw new TooBigFigureExeption();
        if(f<minFigure) throw new TooSmallFigureException();
        return f;
    }

    private void onCancel() {
        canceled=true;
        dispose();
    }

    @Nullable
    public static Integer ask(int min, int max, String warn){
        New dialog=new New(min,max,warn);
        dialog.pack();
        dialog.setVisible(true);
        if(dialog.canceled)return null;
        else return dialog.figureInt;
    }

    private static class TooBigFigureExeption extends Exception{ }

    private static class TooSmallFigureException extends Exception{}

    public static void main(String[] args) {
        System.out.println("user enter: "+ask(0,100,"hghv3"));
        System.exit(0);
    }
}
