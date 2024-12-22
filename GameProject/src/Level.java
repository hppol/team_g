public class Level {
    private int[][] brickLayout;
    private int ballSpeedX;
    private int ballSpeedY;
    private Boss boss; // 보스 정보

    public Level(int[][] brickLayout, int ballSpeedX, int ballSpeedY, Boss boss) {
        this.brickLayout = brickLayout;
        this.ballSpeedX = ballSpeedX;
        this.ballSpeedY = ballSpeedY;
        this.boss = boss;
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

	public void setBrickLayout(int[][] brickLayout) {
		this.brickLayout = brickLayout;
	}

	public void setBallSpeedX(int ballSpeedX) {
		this.ballSpeedX = ballSpeedX;
	}

	public void setBallSpeedY(int ballSpeedY) {
		this.ballSpeedY = ballSpeedY;
	}
	
	public Boss getBoss() {
        return boss;
	}

	public void setBoss(Boss boss) {
		this.boss = boss;
	}
}