package ru.vsu.pustoslov.java.colors;

import java.awt.Color;
import java.io.Serializable;

public enum GeneralColors implements Serializable {
    BLACK_CELL(new Color(210, 140, 69)),
    WHITE_CELL(new Color(255, 206, 158)),
    HIGHLIGHTING(new Color(106, 196, 104));

    private final Color color;

    GeneralColors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
