/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.BeeFlightSound;
import net.minecraft.client.audio.BeeSound;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class BeeAngrySound
extends BeeSound {
    public BeeAngrySound(BeeEntity beeEntity) {
        super(beeEntity, SoundEvents.ENTITY_BEE_LOOP_AGGRESSIVE, SoundCategory.NEUTRAL);
        this.repeatDelay = 0;
    }

    @Override
    protected TickableSound getNextSound() {
        return new BeeFlightSound(this.beeInstance);
    }

    @Override
    protected boolean shouldSwitchSound() {
        return !this.beeInstance.func_233678_J__();
    }
}

