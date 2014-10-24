import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

/**
 * Created by callum on 24/10/14.
 */
public class PebbleGame {
    // 0:X, 1:Y, 2:Z, 3:A, 4:B, 5:C
    static int bags[][];
    static Player players[];

    public static void main(String[] args) {
        int noOfPlayers = Integer.parseInt(args[3]);
        bags = new int[6][];

        /* Assigns values for bags X, Y and Z from read in files */
        bags[0] = readFromFile(args[0]);
        bags[1] = readFromFile(args[1]);
        bags[2] = readFromFile(args[2]);

        /* Checks the minimum number of pebbles present in game */
        if ((bags[0].length + bags[1].length + bags[2].length) < (noOfPlayers * 9)){
            System.out.println("Not enough pebbles in bags for " + args[3] + " players.");
            return;
        }

        players = new Player[noOfPlayers];

        for (int i=0; i<noOfPlayers; i++) {
            // FFFFFUUUUUUCCCCkkk
        }
    }

    /* Nested Player class */
    public class Player extends Thread {
        int[] hand;
        int lastBagIndex = 0;   // 0..2

        public void run() {

            drawStart();

            while(!isInterrupted() && getWeight() != 100) {
                if (getWeight() > 100)
                    discardPebble();
                else
                    drawPebble();
            }
        }

        private void drawStart() {

        }

        private void discardPebble() {

        }

        /* Draws a random pebble from a random bag and adds it to hand */
        public void drawPebble() {
            int newPebble = 0;
            int bagNum = 0;

            while (newPebble != -1) {
                /* Make a random number */
                Random rand = new Random();
                bagNum = rand.nextInt(2);

                newPebble = drawPebbleFrom(bagNum);
            }
            /* Intermediate array */
            int temp[] = new int[hand.length + 1];
            temp[hand.length] = newPebble;
            hand = temp;
            lastBagIndex = bagNum;
        }

        /* Returns total weight of pebbles in player's hand */
        int getWeight() {
            int weight=0;
            for (int i: hand)
                weight += i;
            return weight;
        }
    }


    /* Draws a pebble from the given bag and returns it's weight value */
    synchronized int drawPebbleFrom(int bag){
        if (bags[bag].length > 0){
            Random rand = new Random();
            int pebble = rand.nextInt(bags[bag].length - 1);
            return bags[bag][pebble];
        } else
            return -1;
    }

    synchronized void replacePebble(Player player){

    }

    private static int[] readFromFile(String fileName) {
        String fileContents[];
        int noOfPebbles;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("../" + fileName));
            String line;
            // Read lines until eof
            while ((line = reader.readLine()) != null) {
                fileContents = line.split(","); // Explode string

                noOfPebbles = fileContents.length;
                // Map strings to ints
                int pebbleValues[] = new int[noOfPebbles];
                try {
                    for (int i = 0; i < noOfPebbles; i++) {
                        int temp = Integer.parseInt(fileContents[i]);
                        if (temp <= 0)
                            throw new NumberFormatException("Pebble weight must be positive");
                    }
                    return pebbleValues;
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println("File read error");
            e.printStackTrace();
        }


        return null;
    }
}
