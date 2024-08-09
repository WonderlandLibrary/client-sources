/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum OceanLayer implements IAreaTransformer0
{
    INSTANCE;


    @Override
    public int apply(INoiseRandom iNoiseRandom, int n, int n2) {
        ImprovedNoiseGenerator improvedNoiseGenerator = iNoiseRandom.getNoiseGenerator();
        double d = improvedNoiseGenerator.func_215456_a((double)n / 8.0, (double)n2 / 8.0, 0.0, 0.0, 0.0);
        if (d > 0.4) {
            return 1;
        }
        if (d > 0.2) {
            return 0;
        }
        if (d < -0.4) {
            return 1;
        }
        return d < -0.2 ? 46 : 0;
    }
}

