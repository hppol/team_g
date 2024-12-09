import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
    public int[][] map;
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
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }
    
    public boolean hitBrick(Ball ball) {
        int ballX = ball.getX();
        int ballY = ball.getY();
        int ballDiameter = ball.getDiameter();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) { // 벽돌이 활성화 상태일 때만
                    int brickX = j * brickWidth + 80;
                    int brickY = i * brickHeight + 50;

                    // 충돌 여부 확인
                    if (ballX + ballDiameter > brickX && ballX < brickX + brickWidth &&
                        ballY + ballDiameter > brickY && ballY < brickY + brickHeight) {
                        
                        // 충돌한 벽돌 제거
                        map[i][j] = 0;

                        // 충돌 방향 판별
                        if (ballY + ballDiameter - ball.getSpeedY() <= brickY) {
                            // 공이 벽돌의 위쪽에서 충돌
                            ball.setY(brickY - ballDiameter);
                            ball.reverseY();
                        } else if (ballY - ball.getSpeedY() >= brickY + brickHeight) {
                            // 공이 벽돌의 아래쪽에서 충돌
                            ball.setY(brickY + brickHeight);
                            ball.reverseY();
                        } else if (ballX + ballDiameter - ball.getSpeedX() <= brickX) {
                            // 공이 벽돌의 왼쪽에서 충돌
                            ball.setX(brickX - ballDiameter);
                            ball.reverseX();
                        } else if (ballX - ball.getSpeedX() >= brickX + brickWidth) {
                            // 공이 벽돌의 오른쪽에서 충돌
                            ball.setX(brickX + brickWidth);
                            ball.reverseX();
                        }

                        return true; // 충돌 처리 완료
                    }
                }
            }
        }
        return false; // 충돌 없음
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
}
