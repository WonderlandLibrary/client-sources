package HORIZON-6-0-SKIDPROTECTION;

public class EntityAISit extends EntityAIBase
{
    private EntityTameable HorizonCode_Horizon_È;
    private boolean Â;
    private static final String Ý = "CL_00001613";
    
    public EntityAISit(final EntityTameable p_i1654_1_) {
        this.HorizonCode_Horizon_È = p_i1654_1_;
        this.HorizonCode_Horizon_È(5);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (!this.HorizonCode_Horizon_È.ÐƒÓ()) {
            return false;
        }
        if (this.HorizonCode_Horizon_È.£ÂµÄ()) {
            return false;
        }
        if (!this.HorizonCode_Horizon_È.ŠÂµà) {
            return false;
        }
        final EntityLivingBase var1 = this.HorizonCode_Horizon_È.ŒÐƒà();
        return var1 == null || ((this.HorizonCode_Horizon_È.Âµá€(var1) >= 144.0 || var1.Çªà() == null) && this.Â);
    }
    
    @Override
    public void Âµá€() {
        this.HorizonCode_Horizon_È.Š().à();
        this.HorizonCode_Horizon_È.£á(true);
    }
    
    @Override
    public void Ý() {
        this.HorizonCode_Horizon_È.£á(false);
    }
    
    public void HorizonCode_Horizon_È(final boolean p_75270_1_) {
        this.Â = p_75270_1_;
    }
}
