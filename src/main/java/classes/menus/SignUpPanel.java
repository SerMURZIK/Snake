package classes.menus;

import classes.other.CustomTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignUpPanel extends JPanel {
    private final JButton back = new JButton("Back");
    private final JButton enterButt = new JButton("Enter");
    private final CustomTextField loginField = new CustomTextField();
    private final CustomTextField passwordField = new CustomTextField();
    private final String loginPlaceHolderText = "Input your login";
    private final String passwordPlaceHolderText = "Input your password";
    private final JCheckBox checkSignedBox = new JCheckBox("Remember me");

    public SignUpPanel() {
        Font font = new Font("Calibri", Font.BOLD, 20);

        setLayout(null);
        add(loginField);
        add(passwordField);
        add(checkSignedBox);
        add(enterButt);
        add(back);

        loginField.setBounds(10, 10, 210, 50);
        loginField.setFont(font);
        loginField.setForeground(Color.BLACK);
        loginField.setPlaceholderForeground(Color.GRAY);
        loginField.setPlaceholder(loginPlaceHolderText);

        passwordField.setBounds(10, 70, 210, 50);
        passwordField.setFont(font);
        passwordField.setForeground(Color.BLACK);
        passwordField.setPlaceholderForeground(Color.GRAY);
        passwordField.setPlaceholder(passwordPlaceHolderText);

        checkSignedBox.setBounds(10, 130, 210, 50);
        checkSignedBox.setFont(font);

        enterButt.setBounds(10, 190, 130, 30);
        enterButt.setFont(font);
        enterButt.setBackground(Color.WHITE);

        back.setBounds(10, 230, 100, 30);
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

    @Override
    public int getWidth() {
        return 251;
    }

    @Override
    public int getHeight() {
        return 300;
    }
}
