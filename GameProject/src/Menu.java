import javax.swing.*;
import java.awt.event.*;

public class Menu {
    private JMenuBar menuBar;

    public Menu() {
        // JMenuBar 생성
        menuBar = new JMenuBar();

        // 메뉴 생성
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");

        // 메뉴에 단축키 설정
        fileMenu.setMnemonic(KeyEvent.VK_F); // Alt + F로 File 메뉴 열기
        helpMenu.setMnemonic(KeyEvent.VK_H); // Alt + H로 Help 메뉴 열기

        // 메뉴 아이템 생성
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem aboutItem = new JMenuItem("About");

        // 메뉴 아이템에 단축키와 툴팁 설정
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
        		InputEvent.CTRL_DOWN_MASK)); // Ctrl + N
        newGameItem.setToolTipText("Start a new game.");
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
        		InputEvent.CTRL_DOWN_MASK)); // Ctrl + E
        exitItem.setToolTipText("Exit the application.");
        aboutItem.setToolTipText("Learn more about this application.");

        // 이벤트 리스너 추가
        newGameItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "New Game started!"));
        exitItem.addActionListener(e -> System.exit(0));
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Brick Breaker Game"));

        // 메뉴에 아이템 추가
        fileMenu.add(newGameItem);
        fileMenu.addSeparator(); // 구분선 추가
        fileMenu.add(exitItem);
        helpMenu.add(aboutItem);

        // 메뉴바에 메뉴 추가
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
    }

    // JMenuBar 반환 메서드
    public JMenuBar getMenuBar() {
        return menuBar;
    }

	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}
}
