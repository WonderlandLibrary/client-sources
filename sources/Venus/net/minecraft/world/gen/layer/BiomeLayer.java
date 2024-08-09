/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public class BiomeLayer
implements IC0Transformer {
    private static final int[] field_202743_q = new int[]{2, 4, 3, 6, 1, 5};
    private static final int[] field_202744_r = new int[]{2, 2, 2, 35, 35, 1};
    private static final int[] field_202745_s = new int[]{4, 29, 3, 1, 27, 6};
    private static final int[] field_202746_t = new int[]{4, 3, 5, 1};
    private static final int[] field_202747_u = new int[]{12, 12, 12, 30};
    private int[] warmBiomes = field_202744_r;

    public BiomeLayer(boolean bl) {
        if (bl) {
            this.warmBiomes = field_202743_q;
        }
    }

    @Override
    public int apply(INoiseRandom iNoiseRandom, int n) {
        int n2 = (n & 0xF00) >> 8;
        if (!LayerUtil.isOcean(n &= 0xFFFFF0FF) && n != 14) {
            switch (n) {
                case 1: {
                    if (n2 > 0) {
                        return iNoiseRandom.random(3) == 0 ? 39 : 38;
                    }
                    return this.warmBiomes[iNoiseRandom.random(this.warmBiomes.length)];
                }
                case 2: {
                    if (n2 > 0) {
                        return 0;
                    }
                    return field_202745_s[iNoiseRandom.random(field_202745_s.length)];
                }
                case 3: {
                    if (n2 > 0) {
                        return 1;
                    }
                    return field_202746_t[iNoiseRandom.random(field_202746_t.length)];
                }
                case 4: {
                    return field_202747_u[iNoiseRandom.random(field_202747_u.length)];
                }
            }
            return 1;
        }
        return n;
    }
}

