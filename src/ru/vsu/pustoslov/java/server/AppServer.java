package ru.vsu.pustoslov.java.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AppServer {
    private final ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            AppServer server = new AppServer(port);
            server.start();
        } catch (IOException e) {
            throw new IllegalStateException("Can't start the server. ", e);
        }
    }

    public AppServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("Game server started");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from: "+ clientSocket.getInetAddress());
            GameSession gameSession = new GameSession(clientSocket);
            Thread t = new Thread(gameSession);
            t.start();
        }
    }
}
