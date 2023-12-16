package classes.menus;

import classes.other.CustomTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingSize extends JPanel {
    private final CustomTextField widthField = new CustomTextField();
    private final CustomTextField heightField = new CustomTextField();
    private final JLabel warningMess = new JLabel("Input right information!");
    private boolean wasEntered;
    private final JButton enterSize = new JButton("Enter");
    private final ActionListener mainListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int widthOfWidow = Integer.parseInt(widthField.getText());
                int heightOfWidow = Integer.parseInt(heightField.getText());
                while (widthOfWidow % 30 != 0 || heightOfWidow % 30 != 0) {
                    if (widthOfWidow % 30 != 0) {
                        widthOfWidow--;
                    }
                    if (heightOfWidow % 30 != 0) {
                        heightOfWidow--;
                    }
                }
            } catch (NumberFormatException eex) {
                add(warningMess);
                repaint();
            }
            wasEntered = true;
            remove(warningMess);
            cleanFields();
            repaint();
        }
    };

    public SettingSize() {
        Font font = new Font("Arial Black", Font.BOLD, 35);

        add(widthField);
        add(heightField);
        add(enterSize);

        enterSize.addActionListener(e -> {

        });

        setLayout(null);
        setBackground(new Color(239, 238, 238, 255));
        setVisible(true);

        widthField.setBounds(30, 30, 700, 70);
        widthField.setFont(font);
        widthField.setForeground(Color.BLACK);
        widthField.setPlaceholderForeground(Color.GRAY);
        widthField.setPlaceholder("Input your screen width");

        heightField.setBounds(30, 130, 700, 70);
        heightField.setFont(font);
        heightField.setForeground(Color.BLACK);
        heightField.setPlaceholderForeground(Color.GRAY);
        heightField.setPlaceholder("Input your screen height");

        enterSize.setBounds(30, 230, 350, 70);
        enterSize.setFont(font);
        enterSize.setBackground(Color.WHITE);

        warningMess.setFont(new Font("Arial Black", Font.BOLD, 35));
        warningMess.setForeground(Color.RED);
        warningMess.setBounds(30, 330, 600, 50);
    }

    public boolean wasEntered() {
        return wasEntered;
    }

    public void setEnterSizeListener(ActionListener listener) {
        enterSize.removeAll();
        enterSize.addActionListener(listener);
        enterSize.addActionListener(mainListener);
    }

    public void cleanFields() {
        widthField.setPlaceholder("Input your screen height");
        heightField.setPlaceholder("Input your screen height");
    }

    @Override
    public int getWidth() {
        return 800;
    }

    @Override
    public int getHeight() {
        return 600;
    }
}
