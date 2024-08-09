/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.LocatableSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public abstract class TickableSound
extends LocatableSound
implements ITickableSound {
    private boolean donePlaying;

    protected TickableSound(SoundEvent soundEvent, SoundCategory soundCategory) {
        super(soundEvent, soundCategory);
    }

    @Override
    public boolean isDonePlaying() {
        return this.donePlaying;
    }

    protected final void finishPlaying() {
        this.donePlaying = true;
        this.repeat = false;
    }
}

