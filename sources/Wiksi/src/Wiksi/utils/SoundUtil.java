package src.Wiksi.utils;

import src.Wiksi.utils.client.IMinecraft;
import net.minecraft.util.SoundEvents;

public class SoundUtil implements IMinecraft {
    public static void playSound(float pitch, float volume) {
        if (mc.player == null)
            return;
        mc.player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_PLING, volume, pitch);
    }

}
