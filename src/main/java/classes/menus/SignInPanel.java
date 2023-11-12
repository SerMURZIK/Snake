package classes.menus;

import classes.other.CustomTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignInPanel extends JPanel {
    private final JButton signUp = new JButton("Sign up");
    private final JButton back = new JButton("Back");
    private final JButton enterButt = new JButton("Enter");
    private final JCheckBox checkSignedBox = new JCheckBox("Remember me");
    private final CustomTextField loginField = new CustomTextField();
    private final CustomTextField passwordField = new CustomTextField();
    private final String loginPlaceHolderText = "Input your login";
    private final String passwordPlaceHolderText = "Input your password";
    private final JLabel warningMessage = new JLabel("Wrong login or password");

    public SignInPanel() {
        Font font = new Font("Calibri", Font.BOLD, 20);

        setLayout(null);
        add(loginField);
        add(passwordField);
        add(checkSignedBox);
        add(enterButt);
        add(signUp);
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

        enterButt.setBounds(10, 190, 100, 30);
        enterButt.setFont(font);
        enterButt.setBackground(Color.WHITE);

        signUp.setBounds(10, 230, 100, 30);
        signUp.setFont(font);
        signUp.setBackground(Color.WHITE);

        back.setBounds(10, 270, 100, 30);
        back.setFont(font);
        back.setBackground(Color.WHITE);

        warningMessage.setBounds(10, 310, 250, 30);
        warningMessage.setFont(font);
        warningMessage.setBackground(Color.WHITE);
    }

    public void showWrongMessage() {
        add(warningMessage);
        repaint();
    }

    public void removeWrongMessage() {
        remove(warningMessage);
        repaint();
    }

    public boolean getSigned() {
        return checkSignedBox.isSelected();
    }

    public String getLogin() {
        try {
            return loginField.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPassword() {
        try {
            return passwordField.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setBackListener(ActionListener listener) {
        back.addActionListener(listener);
    }

    public void setEnterListener(ActionListener listener) {
        enterButt.addActionListener(listener);
    }

    public void setSignUpListener(ActionListener listener) {
        signUp.addActionListener(listener);
    }

    public void cleanFields() {
        loginField.setText(null);
        loginField.setPlaceholder(loginPlaceHolderText);

        passwordField.setText(null);
        passwordField.setPlaceholder(passwordPlaceHolderText);
    }

    @Override
    public int getWidth() {
        return 250;
    }

    @Override
    public int getHeight() {
        return 350;
    }
}
