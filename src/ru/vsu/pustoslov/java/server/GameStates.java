package ru.vsu.pustoslov.java.server;

import java.io.Serializable;

public enum GameStates implements Serializable {
    START,
    MOVE,
    WIN,
    LOSE;
}
