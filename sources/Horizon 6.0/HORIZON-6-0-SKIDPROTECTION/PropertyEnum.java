package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Iterator;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import com.google.common.collect.ImmutableSet;

public class PropertyEnum extends PropertyHelper
{
    private final ImmutableSet HorizonCode_Horizon_È;
    private final Map Â;
    private static final String Ý = "CL_00002015";
    
    protected PropertyEnum(final String name, final Class valueClass, final Collection allowedValues) {
        super(name, valueClass);
        this.Â = Maps.newHashMap();
        this.HorizonCode_Horizon_È = ImmutableSet.copyOf(allowedValues);
        for (final Enum var5 : allowedValues) {
            final String var6 = ((IStringSerializable)var5).HorizonCode_Horizon_È();
            if (this.Â.containsKey(var6)) {
                throw new IllegalArgumentException("Multiple values have the same name '" + var6 + "'");
            }
            this.Â.put(var6, var5);
        }
    }
    
    @Override
    public Collection Â() {
        return (Collection)this.HorizonCode_Horizon_È;
    }
    
    public String HorizonCode_Horizon_È(final Enum value) {
        return ((IStringSerializable)value).HorizonCode_Horizon_È();
    }
    
    public static PropertyEnum HorizonCode_Horizon_È(final String name, final Class clazz) {
        return HorizonCode_Horizon_È(name, clazz, Predicates.alwaysTrue());
    }
    
    public static PropertyEnum HorizonCode_Horizon_È(final String name, final Class clazz, final Predicate filter) {
        return HorizonCode_Horizon_È(name, clazz, Collections2.filter((Collection)Lists.newArrayList(clazz.getEnumConstants()), filter));
    }
    
    public static PropertyEnum HorizonCode_Horizon_È(final String name, final Class clazz, final Enum... values) {
        return HorizonCode_Horizon_È(name, clazz, Lists.newArrayList((Object[])values));
    }
    
    public static PropertyEnum HorizonCode_Horizon_È(final String name, final Class clazz, final Collection values) {
        return new PropertyEnum(name, clazz, values);
    }
    
    @Override
    public String HorizonCode_Horizon_È(final Comparable value) {
        return this.HorizonCode_Horizon_È((Enum)value);
    }
}
