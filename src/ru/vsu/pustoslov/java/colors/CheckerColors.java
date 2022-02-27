package ru.vsu.pustoslov.java.colors;

import java.awt.Color;
import java.io.Serializable;

public enum CheckerColors implements Serializable {
    RED(new Color(161, 40, 40)),
    WHITE(new Color(255, 255, 230));

    private final Color color;

    CheckerColors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
