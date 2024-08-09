/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ColumnConfig
implements IFeatureConfig {
    public static final Codec<ColumnConfig> CODEC = RecordCodecBuilder.create(ColumnConfig::lambda$static$2);
    private final FeatureSpread field_242790_b;
    private final FeatureSpread field_242791_c;

    public ColumnConfig(FeatureSpread featureSpread, FeatureSpread featureSpread2) {
        this.field_242790_b = featureSpread;
        this.field_242791_c = featureSpread2;
    }

    public FeatureSpread func_242794_am_() {
        return this.field_242790_b;
    }

    public FeatureSpread func_242795_b() {
        return this.field_242791_c;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)FeatureSpread.func_242254_a(0, 2, 1).fieldOf("reach")).forGetter(ColumnConfig::lambda$static$0), ((MapCodec)FeatureSpread.func_242254_a(1, 5, 5).fieldOf("height")).forGetter(ColumnConfig::lambda$static$1)).apply(instance, ColumnConfig::new);
    }

    private static FeatureSpread lambda$static$1(ColumnConfig columnConfig) {
        return columnConfig.field_242791_c;
    }

    private static FeatureSpread lambda$static$0(ColumnConfig columnConfig) {
        return columnConfig.field_242790_b;
    }
}

