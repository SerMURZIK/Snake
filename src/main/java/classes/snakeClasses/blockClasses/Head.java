package classes.snakeClasses.blockClasses;

import classes.snakeClasses.Direction;

import javax.swing.*;

public class Head extends Block {

    private Direction direction = Direction.LEFT;

    public Head(int x, int y) {
        super(x, y);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public ImageIcon getIco() {
        return switch (direction) {
            case UP -> getHeadUp();
            case DOWN -> getHeadDown();
            case LEFT -> getHeadLeft();
            case RIGHT -> getHeadRight();
        };
    }

    public void move() {
        switch (direction) {
            case UP:
                setY(getY() - Body.BODY_SIZE);
                break;
            case DOWN:
                setY(getY() + Body.BODY_SIZE);
                break;
            case LEFT:
                setX(getX() - Body.BODY_SIZE);
                break;
            case RIGHT:
                setX(getX() + Body.BODY_SIZE);
                break;
        }
    }

    public ImageIcon getHeadDown() {
        return new ImageIcon("src/main/resources/files/sprites/single/headDown.png");
    }

    public ImageIcon getHeadRight() {
        return new ImageIcon("src/main/resources/files/sprites/single/headRight.png");
    }

    public ImageIcon getHeadLeft() {
        return new ImageIcon("src/main/resources/files/sprites/single/headLeft.png");
    }

    public ImageIcon getHeadUp() {
        return new ImageIcon("src/main/resources/files/sprites/single/headUp.png");
    }

    public Direction getDirection() {
        return direction;
    }
}
