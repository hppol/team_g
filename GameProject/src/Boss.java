import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Boss {
    private int x, y, width, height, health;
    private List<DebuffBall> debuffBalls; // 디버프 공 리스트
    private long lastDebuffTime; // 마지막 디버프 발사 시간
    private static final long DEBUFF_INTERVAL = 5000; // 디버프 발사 간격 (밀리초)

    public Boss(int x, int y, int width, int height, int health) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.debuffBalls = new ArrayList<>();
        this.lastDebuffTime = System.currentTimeMillis();
    }

    public void draw(Graphics g) {
        if (health > 0) {
            // 보스의 색상을 건강 상태에 따라 다르게 설정
            if (health > 3) {
                g.setColor(Color.RED); // 건강할 때
            } else if (health > 1) {
                g.setColor(Color.ORANGE); // 중간 상태
            } else {
                g.setColor(Color.YELLOW); // 마지막 상태
            }
            g.fillRect(x, y, width, height);

            // 보스의 테두리
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            
            for (DebuffBall ball : debuffBalls) {
                ball.draw(g);
            }
        }
    }
    
    public void moveDebuffBalls() {
        // 디버프 공 이동
        for (DebuffBall ball : debuffBalls) {
            ball.move();
        }
    }
    
    public void checkDebuffLaunch() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDebuffTime >= DEBUFF_INTERVAL) {
            // 디버프 공 발사
            int ballX = x + width / 2 - 10; // 보스의 중앙에서 발사
            int ballY = y + height;
            debuffBalls.add(new DebuffBall(ballX, ballY, 20, 3));
            lastDebuffTime = currentTime;
        }
    }
    
    public List<DebuffBall> getDebuffBalls() {
        return debuffBalls;
    }

    public boolean isHit(Ball ball) {
        int ballX = ball.getX();
        int ballY = ball.getY();
        int ballDiameter = ball.getDiameter();

        // 보스의 경계 계산
        boolean hit = ballX + ballDiameter > x && ballX < x + width &&
                      ballY + ballDiameter > y && ballY < y + height;

        if (hit) {
            // 충돌 방향 판별
            boolean hitTop = ballY + ballDiameter - ball.getSpeedY() <= y;
            boolean hitBottom = ballY - ball.getSpeedY() >= y + height;
            boolean hitLeft = ballX + ballDiameter - ball.getSpeedX() <= x;
            boolean hitRight = ballX - ball.getSpeedX() >= x + width;

            if (hitTop || hitBottom) {
                ball.reverseY(); // 위 또는 아래에서 충돌 시 Y축 반전
            }
            if (hitLeft || hitRight) {
                ball.reverseX(); // 왼쪽 또는 오른쪽에서 충돌 시 X축 반전
            }

            // 공의 위치 보정 (보스 경계 바깥으로 이동)
            if (hitTop) {
                ball.setY(y - ballDiameter); // 보스 상단 바깥으로 이동
            } else if (hitBottom) {
                ball.setY(y + height); // 보스 하단 바깥으로 이동
            } else if (hitLeft) {
                ball.setX(x - ballDiameter); // 보스 왼쪽 바깥으로 이동
            } else if (hitRight) {
                ball.setX(x + width); // 보스 오른쪽 바깥으로 이동
            }
        }

        return hit;
    }

    public void reduceHealth() {
        if (health > 0) {
            health--;
        }
    }

    public boolean isDefeated() {
        return health <= 0;
    }

	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}
}