package ru.vsu.pustoslov.java.board;

import java.io.Serializable;
import ru.vsu.pustoslov.java.colors.CheckerColors;
import ru.vsu.pustoslov.java.colors.GeneralColors;
import ru.vsu.pustoslov.java.figures.Checker;
import ru.vsu.pustoslov.java.figures.Figure;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {
    public static final int SIZE = 8;
    private final int height;
    private final int width;
    private final Cell[][] gameField;
    private final List<Figure> redFigures = new ArrayList<>();
    private final List<Figure> whiteFigures = new ArrayList<>();

    public Board(int height) {
        this.height = (height / SIZE) * SIZE;
        this.width = (height / SIZE) * SIZE;
        this.gameField = fillWithCells();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<Figure> getRedFigures() {
        return redFigures;
    }

    public List<Figure> getWhiteFigures() {
        return whiteFigures;
    }

    public Cell[][] getGameField() {
        return gameField;
    }

    public Cell getCellFromBoard(int y, int x) {
        return gameField[y][x];
    }

    public void fillWithFigures() {
        for (int y = 0; y < gameField.length; y++) {
            if (y == 3 || y == 4) {
                continue;
            }

            for (int x = 0; x < gameField[0].length; x++) {
                if (gameField[y][x].getCellColor() == GeneralColors.BLACK_CELL) {
                    if (y < 3) {
                        redFigures.add(new Checker(x, y, CheckerColors.RED));
                        gameField[y][x].setFigure(redFigures.get(redFigures.size() - 1));
                    } else {
                        whiteFigures.add(new Checker(x, y, CheckerColors.WHITE));
                        gameField[y][x].setFigure(whiteFigures.get(whiteFigures.size() - 1));
                    }
                }
            }
        }
    }

    private Cell[][] fillWithCells() {
        Cell[][] cellField = new Cell[SIZE][SIZE];
        int cellSize = height / SIZE;
        int yCounter = 0;
        for (int y = 0; y < cellField.length; y++) {
            int xCounter = 0;
            for (int x = 0; x < cellField[0].length; x++) {
                if ((y + x) % 2 == 0) {
                    cellField[y][x] = new Cell(y, x, xCounter, yCounter, cellSize, GeneralColors.WHITE_CELL);
                } else {
                    cellField[y][x] = new Cell(y, x, xCounter, yCounter, cellSize, GeneralColors.BLACK_CELL);
                }

                xCounter += cellSize;
            }
            yCounter += cellSize;
        }

        return cellField;
    }
}
