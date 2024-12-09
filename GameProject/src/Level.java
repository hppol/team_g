public class Level {
    private int[][] brickLayout; 
    private int ballSpeedX; 
    private int ballSpeedY; 

    public Level(int[][] brickLayout, int ballSpeedX, int ballSpeedY) {
        this.brickLayout = brickLayout;
        this.ballSpeedX = ballSpeedX;
        this.ballSpeedY = ballSpeedY;
    }

    public int[][] getBrickLayout() {
        return brickLayout;
    }

    public int getBallSpeedX() {
        return ballSpeedX;
    }

    public int getBallSpeedY() {
        return ballSpeedY;
    }
}