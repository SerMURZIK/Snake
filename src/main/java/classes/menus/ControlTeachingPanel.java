package classes.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlTeachingPanel extends JPanel {
    private final JButton back = new JButton("Back");

    public ControlTeachingPanel() {
        Font font = new Font("Calibri", Font.PLAIN, 20);

        JLabel teachingText = new JLabel("<html>Управление в одиночной игре происходить на <b>стрелочками</b><br>" +
                "Управление в мультиплеере происходит на <b>WASD</b> и <b>стрелочки</b><br>" +
                "В ходе игры Вы можете нажать <b>CTRL</b> и остановить её<br>" +
                "В ходе игры Вы можете нажать <b>ESC</b> и открыть главное меню</html>");

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
        return 600;
    }

    @Override
    public int getHeight() {
        return 150;
    }
}
