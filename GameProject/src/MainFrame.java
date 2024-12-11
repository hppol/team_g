import javax.swing.*;

public class MainFrame extends JFrame {
    private Screen screen;
    private Title titlePanel;

    public MainFrame() {
        setBounds(10, 10, 700, 630);
        setTitle("Brick Breaker");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title 화면 생성 및 추가
        titlePanel = new Title(this); // MainFrame 객체 전달
        add(titlePanel);
        
        Menu menu = new Menu();
        setJMenuBar(menu.getMenuBar());

        setVisible(true); // JFrame 표시
    }

    public void startGame() {
        remove(titlePanel); // Title 화면 제거

        // Screen 생성 및 추가
        screen = new Screen(this);
        add(screen);

        validate();
        repaint();
        screen.requestFocusInWindow(); // 포커스 요청
    }
}
