package ru.vsu.pustoslov.java.figures;

import java.io.Serializable;
import java.util.Comparator;
import ru.vsu.pustoslov.java.colors.CheckerColors;
import ru.vsu.pustoslov.java.board.Board;
import ru.vsu.pustoslov.java.board.Cell;
import java.util.List;

public abstract class Figure implements Comparable<Figure>, Serializable {
    private int x;
    private int y;
    private CheckerColors color;

    public Figure(int x, int y, CheckerColors color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CheckerColors getColor() {
        return color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract List<Cell> getPossibleMoves(Board board);

    public abstract boolean canBeat(Board board);


    @Override
    public int compareTo(Figure o) {
        return Comparator.comparing(Figure::getX)
                .thenComparing(Figure::getY)
                .compare(this, o);
    }

    @Override
    public String toString() {
        return "Figure{" +
                "x=" + x +
                ", y=" + y +
                ", color=" + color +
                '}';
    }
}
