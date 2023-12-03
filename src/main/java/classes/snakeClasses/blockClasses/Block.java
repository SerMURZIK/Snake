package classes.snakeClasses.blockClasses;

import javax.swing.*;
import java.awt.*;

public abstract class Block {
    public final static int BODY_SIZE = 30;
    private int x, y;
    private Block previous;
    private Block next;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(getIco().getImage(), x, y, null);
    }

    public abstract ImageIcon getIco();
    public abstract void move();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Block getPrevious() {
        return previous;
    }

    public void setPrevious(Block previous) {
        this.previous = previous;
    }

    public Block getNext() {
        return next;
    }

    public void setNext(Block next) {
        this.next = next;
    }
}
