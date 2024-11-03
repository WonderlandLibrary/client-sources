package net.augustus.utils.sound;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.FloatControl.Type;

public class SoundUtil {
    private static Clip clip;
    public static final URL button = SoundUtil.class.getClassLoader().getResource("ressources/sounds/buttonClick.wav");
    public static final URL toggleOnSound = SoundUtil.class.getClassLoader().getResource("ressources/sounds/toggleSound.wav");
    public static final URL toggleOffSound = SoundUtil.class.getClassLoader().getResource("ressources/sounds/toggleSound2.wav");
    public static final URL loginSuccessful = SoundUtil.class.getClassLoader().getResource("ressources/sounds/loginSuccessful.wav");
    public static final URL loginFailed = SoundUtil.class.getClassLoader().getResource("ressources/sounds/loginFailed.wav");
    public static final URL welcomeSound = SoundUtil.class.getClassLoader().getResource("ressources/sounds/welcome.wav");

    public static void play(URL filePath) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(filePath));
            FloatControl floatControl = (FloatControl)clip.getControl(Type.MASTER_GAIN);
            floatControl.setValue(0.0F);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException var2) {
            //var2.printStackTrace();
        }
    }
}
