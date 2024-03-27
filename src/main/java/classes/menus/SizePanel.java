package classes.menus;

import classes.snakeClasses.blockClasses.Block;

import javax.swing.*;

public abstract class SizePanel extends JPanel {
    public final static int WINDOW_WIDTH = 1900 / Block.BODY_SIZE * Block.BODY_SIZE,
            WINDOW_HEIGHT = 1000 / Block.BODY_SIZE * Block.BODY_SIZE;

    public int getWidth() {
        return WINDOW_WIDTH;
    }

    public int getHeight() {
        return WINDOW_HEIGHT;
    }
}
