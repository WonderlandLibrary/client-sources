/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ProbabilityConfig
implements ICarverConfig,
IFeatureConfig {
    public static final Codec<ProbabilityConfig> field_236576_b_ = RecordCodecBuilder.create(ProbabilityConfig::lambda$static$1);
    public final float probability;

    public ProbabilityConfig(float f) {
        this.probability = f;
    }

    private static App lambda$static$1(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.floatRange(0.0f, 1.0f).fieldOf("probability")).forGetter(ProbabilityConfig::lambda$static$0)).apply(instance, ProbabilityConfig::new);
    }

    private static Float lambda$static$0(ProbabilityConfig probabilityConfig) {
        return Float.valueOf(probabilityConfig.probability);
    }
}

