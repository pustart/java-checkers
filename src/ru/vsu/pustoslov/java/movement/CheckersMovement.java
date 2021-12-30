package ru.vsu.pustoslov.java.movement;

import ru.vsu.pustoslov.java.board.Board;
import ru.vsu.pustoslov.java.colors.CheckerColors;
import ru.vsu.pustoslov.java.figures.Checker;
import ru.vsu.pustoslov.java.figures.Figure;
import ru.vsu.pustoslov.java.figures.King;
import ru.vsu.pustoslov.java.player.Player;

public class CheckersMovement {
    //Оперирует доской, описывает бой и ход
    //Куда воткнуть ис гейм овер??
    private final Board board;
    private boolean moveFlag = false;

    public CheckersMovement(Board board) {
        this.board = board;
    }

    //Метод для логов на этапе разработки, потом вычистить
    public Board getBoard() {
        return board;
    }

    public void move(int row, int col, int prevRow, int prevCol, Player player) {

        if (!board.getCellFromBoard(prevRow, prevCol).hasFigure()) {
            return;
        }

        if (!player.getCheckers().contains(board.getCellFromBoard(prevRow, prevCol).getFigure())) {
            return;
        }

        if (board.getCellFromBoard(prevRow, prevCol).getFigure().getPossibleMoves(board).size() == 0) {
            return;
        }

        if (!board.getCellFromBoard(prevRow, prevCol).getFigure().getPossibleMoves(board).contains(board.getCellFromBoard(row, col))) {
            return;
        }

        System.out.println("In MOVE");
        System.out.println("Player color: " + player.getTeamColor());

        if (canBecomeKing(row, col, prevRow, prevCol) && !(board.getCellFromBoard(prevRow, prevCol).getFigure() instanceof King)) {
            if (board.getCellFromBoard(prevRow, prevCol).getFigure().getColor() == CheckerColors.WHITE) {
                board.getCellFromBoard(row, col).setFigure(new King(col, row, CheckerColors.WHITE));
                board.getCellFromBoard(prevRow, prevCol).setFigure(null);
                board.getWhiteFigures().add(board.getCellFromBoard(row, col).getFigure());
            } else if (board.getCellFromBoard(prevRow, prevCol).getFigure().getColor() == CheckerColors.RED) {
                board.getCellFromBoard(row, col).setFigure(new King(col, row, CheckerColors.RED));
                board.getCellFromBoard(prevRow, prevCol).setFigure(null);
                board.getRedFigures().add(board.getCellFromBoard(row, col).getFigure());
            }
        } else {
            board.getCellFromBoard(row, col).setFigure(board.getCellFromBoard(prevRow, prevCol).getFigure());
            board.getCellFromBoard(prevRow, prevCol).setFigure(null);
            board.getCellFromBoard(row, col).getFigure().setY(row);
            board.getCellFromBoard(row, col).getFigure().setX(col);
        }

        moveFlag = true;
        System.out.println("Move flag into  MOVE: " + moveFlag);

    }

    public boolean hasMoved() {

        boolean flag = moveFlag;
        System.out.println("In hasMoved, move flag = " + flag);
        resetMovedFlag();
        System.out.println("In hasMoved, move flag reset = " + moveFlag);

        return flag;
    }

    public void beat(int row, int col, int prevRow, int prevCol, Player player) {

        if (!board.getCellFromBoard(prevRow, prevCol).hasFigure()) {
            return;
        }

        if (!board.getCellFromBoard(prevRow, prevCol).getFigure().canBeat(board)) {
            return;
        }

        if (!player.getCheckers().contains(board.getCellFromBoard(prevRow, prevCol).getFigure())) {
            return;
        }

        if (board.getCellFromBoard(prevRow, prevCol).getFigure().getPossibleMoves(board).size() == 0) {
            return;
        }

        if (!board.getCellFromBoard(prevRow, prevCol).getFigure().getPossibleMoves(board).contains(board.getCellFromBoard(row, col))) {
            return;
        }

        System.out.println("IN BEAT");
        System.out.println("Player color: " + player.getTeamColor());


        if (canBecomeKing(row, col, prevRow, prevCol) && !(board.getCellFromBoard(prevRow, prevCol).getFigure() instanceof King)) {
            if (board.getCellFromBoard(prevRow, prevCol).getFigure().getColor() == CheckerColors.WHITE) {
                board.getCellFromBoard(row, col).setFigure(new King(col, row, CheckerColors.WHITE));
                board.getCellFromBoard(prevRow, prevCol).setFigure(null);
                board.getWhiteFigures().add(board.getCellFromBoard(row, col).getFigure());
            } else if (board.getCellFromBoard(prevRow, prevCol).getFigure().getColor() == CheckerColors.RED) {
                board.getCellFromBoard(row, col).setFigure(new King(col, row, CheckerColors.RED));
                board.getCellFromBoard(prevRow, prevCol).setFigure(null);
                board.getRedFigures().add(board.getCellFromBoard(row, col).getFigure());
            }

            board.getCellFromBoard((row + prevRow) / 2, (col + prevCol) / 2).setFigure(null);
        } else {
            board.getCellFromBoard(row, col).setFigure(board.getCellFromBoard(prevRow, prevCol).getFigure());
            board.getCellFromBoard(prevRow, prevCol).setFigure(null);
            board.getCellFromBoard(row, col).getFigure().setY(row);
            board.getCellFromBoard(row, col).getFigure().setX(col);
            if (board.getCellFromBoard(row, col).getFigure() instanceof King) {
                //вверх вправо
                if (row < prevRow && col > prevCol) {
                    while (prevRow > row && prevCol < col) {
                        board.getCellFromBoard(prevRow, prevCol).setFigure(null);
                        prevRow -= 1;
                        prevCol += 1;
                    }
                }

                //вверх влево
                if (row < prevRow && col < prevCol) {
                    while (prevRow > row && prevCol > col) {
                        board.getCellFromBoard(prevRow, prevCol).setFigure(null);
                        prevRow -= 1;
                        prevCol -= 1;
                    }
                }

                //вниз вправо
                if (row > prevRow && col > prevCol) {
                    while (prevRow < row && prevCol < col) {
                        board.getCellFromBoard(prevRow, prevCol).setFigure(null);
                        prevRow += 1;
                        prevCol += 1;
                    }
                }

                //вниз влево
                if (row > prevRow && col < prevCol) {
                    while (prevRow < row && prevCol > col) {
                        board.getCellFromBoard(prevRow, prevCol).setFigure(null);
                        prevRow += 1;
                        prevCol -= 1;
                    }
                }
            } else {
                board.getCellFromBoard((row + prevRow) / 2, (col + prevCol) / 2).setFigure(null);
            }
        }

        moveFlag = true;
        System.out.println("Move flag into  BEAT: " + moveFlag);
    }

    public boolean isGameOver(Player player1, Player player2) {
        return player1.getCheckers().size() == 0 || player2.getCheckers().size() == 0;
    }

    private boolean canBecomeKing(int row, int col, int prevRow, int prevCol) {
        if (board.getCellFromBoard(prevRow, prevCol).getFigure() instanceof Checker) {
            System.out.println("HERE");
            if (board.getCellFromBoard(prevRow, prevCol).getFigure().getColor() == CheckerColors.WHITE && row == 0) {
                return true;
            }

            if (board.getCellFromBoard(prevRow, prevCol).getFigure().getColor() == CheckerColors.RED && row == 7) {
                return true;
            }
        }
        return false;
    }

    public void resetMovedFlag() {
        moveFlag = false;
    }
}
