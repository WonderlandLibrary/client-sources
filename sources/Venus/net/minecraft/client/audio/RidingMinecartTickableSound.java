/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class RidingMinecartTickableSound
extends TickableSound {
    private final PlayerEntity player;
    private final AbstractMinecartEntity minecart;

    public RidingMinecartTickableSound(PlayerEntity playerEntity, AbstractMinecartEntity abstractMinecartEntity) {
        super(SoundEvents.ENTITY_MINECART_INSIDE, SoundCategory.NEUTRAL);
        this.player = playerEntity;
        this.minecart = abstractMinecartEntity;
        this.attenuationType = ISound.AttenuationType.NONE;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.0f;
    }

    @Override
    public boolean shouldPlaySound() {
        return !this.minecart.isSilent();
    }

    @Override
    public boolean canBeSilent() {
        return false;
    }

    @Override
    public void tick() {
        if (!this.minecart.removed && this.player.isPassenger() && this.player.getRidingEntity() == this.minecart) {
            float f = MathHelper.sqrt(Entity.horizontalMag(this.minecart.getMotion()));
            this.volume = (double)f >= 0.01 ? 0.0f + MathHelper.clamp(f, 0.0f, 1.0f) * 0.75f : 0.0f;
        } else {
            this.finishPlaying();
        }
    }
}

