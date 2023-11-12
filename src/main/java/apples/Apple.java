package apples;

import classes.snakeClasses.*;

import javax.swing.*;
import java.awt.*;

public abstract class Apple {
    private int x, y;
    protected int coefficient, windowWidth, windowHeight;
    private final ImageIcon appleIco;

    public Apple(int windowWidth, int windowHeight, int coefficient, ImageIcon appleIco) {
        this.coefficient = coefficient;
        this.appleIco = appleIco;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public boolean wasAppleEaten(Snake snake, Apple... apples) {
        for (Apple appleFromList : apples) {
            if (snake.getHead().getX() == appleFromList.getX() &&
                    snake.getHead().getY() == appleFromList.getY()) {
                if (coefficient > 0) {
                    for (int i = 0; i < coefficient; i++) {
                        snake.addBody();
                    }
                } else if (coefficient < 0) {
                    for (int i = 0; i > coefficient; i--) {
                        snake.removeBody();
                    }
                }
                createApplesList(apples);
                return true;
            }
        }
        return false;
    }

    public abstract void createApple();

    public int getCoefficient() {
        return coefficient;
    }

    public Image getAppleImage() {
        return appleIco.getImage();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    protected static void createApplesList(Apple[] apples) {
        for (Apple apple : apples) {
            apple.createApple();
        }
    }
}
