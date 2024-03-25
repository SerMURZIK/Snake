package classes.menus;

import classes.other.CustomTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignUpPanel extends SizePanel {
    private final JButton back = new JButton("Back");
    private final JButton enterButt = new JButton("Enter");
    private final CustomTextField loginField = new CustomTextField();
    private final CustomTextField passwordField = new CustomTextField();
    private final String loginPlaceHolderText = "Input your login";
    private final String passwordPlaceHolderText = "Input your password";
    private final JCheckBox checkSignedBox = new JCheckBox("Remember me");

    public SignUpPanel() {
        Font font = new Font("Calibri", Font.BOLD, 30);

        setLayout(null);
        add(loginField);
        add(passwordField);
        add(checkSignedBox);
        add(enterButt);
        add(back);


        int startY = 250, buttonWidth = 500, buttonHeight = 50,
                x = getWidth() / 2 - buttonWidth / 2,
                indentation = 15;

        loginField.setBounds(x, startY, buttonWidth, buttonHeight);
        loginField.setFont(font);
        loginField.setForeground(Color.BLACK);
        loginField.setPlaceholderForeground(Color.GRAY);
        loginField.setPlaceholder(loginPlaceHolderText);

        passwordField.setBounds(x, indentation + buttonHeight + startY, buttonWidth, buttonHeight);
        passwordField.setFont(font);
        passwordField.setForeground(Color.BLACK);
        passwordField.setPlaceholderForeground(Color.GRAY);
        passwordField.setPlaceholder(passwordPlaceHolderText);

        checkSignedBox.setBounds(x, (indentation + buttonHeight) * 2 + startY, buttonWidth, buttonHeight);
        checkSignedBox.setFont(font);

        enterButt.setBounds(x, (indentation + buttonHeight) * 3 + startY, buttonWidth, buttonHeight);
        enterButt.setFont(font);
        enterButt.setBackground(Color.WHITE);

        back.setBounds(x, (indentation + buttonHeight) * 4 + startY, buttonWidth, buttonHeight);
        back.setFont(font);
        back.setBackground(Color.WHITE);
    }

    public void cleanFields() {
        loginField.setText(null);
        loginField.setPlaceholder(loginPlaceHolderText);

        passwordField.setText(null);
        passwordField.setPlaceholder(passwordPlaceHolderText);
    }

    public boolean getSigned() {
        return checkSignedBox.isSelected();
    }

    public String getLogin() {
        return loginField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public void setBackListener(ActionListener listener) {
        back.addActionListener(listener);
    }

    public void setEnterListener(ActionListener listener) {
        enterButt.addActionListener(listener);
    }

}
