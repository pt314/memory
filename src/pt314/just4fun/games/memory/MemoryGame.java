package pt314.just4fun.games.memory;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.Timer;
import javax.swing.*;

import pt314.just4fun.games.memory.game.Card;
import pt314.just4fun.games.memory.game.Game;
import pt314.just4fun.games.memory.gui.BoardView;
import pt314.just4fun.games.memory.gui.PlayerView;
import pt314.just4fun.games.memory.player.Player;
import pt314.just4fun.games.memory.player.PlayerThemeManager;

/**
 * Memory game by student (personal information removed).
 * 
 * Mini-project from CS 2410, Introduction to GUI Development in Java, Spring 2014.
 * 
 * Images and sounds not available on this version.
 * 
 * Questions:
 * - What do you think about coupling and cohesion of this class?
 * - How would you improve the design and code?
 */
public class MemoryGame extends JFrame implements ActionListener {

    public static final String BASE_PATH = "res//";
    
    public static final String IMG_PATH = BASE_PATH + "images//";

    // The game
    private Game game;

    // Player stuff...
    private Player player1 = new Player(PlayerThemeManager.getThemes()[0]);
    private Player player2 = new Player(PlayerThemeManager.getThemes()[1]);
    
    // Player and board views
    private PlayerView player1View;
    private PlayerView player2View;
    private BoardView boardView = new BoardView(this);
    
    // Panels and Image Labels
    private JPanel middlePanel = new JPanel();
    // Game Area
    private JButton [] cardButtons;

    // Images
    private ImageIcon [] cardIcon = {new ImageIcon(IMG_PATH + "image1.png"), new ImageIcon(IMG_PATH + "image2.png"),
        new ImageIcon(IMG_PATH + "image3.png"), new ImageIcon(IMG_PATH + "image4.png"), new ImageIcon(IMG_PATH + "image5.png"),
        new ImageIcon(IMG_PATH + "image6.png"), new ImageIcon(IMG_PATH + "image7.png"), new ImageIcon(IMG_PATH + "image8.png"),
        new ImageIcon(IMG_PATH + "image9.png"), new ImageIcon(IMG_PATH + "image10.png"), new ImageIcon(IMG_PATH + "image11.png"),
        new ImageIcon(IMG_PATH + "image12.png"), new ImageIcon(IMG_PATH + "image13.png"), new ImageIcon(IMG_PATH + "image14.png"),
        new ImageIcon(IMG_PATH + "image15.png"), new ImageIcon(IMG_PATH + "image16.png"), new ImageIcon(IMG_PATH + "image17.png"),
        new ImageIcon(IMG_PATH + "image18.png")};

    private ImageIcon background = new ImageIcon(IMG_PATH + "background.png");

    private Container pane = getContentPane();

    public MemoryGame(int rows, int cols) {
        super("Memory Game");
        setSize(806, 652);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMenus();

        pane.setLayout(new BorderLayout());

        int numCards = rows * cols;
        cardButtons = new JButton[numCards];
        middlePanel.setPreferredSize(new Dimension(600, 600));
        middlePanel.setSize(600, 600);

        game = new Game(player1, player2, rows * cols);
        
        player1View = new PlayerView(this, player1);
        player2View = new PlayerView(this, player2);

        pane.add(player1View, BorderLayout.WEST);
        pane.add(player2View, BorderLayout.EAST);
        pane.add(middlePanel, BorderLayout.CENTER);

        updatePlayerViews();
        
        newBoardPanel(rows, cols);

        SoundPlayer.music();
        setVisible(true);
    }
    
    private void initMenus() {
        
    	JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu gameMenu = createGameMenu();
        JMenu helpMenu = createHelpMenu();
        
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
    }

    private JMenu createGameMenu() {
        JMenu gameMenu = new JMenu("Game");

        JMenu newGameMenu = createNewGameMenu();
        gameMenu.add(newGameMenu);
        
        JMenuItem restartGameMenuItem = new JMenuItem("Restart Game");
        restartGameMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	            int choice = JOptionPane.showConfirmDialog(null,
	            		"Would you like to restart the game?",
	                    "Restart Dialog", JOptionPane.YES_NO_OPTION);
	            if (choice == JOptionPane.YES_OPTION)
	            	restartGame();
			}
		});
        gameMenu.add(restartGameMenuItem);
        
        JMenuItem resetScoresMenuItem = new JMenuItem("Reset Scores");
        resetScoresMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	            int choice = JOptionPane.showConfirmDialog(null,
	            		"Would you like to delete win/lose records?",
	                    "Delete Scores Dialog", JOptionPane.YES_NO_OPTION);
	            if (choice == JOptionPane.YES_OPTION) {
	            	resetScores();
	                restartGame();
	            }
			}
		});
        gameMenu.add(resetScoresMenuItem);
        
        return gameMenu;
    }
    
    private JMenu createNewGameMenu() {
    	
    	class NewGameAction extends AbstractAction {
    		
    		private int rows, cols; // board size
    		
    		NewGameAction(int rows, int cols) {
    			this.rows = rows;
    			this.cols = cols;
    		}
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "Would you like to make a new "
						+ rows + " x " + cols + " game?";
	            int choice = JOptionPane.showConfirmDialog(null,
	            		message, "New Game Dialog", JOptionPane.YES_NO_OPTION);

	            System.out.println(choice);
	            if(choice == JOptionPane.YES_OPTION){
	                newBoardPanel(rows, cols);
	            }
			}
    	}
    	
    	JMenu newGameMenu = new JMenu("New Game");
    	
        JMenuItem newGame4x4 = new JMenuItem("4 x 4");
        newGame4x4.addActionListener(new NewGameAction(4, 4));
        newGameMenu.add(newGame4x4);
        
        JMenuItem newGame4x6 = new JMenuItem("4 x 6");
        newGame4x6.addActionListener(new NewGameAction(4, 6));
        newGameMenu.add(newGame4x6);

        JMenuItem newGame6x6 = new JMenuItem("6 x 6");
        newGame6x6.addActionListener(new NewGameAction(6, 6));
        newGameMenu.add(newGame6x6);

    	return newGameMenu;
    }

    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");

        JMenuItem rulesMenuItem = new JMenuItem("Rules");
        rulesMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	            JOptionPane.showMessageDialog(null, "How to Play: \n1. Left side is the first player, right side the second" +
	                    "\n2. Take turns flipping cards in the center \n3. The player with the highest score at the end wins" +
	                    "\n\nScoring: \n1. The white bars beneath each player is the score bar \n2. A point is added by matching " +
	                    "two cards of the same value together \n3. If another match is made in a row, then another point is added " +
	                    "\n4. If the card value is divisible by 2, then another point is added to the score \n5. If the card " +
	                    "value is divisible by 3, then another two points are added to the score \n6. If the card value is " +
	                    "divisible by 6, then three more points are added to the score \n\nPlayer Setup: \n1. " +
	                    "Each player can choose between five elements: \n          Fire     Water     Electricity     " +
	                    "Moss     Air \n2. Each element has it's own theme and card flip sound effect", "Rules",
	                    JOptionPane.PLAIN_MESSAGE);
			}
		});
        helpMenu.add(rulesMenuItem);
        
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	            JOptionPane.showMessageDialog(null, "[A number] \n[Student name] \n" +
	                    "[student email]\n", "About Information", JOptionPane.PLAIN_MESSAGE);
			}
		});
        helpMenu.add(aboutMenuItem);
        
        return helpMenu;
    }
    


    public int getNumberOfCards() {
    	return game.getNumberOfCards();
    }
    
    public Player getCurrentPlayer() {
    	return game.getCurrentPlayer();
    }
   


	// Card Click
    public void actionPerformed(ActionEvent e) {
    	if (game.getFlippedCardCount() == 2)
    		return;

    	JButton theCard = (JButton) e.getSource();
    	int buttonIndex = 0;
    	for (int i = 0; i < cardButtons.length; i++) {
			if (cardButtons[i] == theCard)
				buttonIndex = i;
		}

    	SoundPlayer.playSound( getCurrentPlayer().getTheme().getSoundFile() );

   		Card card = revealCard(buttonIndex);
        updatePlayerViews();
    	
    	if (game.getFlippedCardCount() == 2) {
    		if (game.isCardMatch()) {
                if (game.isOver()) {
                	updatePlayerScores();
                    showGameResult();
                    restartGame();
                }
	            game.endPlay();
    		}
            else { // not a match
                disableAllCards();
                delayAfterNonMatch();
                game.switchPlayer();
            }
    	}
        updatePlayerViews();
    }

    private Card revealCard(int index) {
    	Card card = game.flipCard(index);
    	int cardValue = card.getValue();
    	JButton cardButton = cardButtons[index];
        cardButton.setEnabled(false);
        cardButton.setIcon(cardIcon[cardValue - 1]);
        cardButton.setDisabledIcon(cardIcon[cardValue - 1]);
        return card;
    }
    
    private void disableAllCards() {
        for(int j = 0; j < cardButtons.length; j++){
            cardButtons[j].setEnabled(false);
            if(cardButtons[j].getIcon() == background)
            	cardButtons[j].setDisabledIcon(background);
        }
    }
    
    private void enableUnmatchedCards() {
        for(int j = 0; j < cardButtons.length; j++)
            if(cardButtons[j].getIcon() == background)
            	cardButtons[j].setEnabled(true);
    }

	private void delayAfterNonMatch() {
		Timer setTimer = new Timer(2000, new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
		    	List<Integer> flippedCards = game.getFlippedCardIndices();
		    	int cardIdx1 = flippedCards.get(0);
		    	int cardIdx2 = flippedCards.get(1);
		        cardButtons[cardIdx1].setEnabled(true);
		        cardButtons[cardIdx1].setIcon(background);
		        cardButtons[cardIdx2].setEnabled(true);
		        cardButtons[cardIdx2].setIcon(background);
	            game.endPlay();
		        enableUnmatchedCards();
		    }
		});
		setTimer.setRepeats(false);
		setTimer.start();
	}


	private void updatePlayerScores() {
    	Player winner = game.getWinner();
    	if (winner != null) {
            winner.incrementWins();
            Player loser = (winner == player1) ? player2 : player1;
            loser.incrementLosses();
    	}
    	else {
            player1.incrementTies();
            player2.incrementTies();
    	}
        resetPlayerViews();
        updatePlayerViews();
	}
	
	private void showGameResult(){
    	Player winner = game.getWinner();
    	if (winner != null) {
            ImageIcon playerIcon = winner.getTheme().getImageIcon();
            String msg = "Congratulations! \n";
            msg += "Player ";
            msg += winner == player1 ? "One" : "Two";
            msg += " Wins!";
            JOptionPane.showMessageDialog(null, msg,
                    "Win Dialog", JOptionPane.INFORMATION_MESSAGE, playerIcon);
    	}
        else {
            JOptionPane.showMessageDialog(null, "Tied Game", "Tie Dialog", JOptionPane.PLAIN_MESSAGE);
        }
    }

    // restart the game
    public void restartGame(){
        for(int i=0;i<cardButtons.length;i++){
            cardButtons[i].setEnabled(true);
            cardButtons[i].setIcon(background);
        }

        game = new Game(player1, player2, game.getNumberOfCards());

        resetPlayerViews();
        updatePlayerViews();
    }

    // reset win, losses, draws
    private void resetScores() {
        game.getPlayer1().resetScores();
        game.getPlayer2().resetScores();
    }

    public void updatePlayerViews(){
        player1View.update();
        player2View.update();
    }
    
    public void resetPlayerViews(){
        player1View.resetScoreBoard();
        player2View.resetScoreBoard();
    }
    
    // updating for a new board
    public void newBoardPanel(int rows, int cols){

    	middlePanel.removeAll();
        middlePanel.setLayout(new GridLayout(rows, cols));

        int numCards = rows * cols;
        cardButtons = new JButton[numCards];

        for(int i = 0; i < cardButtons.length; i++){
            cardButtons[i] = new JButton();
            cardButtons[i].setIcon(background);
            cardButtons[i].setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
            cardButtons[i].addActionListener(this);
        }

        for(int i = 0; i < cardButtons.length; i++)
        	middlePanel.add(cardButtons[i]);

        middlePanel.validate();
        middlePanel.updateUI();

        resetPlayerViews();
        
        restartGame();
    }

    
    

    
    
    // beginning dialog
    public static void newGameDialog() {
    	int rows = 0;
    	int cols = 0;
    	
        Object[] choice = {"Easy", "Medium", "Hard"};
        String s = (String)JOptionPane.showInputDialog(null, "Please Pick a Difficulty Level: ",
                "Start Game", JOptionPane.PLAIN_MESSAGE, null, choice, "Easy");
        if((s != null) && (s.length() > 0)){
            if(s == "Easy") {
            	rows = 4;
            	cols = 4;
            }
            if(s == "Medium") {
            	rows = 4;
            	cols = 6;
            }
            if(s == "Hard") {
            	rows = 6;
            	cols = 6;
            }
        }
        else {
            System.exit(0);
        }
        new MemoryGame(rows, cols);
    }

    // main
    public static void main(String[] args){
        newGameDialog();
    }
}