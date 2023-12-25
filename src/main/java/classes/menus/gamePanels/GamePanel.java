package classes.menus.gamePanels;

import classes.apples.Apple;
import classes.apples.BonusApple;
import classes.apples.RedApple;
import classes.other.Audio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;

public abstract class GamePanel extends JPanel implements ActionListener {
    public static int GAME_PANEL_WIDTH = 3300;
    public static int GAME_PANEL_HEIGHT = 1200;

    protected final RedApple standardApple = new RedApple(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT, 1,
            new ImageIcon("src/main/resources/files/sprites/apples/apple.png"));

    protected final BonusApple bonusGoodApple = new BonusApple(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT, 2,
            new ImageIcon("src/main/resources/files/sprites/apples/goodApple.png"));

    protected final BonusApple bonusBadApple = new BonusApple(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT, -3,
            new ImageIcon("src/main/resources/files/sprites/apples/badApple.png"));

    protected final Apple[] applesList = {standardApple, bonusBadApple, bonusGoodApple};
    protected final Font font = new Font("TimesNewRoman", Font.BOLD, 50);
    protected boolean isAlive = true;
    private final JButton exitToMenu = new JButton("Main menu");
    private final Audio audio = new Audio(new File("src/main/resources/sound/soundtrack.wav"));
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

    public boolean isAlive() {
        return isAlive;
    }

    public void continueGame() {
        isAlive = true;
        start();
        addKeyListener(listener);
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

    public abstract KeyListener setListener();
}
