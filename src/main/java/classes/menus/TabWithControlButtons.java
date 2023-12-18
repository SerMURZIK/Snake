package classes.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TabWithControlButtons extends JPanel {
    private final JButton back = new JButton("Back");

    public TabWithControlButtons() {
        Font font = new Font("Calibri", Font.PLAIN, 20);

        JLabel teachingText = new JLabel("<html>Press <b>arrow keys</b> for moving in singleplayer<br>" +
                "Press <b>WASD</b> and <b>arrow keys</b> for moving in multiplayer<br>" +
                "Press <b>CTRL</b> to stop the game<br>" +
                "Press <b>ESC</b> to open main menu</html>");

        add(teachingText);
        add(back);

        teachingText.setFont(font);

        back.setFont(font);
        back.setBackground(Color.WHITE);

        repaint();
    }

    public void setBackListener(ActionListener listener) {
        back.addActionListener(listener);
    }

    @Override
    public int getWidth() {
        return 480;
    }

    @Override
    public int getHeight() {
        return 150;
    }
}
