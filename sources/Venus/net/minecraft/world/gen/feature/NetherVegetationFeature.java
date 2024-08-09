/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class NetherVegetationFeature
extends Feature<BlockStateProvidingFeatureConfig> {
    public NetherVegetationFeature(Codec<BlockStateProvidingFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, BlockStateProvidingFeatureConfig blockStateProvidingFeatureConfig) {
        return NetherVegetationFeature.func_236325_a_(iSeedReader, random2, blockPos, blockStateProvidingFeatureConfig, 8, 4);
    }

    public static boolean func_236325_a_(IWorld iWorld, Random random2, BlockPos blockPos, BlockStateProvidingFeatureConfig blockStateProvidingFeatureConfig, int n, int n2) {
        Block block = iWorld.getBlockState(blockPos.down()).getBlock();
        if (!block.isIn(BlockTags.NYLIUM)) {
            return true;
        }
        int n3 = blockPos.getY();
        if (n3 >= 1 && n3 + 1 < 256) {
            int n4 = 0;
            for (int i = 0; i < n * n; ++i) {
                BlockPos blockPos2 = blockPos.add(random2.nextInt(n) - random2.nextInt(n), random2.nextInt(n2) - random2.nextInt(n2), random2.nextInt(n) - random2.nextInt(n));
                BlockState blockState = blockStateProvidingFeatureConfig.field_227268_a_.getBlockState(random2, blockPos2);
                if (!iWorld.isAirBlock(blockPos2) || blockPos2.getY() <= 0 || !blockState.isValidPosition(iWorld, blockPos2)) continue;
                iWorld.setBlockState(blockPos2, blockState, 2);
                ++n4;
            }
            return n4 > 0;
        }
        return true;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (BlockStateProvidingFeatureConfig)iFeatureConfig);
    }
}

