/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class NoFeatureConfig
implements IFeatureConfig {
    public static final Codec<NoFeatureConfig> field_236558_a_;
    public static final NoFeatureConfig field_236559_b_;

    private static NoFeatureConfig lambda$static$0() {
        return field_236559_b_;
    }

    static {
        field_236559_b_ = new NoFeatureConfig();
        field_236558_a_ = Codec.unit(NoFeatureConfig::lambda$static$0);
    }
}

