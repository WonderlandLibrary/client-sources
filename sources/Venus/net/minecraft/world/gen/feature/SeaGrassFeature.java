/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class SeaGrassFeature
extends Feature<ProbabilityConfig> {
    public SeaGrassFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, ProbabilityConfig probabilityConfig) {
        boolean bl = false;
        int n = random2.nextInt(8) - random2.nextInt(8);
        int n2 = random2.nextInt(8) - random2.nextInt(8);
        int n3 = iSeedReader.getHeight(Heightmap.Type.OCEAN_FLOOR, blockPos.getX() + n, blockPos.getZ() + n2);
        BlockPos blockPos2 = new BlockPos(blockPos.getX() + n, n3, blockPos.getZ() + n2);
        if (iSeedReader.getBlockState(blockPos2).isIn(Blocks.WATER)) {
            BlockState blockState;
            boolean bl2 = random2.nextDouble() < (double)probabilityConfig.probability;
            BlockState blockState2 = blockState = bl2 ? Blocks.TALL_SEAGRASS.getDefaultState() : Blocks.SEAGRASS.getDefaultState();
            if (blockState.isValidPosition(iSeedReader, blockPos2)) {
                if (bl2) {
                    BlockState blockState3 = (BlockState)blockState.with(TallSeaGrassBlock.HALF, DoubleBlockHalf.UPPER);
                    BlockPos blockPos3 = blockPos2.up();
                    if (iSeedReader.getBlockState(blockPos3).isIn(Blocks.WATER)) {
                        iSeedReader.setBlockState(blockPos2, blockState, 2);
                        iSeedReader.setBlockState(blockPos3, blockState3, 2);
                    }
                } else {
                    iSeedReader.setBlockState(blockPos2, blockState, 2);
                }
                bl = true;
            }
        }
        return bl;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (ProbabilityConfig)iFeatureConfig);
    }
}

