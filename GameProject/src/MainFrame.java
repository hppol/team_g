import javax.swing.JFrame;

public class MainFrame extends JFrame {
    private Screen screen;

    public MainFrame() {
        setBounds(10, 10, 700, 600);
        setTitle("Brick Breaker");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        screen = new Screen(this); // 현재 JFrame 객체(this)를 전달
        add(screen);

        screen.requestFocusInWindow(); // 포커스 요청

        setVisible(true);
    }
}
