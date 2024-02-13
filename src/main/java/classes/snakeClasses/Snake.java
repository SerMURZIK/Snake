package classes.snakeClasses;

import classes.snakeClasses.blockClasses.Block;
import classes.snakeClasses.blockClasses.Body;
import classes.snakeClasses.blockClasses.Head;

import java.util.List;

import static classes.snakeClasses.blockClasses.Block.BODY_SIZE;

public class Snake {
    private short score = 0;
    public static final short startSpeed = 100;
    private short speed = startSpeed;
    private Head head;
    private Block tail;

    public Snake(Direction direction, int initialX, int initialY) {
        createSnake(direction, initialX, initialY);
    }

    public void createSnake(Direction direction, int initialX, int initialY) {
        head = new Head(initialX, initialY);
        setDirection(direction);
        Block previous = this.head;
        for (int i = 1; i <= 4; i++) {
            Block next = switch (getDirection()) {
                case LEFT -> new Body(initialX + i * BODY_SIZE, initialY);
                case UP -> new Body(initialX, initialY + i * BODY_SIZE);
                case DOWN -> new Body(initialX, initialY - i * BODY_SIZE);
                case RIGHT -> new Body(initialX - i * BODY_SIZE, initialY);
            };
            previous.setNext(next);
            next.setPrevious(previous);
            previous = next;
        }
        tail = previous;
    }

    public void createSnake(Direction direction, List<Integer> x, List<Integer> y) {
        head = new Head(x.get(0), y.get(0));
        setDirection(direction);
        Block previous = this.head;
        for (int i = 1; i < x.size(); i++) {
            Block next = new Body(x.get(i), y.get(i));
            previous.setNext(next);
            next.setPrevious(previous);
            previous = next;
        }
        tail = previous;
    }

    public Head getHead() {
        return head;
    }

    public Block getTail() {
        return tail;
    }

    public boolean checkDeath(int windowWidth, int windowHeight) {
        Block current = head.getNext();
        do {
            if (current.getX() == head.getX() && current.getY() == head.getY()) {
                return false;
            }
            current = current.getNext();
        }
        while (current.getNext() != null);

        if (getDirection().equals(Direction.UP) && head.getY() < 0) {
            return false;
        }
        if (getDirection().equals(Direction.DOWN) && head.getY() > (windowHeight - BODY_SIZE)) {
            return false;
        }
        if (getDirection().equals(Direction.LEFT) && head.getX() < 0) {
            return false;
        }
        return !getDirection().equals(Direction.RIGHT) || head.getX() < windowWidth;
    }

    public void addBody() {
        Block newTail = new Body(tail.getX(), tail.getY());
        tail.setNext(newTail);
        newTail.setPrevious(tail);
        tail = newTail;
    }

    public void removeBody() {
        try {
            tail = tail.getPrevious();
            tail.setNext(null);
        } catch (IllegalThreadStateException ignored) {
        }
    }

    public Direction getDirection() {
        return getHead().getDirection();
    }

    public void setDirection(Direction direction) {
        getHead().setDirection(direction);
    }

    public short getScore() {
        return score;
    }

    public void setScore(short score) {
        this.score = score;
    }

    public short getSpeed() {
        return speed;
    }

    public void setSpeed(short speed) {
        this.speed = speed;
    }

    public Snake getSnake() {
        return this;
    }
}
