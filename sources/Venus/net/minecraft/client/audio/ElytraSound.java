/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.TickableSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class ElytraSound
extends TickableSound {
    private final ClientPlayerEntity player;
    private int time;

    public ElytraSound(ClientPlayerEntity clientPlayerEntity) {
        super(SoundEvents.ITEM_ELYTRA_FLYING, SoundCategory.PLAYERS);
        this.player = clientPlayerEntity;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.1f;
    }

    @Override
    public void tick() {
        ++this.time;
        if (!this.player.removed && (this.time <= 20 || this.player.isElytraFlying())) {
            this.x = (float)this.player.getPosX();
            this.y = (float)this.player.getPosY();
            this.z = (float)this.player.getPosZ();
            float f = (float)this.player.getMotion().lengthSquared();
            this.volume = (double)f >= 1.0E-7 ? MathHelper.clamp(f / 4.0f, 0.0f, 1.0f) : 0.0f;
            if (this.time < 20) {
                this.volume = 0.0f;
            } else if (this.time < 40) {
                this.volume = (float)((double)this.volume * ((double)(this.time - 20) / 20.0));
            }
            float f2 = 0.8f;
            this.pitch = this.volume > 0.8f ? 1.0f + (this.volume - 0.8f) : 1.0f;
        } else {
            this.finishPlaying();
        }
    }
}

