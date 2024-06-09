package HORIZON-6-0-SKIDPROTECTION;

public class RegistryDefaulted extends RegistrySimple
{
    private final Object HorizonCode_Horizon_È;
    private static final String Â = "CL_00001198";
    
    public RegistryDefaulted(final Object p_i1366_1_) {
        this.HorizonCode_Horizon_È = p_i1366_1_;
    }
    
    @Override
    public Object HorizonCode_Horizon_È(final Object p_82594_1_) {
        final Object var2 = super.HorizonCode_Horizon_È(p_82594_1_);
        return (var2 == null) ? this.HorizonCode_Horizon_È : var2;
    }
}
