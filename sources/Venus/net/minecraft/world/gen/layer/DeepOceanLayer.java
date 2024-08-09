/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum DeepOceanLayer implements ICastleTransformer
{
    INSTANCE;


    @Override
    public int apply(INoiseRandom iNoiseRandom, int n, int n2, int n3, int n4, int n5) {
        if (LayerUtil.isShallowOcean(n5)) {
            int n6 = 0;
            if (LayerUtil.isShallowOcean(n)) {
                ++n6;
            }
            if (LayerUtil.isShallowOcean(n2)) {
                ++n6;
            }
            if (LayerUtil.isShallowOcean(n4)) {
                ++n6;
            }
            if (LayerUtil.isShallowOcean(n3)) {
                ++n6;
            }
            if (n6 > 3) {
                if (n5 == 44) {
                    return 0;
                }
                if (n5 == 45) {
                    return 1;
                }
                if (n5 == 0) {
                    return 1;
                }
                if (n5 == 46) {
                    return 0;
                }
                if (n5 == 10) {
                    return 1;
                }
                return 1;
            }
        }
        return n5;
    }
}

