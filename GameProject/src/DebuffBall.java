import java.awt.Color;
import java.awt.Graphics;

public class DebuffBall {
    private int x, y, diameter;
    private int speedY; // Y축 속도
    private boolean active; // 디버프 공의 활성 상태

    public DebuffBall(int x, int y, int diameter, int speedY) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.speedY = speedY;
        this.active = true;
    }

    public void draw(Graphics g) {
        if (active) {
            g.setColor(Color.MAGENTA); // 디버프 공의 색상
            g.fillOval(x, y, diameter, diameter);
        }
    }

    public void move() {
        if (active) {
            y += speedY; // 공이 아래로 떨어짐
        }
    }

    public boolean isColliding(Paddle paddle) {
        // 충돌 여부 확인
        return active &&
               x + diameter > paddle.getX() &&
               x < paddle.getX() + paddle.getWidth() &&
               y + diameter >= paddle.getY();
    }

    public void deactivate() {
        active = false; // 공 비활성화
    }

    public boolean isActive() {
        return active;
    }
}