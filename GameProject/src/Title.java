import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Title extends JPanel {
    private MainFrame mainFrame; // MainFrame 참조
    private BufferedImage brickImage; // 벽돌 이미지
    private ThemeManager themeManager;

    public Title(MainFrame mainFrame, ThemeManager themeManager) {
        this.mainFrame = mainFrame;
        this.themeManager = themeManager;
        
        setLayout(new BorderLayout());
        setBackground(themeManager.getCurrentTheme().getBackgroundColor());

        // 이미지 로드
        try {
            brickImage = ImageIO.read(new File("res/brick.jpg")); // 벽돌 이미지 로드
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(themeManager.getCurrentTheme().getBackgroundColor());

        // Help 버튼
        JButton helpButton = new JButton("Help");
        styleButton(helpButton);
        helpButton.addActionListener(e -> JOptionPane.showMessageDialog(
                mainFrame,
                "게임 목적\r\n"
                + "플레이어는 공을 튕겨 벽돌을 모두 제거하는 것을 목표로 합니다. 제한된 기회 내에 높은 점수를 얻는 것이 핵심입니다.\r\n"
                + "\r\n"
                + "플레이 방법\r\n"
                + "\r\n"
                + "막대를 조작해 공을 반사시키고 벽돌을 맞춥니다.\r\n"
                + "벽돌이 제거되면 점수를 획득하며, 공이 화면 아래로 떨어지지 않도록 막아야 합니다.\r\n"
                + "특정 벽돌은 아이템을 떨어뜨리며, 아이템은 플레이를 유리하게 도와줍니다.\r\n"
                + "특징 및 전략\r\n"
                + "\r\n"
                + "벽돌의 종류에 따라 내구도가 다릅니다. 특정 벽돌은 여러 번 맞춰야 제거됩니다.\r\n"
                + "막대기의 특정 위치에 공이 닿으면 공의 반사 각도가 달라져 전략적인 플레이가 필요합니다.\r\n"
                + "아이템으로 막대 길이를 늘리거나 공의 속도를 조절하는 등 다양한 효과를 경험할 수 있습니다.\r\n"
                + "도전 요소\r\n"
                + "난이도가 점차 상승하며, 높은 스코어를 위해 빠른 판단과 정교한 조작이 요구됩니다.\r\n"
                + "\r\n"
                + "",
                "Help",
                JOptionPane.INFORMATION_MESSAGE
        ));

        // 버튼 추가
        buttonPanel.add(helpButton);
        buttonPanel.add(Box.createVerticalStrut(20));

        add(buttonPanel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Brick Breaker", JLabel.LEFT); // LEFT로 설정하여 왼쪽 정렬
        titleLabel.setFont(new Font("Serif", Font.BOLD, 70)); // 글자 크기 키우기
        titleLabel.setForeground(Color.WHITE);

        // 왼쪽 여백을 주기 위해 패딩 추가 (위쪽, 왼쪽, 아래쪽, 오른쪽)
        titleLabel.setBorder(BorderFactory.createEmptyBorder(200, 90, 0, 50)); // 왼쪽으로 50픽셀 이동

        add(titleLabel, BorderLayout.CENTER);

        // Game Start 버튼만 추가
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.BLACK);

        JButton gameStartButton = new JButton("Game Start");

        // 글자 크기 설정
        gameStartButton.setFont(new Font("Serif", Font.BOLD, 40)); // 글자 크기 70으로 설정
        gameStartButton.setForeground(Color.WHITE); // 텍스트 색상 설정
        gameStartButton.setBackground(Color.BLACK); // 버튼 배경색 설정
        gameStartButton.setFocusPainted(false); // 버튼 포커스 제거
        gameStartButton.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 버튼 테두리 설정

        // 버튼 위치 및 여백 조정
        formPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 50, 0)); // 위, 좌, 아래, 우 여백 설정
        gameStartButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 버튼 중앙 정렬

        gameStartButton.addActionListener(e -> mainFrame.startGame()); // MainFrame의 startGame 호출

        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(gameStartButton);

        add(formPanel, BorderLayout.SOUTH);
    }
    
    public void updateTheme() {
        setBackground(themeManager.getCurrentTheme().getBackgroundColor());
        repaint(); // 화면을 다시 그려 테마를 반영
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

        // 배경 그리기

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        // 벽돌 이미지 여러 개 그리기
        if (brickImage != null) {
            int brickWidth = 100; // 벽돌의 너비
            int brickHeight = 50; // 벽돌의 높이
            int y = 100; // y 좌표 (100px 아래)

            // 벽돌의 x 좌표들
            int[] xPositions = {
                (getWidth() - brickWidth) / 2,  // 중앙
                (getWidth() - brickWidth) / 2 - brickWidth - 10,  // 왼쪽
                (getWidth() - brickWidth) / 2 + brickWidth + 10,  // 오른쪽
                (getWidth() - brickWidth) / 2 - brickWidth * 2 - 20,  // 왼쪽 2번째
                (getWidth() - brickWidth) / 2 + brickWidth * 2 + 20  // 오른쪽 2번째
            };

            // y 좌표들
            int[] yPositions = {
                y,  // 중앙
                y - brickHeight - 10,  // 위쪽
                y + brickHeight + 10   // 아래쪽
            };

            // x, y 좌표들을 반복하여 각 위치에 벽돌을 그리기
            for (int x : xPositions) {
                for (int yPos : yPositions) {
                    g.drawImage(brickImage, x, yPos, brickWidth, brickHeight, this);
                }
            }
        }
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    

    public BufferedImage getBrickImage() {
        return brickImage;
    }

    public void setBrickImage(BufferedImage brickImage) {
        this.brickImage = brickImage;
    }

	public ThemeManager getThemeManager() {
		return themeManager;
	}

	public void setThemeManager(ThemeManager themeManager) {
		this.themeManager = themeManager;
	}
}
