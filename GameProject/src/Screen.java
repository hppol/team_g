import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class Screen extends JPanel implements KeyListener, ActionListener {
    private boolean play = false; // 게임 실행 상태
    private boolean gameOver = false; // 게임 종료 상태
    private int score = 0; // 점수

    private Timer timer;
    private int delay = 8;

    private Paddle paddle;
    private Ball ball;
    private MapGenerator map;
    private LevelManager levelManager;

    public Screen() {
        paddle = new Paddle(310, 550, 100, 8);
        ball = new Ball(120, 350, 20, 2, -3);
        levelManager = new LevelManager();
        loadLevel();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();
    }

    private void loadLevel() {
        Level currentLevel = levelManager.getCurrentLevel();
        map = new MapGenerator(currentLevel.getBrickLayout());
        ball.setSpeed(currentLevel.getBallSpeedX(), currentLevel.getBallSpeedY());
    }

    public void paint(Graphics g) {
        // 배경
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // 점수와 레벨
        g.setColor(Color.white);
        g.setFont(new Font("Serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 560, 30);
        g.drawString("Level: " + levelManager.getCurrentLevelIndex(), 30, 30);

        // 벽돌
        map.draw((Graphics2D) g);

        // 패들
        paddle.draw(g);

        // 공
        ball.draw(g);

        // 게임 시작 메시지
        if (!play && !gameOver) {
            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Press Enter to Start", 200, 300);
        }

        // 게임 종료 메시지
        if (gameOver) {
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Game Over!", 260, 300);

            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 200, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play) {
            ball.move();

            // 공과 패들 충돌
            if (ball.checkCollision(paddle)) {
                ball.bounceOffPaddle(paddle); // 패들 위치에 따라 반사 각도 변경
            }

            // 공과 벽돌 충돌
            if (map.hitBrick(ball)) { // Ball 객체를 전달
                score += 5;           // 점수 증가
            }

            // 공이 바닥에 닿으면 게임 종료
            if (ball.getY() > 570) {
                play = false;
                gameOver = true;
            }

            // 모든 벽돌 제거 시 다음 레벨로 이동
            if (map.isAllBricksDestroyed()) {
                if (levelManager.hasNextLevel()) {
                    levelManager.moveToNextLevel();
                    loadLevel();
                } else {
                    play = false;
                    gameOver = true;
                }
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddle.moveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            paddle.moveLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (gameOver) {
                resetGame(); // 게임 재시작
            } else {
                play = true; // 게임 시작
            }
        }

        repaint();
    }

    private void resetGame() {
        score = 0;
        gameOver = false;
        levelManager = new LevelManager(); // 레벨 초기화
        loadLevel();
        ball.reset(); // 공 위치 초기화
        play = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}