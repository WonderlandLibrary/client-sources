// 
// Decompiled by Procyon v0.6.0
// 

package net.minecraft.block.properties;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Iterator;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import com.google.common.collect.ImmutableSet;
import net.minecraft.util.IStringSerializable;

public class PropertyEnum<T extends Enum<T> & IStringSerializable> extends PropertyHelper<T>
{
    private final ImmutableSet<T> allowedValues;
    private final Map<String, T> nameToValue;
    
    protected PropertyEnum(final String name, final Class<T> valueClass, final Collection<T> allowedValues) {
        super(name, valueClass);
        this.nameToValue = (Map<String, T>)Maps.newHashMap();
        this.allowedValues = ImmutableSet.copyOf((Collection<? extends T>)allowedValues);
        for (final T t : allowedValues) {
            final String s = t.getName();
            if (this.nameToValue.containsKey(s)) {
                throw new IllegalArgumentException("Multiple values have the same name '" + s + "'");
            }
            this.nameToValue.put(s, t);
        }
    }
    
    @Override
    public Collection<T> getAllowedValues() {
        return this.allowedValues;
    }
    
    @Override
    public String getName(final T value) {
        return value.getName();
    }
    
    public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(final String name, final Class<T> clazz) {
        return create(name, clazz, Predicates.alwaysTrue());
    }
    
    public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(final String name, final Class<T> clazz, final Predicate<T> filter) {
        return create(name, clazz, (Collection<T>)Collections2.filter((Collection<T>)Lists.newArrayList(clazz.getEnumConstants()), (Predicate<? super T>)filter));
    }
    
    public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(final String name, final Class<T> clazz, final T... values) {
        return create(name, clazz, Lists.newArrayList(values));
    }
    
    public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(final String name, final Class<T> clazz, final Collection<T> values) {
        return new PropertyEnum<T>(name, clazz, values);
    }
}
