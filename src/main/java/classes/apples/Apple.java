package classes.apples;

import classes.snakeClasses.snakeEntityClasses.SnakeEntity;

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

    public boolean wasAppleEaten(SnakeEntity snakeEntity, Apple apple, Apple... apples) {
        if (snakeEntity.getHead().getX() == apple.getX() &&
                snakeEntity.getHead().getY() == apple.getY()) {
            if (coefficient > 0) {
                for (int i = 0; i < coefficient; i++) {
                    snakeEntity.addBody();
                }
            } else if (coefficient < 0) {
                for (int i = 0; i > coefficient; i--) {
                    snakeEntity.removeBody();
                }
            }
            createApplesList(apples);
            return true;
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
