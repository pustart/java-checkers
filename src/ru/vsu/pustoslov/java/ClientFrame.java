package ru.vsu.pustoslov.java;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import ru.vsu.pustoslov.java.ui.CheckersPanel;

public class ClientFrame extends JFrame {
    public void createFrame(String host, int port) throws IOException {
        final ClientPanel panel = new ClientPanel("localhost", 9999);

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
