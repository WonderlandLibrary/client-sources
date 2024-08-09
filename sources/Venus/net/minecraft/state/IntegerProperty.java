/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.state;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import net.minecraft.state.Property;

public class IntegerProperty
extends Property<Integer> {
    private final ImmutableSet<Integer> allowedValues;

    protected IntegerProperty(String string, int n, int n2) {
        super(string, Integer.class);
        if (n < 0) {
            throw new IllegalArgumentException("Min value of " + string + " must be 0 or greater");
        }
        if (n2 <= n) {
            throw new IllegalArgumentException("Max value of " + string + " must be greater than min (" + n + ")");
        }
        HashSet<Integer> hashSet = Sets.newHashSet();
        for (int i = n; i <= n2; ++i) {
            hashSet.add(i);
        }
        this.allowedValues = ImmutableSet.copyOf(hashSet);
    }

    @Override
    public Collection<Integer> getAllowedValues() {
        return this.allowedValues;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof IntegerProperty && super.equals(object)) {
            IntegerProperty integerProperty = (IntegerProperty)object;
            return this.allowedValues.equals(integerProperty.allowedValues);
        }
        return true;
    }

    @Override
    public int computeHashCode() {
        return 31 * super.computeHashCode() + this.allowedValues.hashCode();
    }

    public static IntegerProperty create(String string, int n, int n2) {
        return new IntegerProperty(string, n, n2);
    }

    @Override
    public Optional<Integer> parseValue(String string) {
        try {
            Integer n = Integer.valueOf(string);
            return this.allowedValues.contains(n) ? Optional.of(n) : Optional.empty();
        } catch (NumberFormatException numberFormatException) {
            return Optional.empty();
        }
    }

    @Override
    public String getName(Integer n) {
        return n.toString();
    }

    @Override
    public String getName(Comparable comparable) {
        return this.getName((Integer)comparable);
    }
}

