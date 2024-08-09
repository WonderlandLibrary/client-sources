/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IDecoratable;
import net.minecraft.world.gen.feature.ConfiguredRandomFeatureList;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfiguredFeature<FC extends IFeatureConfig, F extends Feature<FC>>
implements IDecoratable<ConfiguredFeature<?, ?>> {
    public static final Codec<ConfiguredFeature<?, ?>> field_242763_a = Registry.FEATURE.dispatch(ConfiguredFeature::lambda$static$0, Feature::getCodec);
    public static final Codec<Supplier<ConfiguredFeature<?, ?>>> field_236264_b_ = RegistryKeyCodec.create(Registry.CONFIGURED_FEATURE_KEY, field_242763_a);
    public static final Codec<List<Supplier<ConfiguredFeature<?, ?>>>> field_242764_c = RegistryKeyCodec.getValueCodecs(Registry.CONFIGURED_FEATURE_KEY, field_242763_a);
    public static final Logger LOGGER = LogManager.getLogger();
    public final F feature;
    public final FC config;

    public ConfiguredFeature(F f, FC FC) {
        this.feature = f;
        this.config = FC;
    }

    public F func_242766_b() {
        return this.feature;
    }

    public FC func_242767_c() {
        return this.config;
    }

    @Override
    public ConfiguredFeature<?, ?> withPlacement(ConfiguredPlacement<?> configuredPlacement) {
        return Feature.DECORATED.withConfiguration(new DecoratedFeatureConfig(this::lambda$withPlacement$1, configuredPlacement));
    }

    public ConfiguredRandomFeatureList withChance(float f) {
        return new ConfiguredRandomFeatureList(this, f);
    }

    public boolean func_242765_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos) {
        return ((Feature)this.feature).func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, this.config);
    }

    public Stream<ConfiguredFeature<?, ?>> func_242768_d() {
        return Stream.concat(Stream.of(this), this.config.func_241856_an_());
    }

    @Override
    public Object withPlacement(ConfiguredPlacement configuredPlacement) {
        return this.withPlacement(configuredPlacement);
    }

    private ConfiguredFeature lambda$withPlacement$1() {
        return this;
    }

    private static Feature lambda$static$0(ConfiguredFeature configuredFeature) {
        return configuredFeature.feature;
    }
}

