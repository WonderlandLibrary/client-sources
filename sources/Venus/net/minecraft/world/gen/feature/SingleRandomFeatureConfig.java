/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.SingleRandomFeature;

public class SingleRandomFeatureConfig
extends Feature<SingleRandomFeature> {
    public SingleRandomFeatureConfig(Codec<SingleRandomFeature> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, SingleRandomFeature singleRandomFeature) {
        int n = random2.nextInt(singleRandomFeature.features.size());
        ConfiguredFeature<?, ?> configuredFeature = singleRandomFeature.features.get(n).get();
        return configuredFeature.func_242765_a(iSeedReader, chunkGenerator, random2, blockPos);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (SingleRandomFeature)iFeatureConfig);
    }
}

