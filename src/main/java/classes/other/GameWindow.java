package classes.other;

import classes.accountClasses.AccountActions;
import classes.accountClasses.CurrentAccount;
import classes.menus.*;
import classes.menus.gamePanels.MultiplayerPanel;
import classes.menus.gamePanels.SinglePlayerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameWindow {
    private final JFrame window = new JFrame();

    private SinglePlayerPanel singlePlayer = new SinglePlayerPanel();
    private MultiplayerPanel multiplayer = new MultiplayerPanel();

    private final MainPanel mainPanel = new MainPanel();
    private final RestartPanel restartPanelSinglePlayer = new RestartPanel();
    private final RestartPanel restartMultiplayerPanel = new RestartPanel();
    private final TabWithControlButtons tabWithControlButtons = new TabWithControlButtons();

    private final SignInPanel signInPanel = new SignInPanel();
    private final SignedPanel signedPanel = new SignedPanel();
    private final SignUpPanel signUpPanel = new SignUpPanel();

    private CurrentAccount currentAccount = new CurrentAccount();
    private final AccountActions accountActions = new AccountActions(
            window,
            mainPanel,
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
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    openSinglePlayerMainMenu();
                }
            }
        });

        multiplayer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    openMultiplayerMainMenu();
                }
            }
        });

        window.setResizable(false);
        window.setUndecorated(true);
        window.setSize(new Dimension(SizePanel.WINDOW_WIDTH, SizePanel.WINDOW_HEIGHT));
        window.setName("Snake");
        window.setLocationRelativeTo(null);
        window.add(mainPanel);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        singlePlayer.setExitListener(e -> openSinglePlayerMainMenu());
        multiplayer.setExitListener(e -> openMultiplayerMainMenu());

        addMainMenuListeners();
        addControlPanelListeners();
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
            window.add(mainPanel);
            window.setSize(new Dimension(mainPanel.getWidth(), mainPanel.getHeight()));
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
            window.add(mainPanel);
            window.setSize(new Dimension(mainPanel.getWidth(), mainPanel.getHeight()));
        } else {
            window.add(restartMultiplayerPanel);
            window.setSize(new Dimension(restartPanelSinglePlayer.getWidth(), restartPanelSinglePlayer.getHeight()));
        }
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void openAccountMenu() {
        window.remove(mainPanel);
        currentAccount = accountActions.getCurrentAccount();
        if (currentAccount.getSigned() || accountActions.getRememberMe()) {
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
        mainPanel.setStartListener(e -> {
            changePanel(mainPanel, singlePlayer);
            singlePlayer.start();
            singlePlayer.playSound(true);
            singlePlayer.requestFocus();
        });

        mainPanel.setMultiplayerListener(e -> {
            changePanel(mainPanel, multiplayer);
            multiplayer.start();
            multiplayer.playSound(true);
            multiplayer.requestFocus();
        });

        mainPanel.setExitListener(e -> accountActions.cleanAccount());
        mainPanel.setControlPanelListener(e -> {
            changePanel(mainPanel, tabWithControlButtons);
            window.repaint();
        });
        mainPanel.setAccountListener(e -> openAccountMenu());
    }

    public void addSignedPanelListeners() {
        signedPanel.setSaveListener(e -> accountActions.saveGame());

        signedPanel.setLoadListener(e -> accountActions.getAllInfoFromJson());

        signedPanel.setBackListener(e -> changePanel(signedPanel, mainPanel));

        signedPanel.setExitFromAccountListener(e -> {
            accountActions.cleanAccount();
            changePanel(signedPanel, mainPanel);
        });
    }

    public void addSignInPanelListeners() {
        signInPanel.setBackListener(e -> {
            changePanel(signInPanel, mainPanel);
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

    public void addControlPanelListeners() {
        tabWithControlButtons.setBackListener(e -> {
            changePanel(tabWithControlButtons, mainPanel);
            window.repaint();
        });
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
}
