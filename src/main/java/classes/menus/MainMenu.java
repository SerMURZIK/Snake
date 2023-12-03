package classes.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    private final JButton start = new JButton("Start");
    private final JButton settings = new JButton("Settings");
    private final JButton multiplayer = new JButton("Multiplayer");
    private final JButton account = new JButton("Account");
    private final JButton exit = new JButton("Exit");
    private ActionListener exitListener;

    public MainMenu() {
        Font font = new Font("Calibri", Font.BOLD, 20);
        exit.addActionListener(e -> System.exit(0));

        add(start);
        add(multiplayer);
        add(settings);
        add(account);
        add(exit);

        start.setFont(font);
        start.setBackground(Color.WHITE);
        multiplayer.setFont(font);
        multiplayer.setBackground(Color.WHITE);
        settings.setFont(font);
        settings.setBackground(Color.WHITE);
        account.setFont(font);
        account.setBackground(Color.WHITE);
        exit.setFont(font);
        exit.setBackground(Color.WHITE);
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

    public void setSettingsListener(ActionListener listener) {
        settings.addActionListener(listener);
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

    @Override
    public int getWidth() {
        return 170;
    }

    @Override
    public int getHeight() {
        return 300;
    }
}
