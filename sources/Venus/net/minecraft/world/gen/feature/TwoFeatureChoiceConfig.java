/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class TwoFeatureChoiceConfig
implements IFeatureConfig {
    public static final Codec<TwoFeatureChoiceConfig> field_236579_a_ = RecordCodecBuilder.create(TwoFeatureChoiceConfig::lambda$static$2);
    public final Supplier<ConfiguredFeature<?, ?>> field_227285_a_;
    public final Supplier<ConfiguredFeature<?, ?>> field_227286_b_;

    public TwoFeatureChoiceConfig(Supplier<ConfiguredFeature<?, ?>> supplier, Supplier<ConfiguredFeature<?, ?>> supplier2) {
        this.field_227285_a_ = supplier;
        this.field_227286_b_ = supplier2;
    }

    @Override
    public Stream<ConfiguredFeature<?, ?>> func_241856_an_() {
        return Stream.concat(this.field_227285_a_.get().func_242768_d(), this.field_227286_b_.get().func_242768_d());
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)ConfiguredFeature.field_236264_b_.fieldOf("feature_true")).forGetter(TwoFeatureChoiceConfig::lambda$static$0), ((MapCodec)ConfiguredFeature.field_236264_b_.fieldOf("feature_false")).forGetter(TwoFeatureChoiceConfig::lambda$static$1)).apply(instance, TwoFeatureChoiceConfig::new);
    }

    private static Supplier lambda$static$1(TwoFeatureChoiceConfig twoFeatureChoiceConfig) {
        return twoFeatureChoiceConfig.field_227286_b_;
    }

    private static Supplier lambda$static$0(TwoFeatureChoiceConfig twoFeatureChoiceConfig) {
        return twoFeatureChoiceConfig.field_227285_a_;
    }
}

