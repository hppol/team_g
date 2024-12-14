import java.awt.Color;

public class Theme {
    private Color backgroundColor;
    private Color paddleColor;
    private Color ballColor;
    private Color brickColor;

    public Theme(Color backgroundColor, Color paddleColor, Color ballColor, Color brickColor) {
        this.backgroundColor = backgroundColor;
        this.paddleColor = paddleColor;
        this.ballColor = ballColor;
        this.brickColor = brickColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getPaddleColor() {
        return paddleColor;
    }

    public Color getBallColor() {
        return ballColor;
    }

    public Color getBrickColor() {
        return brickColor;
    }
}