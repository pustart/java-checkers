package ru.vsu.pustoslov.java;

import java.io.IOException;

public class AppClient2 {
    public static void main(String[] args) throws IOException {
        ClientFrame clientFrame = new ClientFrame();
        clientFrame.createFrame("localhost", 9999);
    }
}
