package classes.menus;

import javax.swing.*;
import java.awt.*;

public abstract class FonPanel extends SizePanel {
    protected final Color color = Color.CYAN;

    public FonPanel() {
        JLabel fon = new JLabel(new ImageIcon("src/main/resources/files/sprites/other/fon.png"));
        fon.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(null);
        add(fon);
    }
}
