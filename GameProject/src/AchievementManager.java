import java.util.ArrayList;
import java.util.List;

public class AchievementManager {
    private List<Achievement> achievements;

    public AchievementManager() {
        achievements = new ArrayList<>();

        // 기본 업적 추가
        achievements.add(new Achievement("First Steps", "벽돌을 하나 부수세요!"));
        achievements.add(new Achievement("Survivor", "생명 1개로 레벨을 완료하세요."));
        achievements.add(new Achievement("Speed Demon", "공의 속도를 최대치로 유지하세요."));
        achievements.add(new Achievement("Boss Slayer", "보스를 처음으로 물리치세요."));
        achievements.add(new Achievement("Brick Destroyer", "벽돌 50개를 부수세요."));
    }

    public void checkAchievements(GameState gameState) {
        // 각 업적 조건을 확인
        for (Achievement achievement : achievements) {
            if (!achievement.isAchieved()) {
                switch (achievement.getName()) {
                    case "First Steps":
                        if (gameState.getBricksDestroyed() >= 1) {
                            achievement.achieve();
                            System.out.println("First Steps 업적 달성");
                        }
                        break;
                    case "Survivor":
                        if (gameState.getLives() == 1 && gameState.isLevelCompleted()) {
                            achievement.achieve();
                        }
                        break;
                    case "Speed Demon":
                        if (gameState.getBallSpeed() >= 10) {
                            achievement.achieve();
                        }
                        break;
                    case "Boss Slayer":
                        if (gameState.isBossDefeated()) {
                            achievement.achieve();
                            System.out.println("Boss Slayer 업적 달성");
                        }
                        break;
                    case "Brick Destroyer":
                        if (gameState.getBricksDestroyed() >= 50) {
                            achievement.achieve();
                        }
                        break;
                }
            }
        }
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }
}
