package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIOcelotAttack extends EntityAIBase
{
    World HorizonCode_Horizon_È;
    EntityLiving Â;
    EntityLivingBase Ý;
    int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001600";
    
    public EntityAIOcelotAttack(final EntityLiving p_i1641_1_) {
        this.Â = p_i1641_1_;
        this.HorizonCode_Horizon_È = p_i1641_1_.Ï­Ðƒà;
        this.HorizonCode_Horizon_È(3);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        final EntityLivingBase var1 = this.Â.Ñ¢Ó();
        if (var1 == null) {
            return false;
        }
        this.Ý = var1;
        return true;
    }
    
    @Override
    public boolean Â() {
        return this.Ý.Œ() && this.Â.Âµá€(this.Ý) <= 225.0 && (!this.Â.Š().Ó() || this.HorizonCode_Horizon_È());
    }
    
    @Override
    public void Ý() {
        this.Ý = null;
        this.Â.Š().à();
    }
    
    @Override
    public void Ø­áŒŠá() {
        this.Â.Ñ¢á().HorizonCode_Horizon_È(this.Ý, 30.0f, 30.0f);
        final double var1 = this.Â.áŒŠ * 2.0f * this.Â.áŒŠ * 2.0f;
        final double var2 = this.Â.Âµá€(this.Ý.ŒÏ, this.Ý.£É().Â, this.Ý.Ê);
        double var3 = 0.8;
        if (var2 > var1 && var2 < 16.0) {
            var3 = 1.33;
        }
        else if (var2 < 225.0) {
            var3 = 0.6;
        }
        this.Â.Š().HorizonCode_Horizon_È(this.Ý, var3);
        this.Ø­áŒŠá = Math.max(this.Ø­áŒŠá - 1, 0);
        if (var2 <= var1 && this.Ø­áŒŠá <= 0) {
            this.Ø­áŒŠá = 20;
            this.Â.Å(this.Ý);
        }
    }
}
