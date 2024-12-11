import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
    public int[][] map;
    public Paddle paddle;
    public int brickWidth;
    public int brickHeight;

    // 기존 생성자
    public MapGenerator(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map[i][j] = 1;
            }
        }
        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    // 새 생성자: 벽돌 배열을 직접 받아 초기화
    public MapGenerator(int[][] layout) {
        map = layout;
        brickWidth = 540 / layout[0].length;
        brickHeight = 150 / layout.length;
    }

    public void setBrickLayout(int[][] layout) {
        map = layout;
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    if (map[i][j] == 1) {
                        g.setColor(Color.white); // 일반 벽돌
                    } else if (map[i][j] == 2) {
                        g.setColor(Color.blue); // 아이템 블록
                    } else if (map[i][j] == 3) {
                        g.setColor(Color.red); // 폭탄 블록
                    }
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }
    

    
    public int hitBrick(Ball ball) {
        int ballX = ball.getX();
        int ballY = ball.getY();
        int ballDiameter = ball.getDiameter();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) { // 벽돌 활성 상태
                    int brickX = j * brickWidth + 80;
                    int brickY = i * brickHeight + 50;

                    // 충돌 여부 확인
                    if (ballX + ballDiameter > brickX && ballX < brickX + brickWidth &&
                        ballY + ballDiameter > brickY && ballY < brickY + brickHeight) {
                        
                        // 충돌 방향 판별
                        if (ballY + ballDiameter - ball.getSpeedY() <= brickY ||
                            ballY - ball.getSpeedY() >= brickY + brickHeight) {
                            ball.reverseY(); // 위/아래에서 충돌
                        } else {
                            ball.reverseX(); // 좌/우에서 충돌
                        }

                        int blockType = map[i][j]; // 블록 유형 저장
                        map[i][j] = 0; // 블록 제거
                        return blockType; // 블록 유형 반환
                    }
                }
            }
        }
        return 0; // 충돌 없음
    }

    // 모든 벽돌이 제거되었는지 확인
    public boolean isAllBricksDestroyed() {
        for (int[] row : map) {
            for (int brick : row) {
                if (brick > 0) {
                    return false; // 아직 남아있는 벽돌이 있음
                }
            }
        }
        return true; // 모든 벽돌이 제거됨
    }

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	public Paddle getPaddle() {
		return paddle;
	}

	public void setPaddle(Paddle paddle) {
		this.paddle = paddle;
	}

	public int getBrickWidth() {
		return brickWidth;
	}

	public void setBrickWidth(int brickWidth) {
		this.brickWidth = brickWidth;
	}

	public int getBrickHeight() {
		return brickHeight;
	}

	public void setBrickHeight(int brickHeight) {
		this.brickHeight = brickHeight;
	}
}