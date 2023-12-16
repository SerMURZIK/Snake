package classes.accountClasses;

import classes.accountClasses.json.CurrentAccountJsonWriter;
import classes.accountClasses.json.JsonReader;
import classes.accountClasses.json.SaveSnakeJsonWriter;
import classes.menus.MainMenu;
import classes.menus.SignInPanel;
import classes.menus.SignUpPanel;
import classes.menus.SignedPanel;
import classes.menus.gameMenues.SinglePlayerPanel;
import classes.snakeClasses.blockClasses.Block;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountActions {

    private static final String SERVICE_FOLDER_JSON = "src/main/resources/files/saves/service/%s.json";
    private static final String GAMERS_FOLDER_JSON = "src/main/resources/files/saves/gamers/%s.json";
    private static final String CLEAR_ACCOUNT = "clearAccount";
    private static final String CURRENT_LOGIN = "currentLogin";

    private boolean rememberMe; //for persons who signed for current session (isSignedForCurrentSession)
    private final JFrame window;
    private final MainMenu mainMenu;
    private final SignInPanel signInPanel;
    private CurrentAccount currentAccount;
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
        JsonReader<SaveSnakeClass> reader = new JsonReader<>();
        if (new File(String.format(GAMERS_FOLDER_JSON, signInPanel.getLogin())).exists()) {
            SaveSnakeClass saveSnakeClass = reader.getObjectFromJson(GAMERS_FOLDER_JSON, signInPanel.getLogin(), SaveSnakeClass.class);

            if (signInPanel.getPassword().equals(saveSnakeClass.getPassword())) {
                writeCurrentAccountJson(signInPanel.getLogin(), signInPanel.getPassword(), signInPanel.getSigned());

                getAllInfoFromJson();
                changePanel(signInPanel, mainMenu);

                signInPanel.removeWrongMessage();
                signInPanel.cleanFields();
                rememberMe = true;
            } else {
                signInPanel.showWrongMessage();
            }
            readAccountFromJson();
        } else {
            signInPanel.showWrongMessage();
        }
    }

    public void writeNewAccount() {
        SaveSnakeJsonWriter.write(GAMERS_FOLDER_JSON, signUpPanel.getLogin(),
                signUpPanel.getPassword(),
                gamePanel.getSnake().getScore(),
                gamePanel.getSnake().getSpeed(),
                gamePanel.getSnake().getDirection(),
                getSnakeXCoordinates(), getSnakeYCoordinates());
        writeCurrentAccountJson(signUpPanel.getLogin(), signUpPanel.getPassword(), signUpPanel.getSigned());
        rememberMe = true;
        getAllInfoFromJson();
        readAccountFromJson();
        changePanel(signUpPanel, mainMenu);
        signUpPanel.cleanFields();
    }

    public void writeCurrentAccountJson(String login, String password, boolean signed) {
        CurrentAccountJsonWriter.write(SERVICE_FOLDER_JSON, CURRENT_LOGIN, login, password, signed);
        rememberMe = signed;
        updateCurrentAccount();
    }

    public void saveGame() {
        SaveSnakeJsonWriter.write(GAMERS_FOLDER_JSON, currentAccount.getLogin(), currentAccount.getPassword(),
                gamePanel.getSnake().getScore(), gamePanel.getSnake().getSpeed(),
                gamePanel.getSnake().getDirection(),
                getSnakeXCoordinates(), getSnakeYCoordinates());

        writeCurrentAccountJson(currentAccount.getLogin(), currentAccount.getPassword(), currentAccount.getSigned());
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
                jsonReader.getObjectFromJson(SERVICE_FOLDER_JSON, CURRENT_LOGIN, CurrentAccountSaveClass.class);

        currentAccount.setLogin(currentAccountSaveClass.getLogin());
        currentAccount.setPassword(currentAccountSaveClass.getPassword());
        currentAccount.setSigned(currentAccountSaveClass.getSigned());
        updateExitListener();
    }

    public void getAllInfoFromJson() {
        String folder = currentAccount.getSigned() || rememberMe ? GAMERS_FOLDER_JSON : SERVICE_FOLDER_JSON;
        JsonReader<SaveSnakeClass> jsonReader = new JsonReader<>();
        SaveSnakeClass saveSnakeClass = jsonReader.getObjectFromJson(folder, currentAccount.getLogin(), SaveSnakeClass.class);
        Optional.ofNullable(saveSnakeClass)
                .ifPresent(this::setSavedSnakeInfoToPanel);
    }


    private void setSavedSnakeInfoToPanel(SaveSnakeClass snakeSave) {
        gamePanel.setInfo(snakeSave.getScore(), snakeSave.getSpeed(),
                snakeSave.getDirection(), snakeSave.getX(), snakeSave.getY());
    }

    public void cleanAccount() {
        JsonReader<CurrentAccount> jsonReader = new JsonReader<>();
        CurrentAccount currAcc = jsonReader.getObjectFromJson(SERVICE_FOLDER_JSON, CLEAR_ACCOUNT, CurrentAccount.class);

        writeCurrentAccountJson(CLEAR_ACCOUNT, currAcc.getPassword(), false);
        getAllInfoFromJson();

        rememberMe = false;
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

    public boolean getRememberMe() {
        return rememberMe;
    }

    public void updateCurrentAccount() {
        JsonReader<CurrentAccount> reader = new JsonReader<>();
        currentAccount = reader.getObjectFromJson(SERVICE_FOLDER_JSON, CURRENT_LOGIN, CurrentAccount.class);
    }

    public CurrentAccount getCurrentAccount() {
        return currentAccount;
    }
}