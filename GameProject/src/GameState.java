public class GameState {
    private int bricksDestroyed;  // 부순 벽돌의 개수
    private int lives;            // 남은 생명
    private int ballSpeed;        // 공의 속도
    private boolean bossDefeated; // 보스 처치 여부
    private boolean levelCompleted; // 레벨 완료 여부

    // 생성자
    public GameState(int initialLives) {
        this.lives = initialLives;
        this.bricksDestroyed = 0;
        this.ballSpeed = 0;
        this.bossDefeated = false;
        this.levelCompleted = false;
    }

    // Getter와 Setter
    public int getBricksDestroyed() {
        return bricksDestroyed;
    }

    public void incrementBricksDestroyed() {
        bricksDestroyed++;
    }

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    public void gainLife() {
        lives++;
    }

    public int getBallSpeed() {
        return ballSpeed;
    }

    public void setBallSpeed(int ballSpeed) {
        this.ballSpeed = ballSpeed;
    }

    public boolean isBossDefeated() {
        return bossDefeated;
    }

    public void setBossDefeated(boolean bossDefeated) {
        this.bossDefeated = bossDefeated;
    }

    public boolean isLevelCompleted() {
        return levelCompleted;
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.levelCompleted = levelCompleted;
    }

    // 게임 상태 초기화
    public void reset() {
        bricksDestroyed = 0;
        ballSpeed = 0;
        bossDefeated = false;
        levelCompleted = false;
    }
}
