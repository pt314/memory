package pt314.just4fun.games.memory.game;

import pt314.just4fun.games.memory.player.Player;
import pt314.just4fun.games.memory.player.PlayerThemeManager;

public class Game {

    private Player player1;
    private Player player2;

    private Player currentPlayer;

    public Game(Player player1, Player player2) {
    	this.player1 = player1;
        this.player2 = player2;
        currentPlayer = player1;
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
