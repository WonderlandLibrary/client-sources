/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.lighting.WorldLightManager;

public interface IBlockDisplayReader
extends IBlockReader {
    public float func_230487_a_(Direction var1, boolean var2);

    public WorldLightManager getLightManager();

    public int getBlockColor(BlockPos var1, ColorResolver var2);

    default public int getLightFor(LightType lightType, BlockPos blockPos) {
        return this.getLightManager().getLightEngine(lightType).getLightFor(blockPos);
    }

    default public int getLightSubtracted(BlockPos blockPos, int n) {
        return this.getLightManager().getLightSubtracted(blockPos, n);
    }

    default public boolean canSeeSky(BlockPos blockPos) {
        return this.getLightFor(LightType.SKY, blockPos) >= this.getMaxLightLevel();
    }
}

