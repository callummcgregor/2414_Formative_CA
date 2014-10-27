import java.util.Random;

public class PebbleGame {
    Bag[] bags = new Bag[3];
    Player players[];
    int noOfPlayers = 0;

    public static void main(String[] args) {
        PebbleGame game = new PebbleGame(args);
        game.startGame();
    }
    
    public PebbleGame(String[] args){
    	noOfPlayers = Integer.parseInt(args[3]);
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

        for (int i=0; i<noOfPlayers; i++) 
           players[i] = new Player("player" + Integer.toString(i + 1));
        for (Player player: players)
        	player.start();
    }
    
    /* Run an instance of the game using the initialised values */
    public void startGame(){
    	
    }
    
    public synchronized int getLargestBag(){
    	int largestBagIndex = -1;
    	int largestBagSize = 0;
    	for (int i = 0; i < 3; i++){
    		if (bags[i].getBlackSize() > largestBagSize) {
    			largestBagSize = bags[i].getBlackSize();
    			largestBagIndex = i;
    		}		
    	}
    	return largestBagIndex;
    }

    /* Nested Player class */
    public class Player extends Thread {
        int[] hand;
        int lastBagIndex = 0;   // 0..2

        public Player(String str){
        	super(str);
        	System.out.println("Hi, my name is " + getName());
        	hand = new int[0];
        }
        
        public void run() {
        	Random rand = new Random();
        	
            drawStart();

            while(!isInterrupted()) {
                if (hand.length == 10) {
                	if (getWeight() == 100) {
                		System.out.println(getName() + " has only gone and won it!!!");
                		System.exit(1);
                	} else {
                		System.out.println("Pebble discarded.");
                		discardPebble();
                	}
                } else {
                	drawPebble(rand.nextInt(2));
                }
                System.out.println(getName() + " hand is: " + getHand());
            }
        }

        private void drawStart() {
        	while (hand.length < 9) {
	        	drawPebble(getLargestBag());
        	}
        }

        /* Draws a random pebble from a random bag and adds it to hand */
        public void drawPebble(int bagNum) {
        	/* Intermediate array */
        	int drawnPebble;
        	if((drawnPebble = bags[bagNum].drawPebble()) == -1) {
        		return;
        	}
            int temp[] = new int[hand.length + 1];
            for (int i = 0; i < hand.length; i++)
            	temp[i] = hand[i];
            temp[hand.length] = drawnPebble;
            hand = temp;
            
            lastBagIndex = bagNum;
        }
        
        private void discardPebble() {
        	Random rand = new Random();
        	
        	int pebbleIndex = rand.nextInt(hand.length-1);
        	int pebble = hand[pebbleIndex];
        	
        	int[] temp = new int[hand.length - 1];
        	
        	for (int i=0; i < pebbleIndex; i++)
    			temp[i] = hand[i];
    		for (int i=pebbleIndex; i < hand.length - 1; i++)
    			temp[i] = hand[i+1];
    		hand = temp;
    		
    		bags[lastBagIndex].discardPebble(pebble);
        }

        /* Returns total weight of pebbles in player's hand */
        private int getWeight() {
            int weight=0;
            for (int i: hand)
                weight += i;
            return weight;
        }
        
        /* Return string of player's hand */
        private String getHand(){
        	String result = "";
        	for (int i : hand){
        		result += Integer.toString(i) + ",";
        	}
        	return result.substring(0,result.length()-1); //remove trailing comma
        }
    }
}