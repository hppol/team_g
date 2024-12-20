import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private List<Level> levels;
    private int currentLevelIndex;

    public LevelManager() {
        levels = new ArrayList<>();
        currentLevelIndex = 0;

        // 일반 레벨 추가
        levels.add(new Level(
            new int[][]{
                {1, 1, 1, 1, 3, 1, 1, 3, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 3, 1, 1, 1, 1, 3},
            },
            2, -3, null // 보스 없음
        ));

        levels.add(new Level(
            new int[][]{
                {1, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1, 0, 1}
            },
            3, -3, null // 보스 없음
        ));

        // 보스 레벨 추가
        levels.add(new Level(
        	    new int[1][1], // 1x1 배열로 설정 (실제로는 벽돌 없음)
        	    4, -4,
        	    new Boss(200, 100, 200, 50, 5) // 보스 정보
        	));
        
        levels.add(new Level(
                new int[][]{
                    {1, 0, 1, 0, 1, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1},
                    {1, 0, 1, 0, 1, 0, 1}
                },
                3, -3, null // 보스 없음
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

	public List<Level> getLevels() {
		return levels;
	}

	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}

	public void setCurrentLevelIndex(int currentLevelIndex) {
		this.currentLevelIndex = currentLevelIndex;
	}
}