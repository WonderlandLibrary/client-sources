/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.state;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.state.StateHolder;

public abstract class Property<T extends Comparable<T>> {
    private final Class<T> field_235913_a_;
    private final String field_235914_b_;
    private Integer field_235915_c_;
    private final Codec<T> field_235916_d_ = Codec.STRING.comapFlatMap(this::lambda$new$1, this::getName);
    private final Codec<ValuePair<T>> field_241488_e_ = this.field_235916_d_.xmap(this::func_241490_b_, ValuePair::func_241493_b_);

    protected Property(String string, Class<T> clazz) {
        this.field_235913_a_ = clazz;
        this.field_235914_b_ = string;
    }

    public ValuePair<T> func_241490_b_(T t) {
        return new ValuePair<T>(this, t);
    }

    public ValuePair<T> func_241489_a_(StateHolder<?, ?> stateHolder) {
        return new ValuePair(this, stateHolder.get(this));
    }

    public Stream<ValuePair<T>> func_241491_c_() {
        return this.getAllowedValues().stream().map(this::func_241490_b_);
    }

    public Codec<ValuePair<T>> func_241492_e_() {
        return this.field_241488_e_;
    }

    public String getName() {
        return this.field_235914_b_;
    }

    public Class<T> getValueClass() {
        return this.field_235913_a_;
    }

    public abstract Collection<T> getAllowedValues();

    public abstract String getName(T var1);

    public abstract Optional<T> parseValue(String var1);

    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", this.field_235914_b_).add("clazz", this.field_235913_a_).add("values", this.getAllowedValues()).toString();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Property)) {
            return true;
        }
        Property property = (Property)object;
        return this.field_235913_a_.equals(property.field_235913_a_) && this.field_235914_b_.equals(property.field_235914_b_);
    }

    public final int hashCode() {
        if (this.field_235915_c_ == null) {
            this.field_235915_c_ = this.computeHashCode();
        }
        return this.field_235915_c_;
    }

    public int computeHashCode() {
        return 31 * this.field_235913_a_.hashCode() + this.field_235914_b_.hashCode();
    }

    private DataResult lambda$new$1(String string) {
        return this.parseValue(string).map(DataResult::success).orElseGet(() -> this.lambda$new$0(string));
    }

    private DataResult lambda$new$0(String string) {
        return DataResult.error("Unable to read property: " + this + " with value: " + string);
    }

    public static final class ValuePair<T extends Comparable<T>> {
        private final Property<T> field_240179_a_;
        private final T field_240180_b_;

        private ValuePair(Property<T> property, T t) {
            if (!property.getAllowedValues().contains(t)) {
                throw new IllegalArgumentException("Value " + t + " does not belong to property " + property);
            }
            this.field_240179_a_ = property;
            this.field_240180_b_ = t;
        }

        public Property<T> func_240181_a_() {
            return this.field_240179_a_;
        }

        public T func_241493_b_() {
            return this.field_240180_b_;
        }

        public String toString() {
            return this.field_240179_a_.getName() + "=" + this.field_240179_a_.getName(this.field_240180_b_);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof ValuePair)) {
                return true;
            }
            ValuePair valuePair = (ValuePair)object;
            return this.field_240179_a_ == valuePair.field_240179_a_ && this.field_240180_b_.equals(valuePair.field_240180_b_);
        }

        public int hashCode() {
            int n = this.field_240179_a_.hashCode();
            return 31 * n + this.field_240180_b_.hashCode();
        }
    }
}

