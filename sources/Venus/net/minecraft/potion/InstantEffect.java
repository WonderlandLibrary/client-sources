/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class InstantEffect
extends Effect {
    public InstantEffect(EffectType effectType, int n) {
        super(effectType, n);
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public boolean isReady(int n, int n2) {
        return n >= 1;
    }
}

