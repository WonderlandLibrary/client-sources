package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Objects;

public abstract class PropertyHelper implements IProperty
{
    private final Class HorizonCode_Horizon_È;
    private final String Â;
    private static final String Ý = "CL_00002018";
    
    protected PropertyHelper(final String name, final Class valueClass) {
        this.HorizonCode_Horizon_È = valueClass;
        this.Â = name;
    }
    
    @Override
    public String HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    @Override
    public Class Ý() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add("name", (Object)this.Â).add("clazz", (Object)this.HorizonCode_Horizon_È).add("values", (Object)this.Â()).toString();
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            final PropertyHelper var2 = (PropertyHelper)p_equals_1_;
            return this.HorizonCode_Horizon_È.equals(var2.HorizonCode_Horizon_È) && this.Â.equals(var2.Â);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 * this.HorizonCode_Horizon_È.hashCode() + this.Â.hashCode();
    }
}
