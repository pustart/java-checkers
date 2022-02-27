package ru.vsu.pustoslov.java.movement;

import java.net.Socket;
import java.util.List;
import ru.vsu.pustoslov.java.board.Board;
import ru.vsu.pustoslov.java.figures.Figure;
import ru.vsu.pustoslov.java.player.Player;

public abstract class AbstractMovement {
    private Board board;
    private boolean moveFlag = false;
    private Socket socket;

    public AbstractMovement(Board board) {
        this.board = board;
    }

    public AbstractMovement(Socket socket) {
        this.socket = socket;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isMoveFlag() {
        return moveFlag;
    }

    public void setMoveFlag(boolean moveFlag) {
        this.moveFlag = moveFlag;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void resetMovedFlag() {
        moveFlag = false;
    }

    public abstract void move(int row, int col, int prevRow, int prevCol, Player player);

    public abstract void beat(int row, int col, int prevRow, int prevCol, Player player);

    public abstract boolean hasMoved();

    public abstract boolean doesWhiteWin (List<Figure> whiteFigures);

    public abstract boolean isGameOver(List<Figure> whiteFigures, List<Figure> redFigures);

    public Socket getSocket() {
        return socket;
    }
}
