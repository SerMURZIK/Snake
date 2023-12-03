package classes.other;

import classes.accountClasses.CurrentAccount;
import classes.menus.*;
import classes.menus.gameMenues.MultiplayerPanel;
import classes.menus.gameMenues.SinglePlayerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameWindow {
    private final JFrame window = new JFrame();

    public  static int WINDOW_WIDTH = 1800;
    public  static int WINDOW_HEIGHT = 1800;

    private SinglePlayerPanel singlePlayer = new SinglePlayerPanel();
    private MultiplayerPanel multiplayer = new MultiplayerPanel();

    private final MainMenu mainMenu = new MainMenu();
    private final StartMenu startMenuPanel = new StartMenu();
    private final SettingSize settingSizePanel = new SettingSize();
    private final SettingsPanel settingsPanel = new SettingsPanel();
    private final RestartPanel restartPanelSinglePlayer = new RestartPanel();
    private final RestartPanel restartMultiplayerPanel = new RestartPanel();

    private final SignInPanel signInPanel = new SignInPanel();
    private final SignedPanel signedPanel = new SignedPanel();
    private final SignUpPanel signUpPanel = new SignUpPanel();

    private final CurrentAccount currentAccount = new CurrentAccount();
    private final AccountActions accountActions = new AccountActions(
            window,
            mainMenu,
            signInPanel,
            currentAccount,
            singlePlayer,
            signUpPanel,
            signedPanel);

    public GameWindow() {
        accountActions.readAccountFromJson();

        singlePlayer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ESCAPE) {
                    openSinglePlayerMainMenu();
                }
            }
        });

        multiplayer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ESCAPE) {
                    openMultiplayerMainMenu();
                }
            }
        });

        window.setResizable(false);
        window.setUndecorated(true);
        window.setSize(new Dimension(startMenuPanel.getWidth(), startMenuPanel.getHeight()));
        window.setName("Snake");
        window.setLocationRelativeTo(null);
        window.add(startMenuPanel);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        singlePlayer.setExitListener(e -> openSinglePlayerMainMenu());
        multiplayer.setExitListener(e -> openMultiplayerMainMenu());

        startMenuPanel.setStartListener(e -> {
            changePanel(startMenuPanel, settingSizePanel);
            accountActions.getAllInfoFromJson();
        });

        settingSizePanel.setEnterSizeListener(e -> {
            if (settingSizePanel.wasEntered()) {
                changePanel(settingSizePanel, mainMenu);
                settingSizePanel.cleanFields();
            }
        });

        addMainMenuListeners();
        addSettingsPanelListeners();
        addSignedPanelListeners();
        addSignInPanelListeners();
        addSignUpPanelListeners();
        addRestartPanelListener();
    }

    public void openSinglePlayerMainMenu() {
        singlePlayer.stop();
        singlePlayer.playSound(false);
        window.remove(singlePlayer);
        if (singlePlayer.isAlive()) {
            window.add(mainMenu);
            window.setSize(new Dimension(mainMenu.getWidth(), mainMenu.getHeight()));
        } else {
            window.add(restartPanelSinglePlayer);
            window.setSize(new Dimension(restartPanelSinglePlayer.getWidth(), restartPanelSinglePlayer.getHeight()));
        }
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void openMultiplayerMainMenu() {
        multiplayer.stop();
        multiplayer.playSound(false);
        window.remove(multiplayer);
        if (multiplayer.isAlive()) {
            window.add(mainMenu);
            window.setSize(new Dimension(mainMenu.getWidth(), mainMenu.getHeight()));
        } else {
            window.add(restartMultiplayerPanel);
            window.setSize(new Dimension(restartPanelSinglePlayer.getWidth(), restartPanelSinglePlayer.getHeight()));
        }
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void openAccountMenu() {
        window.remove(mainMenu);
        if (currentAccount.getSigned() || accountActions.getAhalay()) {
            window.add(signedPanel);
            window.setSize(new Dimension(signedPanel.getWidth(), signedPanel.getHeight()));
        } else {
            window.add(signInPanel);
            window.setSize(new Dimension(signInPanel.getWidth(), signInPanel.getHeight()));
        }
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void changePanel(JPanel removePanel, JPanel newPanel) {
        window.remove(removePanel);
        window.add(newPanel);
        window.setSize(new Dimension(newPanel.getWidth(), newPanel.getHeight()));
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void addMainMenuListeners() {
        mainMenu.setStartListener(e -> {
            changePanel(mainMenu, singlePlayer);
            singlePlayer.start();
            singlePlayer.playSound(true);
            singlePlayer.requestFocus();
        });

        mainMenu.setMultiplayerListener(e -> {
            changePanel(mainMenu, multiplayer);
            multiplayer.start();
            multiplayer.playSound(true);
            multiplayer.requestFocus();
        });

        mainMenu.setExitListener(e -> accountActions.cleanAccount());
        mainMenu.setSettingsListener(e -> changePanel(mainMenu, settingsPanel));
        mainMenu.setAccountListener(e -> openAccountMenu());
    }

    public void addSignedPanelListeners() {
        signedPanel.setSaveListener(e -> accountActions.saveProgress());

        signedPanel.setLoadListener(e -> accountActions.getAllInfoFromJson());

        signedPanel.setBackListener(e -> changePanel(signedPanel, mainMenu));

        signedPanel.setExitFromAccountListener(e -> {
            accountActions.cleanAccount();
            changePanel(signedPanel, mainMenu);
        });
    }

    public void addSignInPanelListeners() {
        signInPanel.setBackListener(e -> {
            changePanel(signInPanel, mainMenu);
            signInPanel.removeWrongMessage();
            signInPanel.cleanFields();
        });

        signInPanel.setSignUpListener(e -> {
            changePanel(signInPanel, signUpPanel);
            signInPanel.removeWrongMessage();
            signInPanel.cleanFields();
        });

        signInPanel.setEnterListener(e -> accountActions.signIn());
    }

    public void addSignUpPanelListeners() {
        signUpPanel.setBackListener(e -> {
            changePanel(signUpPanel, signInPanel);
            signUpPanel.cleanFields();
        });

        signUpPanel.setEnterListener(e -> accountActions.writeNewAccount());
    }

    public void addSettingsPanelListeners() {
        settingsPanel.settingSizeListener(e -> changePanel(settingsPanel, settingSizePanel));
        settingsPanel.setBackListener(e -> changePanel(settingsPanel, mainMenu));
    }

    public void addRestartPanelListener() {
        restartPanelSinglePlayer.setRestartListener(e -> {
            changePanel(restartPanelSinglePlayer, singlePlayer);
            singlePlayer = new SinglePlayerPanel();
            singlePlayer.requestFocus();
        });

        restartPanelSinglePlayer.setLoadListener(e -> {
            accountActions.getAllInfoFromJson();
            changePanel(restartPanelSinglePlayer, singlePlayer);
            singlePlayer.loadLastSave();
            singlePlayer.requestFocus();
        });

        restartMultiplayerPanel.setRestartListener(e -> {
            changePanel(restartMultiplayerPanel, multiplayer);
            multiplayer = new MultiplayerPanel();
            multiplayer.requestFocus();
        });

        restartMultiplayerPanel.setLoadListener(e -> {
            accountActions.getAllInfoFromJson();
            changePanel(restartMultiplayerPanel, multiplayer);
            multiplayer.loadLastSave();
            multiplayer.requestFocus();
        });
    }

    public static void setWindowWidth(int windowWidth) {
        WINDOW_WIDTH = windowWidth;
    }

    public static void setWindowHeight(int windowHeight) {
        WINDOW_HEIGHT = windowHeight;
    }
}
