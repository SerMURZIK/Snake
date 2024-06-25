package classes.accountClasses;

import classes.accountClasses.json.CurrentAccountJsonWriter;
import classes.accountClasses.json.JsonReader;
import classes.accountClasses.json.SaveSnakeJsonWriter;
import classes.menus.MainPanel;
import classes.menus.SignInPanel;
import classes.menus.SignUpPanel;
import classes.menus.SignedPanel;
import classes.menus.gamePanels.SinglePlayerPanel;
import classes.snakeClasses.blockClasses.Block;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountActions {

    private static final String USER_FOLDER = System.getProperty("user.home");
    private static final String GAMERS_SAVES_FOLDER_PATH =
            String.format("%s\\AppData\\LocalLow\\SnakeSaves\\", USER_FOLDER);
    private static final String GAMERS_SAVES_JSON_FORM = GAMERS_SAVES_FOLDER_PATH + "%s.json";
    private static final String SERVICE_SAVES_JSON_FORM = "service-saves/%s.json";
    private static final String CLEAR_ACCOUNT_FILE_NAME = "clearAccount";
    private static final String CURRENT_LOGIN_FILE_NAME = "currentLogin";

    private boolean rememberMe; //for persons who signed for current session (isSignedForCurrentSession)
    private final JFrame window;
    private final MainPanel mainPanel;
    private final SignInPanel signInPanel;
    private CurrentAccount currentAccount;
    private final SinglePlayerPanel gamePanel;
    private final SignUpPanel signUpPanel;
    private final SignedPanel signedPanel;

    public AccountActions(JFrame window,
                          MainPanel mainPanel,
                          SignInPanel signInPanel,
                          CurrentAccount currentAccount,
                          SinglePlayerPanel gamePanel,
                          SignUpPanel signUpPanel,
                          SignedPanel signedPanel) {
        this.window = window;
        this.mainPanel = mainPanel;
        this.signInPanel = signInPanel;
        this.currentAccount = currentAccount;
        this.gamePanel = gamePanel;
        this.signUpPanel = signUpPanel;
        this.signedPanel = signedPanel;
        createGameSavesFolders();
    }

    public void signIn() {
        JsonReader<SaveSnakeClass> reader = new JsonReader<>();
        if (new File(String.format(GAMERS_SAVES_JSON_FORM, signInPanel.getLogin())).exists()) {
            SaveSnakeClass saveSnakeClass = reader.getObjectFromJson(
                    GAMERS_SAVES_JSON_FORM, signInPanel.getLogin(), SaveSnakeClass.class);

            if (signInPanel.getPassword().equals(saveSnakeClass.getPassword())) {
                writeCurrentAccountJson(signInPanel.getLogin(), signInPanel.getPassword(), signInPanel.getSigned());

                rememberMe = true;
                getAllInfoFromJson();
                changePanel(signInPanel, mainPanel);

                signInPanel.removeWrongMessage();
                signInPanel.cleanFields();
            } else {
                signInPanel.showWrongMessage();
            }
            readAccountFromJson();
        } else {
            signInPanel.showWrongMessage();
        }
    }

    public void writeNewAccount() {
        SaveSnakeJsonWriter.write(GAMERS_SAVES_JSON_FORM, signUpPanel.getLogin(),
                signUpPanel.getPassword(),
                gamePanel.getSnake().getScore(),
                gamePanel.getSnake().getSpeed(),
                gamePanel.getSnake().getDirection(),
                getSnakeXCoordinates(), getSnakeYCoordinates());
        writeCurrentAccountJson(signUpPanel.getLogin(), signUpPanel.getPassword(), signUpPanel.getSigned());
        rememberMe = true;
        getAllInfoFromJson();
        readAccountFromJson();
        changePanel(signUpPanel, mainPanel);
        signUpPanel.cleanFields();
    }

    public void writeCurrentAccountJson(String login, String password, boolean signed) {
        CurrentAccountJsonWriter.write(GAMERS_SAVES_JSON_FORM, CURRENT_LOGIN_FILE_NAME, login, password, signed);
        updateCurrentAccount();
    }

    public void saveGame() {
        SaveSnakeJsonWriter.write(GAMERS_SAVES_JSON_FORM, currentAccount.getLogin(), currentAccount.getPassword(),
                gamePanel.getSnake().getScore(), gamePanel.getSnake().getSpeed(),
                gamePanel.getSnake().getDirection(),
                getSnakeXCoordinates(), getSnakeYCoordinates());

        writeCurrentAccountJson(currentAccount.getLogin(), currentAccount.getPassword(), getSigned());
    }

    public List<List<Integer>> getSnakeCoordinates() {
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

        List<List<Integer>> a = new ArrayList<>();
        a.add(x);
        a.add(y);
        return a;
    }

    public List<Integer> getSnakeXCoordinates() {
        return getSnakeCoordinates().get(0);
    }

    public List<Integer> getSnakeYCoordinates() {
        return getSnakeCoordinates().get(1);
    }

    public void readAccountFromJson() {
        JsonReader<CurrentAccountSaveClass> jsonReader = new JsonReader<>();
        CurrentAccountSaveClass currentAccountSaveClass =
                jsonReader.getObjectFromJson(GAMERS_SAVES_JSON_FORM, CURRENT_LOGIN_FILE_NAME,
                        CurrentAccountSaveClass.class);

        currentAccount.setLogin(currentAccountSaveClass.getLogin());
        currentAccount.setPassword(currentAccountSaveClass.getPassword());
        currentAccount.setSigned(currentAccountSaveClass.getSigned());
        updateExitListener();
    }

    public void getAllInfoFromJson() {
        String folder = currentAccount.getSigned() || rememberMe ? GAMERS_SAVES_JSON_FORM : SERVICE_SAVES_JSON_FORM;
        JsonReader<SaveSnakeClass> jsonReader = new JsonReader<>();
        SaveSnakeClass saveSnakeClass = jsonReader.getObjectFromJson(folder, currentAccount.getLogin(),
                SaveSnakeClass.class);
        Optional.ofNullable(saveSnakeClass)
                .ifPresent(this::setSavedSnakeInfoToPanel);
    }


    private void setSavedSnakeInfoToPanel(SaveSnakeClass snakeSave) {
        gamePanel.setInfo(snakeSave.getScore(), snakeSave.getSpeed(),
                snakeSave.getDirection(), snakeSave.getX(), snakeSave.getY());
    }

    public void cleanAccount() {
        JsonReader<CurrentAccount> jsonReader = new JsonReader<>();
        CurrentAccount currAcc = jsonReader.getObjectFromJson(SERVICE_SAVES_JSON_FORM, CLEAR_ACCOUNT_FILE_NAME,
                CurrentAccount.class);

        writeCurrentAccountJson(CLEAR_ACCOUNT_FILE_NAME, currAcc.getPassword(), false);
        rememberMe = false;
        getAllInfoFromJson();

        changePanel(signedPanel, mainPanel);
    }

    public void updateExitListener() {
        mainPanel.updateSign(getSigned());
    }

    public void changePanel(JPanel removePanel, JPanel newPanel) {
        window.remove(removePanel);
        window.add(newPanel);
        window.setSize(new Dimension(newPanel.getWidth(), newPanel.getHeight()));
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.repaint();
    }

    public boolean getRememberMe() {
        return rememberMe;
    }

    public void updateCurrentAccount() {
        JsonReader<CurrentAccount> reader = new JsonReader<>();
        currentAccount = reader.getObjectFromJson(GAMERS_SAVES_JSON_FORM, CURRENT_LOGIN_FILE_NAME,
                CurrentAccount.class);

    }

    public CurrentAccount getCurrentAccount() {
        return currentAccount;
    }

    public boolean getSigned() {
        return currentAccount.getSigned();
    }

    private void createGameSavesFolders() {
        File theDir = new File(GAMERS_SAVES_FOLDER_PATH);
        theDir.mkdirs();
    }
}