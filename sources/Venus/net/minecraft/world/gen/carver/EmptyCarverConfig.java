/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.carver;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.carver.ICarverConfig;

public class EmptyCarverConfig
implements ICarverConfig {
    public static final Codec<EmptyCarverConfig> field_236237_b_;
    public static final EmptyCarverConfig field_236238_c_;

    private static EmptyCarverConfig lambda$static$0() {
        return field_236238_c_;
    }

    static {
        field_236238_c_ = new EmptyCarverConfig();
        field_236237_b_ = Codec.unit(EmptyCarverConfig::lambda$static$0);
    }
}

