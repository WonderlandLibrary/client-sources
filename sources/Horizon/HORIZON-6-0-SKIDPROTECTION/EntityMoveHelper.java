package HORIZON-6-0-SKIDPROTECTION;

public class EntityMoveHelper
{
    protected EntityLiving HorizonCode_Horizon_È;
    protected double Â;
    protected double Ý;
    protected double Ø­áŒŠá;
    protected double Âµá€;
    protected boolean Ó;
    private static final String à = "CL_00001573";
    
    public EntityMoveHelper(final EntityLiving p_i1614_1_) {
        this.HorizonCode_Horizon_È = p_i1614_1_;
        this.Â = p_i1614_1_.ŒÏ;
        this.Ý = p_i1614_1_.Çªà¢;
        this.Ø­áŒŠá = p_i1614_1_.Ê;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Ó;
    }
    
    public double Â() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final double p_75642_1_, final double p_75642_3_, final double p_75642_5_, final double p_75642_7_) {
        this.Â = p_75642_1_;
        this.Ý = p_75642_3_;
        this.Ø­áŒŠá = p_75642_5_;
        this.Âµá€ = p_75642_7_;
        this.Ó = true;
    }
    
    public void Ý() {
        this.HorizonCode_Horizon_È.Âµá€(0.0f);
        if (this.Ó) {
            this.Ó = false;
            final int var1 = MathHelper.Ý(this.HorizonCode_Horizon_È.£É().Â + 0.5);
            final double var2 = this.Â - this.HorizonCode_Horizon_È.ŒÏ;
            final double var3 = this.Ø­áŒŠá - this.HorizonCode_Horizon_È.Ê;
            final double var4 = this.Ý - var1;
            final double var5 = var2 * var2 + var4 * var4 + var3 * var3;
            if (var5 >= 2.500000277905201E-7) {
                final float var6 = (float)(Math.atan2(var3, var2) * 180.0 / 3.141592653589793) - 90.0f;
                this.HorizonCode_Horizon_È.É = this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.É, var6, 30.0f);
                this.HorizonCode_Horizon_È.áŒŠÆ((float)(this.Âµá€ * this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).Âµá€()));
                if (var4 > 0.0 && var2 * var2 + var3 * var3 < 1.0) {
                    this.HorizonCode_Horizon_È.ÇŽÉ().HorizonCode_Horizon_È();
                }
            }
        }
    }
    
    protected float HorizonCode_Horizon_È(final float p_75639_1_, final float p_75639_2_, final float p_75639_3_) {
        float var4 = MathHelper.à(p_75639_2_ - p_75639_1_);
        if (var4 > p_75639_3_) {
            var4 = p_75639_3_;
        }
        if (var4 < -p_75639_3_) {
            var4 = -p_75639_3_;
        }
        float var5 = p_75639_1_ + var4;
        if (var5 < 0.0f) {
            var5 += 360.0f;
        }
        else if (var5 > 360.0f) {
            var5 -= 360.0f;
        }
        return var5;
    }
    
    public double Ø­áŒŠá() {
        return this.Â;
    }
    
    public double Âµá€() {
        return this.Ý;
    }
    
    public double Ó() {
        return this.Ø­áŒŠá;
    }
}
