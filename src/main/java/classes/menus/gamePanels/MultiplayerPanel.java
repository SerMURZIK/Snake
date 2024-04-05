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

public class MultiplayerPanel extends GamePanel {
    private final Snake yellowSnake = new Snake(
            Direction.LEFT,
            GamePanel.GAME_PANEL_WIDTH / 4 * 3 / Body.BODY_SIZE * Body.BODY_SIZE,
            GamePanel.GAME_PANEL_HEIGHT / 2 / Body.BODY_SIZE * Body.BODY_SIZE);

    private final Snake greenSnake = new Snake(
            Direction.RIGHT,
            GamePanel.GAME_PANEL_WIDTH / 4 / Body.BODY_SIZE * Body.BODY_SIZE,
            GamePanel.GAME_PANEL_HEIGHT / 2 / Body.BODY_SIZE * Body.BODY_SIZE);

    private final Timer yellowSnakeTimer = new Timer(yellowSnake.getSpeed(), this);
    private final Timer greenSnakeTimer = new Timer(greenSnake.getSpeed(), this);

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (isAlive) {
            drawApple(graphics, standardApple);
            drawApple(graphics, bonusBadApple);
            drawApple(graphics, bonusGoodApple);

            graphics.setFont(font);

            drawSnake(yellowSnake, graphics);
            drawSnake(greenSnake, graphics);

            graphics.setColor(Color.green);

            graphics.drawString("Yellow's score: " + yellowSnake.getScore(), getWidth() - 600, 100);
            graphics.drawString("Green's score: " + greenSnake.getScore(), 250, 100);

        } else {
            graphics.setColor(Color.red);
            graphics.setFont(font);
            graphics.drawString("You died", (getWidth() / 2) - 140, getHeight() / 2);
        }
    }

    private void drawApple(Graphics graphics, Apple apple) {
        graphics.drawImage(apple.getAppleImage(),
                apple.getX(), apple.getY(), null);
    }

    private void drawSnake(Snake yellowSnake, Graphics graphics) {
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
                greenSnake.getTail().move();
            }
            checkApple(applesList);
            isAlive = (yellowSnake.checkDeath(getWidth(), getHeight()) && yellowSnake.getScore() >= 0) &&
                    (greenSnake.checkDeath(getWidth(), getHeight()) && greenSnake.getScore() >= 0);
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
            if (apple.wasAppleEaten(greenSnake, apple, apples)) {
                greenSnake.setSpeed((short) (greenSnake.getSpeed() - (2 * apple.getCoefficient())));
                greenSnake.setScore((short) (greenSnake.getScore() + apple.getCoefficient()));
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
                        if (!greenSnake.getDirection().equals(Direction.DOWN)) {
                            greenSnake.getSnake().getHead().setDirection(Direction.UP);
                        }
                        break;
                    case KeyEvent.VK_S:
                        if (!greenSnake.getDirection().equals(Direction.UP)) {
                            greenSnake.getSnake().getHead().setDirection(Direction.DOWN);
                        }
                        break;
                    case KeyEvent.VK_A:
                        if (!greenSnake.getDirection().equals(Direction.RIGHT)) {
                            greenSnake.getSnake().getHead().setDirection(Direction.LEFT);
                        }
                        break;
                    case KeyEvent.VK_D:
                        if (!greenSnake.getDirection().equals(Direction.LEFT)) {
                            greenSnake.getSnake().getHead().setDirection(Direction.RIGHT);
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
                GamePanel.GAME_PANEL_WIDTH / 4 * 3 / Body.BODY_SIZE * Body.BODY_SIZE,
                GamePanel.GAME_PANEL_HEIGHT / 2 / Body.BODY_SIZE * Body.BODY_SIZE);
        yellowSnake.setScore((short) 0);
        yellowSnake.setSpeed(Snake.startSpeed);

        greenSnake.createSnake(Direction.RIGHT,
                GamePanel.GAME_PANEL_WIDTH / 4 / Body.BODY_SIZE * Body.BODY_SIZE,
                GamePanel.GAME_PANEL_HEIGHT / 2 / Body.BODY_SIZE * Body.BODY_SIZE);
        greenSnake.setScore((short) 0);
        greenSnake.setSpeed(Snake.startSpeed);

        start();
        checkApple(applesList);
        setListener();
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
