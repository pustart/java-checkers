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
            //Checking the upper left cell for a red enemy figure
            if (getY() - 1 >= 0 && getX() - 1 >= 0 && board.getCellFromBoard(getY() - 1, getX() - 1).hasFigure()
                    && board.getCellFromBoard(getY() - 1, getX() - 1).getFigure().getColor() == CheckerColors.RED) {
                //If the left cell is free, then fight is possible -> add the cell to the array
                if (getY() - 2 >= 0 && getX() - 2 >= 0 && !board.getCellFromBoard(getY() - 2, getX() - 2).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() - 2, getX() - 2));
                }
            }

            //Checking the upper right cell for a red enemy figure
            if (getY() - 1 >= 0 && getX() + 1 < Board.SIZE && board.getCellFromBoard(getY() - 1, getX() + 1).hasFigure()
                    && board.getCellFromBoard(getY() - 1, getX() + 1).getFigure().getColor() == CheckerColors.RED) {
                //If the right cell is free, then fight is possible -> add the cell to the array
                if (getY() - 2 >= 0 && getX() + 2 < Board.SIZE && !board.getCellFromBoard(getY() - 2, getX() + 2).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() - 2, getX() + 2));
                }
            }

            //If fight isn't possible
            if (cellsToMove.size() == 0) {
                //Checking if the upper left cell is free
                if (getY() - 1 >= 0 && getX() - 1 >= 0 && !board.getCellFromBoard(getY() - 1, getX() - 1).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() - 1, getX() - 1));
                }

                //Checking if the upper right cell is free
                if (getY() - 1 >= 0 && getX() + 1 < Board.SIZE && !board.getCellFromBoard(getY() - 1, getX() + 1).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() - 1, getX() + 1));
                }
            }
        }

        if (getColor() == CheckerColors.RED) {
            //Checking the lower left cell for a white enemy figure
            if (getY() + 1 < Board.SIZE && getX() - 1 >= 0 && board.getCellFromBoard(getY() + 1, getX() - 1).hasFigure()
                    && board.getCellFromBoard(getY() + 1, getX() - 1).getFigure().getColor() == CheckerColors.WHITE) {
                //If the cell to the left is free, then a fight is possible -> add the cell to the array
                if (getY() + 2 < Board.SIZE && getX() - 2 >= 0 && !board.getCellFromBoard(getY() + 2, getX() - 2).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() + 2, getX() - 2));
                }
            }

            //Checking the lower right cell for a white enemy figure
            if (getY() + 1 < Board.SIZE && getX() + 1 < Board.SIZE && board.getCellFromBoard(getY() + 1, getX() + 1).hasFigure()
                    && board.getCellFromBoard(getY() + 1, getX() + 1).getFigure().getColor() == CheckerColors.WHITE) {
                //If the right cell is free, then a fight is possible -> add the cell to the array
                if (getY() + 2 < Board.SIZE && getX() + 2 < Board.SIZE && !board.getCellFromBoard(getY() + 2, getX() + 2).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() + 2, getX() + 2));
                }
            }

            //If fight isn't possible
            if (cellsToMove.size() == 0) {
                //Checking if the upper left cell is free
                if (getY() + 1 < Board.SIZE && getX() - 1 >= 0 && !board.getCellFromBoard(getY() + 1, getX() - 1).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() + 1, getX() - 1));
                }

                //Checking if the upper right cell is free
                if (getY() + 1 < Board.SIZE && getX() + 1 < Board.SIZE && !board.getCellFromBoard(getY() + 1, getX() + 1).hasFigure()) {
                    cellsToMove.add(board.getCellFromBoard(getY() + 1, getX() + 1));
                }
            }
        }

        return cellsToMove;
    }

    @Override
    public boolean canBeat(Board board) {
        if (getColor() == CheckerColors.WHITE) {
            if (getY() - 1 >= 0 && getX() - 1 >= 0 && board.getCellFromBoard(getY() - 1, getX() - 1).hasFigure()
                    && board.getCellFromBoard(getY() - 1, getX() - 1).getFigure().getColor() == CheckerColors.RED) {
                if (getY() - 2 >= 0 && getX() - 2 >= 0 && !board.getCellFromBoard(getY() - 2, getX() - 2).hasFigure()) {
                    return true;
                }
            }

            //Checking the upper right cell for a red enemy figure
            if (getY() - 1 >= 0 && getX() + 1 < Board.SIZE && board.getCellFromBoard(getY() - 1, getX() + 1).hasFigure()
                    && board.getCellFromBoard(getY() - 1, getX() + 1).getFigure().getColor() == CheckerColors.RED) {
                if (getY() - 2 >= 0 && getX() + 2 < Board.SIZE && !board.getCellFromBoard(getY() - 2, getX() + 2).hasFigure()) {
                    return true;
                }
            }
        }

        if (getColor() == CheckerColors.RED) {
            //Checking the lower left cell for a white enemy piece
            if (getY() + 1 < Board.SIZE && getX() - 1 >= 0 && board.getCellFromBoard(getY() + 1, getX() - 1).hasFigure()
                    && board.getCellFromBoard(getY() + 1, getX() - 1).getFigure().getColor() == CheckerColors.WHITE) {
                //If the left cell is free, then a fight is possible -> add the cell to the array
                if (getY() + 2 < Board.SIZE && getX() - 2 >= 0 && !board.getCellFromBoard(getY() + 2, getX() - 2).hasFigure()) {
                    return true;
                }
            }

            //Checking the lower right cell for a white enemy piece
            if (getY() + 1 < Board.SIZE && getX() + 1 < Board.SIZE && board.getCellFromBoard(getY() + 1, getX() + 1).hasFigure()
                    && board.getCellFromBoard(getY() + 1, getX() + 1).getFigure().getColor() == CheckerColors.WHITE) {
                //If the right cell is free, then a fight is possible -> add the cell to the array
                return getY() + 2 < Board.SIZE && getX() + 2 < Board.SIZE && !board.getCellFromBoard(getY() + 2, getX() + 2).hasFigure();
            }
        }
        return false;
    }
}
