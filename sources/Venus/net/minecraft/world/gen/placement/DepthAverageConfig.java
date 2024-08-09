/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class DepthAverageConfig
implements IPlacementConfig {
    public static final Codec<DepthAverageConfig> field_236955_a_ = RecordCodecBuilder.create(DepthAverageConfig::lambda$static$2);
    public final int baseline;
    public final int spread;

    public DepthAverageConfig(int n, int n2) {
        this.baseline = n;
        this.spread = n2;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.INT.fieldOf("baseline")).forGetter(DepthAverageConfig::lambda$static$0), ((MapCodec)Codec.INT.fieldOf("spread")).forGetter(DepthAverageConfig::lambda$static$1)).apply(instance, DepthAverageConfig::new);
    }

    private static Integer lambda$static$1(DepthAverageConfig depthAverageConfig) {
        return depthAverageConfig.spread;
    }

    private static Integer lambda$static$0(DepthAverageConfig depthAverageConfig) {
        return depthAverageConfig.baseline;
    }
}

