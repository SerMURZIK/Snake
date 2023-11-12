package classes.other;

import classes.accountClasses.*;
import classes.menus.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow {
    private final JFrame window = new JFrame();
    private final MainMenu mainMenu = new MainMenu();
    private final GamePanel gamePanel = new GamePanel();
    private final RestartPanel restartPanel = new RestartPanel();
    private final SignInPanel signInPanel = new SignInPanel();
    private final SignedPanel signedPanel = new SignedPanel();
    private final CurrentAccount currentAccount = new CurrentAccount();
    private final SignUpPanel signUpPanel = new SignUpPanel();
    private final StartMenu startMenuPanel = new StartMenu();
    private final SettingSize settingSize = new SettingSize();
    private final SettingsPanel settingsPanel = new SettingsPanel();
    private final AccountActions accountActions = new AccountActions(
            window,
            mainMenu,
            signInPanel,
            currentAccount,
            gamePanel,
            signUpPanel,
            signedPanel);

    public GameWindow() {
        accountActions.readAccountFromJson();

        gamePanel.addKeyListener(new KeyAdapter()    {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ESCAPE) {
                    openMainMenu();
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

        gamePanel.setExitListener(e -> openMainMenu());

        startMenuPanel.setStartListener(e -> {
            changePanel(startMenuPanel, settingSize);
            accountActions.getAllInfoFromJson();
        });

        settingSize.setEnterSizeListener(e -> {
            if (settingSize.wasEntered()) {
                changePanel(settingSize, mainMenu);
                settingSize.cleanFields();
            }
        });

        addMainMenuListeners();
        addSettingsPanelListeners();
        addSignedPanelListeners();
        addSignInPanelListeners();
        addSignUpPanelListeners();
        addRestartPanelListener();
    }

    public void openMainMenu() {
        gamePanel.stop();
        gamePanel.playSound(false);
        window.remove(gamePanel);
        if (gamePanel.isAlive()) {
            window.add(mainMenu);
            window.setSize(new Dimension(mainMenu.getWidth(), mainMenu.getHeight()));
        } else {
            window.add(restartPanel);
            window.setSize(new Dimension(restartPanel.getWidth(), restartPanel.getHeight()));
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
            changePanel(mainMenu, gamePanel);
            gamePanel.start();
            gamePanel.playSound(true);
            gamePanel.requestFocus();
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
        settingsPanel.settingSizeListener(e -> changePanel(settingsPanel, settingSize));
        settingsPanel.setBackListener(e -> changePanel(settingsPanel, mainMenu));
    }

    public void addRestartPanelListener() {
        restartPanel.setRestartListener(e -> {
            changePanel(restartPanel, gamePanel);
            gamePanel.restart();
            gamePanel.requestFocus();
        });

        restartPanel.setLoadListener(e -> {
            accountActions.getAllInfoFromJson();
            changePanel(restartPanel, gamePanel);
            gamePanel.loadLastSave();
            gamePanel.requestFocus();
        });
    }
}
