import javax.swing.*;

public class MainFrame extends JFrame {
	private Screen screen;
    private Leaderboard leaderboard;
    private Title titlePanel;
    private ThemeManager themeManager;
    private MainFrame mainframe;
    
    public MainFrame() {
        // ThemeManager와 Leaderboard 초기화
        themeManager = new ThemeManager();
        leaderboard = new Leaderboard();

        // 메뉴바 설정
        Menu menu = new Menu(leaderboard, themeManager);
        setJMenuBar(menu.getMenuBar());

        // Title 화면 생성 및 추가
        titlePanel = new Title(this, themeManager); // MainFrame 객체 전달
        add(titlePanel);

        // JFrame 설정
        setBounds(10, 10, 700, 630);
        setTitle("Brick Breaker");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true); // JFrame 표시
    }
    

    public void startGame() {
        remove(titlePanel); // Title 화면 제거


        // Screen 생성 및 추가
        screen = new Screen(this, leaderboard, themeManager);
        add(screen);
        
        screen.showPressEnterMessage();
        
//        screen.startCountdown();

        validate();
        repaint();
        screen.requestFocusInWindow(); // 포커스 요청
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
