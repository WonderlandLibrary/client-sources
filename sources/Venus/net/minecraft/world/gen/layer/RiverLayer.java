/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum RiverLayer implements ICastleTransformer
{
    INSTANCE;


    @Override
    public int apply(INoiseRandom iNoiseRandom, int n, int n2, int n3, int n4, int n5) {
        int n6 = RiverLayer.riverFilter(n5);
        return n6 == RiverLayer.riverFilter(n4) && n6 == RiverLayer.riverFilter(n) && n6 == RiverLayer.riverFilter(n2) && n6 == RiverLayer.riverFilter(n3) ? -1 : 7;
    }

    private static int riverFilter(int n) {
        return n >= 2 ? 2 + (n & 1) : n;
    }
}

