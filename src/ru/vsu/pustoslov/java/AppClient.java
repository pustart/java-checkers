package ru.vsu.pustoslov.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ru.vsu.pustoslov.java.board.Board;
import ru.vsu.pustoslov.java.board.Cell;
import ru.vsu.pustoslov.java.colors.CheckerColors;
import ru.vsu.pustoslov.java.colors.GeneralColors;
import ru.vsu.pustoslov.java.figures.King;
import ru.vsu.pustoslov.java.movement.CheckersMovement;
import ru.vsu.pustoslov.java.player.Player;

public class AppClient extends JPanel implements MouseListener {
    private final String host;
    private final int port;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private static final int PANEL_WIDTH = 640;
    private static final int PANEL_HEIGHT = 640;
    private int storedRow;
    private int storedCol;
    private Board board;
    private BufferedImage crownImage;

    public static void main(String[] args) throws IOException {
        AppClient client = new AppClient("localhost", 9999);
        client.start();
    }

    public AppClient (String host, int port) throws IOException {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        socket = new Socket(host, port);

        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.WHITE);
        addMouseListener(this);
        setVisible(true);

        try {
            crownImage = ImageIO.read(new File("src/ru/vsu/pustoslov/assets/images/crown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        storedCol = 0;
        storedRow = 0;
        System.out.println("in start after setvisible");

        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());

        try {
            board = (Board) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    /*public void start() throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        String command;
        while (!socket.isClosed() && (command = in.readLine()) != null) {
            String[] parsed = command.split(Command.SEPARATOR);
            System.out.println("From server:"+command);
            if (Command.END.getCommandString().equals(parsed[0])) {
                socket.close();
            }
            if (Command.BET.getCommandString().equals(parsed[0])) {
                System.out.print("Enter your bet (up to "+parsed[1]+"): ");
                int nextInt = new Scanner(System.in).nextInt();
                String resp = Command.RESP.getCommandString()+ Command.SEPARATOR + nextInt;
                System.out.println("To server:"+resp);
                out.println(resp);
            }
        }
    }*/

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!socket.isClosed()) {
            try {
                board = (Board) in.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            int col = (e.getX()) / (board.getHeight() / 8);
            int row = (e.getY() - 30) / (board.getWidth() / 8);

            Integer[] arrOfMoves = new Integer[4];
            arrOfMoves[0] = row;
            arrOfMoves[1] = col;
            arrOfMoves[2] = storedRow;
            arrOfMoves[3] = storedCol;

            resetHighlightedCells();
            if (board.getCellFromBoard(row, col).hasFigure()
                    && board.getCellFromBoard(row, col).getFigure().getColor() == CheckerColors.WHITE) {
                highlightCells(board.getCellFromBoard(row, col).getFigure().getPossibleMoves(board));
            }

            try {
                out.writeObject(arrOfMoves);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            storedRow = row;
            storedCol = col;
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawBoard(g2d);
    }

    private void drawBoard(Graphics2D g2d) {
        for (int i = 0; i < board.getGameField().length; i++) {
            for (int j = 0; j < board.getGameField()[0].length; j++) {
                g2d.setColor(board.getCellFromBoard(i, j).getCellColor().getColor());
                g2d.fillRect(board.getCellFromBoard(i, j).getX(),
                        board.getCellFromBoard(i, j).getY(),
                        board.getCellFromBoard(i, j).getWidth(),
                        board.getCellFromBoard(i, j).getHeight());

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                if (board.getCellFromBoard(i, j).hasFigure()) {
                    switch (board.getCellFromBoard(i, j).getFigure().getColor()) {
                        case RED -> {
                            g2d.setColor(CheckerColors.RED.getColor());
                            g2d.fillOval(board.getCellFromBoard(i, j).getX(),
                                    board.getCellFromBoard(i, j).getY(),
                                    board.getCellFromBoard(i, j).getWidth(),
                                    board.getCellFromBoard(i, j).getHeight());
                        }
                        case WHITE -> {
                            g2d.setColor(CheckerColors.WHITE.getColor());
                            g2d.fillOval(board.getCellFromBoard(i, j).getX(),
                                    board.getCellFromBoard(i, j).getY(),
                                    board.getCellFromBoard(i, j).getWidth(),
                                    board.getCellFromBoard(i, j).getHeight());
                        }
                    }

                    if (board.getCellFromBoard(i, j).getFigure() instanceof King) {
                        g2d.drawImage(crownImage, board.getCellFromBoard(i, j).getX() + 12,
                                board.getCellFromBoard(i, j).getY() + 10,
                                board.getCellFromBoard(i, j).getWidth() - 24,
                                board.getCellFromBoard(i, j).getHeight() - 24,
                                null);
                    }
                }
            }
        }
    }

    private void highlightCells(List<Cell> cells) {
        for (Cell currCell : cells) {
            currCell.setCellColor(GeneralColors.HIGHLIGHTING);
        }
    }

    private void resetHighlightedCells() {
        for (int i = 0; i < board.getGameField().length; i++) {
            for (int j = 0; j < board.getGameField().length; j++) {
                if (board.getCellFromBoard(i, j).getCellColor() == GeneralColors.HIGHLIGHTING) {
                    board.getCellFromBoard(i, j).setCellColor(GeneralColors.BLACK_CELL);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
