import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.player.Player;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.BitstreamException;


public class Music {
    
    // 배경 음악 재생
    public void bgplay() {
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
}
