package pt314.just4fun.games.memory.game;

import java.util.ArrayList;
import java.util.List;

import pt314.just4fun.games.memory.player.Player;

public class Game {

    private Player player1;
    private Player player2;
    
    private Player currentPlayer;
    
    private List<Card> cards;

    public Game(Player player1, Player player2, int numberOfCards) {
    	this.player1 = player1;
        this.player2 = player2;
        currentPlayer = player1;
        initCards(numberOfCards);
    }
    
    private void initCards(int numberOfCards) {
    	if (numberOfCards % 2 != 0)
    		throw new IllegalArgumentException(
    				"Number of cards must be even: " + numberOfCards);
        cards = new ArrayList<Card>();
        for (int value = 1; value < numberOfCards / 2; value++) {
			cards.add(new Card(value));
			cards.add(new Card(value));
		} 
    }
    
    public Player getPlayer1() {
    	return player1;
    }
    
    public Player getPlayer2() {
    	return player2;
    }

    public Player getCurrentPlayer() {
    	return currentPlayer;
    }

    public void switchPlayer() {
    	currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
    
    public void restart() {
        player1.resetScore();
        player2.resetScore();
        player1.resetMoves();
        player2.resetMoves();
    	currentPlayer = player1;
    }
}
