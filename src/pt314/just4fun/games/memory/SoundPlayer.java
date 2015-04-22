package pt314.just4fun.games.memory;

import java.io.FileInputStream;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class SoundPlayer {

    private static final String SOUND_PATH = MemoryGame.BASE_PATH + "sounds//";

    // play sound effects
    public static void playSound(String file){
        try{
            InputStream inStream = new FileInputStream(SOUND_PATH + file);
            AudioStream soundStream = new AudioStream(inStream);
            AudioPlayer.player.start(soundStream);
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
    }

    // play music
    public static void music(){
//    	playSound("background.wav");
    }

}
