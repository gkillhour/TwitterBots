import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Boot {
	public static void main(String args[]){
		HHBot HistoryBot = new HHBot();
		while(true){
			
			/*try to load last saved line */
			try {
				HistoryBot.loadLineNum();
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
				//Possible bug: should i put HistoryBot.resetLineNum() here to prevent errors with line num being null?
			}
			
			HistoryBot.tweetCurrentString(); //tweet current string!
			
			/* sleep thread */
			try {
				TimeUnit.HOURS.sleep(6);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
