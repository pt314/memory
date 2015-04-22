package pt314.just4fun.games.memory.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import pt314.just4fun.games.memory.MemoryGame;

public class BoardView extends JPanel {

	private static final int WIDTH = 600;	// in pixels
	private static final int HEIGHT = 600;	// in pixels
	
	private MemoryGame game; // the game

	private int rows, columns; // board size
	
	public BoardView(MemoryGame game, int rows, int columns) {
		super();
		
		this.game = game;
		this.rows = rows;
		this.columns = columns;
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.YELLOW);
		
		init();
		
		update();
	}

	public BoardView(MemoryGame game) {
		this(game, 0, 0);
	}

	private void init() {

		// arrange card in grid
//		setLayout(new GridLayout(rows, columns));
		
		
	}
	
	public void update() {
		
	}
}
