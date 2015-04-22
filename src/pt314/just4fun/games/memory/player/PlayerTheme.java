package pt314.just4fun.games.memory.player;

import javax.swing.ImageIcon;

import pt314.just4fun.games.memory.MemoryGame;

public class PlayerTheme {

	private String themeName;
	
	private String playerName;
	
	private String imageFile;
	
	private String backgroundFile;
	
	private String soundFile; // sound effect

	public PlayerTheme(String themeName, String playerName,
			String imageFile, String backgroundFile, String soundEffectFile) {
		this.themeName = themeName;
		this.playerName = playerName;
		this.imageFile = imageFile;
		this.backgroundFile = backgroundFile;
		this.soundFile = soundEffectFile;
	}

	public String getName() {
		return themeName;
	}

	public String getPlayerName() {
		return playerName;
	}
	
	public ImageIcon getImageIcon() {
		return new ImageIcon(MemoryGame.IMG_PATH + imageFile);
	}
	
	public ImageIcon getBackgroundIcon() {
		return new ImageIcon(MemoryGame.IMG_PATH + backgroundFile);
	}
	
	public String getSoundFile() {
		return soundFile;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
