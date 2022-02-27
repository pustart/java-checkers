package ru.vsu.pustoslov.java.server;

import java.io.Serializable;
import ru.vsu.pustoslov.java.board.Board;

public class ServerResponse implements Serializable {
    private final GameStates gameState;
    private final Board board;

    public ServerResponse(GameStates gameState, Board board) {
        this.gameState = gameState;
        this.board = board;
    }

    public GameStates getGameState() {
        return gameState;
    }

    public Board getBoard() {
        return board;
    }
}
