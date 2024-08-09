/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class FeatureSpreadConfig
implements IPlacementConfig,
IFeatureConfig {
    public static final Codec<FeatureSpreadConfig> field_242797_a = ((MapCodec)FeatureSpread.func_242254_a(-10, 128, 128).fieldOf("count")).xmap(FeatureSpreadConfig::new, FeatureSpreadConfig::func_242799_a).codec();
    private final FeatureSpread field_242798_c;

    public FeatureSpreadConfig(int n) {
        this.field_242798_c = FeatureSpread.func_242252_a(n);
    }

    public FeatureSpreadConfig(FeatureSpread featureSpread) {
        this.field_242798_c = featureSpread;
    }

    public FeatureSpread func_242799_a() {
        return this.field_242798_c;
    }
}

