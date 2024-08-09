/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.state.Property;

public final class VariantPropertyBuilder {
    private static final VariantPropertyBuilder field_240184_a_ = new VariantPropertyBuilder(ImmutableList.of());
    private static final Comparator<Property.ValuePair<?>> field_240185_b_ = Comparator.comparing(VariantPropertyBuilder::lambda$static$0);
    private final List<Property.ValuePair<?>> field_240186_c_;

    public VariantPropertyBuilder func_240188_a_(Property.ValuePair<?> valuePair) {
        return new VariantPropertyBuilder((List<Property.ValuePair<?>>)((Object)((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(this.field_240186_c_)).add(valuePair)).build()));
    }

    public VariantPropertyBuilder func_240189_a_(VariantPropertyBuilder variantPropertyBuilder) {
        return new VariantPropertyBuilder((List<Property.ValuePair<?>>)((Object)((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(this.field_240186_c_)).addAll(variantPropertyBuilder.field_240186_c_)).build()));
    }

    private VariantPropertyBuilder(List<Property.ValuePair<?>> list) {
        this.field_240186_c_ = list;
    }

    public static VariantPropertyBuilder func_240187_a_() {
        return field_240184_a_;
    }

    public static VariantPropertyBuilder func_240190_a_(Property.ValuePair<?> ... valuePairArray) {
        return new VariantPropertyBuilder(ImmutableList.copyOf(valuePairArray));
    }

    public boolean equals(Object object) {
        return this == object || object instanceof VariantPropertyBuilder && this.field_240186_c_.equals(((VariantPropertyBuilder)object).field_240186_c_);
    }

    public int hashCode() {
        return this.field_240186_c_.hashCode();
    }

    public String func_240191_b_() {
        return this.field_240186_c_.stream().sorted(field_240185_b_).map(Property.ValuePair::toString).collect(Collectors.joining(","));
    }

    public String toString() {
        return this.func_240191_b_();
    }

    private static String lambda$static$0(Property.ValuePair valuePair) {
        return valuePair.func_240181_a_().getName();
    }
}

