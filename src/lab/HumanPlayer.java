package lab;

public class HumanPlayer implements Player{

	Boolean colorRed;
	String name = "Player";
	Player opponent;
	int opponentsLastMove = -1;
	
	
	@Override
	public void init(Boolean color) {
		colorRed = color;
		
	}

	@Override
	public String name() {
		return name + " " + (isRed()?"Red(1)" : "Yellow(0)");
	}

	@Override
	public int move() {
		ConnectFourGame.playerMove(this);
		return 1;
	}

	@Override
	public void inform(int i) {
		System.out.println("\n"+opponent.name() + " last drop is in column :" + (i+1));
		opponentsLastMove = i;
	}

	@Override
	public Boolean isRed() {
		
		return colorRed;
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
