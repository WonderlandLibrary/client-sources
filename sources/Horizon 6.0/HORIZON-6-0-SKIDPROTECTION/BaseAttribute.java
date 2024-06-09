package HORIZON-6-0-SKIDPROTECTION;

public abstract class BaseAttribute implements IAttribute
{
    private final IAttribute HorizonCode_Horizon_È;
    private final String Â;
    private final double Ý;
    private boolean Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001565";
    
    protected BaseAttribute(final IAttribute p_i45892_1_, final String p_i45892_2_, final double p_i45892_3_) {
        this.HorizonCode_Horizon_È = p_i45892_1_;
        this.Â = p_i45892_2_;
        this.Ý = p_i45892_3_;
        if (p_i45892_2_ == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }
    }
    
    @Override
    public String HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    @Override
    public double Â() {
        return this.Ý;
    }
    
    @Override
    public boolean Ý() {
        return this.Ø­áŒŠá;
    }
    
    public BaseAttribute HorizonCode_Horizon_È(final boolean p_111112_1_) {
        this.Ø­áŒŠá = p_111112_1_;
        return this;
    }
    
    @Override
    public IAttribute Ø­áŒŠá() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public int hashCode() {
        return this.Â.hashCode();
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        return p_equals_1_ instanceof IAttribute && this.Â.equals(((IAttribute)p_equals_1_).HorizonCode_Horizon_È());
    }
}
