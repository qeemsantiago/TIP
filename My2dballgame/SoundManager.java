package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {

    Clip clip;
    URL soundURL[] = new URL[30];

    public SoundManager() {
        // Ensure these file names match your actual files EXACTLY (case-sensitive)
        soundURL[0] = getClass().getResource("/sound/bgmusic.wav"); 
        soundURL[1] = getClass().getResource("/sound/catch.wav");
        soundURL[2] = getClass().getResource("/sound/explode.wav");
        
    }

    public void setFile(int i) {
        try {
            // IMPORTANT: Close the previous clip before opening a new one
            if (clip != null && clip.isOpen()) {
                clip.stop();
                clip.close(); 
            }
            
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            System.err.println("Error loading sound index: " + i);
        }
    }

    public void play() {
        if (clip != null) {
            // If it's already playing, don't reset it
            if (clip.isRunning()) return; 
            
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (clip != null) clip.stop();
    }
}
