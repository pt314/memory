package pt314.just4fun.games.memory.player;

public class PlayerThemeManager {

    // TODO: load from configuration file.
    private static PlayerTheme fireTheme = new PlayerTheme("Fire", "BLAZE", "fire.png", "fireBG.png", "fire.wav");
    private static PlayerTheme waterTheme = new PlayerTheme("Water", "WATER", "water.png", "waterBG.png", "water.wav");
    private static PlayerTheme electricTheme = new PlayerTheme("Electricity", "SPARK", "electricity.png", "electricBG.png", "electric.wav");
    private static PlayerTheme mossTheme = new PlayerTheme("Moss", "MOSSY", "moss.png", "mossBG.png", "moss.wav");
    private static PlayerTheme airTheme = new PlayerTheme("Air", "WINDY", "air.png", "airBG.png", "air.wav");

    private static PlayerTheme[] allThemes = {fireTheme, waterTheme, electricTheme, mossTheme, airTheme};
    
    public static PlayerTheme[] getThemes() {
    	return allThemes;
    }

}
