/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class ChanceConfig
implements IPlacementConfig {
    public static final Codec<ChanceConfig> field_236950_a_ = ((MapCodec)Codec.INT.fieldOf("chance")).xmap(ChanceConfig::new, ChanceConfig::lambda$static$0).codec();
    public final int chance;

    public ChanceConfig(int n) {
        this.chance = n;
    }

    private static Integer lambda$static$0(ChanceConfig chanceConfig) {
        return chanceConfig.chance;
    }
}

