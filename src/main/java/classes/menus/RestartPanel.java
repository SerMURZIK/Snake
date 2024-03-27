package classes.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RestartPanel extends SizePanel {
    private final JButton restart = new JButton("Restart");
    private final JButton load = new JButton("Load");

    public RestartPanel() {
        JButton exit = new JButton("Quit");
        Font font = new Font("Calibri", Font.PLAIN, 50);

        setLayout(null);
        add(restart);
        add(load);
        add(exit);

        int buttonWidth = 300, buttonHeight = 100, indentation = 50,
                x = getWidth() / 2 - buttonWidth / 2, startY = 300;

        restart.setFont(font);
        restart.setBackground(Color.WHITE);
        restart.setBounds(x, startY, buttonWidth, buttonHeight);

        load.setFont(font);
        load.setBackground(Color.WHITE);
        load.setBounds(x, indentation + buttonHeight + startY, buttonWidth, buttonHeight);

        exit.setFont(font);
        exit.setBackground(Color.WHITE);
        exit.setBounds(x, (indentation + buttonHeight) * 2 + startY, buttonWidth, buttonHeight);

        exit.addActionListener(e -> System.exit(0));
    }

    public void setRestartListener(ActionListener listener) {
        restart.addActionListener(listener);
    }

    public void setLoadListener(ActionListener listener) {
        load.addActionListener(listener);
    }
}
