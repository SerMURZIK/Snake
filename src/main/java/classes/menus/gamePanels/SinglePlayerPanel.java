package classes.menus.gamePanels;

import classes.apples.Apple;
import classes.snakeClasses.Direction;
import classes.snakeClasses.Snake;
import classes.snakeClasses.blockClasses.Block;
import classes.snakeClasses.blockClasses.Body;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class SinglePlayerPanel extends GamePanel {
    private final Snake snake = new Snake(
            Direction.RIGHT,
            WINDOW_WIDTH / 2 / Body.BODY_SIZE * Body.BODY_SIZE,
            WINDOW_HEIGHT / 2 / Body.BODY_SIZE * Body.BODY_SIZE);

    private final Timer snakeTimer = new Timer(snake.getSpeed(), this);

    public void setInfo(short score, short speed, Direction direction, List<Integer> x, List<Integer> y) {
        snake.setScore(score);
        snake.setSpeed(speed);
        snake.createSnake(direction, x, y);
    }

    @Override
    public void start() {
        snakeTimer.start();
    }

    @Override
    public void stop() {
        snakeTimer.stop();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (isAlive) {
            graphics.drawImage(standardApple.getAppleImage(),
                    standardApple.getX(), standardApple.getY(), null);

            graphics.drawImage(bonusBadApple.getAppleImage(),
                    bonusBadApple.getX(), bonusBadApple.getY(), null);

            graphics.drawImage(bonusGoodApple.getAppleImage(),
                    bonusGoodApple.getX(), bonusGoodApple.getY(), null);
            graphics.setFont(font);

            Block currentBlock = snake.getHead();
            do {
                currentBlock.draw(graphics);
                currentBlock = currentBlock.getNext();
            } while (currentBlock != null);

            graphics.setColor(Color.green);

            graphics.drawString("Score: " + snake.getScore(), getWidth() - 600, 100);

        } else {
            graphics.setColor(Color.red);
            graphics.setFont(font);
            graphics.drawString("You died", (getWidth() / 2) - 140, getHeight() / 2);
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isAlive) {
            snake.getTail().move();
            checkApple(applesList);
            isAlive = snake.checkDeath(getWidth(), getHeight()) && snake.getScore() >= 0;
        } else {
            removeKeyListener(listener);
        }
        repaint();
    }

    @Override
    public void checkApple(Apple... apples) {
        for (Apple apple : apples) {
            if (apple.wasAppleEaten(snake, apple, apples)) {
                snake.setSpeed((short) (snake.getSpeed() - (2 * apple.getCoefficient())));
                snake.setScore((short) (snake.getScore() + apple.getCoefficient()));
                break;
            }
        }
    }

    @Override
    public KeyListener setListener() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_CONTROL) {
                    if (snakeTimer.isRunning()) {
                        stop();
                    } else {
                        start();
                    }
                }
                switch (key) {
                    case KeyEvent.VK_UP:
                        if (!snake.getDirection().equals(Direction.DOWN)) {
                            snake.getSnake().getHead().setDirection(Direction.UP);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (!snake.getDirection().equals(Direction.UP)) {
                            snake.getSnake().getHead().setDirection(Direction.DOWN);
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (!snake.getDirection().equals(Direction.RIGHT)) {
                            snake.getSnake().getHead().setDirection(Direction.LEFT);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!snake.getDirection().equals(Direction.LEFT)) {
                            snake.getSnake().getHead().setDirection(Direction.RIGHT);
                        }
                        break;
                    case KeyEvent.VK_Q:
                }
            }
        };
    }

    public Snake getSnake() {
        return snake;
    }
}
