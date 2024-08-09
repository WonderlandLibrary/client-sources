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

public class TwoLayerFeature
extends AbstractFeatureSizeType {
    public static final Codec<TwoLayerFeature> field_236728_c_ = RecordCodecBuilder.create(TwoLayerFeature::lambda$static$3);
    private final int field_236729_d_;
    private final int field_236730_e_;
    private final int field_236731_f_;

    public TwoLayerFeature(int n, int n2, int n3) {
        this(n, n2, n3, OptionalInt.empty());
    }

    public TwoLayerFeature(int n, int n2, int n3, OptionalInt optionalInt) {
        super(optionalInt);
        this.field_236729_d_ = n;
        this.field_236730_e_ = n2;
        this.field_236731_f_ = n3;
    }

    @Override
    protected FeatureSizeType<?> func_230370_b_() {
        return FeatureSizeType.TWO_LAYERS_FEATURE_SIZE;
    }

    @Override
    public int func_230369_a_(int n, int n2) {
        return n2 < this.field_236729_d_ ? this.field_236730_e_ : this.field_236731_f_;
    }

    private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.intRange(0, 81).fieldOf("limit")).orElse(1).forGetter(TwoLayerFeature::lambda$static$0), ((MapCodec)Codec.intRange(0, 16).fieldOf("lower_size")).orElse(0).forGetter(TwoLayerFeature::lambda$static$1), ((MapCodec)Codec.intRange(0, 16).fieldOf("upper_size")).orElse(1).forGetter(TwoLayerFeature::lambda$static$2), TwoLayerFeature.func_236706_a_()).apply(instance, TwoLayerFeature::new);
    }

    private static Integer lambda$static$2(TwoLayerFeature twoLayerFeature) {
        return twoLayerFeature.field_236731_f_;
    }

    private static Integer lambda$static$1(TwoLayerFeature twoLayerFeature) {
        return twoLayerFeature.field_236730_e_;
    }

    private static Integer lambda$static$0(TwoLayerFeature twoLayerFeature) {
        return twoLayerFeature.field_236729_d_;
    }
}

