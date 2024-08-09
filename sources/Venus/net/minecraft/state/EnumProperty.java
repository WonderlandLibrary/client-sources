/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.state;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.state.Property;
import net.minecraft.util.IStringSerializable;

public class EnumProperty<T extends Enum<T>>
extends Property<T> {
    private final ImmutableSet<T> allowedValues;
    private final Map<String, T> nameToValue = Maps.newHashMap();

    protected EnumProperty(String string, Class<T> clazz, Collection<T> collection) {
        super(string, clazz);
        this.allowedValues = ImmutableSet.copyOf(collection);
        for (Enum enum_ : collection) {
            String string2 = ((IStringSerializable)((Object)enum_)).getString();
            if (this.nameToValue.containsKey(string2)) {
                throw new IllegalArgumentException("Multiple values have the same name '" + string2 + "'");
            }
            this.nameToValue.put(string2, enum_);
        }
    }

    @Override
    public Collection<T> getAllowedValues() {
        return this.allowedValues;
    }

    @Override
    public Optional<T> parseValue(String string) {
        return Optional.ofNullable((Enum)this.nameToValue.get(string));
    }

    @Override
    public String getName(T t) {
        return ((IStringSerializable)t).getString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof EnumProperty && super.equals(object)) {
            EnumProperty enumProperty = (EnumProperty)object;
            return this.allowedValues.equals(enumProperty.allowedValues) && this.nameToValue.equals(enumProperty.nameToValue);
        }
        return true;
    }

    @Override
    public int computeHashCode() {
        int n = super.computeHashCode();
        n = 31 * n + this.allowedValues.hashCode();
        return 31 * n + this.nameToValue.hashCode();
    }

    public static <T extends Enum<T>> EnumProperty<T> create(String string, Class<T> clazz) {
        return EnumProperty.create(string, clazz, Predicates.alwaysTrue());
    }

    public static <T extends Enum<T>> EnumProperty<T> create(String string, Class<T> clazz, Predicate<T> predicate) {
        return EnumProperty.create(string, clazz, Arrays.stream((Enum[])clazz.getEnumConstants()).filter(predicate).collect(Collectors.toList()));
    }

    public static <T extends Enum<T>> EnumProperty<T> create(String string, Class<T> clazz, T ... TArray) {
        return EnumProperty.create(string, clazz, Lists.newArrayList(TArray));
    }

    public static <T extends Enum<T>> EnumProperty<T> create(String string, Class<T> clazz, Collection<T> collection) {
        return new EnumProperty<T>(string, clazz, collection);
    }

    @Override
    public String getName(Comparable comparable) {
        return this.getName((Enum)((Object)comparable));
    }
}

