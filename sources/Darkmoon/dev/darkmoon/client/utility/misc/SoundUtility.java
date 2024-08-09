package dev.darkmoon.client.utility.misc;

import dev.darkmoon.client.utility.Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundUtility implements Utility {
    public static synchronized void playSound(String string, float volume) {
        try {
            Clip clip = AudioSystem.getClip();
            InputStream inputStream = mc.getResourceManager().getResource(new ResourceLocation("darkmoon/sounds/" + string)).getInputStream();

            BufferedInputStream var4 = new BufferedInputStream(inputStream);
            AudioInputStream var5 = AudioSystem.getAudioInputStream(var4);
            clip.open(var5);
            clip.start();
            FloatControl var6 = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            var6.setValue(volume);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public static void playSound() {
        Minecraft.getMinecraft().player.playSound(SoundEvents.BLOCK_NOTE_PLING, 1.0f, 1.7f);
    }

    public static void playSound(float pitch, float volume) {
        if (Minecraft.getMinecraft().player == null)
            return;
        Minecraft.getMinecraft().player.playSound(SoundEvents.BLOCK_NOTE_PLING, volume, pitch);

    }

    public static void playSound(float pitch) {
        playSound(pitch, 3);
    }
}
