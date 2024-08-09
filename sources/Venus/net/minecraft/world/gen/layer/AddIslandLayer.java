/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IBishopTransformer;

public enum AddIslandLayer implements IBishopTransformer
{
    INSTANCE;


    @Override
    public int apply(INoiseRandom iNoiseRandom, int n, int n2, int n3, int n4, int n5) {
        if (!LayerUtil.isShallowOcean(n5) || LayerUtil.isShallowOcean(n4) && LayerUtil.isShallowOcean(n3) && LayerUtil.isShallowOcean(n) && LayerUtil.isShallowOcean(n2)) {
            if (!LayerUtil.isShallowOcean(n5) && (LayerUtil.isShallowOcean(n4) || LayerUtil.isShallowOcean(n) || LayerUtil.isShallowOcean(n3) || LayerUtil.isShallowOcean(n2)) && iNoiseRandom.random(5) == 0) {
                if (LayerUtil.isShallowOcean(n4)) {
                    return n5 == 4 ? 4 : n4;
                }
                if (LayerUtil.isShallowOcean(n)) {
                    return n5 == 4 ? 4 : n;
                }
                if (LayerUtil.isShallowOcean(n3)) {
                    return n5 == 4 ? 4 : n3;
                }
                if (LayerUtil.isShallowOcean(n2)) {
                    return n5 == 4 ? 4 : n2;
                }
            }
            return n5;
        }
        int n6 = 1;
        int n7 = 1;
        if (!LayerUtil.isShallowOcean(n4) && iNoiseRandom.random(n6++) == 0) {
            n7 = n4;
        }
        if (!LayerUtil.isShallowOcean(n3) && iNoiseRandom.random(n6++) == 0) {
            n7 = n3;
        }
        if (!LayerUtil.isShallowOcean(n) && iNoiseRandom.random(n6++) == 0) {
            n7 = n;
        }
        if (!LayerUtil.isShallowOcean(n2) && iNoiseRandom.random(n6++) == 0) {
            n7 = n2;
        }
        if (iNoiseRandom.random(3) == 0) {
            return n7;
        }
        return n7 == 4 ? 4 : n5;
    }
}

