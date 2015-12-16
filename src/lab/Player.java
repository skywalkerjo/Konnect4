package lab;

public interface Player {
	void init (Boolean color); 
	String name (); 
	int move (); 
	void inform (int i);
	Boolean isRed();
	Player getOpponent(); 
	void setOpponent(Player opponent); 
}
