package com.katier.firstStep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class New extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea figureText;
    private JLabel warnLable;
    String figure="";
    int figureInt=0;
    int minFigure,maxFigure;

    boolean canceled =false;

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

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                canceled =true;
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public int getFigure() {
        return figureInt;
    }

    private void onOK() {
        figure = figureText.getText();
        try {
            figureInt = getFigureInt();
            warnLable.setText("Good!");
            warnLable.setForeground(Color.GREEN);
            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    dispose();
                }
            });
            timer.start();
        }
        catch (NumberFormatException ex){
            warnLable.setText("Illegal input!");
            warnLable.setForeground(Color.RED);
        }
        catch (TooBigException ex){
            warnLable.setText("Too big figure!");
            warnLable.setForeground(Color.RED);
        }
        catch (TooSmallException ex){
            warnLable.setText("Too small figure!");
            warnLable.setForeground(Color.RED);
        }
        //dispose();
    }

    private int getFigureInt() throws TooBigException, TooSmallException {
        int f = Integer.parseInt(figure);
        if(f>maxFigure) throw new TooBigException();
        if(f<minFigure) throw new TooSmallException();
        return f;
    }

    private void onCancel() {
        canceled=true;
        // add your code here if necessary
        dispose();
    }

    public static Integer ask(int min, int max,String warn){
        New dialog=new New(min,max,warn);
        dialog.pack();
        dialog.setVisible(true);
        if(dialog.canceled)return null;
        else return dialog.getFigure();
    }

    public static void main(String[] args) {
        System.out.println("user enter: "+ask(0,100,"hghv3"));
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

class TooBigException extends Exception{

}

class TooSmallException extends Exception{

}