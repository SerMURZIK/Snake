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
        try {
            setX(-100);
            setY(-100);
            int a = (int) (Math.random() * 10) + 1;
            if (a < 6) {
                new Thread(getRunnable()).start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Runnable getRunnable() {
        return () -> {
            try {
                setX(((int) (Math.random() * windowWidth / Body.BODY_SIZE)) * Body.BODY_SIZE);
                setY(((int) (Math.random() * windowHeight / Body.BODY_SIZE)) * Body.BODY_SIZE);
                Thread.sleep(5000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                Thread.currentThread().interrupt();
                setX(-100);
                setY(-100);
            }
        };
    }
}
