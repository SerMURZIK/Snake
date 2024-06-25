package classes.snakeClasses.blockClasses;

import javax.swing.*;

public class Body extends Block {
    private final ImageIcon bodyIco = new ImageIcon(this.getClass().getResource("/sprites/single/body.png"));

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
