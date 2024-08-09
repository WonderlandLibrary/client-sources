/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class EntityTickableSound
extends TickableSound {
    private final Entity entity;

    public EntityTickableSound(SoundEvent soundEvent, SoundCategory soundCategory, Entity entity2) {
        this(soundEvent, soundCategory, 1.0f, 1.0f, entity2);
    }

    public EntityTickableSound(SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2, Entity entity2) {
        super(soundEvent, soundCategory);
        this.volume = f;
        this.pitch = f2;
        this.entity = entity2;
        this.x = (float)this.entity.getPosX();
        this.y = (float)this.entity.getPosY();
        this.z = (float)this.entity.getPosZ();
    }

    @Override
    public boolean shouldPlaySound() {
        return !this.entity.isSilent();
    }

    @Override
    public void tick() {
        if (this.entity.removed) {
            this.finishPlaying();
        } else {
            this.x = (float)this.entity.getPosX();
            this.y = (float)this.entity.getPosY();
            this.z = (float)this.entity.getPosZ();
        }
    }
}

