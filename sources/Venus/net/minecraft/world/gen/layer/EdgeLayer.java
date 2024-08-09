/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public class EdgeLayer {

    public static enum Special implements IC0Transformer
    {
        INSTANCE;


        @Override
        public int apply(INoiseRandom iNoiseRandom, int n) {
            if (!LayerUtil.isShallowOcean(n) && iNoiseRandom.random(13) == 0) {
                n |= 1 + iNoiseRandom.random(15) << 8 & 0xF00;
            }
            return n;
        }
    }

    public static enum HeatIce implements ICastleTransformer
    {
        INSTANCE;


        @Override
        public int apply(INoiseRandom iNoiseRandom, int n, int n2, int n3, int n4, int n5) {
            return n5 != 4 || n != 1 && n2 != 1 && n4 != 1 && n3 != 1 && n != 2 && n2 != 2 && n4 != 2 && n3 != 2 ? n5 : 3;
        }
    }

    public static enum CoolWarm implements ICastleTransformer
    {
        INSTANCE;


        @Override
        public int apply(INoiseRandom iNoiseRandom, int n, int n2, int n3, int n4, int n5) {
            return n5 != 1 || n != 3 && n2 != 3 && n4 != 3 && n3 != 3 && n != 4 && n2 != 4 && n4 != 4 && n3 != 4 ? n5 : 2;
        }
    }
}

