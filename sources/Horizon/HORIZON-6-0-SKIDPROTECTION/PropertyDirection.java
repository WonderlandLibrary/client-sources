package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Collection;

public class PropertyDirection extends PropertyEnum
{
    private static final String HorizonCode_Horizon_È = "CL_00002016";
    
    protected PropertyDirection(final String name, final Collection values) {
        super(name, EnumFacing.class, values);
    }
    
    public static PropertyDirection HorizonCode_Horizon_È(final String name) {
        return HorizonCode_Horizon_È(name, Predicates.alwaysTrue());
    }
    
    public static PropertyDirection HorizonCode_Horizon_È(final String name, final Predicate filter) {
        return HorizonCode_Horizon_È(name, Collections2.filter((Collection)Lists.newArrayList((Object[])EnumFacing.values()), filter));
    }
    
    public static PropertyDirection HorizonCode_Horizon_È(final String name, final Collection values) {
        return new PropertyDirection(name, values);
    }
}
