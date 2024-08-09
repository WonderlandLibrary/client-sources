/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block.trees;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

public abstract class BigTree
extends Tree {
    @Override
    public boolean attemptGrowTree(ServerWorld serverWorld, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, Random random2) {
        for (int i = 0; i >= -1; --i) {
            for (int j = 0; j >= -1; --j) {
                if (!BigTree.canBigTreeSpawnAt(blockState, serverWorld, blockPos, i, j)) continue;
                return this.growBigTree(serverWorld, chunkGenerator, blockPos, blockState, random2, i, j);
            }
        }
        return super.attemptGrowTree(serverWorld, chunkGenerator, blockPos, blockState, random2);
    }

    @Nullable
    protected abstract ConfiguredFeature<BaseTreeFeatureConfig, ?> getHugeTreeFeature(Random var1);

    public boolean growBigTree(ServerWorld serverWorld, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, Random random2, int n, int n2) {
        ConfiguredFeature<BaseTreeFeatureConfig, ?> configuredFeature = this.getHugeTreeFeature(random2);
        if (configuredFeature == null) {
            return true;
        }
        ((BaseTreeFeatureConfig)configuredFeature.config).forcePlacement();
        BlockState blockState2 = Blocks.AIR.getDefaultState();
        serverWorld.setBlockState(blockPos.add(n, 0, n2), blockState2, 1);
        serverWorld.setBlockState(blockPos.add(n + 1, 0, n2), blockState2, 1);
        serverWorld.setBlockState(blockPos.add(n, 0, n2 + 1), blockState2, 1);
        serverWorld.setBlockState(blockPos.add(n + 1, 0, n2 + 1), blockState2, 1);
        if (configuredFeature.func_242765_a(serverWorld, chunkGenerator, random2, blockPos.add(n, 0, n2))) {
            return false;
        }
        serverWorld.setBlockState(blockPos.add(n, 0, n2), blockState, 1);
        serverWorld.setBlockState(blockPos.add(n + 1, 0, n2), blockState, 1);
        serverWorld.setBlockState(blockPos.add(n, 0, n2 + 1), blockState, 1);
        serverWorld.setBlockState(blockPos.add(n + 1, 0, n2 + 1), blockState, 1);
        return true;
    }

    public static boolean canBigTreeSpawnAt(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, int n, int n2) {
        Block block = blockState.getBlock();
        return block == iBlockReader.getBlockState(blockPos.add(n, 0, n2)).getBlock() && block == iBlockReader.getBlockState(blockPos.add(n + 1, 0, n2)).getBlock() && block == iBlockReader.getBlockState(blockPos.add(n, 0, n2 + 1)).getBlock() && block == iBlockReader.getBlockState(blockPos.add(n + 1, 0, n2 + 1)).getBlock();
    }
}

