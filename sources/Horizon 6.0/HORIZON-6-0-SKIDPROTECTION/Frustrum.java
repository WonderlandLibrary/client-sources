package HORIZON-6-0-SKIDPROTECTION;

public class Frustrum implements ICamera
{
    private ClippingHelper HorizonCode_Horizon_È;
    private double Â;
    private double Ý;
    private double Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000976";
    
    public Frustrum() {
        this(ClippingHelperImpl.HorizonCode_Horizon_È());
    }
    
    public Frustrum(final ClippingHelper p_i46196_1_) {
        this.HorizonCode_Horizon_È = p_i46196_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final double p_78547_1_, final double p_78547_3_, final double p_78547_5_) {
        this.Â = p_78547_1_;
        this.Ý = p_78547_3_;
        this.Ø­áŒŠá = p_78547_5_;
    }
    
    public boolean HorizonCode_Horizon_È(final double p_78548_1_, final double p_78548_3_, final double p_78548_5_, final double p_78548_7_, final double p_78548_9_, final double p_78548_11_) {
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78548_1_ - this.Â, p_78548_3_ - this.Ý, p_78548_5_ - this.Ø­áŒŠá, p_78548_7_ - this.Â, p_78548_9_ - this.Ý, p_78548_11_ - this.Ø­áŒŠá);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final AxisAlignedBB p_78546_1_) {
        return this.HorizonCode_Horizon_È(p_78546_1_.HorizonCode_Horizon_È, p_78546_1_.Â, p_78546_1_.Ý, p_78546_1_.Ø­áŒŠá, p_78546_1_.Âµá€, p_78546_1_.Ó);
    }
}
