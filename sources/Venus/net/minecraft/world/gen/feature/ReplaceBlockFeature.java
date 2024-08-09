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
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;

public class ReplaceBlockFeature
extends Feature<ReplaceBlockConfig> {
    public ReplaceBlockFeature(Codec<ReplaceBlockConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, ReplaceBlockConfig replaceBlockConfig) {
        if (iSeedReader.getBlockState(blockPos).isIn(replaceBlockConfig.target.getBlock())) {
            iSeedReader.setBlockState(blockPos, replaceBlockConfig.state, 2);
        }
        return false;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (ReplaceBlockConfig)iFeatureConfig);
    }
}

