public class LifeSystem {
    private int lives;

    public LifeSystem(int initialLives) {
        this.lives = initialLives;
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

    public boolean isGameOver() {
        return lives <= 0;
    }
}