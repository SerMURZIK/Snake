package classes.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RestartPanel extends JPanel {
    private final JButton restart = new JButton("Restart");
    private final JButton load = new JButton("load");

    public RestartPanel() {
        JButton exit = new JButton("quit");
        Font font = new Font("Calibri", Font.BOLD, 20);

        add(restart);
        add(load);
        add(exit);

        restart.setFont(font);
        restart.setBackground(Color.WHITE);

        load.setFont(font);
        load.setBackground(Color.WHITE);

        exit.setFont(font);
        exit.setBackground(Color.WHITE);

        exit.addActionListener(e -> System.exit(0));
    }

    public void setRestartListener(ActionListener listener) {
        restart.addActionListener(listener);
    }

    public void setLoadListener(ActionListener listener) {
        load.addActionListener(listener);
    }

    @Override
    public int getWidth() {
        return 140;
    }

    @Override
    public int getHeight() {
        return 300;
    }
}
