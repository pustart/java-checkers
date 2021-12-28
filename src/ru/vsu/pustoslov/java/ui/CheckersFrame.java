package ru.vsu.pustoslov.java.ui;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class CheckersFrame extends JFrame {
    public void createFrame(){
        final CheckersPanel panel = new CheckersPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Checkers");
        add(panel);
        addMouseListener(panel);
        pack();
        setLocationRelativeTo(null);
        try {
            setIconImage(ImageIO.read(new File("src/ru/vsu/pustoslov/assets/images/crown.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setVisible(true);
    }
}
