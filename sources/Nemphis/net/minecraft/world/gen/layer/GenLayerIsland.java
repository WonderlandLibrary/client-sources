/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerIsland
extends GenLayer {
    private static final String __OBFID = "CL_00000558";

    public GenLayerIsland(long p_i2124_1_) {
        super(p_i2124_1_);
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] var5 = IntCache.getIntCache(areaWidth * areaHeight);
        int var6 = 0;
        while (var6 < areaHeight) {
            int var7 = 0;
            while (var7 < areaWidth) {
                this.initChunkSeed(areaX + var7, areaY + var6);
                var5[var7 + var6 * areaWidth] = this.nextInt(10) == 0 ? 1 : 0;
                ++var7;
            }
            ++var6;
        }
        if (areaX > - areaWidth && areaX <= 0 && areaY > - areaHeight && areaY <= 0) {
            var5[- areaX + (- areaY) * areaWidth] = 1;
        }
        return var5;
    }
}

