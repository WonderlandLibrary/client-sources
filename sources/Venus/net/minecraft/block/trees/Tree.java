/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block.trees;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

public abstract class Tree {
    @Nullable
    protected abstract ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random var1, boolean var2);

    public boolean attemptGrowTree(ServerWorld serverWorld, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, Random random2) {
        ConfiguredFeature<BaseTreeFeatureConfig, ?> configuredFeature = this.getTreeFeature(random2, this.hasNearbyFlora(serverWorld, blockPos));
        if (configuredFeature == null) {
            return true;
        }
        serverWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 1);
        ((BaseTreeFeatureConfig)configuredFeature.config).forcePlacement();
        if (configuredFeature.func_242765_a(serverWorld, chunkGenerator, random2, blockPos)) {
            return false;
        }
        serverWorld.setBlockState(blockPos, blockState, 1);
        return true;
    }

    private boolean hasNearbyFlora(IWorld iWorld, BlockPos blockPos) {
        for (BlockPos blockPos2 : BlockPos.Mutable.getAllInBoxMutable(blockPos.down().north(2).west(2), blockPos.up().south(2).east(2))) {
            if (!iWorld.getBlockState(blockPos2).isIn(BlockTags.FLOWERS)) continue;
            return false;
        }
        return true;
    }
}

