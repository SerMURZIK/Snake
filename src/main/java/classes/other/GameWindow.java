package classes.other;

import classes.accountClasses.AccountActions;
import classes.accountClasses.CurrentAccount;
import classes.menus.*;
import classes.menus.gamePanels.GamePanel;
import classes.menus.gamePanels.MultiplayerPanel;
import classes.menus.gamePanels.SinglePlayerPanel;
import classes.menus.restartPanels.RestartPanel;
import classes.menus.restartPanels.LoadingRestartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameWindow {
    private final JFrame window = new JFrame();

    private SinglePlayerPanel singlePlayer = new SinglePlayerPanel();
    private MultiplayerPanel multiplayer = new MultiplayerPanel();

    private final MainPanel mainPanel = new MainPanel();
    private final StartPanel startPanelPanel = new StartPanel();
    private final LoadingRestartPanel loadingRestartPanel = new LoadingRestartPanel();
    private final RestartPanel restartPanel = new RestartPanel();
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

        addSinglePlayerKeyListener();

        addMultiplayerKeyListener();

        window.setResizable(false);
        window.setUndecorated(true);
        window.setSize(new Dimension(startPanelPanel.getWidth(), startPanelPanel.getHeight()));
        window.setName("Snake");
        window.setLocationRelativeTo(null);
        window.add(startPanelPanel);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        startPanelPanel.setStartListener(e -> {
            changePanel(startPanelPanel, mainPanel);
            accountActions.getAllInfoFromJson();
        });

        addMainMenuListeners();
        addTeachControlPanelListeners();
        addSignedPanelListeners();
        addSignInPanelListeners();
        addSignUpPanelListeners();
        addRestartPanelListener();
    }

    private void addMultiplayerKeyListener() {
        multiplayer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ESCAPE) {
                    openGamePanel(multiplayer, restartPanel);
                }
            }
        });
    }

    private void addSinglePlayerKeyListener() {
        singlePlayer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ESCAPE) {
                    openGamePanel(singlePlayer, loadingRestartPanel);
                }
            }
        });
    }

    private void setOpeningListeners() {
        singlePlayer.setExitListener(e -> openGamePanel(singlePlayer, loadingRestartPanel));
        multiplayer.setExitListener(e -> openGamePanel(multiplayer, restartPanel));
        addSinglePlayerKeyListener();
        addMultiplayerKeyListener();
    }

    public void openGamePanel(GamePanel gamePanel, RestartPanel restartPanel) {
        gamePanel.stop();
        gamePanel.playSound(false);
        window.remove(gamePanel);
        if (gamePanel.isAlive()) {
            window.add(mainPanel);
            window.setSize(new Dimension(mainPanel.getWidth(), mainPanel.getHeight()));
        } else {
            window.add(restartPanel);
            window.setSize(new Dimension(restartPanel.getWidth(), restartPanel.getHeight()));
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
        mainPanel.setTeachingPanelListener(e -> changePanel(mainPanel, tabWithControlButtons));
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

    public void addTeachControlPanelListeners() {
        tabWithControlButtons.setBackListener(e -> changePanel(tabWithControlButtons, mainPanel));
    }

    public void addRestartPanelListener() {
        loadingRestartPanel.setRestartListener(e -> {
            singlePlayer = new SinglePlayerPanel();
            changePanel(loadingRestartPanel, singlePlayer);
            setOpeningListeners();
            singlePlayer.continueGame();
            singlePlayer.requestFocus();
        });

        loadingRestartPanel.setLoadListener(e -> {
            accountActions.getAllInfoFromJson();
            changePanel(loadingRestartPanel, singlePlayer);
            singlePlayer.continueGame();
            singlePlayer.requestFocus();
        });

        restartPanel.setRestartListener(e -> {
            multiplayer = new MultiplayerPanel();
            changePanel(restartPanel, multiplayer);
            setOpeningListeners();
            singlePlayer.continueGame();
            multiplayer.requestFocus();
        });

        setOpeningListeners();
    }
}
