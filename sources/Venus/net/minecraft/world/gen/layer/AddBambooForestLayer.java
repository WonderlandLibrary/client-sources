/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

public enum AddBambooForestLayer implements IC1Transformer
{
    INSTANCE;


    @Override
    public int apply(INoiseRandom iNoiseRandom, int n) {
        return iNoiseRandom.random(10) == 0 && n == 21 ? 168 : n;
    }
}

