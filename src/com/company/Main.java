package com.company;

import javax.swing.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;

public class Main extends JFrame {
    public JPanel panelM;
    public JPanel panelB;

    final int WIDTH = 450, HEIGHT = 300;

    public Main() {
        //create JFrame
        super("Event-Driven Programming");
        this.getContentPane().setLayout(null);

        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        panelM = new JPanel();
        panelM.setBounds(0,0,WIDTH,HEIGHT);
        getContentPane().add(panelM);
        this.getContentPane().add(panelM);

        panelB = new JPanel();
        panelB.setBounds(0,0,WIDTH,HEIGHT);
        panelB.setSize(450, 75);
        panelB.setLocation(0, 225);

        JButton Process = new JButton("Process");
        JButton Confirm = new JButton("Confirm");
        JButton View = new JButton("View");
        JButton Finish = new JButton("Finish");
        JButton New = new JButton("New");
        JButton Exit = new JButton("Exit");

        panelB.add(Process);
        panelB.add(Confirm);
        panelB.add(View);
        panelB.add(Finish);
        panelB.add(New);
        panelB.add(Exit);

        getContentPane().add(panelB);
        this.getContentPane().add(panelB);
        //set up Game countdown timer
    }


    public static void main (String [] args) {

        new Main();
    }
}
