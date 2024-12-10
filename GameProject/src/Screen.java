import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Screen extends JPanel implements KeyListener, ActionListener {
    private boolean play = false; // 게임 실행 상태
    private boolean gameOver = false; // 게임 종료 상태
    private boolean gameStarted = false; // 게임 시작 상태
    private boolean showStartMessage = false; // "Press Enter to Start" 메시지 표시 여부
    private int score = 0; // 점수
    private Timer timer;
    private int delay = 8;

    private Paddle paddle;
    private Ball ball;
    private MapGenerator map;
    private LevelManager levelManager;

    public Screen(JFrame frame) {
        paddle = new Paddle(310, 550, 100, 8);
        ball = new Ball(120, 350, 20, 2, -3);
        levelManager = new LevelManager();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
    }

    private void loadLevel() {
        Level currentLevel = levelManager.getCurrentLevel();
        map = new MapGenerator(currentLevel.getBrickLayout());
        ball.setSpeed(currentLevel.getBallSpeedX(), currentLevel.getBallSpeedY());
    }

    public void showPressEnterMessage() {
        // "Press Enter to Start" 메시지 표시
        showStartMessage = true;
        repaint();
    }



    public void paint(Graphics g) {
        super.paint(g);

        // 배경
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        // 점수와 레벨
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 560, 30);
        g.drawString("Level: " + (levelManager != null ? levelManager.getCurrentLevelIndex() : 1), 30, 30);

        // 벽돌
        if (map != null) {
            map.draw((Graphics2D) g);
        }

        // 패들
        paddle.draw(g);

        // 공
        ball.draw(g);

        // 게임 시작 메시지
        if (showStartMessage && !play && !gameOver) { // 게임 시작 메시지 조건
            g.setColor(Color.WHITE);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Press Enter to Start", 200, 300);
        }

        // 게임 종료 메시지
        if (gameOver) { // 게임 종료 메시지 조건
            g.setColor(Color.RED);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Game Over!", 260, 300);

            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 200, 350);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play) {
            ball.move();

            // 공과 패들 충돌
            if (ball.checkCollision(paddle)) {
                ball.bounceOffPaddle(paddle);
            }

            // 공과 벽돌 충돌
            if (map != null && map.hitBrick(ball)) {
                score += 5; // 점수 증가
            }

            // 공이 바닥에 닿으면 게임 종료
            if (ball.getY() > 570) {
                play = false;
                gameOver = true;
                showStartMessage = false; // "Press Enter to Start" 메시지 비활성화
            }

            // 모든 벽돌 제거 시 다음 레벨로 이동
            if (map != null && map.isAllBricksDestroyed()) {
                if (levelManager.hasNextLevel()) {
                    levelManager.moveToNextLevel();
                    loadLevel();
                } else {
                    play = false;
                    gameOver = true;
                    showStartMessage = false; // "Press Enter to Start" 메시지 비활성화
                }
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!gameStarted) {
                gameStarted = true;
                play = true;
                timer.start(); // 타이머 시작
                loadLevel();
                repaint();
            } else if (gameOver) {
                resetGame();
            } else {
                play = true;
            }
        }

        if (play) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                paddle.moveRight();
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                paddle.moveLeft();
            }
        }

        repaint();
    }

    private void resetGame() {
        score = 0;
        gameOver = false;
        levelManager = new LevelManager();
        loadLevel();
        ball.reset();
        play = true;
        showStartMessage = false; // "Press Enter to Start" 메시지 비활성화
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}