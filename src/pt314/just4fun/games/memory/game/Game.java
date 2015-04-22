package pt314.just4fun.games.memory.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt314.just4fun.games.memory.player.Player;

public class Game {

    private Player player1;
    private Player player2;
    
    private Player currentPlayer;
    
    private List<Card> cards;

    // Indices of cards flipped by player on current turn
    private List<Integer> flippedCards;
    
    public Game(Player player1, Player player2, int numberOfCards) {
    	this.player1 = player1;
        this.player2 = player2;
        currentPlayer = player1;
        initCards(numberOfCards);
        restart();
    }
    
    private void initCards(int numberOfCards) {
    	if (numberOfCards % 2 != 0)
    		throw new IllegalArgumentException(
    				"Number of cards must be even: " + numberOfCards);
        cards = new ArrayList<Card>();
        for (int value = 1; value <= numberOfCards / 2; value++) {
			cards.add(new Card(value));
			cards.add(new Card(value));
		}
        Collections.shuffle(cards);
        flippedCards = new ArrayList<Integer>();
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
    
    public Card flipCard(int index) {
    	Card card = cards.get(index);
    	if (!flippedCards.contains(index)) {
    		flippedCards.add(index);
    		if (isCardMatch()) {
    			for (Integer i : flippedCards)
    				cards.get(i).setMatched();
    		}
    	}
    	return card;
    }
    
    public int getFlippedCardCount() {
    	return flippedCards.size();
    }
    
    public List<Integer> getFlippedCardIndices() {
    	return Collections.unmodifiableList(flippedCards);
    }

    public boolean isCardMatch() {
    	if (flippedCards.size() < 2)
    		return false;
		Card card1 = cards.get(flippedCards.get(0));
		Card card2 = cards.get(flippedCards.get(1));
		return card1.getValue() == card2.getValue();
    }
    
    public void endTurn() {
    	flippedCards.clear();
    }
    
    public boolean isOver() {
    	for (Card card : cards)
			if (!card.isMatched())
				return false;
		return true;
    }
    
    public void restart() {
        player1.resetScore();
        player2.resetScore();
        player1.resetMoves();
        player2.resetMoves();
    	currentPlayer = player1;
    }
}
