/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;

public class DecoratedFeatureConfig
implements IFeatureConfig {
    public static final Codec<DecoratedFeatureConfig> field_236491_a_ = RecordCodecBuilder.create(DecoratedFeatureConfig::lambda$static$2);
    public final Supplier<ConfiguredFeature<?, ?>> feature;
    public final ConfiguredPlacement<?> decorator;

    public DecoratedFeatureConfig(Supplier<ConfiguredFeature<?, ?>> supplier, ConfiguredPlacement<?> configuredPlacement) {
        this.feature = supplier;
        this.decorator = configuredPlacement;
    }

    public String toString() {
        return String.format("< %s [%s | %s] >", this.getClass().getSimpleName(), Registry.FEATURE.getKey((Feature<?>)this.feature.get().func_242766_b()), this.decorator);
    }

    @Override
    public Stream<ConfiguredFeature<?, ?>> func_241856_an_() {
        return this.feature.get().func_242768_d();
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)ConfiguredFeature.field_236264_b_.fieldOf("feature")).forGetter(DecoratedFeatureConfig::lambda$static$0), ((MapCodec)ConfiguredPlacement.field_236952_a_.fieldOf("decorator")).forGetter(DecoratedFeatureConfig::lambda$static$1)).apply(instance, DecoratedFeatureConfig::new);
    }

    private static ConfiguredPlacement lambda$static$1(DecoratedFeatureConfig decoratedFeatureConfig) {
        return decoratedFeatureConfig.decorator;
    }

    private static Supplier lambda$static$0(DecoratedFeatureConfig decoratedFeatureConfig) {
        return decoratedFeatureConfig.feature;
    }
}

