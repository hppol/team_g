import java.io.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Leaderboard {
    private List<Integer> scores;
    private final String fileName = "leaderboard.txt"; // 점수 저장 파일

    public Leaderboard() {
        scores = new ArrayList<>();
        ensureFileExists(); // 파일이 없으면 생성
        loadScores(); // 파일에서 점수 불러오기
    }

    // 파일 존재 여부 확인 및 생성
    private void ensureFileExists() {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile(); // 파일 생성
            }
        } catch (IOException e) {
            System.out.println("리더보드 파일 생성 중 오류 발생: " + e.getMessage());
        }
    }

    // 점수 추가
    public void addScore(int score) {
        scores.add(score);
        Collections.sort(scores, Collections.reverseOrder()); // 높은 점수 순으로 정렬
        saveScores(); // 점수 저장
    }

    // 점수 불러오기
    private void loadScores() {
        File file = new File(fileName);
        if (!file.exists()) {
            ensureFileExists(); // 파일 생성
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(Integer.parseInt(line));
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("리더보드 파일을 읽는 중 오류 발생: " + e.getMessage());
        }
    }

    // 점수 저장하기
    private void saveScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int score : scores) {
                writer.write(score + "\n");
            }
        } catch (IOException e) {
            System.out.println("리더보드 파일을 저장하는 중 오류 발생: " + e.getMessage());
        }
    }

    // 리더보드 출력
    public void displayLeaderboard(JFrame parentFrame) {
        StringBuilder leaderboardText = new StringBuilder("Leaderboard:\n");
        int rank = 1;
        for (int score : scores) {
            leaderboardText.append(rank++).append(". ").append(score).append("\n");
        }

        JOptionPane.showMessageDialog(parentFrame, leaderboardText.toString(), "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
    }

    public List<Integer> getScores() {
        return scores;
    }

	public String getFileName() {
		return fileName;
	}

	public void setScores(List<Integer> scores) {
		this.scores = scores;
	}
}