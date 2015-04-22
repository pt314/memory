package pt314.just4fun.games.memory.player;


public class Player {

	private PlayerTheme theme;
	
	private int wins;
	
	private int losses;
	
	private int ties;
	
	private int moves; // moves on current game...
	
	private int score; // score on current game...
	
	public Player(PlayerTheme theme) {
		super();
		setTheme(theme);
		resetScores();
		resetMoves();
		resetScore();
	}
	
	public void setTheme(PlayerTheme theme) {
		this.theme = theme;
	}
	
	public PlayerTheme getTheme() {
		return theme;
	}
	
	public void incrementWins() {
		wins++;
	}

	public int getWins() {
		return wins;
	}
	
	public void incrementLosses() {
		losses++;
	}

	public int getLosses() {
		return losses;
	}
	
	public void incrementTies() {
		ties++;
	}

	public int getTies() {
		return ties;
	}

	public void incrementMoves() {
		moves++;
	}

	public int getMoves() {
		return moves;
	}

	public void incrementScore(int extra) {
		score += extra;
	}

	public int getScore() {
		return score;
	}

	public void resetScores() {
		wins = 0;
		losses = 0;
		ties = 0;
	}
	
	public void resetMoves() {
		moves = 0;
	}
	
	public void resetScore() {
		score = 0;
	}
}
