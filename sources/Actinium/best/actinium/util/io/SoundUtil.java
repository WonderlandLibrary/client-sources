package best.actinium.util.io;

import best.actinium.util.IAccess;
import net.minecraft.util.ResourceLocation;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;

public class SoundUtil implements IAccess {
    public static void playSound(ResourceLocation location, float volume) {
        new Thread(() -> {
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(mc.getResourceManager().getResource(location).getInputStream());
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedInputStream);

                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);

                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float range = gainControl.getMaximum() - gainControl.getMinimum();
                float gain = (range * volume) + gainControl.getMinimum();
                gainControl.setValue(gain);

                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
