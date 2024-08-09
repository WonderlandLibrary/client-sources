/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum IslandLayer implements IAreaTransformer0
{
    INSTANCE;


    @Override
    public int apply(INoiseRandom iNoiseRandom, int n, int n2) {
        if (n == 0 && n2 == 0) {
            return 0;
        }
        return iNoiseRandom.random(10) == 0 ? 1 : 0;
    }
}

