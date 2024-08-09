/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.AbstractFeatureSizeType;
import net.minecraft.world.gen.feature.ThreeLayerFeature;
import net.minecraft.world.gen.feature.TwoLayerFeature;

public class FeatureSizeType<P extends AbstractFeatureSizeType> {
    public static final FeatureSizeType<TwoLayerFeature> TWO_LAYERS_FEATURE_SIZE = FeatureSizeType.register("two_layers_feature_size", TwoLayerFeature.field_236728_c_);
    public static final FeatureSizeType<ThreeLayerFeature> THREE_LAYERS_FEATURE_SIZE = FeatureSizeType.register("three_layers_feature_size", ThreeLayerFeature.field_236716_c_);
    private final Codec<P> codec;

    private static <P extends AbstractFeatureSizeType> FeatureSizeType<P> register(String string, Codec<P> codec) {
        return Registry.register(Registry.FEATURE_SIZE_TYPE, string, new FeatureSizeType<P>(codec));
    }

    private FeatureSizeType(Codec<P> codec) {
        this.codec = codec;
    }

    public Codec<P> getCodec() {
        return this.codec;
    }
}

