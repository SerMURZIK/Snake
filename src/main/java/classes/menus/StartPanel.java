package classes.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {
    private final int width = 800;
    private final int height = 600;
    private final JButton start = new JButton("Начать");

    public StartPanel() {
        JLabel fon = new JLabel(new ImageIcon("src/main/resources/files/sprites/other/fon.png"));
        JButton closeGame = new JButton("Закрыть игру");
        Font font = new Font("Calibri", Font.BOLD, 20);

        fon.setBounds(0, 0, width, height);
        setLayout(null);

        add(start);
        add(closeGame);
        add(fon);

        start.setFont(font);
        closeGame.setFont(font);

        start.setBounds(500, 300, 100, 50);
        start.setBackground(Color.CYAN);

        closeGame.setBounds(500, 370, 150, 50);
        closeGame.setBackground(new Color(15, 227, 15));
        closeGame.addActionListener(e -> System.exit(0));
        start.setFocusable(false);
        closeGame.setFocusable(false);
    }

    public void setStartListener(ActionListener lister) {
        start.addActionListener(lister);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
