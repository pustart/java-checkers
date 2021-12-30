package ru.vsu.pustoslov.java.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
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
            out.writeObject(board);
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!checkersMovement.isGameOver(player1, player2)) {
            System.out.println("---Player figures: " + player1.getCheckers() + "\n");
            System.out.println("---Bot figures: " + player2.getCheckers());

            Integer[] arrOfMoves = new Integer[4];

            try {
                System.out.println("Into try IN");
                arrOfMoves = (Integer[]) in.readObject();
                System.out.println("Read array from client");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            System.out.println("Client array: ");
            Arrays.stream(arrOfMoves).forEach(System.out::println);


            checkersMovement.beat(arrOfMoves[0], arrOfMoves[1], arrOfMoves[2], arrOfMoves[3], player1);
            checkersMovement.move(arrOfMoves[0], arrOfMoves[1], arrOfMoves[2], arrOfMoves[3], player1);

            if (checkersMovement.hasMoved()) {
                System.out.println("--In bot move section");

                Figure figureToMove = player2.getStrategy().pickFigure(board, board.getRedFigures());
                Cell cellToMove = player2.getStrategy().pickCellToMove(figureToMove.getPossibleMoves(board));

                System.out.println("Picked figure: " + figureToMove);
                System.out.println("Picked cell: " + cellToMove);
                System.out.println("-------");


                checkersMovement.beat(cellToMove.getRow(), cellToMove.getCol(), figureToMove.getY(), figureToMove.getX(), player2);
                checkersMovement.move(cellToMove.getRow(), cellToMove.getCol(), figureToMove.getY(), figureToMove.getX(), player2);
                checkersMovement.resetMovedFlag();
            }

            try {
                System.out.println("Into try OUT");
                out.writeObject(board);
                out.reset();
                System.out.println("Post board to client\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Game Over!");
    }
}
