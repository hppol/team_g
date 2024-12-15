import java.awt.Color;
import java.util.HashMap;

public class ThemeManager {
    private HashMap<String, Theme> themes;
    private Theme currentTheme;

    public ThemeManager() {
        themes = new HashMap<>();
        
        // 기본 테마 추가
        themes.put("Classic",
        		new Theme(Color.BLACK, Color.GREEN, Color.YELLOW, Color.WHITE));
        
        themes.put("Dark",
        		new Theme(Color.DARK_GRAY, Color.CYAN, Color.RED, Color.LIGHT_GRAY));
        
        themes.put("Retro",
        		new Theme(Color.ORANGE, Color.MAGENTA, Color.BLUE, Color.PINK));
        
        themes.put("Pastel",
        		new Theme(new Color(255, 223, 186), new Color(186, 255, 201), new Color(255, 186, 217), new Color(223, 255, 255))); // 부드러운 색상
        
        themes.put("Ocean",
        		new Theme(new Color(0, 105, 148), new Color(0, 168, 255), new Color(0, 75, 122), new Color(72, 209, 204))); // 바다 느낌
        
        themes.put("Forest",
        		new Theme(new Color(34, 139, 34), new Color(85, 107, 47), new Color(139, 69, 19), new Color(107, 142, 35))); // 숲의 느낌
        
        themes.put("Neon",
        		new Theme(new Color(57, 255, 20), new Color(255, 20, 147), new Color(50, 205, 50), new Color(255, 255, 0))); // 네온 느낌
        
        themes.put("Monochrome",
        		new Theme(Color.DARK_GRAY, Color.LIGHT_GRAY, Color.GRAY, Color.WHITE)); // 단색
        
        themes.put("Solarized",
        		new Theme(new Color(253, 246, 227), new Color(38, 139, 210), new Color(133, 153, 0), new Color(181, 137, 0))); // Solarized 색상 팔레트
        
        themes.put("Berry",
        		new Theme(new Color(123, 63, 0), new Color(227, 66, 52), new Color(210, 77, 87), new Color(239, 183, 200))); // 과일 테마
        
        themes.put("Desert",
        		new Theme(new Color(250, 235, 215), new Color(210, 180, 140), new Color(244, 164, 96), new Color(160, 82, 45))); // 사막 테마
        
        themes.put("Galaxy",
        		new Theme(new Color(25, 25, 112), new Color(138, 43, 226), new Color(0, 0, 128), new Color(75, 0, 130))); // 우주 테마
        
        themes.put("Rainbow",
        		new Theme(new Color(255, 0, 0), new Color(255, 127, 0), new Color(0, 0, 255), new Color(127, 0, 255))); // 무지개 테마
        
        themes.put("Mint",
        		new Theme(new Color(189, 236, 182), new Color(150, 229, 206), new Color(200, 255, 233), new Color(170, 255, 195))); // 민트 테마
        
        
        // 초기 테마 설정
        currentTheme = themes.get("Classic");
    }

    public Theme getCurrentTheme() {
        return currentTheme;
    }

    public void setTheme(String themeName) {
        if (themes.containsKey(themeName)) {
            currentTheme = themes.get(themeName);
        }
    }

    public HashMap<String, Theme> getThemes() {
        return themes;
    }
}
