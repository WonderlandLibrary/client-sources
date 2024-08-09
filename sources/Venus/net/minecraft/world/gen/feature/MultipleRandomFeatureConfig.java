/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredRandomFeatureList;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class MultipleRandomFeatureConfig
implements IFeatureConfig {
    public static final Codec<MultipleRandomFeatureConfig> field_236583_a_ = RecordCodecBuilder.create(MultipleRandomFeatureConfig::lambda$static$2);
    public final List<ConfiguredRandomFeatureList> features;
    public final Supplier<ConfiguredFeature<?, ?>> defaultFeature;

    public MultipleRandomFeatureConfig(List<ConfiguredRandomFeatureList> list, ConfiguredFeature<?, ?> configuredFeature) {
        this(list, () -> MultipleRandomFeatureConfig.lambda$new$3(configuredFeature));
    }

    private MultipleRandomFeatureConfig(List<ConfiguredRandomFeatureList> list, Supplier<ConfiguredFeature<?, ?>> supplier) {
        this.features = list;
        this.defaultFeature = supplier;
    }

    @Override
    public Stream<ConfiguredFeature<?, ?>> func_241856_an_() {
        return Stream.concat(this.features.stream().flatMap(MultipleRandomFeatureConfig::lambda$func_241856_an_$4), this.defaultFeature.get().func_242768_d());
    }

    private static Stream lambda$func_241856_an_$4(ConfiguredRandomFeatureList configuredRandomFeatureList) {
        return configuredRandomFeatureList.feature.get().func_242768_d();
    }

    private static ConfiguredFeature lambda$new$3(ConfiguredFeature configuredFeature) {
        return configuredFeature;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.apply2(MultipleRandomFeatureConfig::new, ((MapCodec)ConfiguredRandomFeatureList.field_236430_a_.listOf().fieldOf("features")).forGetter(MultipleRandomFeatureConfig::lambda$static$0), ((MapCodec)ConfiguredFeature.field_236264_b_.fieldOf("default")).forGetter(MultipleRandomFeatureConfig::lambda$static$1));
    }

    private static Supplier lambda$static$1(MultipleRandomFeatureConfig multipleRandomFeatureConfig) {
        return multipleRandomFeatureConfig.defaultFeature;
    }

    private static List lambda$static$0(MultipleRandomFeatureConfig multipleRandomFeatureConfig) {
        return multipleRandomFeatureConfig.features;
    }
}

