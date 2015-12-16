package lab;

import java.awt.Toolkit;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ConnectFourGame {

	
	public static final int YELLOW_WON = 0;
	public static final int RED_WON = 1;
	public static final int DRAW = 0;
	public static Board board = new Board();
	
	private static Scanner in = new Scanner(System.in);
	private static int currentState = -1;
	private static int currentCol = -1;
//	public static Player player1 = null;
//	public static Player player2 = null;

	public static void playerMove(Player player) {
		
		boolean validInput = false;  // input validation
		board.printBoard();
		do {
			currentCol = -1;
			TimerTask task = new TimerTask(){
				public void run(){
					if( currentCol == -1 ){
						Toolkit.getDefaultToolkit().beep();
						System.out.println( "\nTime is up!!! \n" + player.name() + " loses! \nGame Over!!..." );
						System.exit( 0 );
					}
				}
			};
			
			
			Timer timer = new Timer();
			timer.schedule( task, 18*1000 ); // 18 seconds
			System.out.print(player.name() + ", Select a column [1 - 7] to drop token: ");
			currentCol = in.nextInt() - 1;
			timer.cancel();
			
			if (currentCol >= 0 && currentCol < Board.COLS ) {
				if(board.dropToken(player, currentCol)){

					player.getOpponent().inform(currentCol);
					validInput = true;  // input okay, exit loop
				}
			}
			if(validInput == false){
				System.out.println("This move at (" + (currentCol + 1)
						+ ") is not valid. Try again...");
			}
		} while (!validInput);  // repeat until input is valid
	}
	
	
	public static void automaticMove(Player player) {
		boolean validInput = false;  // input validation
		do {
			currentCol = -1;
			int column = ((AutomaticPlayer)player).getOpponentsLastMove();
			if(column == -1){
				currentCol = getRandomMove();
			}else{
				//Artificial Intelligence
				//test for vertical possible opponent win 
				int token = player.isRed()? Board.YELLOW : Board.RED;
				int row = 6 - board.getMap().get(column).size();
				
				testForWinningPlace(player, token);
				
				blockFourVertical(column, token, row);
				
				blockFourHorizontal(player, token);
				
				blockFourDiagonalDownward(player, token);
			
				blockFourDiagonalUpward(player, token);

				
				if(currentCol == -1){
					currentCol = getRandomMove();
				}
				
			}
			
			
			if (currentCol >= 0 && currentCol < Board.COLS ) {
				if(board.dropToken(player, currentCol)){
					player.getOpponent().inform(currentCol);
					validInput = true;  // input okay, exit loop
				}
			} else {
				System.out.println("This move at (" + (currentCol + 1)
						+ ") is not valid. Try again...");
			}
			
		}while (!validInput);  // repeat until input is valid
		
		
		

	}


	public static void testForWinningPlace(Player player, int token) {
		if(currentCol == -1){
			for(int i = 0 ; i < Board.ROWS ; i++){
				for(int j =0 ; j < Board.COLS ; j++){
					if(board.isEmptyCell(i,j)){
						//temporarily set to see if possible win 
						board.dropToken(player, j);
						int place = Board.ROWS - board.getMap().get(j).size();
						if(currentState != -1){
							currentCol = j;
							board.removeTestToken(player, j);
							board.updateBoard(place, j, Board.EMPTY);
							currentState = -1;
							break;
						}else{
							//
							board.removeTestToken(player, j);
							board.updateBoard(place, j, Board.EMPTY);
							currentState = -1;
						}
					}
				}
				if(currentCol != -1){
					break;
				}
			}
		}
	}


	private static void blockFourVertical(int column, int token, int row) {
		if( board.hasThreeVertically(token,  row, column) && row > 0){
			currentCol = column;
		}
	}


	private static void blockFourHorizontal(Player player, int token) {
		if(currentCol == -1){
			for(int i = 0 ; i < Board.ROWS ; i++){
				for(int j =0 ; j < Board.COLS ; j++){
					if(board.isEmptyCell(i,j)){
						//temporarily set to see if possible win 
						board.dropToken(player.getOpponent(), currentCol);
						if(board.hasSameHorizontally(token, i, j, 4)){
							currentCol = j;
							board.removeTestToken(player.getOpponent(), currentCol);
							break;
						}else{
							//
							board.removeTestToken(player.getOpponent(), currentCol);
						}
					}
				}
				if(currentCol != -1){
					break;
				}
			}
		}
	}


	public static void blockFourDiagonalDownward(Player player, int token) {
		if(currentCol == -1){
			for(int i = 0 ; i < Board.ROWS ; i++){
				for(int j =0 ; j < Board.COLS ; j++){
					if(board.isEmptyCell(i,j)){
						//temporarily set to see if possible win 
						board.dropToken(player.getOpponent(), j);
						
						int place = Board.ROWS - board.getMap().get(j).size();
						
//						if(board.hasFourInADiagonalDownward(token, place, j)){
						if(currentState != -1){
							currentCol = j;
							board.removeTestToken(player.getOpponent(), j);
							board.updateBoard(place, j, Board.EMPTY);
							currentState = -1;
							break;
						}else{
							//
							board.removeTestToken(player.getOpponent(), j);
							board.updateBoard(place, j, Board.EMPTY);
							currentState = -1;
						}
					}
				}
				if(currentCol != -1){
					break;
				}
			}
		}
	}


	public static void blockFourDiagonalUpward(Player player, int token) {
		if(currentCol == -1){
			for(int i = 0 ; i < Board.ROWS ; i++){
				for(int j =0 ; j < Board.COLS ; j++){
					if(board.isEmptyCell(i,j)){
						//temporarily set to see if possible win 
						board.dropToken(player.getOpponent(), j);
						int place = Board.ROWS - board.getMap().get(j).size();
						if(board.hasFourInADiagonalUpward(token, place, j)){
							currentCol = j;
							board.removeTestToken(player.getOpponent(), j);
							board.updateBoard(place, j, Board.EMPTY);
							break;
						}else{
							//
							board.removeTestToken(player.getOpponent(), j);
							board.updateBoard(place, j, Board.EMPTY);
						}
					}
				}
				if(currentCol != -1){
					break;
				}
			}
		}
	}
	
	
	
	public static void updateGame(int row, int column, int token) {
		
		if (hasWon(token, row, column)) {  // check if winning move
			currentState =((token == Board.YELLOW) ? YELLOW_WON : RED_WON);
		} else if (isDraw()) {  // check for draw
			currentState = DRAW;
		}
		
	}
	public static boolean hasWon(int token, int currentRow, int currentCol) {
		//System.out.println("hasWon");
		return (board.hasFourVertically( token,  currentRow,  currentCol) ||
				board.hasSameHorizontally( token,  currentRow,  currentCol, 4) ||
				board.hasFourInADiagonalDownward(token,  currentRow,  currentCol) ||
				board.hasFourInADiagonalUpward(token, currentRow, currentCol)
				);
	}
	
	public static boolean isDraw() {
	     return board.hasNoMoreEmptyCell();
	 }


	public static int getCurrentState() {
		return currentState;
	}


//	public static void setCurrentState(int currentState) {
//		currentState = currentState;
//	}
	
	private static int getRandomMove(){
		Random rn = new Random();
		int n = 7 ;
		int i = rn.nextInt() % n;
		int randomNum =  1 + i;
		if(randomNum > 6){
			return  getRandomMove();
		}
		return (Math.abs(randomNum));
		
	}
	
	
}
