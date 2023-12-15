package classes.accountClasses;

import classes.menus.*;
import classes.menus.gameMenues.SinglePlayerPanel;
import classes.snakeClasses.blockClasses.*;
import com.google.gson.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class AccountActions {
    private boolean ahalay;
    private final JFrame window;
    private final File serviceFolderJson = new File("src/main/resources/files/saves/service");
    private final File gamersFolderJson = new File("src/main/resources/files/saves/gamers");
    private final String titleOfClearAcc = "clearAccount";
    private final MainMenu mainMenu;
    private final SignInPanel signInPanel;
    private final CurrentAccount currentAccount;
    private final SinglePlayerPanel gamePanel;
    private final SignUpPanel signUpPanel;
    private final SignedPanel signedPanel;

    public AccountActions(JFrame window,
                          MainMenu mainMenu,
                          SignInPanel signInPanel,
                          CurrentAccount currentAccount,
                          SinglePlayerPanel gamePanel,
                          SignUpPanel signUpPanel,
                          SignedPanel signedPanel) {
        this.window = window;
        this.mainMenu = mainMenu;
        this.signInPanel = signInPanel;
        this.currentAccount = currentAccount;
        this.gamePanel = gamePanel;
        this.signUpPanel = signUpPanel;
        this.signedPanel = signedPanel;
    }

    public void signIn() {
        if (new File(gamersFolderJson + "/" + signInPanel.getLogin() + ".json").exists()) {
            try (FileReader reader = new FileReader(
                    gamersFolderJson.getPath() + "/" + signInPanel.getLogin() + ".json")) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                SnakeSaveClass snakeSaveClass = gson.fromJson(reader, SnakeSaveClass.class);

                if (signInPanel.getPassword().equals(snakeSaveClass.getPassword())) {
                    writeCurrentAccountJson(signInPanel.getLogin(), signInPanel.getPassword(), signInPanel.getSigned());

                    getAllInfoFromJson();
                    changePanel(signInPanel, mainMenu);

                    signInPanel.removeWrongMessage();
                    signInPanel.cleanFields();
                    ahalay = true;
                } else {
                    signInPanel.showWrongMessage();
                }
                readAccountFromJson();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        } else {
            signInPanel.showWrongMessage();
        }
    }

    public void writeNewAccount() {
        try (FileWriter file = new FileWriter(gamersFolderJson.getPath() + "/" + signUpPanel.getLogin() + ".json")) {
            GsonBuilder builderW = new GsonBuilder();
            Gson gsonW = builderW.create();

            try (FileReader reader = new FileReader(serviceFolderJson.getPath() + "/" + titleOfClearAcc + ".json")) {

                GsonBuilder builderR = new GsonBuilder();
                Gson gsonR = builderR.create();
                SnakeSaveClass snakeSaveClass = gsonR.fromJson(reader, SnakeSaveClass.class);
                file.write(gsonW.toJson(new SnakeSaveClass(
                        signUpPanel.getPassword(),
                        gamePanel.getSnake().getScore(),
                        gamePanel.getSnake().getSpeed(),
                        gamePanel.getSnake().getDirection(),
                        snakeSaveClass.getX(),
                        snakeSaveClass.getY())));
                updateExitListener();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
            file.flush();

            writeCurrentAccountJson(signUpPanel.getLogin(), signUpPanel.getPassword(), signUpPanel.getSigned());
            ahalay = true;
            getAllInfoFromJson();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        readAccountFromJson();
        changePanel(signUpPanel, mainMenu);
        signUpPanel.cleanFields();
    }

    public void writeCurrentAccountJson(String login, String password, boolean signed) {
        try (FileWriter file = new FileWriter(serviceFolderJson.getPath() + "/currentLogin.json")) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            file.write(gson.toJson(new CurrentAccountSaveClass(login, password, signed)));
            file.flush();
            readAccountFromJson();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void saveGame() {
        try (FileWriter file = new FileWriter(gamersFolderJson.getPath() + "/" + currentAccount.getLogin() + ".json")) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            List<Integer> x = new ArrayList<>();
            List<Integer> y = new ArrayList<>();

            Block current = gamePanel.getSnake().getHead();
            x.add(current.getX());
            y.add(current.getY());
            do {
                current = current.getNext();
                x.add(current.getX());
                y.add(current.getY());
            } while (current.getNext() != null);

            file.write(gson.toJson(new SnakeSaveClass(
                    currentAccount.getPassword(),
                    gamePanel.getSnake().getScore(),
                    gamePanel.getSnake().getSpeed(),
                    gamePanel.getSnake().getDirection(),
                    x, y)));
            file.flush();

            writeCurrentAccountJson(currentAccount.getLogin(), currentAccount.getPassword(), currentAccount.getSigned());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void readAccountFromJson() {
        try (FileReader reader = new FileReader(serviceFolderJson.getPath() + "/currentLogin.json")) {

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            CurrentAccountSaveClass currentAccountSaveClass = gson.fromJson(reader, CurrentAccountSaveClass.class);

            currentAccount.setLogin(currentAccountSaveClass.getLogin());
            currentAccount.setPassword(currentAccountSaveClass.getPassword());
            currentAccount.setSigned(currentAccountSaveClass.getSigned());
            updateExitListener();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void getAllInfoFromJson() {
        try (FileReader reader = new FileReader(
                (currentAccount.getSigned() ? gamersFolderJson.getPath() : serviceFolderJson.getPath())
                        + "/" + currentAccount.getLogin() + ".json")) {

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            SnakeSaveClass snakeSaveClass = gson.fromJson(reader, SnakeSaveClass.class);

            gamePanel.setInfo(snakeSaveClass.getScore(), snakeSaveClass.getSpeed(), snakeSaveClass.getDirection(), snakeSaveClass.getX(), snakeSaveClass.getY());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void cleanAccount() {
        try (FileReader reader = new FileReader(serviceFolderJson.getPath() + "/" + titleOfClearAcc + ".json")) {

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            CurrentAccount currAcc = gson.fromJson(reader, CurrentAccount.class);

            writeCurrentAccountJson(titleOfClearAcc, currAcc.getPassword(), false);
            getAllInfoFromJson();

            ahalay = false;
            changePanel(signedPanel, mainMenu);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void updateExitListener() {
        mainMenu.updateSign(currentAccount.getSigned());
    }

    public void changePanel(JPanel removePanel, JPanel newPanel) {
        window.remove(removePanel);
        window.add(newPanel);
        window.setSize(new Dimension(newPanel.getWidth(), newPanel.getHeight()));
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public boolean getAhalay() {
        return ahalay;
    }
}


/*package classes.accountClasses;

import classes.menus.MainMenu;
import classes.menus.SignInPanel;
import classes.menus.SignUpPanel;
import classes.menus.SignedPanel;
import classes.menus.gameMenues.SinglePlayerPanel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class AccountActions {
    private boolean ahalay; //for persons who signed for current session (isSignedForCurrentSession)
    private final JFrame window;
    private final File serviceFolderJson = new File("src/main/resources/files/saves/service");
    private final File gamersFolderJson = new File("src/main/resources/files/saves/gamers");
    private final String titleOfClearAcc = "clearAccount";
    private final MainMenu mainMenu;
    private final SignInPanel signInPanel;
    private final CurrentAccount currentAccount;
    private final SinglePlayerPanel gamePanel;
    private final SignUpPanel signUpPanel;
    private final SignedPanel signedPanel;

    public AccountActions(JFrame window,
                          MainMenu mainMenu,
                          SignInPanel signInPanel,
                          CurrentAccount currentAccount,
                          SinglePlayerPanel gamePanel,
                          SignUpPanel signUpPanel,
                          SignedPanel signedPanel) {
        this.window = window;
        this.mainMenu = mainMenu;
        this.signInPanel = signInPanel;
        this.currentAccount = currentAccount;
        this.gamePanel = gamePanel;
        this.signUpPanel = signUpPanel;
        this.signedPanel = signedPanel;
    }

    public void signIn() {
        if (new File(gamersFolderJson + "/" + signInPanel.getLogin() + ".json").exists()) {
            try (FileReader reader = new FileReader(
                    gamersFolderJson.getPath() + "/" + signInPanel.getLogin() + ".json")) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                SnakeSaveClass snakeSaveClass = gson.fromJson(reader, SnakeSaveClass.class);

                if (signInPanel.getPassword().equals(snakeSaveClass.getPassword())) {
                    writeCurrentAccountJson(signInPanel.getLogin(), signInPanel.getPassword(), signInPanel.getSigned());

                    getAllInfoFromJson();
                    changePanel(signInPanel, mainMenu);

                    signInPanel.removeWrongMessage();
                    signInPanel.cleanFields();
                    ahalay = true;
                } else {
                    signInPanel.showWrongMessage();
                }
                readAccountFromJson();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        } else {
            signInPanel.showWrongMessage();
        }
    }

    public void writeNewAccount() {
        try (FileWriter file = new FileWriter(gamersFolderJson.getPath() + "/" + signUpPanel.getLogin() + ".json")) {
            GsonBuilder builderW = new GsonBuilder();
            Gson gsonW = builderW.create();

            file.write(gsonW.toJson(new SnakeSaveClass(
                    signUpPanel.getPassword(),
                    gamePanel.getSnake().getScore(),
                    gamePanel.getSnake().getSpeed(),
                    gamePanel.getSnake().getDirection(),
                    gamePanel.getSnake().getHead(),
                    gamePanel.getSnake().getTail())));
            updateExitListener();
            file.flush();

            writeCurrentAccountJson(signUpPanel.getLogin(), signUpPanel.getPassword(), signUpPanel.getSigned());
            ahalay = true;
            getAllInfoFromJson();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        readAccountFromJson();
        changePanel(signUpPanel, mainMenu);
        signUpPanel.cleanFields();
    }

    public void writeCurrentAccountJson(String login, String password, boolean signed) {
        try (FileWriter file = new FileWriter(serviceFolderJson.getPath() + "/currentLogin.json")) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            file.write(gson.toJson(new CurrentAccountSaveClass(login, password, signed)));
            file.flush();
            readAccountFromJson();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void saveGame() {
        try (FileWriter file = new FileWriter(gamersFolderJson.getPath() + "/" + currentAccount.getLogin() + ".json")) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            file.write(gson.toJson(new SnakeSaveClass(
                    currentAccount.getPassword(),
                    gamePanel.getSnake().getScore(),
                    gamePanel.getSnake().getSpeed(),
                    gamePanel.getSnake().getDirection(),
                    gamePanel.getSnake().getHead(),
                    gamePanel.getSnake().getTail())));
            file.flush();

            writeCurrentAccountJson(currentAccount.getLogin(), currentAccount.getPassword(), currentAccount.getSigned());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void readAccountFromJson() {
        try (FileReader reader = new FileReader(serviceFolderJson.getPath() + "/currentLogin.json")) {

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            CurrentAccountSaveClass currentAccountSaveClass = gson.fromJson(reader, CurrentAccountSaveClass.class);

            currentAccount.setLogin(currentAccountSaveClass.getLogin());
            currentAccount.setPassword(currentAccountSaveClass.getPassword());
            currentAccount.setSigned(currentAccountSaveClass.getSigned());
            updateExitListener();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void getAllInfoFromJson() {
        try (FileReader reader = new FileReader(
                (currentAccount.getSigned() ? gamersFolderJson.getPath() : serviceFolderJson.getPath())
                        + "/" + currentAccount.getLogin() + ".json")) {

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            SnakeSaveClass snakeSaveClass = gson.fromJson(reader, SnakeSaveClass.class);

            gamePanel.setInfo(
                    snakeSaveClass.getHead(),
                    snakeSaveClass.getTail(),
                    snakeSaveClass.getScore(),
                    snakeSaveClass.getSpeed());
            gamePanel.getSnake().setDirection(snakeSaveClass.getDirection());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void cleanAccount() {
        try (FileReader reader = new FileReader(serviceFolderJson.getPath() + "/" + titleOfClearAcc + ".json")) {

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            CurrentAccount currAcc = gson.fromJson(reader, CurrentAccount.class);

            writeCurrentAccountJson(titleOfClearAcc, currAcc.getPassword(), false);
            getAllInfoFromJson();

            ahalay = false;
            changePanel(signedPanel, mainMenu);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void updateExitListener() {
        mainMenu.updateSign(currentAccount.getSigned());
    }

    public void changePanel(JPanel removePanel, JPanel newPanel) {
        window.remove(removePanel);
        window.add(newPanel);
        window.setSize(new Dimension(newPanel.getWidth(), newPanel.getHeight()));
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public boolean getAhalay() {
        return ahalay;
    }
}
*/