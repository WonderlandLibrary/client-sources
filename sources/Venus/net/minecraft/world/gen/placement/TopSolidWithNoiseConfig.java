/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class TopSolidWithNoiseConfig
implements IPlacementConfig {
    public static final Codec<TopSolidWithNoiseConfig> field_236978_a_ = RecordCodecBuilder.create(TopSolidWithNoiseConfig::lambda$static$3);
    public final int noiseToCountRatio;
    public final double noiseFactor;
    public final double noiseOffset;

    public TopSolidWithNoiseConfig(int n, double d, double d2) {
        this.noiseToCountRatio = n;
        this.noiseFactor = d;
        this.noiseOffset = d2;
    }

    private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.INT.fieldOf("noise_to_count_ratio")).forGetter(TopSolidWithNoiseConfig::lambda$static$0), ((MapCodec)Codec.DOUBLE.fieldOf("noise_factor")).forGetter(TopSolidWithNoiseConfig::lambda$static$1), ((MapCodec)Codec.DOUBLE.fieldOf("noise_offset")).orElse(0.0).forGetter(TopSolidWithNoiseConfig::lambda$static$2)).apply(instance, TopSolidWithNoiseConfig::new);
    }

    private static Double lambda$static$2(TopSolidWithNoiseConfig topSolidWithNoiseConfig) {
        return topSolidWithNoiseConfig.noiseOffset;
    }

    private static Double lambda$static$1(TopSolidWithNoiseConfig topSolidWithNoiseConfig) {
        return topSolidWithNoiseConfig.noiseFactor;
    }

    private static Integer lambda$static$0(TopSolidWithNoiseConfig topSolidWithNoiseConfig) {
        return topSolidWithNoiseConfig.noiseToCountRatio;
    }
}

