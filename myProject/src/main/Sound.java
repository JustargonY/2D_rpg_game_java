package main;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.ArrayList;

public class Sound {

    // Playing track
    Clip clip;

    // Track list
    ArrayList<URL> soundURL = new ArrayList<>();

    // Sound control parameters
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound() {

        loadSoundEffect("Bonetrousle");
        loadSoundEffect("test");
        loadSoundEffect("sword_strike");
        loadSoundEffect("dead");
        loadSoundEffect("dead1");
        loadSoundEffect("hitHurt");
        loadSoundEffect("MountainSong");
        loadSoundEffect("ekusupuroshion");
        loadSoundEffect("lvlup");

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
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            getVolume();
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

    public void getVolume() {

        switch (volumeScale) {
            case 0 -> volume = -80f;
            case 1 -> volume = -20f;
            case 2 -> volume = -12f;
            case 3 -> volume = -5f;
            case 4 -> volume = 1f;
        }

        fc.setValue(volume);

    }

}
