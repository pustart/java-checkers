package ru.vsu.pustoslov.java.player;

import ru.vsu.pustoslov.java.colors.CheckerColors;
import ru.vsu.pustoslov.java.figures.Figure;
import ru.vsu.pustoslov.java.strategies.BotStrategy;
import java.util.List;

//Отличие от игрока в том, что бот в конструктор принимает стратегию
public class Bot extends Player {
    private final BotStrategy strategy;

    public Bot(List<Figure> checkers, CheckerColors teamColor, BotStrategy strategy) {
        super(checkers, teamColor);
        this.strategy = strategy;
    }

    public BotStrategy getStrategy() {
        return strategy;
    }
}
