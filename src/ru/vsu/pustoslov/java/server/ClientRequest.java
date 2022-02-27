package ru.vsu.pustoslov.java.server;

import java.io.Serializable;
import ru.vsu.pustoslov.java.board.Board;

public class ClientRequest implements Serializable {
    private final Requests request;
    private final int row;
    private final int col;
    private final int storedRow;
    private final int storedCol;

    public ClientRequest(Requests request, int row, int col, int storedRow, int storedCol) {
        this.request = request;
        this.row = row;
        this.col = col;
        this.storedRow = storedRow;
        this.storedCol = storedCol;
    }

    public Requests getRequest() {
        return request;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getStoredRow() {
        return storedRow;
    }

    public int getStoredCol() {
        return storedCol;
    }
}
