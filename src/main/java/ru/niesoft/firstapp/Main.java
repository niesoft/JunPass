package ru.niesoft.firstapp;
// Для работы с окошками:

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Type;
import java.io.FileWriter;


public class Main implements ActionListener {

    // Стартовые переменные
//    final static String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    final static String jarPath = System.getProperty("user.dir") + System.getProperty("file.separator");
    private JFrame viewForm;
    public static BufferedReader input = null;
    // Настройки программы по умолчанию
    public static int startup_size_w = 400;
    public static int startup_size_h = 400;
    public static int startup_location_x = 0;
    public static int startup_location_y = 0;
    public static boolean window_on_top = false;




    public Main() {
        ToConsole("Определим нужные нам папки, проверим файл настроек");
        //JOptionPane.showMessageDialog(viewForm, jarPath, "Warning", JOptionPane.WARNING_MESSAGE);



        GetFileParam();

        initComponents();


    }




    public static void ToConsole(String text){
        System.out.println(text);
    }

    private void initComponents() {
        viewForm = new JFrame("Main Form");
        viewForm.setSize(startup_size_w, startup_size_h);
        viewForm.setLocation(startup_location_x, startup_location_y);
        viewForm.setAlwaysOnTop(window_on_top);
        viewForm.setVisible(true);
        viewForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        WindowListener winListener = new TestWindowListener();
        viewForm.addWindowListener(winListener);



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
            ToConsole("Загружаем настройки " + jarPath + ".config");
            String tmp = "";
            String config = "";
            try {
                while ((tmp = input.readLine()) != null){
                    config += tmp;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ToConsole("Разбираем JSON формат");
            ToConsole(config);
            ReadParam(config);


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

    public static class GoodsItem{
        String config;
        String param;
        public GoodsItem(String config, String param) {
            this.config = config;
            this.param = param;
        }
        public String toString(){
            return config + " : " + param;
        }
    }

    public static void ReadParam(String config){
        Gson gson = new Gson();
        Type itemsArrType = new TypeToken<GoodsItem[]>() {}.getType();
        GoodsItem[] arrItemsDes = new Gson().fromJson(config, itemsArrType);
        for (int i=0; i < arrItemsDes.length; i++){
            if (arrItemsDes[i].config.equalsIgnoreCase("startup_size_w")) startup_size_w = Integer.parseInt(arrItemsDes[i].param);
            if (arrItemsDes[i].config.equalsIgnoreCase("startup_size_h")) startup_size_h = Integer.parseInt(arrItemsDes[i].param);
            if (arrItemsDes[i].config.equalsIgnoreCase("startup_location_x")) startup_location_x = Integer.parseInt(arrItemsDes[i].param);
            if (arrItemsDes[i].config.equalsIgnoreCase("startup_location_y")) startup_location_y = Integer.parseInt(arrItemsDes[i].param);
            if (arrItemsDes[i].config.equalsIgnoreCase("window_on_top")) window_on_top = Boolean.parseBoolean(arrItemsDes[i].param);
        }
        ToConsole("Настройки загружены");
    }

    public void WriteParam(){
        startup_size_w = viewForm.getSize().width;
        startup_size_h = viewForm.getSize().height;
        startup_location_x = viewForm.getLocation().x;
        startup_location_y = viewForm.getLocation().y;
        window_on_top = viewForm.isAlwaysOnTop();


        GoodsItem[] arrItems = new GoodsItem[4];
        arrItems[0] = new GoodsItem("startup_size_w",       String.valueOf(startup_size_w));
        arrItems[1] = new GoodsItem("startup_size_h",       String.valueOf(startup_size_h));
        arrItems[2] = new GoodsItem("startup_location_x",   String.valueOf(startup_location_x));
        arrItems[3] = new GoodsItem("startup_location_y",   String.valueOf(startup_location_y));
        arrItems[3] = new GoodsItem("window_on_top",        String.valueOf(window_on_top));

        String jsonStr = new Gson().toJson(arrItems);
        System.out.println(jsonStr);

        File f = new File(jarPath + ".config");
        try {
            FileWriter writer = new FileWriter(jarPath + ".config", false);
            writer.write(jsonStr);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }


    }

    public class TestWindowListener implements WindowListener {
        public void windowActivated(WindowEvent e) {
            System.out.println("windowActivated");
        }
        public void windowClosed(WindowEvent e) {
            System.out.println("windowClosed");
        }
        public void windowClosing(WindowEvent e) {
            WriteParam();
            System.out.println("windowClosing");
        }
        public void windowDeactivated(WindowEvent e) {
            System.out.println("windowDeactivated");
        }
        public void windowDeiconified(WindowEvent e) {
            System.out.println("windowDeiconified");
        }
        public void windowIconified(WindowEvent e) {
            System.out.println("windowIconified");
        }
        public void windowOpened(WindowEvent e) {
            System.out.println("windowOpened");
        }
    }







}
