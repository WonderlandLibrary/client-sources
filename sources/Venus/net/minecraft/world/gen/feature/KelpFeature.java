/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.KelpTopBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class KelpFeature
extends Feature<NoFeatureConfig> {
    public KelpFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        int n = 0;
        int n2 = iSeedReader.getHeight(Heightmap.Type.OCEAN_FLOOR, blockPos.getX(), blockPos.getZ());
        BlockPos blockPos2 = new BlockPos(blockPos.getX(), n2, blockPos.getZ());
        if (iSeedReader.getBlockState(blockPos2).isIn(Blocks.WATER)) {
            BlockState blockState = Blocks.KELP.getDefaultState();
            BlockState blockState2 = Blocks.KELP_PLANT.getDefaultState();
            int n3 = 1 + random2.nextInt(10);
            for (int i = 0; i <= n3; ++i) {
                if (iSeedReader.getBlockState(blockPos2).isIn(Blocks.WATER) && iSeedReader.getBlockState(blockPos2.up()).isIn(Blocks.WATER) && blockState2.isValidPosition(iSeedReader, blockPos2)) {
                    if (i == n3) {
                        iSeedReader.setBlockState(blockPos2, (BlockState)blockState.with(KelpTopBlock.AGE, random2.nextInt(4) + 20), 2);
                        ++n;
                    } else {
                        iSeedReader.setBlockState(blockPos2, blockState2, 2);
                    }
                } else if (i > 0) {
                    BlockPos blockPos3 = blockPos2.down();
                    if (!blockState.isValidPosition(iSeedReader, blockPos3) || iSeedReader.getBlockState(blockPos3.down()).isIn(Blocks.KELP)) break;
                    iSeedReader.setBlockState(blockPos3, (BlockState)blockState.with(KelpTopBlock.AGE, random2.nextInt(4) + 20), 2);
                    ++n;
                    break;
                }
                blockPos2 = blockPos2.up();
            }
        }
        return n > 0;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

