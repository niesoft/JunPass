package ru.niesoft.firstapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class Main implements ActionListener {

    // Стартовые переменные
    public static BufferedReader input = null;
    // Настройки программы по умолчанию
    public static int startup_size_w = 400;
    public static int startup_size_h = 400;
    public static int startup_location_x = 0;
    public static int startup_location_y = 0;
    public static boolean window_on_top = false;
    public JFrame viewForm;
    public JFrame authForm;
    public ru.niesoft.firstapp.Designer Designer = new ru.niesoft.firstapp.Designer();

    // Глобальные переменные которые не меняются
    final static String jarPath = System.getProperty("user.dir") + System.getProperty("file.separator");
    final static int RESOLUTION_W = Toolkit.getDefaultToolkit().getScreenSize().width;
    final static int RESOLUTION_H = Toolkit.getDefaultToolkit().getScreenSize().height;

    // Colors
    final public Color GREENOSNOVNOY = new Color(0, 121, 107);
    final public Color GREENDARK = new Color(0, 77, 64);
    final public Color GREENWHITE = new Color(224, 242, 241);
    final public Color DARKERROR = new Color(191, 54, 12);


    public Main() {
        getAuth();
        ToConsole("Определим нужные нам папки, проверим файл настроек");
        //JOptionPane.showMessageDialog(viewForm, jarPath, "Warning", JOptionPane.WARNING_MESSAGE);
       // GetFileParam();
       // initComponents();

    }


    public static void ToConsole(String text) {
        System.out.println(text);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    public static JTextArea driwTextArea(int left, int top, int x, int y) {
        JTextArea inpts = new JTextArea();
        inpts.setSize(x, y);
        inpts.setLocation(left, top);
        return inpts;
    }

    public static void ReadParam(String config) {
        Type itemsArrType = new TypeToken<GoodsItem[]>() {
        }.getType();
        GoodsItem[] arrItemsDes = new Gson().fromJson(config, itemsArrType);
        for (int i = 0; i < arrItemsDes.length; i++) {
            if (arrItemsDes[i].config.equalsIgnoreCase("startup_size_w"))
                startup_size_w = Integer.parseInt(arrItemsDes[i].param);
            if (arrItemsDes[i].config.equalsIgnoreCase("startup_size_h"))
                startup_size_h = Integer.parseInt(arrItemsDes[i].param);
            if (arrItemsDes[i].config.equalsIgnoreCase("startup_location_x"))
                startup_location_x = Integer.parseInt(arrItemsDes[i].param);
            if (arrItemsDes[i].config.equalsIgnoreCase("startup_location_y"))
                startup_location_y = Integer.parseInt(arrItemsDes[i].param);
            if (arrItemsDes[i].config.equalsIgnoreCase("window_on_top"))
                window_on_top = Boolean.parseBoolean(arrItemsDes[i].param);
        }
        ToConsole("Настройки загружены");
    }

    public static byte[] InCompress(String toCompress) {
        byte[] input = toCompress.getBytes();
        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_COMPRESSION);
        compressor.setInput(input);
        compressor.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        byte[] buf = new byte[1024];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }
        try {
            bos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        byte[] compressedData = bos.toByteArray();
        DeCompress(compressedData);
        System.out.println(String.valueOf(compressedData));
        return compressedData;
    }

    public static String DeCompress(byte[] compressedData) {
        Inflater decompressor = new Inflater();
        decompressor.setInput(compressedData);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(compressedData.length);
        byte[] buf = new byte[1024];
        while (!decompressor.finished()) {
            try {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            } catch (DataFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            bos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        String decompressedData = bos.toString();
        return decompressedData;
    }
// Чтение бинарного файла
    public static String ReadBinary(String filepath) {
        ByteArrayOutputStream out = null;
        InputStream input = null;
        try {
            out = new ByteArrayOutputStream();
            input = new BufferedInputStream(new FileInputStream(filepath));
            int data = 0;
            try {
                while ((data = input.read()) != -1) {
                    out.write(data);
                }
                input.close();
                out.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return DeCompress(out.toByteArray());
    }
//    Запись бинарного файла
    public static void WriteBinary(String filepath, String source) {
        byte[] arr = InCompress(source);
        try {
            FileOutputStream fos = new FileOutputStream(new File(filepath));
            fos.write(arr);
            fos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    private void getAuth(){
        authForm = new JFrame("Авторизация");
        authForm.setSize(320, 400);
        authForm.setLocation(RESOLUTION_W / 2 - authForm.getSize().width / 2, RESOLUTION_H / 2 - authForm.getSize().height / 2);
        authForm.setVisible(true);
        authForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        authForm.setAlwaysOnTop(true);
        authForm.setResizable(false);
        //ImageIcon imageIcon = new ImageIcon("icon.png");
        //Image image = imageIcon.getImage();

        //authForm.setIconImage(image);

        JPanel splash = new JPanel();
        splash.setBackground(GREENOSNOVNOY);


        final JPasswordField password = new JPasswordField();
        password.setSize(authForm.getSize().width - 100, 50);
        password.setForeground(GREENWHITE);
        password.setBackground(GREENOSNOVNOY);
        password.setLocation(50, authForm.getSize().height - 120);
        password.setHorizontalAlignment(JTextField.CENTER);
        Font font = new Font("Default", 0, 24);
        password.setFont(font);
        Border empt = BorderFactory.createMatteBorder(0, 0, 4, 0, GREENDARK);
        password.setBorder(empt);

        KeyListener kl = new KeyListener() {
            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {

            }

            public void keyReleased(KeyEvent e) {
                password.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, GREENDARK));
                if (e.getKeyCode() == 27) password.setText("");
                if (e.getKeyCode() == 10){
                    boolean auth = CheckAuth(password.getPassword());
                    if (auth){
                        ToConsole("auth true");
                    }else{
                        ToConsole("auth false");
                        password.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, DARKERROR));
                    }
                }
            }
        };
        password.addKeyListener(kl);






        authForm.getContentPane().add(password);
        authForm.getContentPane().add(splash);
    }

    public boolean CheckAuth(char[] pwd){

        ToConsole(String.valueOf(pwd));
        if (String.valueOf(pwd).equalsIgnoreCase("111")) {
            return true;
        }else{
            return false;
        }
    }

    private void initComponents() {
        viewForm = new JFrame("JunPass v0.1");
        viewForm.setSize(startup_size_w, startup_size_h);
        viewForm.setLocation(startup_location_x, startup_location_y);
        viewForm.setAlwaysOnTop(window_on_top);
        viewForm.setVisible(true);
        viewForm.setMinimumSize(new Dimension(320,240));
        viewForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        WindowListener winListener = new TestWindowListener();
        viewForm.addWindowListener(winListener);

        ComponentListener resizeForm = new ResizeForm();
        viewForm.addComponentListener(resizeForm);



        ToConsole("Designer.CreateLayout()");
        RedrawForm();

        viewForm.getContentPane().add(new JLabel());
    }

    public void RedrawForm(){
        JPanel left_panel = Designer.dpanel(0,0,300,viewForm.getSize().height, GREENOSNOVNOY, "left_panel");
        Border empt = BorderFactory.createMatteBorder(0, 0, 0, 1, GREENDARK);
        left_panel.setBorder(empt);
        viewForm.getContentPane().add(left_panel);
    }



    public void actionPerformed(ActionEvent action) {
    }

    public void GetFileParam() {
        File f = new File(jarPath + ".config");
        if (f.exists() && !f.isDirectory()) {
            ToConsole("Загружаем файл настроек");
            String config = ReadBinary(jarPath + ".config");
            ReadParam(config);
        } else {
            try {
                boolean bool = f.createNewFile();
                ToConsole("Файл настроек не найден, статус записи: " + bool);
                WriteParam();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void WriteParam() {
        startup_size_w = viewForm.getSize().width;
        startup_size_h = viewForm.getSize().height;
        startup_location_x = viewForm.getLocation().x;
        startup_location_y = viewForm.getLocation().y;
        window_on_top = viewForm.isAlwaysOnTop();


        GoodsItem[] arrItems = new GoodsItem[5];
        arrItems[0] = new GoodsItem("startup_size_w", String.valueOf(startup_size_w));
        arrItems[1] = new GoodsItem("startup_size_h", String.valueOf(startup_size_h));
        arrItems[2] = new GoodsItem("startup_location_x", String.valueOf(startup_location_x));
        arrItems[3] = new GoodsItem("startup_location_y", String.valueOf(startup_location_y));
        arrItems[4] = new GoodsItem("window_on_top", String.valueOf(window_on_top));

        String jsonStr = new Gson().toJson(arrItems);
        System.out.println(jsonStr);
        WriteBinary(jarPath + ".config", jsonStr);
    }

    public static class GoodsItem {
        String config;
        String param;

        public GoodsItem(String config, String param) {
            this.config = config;
            this.param = param;
        }

        public String toString() {
            return config + " : " + param;
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

    public class ResizeForm implements ComponentListener {
        public void componentResized(ComponentEvent evt) {
            Component c = (Component)evt.getSource();
            viewForm.getContentPane().getComponent(0).setSize(220, viewForm.getSize().height);
        }
        public void componentMoved(ComponentEvent e) {

        }
        public void componentShown(ComponentEvent e) {

        }
        public void componentHidden(ComponentEvent e) {

        }
    }



}
