package classes.other;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {
    private final Clip clip;

    public Audio(File soundFile) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void play() {
        //clip.start();
    }

    public void stop() {
        clip.stop();
    }
}
