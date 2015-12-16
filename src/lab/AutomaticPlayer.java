package lab;


public class AutomaticPlayer implements Player{

	Boolean colorRed;
	int opponentsLastMove = -1;
	String name = "Automatic";
	Player opponent;
	
	@Override
	public void init(Boolean color) {
		this.colorRed = color;
		
	}

	@Override
	public String name() {
		return name + " " + (isRed()?"Red(1)" : "Yellow(0)");
	}

	@Override
	public int move() {
		ConnectFourGame.automaticMove(this);
		return 1;
	}

	@Override
	public void inform(int i) {
		System.out.println("\n"+opponent.name() + " last drop is in column :" + (i+1));
		opponentsLastMove = i;
		ConnectFourGame.board.printBoard();
	}

	@Override
	public Boolean isRed() {
		// TODO Auto-generated method stub
		return colorRed;
	}

	public int getOpponentsLastMove() {
		return opponentsLastMove;
	}

	@Override
	public Player getOpponent() {
		
		return opponent;
	}

	@Override
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
		
	}

}
