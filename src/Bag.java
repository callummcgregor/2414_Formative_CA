import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;


public class Bag {
	private int[] white;
	private int[] black;
	char blackID; // black bag label
	char whiteID; // white  "    "
	
	/* Create new instance of Bag (contains both black and white bags */
	public Bag(String file, char blackID, char whiteID){
		this.blackID = blackID;
		this.whiteID = whiteID;
		black = readFromFile(file);
		white = new int[0];
	}
	
	 /* Draws a pebble from the given bag and returns it's weight value
	  * -1 if empty */
    public synchronized int drawPebble(int bag){
    	int result = -1;
    	if (getBlackSize() < 1){
    		whiteToBlack();
    		return result;
    	} else {
    		Random rand = new Random();
    		// Keep reassigning a new index until a valid one appears
    		while (result == -1){
    			int removeIndex = rand.nextInt(black.length - 1);
    			if (black[removeIndex] >= 0){
    				result = black[removeIndex];
    				black[removeIndex] = -1;
    			}
    		}
    	}
    	return result;
    }
    
    /* Put pebble into white bag */
    public synchronized void discardPebble(int value){
    	 int temp[] = new int[white.length + 1];
         for (int i = 0; i < white.length; i++)
         	temp[i] = white[i];
         temp[white.length] = value;
         white = temp;
    }
    
    /* Return the current number of items in black bag */
    public int getBlackSize(){
    	int result = 0;
    	for (int val : black)
    		if (val > 0)
    			result++;
    	System.out.println(result);
    	return result;
    }
    
    /* Return the current number of items in black bag */
    public int getWhiteSize(){
    	return white.length;
    }
    
    /* Move white values to black bag */
    private void whiteToBlack(){
    	black = white;
    	white = new int[0];
    }
    
    /* Get pebble values from given file */
    private static int[] readFromFile(String fileName) {
        String fileContents[];
        int noOfPebbles = 0;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("../" + fileName));
            String line;
            // Read lines until eof
            while ((line = reader.readLine()) != null) {
                fileContents = line.split(","); // Explode string
                
                noOfPebbles += fileContents.length;
                
                // Map strings to ints
                int pebbleValues[] = new int[noOfPebbles];
                try {
                    for (int i = 0; i < noOfPebbles; i++) {
                        int temp = Integer.parseInt(fileContents[i]);
                        if (temp <= 0)
                            throw new NumberFormatException("Pebble weight must be positive");
                        pebbleValues[i] = temp;
                    }
                    reader.close();
                    return pebbleValues;
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println("File read error");
            e.printStackTrace();
        }

        // Method only reaches this line if unsuccessful file read
        System.out.println("Invalid file, no values read");
        return new int[] {};
    }
}
