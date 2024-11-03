package vestige.util.misc;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;

public class SoundPlayer {
   public static synchronized void playSound(String url, float volume, boolean stop) {
      (new Thread(() -> {
         try {
            Clip clip = AudioSystem.getClip();
            InputStream audioSrc = SoundPlayer.class.getResourceAsStream("/assets/minecraft/sounds/" + url);
            BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedIn);
            clip.open(inputStream);
            FloatControl gainControl = (FloatControl)clip.getControl(Type.MASTER_GAIN);
            gainControl.setValue(volume);
            clip.start();
            if (stop) {
               clip.stop();
            }
         } catch (Exception var8) {
            var8.printStackTrace();
         }

      })).start();
   }
}
