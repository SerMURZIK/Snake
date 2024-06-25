package classes.other;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Audio {
    private final Clip clip;

    public Audio(InputStream stream) {
        try {
            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(new BufferedInputStream(stream));

            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void play() {
        // clip.start();
    }

    public void stop() {
        clip.stop();
    }
}
