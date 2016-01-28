package ru.niesoft.firstapp;
// Для работы с окошками:
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Main implements ActionListener {

    // Стартовые переменные


    public Main() {
        toConsole("hallo niesoft");
        initComponents();
    }


    private JFrame viewForm;

    public static void toConsole(String text){
        System.out.print(text);
    }

    private void initComponents() {
        viewForm = new JFrame("Main Form");
        viewForm.setSize(600, 800);
        viewForm.setVisible(true);
        viewForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        JButton button = new JButton("Провенрка кодировки");
        button.setVisible(true);
        button.setLocation(112, 112);
        button.setSize(165, 50);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                   JOptionPane.showMessageDialog(viewForm, "Don't touch me!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            });

        JLabel label = new JLabel("text");
        label.setVisible(true);
        label.setLocation(300,0);
        label.setSize(200,100);
        label.setText("text");
        label.setBackground(Color.black);
        label.setForeground(Color.magenta);


        viewForm.getContentPane().add(button);
        viewForm.getContentPane().add(label);
        viewForm.getContentPane().add(new JLabel());

    }

    public void actionPerformed(ActionEvent action) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    public static JTextArea driwTextArea(int left, int top, int x, int y){
        JTextArea inpts = new JTextArea();
        inpts.setSize(x,y);
        inpts.setLocation(left, top);
        return inpts;
    }


}
