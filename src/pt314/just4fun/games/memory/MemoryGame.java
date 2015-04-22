package pt314.just4fun.games.memory;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.Timer;
import javax.swing.*;

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

    private static int counter = 0;
    private static int numPairs = 0;
    
    private static boolean addScore = false;
    public static int numCards;

    private Timer setTimer;

    // Player stuff...
    private Player player1 = new Player(PlayerThemeManager.getThemes()[0]);
    private Player player2 = new Player(PlayerThemeManager.getThemes()[1]);
    private Player currentPlayer = player1;
    
    // Player and board views
    private PlayerView player1View = new PlayerView(this, player1);
    private PlayerView player2View = new PlayerView(this, player2);
    private BoardView boardView = new BoardView(this);
    
    // Panels and Image Labels
    private JPanel middlePanel = new JPanel();

    // Game Area
    private JButton [] card = new JButton[numCards];

    // Images
    private ImageIcon [] cardIcon = {new ImageIcon(IMG_PATH + "image1.png"), new ImageIcon(IMG_PATH + "image2.png"),
        new ImageIcon(IMG_PATH + "image3.png"), new ImageIcon(IMG_PATH + "image4.png"), new ImageIcon(IMG_PATH + "image5.png"),
        new ImageIcon(IMG_PATH + "image6.png"), new ImageIcon(IMG_PATH + "image7.png"), new ImageIcon(IMG_PATH + "image8.png"),
        new ImageIcon(IMG_PATH + "image9.png"), new ImageIcon(IMG_PATH + "image10.png"), new ImageIcon(IMG_PATH + "image11.png"),
        new ImageIcon(IMG_PATH + "image12.png"), new ImageIcon(IMG_PATH + "image13.png"), new ImageIcon(IMG_PATH + "image14.png"),
        new ImageIcon(IMG_PATH + "image15.png"), new ImageIcon(IMG_PATH + "image16.png"), new ImageIcon(IMG_PATH + "image17.png"),
        new ImageIcon(IMG_PATH + "image18.png")};

    private ImageIcon background = new ImageIcon(IMG_PATH + "background.png");

    // Card Information
    private ArrayList<Integer> cardList = new ArrayList<Integer>();
    private int [] buttonID = new int[2];
    private int [] buttonVal = new int[2];

    private Container pane = getContentPane();

    public MemoryGame() {
        super("Memory Game");
        setSize(806, 652);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMenus();

        pane.setLayout(new BorderLayout());

        middlePanel.setPreferredSize(new Dimension(600, 600));
        middlePanel.setSize(600, 600);

        pane.add(player1View, BorderLayout.WEST);
        pane.add(player2View, BorderLayout.EAST);
        pane.add(middlePanel, BorderLayout.CENTER);

        updatePlayerViews();
        
        // board
        

        newBoardPanel(4, 6);

        Collections.shuffle(cardList);

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
	                numCards = rows * cols;
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
    	return numCards;
    }
    
    public Player getCurrentPlayer() {
    	return currentPlayer;
    }
   
    public void switchPlayer() {
    	currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
   
    public void actionPerformed(ActionEvent e) {
        // Card Click
        for(int i=0;i<card.length;i++){
            if(card[i] == e.getSource()){
                //card[i].setIcon(null);
                //card[i].setText("" + cardList.get(i));
                //card[i].setBackground(Color.WHITE);
                
            	card[i].setEnabled(false);
                counter++;

            	SoundPlayer.playSound( getCurrentPlayer().getTheme().getSoundFile() );
            	getCurrentPlayer().incrementMoves();

                updatePlayerViews();

                // first card selected
                if(counter == 1){
                    buttonID[0] = i;
                    buttonVal[0] = cardList.get(i);
                    card[i].setIcon(cardIcon[buttonVal[0] - 1]);
                    card[i].setDisabledIcon(cardIcon[buttonVal[0]-1]);
                }

                // second card selected
                if(counter == 2){
                    buttonID[1] = i;
                    buttonVal[1] = cardList.get(i);

                    card[buttonID[0]].setEnabled(false);
                    card[buttonID[1]].setEnabled(false);

                    card[i].setIcon(cardIcon[buttonVal[1]-1]);
                    card[i].setDisabledIcon(cardIcon[buttonVal[1] - 1]);

                    if(buttonVal[0] == buttonVal[1]){
                    	int extraScore = 0;
                        if(buttonVal[1] % 3 == 0)
                        	extraScore += 2;
                        if(buttonVal[1] % 2 == 0)
                        	extraScore++;
                        if(addScore)
                        	extraScore++;
                        extraScore++;
                        getCurrentPlayer().incrementScore(extraScore);
                        
                        updatePlayerViews();

                        addScore = true;

                        numPairs++;
                        if(numPairs == numCards/2){
                            checkWin();
                            restartGame();
                        }
                    } else {
                        addScore = false;
                        updatePlayerViews();

                        for(int j=0;j<card.length;j++){
                            card[j].setEnabled(false);
                            if(card[j].getIcon() == background) card[j].setDisabledIcon(background);
                        }

                        setTimer = new Timer(2000, (ActionListener) new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent arg0) {
                                for(int j=0;j<card.length;j++){
                                    if(card[j].getIcon() == background) card[j].setEnabled(true);
                                }

                                card[buttonID[0]].setEnabled(true);
                                card[buttonID[0]].setIcon(background);
                                card[buttonID[1]].setEnabled(true);
                                card[buttonID[1]].setIcon(background);

                                setTimer.stop();
                                setTimer = null;
                            }
                        });
                        setTimer.start();
                    }
                    counter = 0;
                    switchPlayer();
                    updatePlayerViews();
                }
            }
        }
    }

    // check to see who won
    public void checkWin(){
        if(player1.getScore() > player2.getScore()){
            player1.incrementWins();
            player2.incrementLosses();
            ImageIcon player1Icon = player1.getTheme().getImageIcon();
            JOptionPane.showMessageDialog(null, "Congratulations! \nPlayer One Wins!",
                    "Win Dialog", JOptionPane.INFORMATION_MESSAGE, player1Icon);
        }
        else if(player2.getScore() > player1.getScore()){
            player2.incrementWins();
            player1.incrementLosses();
            ImageIcon player2Icon = player2.getTheme().getImageIcon();
            JOptionPane.showMessageDialog(null, "Congratulations! \nPlayer Two Wins!",
                    "Win Dialog", JOptionPane.INFORMATION_MESSAGE, player2Icon);
        }
        else if(player2.getScore() == player1.getScore()){
            player1.incrementTies();
            player2.incrementTies();
            JOptionPane.showMessageDialog(null, "Tied Game", "Tie Dialog", JOptionPane.PLAIN_MESSAGE);
        }

        resetPlayerViews();
        updatePlayerViews();
    }

    // restart the game
    public void restartGame(){
        Collections.shuffle(cardList);
        for(int i=0;i<card.length;i++){
            card[i].setEnabled(true);
            card[i].setIcon(background);
        }

        currentPlayer = player1;
        numPairs = 0;

        player1.resetScore();
        player2.resetScore();
        player1.resetMoves();
        player2.resetMoves();
        
        resetPlayerViews();
        updatePlayerViews();
    }

    // reset win, losses, draws
    private void resetScores() {
        player1.resetScores();
        player2.resetScores();
    }

    // beginning dialog
    public static void newGameDialog(){
        Object[] choice = {"Easy", "Medium", "Hard"};
        String s = (String)JOptionPane.showInputDialog(null, "Please Pick a Difficulty Level: ",
                "Start Game", JOptionPane.PLAIN_MESSAGE, null, choice, "Easy");

        if((s != null) && (s.length() > 0)){
            if(s == "Easy") numCards = 16;
            if(s == "Medium") numCards = 24;
            if(s == "Hard") numCards = 36;
        } else {
            System.exit(0);
        }
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

        card = new JButton[numCards];

        for(int i = 0; i < card.length; i++){
            card[i] = new JButton();
            card[i].setIcon(background);
            card[i].setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
            card[i].addActionListener(this);
        }

        for(int i = 0; i < card.length; i++)
        	middlePanel.add(card[i]);

        cardList.clear();
        for(int i=0;i<2;i++){
            for(int j=1;j<(card.length/2)+1;j++){
                cardList.add(j);
            }
        }

        middlePanel.validate();
        middlePanel.updateUI();

        resetPlayerViews();
        
        restartGame();
    }

    // main
    public static void main(String[] args){
        newGameDialog();
        SoundPlayer.music();
        new MemoryGame();
    }
}