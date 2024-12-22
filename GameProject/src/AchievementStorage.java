import java.io.*;
import java.util.List;

public class AchievementStorage {
    private static final String FILE_NAME = "achievements.txt";

    public static void saveAchievements(List<Achievement> achievements) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Achievement achievement : achievements) {
                writer.write(achievement.getName() + ":" + achievement.isAchieved());
                writer.newLine();
            }
        }
    }

    public static void loadAchievements(List<Achievement> achievements) throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                for (Achievement achievement : achievements) {
                    if (achievement.getName().equals(parts[0])) {
                        achievement.achieve();
                    }
                }
            }
        }
    }
}