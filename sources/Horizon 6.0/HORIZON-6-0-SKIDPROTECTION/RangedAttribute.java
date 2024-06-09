package HORIZON-6-0-SKIDPROTECTION;

public class RangedAttribute extends BaseAttribute
{
    private final double HorizonCode_Horizon_È;
    private final double Â;
    private String Ý;
    private static final String Ø­áŒŠá = "CL_00001568";
    
    public RangedAttribute(final IAttribute p_i45891_1_, final String p_i45891_2_, final double p_i45891_3_, final double p_i45891_5_, final double p_i45891_7_) {
        super(p_i45891_1_, p_i45891_2_, p_i45891_3_);
        this.HorizonCode_Horizon_È = p_i45891_5_;
        this.Â = p_i45891_7_;
        if (p_i45891_5_ > p_i45891_7_) {
            throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
        }
        if (p_i45891_3_ < p_i45891_5_) {
            throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
        }
        if (p_i45891_3_ > p_i45891_7_) {
            throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
        }
    }
    
    public RangedAttribute HorizonCode_Horizon_È(final String p_111117_1_) {
        this.Ý = p_111117_1_;
        return this;
    }
    
    public String Âµá€() {
        return this.Ý;
    }
    
    @Override
    public double HorizonCode_Horizon_È(double p_111109_1_) {
        p_111109_1_ = MathHelper.HorizonCode_Horizon_È(p_111109_1_, this.HorizonCode_Horizon_È, this.Â);
        return p_111109_1_;
    }
}
