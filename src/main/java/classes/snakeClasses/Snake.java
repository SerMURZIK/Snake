package classes.snakeClasses;

import classes.snakeClasses.blockClasses.*;

public class Snake {
    public static final byte BODY_SIZE = 30;//в пикселях
    public static final int INITIAL_X = 1800;
    public static final int INITIAL_Y = 600;
    private Head head;
    private Block tail;

    public Snake() {
        createSnake();
    }

    public void createSnake() {
        head = new Head(INITIAL_X, INITIAL_Y);
        Block previous = this.head;
        for (int i = 1; i <= 5; i++) {
            Block next = new Body(INITIAL_X + i * BODY_SIZE, INITIAL_Y);
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

    public void setTail(Block tail) {
        this.tail = tail;
    }

    public void setHead(Head head) {
        this.head = head;
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
        tail = tail.getPrevious();
        tail.setNext(null);
    }

    public Direction getDirection() {
        return getHead().getDirection();
    }

    public void setDirection(Direction direction) {
        getHead().setDirection(direction);
    }
}
