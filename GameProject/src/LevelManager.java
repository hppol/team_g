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

        int screenWidth = 700; // 화면 너비
        int screenHeight = 300; // 화면 높이

        int bossWidth = (int) (screenWidth * 0.8); // 가로 크기: 화면 너비의 80%
        int bossHeight = (int) (screenHeight * 0.4); // 세로 크기: 화면 높이의 20%

        int bossX = (screenWidth - bossWidth) / 2; // 화면 가운데 정렬
        int bossY = (screenHeight - bossHeight) / 2; // 화면 가운데 정렬

        levels.add(new Level(
            new int[1][1], // 1x1 배열로 설정 (실제로는 벽돌 없음)
            4, -4,
            new Boss(bossX, bossY, bossWidth, bossHeight, 5) // 보스 정보
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