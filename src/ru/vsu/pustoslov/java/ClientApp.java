package ru.vsu.pustoslov.java;

import java.io.IOException;
import ru.vsu.pustoslov.java.ui.CheckersFrame;

public class ClientApp {
    public static void main(String[] args) throws IOException {
        CheckersFrame checkersFrame = new CheckersFrame();
        checkersFrame.createFrame("localhost", 9999);
    }
}
