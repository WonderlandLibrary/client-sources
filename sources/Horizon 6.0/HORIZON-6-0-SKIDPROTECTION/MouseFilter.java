package HORIZON-6-0-SKIDPROTECTION;

public class MouseFilter
{
    private float HorizonCode_Horizon_È;
    private float Â;
    private float Ý;
    private static final String Ø­áŒŠá = "CL_00001500";
    
    public float HorizonCode_Horizon_È(float p_76333_1_, final float p_76333_2_) {
        this.HorizonCode_Horizon_È += p_76333_1_;
        p_76333_1_ = (this.HorizonCode_Horizon_È - this.Â) * p_76333_2_;
        this.Ý += (p_76333_1_ - this.Ý) * 0.5f;
        if ((p_76333_1_ > 0.0f && p_76333_1_ > this.Ý) || (p_76333_1_ < 0.0f && p_76333_1_ < this.Ý)) {
            p_76333_1_ = this.Ý;
        }
        this.Â += p_76333_1_;
        return p_76333_1_;
    }
    
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È = 0.0f;
        this.Â = 0.0f;
        this.Ý = 0.0f;
    }
}
