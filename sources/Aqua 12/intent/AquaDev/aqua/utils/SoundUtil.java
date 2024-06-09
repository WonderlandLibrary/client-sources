// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.AudioSystem;
import java.net.URL;
import javax.sound.sampled.Clip;

public class SoundUtil
{
    private static Clip clip;
    public static final URL toggleOnSound;
    public static final URL toggleOffSound;
    
    public static void play(final URL filePath) {
        try {
            (SoundUtil.clip = AudioSystem.getClip()).open(AudioSystem.getAudioInputStream(filePath));
            final FloatControl floatControl = (FloatControl)SoundUtil.clip.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(6.0206f);
            SoundUtil.clip.start();
        }
        catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    static {
        toggleOnSound = SoundUtil.class.getClassLoader().getResource("assets/minecraft/Aqua/gui/toggleSound.wav");
        toggleOffSound = SoundUtil.class.getClassLoader().getResource("assets/minecraft/Aqua/gui/toggleSound2.wav");
    }
}
