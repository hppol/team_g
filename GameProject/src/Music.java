import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Random;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.player.Player;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.BitstreamException;


public class Music {
    
    // 배경 음악 재생
    public void bgplay1() {
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


public void playRandomSong() {
    Random rand = new Random();
    int songChoice = rand.nextInt(2);  // 0 또는 1이 랜덤으로 생성됨
    if (songChoice == 0) {
        bgplay1();  // RyuTheme.mp3 재생
    } else {
        bgplay2();  // guitar.mp3 재생
    }
}
}
