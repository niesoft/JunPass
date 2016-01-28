package ru.niesoft.firstapp;
// Для работы с окошками:

import sun.org.mozilla.javascript.internal.json.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;





public class Main implements ActionListener {

    // Стартовые переменные
    final static String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    private JFrame viewForm;
    public static BufferedReader input = null;


    public Main() {
        ToConsole("Определим нужные нам папки, проверим файл настроек");
        GetFileParam();

        initComponents();
        //JOptionPane.showMessageDialog(viewForm, jarPath, "Warning", JOptionPane.WARNING_MESSAGE);

        ToConsole(jarPath);

    }




    public static void ToConsole(String text){
        System.out.println(text);
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


    public static void GetFileParam(){
        boolean bool = false;
        File f = new File(jarPath + ".config");
        if(f.exists() && !f.isDirectory()) {
            ToConsole("Загружаем файл настроек");
            try {
                input = new BufferedReader(new FileReader(jarPath + ".config"));
            }catch (FileNotFoundException e) {
                System.out.println("File \"" + jarPath + ".config" + "\" not found");
                return;
            }
            ToConsole("Загружаем настройки");
            String tmp;
            try {
                while ((tmp = input.readLine()) != null) ToConsole(tmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ToConsole("Разбираем JSON формат");
            JsonParser parser = new JsonParser();
            parser.parseValue(tmp);




//            final String[] CONFIG = new JsonParser.;

//            final String CONFIG = f.
        }else{
            try{
                bool = f.createNewFile();
                ToConsole("Файл настроек не найден, статус записи: " + bool);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

}
