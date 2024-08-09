/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.OptionalInt;
import net.minecraft.world.gen.feature.AbstractFeatureSizeType;
import net.minecraft.world.gen.feature.FeatureSizeType;

public class ThreeLayerFeature
extends AbstractFeatureSizeType {
    public static final Codec<ThreeLayerFeature> field_236716_c_ = RecordCodecBuilder.create(ThreeLayerFeature::lambda$static$5);
    private final int field_236717_d_;
    private final int field_236718_e_;
    private final int field_236719_f_;
    private final int field_236720_g_;
    private final int field_236721_h_;

    public ThreeLayerFeature(int n, int n2, int n3, int n4, int n5, OptionalInt optionalInt) {
        super(optionalInt);
        this.field_236717_d_ = n;
        this.field_236718_e_ = n2;
        this.field_236719_f_ = n3;
        this.field_236720_g_ = n4;
        this.field_236721_h_ = n5;
    }

    @Override
    protected FeatureSizeType<?> func_230370_b_() {
        return FeatureSizeType.THREE_LAYERS_FEATURE_SIZE;
    }

    @Override
    public int func_230369_a_(int n, int n2) {
        if (n2 < this.field_236717_d_) {
            return this.field_236719_f_;
        }
        return n2 >= n - this.field_236718_e_ ? this.field_236721_h_ : this.field_236720_g_;
    }

    private static App lambda$static$5(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.intRange(0, 80).fieldOf("limit")).orElse(1).forGetter(ThreeLayerFeature::lambda$static$0), ((MapCodec)Codec.intRange(0, 80).fieldOf("upper_limit")).orElse(1).forGetter(ThreeLayerFeature::lambda$static$1), ((MapCodec)Codec.intRange(0, 16).fieldOf("lower_size")).orElse(0).forGetter(ThreeLayerFeature::lambda$static$2), ((MapCodec)Codec.intRange(0, 16).fieldOf("middle_size")).orElse(1).forGetter(ThreeLayerFeature::lambda$static$3), ((MapCodec)Codec.intRange(0, 16).fieldOf("upper_size")).orElse(1).forGetter(ThreeLayerFeature::lambda$static$4), ThreeLayerFeature.func_236706_a_()).apply(instance, ThreeLayerFeature::new);
    }

    private static Integer lambda$static$4(ThreeLayerFeature threeLayerFeature) {
        return threeLayerFeature.field_236721_h_;
    }

    private static Integer lambda$static$3(ThreeLayerFeature threeLayerFeature) {
        return threeLayerFeature.field_236720_g_;
    }

    private static Integer lambda$static$2(ThreeLayerFeature threeLayerFeature) {
        return threeLayerFeature.field_236719_f_;
    }

    private static Integer lambda$static$1(ThreeLayerFeature threeLayerFeature) {
        return threeLayerFeature.field_236718_e_;
    }

    private static Integer lambda$static$0(ThreeLayerFeature threeLayerFeature) {
        return threeLayerFeature.field_236717_d_;
    }
}

