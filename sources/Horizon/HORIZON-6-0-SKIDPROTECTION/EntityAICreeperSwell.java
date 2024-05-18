package HORIZON-6-0-SKIDPROTECTION;

public class EntityAICreeperSwell extends EntityAIBase
{
    EntityCreeper HorizonCode_Horizon_È;
    EntityLivingBase Â;
    private static final String Ý = "CL_00001614";
    
    public EntityAICreeperSwell(final EntityCreeper p_i1655_1_) {
        this.HorizonCode_Horizon_È = p_i1655_1_;
        this.HorizonCode_Horizon_È(1);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        final EntityLivingBase var1 = this.HorizonCode_Horizon_È.Ñ¢Ó();
        return this.HorizonCode_Horizon_È.ÇŽÅ() > 0 || (var1 != null && this.HorizonCode_Horizon_È.Âµá€(var1) < 9.0);
    }
    
    @Override
    public void Âµá€() {
        this.HorizonCode_Horizon_È.Š().à();
        this.Â = this.HorizonCode_Horizon_È.Ñ¢Ó();
    }
    
    @Override
    public void Ý() {
        this.Â = null;
    }
    
    @Override
    public void Ø­áŒŠá() {
        if (this.Â == null) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(-1);
        }
        else if (this.HorizonCode_Horizon_È.Âµá€(this.Â) > 49.0) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(-1);
        }
        else if (!this.HorizonCode_Horizon_È.Ø­Ñ¢á€().HorizonCode_Horizon_È(this.Â)) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(-1);
        }
        else {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(1);
        }
    }
}
