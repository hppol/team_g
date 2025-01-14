import javax.swing.*;

public class MainFrame extends JFrame {
	private Screen screen;
    private Leaderboard leaderboard;
    private Title titlePanel;
    private ThemeManager themeManager;
    private MainFrame mainframe;
    private Music music;
    
    public MainFrame() {
        // ThemeManager와 Leaderboard 초기화
        themeManager = new ThemeManager();
        leaderboard = new Leaderboard();
        music = new Music();

        // 메뉴바 설정
        Menu menu = new Menu(leaderboard, themeManager, music, this);
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
        screen = new Screen(this, leaderboard, themeManager, music);
        add(screen);
        
        screen.showPressEnterMessage();
        
//        screen.startCountdown();

        validate();
        repaint();
        screen.requestFocusInWindow(); // 포커스 요청
    }
    
    public void pauseGame() {
        if (screen != null) {
            screen.setPlay(false); // Screen에서 play 상태를 멈춤
        }
    }

    public void resumeGame() {
        if (screen != null) {
            screen.setPlay(true); // Screen에서 play 상태를 재개
        }
    }
    
    public void showTitleScreen() {
        if (screen != null) {
            remove(screen); // 게임 화면 제거
            screen = null;  // 참조 해제
        }
        titlePanel = new Title(this, themeManager); // 타이틀 화면 생성
        add(titlePanel);
        validate(); // 컴포넌트 갱신
        repaint();  // 화면 다시 그리기
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


	public Leaderboard getLeaderboard() {
		return leaderboard;
	}


	public void setLeaderboard(Leaderboard leaderboard) {
		this.leaderboard = leaderboard;
	}


	public ThemeManager getThemeManager() {
		return themeManager;
	}


	public void setThemeManager(ThemeManager themeManager) {
		this.themeManager = themeManager;
	}


	public MainFrame getMainframe() {
		return mainframe;
	}


	public void setMainframe(MainFrame mainframe) {
		this.mainframe = mainframe;
	}
	
}
