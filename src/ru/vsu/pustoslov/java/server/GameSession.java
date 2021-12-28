package ru.vsu.pustoslov.java.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import ru.vsu.pustoslov.java.board.Board;
import ru.vsu.pustoslov.java.board.Cell;
import ru.vsu.pustoslov.java.colors.CheckerColors;
import ru.vsu.pustoslov.java.figures.Figure;
import ru.vsu.pustoslov.java.movement.CheckersMovement;
import ru.vsu.pustoslov.java.player.Bot;
import ru.vsu.pustoslov.java.player.Player;
import ru.vsu.pustoslov.java.strategies.BotStrategy;

public class GameSession implements Runnable {
    private final Socket socket;
    private final CheckersMovement checkersMovement;
    private final Board board;
    private final Player player1;
    private final Bot player2;

    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public GameSession(Socket socket) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot connect to client", ex);
        }
        board = new Board(640);
        board.fillWithFigures();
        try {
            out.writeObject(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkersMovement = new CheckersMovement(board);
        player1 = new Player(board.getWhiteFigures(), CheckerColors.WHITE);
        player2 = new Bot(board.getRedFigures(), CheckerColors.RED, new BotStrategy());
    }

    @Override
    public void run() {
        while (!checkersMovement.isGameOver(player1, player2)) {
            Integer[] arrOfMoves = new Integer[4];
            try {
                arrOfMoves = (Integer[]) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            checkersMovement.beat(arrOfMoves[0], arrOfMoves[1], arrOfMoves[2], arrOfMoves[3], player1);
            checkersMovement.move(arrOfMoves[0], arrOfMoves[1], arrOfMoves[2], arrOfMoves[3], player1);

            if (checkersMovement.hasMoved()) {
                Figure figureToMove = player2.getStrategy().pickFigure(board, player2.getCheckers());
                Cell cellToMove = player2.getStrategy().pickCellToMove(figureToMove.getPossibleMoves(board));

                checkersMovement.beat(cellToMove.getY(), cellToMove.getX(), figureToMove.getY(), cellToMove.getX(), player2);
                checkersMovement.move(cellToMove.getY(), cellToMove.getX(), figureToMove.getY(), cellToMove.getX(), player2);
            }

            try {
                out.writeObject(board);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Game Over!");
    }
}
