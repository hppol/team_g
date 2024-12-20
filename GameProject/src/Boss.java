import java.awt.Color;
import java.awt.Graphics;

public class Boss {
    private int x, y, width, height, health;

    public Boss(int x, int y, int width, int height, int health) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
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
        }
    }

    public boolean isHit(Ball ball) {
        int ballX = ball.getX();
        int ballY = ball.getY();
        int ballDiameter = ball.getDiameter();

        // 충돌 여부 확인
        return ballX + ballDiameter > x && ballX < x + width &&
               ballY + ballDiameter > y && ballY < y + height;
    }

    public void reduceHealth() {
        if (health > 0) {
            health--;
        }
    }

    public boolean isDefeated() {
        return health <= 0;
    }
}