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
    
    public void grow() {
        width += 30; // 크기 증가
        if (width > 200) {
            width = 200; // 최대 크기 제한
        }
    }
    
    public void resetSize() {
        width = 100; // 기본 크기
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

   public void reset() {
      // TODO Auto-generated method stub
      
   }

public void setX(int x) {
	this.x = x;
}

public void setY(int y) {
	this.y = y;
}

public void setWidth(int width) {
	this.width = width;
}

public void setHeight(int height) {
	this.height = height;
}
}