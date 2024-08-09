/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.FeatureSizeType;

public abstract class AbstractFeatureSizeType {
    public static final Codec<AbstractFeatureSizeType> field_236704_a_ = Registry.FEATURE_SIZE_TYPE.dispatch(AbstractFeatureSizeType::func_230370_b_, FeatureSizeType::getCodec);
    protected final OptionalInt field_236705_b_;

    protected static <S extends AbstractFeatureSizeType> RecordCodecBuilder<S, OptionalInt> func_236706_a_() {
        return Codec.intRange(0, 80).optionalFieldOf("min_clipped_height").xmap(AbstractFeatureSizeType::lambda$func_236706_a_$0, AbstractFeatureSizeType::lambda$func_236706_a_$1).forGetter(AbstractFeatureSizeType::lambda$func_236706_a_$2);
    }

    public AbstractFeatureSizeType(OptionalInt optionalInt) {
        this.field_236705_b_ = optionalInt;
    }

    protected abstract FeatureSizeType<?> func_230370_b_();

    public abstract int func_230369_a_(int var1, int var2);

    public OptionalInt func_236710_c_() {
        return this.field_236705_b_;
    }

    private static OptionalInt lambda$func_236706_a_$2(AbstractFeatureSizeType abstractFeatureSizeType) {
        return abstractFeatureSizeType.field_236705_b_;
    }

    private static Optional lambda$func_236706_a_$1(OptionalInt optionalInt) {
        return optionalInt.isPresent() ? Optional.of(optionalInt.getAsInt()) : Optional.empty();
    }

    private static OptionalInt lambda$func_236706_a_$0(Optional optional) {
        return optional.map(OptionalInt::of).orElse(OptionalInt.empty());
    }
}

