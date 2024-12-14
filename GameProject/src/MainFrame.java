import javax.swing.*;

public class MainFrame extends JFrame {
    private Screen screen;
    private Leaderboard leaderboard;
    private Title titlePanel;

    public MainFrame() {
    	
    	leaderboard = new Leaderboard();
        Menu menu = new Menu(leaderboard);
        setJMenuBar(menu.getMenuBar());
        
        Screen screen = new Screen(this, leaderboard); // Screen에 리더보드 전달
        add(screen);
    	
        setBounds(10, 10, 700, 630);
        setTitle("Brick Breaker");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title 화면 생성 및 추가
        titlePanel = new Title(this); // MainFrame 객체 전달
        add(titlePanel);
        
        

        setVisible(true); // JFrame 표시
    }

    public void startGame() {
        remove(titlePanel); // Title 화면 제거

        // Screen 생성 및 추가
        screen = new Screen(this, leaderboard);
        add(screen);

        validate();
        repaint();
        screen.requestFocusInWindow(); // 포커스 요청
        screen.startCountdown(); // 타이머 시작
    }

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public Title getTitlePanel() {
		return titlePanel;
	}

	public void setTitlePanel(Title titlePanel) {
		this.titlePanel = titlePanel;
	}
	
}
