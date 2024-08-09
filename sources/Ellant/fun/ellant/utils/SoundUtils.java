/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.utils;

import net.minecraft.util.SoundEvents;
import fun.ellant.utils.client.IMinecraft;

public class SoundUtils
implements IMinecraft {
    public static void playSound(float pitch, float volume) {
        if (SoundUtils.mc.player == null) {
            return;
        }
        SoundUtils.mc.player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_PLING, volume, pitch);
    }
}

