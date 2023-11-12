package classes.other;

import javax.sound.sampled.*;
import java.io.*;

public class Audio {
    private Clip clip;

    public Audio(File soundFile) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void play() {
       //clip.start();
    }

    public void stop() {
        clip.stop();
    }
}
