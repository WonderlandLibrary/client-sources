package net.minecraft.block.properties;

import java.util.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class PropertyDirection extends PropertyEnum
{
    protected PropertyDirection(final String name, final Collection values) {
        super(name, EnumFacing.class, values);
    }
    
    public static PropertyDirection create(final String name) {
        return create(name, Predicates.alwaysTrue());
    }
    
    public static PropertyDirection create(final String name, final Predicate filter) {
        return create(name, Collections2.filter((Collection)Lists.newArrayList((Object[])EnumFacing.values()), filter));
    }
    
    public static PropertyDirection create(final String name, final Collection values) {
        return new PropertyDirection(name, values);
    }
}
