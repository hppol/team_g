import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Title extends JPanel {
    private Screen screen;
    private JFrame frame;

    public Title(Screen screen, JFrame frame) {
        this.screen = screen;
        this.frame = frame;

        setLayout(new BorderLayout());

        // 왼쪽 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.BLACK);

        // Game Start 버튼
        JButton gameStartButton = new JButton("Game Start");
        styleButton(gameStartButton);
        gameStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.startGameFromTitle();
            }
        });

        // Help 버튼
        JButton helpButton = new JButton("Help");
        styleButton(helpButton);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        frame,
                        "이 게임은 벽돌 깨기 게임이며 막대기의 각 부분에 따라 공이 튕기는 "
                        + "각도가 달라집니다.\n"
                        + "또한 블럭을 깨서 아이템을 구하고 재화를 이용해 배경 등을 변경할 수 있습니다.",
                        "Help",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        // Item 버튼
        JButton itemButton = new JButton("Item");
        styleButton(itemButton);
        itemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        frame,
                        "This is a placeholder for item functionality.",
                        "Item",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        // 버튼 패널에 버튼 추가
        buttonPanel.add(gameStartButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(helpButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(itemButton);

        add(buttonPanel, BorderLayout.WEST);

        // 타이틀 메시지 중앙에 추가
        JLabel titleLabel = new JLabel("Brick Breaker", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Serif", Font.BOLD, 18));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
