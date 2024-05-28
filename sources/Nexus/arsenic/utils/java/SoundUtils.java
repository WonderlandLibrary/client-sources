package arsenic.utils.java;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundUtils {

    public static void playSound(String name) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(SoundUtils.class.getResource("/assets/arsenic/sounds/" + name + ".wav")));
                clip.start();
                Thread.sleep(clip.getMicrosecondLength());
            } catch (Exception e) {
                System.out.println("Error with playing sound.");
            }
            if(clip != null)
                clip.close();
        });
    }
}
