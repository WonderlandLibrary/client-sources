/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class TopSolidRangeConfig
implements IPlacementConfig {
    public static final Codec<TopSolidRangeConfig> field_236985_a_ = RecordCodecBuilder.create(TopSolidRangeConfig::lambda$static$3);
    public final int field_242813_c;
    public final int field_242814_d;
    public final int field_242815_e;

    public TopSolidRangeConfig(int n, int n2, int n3) {
        this.field_242813_c = n;
        this.field_242814_d = n2;
        this.field_242815_e = n3;
    }

    private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.INT.fieldOf("bottom_offset")).orElse(0).forGetter(TopSolidRangeConfig::lambda$static$0), ((MapCodec)Codec.INT.fieldOf("top_offset")).orElse(0).forGetter(TopSolidRangeConfig::lambda$static$1), ((MapCodec)Codec.INT.fieldOf("maximum")).orElse(0).forGetter(TopSolidRangeConfig::lambda$static$2)).apply(instance, TopSolidRangeConfig::new);
    }

    private static Integer lambda$static$2(TopSolidRangeConfig topSolidRangeConfig) {
        return topSolidRangeConfig.field_242815_e;
    }

    private static Integer lambda$static$1(TopSolidRangeConfig topSolidRangeConfig) {
        return topSolidRangeConfig.field_242814_d;
    }

    private static Integer lambda$static$0(TopSolidRangeConfig topSolidRangeConfig) {
        return topSolidRangeConfig.field_242813_c;
    }
}

