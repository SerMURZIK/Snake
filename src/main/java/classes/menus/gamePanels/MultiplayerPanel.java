package classes.menus.gamePanels;

import classes.apples.Apple;
import classes.snakeClasses.Direction;
import classes.snakeClasses.snakeEntityClasses.SnakeEntity;
import classes.snakeClasses.blockClasses.Block;
import classes.snakeClasses.blockClasses.Body;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MultiplayerPanel extends GamePanel {
    private final SnakeEntity yellowSnake = new SnakeEntity(
            Direction.LEFT,
            GamePanel.WINDOW_WIDTH / 4 * 3 / Body.BODY_SIZE * Body.BODY_SIZE,
            GamePanel.WINDOW_HEIGHT / 2 / Body.BODY_SIZE * Body.BODY_SIZE);

    private final SnakeEntity greenSnakeEntity = new SnakeEntity(
            Direction.RIGHT,
            GamePanel.WINDOW_WIDTH / 4 / Body.BODY_SIZE * Body.BODY_SIZE,
            GamePanel.WINDOW_HEIGHT / 2 / Body.BODY_SIZE * Body.BODY_SIZE);

    private final Timer yellowSnakeTimer = new Timer(yellowSnake.getSpeed(), this);
    private final Timer greenSnakeTimer = new Timer(greenSnakeEntity.getSpeed(), this);

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (isAlive) {
            paintGame(graphics);
        } else {
            paintGame(graphics);
            graphics.setColor(Color.red);
            graphics.setFont(font);
            graphics.drawString(DEATH_MESSAGE, (getWidth() / 2) - 140, getHeight() / 2);
        }
    }

    private void paintGame(Graphics graphics) {
        drawApple(graphics, standardApple);
        drawApple(graphics, bonusApple);

        graphics.setFont(font);

        drawSnake(yellowSnake, graphics);
        drawSnake(greenSnakeEntity, graphics);

        graphics.setColor(Color.green);

        graphics.drawString("Yellow's score: " + yellowSnake.getScore(), getWidth() - 600, 100);
        graphics.drawString("Green's score: " + greenSnakeEntity.getScore(), 250, 100);
    }

    private void drawApple(Graphics graphics, Apple apple) {
        graphics.drawImage(apple.getAppleImage(),
                apple.getX(), apple.getY(), null);
    }

    private void drawSnake(SnakeEntity yellowSnake, Graphics graphics) {
        Block currentYellowBlock = yellowSnake.getHead();
        do {
            currentYellowBlock.draw(graphics);
            currentYellowBlock = currentYellowBlock.getNext();
        } while (currentYellowBlock != null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isAlive) {
            if (e.getSource().equals(yellowSnakeTimer)) {
                yellowSnake.getTail().move();
            }
            if (e.getSource().equals(greenSnakeTimer)) {
                greenSnakeEntity.getTail().move();
            }
            checkApple(applesList);
            isAlive = (yellowSnake.checkDeath(getWidth(), getHeight()) && yellowSnake.getScore() >= 0) &&
                    (greenSnakeEntity.checkDeath(getWidth(), getHeight()) && greenSnakeEntity.getScore() >= 0);
        } else {
            removeKeyListener(listener);
        }
        repaint();
    }

    @Override
    public void checkApple(Apple... apples) {
        for (Apple apple : apples) {
            if (apple.wasAppleEaten(yellowSnake, apple, apples)) {
                yellowSnake.setSpeed((short) (yellowSnake.getSpeed() - (2 * apple.getCoefficient())));
                yellowSnake.setScore((short) (yellowSnake.getScore() + apple.getCoefficient()));
                break;
            }
            if (apple.wasAppleEaten(greenSnakeEntity, apple, apples)) {
                greenSnakeEntity.setSpeed((short) (greenSnakeEntity.getSpeed() - (2 * apple.getCoefficient())));
                greenSnakeEntity.setScore((short) (greenSnakeEntity.getScore() + apple.getCoefficient()));
                break;
            }
        }
    }

    @Override
    public void updateAppleLocation() {
        for (Apple apple : applesList) {
            apple.createApple();
        }
    }

    @Override
    public KeyListener setListener() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_UP:
                        if (!yellowSnake.getDirection().equals(Direction.DOWN)) {
                            yellowSnake.getSnake().getHead().setDirection(Direction.UP);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (!yellowSnake.getDirection().equals(Direction.UP)) {
                            yellowSnake.getSnake().getHead().setDirection(Direction.DOWN);
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (!yellowSnake.getDirection().equals(Direction.RIGHT)) {
                            yellowSnake.getSnake().getHead().setDirection(Direction.LEFT);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!yellowSnake.getDirection().equals(Direction.LEFT)) {
                            yellowSnake.getSnake().getHead().setDirection(Direction.RIGHT);
                        }
                        break;
                    case KeyEvent.VK_W:
                        if (!greenSnakeEntity.getDirection().equals(Direction.DOWN)) {
                            greenSnakeEntity.getSnake().getHead().setDirection(Direction.UP);
                        }
                        break;
                    case KeyEvent.VK_S:
                        if (!greenSnakeEntity.getDirection().equals(Direction.UP)) {
                            greenSnakeEntity.getSnake().getHead().setDirection(Direction.DOWN);
                        }
                        break;
                    case KeyEvent.VK_A:
                        if (!greenSnakeEntity.getDirection().equals(Direction.RIGHT)) {
                            greenSnakeEntity.getSnake().getHead().setDirection(Direction.LEFT);
                        }
                        break;
                    case KeyEvent.VK_D:
                        if (!greenSnakeEntity.getDirection().equals(Direction.LEFT)) {
                            greenSnakeEntity.getSnake().getHead().setDirection(Direction.RIGHT);
                        }
                        break;
                    case KeyEvent.VK_CONTROL:
                        if (yellowSnakeTimer.isRunning() || greenSnakeTimer.isRunning()) {
                            stop();
                        } else {
                            start();
                        }
                        break;
                }
            }
        };
    }

    @Override
    public void restart() {
        isAlive = true;
        yellowSnake.createSnake(Direction.LEFT,
                GamePanel.WINDOW_WIDTH / 4 * 3 / Body.BODY_SIZE * Body.BODY_SIZE,
                GamePanel.WINDOW_HEIGHT / 2 / Body.BODY_SIZE * Body.BODY_SIZE);
        yellowSnake.setScore((short) 0);
        yellowSnake.setSpeed(SnakeEntity.startSpeed);

        greenSnakeEntity.createSnake(Direction.RIGHT,
                GamePanel.WINDOW_WIDTH / 4 / Body.BODY_SIZE * Body.BODY_SIZE,
                GamePanel.WINDOW_HEIGHT / 2 / Body.BODY_SIZE * Body.BODY_SIZE);
        greenSnakeEntity.setScore((short) 0);
        greenSnakeEntity.setSpeed(SnakeEntity.startSpeed);

        start();
        checkApple(applesList);
        backListener();
    }

    @Override
    public void start() {
        yellowSnakeTimer.start();
        greenSnakeTimer.start();
    }

    @Override
    public void stop() {
        yellowSnakeTimer.stop();
        greenSnakeTimer.stop();
    }
}
