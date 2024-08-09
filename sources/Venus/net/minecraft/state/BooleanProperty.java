/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.state;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Optional;
import net.minecraft.state.Property;

public class BooleanProperty
extends Property<Boolean> {
    private final ImmutableSet<Boolean> allowedValues = ImmutableSet.of(true, false);

    protected BooleanProperty(String string) {
        super(string, Boolean.class);
    }

    @Override
    public Collection<Boolean> getAllowedValues() {
        return this.allowedValues;
    }

    public static BooleanProperty create(String string) {
        return new BooleanProperty(string);
    }

    @Override
    public Optional<Boolean> parseValue(String string) {
        return !"true".equals(string) && !"false".equals(string) ? Optional.empty() : Optional.of(Boolean.valueOf(string));
    }

    @Override
    public String getName(Boolean bl) {
        return bl.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof BooleanProperty && super.equals(object)) {
            BooleanProperty booleanProperty = (BooleanProperty)object;
            return this.allowedValues.equals(booleanProperty.allowedValues);
        }
        return true;
    }

    @Override
    public int computeHashCode() {
        return 31 * super.computeHashCode() + this.allowedValues.hashCode();
    }

    @Override
    public String getName(Comparable comparable) {
        return this.getName((Boolean)comparable);
    }
}

