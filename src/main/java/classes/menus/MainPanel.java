package classes.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainPanel extends FonPanel {
    private final JButton start = new JButton("Single player");
    private final JButton multiplayer = new JButton("Multiplayer");
    private final JButton account = new JButton("Account");
    private final JButton controlButtons = new JButton("Control buttons");
    private final JButton exit = new JButton("Exit");
    private ActionListener exitListener;

    public MainPanel() {
        Font font = new Font("Calibri", Font.PLAIN, 50);
        exit.addActionListener(e -> System.exit(0));

        add(start, 0);
        add(multiplayer, 1);
        add(controlButtons, 2);
        add(account, 3);
        add(exit, 4);

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

    public void setControlPanelListener(ActionListener listener) {
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
