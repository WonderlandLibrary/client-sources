package net.minecraft.block.properties;

import com.google.common.collect.*;
import java.util.*;

public class PropertyBool extends PropertyHelper
{
    private final ImmutableSet allowedValues;
    
    protected PropertyBool(final String name) {
        super(name, Boolean.class);
        this.allowedValues = ImmutableSet.of((Object)true, (Object)false);
    }
    
    public static PropertyBool create(final String name) {
        return new PropertyBool(name);
    }
    
    @Override
    public Collection getAllowedValues() {
        return (Collection)this.allowedValues;
    }
    
    public String getName0(final Boolean value) {
        return value.toString();
    }
    
    @Override
    public String getName(final Comparable value) {
        return this.getName0((Boolean)value);
    }
}
