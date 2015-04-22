import sun.audio.*;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.Timer;
import javax.swing.*;

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
    private static int scoreF = 0, scoreS = 0, counter = 0, numPairs = 0;
    private static int winF = 0, winS = 0, loseF = 0, loseS = 0, tie = 0, moveF = 0, moveS =0;
    private static boolean switchTurn = false, addScore = false;
    public static char p1Type = 'f', p2Type = 'w';
    public static int numCards;

    private Timer setTimer;

    // Menu Items
    private JMenuBar menuBar = new JMenuBar();
    private JMenu options = new JMenu("Information");
    private JMenu game = new JMenu("Game Settings");
    private JMenu newGameSet = new JMenu("New Game");

    private JMenuItem rules, about;
    private JMenuItem restart, deleteScores;
    private JMenuItem four, fourBySix, six;

    // Panels and Image Labels
    private JPanel leftPanel = new JPanel();
    private JPanel middlePanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel p1Panel = new JPanel();
    private JPanel p2Panel = new JPanel();

    private JLabel leftImage = new JLabel();
    private JLabel middleImage = new JLabel();
    private JLabel rightImage = new JLabel();
    private JLabel p1Turn = new JLabel();
    private JLabel p2Turn = new JLabel();

    // Player Areas
    private String [] comboString = {"Fire", "Water", "Electricity", "Moss", "Air"};
    private JComboBox lPlayer = new JComboBox(comboString);
    private JComboBox rPlayer = new JComboBox(comboString);

    private static JLabel firstPlayer = new JLabel();
    private static JLabel secondPlayer = new JLabel();
    private static JLabel firstScore = new JLabel("Moves: " + moveS);
    private static JLabel secondScore = new JLabel("Moves: " + moveF);
    private static JTextArea fCardDesc = new JTextArea();
    private static JTextArea sCardDesc = new JTextArea();

    // Game Area
    private JButton [] card = new JButton[numCards];
    private JButton [] p1Score = new JButton[numCards*2-1];
    private JButton [] p2Score = new JButton[numCards*2-1];

    // Images
    private ImageIcon [] cardIcon = {new ImageIcon("src//Images//image1.png"), new ImageIcon("src//Images//image2.png"),
        new ImageIcon("src//Images//image3.png"), new ImageIcon("src//Images//image4.png"), new ImageIcon("src//Images//image5.png"),
        new ImageIcon("src//Images//image6.png"), new ImageIcon("src//Images//image7.png"), new ImageIcon("src//Images//image8.png"),
        new ImageIcon("src//Images//image9.png"), new ImageIcon("src//Images//image10.png"), new ImageIcon("src//Images//image11.png"),
        new ImageIcon("src//Images//image12.png"), new ImageIcon("src//Images//image13.png"), new ImageIcon("src//Images//image14.png"),
        new ImageIcon("src//Images//image15.png"), new ImageIcon("src//Images//image16.png"), new ImageIcon("src//Images//image17.png"),
        new ImageIcon("src//Images//image18.png")};

    private static ImageIcon fire = new ImageIcon("src//Images//fire.png");
    private static ImageIcon water = new ImageIcon("src//Images//water.png");
    private static ImageIcon electricity = new ImageIcon("src//Images//electricity.png");
    private static ImageIcon moss = new ImageIcon("src//Images//moss.png");
    private static ImageIcon air = new ImageIcon("src//Images//air.png");

    private ImageIcon background = new ImageIcon("src//Images//background.png");
    private ImageIcon fireBG = new ImageIcon("src//Images//fireBG.png");
    private ImageIcon waterBG = new ImageIcon("src//Images//waterBG.png");
    private ImageIcon electricBG = new ImageIcon("src//Images//electricBG.png");
    private ImageIcon mossBG = new ImageIcon("src//Images//mossBG.png");
    private ImageIcon airBG = new ImageIcon("src//Images//airBG.png");

    // Card Information
    private ArrayList<Integer> cardList = new ArrayList<Integer>();
    private int [] buttonID = new int[2];
    private int [] buttonVal = new int[2];

    private Container pane = getContentPane();

    public MemoryGame() {
        setTitle("Memory Game");
        setSize(806, 652);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // MenuBar Setup
        setJMenuBar(menuBar);
        menuBar.add(options);
        menuBar.add(game);

        setOptionsMenu();
        setGameMenu();

        // misc.
        fCardDesc.setEditable(false);
        sCardDesc.setEditable(false);
        fCardDesc.setFont(new Font("Calibre", Font.BOLD, 12));
        sCardDesc.setFont(new Font("Calibre", Font.BOLD, 12));

        lPlayer.setSelectedIndex(0);
        lPlayer.addActionListener(this);
        rPlayer.setSelectedIndex(1);
        rPlayer.addActionListener(this);

        // size and layout
        pane.setLayout(null);

        leftPanel.setSize(new Dimension(100, 600));
        leftImage.setSize(new Dimension(100, 600));
        middlePanel.setSize(new Dimension(600, 600));
        middleImage.setSize(new Dimension(600, 600));
        rightPanel.setSize(new Dimension(100, 600));
        rightImage.setSize(new Dimension(100, 600));
        p1Panel.setSize(new Dimension(20, 300));
        p2Panel.setSize(new Dimension(20, 300));

        leftPanel.setLocation(0, 0);
        leftImage.setLocation(0, 0);
        middlePanel.setLocation(100, 0);
        middleImage.setLocation(0, 0);
        rightPanel.setLocation(700, 0);
        rightImage.setLocation(0, 0);
        p1Panel.setLocation(35, 270);
        p2Panel.setLocation(35, 270);

        if(numCards == 24) middlePanel.setLayout(new GridLayout(4, 6));
        else {
            double layout = Math.sqrt(numCards);
            middlePanel.setLayout(new GridLayout((int) layout, (int) layout));
        }

        leftPanel.setLayout(null);
        rightPanel.setLayout(null);
        p1Panel.setLayout(new GridLayout(numCards*2-1, 1));
        p2Panel.setLayout(new GridLayout(numCards*2-1, 1));

        p1Panel.setBackground(Color.WHITE);
        p2Panel.setBackground(Color.WHITE);

        firstPlayer.setSize(new Dimension(90, 90));
        secondPlayer.setSize(new Dimension(90, 90));
        lPlayer.setSize(new Dimension(90, 20));
        rPlayer.setSize(new Dimension(90, 20));
        fCardDesc.setSize(new Dimension(90, 100));
        sCardDesc.setSize(new Dimension(90, 100));
        firstScore.setSize(new Dimension(90, 20));
        secondScore.setSize(new Dimension(90, 20));
        p1Turn.setSize(new Dimension(90, 20));
        p2Turn.setSize(new Dimension(90, 20));

        firstPlayer.setLocation(5, 10);
        secondPlayer.setLocation(5, 10);
        lPlayer.setLocation(5, 100);
        rPlayer.setLocation(5, 100);
        fCardDesc.setLocation(5, 130);
        sCardDesc.setLocation(5, 130);
        firstScore.setLocation(5, 240);
        secondScore.setLocation(5, 240);
        p1Turn.setLocation(5, 570);
        p2Turn.setLocation(5, 570);

        // set text and images
        fCardDesc.setText("BLAZE PLAYER\n\n" + "Wins:   " + winF + "\nLoses:  " + loseF + "\nTies:   " + tie);
        sCardDesc.setText("WATER PLAYER\n\n" + "Wins:   " + winS + "\nLoses:  " + loseS + "\nTies:   " + tie);
        firstPlayer.setIcon(fire);
        secondPlayer.setIcon(water);
        p1Turn.setText("PLAYER TURN");
        p2Turn.setText("");
        p1Turn.setForeground(Color.WHITE);
        p2Turn.setForeground(Color.WHITE);
        firstScore.setForeground(Color.WHITE);
        secondScore.setForeground(Color.WHITE);

        for(int i=0;i<p1Score.length;i++){
            p1Score[i] = new JButton();
            p2Score[i] = new JButton();
            p1Score[i].setBackground(Color.WHITE);
            p2Score[i].setBackground(Color.WHITE);

            if(numCards == 16){
                p1Score[i].setText("" + (i+1));
                p2Score[i].setText("" + (i+1));
                p1Score[i].setFont(new Font("sansserif", Font.BOLD, 8));
                p2Score[i].setFont(new Font("sansserif", Font.BOLD, 8));
            }

            p1Score[i].setBorder(BorderFactory.createEmptyBorder());
            p2Score[i].setBorder(BorderFactory.createEmptyBorder());
        }

        for(int i=0;i<p1Score.length;i++){
            p1Panel.add(p1Score[i]);
            p2Panel.add(p2Score[i]);
        }

        // add to pane
        leftPanel.add(firstPlayer);
        leftPanel.add(lPlayer);
        leftPanel.add(fCardDesc);
        leftPanel.add(firstScore);
        leftPanel.add(p1Panel);
        leftPanel.add(p1Turn);

        rightPanel.add(secondPlayer);
        rightPanel.add(rPlayer);
        rightPanel.add(sCardDesc);
        rightPanel.add(secondScore);
        rightPanel.add(p2Panel);
        rightPanel.add(p2Turn);

        for(int i=0;i<card.length;i++){
            card[i] = new JButton();
            card[i].setIcon(background);
            card[i].setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
            card[i].addActionListener(this);
        }

        for(int i=0;i<card.length;i++) middlePanel.add(card[i]);

        for(int i=0;i<2;i++){
            for(int j=1;j<(card.length/2)+1;j++){
                cardList.add(j);
            }
        }

        Collections.shuffle(cardList);

        leftImage.setIcon(fireBG);
        leftPanel.add(leftImage);
        rightImage.setIcon(waterBG);
        rightPanel.add(rightImage);

        pane.add(leftPanel);
        pane.add(middlePanel);
        pane.add(rightPanel);

        setVisible(true);
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

                if(!switchTurn){
                    playSound(p1Type);
                    moveF++;
                } else {
                    playSound(p2Type);
                    moveS++;
                }

                firstScore.setText("Moves: " + moveF);
                secondScore.setText("Moves: " + moveS);

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
                        if(!switchTurn){
                            if(buttonVal[1]%3 == 0){scoreF += 2;}
                            if(buttonVal[1]%2 == 0){scoreF++;}
                            if(addScore){scoreF++;}
                            scoreF++;

                            firstScore.setText("Moves: " + moveF);

                            for(int k=0;k<scoreF;k++){
                                p1Score[k].setBackground(Color.BLACK);
                            }

                            addScore = true;
                        } else{
                            if(buttonVal[1]%3 == 0){scoreS += 2;}
                            if(buttonVal[1]%2 == 0){scoreS++;}
                            if(addScore){scoreS++;}
                            scoreS++;

                            secondScore.setText("Moves: " + moveS);

                            for(int k=0;k<scoreS;k++){
                                p2Score[k].setBackground(Color.BLACK);
                            }

                            addScore = true;
                        }

                        numPairs++;
                        if(numPairs == numCards/2){
                            checkWin();
                            restartGame();
                        }
                    } else {
                        if(!switchTurn){
                            p1Turn.setText("");
                            p2Turn.setText("PLAYER TURN");

                            switchTurn = true;
                            addScore = false;
                        }
                        else{
                            p1Turn.setText("PLAYER TURN");
                            p2Turn.setText("");

                            switchTurn = false;
                            addScore = false;
                        }

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
                }
            }
        }

        // combo box selections
         if(e.getSource() == lPlayer){
            JComboBox lp = (JComboBox)e.getSource();
            if(lp.getSelectedItem() == "Fire"){
                if(secondPlayer.getIcon() != fire){
                    firstPlayer.setIcon(fire);
                    leftImage.setIcon(fireBG);
                    fCardDesc.setText("BLAZE PLAYER\n\n" + "Wins:   " + winF + "\nLoses:  " + loseF + "\nTies:   " + tie);
                    p1Type = 'f';
                }
            }
            if(lp.getSelectedItem() == "Water"){
                if(secondPlayer.getIcon() != water){
                    firstPlayer.setIcon(water);
                    leftImage.setIcon(waterBG);
                    fCardDesc.setText("WATER PLAYER\n\n" + "Wins:   " + winF + "\nLoses:  " + loseF + "\nTies:   " + tie);
                    p1Type = 'w';
                }
            }
            if(lp.getSelectedItem() == "Electricity"){
                if(secondPlayer.getIcon() != electricity){
                    firstPlayer.setIcon(electricity);
                    leftImage.setIcon(electricBG);
                    fCardDesc.setText("SPARK PLAYER\n\n" + "Wins:   " + winF + "\nLoses:  " + loseF + "\nTies:   " + tie);
                    p1Type = 'e';
                }
            }
            if(lp.getSelectedItem() == "Moss"){
                if(secondPlayer.getIcon() != moss){
                    firstPlayer.setIcon(moss);
                    leftImage.setIcon(mossBG);
                    fCardDesc.setText("MOSSY PLAYER\n\n" + "Wins:   " + winF + "\nLoses:  " + loseF + "\nTies:   " + tie);
                    p1Type = 'm';
                }
            }
            if(lp.getSelectedItem() == "Air"){
                if(secondPlayer.getIcon() != air){
                    firstPlayer.setIcon(air);
                    leftImage.setIcon(airBG);
                    fCardDesc.setText("WINDY PLAYER\n\n" + "Wins:   " + winF + "\nLoses:  " + loseF + "\nTies:   " + tie);
                    p1Type = 'a';
                }
            }
        }

        if(e.getSource() == rPlayer){
            JComboBox rp = (JComboBox)e.getSource();
            if(rp.getSelectedItem() == "Fire"){
                if(firstPlayer.getIcon() != fire){
                    secondPlayer.setIcon(fire);
                    rightImage.setIcon(fireBG);
                    sCardDesc.setText("BLAZE PLAYER\n\n" + "Wins:   " + winS + "\nLoses:  " + loseS + "\nTies:   " + tie);
                    p2Type = 'f';
                }
            }
            if(rp.getSelectedItem() == "Water"){
                if(firstPlayer.getIcon() != water){
                    secondPlayer.setIcon(water);
                    rightImage.setIcon(waterBG);
                    sCardDesc.setText("WATER PLAYER\n\n" + "Wins:   " + winS + "\nLoses:  " + loseS + "\nTies:   " + tie);
                    p2Type = 'w';
                }
            }
            if(rp.getSelectedItem() == "Electricity"){
                if(firstPlayer.getIcon() != electricity){
                    secondPlayer.setIcon(electricity);
                    rightImage.setIcon(electricBG);
                    sCardDesc.setText("SPARK PLAYER\n\n" + "Wins:   " + winS + "\nLoses:  " + loseS + "\nTies:   " + tie);
                    p2Type = 'e';
                }
            }
            if(rp.getSelectedItem() == "Moss"){
                if(firstPlayer.getIcon() != moss){
                    secondPlayer.setIcon(moss);
                    rightImage.setIcon(mossBG);
                    sCardDesc.setText("MOSSY PLAYER\n\n" + "Wins:   " + winS + "\nLoses:  " + loseS + "\nTies:   " + tie);
                    p2Type = 'm';
                }
            }
            if(rp.getSelectedItem() == "Air"){
                if(firstPlayer.getIcon() != air){
                    secondPlayer.setIcon(air);
                    rightImage.setIcon(airBG);
                    sCardDesc.setText("WINDY PLAYER\n\n" + "Wins:   " + winS + "\nLoses:  " + loseS + "\nTies:   " + tie);
                    p2Type = 'a';
                }
            }
        }

        // menu item selections
        if(e.getSource() == rules){
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

        if(e.getSource() == about){
            JOptionPane.showMessageDialog(null, "[A number] \n[Student name] \n" +
                    "[student email]\n", "About Information", JOptionPane.PLAIN_MESSAGE);
        }

        if(e.getSource() == four){
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int n = (int)JOptionPane.showConfirmDialog(null, "Would you like to make a new 4x4 game?",
                    "New Game Dialog", dialogButton);

            if(n == 0){
                numCards = 16;
                newBoardPanel();
            }
        }

        if(e.getSource() == fourBySix){
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int n = (int)JOptionPane.showConfirmDialog(null, "Would you like to make a new 4x6 game?",
                    "New Game Dialog", dialogButton);

            if(n == 0){
                numCards = 24;
                newBoardPanel();
            }
        }

        if(e.getSource() == six){
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int n = (int)JOptionPane.showConfirmDialog(null, "Would you like to make a new 6x6 game?",
                    "New Game Dialog", dialogButton);

            if(n == 0){
                numCards = 36;
                newBoardPanel();
            }
        }

        if(e.getSource() == restart){
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int n = (int)JOptionPane.showConfirmDialog(null, "Would you like to restart the game?",
                    "Restart Dialog", dialogButton);

            if((n == 0)) restartGame();
        }

        if(e.getSource() == deleteScores){
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int n = (int)JOptionPane.showConfirmDialog(null, "Would you like to delete win/lose records?",
                    "Delete Scores Dialog", dialogButton);

            if((n == 0)){
                restartGame();

                winF = 0;
                winS = 0;
                loseF = 0;
                loseS = 0;
                tie = 0;

                setPlayerText();
            }
        }
    }

    // check to see who won
    public static void checkWin(){
        if(scoreF > scoreS){
            winF++;
            loseS++;
            JOptionPane.showMessageDialog(null, "Congratulations! \nPlayer One Wins!",
                    "Win Dialog", JOptionPane.INFORMATION_MESSAGE, firstPlayer.getIcon());
        }
        else if(scoreS > scoreF){
            winS++;
            loseF++;
            JOptionPane.showMessageDialog(null, "Congratulations! \nPlayer Two Wins!",
                    "Win Dialog", JOptionPane.INFORMATION_MESSAGE, secondPlayer.getIcon());
        }
        else if(scoreS == scoreF){
            tie++;
            JOptionPane.showMessageDialog(null, "Tied Game", "Tie Dialog", JOptionPane.PLAIN_MESSAGE);
        }

        setPlayerText();
    }

    // restart the game
    public void restartGame(){
        Collections.shuffle(cardList);
        for(int i=0;i<card.length;i++){
            card[i].setEnabled(true);
            card[i].setIcon(background);
        }

        for(int j=0;j<p1Score.length;j++){
            p1Score[j].setBackground(Color.WHITE);
            p2Score[j].setBackground(Color.WHITE);
        }

        switchTurn = false;
        numPairs = 0;
        scoreS = 0;
        scoreF = 0;
        moveF = 0;
        moveS = 0;
        firstScore.setText("Moves: " + moveF);
        secondScore.setText("Moves: " + moveS);
        p1Turn.setText("PLAYER TURN");
        p2Turn.setText("");
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

    // menu bar add items
    private void setOptionsMenu() {
        rules = new JMenuItem("Rules");
        about = new JMenuItem("About");

        options.add(rules);
        options.add(about);

        rules.addActionListener(this);
        about.addActionListener(this);
    }

    private void setGameMenu() {
        restart = new JMenuItem("Restart Game");
        deleteScores = new JMenuItem("Reset Scores");

        four = new JMenuItem("4x4");
        fourBySix = new JMenuItem("4x6");
        six = new JMenuItem("6x6");

        newGameSet.add(four);
        newGameSet.add(fourBySix);
        newGameSet.add(six);

        game.add(newGameSet);
        game.add(restart);
        game.add(deleteScores);

        newGameSet.addActionListener(this);
        restart.addActionListener(this);
        deleteScores.addActionListener(this);

        four.addActionListener(this);
        fourBySix.addActionListener(this);
        six.addActionListener(this);
    }

    // play sound effects
    public void playSound(char choice){
        String chosen = "src\\Sound\\fire.wav";
        switch(choice){
            case 'f':
                chosen = "src\\Sound\\fire.wav";
                break;
            case 'w':
                chosen = "src\\Sound\\water.wav";
                break;
            case 'm':
                chosen = "src\\Sound\\moss.wav";
                break;
            case 'e':
                chosen = "src\\Sound\\electric.wav";
                break;
            case 'a':
                chosen = "src\\Sound\\air.wav";
                break;
        }

        try{
            InputStream inStream = new FileInputStream(chosen);
            AudioStream soundStream = new AudioStream(inStream);
            AudioPlayer.player.start(soundStream);
        } catch(Exception Ex){}
    }

    // play music
    public static void music(){
        // figure out how to loop?
            try{
                InputStream in = new FileInputStream("src//Sound//background.wav");
                AudioStream bgStream = new AudioStream(in);
                AudioPlayer.player.start(bgStream);
            }
            catch(Exception Ex){}
    }

    // set player text
    public static void setPlayerText(){
        if(firstPlayer.getIcon() == fire) fCardDesc.setText("BLAZE PLAYER\n\n" + "Wins:   " + winF + "\nLoses:  " + loseF + "\nTies:   " + tie);
        if(firstPlayer.getIcon() == water) fCardDesc.setText("WATER PLAYER\n\n" + "Wins:   " + winF + "\nLoses:  " + loseF + "\nTies:   " + tie);
        if(firstPlayer.getIcon() == electricity) fCardDesc.setText("SPARK PLAYER\n\n" + "Wins:   " + winF + "\nLoses:  " + loseF + "\nTies:   " + tie);
        if(firstPlayer.getIcon() == moss) fCardDesc.setText("MOSSY PLAYER\n\n" + "Wins:   " + winF + "\nLoses:  " + loseF + "\nTies:   " + tie);
        if(firstPlayer.getIcon() == air) fCardDesc.setText("WINDY PLAYER\n\n" + "Wins:   " + winF + "\nLoses:  " + loseF + "\nTies:   " + tie);

        if(secondPlayer.getIcon() == fire) sCardDesc.setText("BLAZE PLAYER\n\n" + "Wins:   " + winS + "\nLoses:  " + loseS + "\nTies:   " + tie);
        if(secondPlayer.getIcon() == water) sCardDesc.setText("WATER PLAYER\n\n" + "Wins:   " + winS + "\nLoses:  " + loseS + "\nTies:   " + tie);
        if(secondPlayer.getIcon() == electricity) sCardDesc.setText("SPARK PLAYER\n\n" + "Wins:   " + winS + "\nLoses:  " + loseS + "\nTies:   " + tie);
        if(secondPlayer.getIcon() == moss) sCardDesc.setText("MOSSY PLAYER\n\n" + "Wins:   " + winS + "\nLoses:  " + loseS + "\nTies:   " + tie);
        if(secondPlayer.getIcon() == air) sCardDesc.setText("WINDY PLAYER\n\n" + "Wins:   " + winS + "\nLoses:  " + loseS + "\nTies:   " + tie);
    }

    // updating for a new board
    public void newBoardPanel(){
        middlePanel.removeAll();
        p1Panel.removeAll();
        p2Panel.removeAll();

        if(numCards == 24) middlePanel.setLayout(new GridLayout(4, 6));
        else {
            double layout = Math.sqrt(numCards);
            middlePanel.setLayout(new GridLayout((int) layout, (int) layout));
        }

        card = new JButton[numCards];

        for(int i=0;i<card.length;i++){
            card[i] = new JButton();
            card[i].setIcon(background);
            card[i].setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
            card[i].addActionListener(this);
        }

        for(int i=0;i<card.length;i++) middlePanel.add(card[i]);

        cardList.clear();
        for(int i=0;i<2;i++){
            for(int j=1;j<(card.length/2)+1;j++){
                cardList.add(j);
            }
        }

        middlePanel.validate();
        middlePanel.updateUI();

        p1Score = new JButton[numCards*2];
        p2Score = new JButton[numCards*2];

        p1Panel.setLayout(new GridLayout(numCards*2, 1));
        p2Panel.setLayout(new GridLayout(numCards*2, 1));

        for(int i=0;i<p1Score.length;i++){
            p1Score[i] = new JButton();
            p2Score[i] = new JButton();
            p1Score[i].setBackground(Color.WHITE);
            p2Score[i].setBackground(Color.WHITE);

            if(numCards == 16){
                p1Score[i].setText("" + (i+1));
                p2Score[i].setText("" + (i+1));
                p1Score[i].setFont(new Font("sansserif", Font.BOLD, 8));
                p2Score[i].setFont(new Font("sansserif", Font.BOLD, 8));
            }

            p1Score[i].setBorder(BorderFactory.createEmptyBorder());
            p2Score[i].setBorder(BorderFactory.createEmptyBorder());
        }

        for(int i=0;i<p1Score.length;i++){
            p1Panel.add(p1Score[i]);
            p2Panel.add(p2Score[i]);
        }

        p1Panel.validate();
        p1Panel.updateUI();
        p2Panel.validate();
        p2Panel.updateUI();

        restartGame();
    }

    // main
    public static void main(String[] args){
        newGameDialog();
        music();
        new MemoryGame();
    }
}