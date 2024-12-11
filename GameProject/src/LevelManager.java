import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private List<Level> levels;
    private int currentLevelIndex;

    public LevelManager() {
        levels = new ArrayList<>();
        currentLevelIndex = 0;

        // 레벨 1
        levels.add(new Level(
            new int[][]{
                {1, 1, 1, 1, 1, 1, 1,1,1},
                {1, 1, 1, 1, 1, 1, 1,1,1},
                {1, 1, 1, 2, 3, 1, 1,1,1},
            },
            2, // 공의 X축 속도
            -3 // 공의 Y축 속도
        ));

        // 레벨 2
        levels.add(new Level(
            new int[][]{
                {1, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1, 0, 1}
            },
            3, 
            -3
        ));

        // 레벨 3
        levels.add(new Level(
            new int[][]{
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 1, 1},
                {1, 1, 1, 1, 1, 1, 1}
            },
            4,
            -4
        ));
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    public boolean hasNextLevel() {
        return currentLevelIndex < levels.size() - 1;
    }

    public void moveToNextLevel() {
        if (hasNextLevel()) {
            currentLevelIndex++;
        }
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex + 1; // 1부터 시작하는 레벨 번호
    }
}