package ru.vsu.pustoslov.java.movement;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import ru.vsu.pustoslov.java.board.Board;
import ru.vsu.pustoslov.java.figures.Figure;
import ru.vsu.pustoslov.java.player.Player;
import ru.vsu.pustoslov.java.server.ClientRequest;
import ru.vsu.pustoslov.java.server.Requests;
import ru.vsu.pustoslov.java.server.ServerResponse;

public class NetworkMovement extends AbstractMovement {
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public NetworkMovement(Socket socket) {
        super(socket);
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ServerResponse serverResponse = (ServerResponse) in.readObject();
            Board board = serverResponse.getBoard();
            setBoard(board);
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void move(int row, int col, int prevRow, int prevCol, Player player) {
        try {
            out.writeObject(new ClientRequest(Requests.MOVE, row, col, prevRow, prevCol));
            ServerResponse serverResponse = (ServerResponse) in.readObject();
            setBoard(serverResponse.getBoard());
            out.reset();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void beat(int row, int col, int prevRow, int prevCol, Player player) {
        try {
            out.writeObject(new ClientRequest(Requests.BEAT, row, col, prevRow, prevCol));
            ServerResponse serverResponse = (ServerResponse) in.readObject();
            setBoard(serverResponse.getBoard());
            out.reset();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean hasMoved() {
        return false;
    }

    @Override
    public boolean doesWhiteWin(List<Figure> whiteFigures) {
        return false;
    }

    @Override
    public boolean isGameOver(List<Figure> whiteFigures, List<Figure> redFigures) {
        return false;
    }
}
