/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FillLayerConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class FillLayerFeature
extends Feature<FillLayerConfig> {
    public FillLayerFeature(Codec<FillLayerConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, FillLayerConfig fillLayerConfig) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                int n = blockPos.getX() + i;
                int n2 = blockPos.getZ() + j;
                int n3 = fillLayerConfig.height;
                mutable.setPos(n, n3, n2);
                if (!iSeedReader.getBlockState(mutable).isAir()) continue;
                iSeedReader.setBlockState(mutable, fillLayerConfig.state, 2);
            }
        }
        return false;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (FillLayerConfig)iFeatureConfig);
    }
}

