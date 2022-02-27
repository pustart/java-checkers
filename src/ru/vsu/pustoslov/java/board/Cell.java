package ru.vsu.pustoslov.java.board;

import java.io.Serializable;
import ru.vsu.pustoslov.java.colors.GeneralColors;
import ru.vsu.pustoslov.java.figures.Figure;

public class Cell implements Serializable {
    private final int height;
    private final int width;
    private final int x;
    private final int row;
    private final int col;
    private final int y;
    private Figure figure = null;

    private GeneralColors cellColor;

    public Cell(int row, int col, int x, int y, int height, GeneralColors cellColor) {
        this.row = row;
        this.col = col;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = height;
        this.cellColor = cellColor;
    }

    public boolean hasFigure() {
        return figure != null;
    }

    public Figure getFigure() {
        return figure;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public GeneralColors getCellColor() {
        return cellColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setCellColor(GeneralColors cellColor) {
        this.cellColor = cellColor;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "row=" + row +
                ", col=" + col +
                ", cellColor=" + cellColor +
                '}';
    }
}
