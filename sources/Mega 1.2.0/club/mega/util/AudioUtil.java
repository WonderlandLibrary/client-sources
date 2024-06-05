package club.mega.util;

import club.mega.interfaces.MinecraftInterface;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public final class AudioUtil implements MinecraftInterface {

    public static void playSound(final String sound) {
        if (MC.theWorld != null)
            try {
                final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(MC.mcDataDir, "Mega/sounds/" + sound + ".wav"));
                final Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch(Exception ex) {
                ChatUtil.sendMessage("ERROR: " + ex.getMessage());
            }
    }

}
