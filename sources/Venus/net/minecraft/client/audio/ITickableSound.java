/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.ISound;

public interface ITickableSound
extends ISound {
    public boolean isDonePlaying();

    public void tick();
}

