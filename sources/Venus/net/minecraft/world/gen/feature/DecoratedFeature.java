/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class DecoratedFeature
extends Feature<DecoratedFeatureConfig> {
    public DecoratedFeature(Codec<DecoratedFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, DecoratedFeatureConfig decoratedFeatureConfig) {
        MutableBoolean mutableBoolean = new MutableBoolean();
        decoratedFeatureConfig.decorator.func_242876_a(new WorldDecoratingHelper(iSeedReader, chunkGenerator), random2, blockPos).forEach(arg_0 -> DecoratedFeature.lambda$func_241855_a$0(decoratedFeatureConfig, iSeedReader, chunkGenerator, random2, mutableBoolean, arg_0));
        return mutableBoolean.isTrue();
    }

    public String toString() {
        return String.format("< %s [%s] >", this.getClass().getSimpleName(), Registry.FEATURE.getKey(this));
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (DecoratedFeatureConfig)iFeatureConfig);
    }

    private static void lambda$func_241855_a$0(DecoratedFeatureConfig decoratedFeatureConfig, ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, MutableBoolean mutableBoolean, BlockPos blockPos) {
        if (decoratedFeatureConfig.feature.get().func_242765_a(iSeedReader, chunkGenerator, random2, blockPos)) {
            mutableBoolean.setTrue();
        }
    }
}

