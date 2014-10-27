public class TestPebbleGame {

	public static void main(String[] args) {
		testBag();
	}
	
	public static void testBag(){
		Bag b = new Bag("file1.txt", 'X','A');
		for (int item : b.getBlackBag())
			System.out.print(Integer.toString(item) + ",");
		
		
		for(int i = 0; i < 12; i++){
			
			b.discardPebble(b.drawPebble());
			
			System.out.println("\n");
			for (int item : b.getBlackBag())
				System.out.print(Integer.toString(item) + ",");
		}
	}

}
