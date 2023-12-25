package classes.menus.restartPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SinglePlayerRestartPanel extends RestartPanel{
    private final JButton load = new JButton("load");

    public SinglePlayerRestartPanel() {
        super();

        add(load, 1);
        load.setFont(font);
        load.setBackground(Color.WHITE);
    }

    public void setLoadListener(ActionListener listener) {
        load.addActionListener(listener);
    }
}
