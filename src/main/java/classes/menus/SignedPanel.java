package classes.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignedPanel extends SizePanel {
    private final JButton save = new JButton("Save");
    private final JButton load = new JButton("Load");
    private final JButton exitFromAccount = new JButton("Exit from account");
    private final JButton back = new JButton("Back");

    public SignedPanel() {
        Font font = new Font("Calibri", Font.BOLD, 30);

        setLayout(null);
        add(save);
        add(load);
        add(exitFromAccount);
        add(back);

        int startY = 300, buttonWidth = 500, buttonHeight = 50,
                x = getWidth() / 2 - buttonWidth / 2,
                indentation = 15;

        save.setFont(font);
        save.setBackground(Color.WHITE);
        save.setBounds(x, startY, buttonWidth, buttonHeight);

        load.setFont(font);
        load.setBackground(Color.WHITE);
        load.setBounds(x, indentation + buttonHeight + startY, buttonWidth, buttonHeight);

        exitFromAccount.setFont(font);
        exitFromAccount.setBackground(Color.WHITE);
        exitFromAccount.setBounds(x, (indentation + buttonHeight) * 2 + startY, buttonWidth, buttonHeight);

        back.setFont(font);
        back.setBackground(Color.WHITE);
        back.setBounds(x, (indentation + buttonHeight) * 3 + startY, buttonWidth, buttonHeight);
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
}
