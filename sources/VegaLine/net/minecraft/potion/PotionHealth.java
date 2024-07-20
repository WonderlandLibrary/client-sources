/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import net.minecraft.potion.Potion;

public class PotionHealth
extends Potion {
    public PotionHealth(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration >= 1;
    }
}

