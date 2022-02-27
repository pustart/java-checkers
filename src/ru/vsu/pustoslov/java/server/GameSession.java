package ru.vsu.pustoslov.java.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

        board = new Board(640);
        board.fillWithFigures();

        checkersMovement = new CheckersMovement(board);

        player1 = new Player(board.getWhiteFigures(), CheckerColors.WHITE);
        player2 = new Bot(board.getRedFigures(), CheckerColors.RED, new BotStrategy());

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot connect to client", ex);
        }
        try {
            out.writeObject(new ServerResponse(GameStates.START, board));
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!checkersMovement.isGameOver(player1.getCheckers(), player2.getCheckers())) {
            ClientRequest clientRequest;
            Integer[] arrOfMoves = new Integer[4];

            try {
                clientRequest = (ClientRequest) in.readObject();
                arrOfMoves[0] = clientRequest.getRow();
                arrOfMoves[1] = clientRequest.getCol();
                arrOfMoves[2] = clientRequest.getStoredRow();
                arrOfMoves[3] = clientRequest.getStoredCol();

                System.out.print("Get array from client: ");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            checkersMovement.beat(arrOfMoves[0], arrOfMoves[1], arrOfMoves[2], arrOfMoves[3], player1);
            checkersMovement.move(arrOfMoves[0], arrOfMoves[1], arrOfMoves[2], arrOfMoves[3], player1);

            if (checkersMovement.hasMoved()) {
                Figure figureToMove = player2.getStrategy().pickFigure(board, board.getRedFigures());
                Cell cellToMove = player2.getStrategy().pickCellToMove(figureToMove.getPossibleMoves(board));

                checkersMovement.beat(cellToMove.getRow(), cellToMove.getCol(), figureToMove.getY(), figureToMove.getX(), player2);
                checkersMovement.move(cellToMove.getRow(), cellToMove.getCol(), figureToMove.getY(), figureToMove.getX(), player2);
                checkersMovement.resetMovedFlag();
            }

            try {
                out.writeObject(new ServerResponse(GameStates.MOVE, board));
                out.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (checkersMovement.doesWhiteWin(player1.getCheckers())) {
            try {
                out.writeObject(new ServerResponse(GameStates.WIN, board));
                out.reset();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Cannot close socket: " + e.getMessage());
                }
            }
        } else {
            try {
                out.writeObject(new ServerResponse(GameStates.LOSE, board));
                out.reset();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Cannot close socket: " + e.getMessage());
                }
            }
        }
    }
}
