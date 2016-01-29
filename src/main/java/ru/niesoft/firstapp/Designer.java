package ru.niesoft.firstapp;

import javax.swing.*;
import java.awt.*;

/**
 * Created by niesoft on 29.01.16.
 */
public class Designer {
    public JFrame viewForm;

    public JPanel dpanel(int x, int y, int w, int h, Color color, String name){
        JPanel panel = new JPanel();
        panel.setLocation(x, y);
        panel.setSize(w, h);
        panel.setBackground(color);
        panel.setName(name);
        return panel;
    }

}
