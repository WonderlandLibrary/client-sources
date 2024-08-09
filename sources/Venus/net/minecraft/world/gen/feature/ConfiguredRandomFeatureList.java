/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ConfiguredRandomFeatureList {
    public static final Codec<ConfiguredRandomFeatureList> field_236430_a_ = RecordCodecBuilder.create(ConfiguredRandomFeatureList::lambda$static$2);
    public final Supplier<ConfiguredFeature<?, ?>> feature;
    public final float chance;

    public ConfiguredRandomFeatureList(ConfiguredFeature<?, ?> configuredFeature, float f) {
        this(() -> ConfiguredRandomFeatureList.lambda$new$3(configuredFeature), f);
    }

    private ConfiguredRandomFeatureList(Supplier<ConfiguredFeature<?, ?>> supplier, float f) {
        this.feature = supplier;
        this.chance = f;
    }

    public boolean func_242787_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos) {
        return this.feature.get().func_242765_a(iSeedReader, chunkGenerator, random2, blockPos);
    }

    private static ConfiguredFeature lambda$new$3(ConfiguredFeature configuredFeature) {
        return configuredFeature;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)ConfiguredFeature.field_236264_b_.fieldOf("feature")).forGetter(ConfiguredRandomFeatureList::lambda$static$0), ((MapCodec)Codec.floatRange(0.0f, 1.0f).fieldOf("chance")).forGetter(ConfiguredRandomFeatureList::lambda$static$1)).apply(instance, ConfiguredRandomFeatureList::new);
    }

    private static Float lambda$static$1(ConfiguredRandomFeatureList configuredRandomFeatureList) {
        return Float.valueOf(configuredRandomFeatureList.chance);
    }

    private static Supplier lambda$static$0(ConfiguredRandomFeatureList configuredRandomFeatureList) {
        return configuredRandomFeatureList.feature;
    }
}

