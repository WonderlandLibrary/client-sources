/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import java.util.stream.Stream;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public interface IFeatureConfig {
    public static final NoFeatureConfig NO_FEATURE_CONFIG = NoFeatureConfig.field_236559_b_;

    default public Stream<ConfiguredFeature<?, ?>> func_241856_an_() {
        return Stream.empty();
    }
}

