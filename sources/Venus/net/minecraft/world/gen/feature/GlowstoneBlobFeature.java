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
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class GlowstoneBlobFeature
extends Feature<NoFeatureConfig> {
    public GlowstoneBlobFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        if (!iSeedReader.isAirBlock(blockPos)) {
            return true;
        }
        BlockState blockState = iSeedReader.getBlockState(blockPos.up());
        if (!(blockState.isIn(Blocks.NETHERRACK) || blockState.isIn(Blocks.BASALT) || blockState.isIn(Blocks.BLACKSTONE))) {
            return true;
        }
        iSeedReader.setBlockState(blockPos, Blocks.GLOWSTONE.getDefaultState(), 2);
        for (int i = 0; i < 1500; ++i) {
            BlockPos blockPos2 = blockPos.add(random2.nextInt(8) - random2.nextInt(8), -random2.nextInt(12), random2.nextInt(8) - random2.nextInt(8));
            if (!iSeedReader.getBlockState(blockPos2).isAir()) continue;
            int n = 0;
            for (Direction direction : Direction.values()) {
                if (iSeedReader.getBlockState(blockPos2.offset(direction)).isIn(Blocks.GLOWSTONE)) {
                    ++n;
                }
                if (n > 1) break;
            }
            if (n != true) continue;
            iSeedReader.setBlockState(blockPos2, Blocks.GLOWSTONE.getDefaultState(), 2);
        }
        return false;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

