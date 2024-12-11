import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import javazoom.jl.player.Player;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Screen extends JPanel implements KeyListener, ActionListener {
    private boolean play = false; // 게임 실행 상태
    private boolean gameOver = false; // 게임 종료 상태
    private boolean gameStarted = false; // 게임 시작 상태
    private boolean showStartMessage = false; // "Press Enter to Start" 메시지 표시 여부
    private int score = 0; // 점수
    private Timer timer;
    private int delay = 8;
    
    private LifeSystem lifeSystem;
    private int lives;
    private boolean isPaddleGrown = false; // 페달 크기 증가 상태


    private Paddle paddle;
    private Ball ball;
    private MapGenerator map;
    private LevelManager levelManager;
    
 // Music 객체 추가
    private Music music;

    public Screen(JFrame frame) {
    	lives = 3;
        paddle = new Paddle(310, 550, 100, 8);
        ball = new Ball(120, 350, 20, 2, -3);
        levelManager = new LevelManager();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        music = new Music();
//        music.playRandomSong();
        
    }

    private void loadLevel() {
        Level currentLevel = levelManager.getCurrentLevel();
        map = new MapGenerator(currentLevel.getBrickLayout());
        ball.setSpeed(currentLevel.getBallSpeedX(), currentLevel.getBallSpeedY());
    }
    
    private void handleBallOutOfBounds() {
        lives--; // 생명 감소
        if (lives <= 0) {
            play = false;
            gameOver = true; // 게임 종료
        } else {
            resetBallAndPaddle(); // 공과 패들 위치 초기화
        }
    }
    
    private void resetBallAndPaddle() {
        ball.reset();
        paddle.reset();
    }
    
    private void resetItemEffects() {
        isPaddleGrown = false;

        // 페달 크기를 기본 크기로 초기화
        paddle.resetSize();

        // 공 속도를 기본 속도로 초기화
        ball.setSpeed(2, -3); // 기본 속도 설정
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
        g.drawString("Lives: " + lives, 200, 30);
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

            // 공과 벽돌 또는 아이템 블록 충돌
            int blockType = map.hitBrick(ball); // 블록 유형 반환
            if (blockType == 1) {
                score += 5; // 일반 벽돌 점수 증가
            } else if (blockType == 2) {
                paddle.grow(); // 아이템 블록: 페달 크기 증가
            } else if (blockType == 3) {
                triggerBombEffect(ball); // 폭탄 블록: 폭발 효과
            }

            // 공이 바닥에 닿으면 게임 종료
            if (ball.getY() > 570) {
               handleBallOutOfBounds();
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
    
    private void triggerBombEffect(Ball ball) {
        int ballX = ball.getX();
        int ballY = ball.getY();
        int ballDiameter = ball.getDiameter();

        // 공의 중심 좌표를 계산
        int centerX = ballX + ballDiameter / 2;
        int centerY = ballY + ballDiameter / 2;

        // 벽돌 배열에서 폭탄 블록의 행(row)과 열(col)을 계산
        int bombRow = (centerY - 50) / map.brickHeight;
        int bombCol = (centerX - 80) / map.brickWidth;

        // 폭탄 블록 주변 3x3 범위를 정확히 탐색
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                int currentRow = bombRow + rowOffset;
                int currentCol = bombCol + colOffset;

                // 배열의 범위를 벗어나지 않는지 확인
                if (currentRow >= 0 && currentRow < map.map.length &&
                    currentCol >= 0 && currentCol < map.map[0].length) {
                    // 위쪽 3칸 포함 모든 3x3 블록 제거
                    map.map[currentRow][currentCol] = 0;
                }
            }
        }
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
                File file = new File("res/bear.wav");
    			playBearSound(file);
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                paddle.moveLeft();
                File file = new File("res/light-punch.wav");
    			playPunchSound(file);
            }
        }
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (gameOver) {
                resetGame(); // 게임 재시작
            } else {
                play = true; // 게임 시작
            }
        }
        
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N) {
            moveToNextLevel();
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
    
    private void moveToNextLevel() {
        if (levelManager.hasNextLevel()) {
            levelManager.moveToNextLevel();
            loadLevel();
            ball.reset(); // 공 초기화
            play = true; // 게임 시작 상태로 변경
        } else {
            play = false;
            gameOver = true; // 모든 레벨 완료
        }
    }
    
    private void playPunchSound(File file) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    private void playBearSound(File file) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    


    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}