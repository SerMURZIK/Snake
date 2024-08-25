package classes.menus.gamePanels;

import classes.apples.Apple;
import classes.apples.BonusApple;
import classes.apples.DefaultApple;
import classes.menus.SizePanel;
import classes.other.Audio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public abstract class GamePanel extends SizePanel implements ActionListener {
    private final String pathToAppleSprite = "/sprites/apples/";

    protected final DefaultApple standardApple = new DefaultApple(WINDOW_WIDTH, WINDOW_HEIGHT, 1,
            new ImageIcon(this.getClass().getResource(pathToAppleSprite + "apple.png")));

    protected final BonusApple bonusApple = new BonusApple(WINDOW_WIDTH, WINDOW_HEIGHT, 2,
            new ImageIcon(this.getClass().getResource(pathToAppleSprite + "goodApple.png")));

    protected final Apple[] applesList = {standardApple, bonusApple};
    protected final Font font = new Font("TimesNewRoman", Font.BOLD, 50);
    protected boolean isAlive = true;
    private final JButton exitToMenu = new JButton("Main menu");
    private final Audio audio = new Audio(this.getClass().getResourceAsStream("/sound/soundtrack.wav"));
    protected final KeyListener listener = setListener();

    public GamePanel() {
        setLayout(null);
        add(exitToMenu);

        exitToMenu.setFont(new Font("Calibri", Font.BOLD, 20));
        exitToMenu.setBounds(30, 30, 130, 30);
        exitToMenu.setBackground(Color.YELLOW);

        setBackground(new Color(54, 143, 255, 255));
        addKeyListener(listener);
    }


    public void backListener() {
        addKeyListener(setListener());
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setExitListener(ActionListener listener) {
        exitToMenu.addActionListener(listener);
    }

    public abstract void start();

    public abstract void stop();

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);

    public abstract void checkApple(Apple... apples);

    public abstract void updateAppleLocation();

    public void playSound(boolean play) {
        if (play) {
            audio.play();
        } else {
            audio.stop();
        }
    }

    public abstract KeyListener setListener();

    public abstract void restart();

    public Apple[] getAppleList() {
        return applesList;
    }
}
