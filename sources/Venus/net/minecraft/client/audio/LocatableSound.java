/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public abstract class LocatableSound
implements ISound {
    protected Sound sound;
    protected final SoundCategory category;
    protected final ResourceLocation positionedSoundLocation;
    protected float volume = 1.0f;
    protected float pitch = 1.0f;
    protected double x;
    protected double y;
    protected double z;
    protected boolean repeat;
    protected int repeatDelay;
    protected ISound.AttenuationType attenuationType = ISound.AttenuationType.LINEAR;
    protected boolean priority;
    protected boolean global;

    protected LocatableSound(SoundEvent soundEvent, SoundCategory soundCategory) {
        this(soundEvent.getName(), soundCategory);
    }

    protected LocatableSound(ResourceLocation resourceLocation, SoundCategory soundCategory) {
        this.positionedSoundLocation = resourceLocation;
        this.category = soundCategory;
    }

    @Override
    public ResourceLocation getSoundLocation() {
        return this.positionedSoundLocation;
    }

    @Override
    public SoundEventAccessor createAccessor(SoundHandler soundHandler) {
        SoundEventAccessor soundEventAccessor = soundHandler.getAccessor(this.positionedSoundLocation);
        this.sound = soundEventAccessor == null ? SoundHandler.MISSING_SOUND : soundEventAccessor.cloneEntry();
        return soundEventAccessor;
    }

    @Override
    public Sound getSound() {
        return this.sound;
    }

    @Override
    public SoundCategory getCategory() {
        return this.category;
    }

    @Override
    public boolean canRepeat() {
        return this.repeat;
    }

    @Override
    public int getRepeatDelay() {
        return this.repeatDelay;
    }

    @Override
    public float getVolume() {
        return this.volume * this.sound.getVolume();
    }

    @Override
    public float getPitch() {
        return this.pitch * this.sound.getPitch();
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public ISound.AttenuationType getAttenuationType() {
        return this.attenuationType;
    }

    @Override
    public boolean isGlobal() {
        return this.global;
    }

    public String toString() {
        return "SoundInstance[" + this.positionedSoundLocation + "]";
    }
}

