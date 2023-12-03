package apples;

import classes.snakeClasses.*;
import classes.snakeClasses.blockClasses.Body;

import javax.swing.*;

public class RedApple extends Apple {

    public RedApple(int windowWidth, int windowHeight, int coefficient, ImageIcon appleIco) {
        super(windowWidth, windowHeight, coefficient, appleIco);
        createApple();
    }

    @Override
    public void createApple() {
        setX(((int) (Math.random() * windowWidth / Body.BODY_SIZE)) * Body.BODY_SIZE);
        setY(((int) (Math.random() * windowHeight / Body.BODY_SIZE)) * Body.BODY_SIZE);
    }
}
