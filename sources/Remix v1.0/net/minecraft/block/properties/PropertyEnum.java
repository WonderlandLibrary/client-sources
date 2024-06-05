package net.minecraft.block.properties;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class PropertyEnum extends PropertyHelper
{
    private final ImmutableSet allowedValues;
    private final Map nameToValue;
    
    protected PropertyEnum(final String name, final Class valueClass, final Collection allowedValues) {
        super(name, valueClass);
        this.nameToValue = Maps.newHashMap();
        this.allowedValues = ImmutableSet.copyOf(allowedValues);
        for (final Enum var5 : allowedValues) {
            final String var6 = ((IStringSerializable)var5).getName();
            if (this.nameToValue.containsKey(var6)) {
                throw new IllegalArgumentException("Multiple values have the same name '" + var6 + "'");
            }
            this.nameToValue.put(var6, var5);
        }
    }
    
    public static PropertyEnum create(final String name, final Class clazz) {
        return create(name, clazz, Predicates.alwaysTrue());
    }
    
    public static PropertyEnum create(final String name, final Class clazz, final Predicate filter) {
        return create(name, clazz, Collections2.filter((Collection)Lists.newArrayList(clazz.getEnumConstants()), filter));
    }
    
    public static PropertyEnum create(final String name, final Class clazz, final Enum... values) {
        return create(name, clazz, Lists.newArrayList((Object[])values));
    }
    
    public static PropertyEnum create(final String name, final Class clazz, final Collection values) {
        return new PropertyEnum(name, clazz, values);
    }
    
    @Override
    public Collection getAllowedValues() {
        return (Collection)this.allowedValues;
    }
    
    public String getName(final Enum value) {
        return ((IStringSerializable)value).getName();
    }
    
    @Override
    public String getName(final Comparable value) {
        return this.getName((Enum)value);
    }
}
