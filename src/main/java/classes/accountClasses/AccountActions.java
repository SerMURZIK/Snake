package classes.accountClasses;

import classes.accountClasses.json.JsonReader;
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

    private static final String SERVICE_FOLDER_JSON = "src/main/resources/files/saves/service/%s.json";
    private static final String GAMERS_FOLDER_JSON = "src/main/resources/files/saves/gamers/%s.json";
    private static final String CLEAR_ACCOUNT = "clearAccount";
    private static final String CURRENT_LOGIN = "currentLogin";

    private boolean keepMeSigned; //for persons who signed for current session (isSignedForCurrentSession)
    private final JFrame window;
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
        if (new File(String.format(GAMERS_FOLDER_JSON, signInPanel.getLogin())).exists()) {
            try (FileReader reader = new FileReader(
                    String.format(GAMERS_FOLDER_JSON, signInPanel.getLogin()))) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                SnakeSaveClass snakeSaveClass = gson.fromJson(reader, SnakeSaveClass.class);

                if (signInPanel.getPassword().equals(snakeSaveClass.getPassword())) {
                    writeCurrentAccountJson(signInPanel.getLogin(), signInPanel.getPassword(), signInPanel.getSigned());

                    getAllInfoFromJson();
                    changePanel(signInPanel, mainMenu);

                    signInPanel.removeWrongMessage();
                    signInPanel.cleanFields();
                    keepMeSigned = true;
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
        try (FileWriter file = new FileWriter(String.format(GAMERS_FOLDER_JSON, signUpPanel.getLogin()))) {
            GsonBuilder builderW = new GsonBuilder();
            Gson gsonW = builderW.create();

            try (FileReader reader = new FileReader(String.format(SERVICE_FOLDER_JSON, CLEAR_ACCOUNT))) {
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
            keepMeSigned = true;
            getAllInfoFromJson();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        readAccountFromJson();
        changePanel(signUpPanel, mainMenu);
        signUpPanel.cleanFields();
    }

    public void writeCurrentAccountJson(String login, String password, boolean signed) {
        try (FileWriter file = new FileWriter(String.format(SERVICE_FOLDER_JSON, CURRENT_LOGIN))) {
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
        try (FileWriter file = new FileWriter(String.format(GAMERS_FOLDER_JSON, currentAccount.getLogin()))) {
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
        JsonReader<CurrentAccountSaveClass> jsonReader = new JsonReader<>();
        CurrentAccountSaveClass currentAccountSaveClass = jsonReader.getObjectFromJson(SERVICE_FOLDER_JSON, CURRENT_LOGIN, CurrentAccountSaveClass.class);

        currentAccount.setLogin(currentAccountSaveClass.getLogin());
        currentAccount.setPassword(currentAccountSaveClass.getPassword());
        currentAccount.setSigned(currentAccountSaveClass.getSigned());
        updateExitListener();
    }

    public void getAllInfoFromJson() {
        String folder = currentAccount.getSigned() ? GAMERS_FOLDER_JSON : SERVICE_FOLDER_JSON;
        JsonReader<SnakeSaveClass> jsonReader = new JsonReader<>();
        SnakeSaveClass snakeSaveClass = jsonReader.getObjectFromJson(folder, currentAccount.getLogin(), SnakeSaveClass.class);
        Optional.ofNullable(snakeSaveClass)
                .ifPresent(this::setSavedSnakeInfoToPanel);
    }


    private void setSavedSnakeInfoToPanel(SnakeSaveClass snakeSave) {
        gamePanel.setInfo(snakeSave.getScore(), snakeSave.getSpeed(), snakeSave.getDirection(), snakeSave.getX(), snakeSave.getY());
    }

    public void cleanAccount() {
        JsonReader<CurrentAccount> jsonReader = new JsonReader<>();
        CurrentAccount currAcc = jsonReader.getObjectFromJson(SERVICE_FOLDER_JSON, CLEAR_ACCOUNT, CurrentAccount.class);

        writeCurrentAccountJson(CLEAR_ACCOUNT, currAcc.getPassword(), false);
        getAllInfoFromJson();

        keepMeSigned = false;
        changePanel(signedPanel, mainMenu);
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

    public boolean getKeepMeSigned() {
        return keepMeSigned;
    }
}