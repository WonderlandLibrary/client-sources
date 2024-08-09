/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class NoPlacementConfig
implements IPlacementConfig {
    public static final Codec<NoPlacementConfig> field_236555_a_;
    public static final NoPlacementConfig field_236556_b_;

    private static NoPlacementConfig lambda$static$0() {
        return field_236556_b_;
    }

    static {
        field_236556_b_ = new NoPlacementConfig();
        field_236555_a_ = Codec.unit(NoPlacementConfig::lambda$static$0);
    }
}

