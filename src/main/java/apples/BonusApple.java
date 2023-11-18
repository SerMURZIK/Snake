package apples;

import classes.snakeClasses.Snake;

import javax.swing.*;

public class BonusApple extends Apple {
    private final Thread settingAppleThread = new Thread(getRunnable());

    public BonusApple(int windowWidth, int windowHeight, int coefficient, ImageIcon appleIco) {
        super(windowWidth, windowHeight, coefficient, appleIco);
        setX(-100);
        setY(-100);
    }

    @Override
    public void createApple() {
        try {
            setX(-100);
            setY(-100);
            int a = (int) (Math.random() * 10) + 1;
            if (a < 6) {
                settingAppleThread.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Runnable getRunnable() {
        return () -> {
            try {
                setX(((int) (Math.random() * windowWidth / Snake.BODY_SIZE)) * Snake.BODY_SIZE);
                setY(((int) (Math.random() * windowHeight / Snake.BODY_SIZE)) * Snake.BODY_SIZE);
                Thread.sleep(5000);
                setX(-100);
                setY(-100);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            settingAppleThread.interrupt();
        };
    }
}
