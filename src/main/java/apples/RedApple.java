package apples;

import classes.snakeClasses.*;

import javax.swing.*;

public class RedApple extends Apple {

    public RedApple(int windowWidth, int windowHeight, int coefficient, ImageIcon appleIco) {
        super(windowWidth, windowHeight, coefficient, appleIco);
        createApple();
    }

    @Override
    public void createApple() {
        setX(((int) (Math.random() * windowWidth / Snake.BODY_SIZE)) * Snake.BODY_SIZE);
        setY(((int) (Math.random() * windowHeight / Snake.BODY_SIZE)) * Snake.BODY_SIZE);
    }
}
