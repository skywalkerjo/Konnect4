package lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Board {
	public static final int ROWS = 6;
	public static final int COLS = 7;
	public static final int EMPTY = -1;
	public static final int RED = 1;
	public static final int YELLOW = 0;
	public static int[][] gameBoard = new int[ROWS][COLS];
	
	public static int[][] getGameBoard() {
		return gameBoard;
	}

	private HashMap<Integer, LinkedList<Player>> map = new HashMap<Integer, LinkedList<Player>>();
	
	
	public static void main(String[] args) {
		// Testing

		Board b = new Board();

		b.printBoard();

		

	}

	public Board(){
		initBoard();
	}

	public  void initBoard() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				gameBoard[row][col] = EMPTY; 
			}
		}

	}

	public  void printBoard() {
		System.out.println();
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				printCell(gameBoard[row][col]); 
				if (col != COLS - 1) {
					System.out.print("|");   
				}
			}
			
			System.out.println("\n---------------------------"); 
		}
		System.out.println("---------------------------"); 
		System.out.println();
	}

	public  void printCell(int content) {
		switch (content) {
		case EMPTY:  	System.out.print("   "); 
		break;
		case RED: 		System.out.print(" 1 "); 
		break;
		case YELLOW:  	System.out.print(" 0 "); 
		break;
		}
	}

	public void updateBoard(int row, int col, int token){
		gameBoard[row][col] = token;
//		printBoard();
	}


	public boolean isEmptyCell(int row, int col) {
		return gameBoard[row][col] == EMPTY;
	}


	public boolean hasFourVertically(int token, int currentRow, int currentCol) {
		return hasSameTokenVetically(token, currentRow, currentCol, 4);
	}
	
	public boolean hasThreeVertically(int token, int currentRow, int currentCol) {
		return hasSameTokenVetically(token, currentRow, currentCol, 3);
	}

	private boolean hasSameTokenVetically(int token, int currentRow, int currentCol, int targetCount) {
		int j = currentCol;
		int count = 0;

		for(int i = currentRow ; i < gameBoard.length ; i++ ){

			if(gameBoard[i][j] == token){
				count++;
				if(count == targetCount ){
					//System.out.println("hasSameTokenVetically " + token);
					return true;
				}
			}else{
				break;
			}

		}

		return false;
	}


	public boolean hasSameHorizontally(int token, int currentRow, int currentCol, int target) {


		int j = currentRow;
		int count = 0;
		for(int i = currentCol ; i < (gameBoard[0]).length ; i++ ){
			if(i >= 0 ){
				if(gameBoard[j][i] == token){
					count++;
					if(count == target ){ 
						//System.out.println("hasSameHorizontally " + token);
						return true;
					}
				}else{
					break;
				}
			}
		}
		if(currentCol > 0){
			//shift left and test
			int beforeCol = currentCol - 1;
			return hasSameHorizontally( token,  currentRow,   beforeCol, target);
		}

		return false;
	}
	
	

	
	


	public boolean hasFourInADiagonalDownward(int token, int currentRow,  int currentCol) {


		// downward right test
		int j = currentRow;
		int count = 0;
		for(int i = currentCol ; i < (gameBoard[0]).length ; i++ ){
			if((i < COLS) && (j < ROWS)){
				if(gameBoard[j][i] == token){
					count++;
					j++;
					if(count == 4 ){ //&& (Math.abs(j - currentRow) == 4)){
						//System.out.println("hasFourInADiagonalDownward " + token);
						return true;
					}
				}else{
					break;
				}
			}
		}
		if(currentRow > 0){
			//shift upward and test
			int beforeRow = currentRow - 1;
			int beforeCol = currentCol - 1;
			if(beforeRow > -1 && beforeCol > -1){
				return hasFourInADiagonalDownward( token,  beforeRow,   beforeCol);
			}
		}

		return false;
	}

	public boolean hasFourInADiagonalUpward(int token, int currentRow,  int currentCol) {


		// downward right test
		int j = currentRow;
		int count = 0;
		for(int i = currentCol ; i < (gameBoard[0]).length ; i++ ){
			if((i < COLS) && (j < ROWS && j > -1)){
				if(gameBoard[j][i] == token){
					count++;
					j--;
					if(count == 4 ){// && (Math.abs(j - currentRow) == 4)){
						//System.out.println("hasFourInADiagonalUpward " + token);
						return true;
					}
				}else{
					break;
				}
			}
		}
		if(currentRow < ROWS){
			//shift downward and test
			int beforeRow = currentRow + 1;
			int beforeCol = currentCol - 1;
			if( beforeCol > -1){
				return hasFourInADiagonalUpward( token,  beforeRow,   beforeCol);
			}
		}

		return false;
	}

	public boolean hasNoMoreEmptyCell() {

		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				if (gameBoard[row][col] == EMPTY) {
					return false;  
				}
			}
		}
		return true;  // no more empty cell
	}

	public boolean dropToken(Player player, int column) {
		boolean boolValue = false;
		
		if(column > -1 && column < 7){
			LinkedList<Player> list = null;
			if(map.containsKey(column)){
				list = map.get(column);
				if(list.size() < 6){
					list.add(player);
					boolValue = true;
				}
			}else{
				list = new LinkedList<Player>();
				list.add(player);
				map.put(column, list);
				boolValue = true;
			}
			
			if(boolValue == true){
				int row = 6 - list.size();
				int token = player.isRed()? RED : YELLOW;
				if(row > -1){
					updateBoard(row, column, token);
					ConnectFourGame.updateGame(row, column, token);
					return true;
				}
			}
		}
		return boolValue;// return invalid
	}

	public HashMap<Integer, LinkedList<Player>> getMap() {
		return map;
	}

	public void setMap(HashMap<Integer, LinkedList<Player>> map) {
		this.map = map;
	}


	public void removeTestToken(Player opponent, int column) {
		LinkedList<Player> list = map.get(column);
		if(list != null){
			list.remove(opponent);
		}
	}
	
	
}
