package lab;

import java.util.Scanner;

public class Main {
	//	static ConnectFourGame game = new ConnectFourGame();
	static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		Player player1 = null;
		Player player2 = null;
		
		do {
			System.out.println("How many human players [1 or 2] ?");
			int numberOfPlayers = in.nextInt();
			if(numberOfPlayers == 1){
				player1 = new HumanPlayer();
				player2 = new AutomaticPlayer();
			}else if(numberOfPlayers == 2){
				player1 = new HumanPlayer();
				player2 = new HumanPlayer();
			}else{
				System.out.println("Invalid number of players!  Choose between 1 or 2");
			}
		}while(player1 == null && player2 == null);


		
		assignColorToPlayer(player1, player2);
		
		Player currentPlayer;
		// play game
		if(randomPick()){ // pick whose goes first
			currentPlayer = player1;
		}else{
			currentPlayer = player2;
		}
//		if(currentPlayer instanceof AutomaticPlayer){
			System.out.println(currentPlayer.name() + " goes first move!");
//		}
		
		String message = "";
		do{
			
			currentPlayer.move();
			int gameState = ConnectFourGame.getCurrentState();
			if (gameState == ConnectFourGame.RED_WON) {
				message = (currentPlayer.name() + " wins!");
				break;
			} else if (gameState == ConnectFourGame.YELLOW_WON) {
				message = (currentPlayer.name() + " wins!");
				break;
			} else if (gameState == ConnectFourGame.DRAW) {
				message = ("It's a Draw!");
				break;
			}



			currentPlayer = (currentPlayer.equals(player1)) ? player2 : player1;
		}while(true);
		ConnectFourGame.board.printBoard();
		System.out.println(message);
		System.out.println("Game Over!");

		

	}
	private static void assignColorToPlayer(Player player1, Player player2) {
		// init whose X and whose O
		if(randomPick()){
			player1.init(true); // player1 is RED
			player2.init(false); 
		}else{
			player1.init(false);
			player2.init(true); // player2 is RED
		}
		
		player1.setOpponent(player2);
		player2.setOpponent(player1);
	}
	private static boolean randomPick() {
		return ((int)(Math.random() * 10) & 1)  == 0;
	}
}
