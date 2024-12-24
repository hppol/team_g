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
    private AchievementManager achievementManager; // 업적 관리
    private GameState gameState; // 게임 상태 관리
    
    private LifeSystem lifeSystem;
    private int lives;
    private boolean isPaddleGrown = false; // 페달 크기 증가 상태
    private Boss boss;


    private Paddle paddle;
    private Ball ball;
    private MapGenerator map;
    private LevelManager levelManager;
    private ThemeManager themeManager;
    
 // Music 객체 추가
    private Music music;

    public Screen(JFrame frame, Leaderboard leaderboard, ThemeManager themeManager, Music music) {
    	this.themeManager = themeManager;
    	this.leaderboard = leaderboard;
    	this.music = music;
        this.gameState = new GameState(3); // 초기 생명값 설정
    	this.achievementManager = new AchievementManager(); // 업적 관리 객체 초기화
    	lives = 3;
        paddle = new Paddle(310, 550, 100, 8);
        ball = new Ball(350, 520, 20, 0, 0);
        levelManager = new LevelManager();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        music = new Music();
        music.playRandomSong();
        
//        startCountdown();
        
    }
    

    private void initializeLevel() {
        Level currentLevel = levelManager.getCurrentLevel(); // 현재 레벨 가져오기
        map = new MapGenerator(currentLevel.getBrickLayout()); // 벽돌 배열 로드
        ball.setSpeed(currentLevel.getBallSpeedX(), currentLevel.getBallSpeedY()); // 공의 속도 설정
        boss = currentLevel.getBoss(); // 보스 정보 가져오기
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
    
    private void handleBallOutOfBounds() {
        lives--; // 생명 감소
        play = false;

        // 생명이 다했으면 게임 종료
        if (lives <= 0) {
            play = false; // 게임 중지
            gameOver = true; // 게임 오버 처리
            timer.stop();
            if(countdownTimer != null) {
            	countdownTimer.stop();
            }
            lives = 3; // 생명 리셋
        } else {
            // 공과 패들 위치 초기화
            resetBallAndPaddle();
        }
    }

    // 공과 패들의 위치를 초기화하는 메서드
    private void resetBallAndPaddle() {
        // 공을 패들의 중앙 위치로 설정
        ball.setX(paddle.getX() + (paddle.getWidth() / 2) - (ball.getDiameter() / 2));
        ball.setY(paddle.getY() - ball.getDiameter()); // 공을 패들 바로 위에 위치하도록 설정

        // 공의 속도를 설정 (필요에 따라 수정 가능)
        ball.setSpeed(2, -3); // 예시로 X속도는 2, Y속도는 -3으로 설정
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
    
    private void loadLevel() {
        Level currentLevel = levelManager.getCurrentLevel();

        // 벽돌 배열이 있을 경우에만 MapGenerator 초기화
        if (currentLevel.getBrickLayout() != null && currentLevel.getBrickLayout().length > 0) {
            map = new MapGenerator(currentLevel.getBrickLayout());
        } else {
            map = null; // 벽돌이 없는 경우 null 처리
        }

        ball.setSpeed(currentLevel.getBallSpeedX(), currentLevel.getBallSpeedY());
        boss = currentLevel.getBoss(); // 보스 정보 가져오기
        
        timeLeft = 60;

        // 공 초기화
        resetBallAndPaddle();
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
        
        if (boss != null) {
            boss.draw(g);
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
    
    private void applyDebuffToPaddle() {
        // 예: 패들의 이동 속도를 느리게 하기
        paddle.setWidth(paddle.getWidth() - 30); // 패들의 크기를 줄임
        if (paddle.getWidth() < 50) { // 최소 크기 제한
            paddle.setWidth(50);
        }

        // 일정 시간 후 원래 상태로 복원
        Timer restoreTimer = new Timer(5000, evt -> paddle.resetSize()); // 5초 후 복원
        restoreTimer.setRepeats(false);
        restoreTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!play || gameOver) {
        	if(gameOver){
        		timer.stop();
        	}
        	return; // 게임이 실행 중이 아니거나 종료 상태면 반환
        }

        // 보스 로직 처리
        handleBossLogic();

        // 공과 패들의 충돌 처리
        if (ball.checkCollision(paddle)) {
            ball.bounceOffPaddle(paddle);
        }

        // 벽돌과 공의 충돌 처리
        handleBrickCollision();

        // 공이 바닥에 닿은 경우 처리
        if (ball.getY() > 570) {
            handleBallOutOfBounds();
        }

        // 레벨 완료 상태 확인
        checkLevelCompletion();

        // 공 이동 및 화면 갱신
        ball.move();
        repaint();
    }
    
    private void checkLevelCompletion() {
        if (map != null && map.isAllBricksDestroyed() && boss == null) {
            if (levelManager.hasNextLevel()) {
                levelManager.moveToNextLevel();
                loadLevel();
            } else {
                play = false;
                gameOver = true;
            }
        }
    }
    
    private void handleBrickCollision() {
        if (map == null) {
            return; // 벽돌이 없는 경우 처리하지 않음
        }

        int blockType = map.hitBrick(ball);
        if (blockType > 0) {
            gameState.incrementBricksDestroyed();
            checkAchievements();
        switch (blockType) {
            case 1: // 일반 벽돌
                score += 5;
                playBreakSound(new File("res/break.wav"));
                break;
            case 2: // 아이템 블록
                paddle.grow();
                break;
            case 3: // 폭탄 블록
                triggerBombEffect(ball);
                score += 15;
                playBombSound(new File("res/bomb.wav"));
                break;
            case 4: // 생명 추가 블록
                lives++; // 생명 1 추가
                playBreakSound(new File("res/break.wav")); // 생명 추가 효과음 (선택 사항)
                break;
            default:
                break; // 충돌하지 않은 경우
        }
        return;
    }
  }
    
    private void handleBossLogic() {
        if (boss == null) {
            return; // 보스가 없는 경우 처리하지 않음
        }

        // 보스와 공 충돌
        if (boss.isHit(ball)) {
            boss.reduceHealth();

            if (boss.isDefeated()) {
            	gameState.setBossDefeated(true);
            	checkAchievements();
                score += 50;
                
                boss = null;
                

                if (levelManager.hasNextLevel()) {
                    levelManager.moveToNextLevel();
                    loadLevel();
                } else {
                    play = false;
                    gameOver = true;
                }
           
                return;
            }
        }

        // 디버프 공 발사 및 이동
        boss.checkDebuffLaunch();
        boss.moveDebuffBalls();

        // 디버프 공과 패들 충돌 처리
        for (DebuffBall debuffBall : boss.getDebuffBalls()) {
            if (debuffBall.isColliding(paddle)) {
                debuffBall.deactivate();
                applyDebuffToPaddle();
            }
        }
    }

    
    private void checkAchievements() {
        for (Achievement achievement : achievementManager.getAchievements()) {
            if (!achievement.isAchieved()) { // 아직 달성되지 않은 업적만 확인
                achievementManager.checkAchievements(gameState); // 업적 조건 확인
                if (achievement.isAchieved()) {
                    System.out.println("업적 달성됨: " + achievement.getName()); // 디버깅 메시지
                    AchievementUI.showAchievementUnlocked(achievement); // 업적 달성 알림
                }
            }
        }
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
//        System.out.println("Bomb Center: Row = " + bombRow + ", Col = " + bombCol);

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
                startCountdown();
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
        
        int keyCode = e.getKeyCode();
        
        if (keyCode == KeyEvent.VK_SPACE && !play) {
            play = true; // 게임 시작
            ball.setSpeed(2, -3); // 공의 속도 설정 (원하는 값으로 조정)
        }



        repaint();
    }

    private void resetGame() {
        if (gameOver) {
            leaderboard.addScore(score); // 점수 추가
        }
        gameState.reset();
        score = 0;
        gameOver = false;
        timeLeft = 60; // 타이머 초기화
        levelManager = new LevelManager();  // 레벨 초기화
        loadLevel();  // 레벨 로드
        paddle.reset();  // 패들 초기화

        // 공 초기화 (패들 위치에 맞게 공의 위치를 설정)
        // 패들의 중앙에 공을 위치시키기 위해 패들의 X 위치와 Y 위치를 조정
        ball.reset(paddle); // Ball 객체에 패들 객체를 넘겨줌

        play = false;
        showStartMessage = false;

        if (countdownTimer != null) {
            countdownTimer.stop(); // 기존 타이머 중지
        }
        startCountdown(); // 새로운 타이머 시작
        timer.start();
    }
    
    public void pauseGame() {
        play = false;
        if (timer != null) {
            timer.stop();
        }
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
    }

    public void resumeGame() {
        play = true;
        if (timer != null) {
            timer.start();
        }
        if (countdownTimer != null) {
            countdownTimer.start();
        }
    }
    
    private void moveToNextLevel() {
        if (levelManager.hasNextLevel()) {
            levelManager.moveToNextLevel();
            loadLevel();
            gameState.setLevelCompleted(true);
            checkAchievements();
            
            // 공 초기화 (패들의 위치에서 시작하도록)
            ball.reset(paddle);  // 공을 패들의 중앙 위치로 초기화
            
            play = true; // 게임 시작 상태로 변경
        } else {
            play = false;
            gameOver = true; // 모든 레벨 완료
        }
        
        checkAchievements();
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


	public boolean isPlay() {
		return play;
	}


	public void setPlay(boolean play) {
		this.play = play;
		if(play) {
			timer.start();
		} else {
			timer.stop();
		}
	}


	public boolean isGameOver() {
		return gameOver;
	}


	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}


	public boolean isGameStarted() {
		return gameStarted;
	}


	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}


	public boolean isShowStartMessage() {
		return showStartMessage;
	}


	public void setShowStartMessage(boolean showStartMessage) {
		this.showStartMessage = showStartMessage;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public Timer getTimer() {
		return timer;
	}


	public void setTimer(Timer timer) {
		this.timer = timer;
	}


	public Leaderboard getLeaderboard() {
		return leaderboard;
	}


	public void setLeaderboard(Leaderboard leaderboard) {
		this.leaderboard = leaderboard;
	}


	public int getDelay() {
		return delay;
	}


	public void setDelay(int delay) {
		this.delay = delay;
	}


	public int getTimeLeft() {
		return timeLeft;
	}


	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}


	public Timer getCountdownTimer() {
		return countdownTimer;
	}


	public void setCountdownTimer(Timer countdownTimer) {
		this.countdownTimer = countdownTimer;
	}


	public LifeSystem getLifeSystem() {
		return lifeSystem;
	}


	public void setLifeSystem(LifeSystem lifeSystem) {
		this.lifeSystem = lifeSystem;
	}


	public int getLives() {
		return lives;
	}


	public void setLives(int lives) {
		this.lives = lives;
	}


	public boolean isPaddleGrown() {
		return isPaddleGrown;
	}


	public void setPaddleGrown(boolean isPaddleGrown) {
		this.isPaddleGrown = isPaddleGrown;
	}


	public Boss getBoss() {
		return boss;
	}


	public void setBoss(Boss boss) {
		this.boss = boss;
	}


	public Paddle getPaddle() {
		return paddle;
	}


	public void setPaddle(Paddle paddle) {
		this.paddle = paddle;
	}


	public Ball getBall() {
		return ball;
	}


	public void setBall(Ball ball) {
		this.ball = ball;
	}


	public MapGenerator getMap() {
		return map;
	}


	public void setMap(MapGenerator map) {
		this.map = map;
	}


	public LevelManager getLevelManager() {
		return levelManager;
	}


	public void setLevelManager(LevelManager levelManager) {
		this.levelManager = levelManager;
	}


	public ThemeManager getThemeManager() {
		return themeManager;
	}


	public void setThemeManager(ThemeManager themeManager) {
		this.themeManager = themeManager;
	}


	public Music getMusic() {
		return music;
	}


	public void setMusic(Music music) {
		this.music = music;
	}
}