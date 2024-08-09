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
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;

public class TwoFeatureChoiceFeature
extends Feature<TwoFeatureChoiceConfig> {
    public TwoFeatureChoiceFeature(Codec<TwoFeatureChoiceConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, TwoFeatureChoiceConfig twoFeatureChoiceConfig) {
        boolean bl = random2.nextBoolean();
        return bl ? twoFeatureChoiceConfig.field_227285_a_.get().func_242765_a(iSeedReader, chunkGenerator, random2, blockPos) : twoFeatureChoiceConfig.field_227286_b_.get().func_242765_a(iSeedReader, chunkGenerator, random2, blockPos);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (TwoFeatureChoiceConfig)iFeatureConfig);
    }
}

