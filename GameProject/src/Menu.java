import javax.swing.*;
import java.awt.event.*;

public class Menu {
    private JMenuBar menuBar;
    private Leaderboard leaderboard;
    private ThemeManager themeManager;
    private Music music;
    private MainFrame mainFrame;

    public Menu(Leaderboard leaderboard, ThemeManager themeManager, Music music, MainFrame mainFrame) {
        if (leaderboard == null) {
            throw new IllegalArgumentException("Leaderboard cannot be null");
        }
        
        if (music == null) {
            throw new IllegalArgumentException("Music object cannot be null");
        }
        
        this.leaderboard = leaderboard;
        this.themeManager = themeManager;
        this.music = music;
        this.mainFrame = mainFrame;

        menuBar = new JMenuBar();

        // 주요 메뉴
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");
        JMenu settingsMenu = new JMenu("Settings");
        JMenu soundMenu = new JMenu("Sound");
        JMenu themeMenu = new JMenu("Themes");

        // 단축키 설정
        fileMenu.setMnemonic(KeyEvent.VK_F);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        settingsMenu.setMnemonic(KeyEvent.VK_S);

        // File 메뉴
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem pauseGameItem = new JMenuItem("Pause Game");
        JMenuItem resumeGameItem = new JMenuItem("Resume Game");
        JMenuItem goToTItleItem = new JMenuItem("Go to Title");
        JMenuItem exitItem = new JMenuItem("Exit");

        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        newGameItem.setToolTipText("Start a new game.");
        pauseGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        pauseGameItem.setToolTipText("Pause the game.");
        resumeGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        resumeGameItem.setToolTipText("Resume the paused game.");
        goToTItleItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
        goToTItleItem.setToolTipText("Return to the title screen.");
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        exitItem.setToolTipText("Exit the application.");

        newGameItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "New Game started!"));
        pauseGameItem.addActionListener(e -> {
            if (mainFrame.getScreen() != null) {
                mainFrame.getScreen().pauseGame(); // 게임 일시정지
                JOptionPane.showMessageDialog(null, "Game paused!");
            }
        });

        resumeGameItem.addActionListener(e -> {
            // "Resume Game" 메시지를 띄우고, OK 버튼 클릭 후 3초 딜레이
        	int result = JOptionPane.showConfirmDialog(
        		    null,
        		    "Game will resume in 3 seconds...",
        		    "Resuming Game",
        		    JOptionPane.OK_CANCEL_OPTION,
        		    JOptionPane.INFORMATION_MESSAGE
        		);

        		if (result == JOptionPane.OK_OPTION) {
        		    // 메시지 창이 닫힌 후 3초 타이머 시작
        		    Timer delayTimer = new Timer(3000, evt -> {
        		        mainFrame.resumeGame(); // 3초 후 게임 재개
        		    });
        		    delayTimer.setRepeats(false); // 한 번만 실행
        		    delayTimer.start();
        		}
        });
        
        goToTItleItem.addActionListener(e -> {
            mainFrame.showTitleScreen(); // 타이틀 화면으로 이동
        });
        
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newGameItem);
        fileMenu.add(pauseGameItem);
        fileMenu.add(resumeGameItem);
        fileMenu.add(goToTItleItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Help 메뉴
        JMenuItem aboutItem = new JMenuItem("About");
        JMenuItem leaderboardItem = new JMenuItem("Leaderboard");
        JMenuItem howToPlayItem = new JMenuItem("How to Play");

        aboutItem.setToolTipText("Learn more about this application.");
        leaderboardItem.setToolTipText("View the leaderboard.");
        howToPlayItem.setToolTipText("Learn how to play the game.");

        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Brick Breaker Game - Version 1.0"));
        leaderboardItem.addActionListener(e -> leaderboard.displayLeaderboard(null));
        howToPlayItem.addActionListener(e -> JOptionPane.showMessageDialog(null, 
        		"Game Objective\r\n"
        				+ "Players aim to bounce the ball and break all the bricks. The key is to achieve a high score within limited opportunities.\r\n"
        				+ "\r\n"
        				+ "How to Play\r\n"
        				+ "\r\n"
        				+ "Control the paddle to reflect the ball and hit the bricks.\r\n"
        				+ "Earn points as bricks are destroyed, and prevent the ball from falling below the screen.\r\n"
        				+ "Some special bricks drop items that can help you gain advantages.\r\n"
        				+ "Features and Strategies\r\n"
        				+ "\r\n"
        				+ "Bricks come in various types, each with different durability. Some require multiple hits to break.\r\n"
        				+ "The reflection angle of the ball depends on where it hits the paddle, requiring strategic play.\r\n"
        				+ "Experience various effects, such as extending the paddle length or adjusting the ball's speed, through items.\r\n"
        				+ "Challenges\r\n"
        				+ "The difficulty increases progressively, demanding quick decisions and precise control to achieve high scores.\r\n"
        				+ "\r\n"
        				+ ""));


        helpMenu.add(leaderboardItem);
        helpMenu.add(howToPlayItem);
        helpMenu.add(aboutItem);

        // Settings 메뉴
        JCheckBoxMenuItem soundToggleItem = new JCheckBoxMenuItem("Mute Sound");
        JCheckBoxMenuItem fullscreenToggleItem = new JCheckBoxMenuItem("Fullscreen Mode");

        soundToggleItem.setToolTipText("Mute or unmute the game sound.");
        fullscreenToggleItem.setToolTipText("Toggle fullscreen mode.");

        soundToggleItem.addActionListener(e -> {
            if (soundToggleItem.isSelected()) {
                JOptionPane.showMessageDialog(null, "Sound muted!");
            } else {
                JOptionPane.showMessageDialog(null, "Sound unmuted!");
            }
        });

        fullscreenToggleItem.addActionListener(e -> {
            if (fullscreenToggleItem.isSelected()) {
                JOptionPane.showMessageDialog(null, "Fullscreen mode enabled!");
            } else {
                JOptionPane.showMessageDialog(null, "Fullscreen mode disabled!");
            }
        });

        settingsMenu.add(soundToggleItem);
        settingsMenu.add(fullscreenToggleItem);

        // Themes 메뉴
        for (String themeName : themeManager.getThemes().keySet()) {
            JMenuItem themeItem = new JMenuItem(themeName);
            themeItem.addActionListener((ActionEvent e) -> themeManager.setTheme(themeName));
            themeMenu.add(themeItem);
        }

        settingsMenu.add(themeMenu);

        // Sound 메뉴
        JMenuItem increaseVolumeItem = new JMenuItem("Increase Volume");
        JMenuItem decreaseVolumeItem = new JMenuItem("Decrease Volume");

        increaseVolumeItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Volume increased!"));
        decreaseVolumeItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Volume decreased!"));

        soundMenu.add(increaseVolumeItem);
        soundMenu.add(decreaseVolumeItem);
        
        // 볼륨 증가
        increaseVolumeItem.addActionListener(e -> music.increaseVolume());

        // 볼륨 감소
        decreaseVolumeItem.addActionListener(e -> music.decreaseVolume());

        // 음소거 토글
        soundToggleItem.addActionListener(e -> music.stopMusic());

        // 메뉴바에 추가
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        menuBar.add(settingsMenu);
        menuBar.add(soundMenu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }
}
