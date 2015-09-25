import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * this class tweets out messages from a text file
 * @author goob
 *
 */
public class HHBot {
	
	/** fields */
	private static Reader r;
	private static ArrayList<String> textArray;
	private static Integer lineNum;
	private static String currentString;  //current line number - updated with tweetCurrentString()
	
	/**default constructor, builds new reader with each bot*/
	public HHBot(){
	r = new Reader(); //new reader() default constructor
	textArray = r.getFile("src/YourTextFileHere.txt");//source file
	lineNum = 0; //line num starts at 0
	currentString = "default string";
	}
	
	/** methods */
	public void tweetCurrentString(){
		Twitter twitter = TwitterFactory.getSingleton();
		System.out.println("array size:" + textArray.size()); /*print array size for debug*/
		
		if (lineNum <= (textArray.size() -1)){ //if object's current line is < size of the array
			currentString = textArray.get(lineNum); //get this string from the array
			System.out.println("current string = "+ currentString); /*print string in line array for debug*/

			if (currentString.length() <= 140){ //if currentString length is acceptable, try to post
				try {
					System.out.println("HOPEFULLY SUCESSFULLY Tweeting: "+ currentString);
					twitter.updateStatus(currentString);
					lineNum++; 
				} catch (TwitterException e) {
					e.printStackTrace();
					}
					
			}else if (currentString.length()> 140){
				System.out.println("could not tweet-string length too long \n tweeting next string");
				lineNum++; 					//increment line num
				this.tweetCurrentString(); //if the tweet was too long, try again
				} 
								//currentline++ at the end of the loop
			System.out.println("tried to tweet, incremented line num to: "+ lineNum);
			/** try catch surrounding our saveLineNum method - autosave after every attempted tweet*/
			try {
				this.saveLineNum(lineNum);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (lineNum <= textArray.size()-1) {System.out.println("your array is at its end, you've tweeted all the tweets!");
		try {
			this.resetLineNum(); //automatically reset our line number
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	}
	/**
	 * setTextFile Loads a new file into the reader, and from that a new file into the static field textArray 
	 * @param fileName The name of the new file 
	 * @return void sets static field textArray
	 */
	public void setTextFile(String fileName){
		textArray = r.getFile(fileName);
	}
	
	/** these methods all effect the lineNum which stored in the bin file, and read using the serializable
	 * class Integer, and ObjectInputStreams
	 */
	
	/**This resets the line number in the bin file, resetLineNum can also be done by deleting the old 
	 * linenum.bin file, in which case, a new one starting at 0 will be automatically created*
	 **/
	public void resetLineNum() throws IOException{
		Integer i = 0;
		lineNum = 0;
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("lineNum.bin"));
		
		objectOutputStream.writeObject(i);
		objectOutputStream.close();
	}
	/*save line number, this is automatically implemented after each sucessful tweet*/
	public void saveLineNum(Integer lineNumber) throws IOException{
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("lineNum.bin"));
		
		objectOutputStream.writeObject(lineNumber);
		objectOutputStream.close();
	}
	/*load up the line number, useful for returning to a file after a program crash*/
	public void loadLineNum() throws IOException, ClassNotFoundException{
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("lineNum.bin"));
		
		lineNum = (Integer) objectInputStream.readObject();
		objectInputStream.close();	
	}
	/* print the saved line number to stdout*/
	public void printLineNum() throws IOException, ClassNotFoundException{
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("lineNum.bin"));
		
		Integer p = (Integer) objectInputStream.readObject();
		System.out.println(p);
		objectInputStream.close();	
	}
	
}
