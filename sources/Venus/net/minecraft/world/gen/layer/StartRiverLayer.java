/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public enum StartRiverLayer implements IC0Transformer
{
    INSTANCE;


    @Override
    public int apply(INoiseRandom iNoiseRandom, int n) {
        return LayerUtil.isShallowOcean(n) ? n : iNoiseRandom.random(299999) + 2;
    }
}

