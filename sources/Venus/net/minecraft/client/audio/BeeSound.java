/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

public abstract class BeeSound
extends TickableSound {
    protected final BeeEntity beeInstance;
    private boolean hasSwitchedSound;

    public BeeSound(BeeEntity beeEntity, SoundEvent soundEvent, SoundCategory soundCategory) {
        super(soundEvent, soundCategory);
        this.beeInstance = beeEntity;
        this.x = (float)beeEntity.getPosX();
        this.y = (float)beeEntity.getPosY();
        this.z = (float)beeEntity.getPosZ();
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.0f;
    }

    @Override
    public void tick() {
        boolean bl = this.shouldSwitchSound();
        if (bl && !this.isDonePlaying()) {
            Minecraft.getInstance().getSoundHandler().playOnNextTick(this.getNextSound());
            this.hasSwitchedSound = true;
        }
        if (!this.beeInstance.removed && !this.hasSwitchedSound) {
            this.x = (float)this.beeInstance.getPosX();
            this.y = (float)this.beeInstance.getPosY();
            this.z = (float)this.beeInstance.getPosZ();
            float f = MathHelper.sqrt(Entity.horizontalMag(this.beeInstance.getMotion()));
            if ((double)f >= 0.01) {
                this.pitch = MathHelper.lerp(MathHelper.clamp(f, this.getMinPitch(), this.getMaxPitch()), this.getMinPitch(), this.getMaxPitch());
                this.volume = MathHelper.lerp(MathHelper.clamp(f, 0.0f, 0.5f), 0.0f, 1.2f);
            } else {
                this.pitch = 0.0f;
                this.volume = 0.0f;
            }
        } else {
            this.finishPlaying();
        }
    }

    private float getMinPitch() {
        return this.beeInstance.isChild() ? 1.1f : 0.7f;
    }

    private float getMaxPitch() {
        return this.beeInstance.isChild() ? 1.5f : 1.1f;
    }

    @Override
    public boolean canBeSilent() {
        return false;
    }

    @Override
    public boolean shouldPlaySound() {
        return !this.beeInstance.isSilent();
    }

    protected abstract TickableSound getNextSound();

    protected abstract boolean shouldSwitchSound();
}

