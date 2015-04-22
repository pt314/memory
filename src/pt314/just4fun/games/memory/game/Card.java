package pt314.just4fun.games.memory.game;

public class Card {

	private int value;
	
	private boolean matched;
	
	public Card(int value) {
		this.value = value;
		this.matched = false;
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean isMatched() {
		return matched;
	}
	
	public void setMatched() {
		this.matched = true;
	}
}
