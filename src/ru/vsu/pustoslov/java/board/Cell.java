package ru.vsu.pustoslov.java.board;

import java.io.Serializable;
import ru.vsu.pustoslov.java.colors.GeneralColors;
import ru.vsu.pustoslov.java.figures.Figure;

public class Cell implements Serializable {
    private final int height;
    private final int width;
    private final int x;
    private final int y;
    private Figure figure = null;

    private GeneralColors cellColor;

    public Cell(int x, int y, int height, GeneralColors cellColor) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = height;
        this.cellColor = cellColor;
    }

    public Cell(int x, int y, int height, GeneralColors cellColor, Figure figure) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = height;
        this.cellColor = cellColor;
        this.figure = figure;
    }

    //Возможно лишний метод
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

    public void setCellColor(GeneralColors cellColor) {
        this.cellColor = cellColor;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }
}
