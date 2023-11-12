package classes.snakeClasses.blockClasses;

import classes.snakeClasses.*;

import javax.swing.*;

public class Head extends Block {
    private final ImageIcon headLeft
            = new ImageIcon("src/main/resources/files/sprites/single/headLeft.png");
    private final ImageIcon headRight
            = new ImageIcon("src/main/resources/files/sprites/single/headRight.png");
    private final ImageIcon headUp
            = new ImageIcon("src/main/resources/files/sprites/single/headUp.png");
    private final ImageIcon headDown
            = new ImageIcon("src/main/resources/files/sprites/single/headDown.png");

    private Direction direction = Direction.LEFT;

    public Head(int x, int y) {
        super(x, y);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public ImageIcon getIco() {
        switch (direction) {
            case UP:
                return headUp;

            case DOWN:
                return headDown;

            case LEFT:
                return headLeft;

            case RIGHT:
                return headRight;
        }
        return null;
    }

    public void move() {
        switch (direction) {
            case UP:
                setY(getY() - Snake.BODY_SIZE);
                break;
            case DOWN:
                setY(getY() + Snake.BODY_SIZE);
                break;
            case LEFT:
                setX(getX() - Snake.BODY_SIZE);
                break;
            case RIGHT:
                setX(getX() + Snake.BODY_SIZE);
                break;
        }
    }

    public Direction getDirection() {
        return direction;
    }
}
