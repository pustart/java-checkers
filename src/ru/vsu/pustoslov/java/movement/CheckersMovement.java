package ru.vsu.pustoslov.java.movement;

import java.util.ArrayList;
import java.util.List;
import ru.vsu.pustoslov.java.board.Board;
import ru.vsu.pustoslov.java.colors.CheckerColors;
import ru.vsu.pustoslov.java.figures.Checker;
import ru.vsu.pustoslov.java.figures.Figure;
import ru.vsu.pustoslov.java.figures.King;
import ru.vsu.pustoslov.java.player.Player;

public class CheckersMovement extends AbstractMovement {
    public CheckersMovement(Board board) {
        super(board);
    }

    public void move(int row, int col, int prevRow, int prevCol, Player player) {

        if (!getBoard().getCellFromBoard(prevRow, prevCol).hasFigure()) {
            return;
        }

        if (!player.getCheckers().contains(getBoard().getCellFromBoard(prevRow, prevCol).getFigure())) {
            return;
        }

        if (getBoard().getCellFromBoard(prevRow, prevCol).getFigure().getPossibleMoves(getBoard()).size() == 0) {
            return;
        }

        if (!getBoard().getCellFromBoard(prevRow, prevCol).getFigure().getPossibleMoves(getBoard()).contains(getBoard().getCellFromBoard(row, col))) {
            return;
        }

        if (canBecomeKing(row, col, prevRow, prevCol) && !(getBoard().getCellFromBoard(prevRow, prevCol).getFigure() instanceof King)) {
            if (getBoard().getCellFromBoard(prevRow, prevCol).getFigure().getColor() == CheckerColors.WHITE) {
                getBoard().getCellFromBoard(row, col).setFigure(new King(col, row, CheckerColors.WHITE));
                getBoard().getCellFromBoard(prevRow, prevCol).setFigure(null);
                getBoard().getWhiteFigures().add(getBoard().getCellFromBoard(row, col).getFigure());
            } else if (getBoard().getCellFromBoard(prevRow, prevCol).getFigure().getColor() == CheckerColors.RED) {
                getBoard().getCellFromBoard(row, col).setFigure(new King(col, row, CheckerColors.RED));
                getBoard().getCellFromBoard(prevRow, prevCol).setFigure(null);
                getBoard().getRedFigures().add(getBoard().getCellFromBoard(row, col).getFigure());
            }
        } else {
            getBoard().getCellFromBoard(row, col).setFigure(getBoard().getCellFromBoard(prevRow, prevCol).getFigure());
            getBoard().getCellFromBoard(prevRow, prevCol).setFigure(null);
            getBoard().getCellFromBoard(row, col).getFigure().setY(row);
            getBoard().getCellFromBoard(row, col).getFigure().setX(col);
        }

        setMoveFlag(true);
    }

    public void beat(int row, int col, int prevRow, int prevCol, Player player) {

        if (!getBoard().getCellFromBoard(prevRow, prevCol).hasFigure()) {
            return;
        }

        if (!getBoard().getCellFromBoard(prevRow, prevCol).getFigure().canBeat(getBoard())) {
            return;
        }

        if (!player.getCheckers().contains(getBoard().getCellFromBoard(prevRow, prevCol).getFigure())) {
            return;
        }

        if (getBoard().getCellFromBoard(prevRow, prevCol).getFigure().getPossibleMoves(getBoard()).size() == 0) {
            return;
        }

        if (!getBoard().getCellFromBoard(prevRow, prevCol).getFigure().getPossibleMoves(getBoard()).contains(getBoard().getCellFromBoard(row, col))) {
            return;
        }

        if (canBecomeKing(row, col, prevRow, prevCol) && !(getBoard().getCellFromBoard(prevRow, prevCol).getFigure() instanceof King)) {
            if (getBoard().getCellFromBoard(prevRow, prevCol).getFigure().getColor() == CheckerColors.WHITE) {
                getBoard().getCellFromBoard(row, col).setFigure(new King(col, row, CheckerColors.WHITE));
                getBoard().getWhiteFigures().remove(getBoard().getCellFromBoard(prevRow, prevCol).getFigure());
                getBoard().getCellFromBoard(prevRow, prevCol).setFigure(null);
                getBoard().getWhiteFigures().add(getBoard().getCellFromBoard(row, col).getFigure());
                getBoard().getRedFigures().remove(getBoard().getCellFromBoard((row + prevRow) / 2, (col + prevCol) / 2).getFigure());
            } else if (getBoard().getCellFromBoard(prevRow, prevCol).getFigure().getColor() == CheckerColors.RED) {
                getBoard().getCellFromBoard(row, col).setFigure(new King(col, row, CheckerColors.RED));
                getBoard().getRedFigures().remove(getBoard().getCellFromBoard(prevRow, prevCol).getFigure());
                getBoard().getCellFromBoard(prevRow, prevCol).setFigure(null);
                getBoard().getRedFigures().add(getBoard().getCellFromBoard(row, col).getFigure());
                getBoard().getWhiteFigures().remove(getBoard().getCellFromBoard((row + prevRow) / 2, (col + prevCol) / 2).getFigure());

            }

            getBoard().getCellFromBoard((row + prevRow) / 2, (col + prevCol) / 2).setFigure(null);
        } else {
            getBoard().getCellFromBoard(row, col).setFigure(getBoard().getCellFromBoard(prevRow, prevCol).getFigure());
            getBoard().getCellFromBoard(prevRow, prevCol).setFigure(null);
            getBoard().getCellFromBoard(row, col).getFigure().setY(row);
            getBoard().getCellFromBoard(row, col).getFigure().setX(col);
            if (getBoard().getCellFromBoard(row, col).getFigure() instanceof King) {
                //Upper right
                if (row < prevRow && col > prevCol) {
                    while (prevRow > row && prevCol < col) {
                        getBoard().getCellFromBoard(prevRow, prevCol).setFigure(null);
                        prevRow -= 1;
                        prevCol += 1;
                    }
                }

                //Upper left
                if (row < prevRow && col < prevCol) {
                    while (prevRow > row && prevCol > col) {
                        getBoard().getCellFromBoard(prevRow, prevCol).setFigure(null);
                        prevRow -= 1;
                        prevCol -= 1;
                    }
                }

                //Lower right
                if (row > prevRow && col > prevCol) {
                    while (prevRow < row && prevCol < col) {
                        getBoard().getCellFromBoard(prevRow, prevCol).setFigure(null);
                        prevRow += 1;
                        prevCol += 1;
                    }
                }

                //Lower left
                if (row > prevRow && col < prevCol) {
                    while (prevRow < row && prevCol > col) {
                        getBoard().getCellFromBoard(prevRow, prevCol).setFigure(null);
                        prevRow += 1;
                        prevCol -= 1;
                    }
                }
            } else {
                getBoard().getCellFromBoard((row + prevRow) / 2, (col + prevCol) / 2).setFigure(null);
            }
        }

        setMoveFlag(true);
    }

    public boolean isGameOver(List<Figure> whiteFigures, List<Figure> redFigures) {
        List<Figure> availableCheckersP1 = new ArrayList<>();
        List<Figure> availableCheckersP2 = new ArrayList<>();

        for (Figure figure : whiteFigures) {
            if (figure.getPossibleMoves(getBoard()).size() > 0) {
                availableCheckersP1.add(figure);
            }
        }

        for (Figure figure : redFigures) {
            if (figure.getPossibleMoves(getBoard()).size() > 0) {
                availableCheckersP2.add(figure);
            }
        }
        return whiteFigures.size() == 0 || redFigures.size() == 0
                || availableCheckersP1.size() == 0 || availableCheckersP2.size() == 0;
    }

    public boolean doesWhiteWin(List<Figure> whiteFigures) {
        List<Figure> availableCheckersP1 = new ArrayList<>();

        for (Figure figure : whiteFigures) {
            if (figure.getPossibleMoves(getBoard()).size() > 0) {
                availableCheckersP1.add(figure);
            }
        }

        return availableCheckersP1.size() > 0;
    }

    public boolean hasMoved() {
        boolean flag = isMoveFlag();
        resetMovedFlag();
        return flag;
    }

    public void resetMovedFlag() {
        setMoveFlag(false);
    }

    private boolean canBecomeKing(int row, int col, int prevRow, int prevCol) {
        if (getBoard().getCellFromBoard(prevRow, prevCol).getFigure() instanceof Checker) {
            if (getBoard().getCellFromBoard(prevRow, prevCol).getFigure().getColor() == CheckerColors.WHITE && row == 0) {
                return true;
            }

            if (getBoard().getCellFromBoard(prevRow, prevCol).getFigure().getColor() == CheckerColors.RED && row == 7) {
                return true;
            }
        }
        return false;
    }
}
