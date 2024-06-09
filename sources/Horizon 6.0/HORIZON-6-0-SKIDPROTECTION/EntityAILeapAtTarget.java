package HORIZON-6-0-SKIDPROTECTION;

public class EntityAILeapAtTarget extends EntityAIBase
{
    EntityLiving HorizonCode_Horizon_È;
    EntityLivingBase Â;
    float Ý;
    private static final String Ø­áŒŠá = "CL_00001591";
    
    public EntityAILeapAtTarget(final EntityLiving p_i1630_1_, final float p_i1630_2_) {
        this.HorizonCode_Horizon_È = p_i1630_1_;
        this.Ý = p_i1630_2_;
        this.HorizonCode_Horizon_È(5);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        this.Â = this.HorizonCode_Horizon_È.Ñ¢Ó();
        if (this.Â == null) {
            return false;
        }
        final double var1 = this.HorizonCode_Horizon_È.Âµá€(this.Â);
        return var1 >= 4.0 && var1 <= 16.0 && this.HorizonCode_Horizon_È.ŠÂµà && this.HorizonCode_Horizon_È.ˆÐƒØ().nextInt(5) == 0;
    }
    
    @Override
    public boolean Â() {
        return !this.HorizonCode_Horizon_È.ŠÂµà;
    }
    
    @Override
    public void Âµá€() {
        final double var1 = this.Â.ŒÏ - this.HorizonCode_Horizon_È.ŒÏ;
        final double var2 = this.Â.Ê - this.HorizonCode_Horizon_È.Ê;
        final float var3 = MathHelper.HorizonCode_Horizon_È(var1 * var1 + var2 * var2);
        final EntityLiving horizonCode_Horizon_È = this.HorizonCode_Horizon_È;
        horizonCode_Horizon_È.ÇŽÉ += var1 / var3 * 0.5 * 0.800000011920929 + this.HorizonCode_Horizon_È.ÇŽÉ * 0.20000000298023224;
        final EntityLiving horizonCode_Horizon_È2 = this.HorizonCode_Horizon_È;
        horizonCode_Horizon_È2.ÇŽÕ += var2 / var3 * 0.5 * 0.800000011920929 + this.HorizonCode_Horizon_È.ÇŽÕ * 0.20000000298023224;
        this.HorizonCode_Horizon_È.ˆá = this.Ý;
    }
}
