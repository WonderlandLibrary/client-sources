/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class SeaPickleFeature
extends Feature<FeatureSpreadConfig> {
    public SeaPickleFeature(Codec<FeatureSpreadConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, FeatureSpreadConfig featureSpreadConfig) {
        int n = 0;
        int n2 = featureSpreadConfig.func_242799_a().func_242259_a(random2);
        for (int i = 0; i < n2; ++i) {
            int n3 = random2.nextInt(8) - random2.nextInt(8);
            int n4 = random2.nextInt(8) - random2.nextInt(8);
            int n5 = iSeedReader.getHeight(Heightmap.Type.OCEAN_FLOOR, blockPos.getX() + n3, blockPos.getZ() + n4);
            BlockPos blockPos2 = new BlockPos(blockPos.getX() + n3, n5, blockPos.getZ() + n4);
            BlockState blockState = (BlockState)Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, random2.nextInt(4) + 1);
            if (!iSeedReader.getBlockState(blockPos2).isIn(Blocks.WATER) || !blockState.isValidPosition(iSeedReader, blockPos2)) continue;
            iSeedReader.setBlockState(blockPos2, blockState, 2);
            ++n;
        }
        return n > 0;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (FeatureSpreadConfig)iFeatureConfig);
    }
}

