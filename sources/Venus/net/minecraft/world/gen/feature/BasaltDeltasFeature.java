/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BasaltDeltasFeature
implements IFeatureConfig {
    public static final Codec<BasaltDeltasFeature> field_236495_a_ = RecordCodecBuilder.create(BasaltDeltasFeature::lambda$static$4);
    private final BlockState field_236496_b_;
    private final BlockState field_236497_c_;
    private final FeatureSpread field_242800_d;
    private final FeatureSpread field_242801_e;

    public BasaltDeltasFeature(BlockState blockState, BlockState blockState2, FeatureSpread featureSpread, FeatureSpread featureSpread2) {
        this.field_236496_b_ = blockState;
        this.field_236497_c_ = blockState2;
        this.field_242800_d = featureSpread;
        this.field_242801_e = featureSpread2;
    }

    public BlockState func_242804_b() {
        return this.field_236496_b_;
    }

    public BlockState func_242806_c() {
        return this.field_236497_c_;
    }

    public FeatureSpread func_242807_d() {
        return this.field_242800_d;
    }

    public FeatureSpread func_242808_e() {
        return this.field_242801_e;
    }

    private static App lambda$static$4(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)BlockState.CODEC.fieldOf("contents")).forGetter(BasaltDeltasFeature::lambda$static$0), ((MapCodec)BlockState.CODEC.fieldOf("rim")).forGetter(BasaltDeltasFeature::lambda$static$1), ((MapCodec)FeatureSpread.func_242254_a(0, 8, 8).fieldOf("size")).forGetter(BasaltDeltasFeature::lambda$static$2), ((MapCodec)FeatureSpread.func_242254_a(0, 8, 8).fieldOf("rim_size")).forGetter(BasaltDeltasFeature::lambda$static$3)).apply(instance, BasaltDeltasFeature::new);
    }

    private static FeatureSpread lambda$static$3(BasaltDeltasFeature basaltDeltasFeature) {
        return basaltDeltasFeature.field_242801_e;
    }

    private static FeatureSpread lambda$static$2(BasaltDeltasFeature basaltDeltasFeature) {
        return basaltDeltasFeature.field_242800_d;
    }

    private static BlockState lambda$static$1(BasaltDeltasFeature basaltDeltasFeature) {
        return basaltDeltasFeature.field_236497_c_;
    }

    private static BlockState lambda$static$0(BasaltDeltasFeature basaltDeltasFeature) {
        return basaltDeltasFeature.field_236496_b_;
    }
}

