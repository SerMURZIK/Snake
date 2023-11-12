package apples;

import classes.snakeClasses.*;

import javax.swing.*;

public class BonusApple extends Apple {

    public BonusApple(int windowWidth, int windowHeight, int coefficient, ImageIcon appleIco) {
        super(windowWidth, windowHeight, coefficient, appleIco);
        setX(-100);
        setY(-100);
    }

    @Override
    public void createApple() {
        setX(-100);
        setY(-100);
        int a = (int) (Math.random() * 10) + 1;
        if (a < 6) {
            setX(((int) (Math.random() * windowWidth / Snake.BODY_SIZE)) * Snake.BODY_SIZE);
            setY(((int) (Math.random() * windowHeight / Snake.BODY_SIZE)) * Snake.BODY_SIZE);
        }
    }
}
