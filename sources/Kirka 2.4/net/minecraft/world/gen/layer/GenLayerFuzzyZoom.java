/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class GenLayerFuzzyZoom
extends GenLayerZoom {
    private static final String __OBFID = "CL_00000556";

    public GenLayerFuzzyZoom(long p_i2123_1_, GenLayer p_i2123_3_) {
        super(p_i2123_1_, p_i2123_3_);
    }

    @Override
    protected int selectModeOrRandom(int p_151617_1_, int p_151617_2_, int p_151617_3_, int p_151617_4_) {
        return this.selectRandom(p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_);
    }
}

