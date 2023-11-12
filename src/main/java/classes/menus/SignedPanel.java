package classes.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignedPanel extends JPanel {
    private final JButton save = new JButton("Save");
    private final JButton load = new JButton("Load");
    private final JButton exitFromAccount = new JButton("Exit from account");
    private final JButton back = new JButton("Back");

    public SignedPanel() {
        Font font = new Font("Calibri", Font.BOLD, 20);

        add(save);
        add(load);
        add(exitFromAccount);
        add(back);

        save.setFont(font);
        save.setBackground(Color.WHITE);

        load.setFont(font);
        load.setBackground(Color.WHITE);

        exitFromAccount.setFont(font);
        exitFromAccount.setBackground(Color.WHITE);

        back.setFont(font);
        back.setBackground(Color.WHITE);
    }

    public void setBackListener(ActionListener listener) {
        back.addActionListener(listener);
    }

    public void setSaveListener(ActionListener listener) {
        save.addActionListener(listener);
    }

    public void setLoadListener(ActionListener listener) {
        load.addActionListener(listener);
    }

    public void setExitFromAccountListener(ActionListener listener) {
        exitFromAccount.addActionListener(listener);
    }

    @Override
    public int getWidth() {
        return 200;
    }

    @Override
    public int getHeight() {
        return 200;
    }
}
