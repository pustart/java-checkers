package ru.vsu.pustoslov.java.ui;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class CheckersFrame extends JFrame {
    public void createFrame(){
        final CheckersPanel panel = new CheckersPanel();
        add(panel);
        addMouseListener(panel);
        setFrameDefaults();
        setVisible(true);
    }

    public void createFrame(String host, int port) throws IOException{
        final CheckersPanel panel = new CheckersPanel(host, port);
        add(panel);
        addMouseListener(panel);
        setFrameDefaults();
        setVisible(true);
    }

    private void setFrameDefaults() {
        setTitle("Checkers");
        pack();
        setLocationRelativeTo(null);
        try {
            setIconImage(ImageIO.read(new File("src/ru/vsu/pustoslov/assets/images/crown.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
