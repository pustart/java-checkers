package ru.vsu.pustoslov.java.strategies;

import java.util.List;
import ru.vsu.pustoslov.java.board.Board;
import ru.vsu.pustoslov.java.figures.Figure;

public interface Strategable {
    Figure pickFigure(Board board, List<Figure> figures);
}
