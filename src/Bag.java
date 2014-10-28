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
	
	 /* Draws a pebble from the given bag and returns it's weight value */
    public synchronized int drawPebble(){
    	int result = 0;
		Random rand = new Random();

        /* Exit with value -1 if both bags empty */
		if (getBlackSize() == 0 && getWhiteSize() == 0) {
			return -1;
		}
		if (getBlackSize() == 0){
			whiteToBlack();
		}
		int removeIndex = (getBlackSize() == 1) ? 0 : rand.nextInt(getBlackSize() - 1);
	
		result = black[removeIndex];
		
		int[] temp = new int[getBlackSize() - 1];
		
		/* Concatenate black list before and after item being removed */
		for (int i=0; i < removeIndex; i++)
			temp[i] = black[i];
		for (int i=removeIndex; i < getBlackSize() - 1; i++)
			temp[i] = black[i+1];
		
		black = temp;
		
    	if (getBlackSize() == 0)
    		whiteToBlack();
    	return result;
    }
    
    /* Put pebble into white bag */
    public synchronized void discardPebble(int value){
    	 int temp[] = new int[getWhiteSize() + 1];
         for (int i = 0; i < getWhiteSize(); i++)
         	temp[i] = white[i];
         temp[getWhiteSize()] = value;
         white = temp;
    }
    
    /* Return the current number of items in black bag */
    public synchronized int getBlackSize(){
    	return black.length;
    }
    
    /* Return the current number of items in black bag */
    public synchronized int getWhiteSize(){
    	return white.length;
    }
    
    /* Move white values to black bag */
    private synchronized void whiteToBlack(){
    	black = white;
    	white = new int[0];
    }
    
    public int[] getBlackBag(){
    	return black;
    }
    /* Get pebble values from given file */
    private static int[] readFromFile(String fileName) {
        int noOfPebbles = 0;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            // Read lines until eof
            while ((line = reader.readLine()) != null) {
                String fileContents[] = line.split(","); // Explode string
                
                noOfPebbles = fileContents.length;
                
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
            System.exit(1);
        }

        // Method only reaches this line if unsuccessful file read
        System.out.println("Invalid file, no values read");
        return new int[] {};
    }
}