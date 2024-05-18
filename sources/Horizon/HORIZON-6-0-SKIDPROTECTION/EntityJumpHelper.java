package HORIZON-6-0-SKIDPROTECTION;

public class EntityJumpHelper
{
    private EntityLiving Â;
    protected boolean HorizonCode_Horizon_È;
    private static final String Ý = "CL_00001571";
    
    public EntityJumpHelper(final EntityLiving p_i1612_1_) {
        this.Â = p_i1612_1_;
    }
    
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È = true;
    }
    
    public void Â() {
        this.Â.ÂµÈ(this.HorizonCode_Horizon_È);
        this.HorizonCode_Horizon_È = false;
    }
}
