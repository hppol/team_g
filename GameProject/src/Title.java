import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Title extends JPanel {
    private JButton startButton; // 게임 시작 버튼
    private Screen screen; // Screen 객체 참조

    public Title(Screen screen) {
        this.screen = screen;

        setLayout(new BorderLayout());

        // 타이틀 메시지
        JLabel titleLabel = new JLabel("Brick Breaker", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(Color.BLUE);
        add(titleLabel, BorderLayout.CENTER);

        // 시작 버튼 생성
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Serif", Font.BOLD, 24));
        add(startButton, BorderLayout.SOUTH);

        // 버튼 이벤트 핸들러
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.startGameFromTitle(); // Screen에서 게임 시작
            }
        });
    }
}
