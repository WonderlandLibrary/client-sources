package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import com.google.common.collect.ImmutableSet;

public class PropertyBool extends PropertyHelper
{
    private final ImmutableSet HorizonCode_Horizon_È;
    private static final String Â = "CL_00002017";
    
    protected PropertyBool(final String name) {
        super(name, Boolean.class);
        this.HorizonCode_Horizon_È = ImmutableSet.of((Object)true, (Object)false);
    }
    
    @Override
    public Collection Â() {
        return (Collection)this.HorizonCode_Horizon_È;
    }
    
    public static PropertyBool HorizonCode_Horizon_È(final String name) {
        return new PropertyBool(name);
    }
    
    public String HorizonCode_Horizon_È(final Boolean value) {
        return value.toString();
    }
    
    @Override
    public String HorizonCode_Horizon_È(final Comparable value) {
        return this.HorizonCode_Horizon_È((Boolean)value);
    }
}
