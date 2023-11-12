package classes.snakeClasses.blockClasses;

import javax.swing.*;

public class Body extends Block {
    private static final ImageIcon bodyIco = new ImageIcon("src/main/resources/files/sprites/single/body.png");

    public Body(int x, int y) {
        super(x, y);
    }

    public ImageIcon getIco() {
        return bodyIco;
    }

    @Override
    public void move() {
        Block previous = getPrevious();
        setX(previous.getX());
        setY(previous.getY());
        previous.move();
    }
}
