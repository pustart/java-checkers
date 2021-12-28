package ru.vsu.pustoslov.java.strategies;

import java.util.ArrayList;
import java.util.List;
import ru.vsu.pustoslov.java.board.Board;
import ru.vsu.pustoslov.java.board.Cell;
import ru.vsu.pustoslov.java.figures.Figure;

public class BotStrategy  {
    /*public Cell pickCellToMove (Board board, List<Figure> figures) {
        Figure figure = pickFigure(board, figures);
        List<Cell> availableCells = figure.getPossibleMoves(board);
        return availableCells.get(chooseRandom(availableCells.size()));
    }*/

    public Figure pickFigure(Board board, List<Figure> figures) {
        List<Figure> availableCheckers = new ArrayList<>();
        List<Figure> attackersCheckers = new ArrayList<>();

        for (Figure figure : figures) {
            if (figure.canBeat(board)) {
                attackersCheckers.add(figure);
            }

            if (figure.getPossibleMoves(board).size() != 0) {
                availableCheckers.add(figure);
            }
        }

        if (attackersCheckers.size() > 0) {
            return attackersCheckers.get(chooseRandom(attackersCheckers.size()));
        }

        return availableCheckers.get(chooseRandom(availableCheckers.size()));
    }

    public Cell pickCellToMove(List<Cell> availableCells) {
        return availableCells.get(chooseRandom(availableCells.size()));
    }

    private int chooseRandom(int topBorder) {
        return (int) (Math.random() * topBorder);
    }
}
