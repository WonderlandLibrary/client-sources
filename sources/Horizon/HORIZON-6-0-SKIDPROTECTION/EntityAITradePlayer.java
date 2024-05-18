package HORIZON-6-0-SKIDPROTECTION;

public class EntityAITradePlayer extends EntityAIBase
{
    private EntityVillager HorizonCode_Horizon_È;
    private static final String Â = "CL_00001617";
    
    public EntityAITradePlayer(final EntityVillager p_i1658_1_) {
        this.HorizonCode_Horizon_È = p_i1658_1_;
        this.HorizonCode_Horizon_È(5);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (!this.HorizonCode_Horizon_È.Œ()) {
            return false;
        }
        if (this.HorizonCode_Horizon_È.£ÂµÄ()) {
            return false;
        }
        if (!this.HorizonCode_Horizon_È.ŠÂµà) {
            return false;
        }
        if (this.HorizonCode_Horizon_È.È) {
            return false;
        }
        final EntityPlayer var1 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        return var1 != null && this.HorizonCode_Horizon_È.Âµá€(var1) <= 16.0 && var1.Ï­Ï instanceof Container;
    }
    
    @Override
    public void Âµá€() {
        this.HorizonCode_Horizon_È.Š().à();
    }
    
    @Override
    public void Ý() {
        this.HorizonCode_Horizon_È.a_((EntityPlayer)null);
    }
}
