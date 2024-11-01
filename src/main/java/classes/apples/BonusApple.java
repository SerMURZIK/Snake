package classes.apples;

import classes.snakeClasses.blockClasses.Body;

import javax.swing.*;

public class BonusApple extends Apple {

    public BonusApple(int windowWidth, int windowHeight, int coefficient, ImageIcon appleIco) {
        super(windowWidth, windowHeight, coefficient, appleIco);
        setX(-100);
        setY(-100);
    }

    @Override
    public void createApple() {
        setX(((int) (Math.random() * windowWidth / Body.BODY_SIZE)) * Body.BODY_SIZE);
        setY(((int) (Math.random() * windowHeight / Body.BODY_SIZE)) * Body.BODY_SIZE);
        coefficient = (int) ((Math.random() * 100) / 10 - (Math.random() * 100) / 10);
        System.out.println(coefficient);
    }
}
