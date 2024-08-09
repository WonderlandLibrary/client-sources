/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.AbstractSphereReplaceConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;

public class SphereReplaceFeature
extends AbstractSphereReplaceConfig {
    public SphereReplaceFeature(Codec<SphereReplaceConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, SphereReplaceConfig sphereReplaceConfig) {
        return !iSeedReader.getFluidState(blockPos).isTagged(FluidTags.WATER) ? false : super.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, sphereReplaceConfig);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (SphereReplaceConfig)iFeatureConfig);
    }
}

