/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IBishopTransformer;

public enum AddMushroomIslandLayer implements IBishopTransformer
{
    INSTANCE;


    @Override
    public int apply(INoiseRandom iNoiseRandom, int n, int n2, int n3, int n4, int n5) {
        return LayerUtil.isShallowOcean(n5) && LayerUtil.isShallowOcean(n4) && LayerUtil.isShallowOcean(n) && LayerUtil.isShallowOcean(n3) && LayerUtil.isShallowOcean(n2) && iNoiseRandom.random(100) == 0 ? 14 : n5;
    }
}

