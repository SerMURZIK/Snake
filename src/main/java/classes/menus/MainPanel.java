package classes.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainPanel extends SizePanel {
    private final JButton start = new JButton("Single player");
    private final JButton multiplayer = new JButton("Multiplayer");
    private final JButton account = new JButton("Account");
    private final JButton controlButtons = new JButton("Control buttons");
    private final JButton exit = new JButton("Exit");
    protected final Color color = Color.CYAN;
    private ActionListener exitListener;

    public MainPanel() {
        JLabel fon = new JLabel(new ImageIcon("src/main/resources/files/sprites/other/fon.png"));
        fon.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        Font font = new Font("Calibri", Font.PLAIN, 50);
        exit.addActionListener(e -> System.exit(0));

        setLayout(null);
        add(start);
        add(multiplayer);
        add(controlButtons);
        add(account);
        add(exit);
        add(fon);

        int buttonWidth = 500, buttonHeight = 100, startPositionX = 800, startPositionY = 550, indentation = 30;
        start.setFont(font);
        start.setBackground(color);
        start.setBounds(startPositionX,
                550,
                buttonWidth, buttonHeight);

        multiplayer.setFont(font);
        multiplayer.setBackground(color);
        multiplayer.setBounds(startPositionX + buttonWidth + indentation,
                startPositionY,
                buttonWidth, buttonHeight);

        controlButtons.setFont(font);
        controlButtons.setBackground(color);
        controlButtons.setBounds(startPositionX,
                startPositionY + buttonHeight + indentation,
                buttonWidth, buttonHeight);

        account.setFont(font);
        account.setBackground(color);
        account.setBounds(startPositionX + buttonWidth + indentation,
                startPositionY + buttonHeight + indentation,
                buttonWidth, buttonHeight);

        exit.setFont(font);
        exit.setBackground(Color.CYAN);
        exit.setBounds(startPositionX,
                startPositionY + (buttonHeight + indentation) * 2,
                buttonWidth, buttonHeight);
    }

    public void setStartListener(ActionListener listener) {
        start.addActionListener(listener);
    }

    public void setMultiplayerListener(ActionListener listener) {
        multiplayer.addActionListener(listener);
    }

    public void setAccountListener(ActionListener listener) {
        account.addActionListener(listener);
    }

    public void setTabWithControlButtonsListener(ActionListener listener) {
        controlButtons.addActionListener(listener);
    }

    public void updateSign(boolean signed) {
        if (signed) {
            exit.removeActionListener(exitListener);
        } else {
            exit.addActionListener(exitListener);
        }
    }

    public void setExitListener(ActionListener listener) {
        exitListener = listener;
    }
}
