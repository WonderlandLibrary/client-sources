/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class DecoratedPlacementConfig
implements IPlacementConfig {
    public static final Codec<DecoratedPlacementConfig> field_242883_a = RecordCodecBuilder.create(DecoratedPlacementConfig::lambda$static$0);
    private final ConfiguredPlacement<?> field_242884_c;
    private final ConfiguredPlacement<?> field_242885_d;

    public DecoratedPlacementConfig(ConfiguredPlacement<?> configuredPlacement, ConfiguredPlacement<?> configuredPlacement2) {
        this.field_242884_c = configuredPlacement;
        this.field_242885_d = configuredPlacement2;
    }

    public ConfiguredPlacement<?> func_242886_a() {
        return this.field_242884_c;
    }

    public ConfiguredPlacement<?> func_242888_b() {
        return this.field_242885_d;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)ConfiguredPlacement.field_236952_a_.fieldOf("outer")).forGetter(DecoratedPlacementConfig::func_242886_a), ((MapCodec)ConfiguredPlacement.field_236952_a_.fieldOf("inner")).forGetter(DecoratedPlacementConfig::func_242888_b)).apply(instance, DecoratedPlacementConfig::new);
    }
}

