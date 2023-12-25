package classes.menus.restartPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RestartPanel extends JPanel {
    private final JButton restart = new JButton("Restart");
    protected final Font font = new Font("Calibri", Font.BOLD, 20);

    public RestartPanel() {
        JButton exit = new JButton("quit");

        add(restart);
        add(exit);

        restart.setFont(font);
        restart.setBackground(Color.WHITE);

        exit.setFont(font);
        exit.setBackground(Color.WHITE);

        exit.addActionListener(e -> System.exit(0));
    }

    public void setRestartListener(ActionListener listener) {
        restart.addActionListener(listener);
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
