/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

public enum AddSnowLayer implements IC1Transformer
{
    INSTANCE;


    @Override
    public int apply(INoiseRandom iNoiseRandom, int n) {
        if (LayerUtil.isShallowOcean(n)) {
            return n;
        }
        int n2 = iNoiseRandom.random(6);
        if (n2 == 0) {
            return 1;
        }
        return n2 == 1 ? 3 : 1;
    }
}

