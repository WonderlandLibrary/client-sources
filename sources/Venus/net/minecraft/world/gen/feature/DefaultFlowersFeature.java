/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class DefaultFlowersFeature
extends FlowersFeature<BlockClusterFeatureConfig> {
    public DefaultFlowersFeature(Codec<BlockClusterFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean isValidPosition(IWorld iWorld, BlockPos blockPos, BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return !blockClusterFeatureConfig.blacklist.contains(iWorld.getBlockState(blockPos));
    }

    @Override
    public int getFlowerCount(BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return blockClusterFeatureConfig.tryCount;
    }

    @Override
    public BlockPos getNearbyPos(Random random2, BlockPos blockPos, BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return blockPos.add(random2.nextInt(blockClusterFeatureConfig.xSpread) - random2.nextInt(blockClusterFeatureConfig.xSpread), random2.nextInt(blockClusterFeatureConfig.ySpread) - random2.nextInt(blockClusterFeatureConfig.ySpread), random2.nextInt(blockClusterFeatureConfig.zSpread) - random2.nextInt(blockClusterFeatureConfig.zSpread));
    }

    @Override
    public BlockState getFlowerToPlace(Random random2, BlockPos blockPos, BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return blockClusterFeatureConfig.stateProvider.getBlockState(random2, blockPos);
    }

    @Override
    public BlockState getFlowerToPlace(Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.getFlowerToPlace(random2, blockPos, (BlockClusterFeatureConfig)iFeatureConfig);
    }

    @Override
    public BlockPos getNearbyPos(Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.getNearbyPos(random2, blockPos, (BlockClusterFeatureConfig)iFeatureConfig);
    }

    @Override
    public int getFlowerCount(IFeatureConfig iFeatureConfig) {
        return this.getFlowerCount((BlockClusterFeatureConfig)iFeatureConfig);
    }

    @Override
    public boolean isValidPosition(IWorld iWorld, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.isValidPosition(iWorld, blockPos, (BlockClusterFeatureConfig)iFeatureConfig);
    }
}

