import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * this class reads a file from a text file
 * @author goob
 *
 */

/** reader, accessible through constructor*/
public class Reader {
	
    /** getter for file*/
    public ArrayList<String> getFile(String x){
    	return this.readFile(x);
    }
    /**setter for file */
    //public ArrayList

    /**
     * readFile basically reads a text file, run this private method through the getFile method
     * @param x file to read
     * @return ArrayList of lines in specified file
     */
	private ArrayList<String> readFile(String x){
        File file = new File(x);
        ArrayList<String> array = new ArrayList<String>(); //new array list for lines
        try {
            Scanner scanner = new Scanner(file); //try to locate the file
            
            while (scanner.hasNextLine()){ //while file has another line 
                String s = scanner.nextLine(); //get line
                array.add(s);                  //add line to array
                System.out.println("::"+s);
            }
            scanner.close(); //close scanner
            return array;    //return arraylist of all lines
        } 
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return null; //if scanner didn't have a file, return null?

    }
}