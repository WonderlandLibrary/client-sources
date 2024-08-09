/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BlockPileFeature
extends Feature<BlockStateProvidingFeatureConfig> {
    public BlockPileFeature(Codec<BlockStateProvidingFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, BlockStateProvidingFeatureConfig blockStateProvidingFeatureConfig) {
        if (blockPos.getY() < 5) {
            return true;
        }
        int n = 2 + random2.nextInt(2);
        int n2 = 2 + random2.nextInt(2);
        for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(blockPos.add(-n, 0, -n2), blockPos.add(n, 1, n2))) {
            int n3;
            int n4 = blockPos.getX() - blockPos2.getX();
            if ((float)(n4 * n4 + (n3 = blockPos.getZ() - blockPos2.getZ()) * n3) <= random2.nextFloat() * 10.0f - random2.nextFloat() * 6.0f) {
                this.func_227225_a_(iSeedReader, blockPos2, random2, blockStateProvidingFeatureConfig);
                continue;
            }
            if (!((double)random2.nextFloat() < 0.031)) continue;
            this.func_227225_a_(iSeedReader, blockPos2, random2, blockStateProvidingFeatureConfig);
        }
        return false;
    }

    private boolean canPlaceOn(IWorld iWorld, BlockPos blockPos, Random random2) {
        BlockPos blockPos2 = blockPos.down();
        BlockState blockState = iWorld.getBlockState(blockPos2);
        return blockState.isIn(Blocks.GRASS_PATH) ? random2.nextBoolean() : blockState.isSolidSide(iWorld, blockPos2, Direction.UP);
    }

    private void func_227225_a_(IWorld iWorld, BlockPos blockPos, Random random2, BlockStateProvidingFeatureConfig blockStateProvidingFeatureConfig) {
        if (iWorld.isAirBlock(blockPos) && this.canPlaceOn(iWorld, blockPos, random2)) {
            iWorld.setBlockState(blockPos, blockStateProvidingFeatureConfig.field_227268_a_.getBlockState(random2, blockPos), 4);
        }
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (BlockStateProvidingFeatureConfig)iFeatureConfig);
    }
}

