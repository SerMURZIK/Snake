package classes.menus.restartPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoadingRestartPanel extends RestartPanel {
    private final JButton load = new JButton("Load");

    public LoadingRestartPanel() {
        super();

        add(load, 1);
        load.setFont(font);
        load.setBackground(Color.WHITE);
    }

    public void setLoadListener(ActionListener listener) {
        load.addActionListener(listener);
    }
}
