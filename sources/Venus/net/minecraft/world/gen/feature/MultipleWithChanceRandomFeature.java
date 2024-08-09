/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredRandomFeatureList;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;

public class MultipleWithChanceRandomFeature
extends Feature<MultipleRandomFeatureConfig> {
    public MultipleWithChanceRandomFeature(Codec<MultipleRandomFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, MultipleRandomFeatureConfig multipleRandomFeatureConfig) {
        for (ConfiguredRandomFeatureList configuredRandomFeatureList : multipleRandomFeatureConfig.features) {
            if (!(random2.nextFloat() < configuredRandomFeatureList.chance)) continue;
            return configuredRandomFeatureList.func_242787_a(iSeedReader, chunkGenerator, random2, blockPos);
        }
        return multipleRandomFeatureConfig.defaultFeature.get().func_242765_a(iSeedReader, chunkGenerator, random2, blockPos);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (MultipleRandomFeatureConfig)iFeatureConfig);
    }
}

