package ru.vsu.pustoslov.java.player;

import java.util.Objects;
import ru.vsu.pustoslov.java.colors.CheckerColors;
import ru.vsu.pustoslov.java.figures.Figure;
import java.util.List;

public class Player {
    private final List<Figure> checkers;
    private final CheckerColors teamColor;

    public Player(List<Figure> playerCheckers, CheckerColors teamColor) {
        this.checkers = playerCheckers;
        this.teamColor = teamColor;
    }

    public List<Figure> getCheckers() {
        return checkers;
    }

    public CheckerColors getTeamColor() {
        return teamColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(getCheckers(), player.getCheckers()) && getTeamColor() == player.getTeamColor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCheckers(), getTeamColor());
    }

    @Override
    public String toString() {
        return "Player{" +
                "checkers=" + checkers +
                ",\n teamColor=" + teamColor +
                '}';
    }
}
