import java.awt.Color;
import java.awt.Graphics;

public class Paddle {
    private int x, y, width, height;

    public Paddle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(x, y, width, height);
    }

    public void moveRight() {
        if (x < 600) {
            x += 20;
        }
    }

    public void moveLeft() {
        if (x > 10) {
            x -= 20;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}