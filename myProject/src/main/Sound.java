package main;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.ArrayList;

public class Sound {

    Clip clip;
    ArrayList<URL> soundURL = new ArrayList<>();

    public Sound() {

        loadSoundEffect("Bonetrousle");
        loadSoundEffect("test");
        loadSoundEffect("sword_strike");
        loadSoundEffect("dead");
        loadSoundEffect("dead1");
        loadSoundEffect("hitHurt");
        loadSoundEffect("MountainSong");
        loadSoundEffect("asylum");

    }

    private void loadSoundEffect(String name) {
        URL e = getClass().getResource("/sound/" + name + ".wav");
        soundURL.add(e);
    }

    public void load(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL.get(i));
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

}
