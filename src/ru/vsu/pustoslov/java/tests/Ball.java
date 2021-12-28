package ru.vsu.pustoslov.java.tests;

public class Ball {
    private String size;

    public Ball(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Ball{" +
                "size=" + size +
                '}';
    }
}
