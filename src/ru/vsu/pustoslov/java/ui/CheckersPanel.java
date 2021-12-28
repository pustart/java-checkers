package ru.vsu.pustoslov.java.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import ru.vsu.pustoslov.java.board.Cell;
import ru.vsu.pustoslov.java.colors.CheckerColors;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import ru.vsu.pustoslov.java.board.Board;
import javax.swing.JPanel;
import ru.vsu.pustoslov.java.colors.GeneralColors;
import ru.vsu.pustoslov.java.figures.King;
import ru.vsu.pustoslov.java.movement.CheckersMovement;
import ru.vsu.pustoslov.java.player.Player;

public class CheckersPanel extends JPanel implements MouseListener {
    private static final int PANEL_WIDTH = 640;
    private static final int PANEL_HEIGHT = 640;
    private int storedRow;
    private int storedCol;
    private Player currentPlayer;
    private final Board board;
    private final CheckersMovement checkersMovement;
    private final Player player1;
    private final Player player2;
    private BufferedImage crownImage;


    public CheckersPanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.WHITE);

        try {
            crownImage = ImageIO.read(new File("src/ru/vsu/pustoslov/assets/images/crown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        board = new Board(PANEL_HEIGHT);
        board.fillWithFigures();

        checkersMovement = new CheckersMovement(board);
        player1 = new Player(board.getWhiteFigures(), CheckerColors.WHITE);
        player2 = new Player(board.getRedFigures(), CheckerColors.RED);

        storedCol = 0;
        storedRow = 0;

        currentPlayer = player1;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int col = (e.getX()) / (board.getHeight() / 8);
        int row = (e.getY() - 30) / (board.getWidth() / 8);

        resetHighlightedCells();
        if (board.getCellFromBoard(row, col).hasFigure()
                && board.getCellFromBoard(row, col).getFigure().getColor() == currentPlayer.getTeamColor()) {
            highlightCells(board.getCellFromBoard(row, col).getFigure().getPossibleMoves(board));
        }

        checkersMovement.beat(row, col, storedRow, storedCol, currentPlayer);
        checkersMovement.move(row, col, storedRow, storedCol, currentPlayer);

        if (checkersMovement.hasMoved()) {
            System.out.println("has moved");
            switchPlayers();
        }

        storedRow = row;
        storedCol = col;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawBoard(g2d);
    }

    //todo отрисовка поля единожды, а потом только отрисовка шашек
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

    private void switchPlayers() {
        if (currentPlayer.equals(player1)) {
            System.out.println("log");
            currentPlayer = player2;
        } else if (currentPlayer.equals(player2)) {
            currentPlayer = player1;
        }
        System.out.println("Current player team color: " + currentPlayer.getTeamColor());
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
