/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum SmoothLayer implements ICastleTransformer
{
    INSTANCE;


    @Override
    public int apply(INoiseRandom iNoiseRandom, int n, int n2, int n3, int n4, int n5) {
        boolean bl;
        boolean bl2 = n2 == n4;
        boolean bl3 = bl = n == n3;
        if (bl2 == bl) {
            if (bl2) {
                return iNoiseRandom.random(2) == 0 ? n4 : n;
            }
            return n5;
        }
        return bl2 ? n4 : n;
    }
}

