import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setResizable(false);

        Screen screen = new Screen(frame); // Screen 생성
        frame.setVisible(true);
    }
}
