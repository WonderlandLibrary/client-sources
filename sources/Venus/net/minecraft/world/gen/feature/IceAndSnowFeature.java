/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class IceAndSnowFeature
extends Feature<NoFeatureConfig> {
    public IceAndSnowFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, NoFeatureConfig noFeatureConfig) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                int n = blockPos.getX() + i;
                int n2 = blockPos.getZ() + j;
                int n3 = iSeedReader.getHeight(Heightmap.Type.MOTION_BLOCKING, n, n2);
                mutable.setPos(n, n3, n2);
                mutable2.setPos(mutable).move(Direction.DOWN, 1);
                Biome biome = iSeedReader.getBiome(mutable);
                if (biome.doesWaterFreeze(iSeedReader, mutable2, true)) {
                    iSeedReader.setBlockState(mutable2, Blocks.ICE.getDefaultState(), 2);
                }
                if (!biome.doesSnowGenerate(iSeedReader, mutable)) continue;
                iSeedReader.setBlockState(mutable, Blocks.SNOW.getDefaultState(), 2);
                BlockState blockState = iSeedReader.getBlockState(mutable2);
                if (!blockState.hasProperty(SnowyDirtBlock.SNOWY)) continue;
                iSeedReader.setBlockState(mutable2, (BlockState)blockState.with(SnowyDirtBlock.SNOWY, true), 2);
            }
        }
        return false;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (NoFeatureConfig)iFeatureConfig);
    }
}

