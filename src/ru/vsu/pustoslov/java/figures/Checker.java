package ru.vsu.pustoslov.java.figures;

import java.util.ArrayList;
import ru.vsu.pustoslov.java.colors.CheckerColors;
import ru.vsu.pustoslov.java.board.Board;
import ru.vsu.pustoslov.java.board.Cell;
import java.util.List;

public class Checker extends Figure {
    public Checker(int x, int y, CheckerColors color) {
        super(x, y, color);
    }

    @Override
    public List<Cell> getPossibleMoves(Board board) {
        List<Cell> cellsToMove = new ArrayList<>();

        if (getColor() == CheckerColors.WHITE) {
            //Проверяем левую верхнюю клетку на наличие красной вражеской фигуры
            if (getY() - 1 >= 0 && getX() - 1 >= 0 && board.getCellFromBoard(getY() - 1, getX() - 1).hasFigure()
                    && board.getCellFromBoard(getY() - 1, getX() - 1).getFigure().getColor() == CheckerColors.RED) {
                //Если клетка левее свободна, то возможен бой -> добавляем поле в массив
                if (getY() - 2 >= 0 && getX() - 2 >= 0 && !board.getCellFromBoard(getY() - 2, getX() - 2).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() - 2, getX() - 2));
                }
            }

            //Проверяем правую верхнюю клетку на наличие красной вражеской фигуры
            if (getY() - 1 >= 0 && getX() + 1 < Board.SIZE && board.getCellFromBoard(getY() - 1, getX() + 1).hasFigure()
                    && board.getCellFromBoard(getY() - 1, getX() + 1).getFigure().getColor() == CheckerColors.RED) {
                //Если клетка правее свободна, то возможен бой -> добавляем поле в массив
                if (getY() - 2 >= 0 && getX() + 2 < Board.SIZE && !board.getCellFromBoard(getY() - 2, getX() + 2).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() - 2, getX() + 2));
                }
            }

            //Если бой невозможен
            if (cellsToMove.size() == 0) {
                //Проверяем свободна ли левая верхняя клетка
                if (getY() - 1 >= 0 && getX() - 1 >= 0 && !board.getCellFromBoard(getY() - 1, getX() - 1).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() - 1, getX() - 1));
                }

                //Проверяем свободна ли правая верхняя клетка
                if (getY() - 1 >= 0 && getX() + 1 < Board.SIZE && !board.getCellFromBoard(getY() - 1, getX() + 1).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() - 1, getX() + 1));
                }
            }
        }

        if (getColor() == CheckerColors.RED) {
            //Проверяем левую нижней клетку на наличие белой вражеской фигуры
            if (getY() + 1  < Board.SIZE && getX() - 1 >= 0 && board.getCellFromBoard(getY() + 1, getX() - 1).hasFigure()
                    && board.getCellFromBoard(getY() + 1, getX() - 1).getFigure().getColor() == CheckerColors.WHITE) {
                //Если клетка левее свободна, то возможен бой -> добавляем поле в массив
                if (getY() + 2 < Board.SIZE && getX() - 2 >= 0 && !board.getCellFromBoard(getY() + 2, getX() - 2).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() + 2, getX() - 2));
                }
            }

            //Проверяем правую нижнюю клетку на наличие красной белой фигуры
            if (getY() + 1 < Board.SIZE && getX() + 1 < Board.SIZE && board.getCellFromBoard(getY() + 1, getX() + 1).hasFigure()
                    && board.getCellFromBoard(getY() + 1, getX() + 1).getFigure().getColor() == CheckerColors.WHITE) {
                //Если клетка правее свободна, то возможен бой -> добавляем поле в массив
                if (getY() + 2 < Board.SIZE && getX() + 2 < Board.SIZE && !board.getCellFromBoard(getY() + 2, getX() + 2).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() + 2, getX() + 2));
                }
            }

            //Если бой невозможен
            if (cellsToMove.size() == 0) {
                //Проверяем свободна ли левая верхняя клетка
                if (getY() + 1 < Board.SIZE && getX() - 1 >= 0 && !board.getCellFromBoard(getY() + 1, getX() - 1).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() + 1, getX() - 1));
                }

                //Проверяем свободна ли левая правая клетка
                if (getY() + 1 < Board.SIZE && getX() + 1 < Board.SIZE && !board.getCellFromBoard(getY() + 1, getX() + 1).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() + 1, getX() + 1));
                }
            }
        }

        return cellsToMove;
    }

    //todo отрефакторить и разбить на несколько приватных методов проверки клеток
    @Override
    public boolean canBeat(Board board) {
        if (getColor() == CheckerColors.WHITE) {
            if (getY() - 1 >= 0 && getX() - 1 >= 0 && board.getCellFromBoard(getY() - 1, getX() - 1).hasFigure()
                    && board.getCellFromBoard(getY() - 1, getX() - 1).getFigure().getColor() == CheckerColors.RED) {
                if (getY() - 2 >= 0 && getX() - 2 >= 0 && !board.getCellFromBoard(getY() - 2, getX() - 2).hasFigure()) {
                    return true;
                }
            }

            //Проверяем правую верхнюю клетку на наличие красной вражеской фигуры
            if (getY() - 1 >= 0 && getX() + 1 < Board.SIZE && board.getCellFromBoard(getY() - 1, getX() + 1).hasFigure()
                    && board.getCellFromBoard(getY() - 1, getX() + 1).getFigure().getColor() == CheckerColors.RED) {
                if (getY() - 2 >= 0 && getX() + 2 < Board.SIZE && !board.getCellFromBoard(getY() - 2, getX() + 2).hasFigure()) {
                    return true;
                }
            }
        }

        if (getColor() == CheckerColors.RED) {
            //Проверяем левую нижней клетку на наличие белой вражеской фигуры
            if (getY() + 1  < Board.SIZE && getX() - 1 >= 0 && board.getCellFromBoard(getY() + 1, getX() - 1).hasFigure()
                    && board.getCellFromBoard(getY() + 1, getX() - 1).getFigure().getColor() == CheckerColors.WHITE) {
                //Если клетка левее свободна, то возможен бой -> добавляем поле в массив
                if (getY() + 2 < Board.SIZE && getX() - 2 >= 0 && !board.getCellFromBoard(getY() + 2, getX() - 2).hasFigure()) {
                    return true;
                }
            }

            //Проверяем правую нижнюю клетку на наличие красной белой фигуры
            if (getY() + 1 < Board.SIZE && getX() + 1 < Board.SIZE && board.getCellFromBoard(getY() + 1, getX() + 1).hasFigure()
                    && board.getCellFromBoard(getY() + 1, getX() + 1).getFigure().getColor() == CheckerColors.WHITE) {
                //Если клетка правее свободна, то возможен бой -> добавляем поле в массив
                if (getY() + 2 < Board.SIZE && getX() + 2 < Board.SIZE && !board.getCellFromBoard(getY() + 2, getX() + 2).hasFigure()) {
                    return true;
                }
            }
        }
        return false;
    }
}
