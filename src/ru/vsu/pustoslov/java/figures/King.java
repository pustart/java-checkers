package ru.vsu.pustoslov.java.figures;

import java.util.ArrayList;
import ru.vsu.pustoslov.java.colors.CheckerColors;
import ru.vsu.pustoslov.java.board.Board;
import ru.vsu.pustoslov.java.board.Cell;
import java.util.List;

public class King extends Figure {
    private boolean canKill = false;

    public King(int x, int y, CheckerColors color) {
        super(x, y, color);
    }

    @Override
    public List<Cell> getPossibleMoves(Board board) {
        List<Cell> cellsToMove = new ArrayList<>();
        List<Cell> cellsToBeat = new ArrayList<>();
        boolean killFlag = false;

        System.out.println("row y: " + getY());
        System.out.println("col x: " + getX());
        //идем вправо вверх
        int x = getX() + 1;
        int y = getY() - 1;

        while (x < Board.SIZE && y >= 0) {
            if (!board.getCellFromBoard(y, x).hasFigure()) {
                cellsToMove.add(board.getCellFromBoard(y, x));
            } else if (board.getCellFromBoard(y, x).getFigure().getColor() != getColor()) {
                if (x + 1 < Board.SIZE && y - 1 >= 0 && !board.getCellFromBoard(y - 1, x + 1).hasFigure()) {
                    cellsToMove.clear();
                    cellsToMove.add(board.getCellFromBoard(y - 1, x + 1));
                    killFlag = true;
                } else if (x + 1 < Board.SIZE && y - 1 >= 0 && board.getCellFromBoard(y - 1, x + 1).hasFigure()) {
                    break;
                }
            } else if (board.getCellFromBoard(y, x).getFigure().getColor() == getColor()) {
                break;
            }
            x += 1;
            y -= 1;
        }

        if (killFlag) {
            cellsToBeat.addAll(cellsToMove);
            killFlag = false;
        }

        //влево вверх
        x = getX() - 1;
        y = getY() - 1;

        while (x >= 0 && y >= 0) {
            if (!board.getCellFromBoard(y, x).hasFigure()) {
                cellsToMove.add(board.getCellFromBoard(y, x));
            } else if (board.getCellFromBoard(y, x).getFigure().getColor() != getColor()) {
                if (x - 1 >= 0 && y - 1 >= 0 && !board.getCellFromBoard(y - 1, x - 1).hasFigure()) {
                    cellsToMove.clear();
                    cellsToMove.add(board.getCellFromBoard(y - 1, x - 1));
                    killFlag = true;
                } else if (x - 1 >= 0 && y - 1 >= 0 && board.getCellFromBoard(y - 1, x - 1).hasFigure()) {
                    break;
                }
            } else if (board.getCellFromBoard(y, x).getFigure().getColor() == getColor()) {
                break;
            }
            x -= 1;
            y -= 1;
        }

        if (killFlag) {
            cellsToBeat.addAll(cellsToMove);
            killFlag = false;
        }

        //влево вниз
        x = getX() - 1;
        y = getY() + 1;

        while (x >= 0 && y < Board.SIZE) {
            if (!board.getCellFromBoard(y, x).hasFigure()) {
                cellsToMove.add(board.getCellFromBoard(y, x));
            } else if (board.getCellFromBoard(y, x).getFigure().getColor() != getColor()) {
                if (x - 1 >= 0 && y + 1 < Board.SIZE && !board.getCellFromBoard(y + 1, x - 1).hasFigure()) {
                    cellsToMove.clear();
                    cellsToMove.add(board.getCellFromBoard(y + 1, x - 1));
                    killFlag = true;
                } else if (x - 1 >= 0 && y + 1 < Board.SIZE && board.getCellFromBoard(y + 1, x - 1).hasFigure()) {
                    break;
                }
            } else if (board.getCellFromBoard(y, x).getFigure().getColor() == getColor()) {
                break;
            }
            x -= 1;
            y += 1;
        }

        if (killFlag) {
            cellsToBeat.addAll(cellsToMove);
            killFlag = false;
        }

        //вправо вниз
        x = getX() + 1;
        y = getY() + 1;

        while (x < Board.SIZE && y < Board.SIZE) {
            if (!board.getCellFromBoard(y, x).hasFigure()) {
                cellsToMove.add(board.getCellFromBoard(y, x));
            } else if (board.getCellFromBoard(y, x).getFigure().getColor() != getColor()) {
                if (x + 1 < Board.SIZE && y + 1 < Board.SIZE && !board.getCellFromBoard(y + 1, x + 1).hasFigure()) {
                    cellsToMove.clear();
                    cellsToMove.add(board.getCellFromBoard(y + 1, x + 1));
                    killFlag = true;
                } else if (x + 1 < Board.SIZE && y + 1 < Board.SIZE && board.getCellFromBoard(y + 1, x + 1).hasFigure()) {
                    break;
                }
            } else if (board.getCellFromBoard(y, x).getFigure().getColor() == getColor()) {
                break;
            }
            x += 1;
            y += 1;
        }

        if (killFlag) {
            cellsToBeat.addAll(cellsToMove);
        }

        if (cellsToBeat.size() > 0) {
            canKill = true;
            return cellsToBeat;
        }

        return cellsToMove;
    }

    @Override
    public boolean canBeat(Board board) {
        getPossibleMoves(board);
        boolean kill = canKill;
        canKill = false;
        return kill;
    }
}
