package HORIZON-6-0-SKIDPROTECTION;

import org.apache.commons.lang3.Validate;

public class RegistryNamespacedDefaultedByKey extends RegistryNamespaced
{
    private final Object Ø­áŒŠá;
    private Object Âµá€;
    private static final String Ó = "CL_00001196";
    
    public RegistryNamespacedDefaultedByKey(final Object p_i46017_1_) {
        this.Ø­áŒŠá = p_i46017_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_177775_1_, final Object p_177775_2_, final Object p_177775_3_) {
        if (this.Ø­áŒŠá.equals(p_177775_2_)) {
            this.Âµá€ = p_177775_3_;
        }
        super.HorizonCode_Horizon_È(p_177775_1_, p_177775_2_, p_177775_3_);
    }
    
    public void Â() {
        Validate.notNull(this.Ø­áŒŠá);
    }
    
    @Override
    public Object HorizonCode_Horizon_È(final Object p_82594_1_) {
        final Object var2 = super.HorizonCode_Horizon_È(p_82594_1_);
        return (var2 == null) ? this.Âµá€ : var2;
    }
    
    @Override
    public Object HorizonCode_Horizon_È(final int p_148754_1_) {
        final Object var2 = super.HorizonCode_Horizon_È(p_148754_1_);
        return (var2 == null) ? this.Âµá€ : var2;
    }
}
