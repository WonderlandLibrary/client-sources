package HORIZON-6-0-SKIDPROTECTION;

import java.util.HashSet;
import java.util.Collection;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableSet;

public class PropertyInteger extends PropertyHelper
{
    private final ImmutableSet HorizonCode_Horizon_È;
    private static final String Â = "CL_00002014";
    
    protected PropertyInteger(final String name, final int min, final int max) {
        super(name, Integer.class);
        if (min < 0) {
            throw new IllegalArgumentException("Min value of " + name + " must be 0 or greater");
        }
        if (max <= min) {
            throw new IllegalArgumentException("Max value of " + name + " must be greater than min (" + min + ")");
        }
        final HashSet var4 = Sets.newHashSet();
        for (int var5 = min; var5 <= max; ++var5) {
            var4.add(var5);
        }
        this.HorizonCode_Horizon_È = ImmutableSet.copyOf((Collection)var4);
    }
    
    @Override
    public Collection Â() {
        return (Collection)this.HorizonCode_Horizon_È;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (p_equals_1_ == null || this.getClass() != p_equals_1_.getClass()) {
            return false;
        }
        if (!super.equals(p_equals_1_)) {
            return false;
        }
        final PropertyInteger var2 = (PropertyInteger)p_equals_1_;
        return this.HorizonCode_Horizon_È.equals((Object)var2.HorizonCode_Horizon_È);
    }
    
    @Override
    public int hashCode() {
        int var1 = super.hashCode();
        var1 = 31 * var1 + this.HorizonCode_Horizon_È.hashCode();
        return var1;
    }
    
    public static PropertyInteger HorizonCode_Horizon_È(final String name, final int min, final int max) {
        return new PropertyInteger(name, min, max);
    }
    
    public String HorizonCode_Horizon_È(final Integer value) {
        return value.toString();
    }
    
    @Override
    public String HorizonCode_Horizon_È(final Comparable value) {
        return this.HorizonCode_Horizon_È((Integer)value);
    }
}
