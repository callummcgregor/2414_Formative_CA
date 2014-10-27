import java.util.Random;

/**
 * Created by callum on 24/10/14.
 */
public class PebbleGame {
    Bag[] bags = new Bag[3];
    Player players[];

    public static void main(String[] args) {
        PebbleGame game = new PebbleGame(args);
    }
    
    public PebbleGame(String[] args){
    	int noOfPlayers = Integer.parseInt(args[3]);
        char[] ids = {'X','Y','Z', 'A','B','C'}; // Used for printing output file

        /* Assigns values for bags X, Y and Z from read in files */
        for (int i = 0; i < 3; i++)
        	bags[i] = new Bag(args[i],ids[i], ids[i+3]);
        if (bags[0].getBlackSize() == 0 || bags[1].getBlackSize() == 0 || bags[2].getBlackSize() == 0){
        	System.out.println("One or more bags contains no pebbles, exiting");
        	System.exit(1);
        }
        
        /* Checks the minimum number of pebbles present in game */
        if ((bags[0].getBlackSize() + bags[1].getBlackSize() + bags[2].getBlackSize()) < (noOfPlayers * 9)){
            System.out.println("Not enough pebbles in bags for " + args[3] + " players.");
            System.exit(1);
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

                newPebble = bags[bagNum].drawPebble(bagNum);
            }
            /* Intermediate array */
            int temp[] = new int[hand.length + 1];
            for (int i = 0; i < hand.length; i++)
            	temp[i] = hand[i];
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
}