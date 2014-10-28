public class TestPebbleGame {

	public static void main(String[] args) {

        for (int i = 0; i <= 1000; i++) {
            PebbleGame pg = new PebbleGame(new String[] {"file1.txt", "file2.txt", "file3.txt", "3"});
            pg.startGame();
            System.out.println(i);
        }

        System.exit(1);
	}

}
