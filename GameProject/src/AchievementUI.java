import javax.swing.JOptionPane;

public class AchievementUI {
    public static void showAchievementUnlocked(Achievement achievement) {
        JOptionPane.showMessageDialog(
            null,
            "업적 달성! " + achievement.getName() + "\n" + achievement.getDescription(),
            "업적 달성",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
