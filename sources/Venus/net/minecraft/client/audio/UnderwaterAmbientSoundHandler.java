/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.IAmbientSoundHandler;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.UnderwaterAmbientSounds;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundEvents;

public class UnderwaterAmbientSoundHandler
implements IAmbientSoundHandler {
    private final ClientPlayerEntity player;
    private final SoundHandler soundHandler;
    private int delay = 0;

    public UnderwaterAmbientSoundHandler(ClientPlayerEntity clientPlayerEntity, SoundHandler soundHandler) {
        this.player = clientPlayerEntity;
        this.soundHandler = soundHandler;
    }

    @Override
    public void tick() {
        --this.delay;
        if (this.delay <= 0 && this.player.canSwim()) {
            float f = this.player.world.rand.nextFloat();
            if (f < 1.0E-4f) {
                this.delay = 0;
                this.soundHandler.play(new UnderwaterAmbientSounds.SubSound(this.player, SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE));
            } else if (f < 0.001f) {
                this.delay = 0;
                this.soundHandler.play(new UnderwaterAmbientSounds.SubSound(this.player, SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE));
            } else if (f < 0.01f) {
                this.delay = 0;
                this.soundHandler.play(new UnderwaterAmbientSounds.SubSound(this.player, SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS));
            }
        }
    }
}

