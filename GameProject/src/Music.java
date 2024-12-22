import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Random;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.player.Player;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.BitstreamException;


public class Music {
	private Player player;
    private boolean isMuted = false; // 음소거 상태
    private float volume = 1.0f;     // 기본 볼륨 값 (0.0 ~ 1.0)
    
    // 배경 음악 재생
    public void bgplay1() {
    	stopMusic();
   	 	Player jlPlayer= null;
   	 	try{
	    	 FileInputStream fileInputStream = new
	    	 FileInputStream("res/RyuTheme.mp3");
	    	 BufferedInputStream bufferedInputStream= new
	    	 BufferedInputStream(fileInputStream);
	    	 jlPlayer = new Player(bufferedInputStream);
   	 } catch(Exception e) {
   		 System.out.println(e.getMessage());
   	 }
   	        
   	final Player player= jlPlayer;
   	 new Thread() {
	    	 public void run() {
		    	 try {
		    		 player.play();
		    	 } catch (Exception e) {
		    		 System.out.println(e.getMessage());         
		    	 }
	    	 }
   	 }.start();
   }

    public void bgplay2() {
    	stopMusic();
    	Player jlPlayer= null;
    	try{
	    	 FileInputStream fileInputStream = new
	    	 FileInputStream("res/guitar.mp3");
	    	 BufferedInputStream bufferedInputStream= new
	    	 BufferedInputStream(fileInputStream);
	    	 jlPlayer = new Player(bufferedInputStream);
  	 } catch(Exception e) {
  		 System.out.println(e.getMessage());
  	 }
  	        
  	final Player player= jlPlayer;
  	 new Thread() {
	    	 public void run() {
		    	 try {
		    		 player.play();
		    	 } catch (Exception e) {
		    		 System.out.println(e.getMessage());         
		    	 }
	    	 }
  	 }.start();
  }
    
    // 음악 중지
    public void stopMusic() {
        if (player != null) {
            player.close();
        }
    }

    // 음소거 토글
    public void muteToggle() {
        isMuted = !isMuted;
        if (isMuted) {
            System.out.println("Music muted.");
            volume = 0.0f; // 음소거 시 볼륨 0으로 설정
        } else {
            System.out.println("Music unmuted.");
            volume = 1.0f; // 음소거 해제 시 기본 볼륨 복원
        }
    }

    // 볼륨 증가
    public void increaseVolume() {
        if (volume < 1.0f) {
            volume += 0.1f;
            System.out.printf("Volume increased to %.1f%n", volume);
        } else {
            System.out.println("Volume is already at maximum.");
        }
    }

    // 볼륨 감소
    public void decreaseVolume() {
        if (volume > 0.0f) {
            volume -= 0.1f;
            System.out.printf("Volume decreased to %.1f%n", volume);
        } else {
            System.out.println("Volume is already at minimum.");
        }
    }


public void playRandomSong() {
    Random rand = new Random();
    int songChoice = rand.nextInt(2);  // 0 또는 1이 랜덤으로 생성됨

    switch (songChoice) {
        case 0:
            bgplay1();  // RyuTheme.mp3 재생
            break;
        case 1:
            bgplay2();  // guitar.mp3 재생
            break;
        default:
//            System.out.println("Error: Invalid song choice.");
            break;
    }
}

public Player getPlayer() {
	return player;
}

public void setPlayer(Player player) {
	this.player = player;
}

public boolean isMuted() {
	return isMuted;
}

public void setMuted(boolean isMuted) {
	this.isMuted = isMuted;
}

public float getVolume() {
	return volume;
}

public void setVolume(float volume) {
	this.volume = volume;
}

}
