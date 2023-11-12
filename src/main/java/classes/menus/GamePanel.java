package classes.menus;

import apples.*;
import classes.other.*;
import classes.snakeClasses.*;
import classes.snakeClasses.blockClasses.*;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener {
    private final RedApple standardApple = new RedApple(getWidth(), getHeight(), 1,
            new ImageIcon("src/main/resources/files/sprites/apples/apple.png"));
    private final BonusApple bonusGoodApple = new BonusApple(getWidth(), getHeight(), 2,
            new ImageIcon("src/main/resources/files/sprites/apples/goodApple.png"));
    private final BonusApple bonusBadApple = new BonusApple(getWidth(), getHeight(), -3,
            new ImageIcon("src/main/resources/files/sprites/apples/badApple.png"));
    private final Apple[] applesList = {standardApple, bonusBadApple, bonusGoodApple};
    private final Snake snake = new Snake();
    private short score = 0;
    private static final int startSpeed = 100;
    private short speed = startSpeed;
    private final Timer timer = new Timer(speed, this);
    private final Font font = new Font("TimesNewRoman", Font.BOLD, 50);
    private boolean isAlive = true;
    private final JButton exitToMenu = new JButton("Main menu");
    private final Audio audio = new Audio(new File("src/main/resources/sound/soundtrack.wav"));
    private final KeyListener listener = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_CONTROL) {
                if (timer.isRunning()) {
                    stop();
                } else {
                    start();
                }
            }
            switch (key) {
                case KeyEvent.VK_UP:
                    if (!getDirection().equals(Direction.DOWN)) {
                        getSnake().getHead().setDirection(Direction.UP);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (!getDirection().equals(Direction.UP)) {
                        getSnake().getHead().setDirection(Direction.DOWN);
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (!getDirection().equals(Direction.RIGHT)) {
                        getSnake().getHead().setDirection(Direction.LEFT);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (!getDirection().equals(Direction.LEFT)) {
                        getSnake().getHead().setDirection(Direction.RIGHT);
                    }
                    break;
                case KeyEvent.VK_Q:
            }
        }
    };

    public GamePanel() {
        setLayout(null);
        add(exitToMenu);

        exitToMenu.setFont(new Font("Calibri", Font.BOLD, 20));
        exitToMenu.setBounds(30, 30, 130, 30);
        exitToMenu.setBackground(Color.YELLOW);

        setBackground(new Color(54, 143, 255, 255));
        addKeyListener(listener);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setInfo(int xApple, int yApple,
                        short score, short speed,
                        List<Integer> x, List<Integer> y) {
        this.score = score;
        this.speed = speed;
        standardApple.setY(yApple);
        standardApple.setX(xApple);

        Head head = new Head(x.get(0), y.get(0));
        Block previous = head;
        int i = 1;
        do {
            Block next = new Body(x.get(i), y.get(i));
            previous.setNext(next);
            next.setPrevious(previous);
            previous = next;
            i++;
        } while (i < x.size());
        snake.setTail(previous);
        snake.setHead(head);
    }

    public void restart() {
        isAlive = true;
        start();
        standardApple.createApple();
        snake.getHead().setDirection(Direction.LEFT);
        snake.createSnake();
        speed = startSpeed;
        score = 0;
        addKeyListener(listener);
    }

    public void loadLastSave() {
        isAlive = true;
        start();
        addKeyListener(listener);
    }

    public void setExitListener(ActionListener listener) {
        exitToMenu.addActionListener(listener);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
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

            graphics.drawString("Score: " + score, getWidth() - 600, 100);

        } else {
            graphics.setColor(Color.red);
            graphics.setFont(font);
            graphics.drawString("You died", (getWidth() / 2) - 140, getHeight() / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isAlive) {
            snake.getTail().move();
            checkApple(applesList);
            isAlive = snake.checkDeath(getWidth(), getHeight()) && score >= 0;
        } else {
            removeKeyListener(listener);
        }
        repaint();
    }

    public void checkApple(Apple... apples) {
        for (Apple apple : apples) {
            if (apple.wasAppleEaten(snake, apple, apples)) {
                setSpeed(speed -= 2 * apple.getCoefficient());
                score += apple.getCoefficient();
                break;
            }
        }
    }

    public void setSpeed(short speed) {
        timer.setDelay(speed);
    }

    public RedApple getApple() {
        return standardApple;
    }

    public Snake getSnake() {
        return snake;
    }

    public short getScore() {
        return score;
    }

    public short getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return snake.getDirection();
    }

    public void setDirection(Direction direction) {
        snake.setDirection(direction);
    }

    @Override
    public int getWidth() {
        return 3300;
    }

    @Override
    public int getHeight() {
        return 1200;
    }

    public void playSound(boolean play) {
        if (play) {
            audio.play();
        } else {
            audio.stop();
        }
    }
}
