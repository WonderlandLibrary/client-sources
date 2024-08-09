/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ShipwreckConfig
implements IFeatureConfig {
    public static final Codec<ShipwreckConfig> field_236634_a_ = ((MapCodec)Codec.BOOL.fieldOf("is_beached")).orElse(false).xmap(ShipwreckConfig::new, ShipwreckConfig::lambda$static$0).codec();
    public final boolean isBeached;

    public ShipwreckConfig(boolean bl) {
        this.isBeached = bl;
    }

    private static Boolean lambda$static$0(ShipwreckConfig shipwreckConfig) {
        return shipwreckConfig.isBeached;
    }
}

