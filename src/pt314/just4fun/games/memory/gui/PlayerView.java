package pt314.just4fun.games.memory.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import pt314.just4fun.games.memory.MemoryGame;
import pt314.just4fun.games.memory.player.Player;
import pt314.just4fun.games.memory.player.PlayerTheme;
import pt314.just4fun.games.memory.player.PlayerThemeManager;

// TODO: Use JLayeredPane for handling the background.
// https://docs.oracle.com/javase/tutorial/uiswing/components/layeredpane.html

public class PlayerView extends JPanel {
	
	private static final int WIDTH = 100;	// in pixels
	private static final int HEIGHT = 600;	// in pixels
	
	private MemoryGame game; // the game
	
	private Player player;   // the player
	
	private JLabel imageLabel; // player image
	
	private JLabel backgroundLabel; // background image
	
	private JComboBox<PlayerTheme> themeSelector; // for selecting player theme 
	
    private JTextArea playerSummary; // player name, wins, losses, ties

    private JLabel movesLabel; // number of moves
    
    private JPanel scorePanel; // displays score bar

    private JLabel[] scoreLabels; // used by scorePanel
    
    private JLabel playerTurn; // indicates if it's the player's turn to play
    
	public PlayerView(MemoryGame game, Player player) {
		super();
		
		this.game = game;
		this.player = player;
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setSize(new Dimension(WIDTH, HEIGHT));
		
		init();
		
		update();
	}
	
	private void init() {

		// use absolute positioning
		setLayout(null);
		
		// player image
		imageLabel = new JLabel();
		imageLabel.setSize(90, 90);
		imageLabel.setLocation(5, 10);
		
		// background image
		backgroundLabel = new JLabel();
		backgroundLabel.setSize(100, 600);
		backgroundLabel.setLocation(0, 0);
		
		// theme selector
		themeSelector = new JComboBox<PlayerTheme>(PlayerThemeManager.getThemes());
		themeSelector.setSize(90, 20);
		themeSelector.setLocation(5, 100);
		themeSelector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerTheme theme = (PlayerTheme) themeSelector.getSelectedItem();
				player.setTheme(theme);
				update();
			}
		});

        // player summary
		playerSummary = new JTextArea();
		playerSummary.setSize(90, 100);
		playerSummary.setLocation(5, 130);
		playerSummary.setEditable(false);
		playerSummary.setFont(new Font("Calibre", Font.BOLD, 12));

		// number of moves
		movesLabel = new JLabel();
		movesLabel.setSize(90, 20);
		movesLabel.setLocation(5, 240);
		movesLabel.setForeground(Color.WHITE);

		// score bar
		scorePanel = new JPanel();
		scorePanel.setSize(20, 300);
		scorePanel.setLocation(35, 270);
		scorePanel.setBackground(Color.WHITE);
		resetScoreBoard();
		
		// player's turn?
		playerTurn = new JLabel();
		playerTurn.setSize(90, 20);
        playerTurn.setLocation(5, 570);
        playerTurn.setForeground(Color.WHITE);
		
		// add all parts to panel
		add(imageLabel);
		add(themeSelector);
		add(playerSummary);
		add(movesLabel);
		add(scorePanel);
		add(playerTurn);
		add(backgroundLabel);
	}
	
	public void update() {
		imageLabel.setIcon( player.getTheme().getImageIcon() );
		backgroundLabel.setIcon( player.getTheme().getBackgroundIcon() );
		themeSelector.setSelectedItem( player.getTheme() );
		playerSummary.setText( getPlayerText(player) );
        movesLabel.setText("Moves: " + player.getMoves());
        
        // update score bar
        for(int i = 0; i < player.getScore(); i++)
            scoreLabels[i].setBackground(Color.BLACK);
        
        // update player's turn
        if (player == game.getCurrentPlayer())
        	playerTurn.setText("PLAYER TURN");
        else
        	playerTurn.setText("");
	}

    private String getPlayerText(Player player) {
    	String playerName = player.getTheme().getPlayerName();
    	String text = playerName + " PLAYER\n";
    	text += "\nWins:   " + player.getWins();
    	text += "\nLosses:  " + player.getLosses();
    	text += "\nTies:   " + player.getTies();
    	return text;
    }
    
    public void resetScoreBoard() {
    	
    	scorePanel.removeAll();

		int scoreBoardSize = game.getNumberOfCards() * 2 - 1;
		scoreLabels = new JLabel[scoreBoardSize];
		scorePanel.setLayout(new GridLayout(scoreBoardSize, 1));
		for (int i = 0; i < scoreBoardSize; i++) {
			JLabel scoreLabel = new JLabel();
			scoreLabel.setBackground(Color.WHITE);
			scoreLabel.setOpaque(true);
			scoreLabel.setHorizontalAlignment(JLabel.CENTER);
			scoreLabel.setFont(new Font("sansserif", Font.BOLD, 8));
			scoreLabel.setBorder(BorderFactory.createEmptyBorder());
			// TODO: use constant, check number of cards instead?
			if (scoreBoardSize <= 32) {
				scoreLabel.setText(String.valueOf(i + 1));
			}
			scorePanel.add(scoreLabel);
			scoreLabels[i] = scoreLabel;
		}
    	
    	scorePanel.validate();
    	scorePanel.updateUI();
    }

}
