package classes.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private final JButton settingsSize = new JButton("Set size of game window");
    private final JButton back = new JButton("Back");

    public SettingsPanel() {
        Font font = new Font("Calibri", Font.BOLD, 20);

        add(settingsSize);
        add(back);

        settingsSize.setFont(font);
        settingsSize.setBackground(Color.WHITE);

        back.setFont(font);
        back.setBackground(Color.WHITE);

        repaint();
    }

    public void settingSizeListener(ActionListener listener) {
        settingsSize.addActionListener(listener);
    }

    public void setBackListener(ActionListener listener) {
        back.addActionListener(listener);
    }

    @Override
    public int getWidth() {
        return 250;
    }

    @Override
    public int getHeight() {
        return 300;
    }
}
