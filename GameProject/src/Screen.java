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
    private Leaderboard leaderboard;
    private int delay = 8;
    private int timeLeft = 60; // 타이머 시작 시간 (초 단위)
    private Timer countdownTimer;
    
    private LifeSystem lifeSystem;
    private int lives;
    private boolean isPaddleGrown = false; // 페달 크기 증가 상태


    private Paddle paddle;
    private Ball ball;
    private MapGenerator map;
    private LevelManager levelManager;
    private ThemeManager themeManager;
    
 // Music 객체 추가
    private Music music;

    public Screen(JFrame frame, Leaderboard leaderboard, ThemeManager themeManager) {
    	this.themeManager = themeManager;
    	this.leaderboard = leaderboard;
    	lives = 3;
        paddle = new Paddle(310, 550, 100, 8);
        ball = new Ball(350, 520, 20, 2, -3);
        levelManager = new LevelManager();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        music = new Music();
        music.playRandomSong();
        
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
    
    public void startCountdown() {
        countdownTimer = new Timer(1000, e -> { // 1초마다 실행
            if (timeLeft > 0) {
                timeLeft--; // 남은 시간 감소
            } else {
                play = false;
                gameOver = true; // 시간이 끝나면 게임 종료
                countdownTimer.stop(); // 타이머 중지
            }
            repaint(); // 화면 갱신
        });
        countdownTimer.start(); // 타이머 시작
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
        
        Theme currentTheme = themeManager.getCurrentTheme();

        // 배경
        g.setColor(currentTheme.getBackgroundColor());
        g.fillRect(1, 1, 692, 592);

        // 점수와 레벨
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.BOLD, 25));
        g.drawString("Lives: " + lives, 200, 30);
        g.drawString("Score: " + score, 560, 30);
        g.drawString("Level: " + (levelManager != null ? levelManager.getCurrentLevelIndex() : 1), 30, 30);
        g.drawString("Time Left: " + timeLeft + "s", 300, 30); // 남은 시간 표시

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
            g.setFont(new Font("Serif", Font.BOLD, 50));
            g.drawString("Game Over!", 200, 300);

            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart", 190, 350);
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
                File file = new File("res/break.wav");
    			playBreakSound(file);
                

                
            } else if (blockType == 2) {
                paddle.grow(); // 아이템 블록: 페달 크기 증가
                
            } else if (blockType == 3) {
                triggerBombEffect(ball); // 폭탄 블록: 폭발 효과
                File file = new File("res/bomb.wav");
    			playBombSound(file);
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

        // 공의 중심 좌표 계산
        int centerX = ballX + ballDiameter / 2;
        int centerY = ballY + ballDiameter / 2;

        // 벽돌 배열에서 폭탄 블록의 행(row)과 열(col)을 계산
        int bombRow = (centerY - 50) / map.brickHeight; // Y 오프셋: 50
        int bombCol = (centerX - 80) / map.brickWidth;  // X 오프셋: 80

        // 디버깅 출력 (폭탄 블록의 중심 좌표 확인)
        System.out.println("Bomb Center: Row = " + bombRow + ", Col = " + bombCol);

        // 3x3 범위의 정확한 좌표 지정
        int[][] positions = {
            {bombRow - 2, bombCol - 1}, {bombRow-2, bombCol }, {bombRow -2, bombCol +1}, // 위쪽
            {bombRow-1, bombCol-1},     {bombRow -1, bombCol},     {bombRow -1, bombCol+1},     // 중간
            {bombRow , bombCol - 1}, {bombRow, bombCol }, {bombRow , bombCol + 1}  // 아래쪽
        };

        // 각 위치의 벽돌 제거
        for (int[] pos : positions) {
            int currentRow = pos[0];
            int currentCol = pos[1];

            // 배열 경계 조건 확인
            if (currentRow >= 0 && currentRow < map.map.length &&
                currentCol >= 0 && currentCol < map.map[0].length) {

                // 벽돌 제거
                if (map.map[currentRow][currentCol] > 0) { // 벽돌이 있을 경우만 제거
                    map.map[currentRow][currentCol] = 0;
//                    System.out.println("Removing Brick at: Row = " + currentRow + ", Col = " + currentCol);
                }
            }
        }

        // 화면 갱신
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
                File file = new File("res/light-punch.wav");
    			playPunchSound(file);
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
        if (gameOver) {
            leaderboard.addScore(score); // 점수 추가
        }
        score = 0;
        gameOver = false;
        timeLeft = 60; // 타이머 초기화
        levelManager = new LevelManager();
        loadLevel();
        ball.reset();
        play = true;
        showStartMessage = false;

        if (countdownTimer != null) {
            countdownTimer.stop(); // 기존 타이머 중지
        }
        startCountdown(); // 새로운 타이머 시작
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
    
    private void playBombSound(File file) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    private void playBreakSound(File file) {
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