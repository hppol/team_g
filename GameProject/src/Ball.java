import java.awt.Color;
import java.awt.Graphics;

public class Ball {
    private int x, y, diameter, speedX, speedY;

    public Ball(int x, int y, int diameter, int speedX, int speedY) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void draw(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval(x, y, diameter, diameter);
    }

    public void move() {
        x += speedX;
        y += speedY;

        if (x < 0 || x > 670) {
            speedX = -speedX;
        }
        if (y < 0) {
            speedY = -speedY;
        }
    }

    public void setSpeed(int speedX, int speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public boolean checkCollision(Paddle paddle) {
        // 공이 패들과 충돌했는지 확인
        return x + diameter > paddle.getX() && x < paddle.getX() + paddle.getWidth() &&
               y + diameter >= paddle.getY();
    }

    public void bounceOffPaddle(Paddle paddle) {
        // 패들의 중심점과 공의 중심점 차이 계산
        int paddleCenter = paddle.getX() + (paddle.getWidth() / 2);
        int ballCenter = x + (diameter / 2);
        

        // 반사 각도 조정: 공의 X축 속도를 패들 중심과의 거리로 결정
        int offset = ballCenter - paddleCenter; // 공과 패들 중심의 거리
        speedX = (int) (offset * 0.1); // X축 속도는 거리 비례
        speedY = -Math.abs(speedY);
        normalizeSpeed(5); // 총 속도 크기를 5로 유지
    }

    // 속도 크기 조정 메서드
    private void normalizeSpeed(int totalSpeed) {
        double magnitude = Math.sqrt(speedX * speedX + speedY * speedY);
        speedX = (int) (speedX / magnitude * totalSpeed);
        speedY = (int) (speedY / magnitude * totalSpeed);
    }

    public void bounceOffBrick() {
        speedY = -speedY; // 벽돌에 충돌 시 Y축 방향 반전
    }

    public void reset() {
        x = 120;
        y = 350;
        speedX = 2;
        speedY = -3;
    }
    
    public void reverseX() {
        speedX = -speedX;
    }

    public void reverseY() {
        speedY = -speedY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDiameter() {
        return diameter;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }
}