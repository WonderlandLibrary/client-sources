/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class AtSurfaceWithExtraConfig
implements IPlacementConfig {
    public static final Codec<AtSurfaceWithExtraConfig> field_236973_a_ = RecordCodecBuilder.create(AtSurfaceWithExtraConfig::lambda$static$3);
    public final int count;
    public final float extraChance;
    public final int extraCount;

    public AtSurfaceWithExtraConfig(int n, float f, int n2) {
        this.count = n;
        this.extraChance = f;
        this.extraCount = n2;
    }

    private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.INT.fieldOf("count")).forGetter(AtSurfaceWithExtraConfig::lambda$static$0), ((MapCodec)Codec.FLOAT.fieldOf("extra_chance")).forGetter(AtSurfaceWithExtraConfig::lambda$static$1), ((MapCodec)Codec.INT.fieldOf("extra_count")).forGetter(AtSurfaceWithExtraConfig::lambda$static$2)).apply(instance, AtSurfaceWithExtraConfig::new);
    }

    private static Integer lambda$static$2(AtSurfaceWithExtraConfig atSurfaceWithExtraConfig) {
        return atSurfaceWithExtraConfig.extraCount;
    }

    private static Float lambda$static$1(AtSurfaceWithExtraConfig atSurfaceWithExtraConfig) {
        return Float.valueOf(atSurfaceWithExtraConfig.extraChance);
    }

    private static Integer lambda$static$0(AtSurfaceWithExtraConfig atSurfaceWithExtraConfig) {
        return atSurfaceWithExtraConfig.count;
    }
}

