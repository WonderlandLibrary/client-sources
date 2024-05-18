package HORIZON-6-0-SKIDPROTECTION;

public class EntityLookHelper
{
    private EntityLiving HorizonCode_Horizon_È;
    private float Â;
    private float Ý;
    private boolean Ø­áŒŠá;
    private double Âµá€;
    private double Ó;
    private double à;
    private static final String Ø = "CL_00001572";
    
    public EntityLookHelper(final EntityLiving p_i1613_1_) {
        this.HorizonCode_Horizon_È = p_i1613_1_;
    }
    
    public void HorizonCode_Horizon_È(final Entity p_75651_1_, final float p_75651_2_, final float p_75651_3_) {
        this.Âµá€ = p_75651_1_.ŒÏ;
        if (p_75651_1_ instanceof EntityLivingBase) {
            this.Ó = p_75651_1_.Çªà¢ + p_75651_1_.Ðƒáƒ();
        }
        else {
            this.Ó = (p_75651_1_.£É().Â + p_75651_1_.£É().Âµá€) / 2.0;
        }
        this.à = p_75651_1_.Ê;
        this.Â = p_75651_2_;
        this.Ý = p_75651_3_;
        this.Ø­áŒŠá = true;
    }
    
    public void HorizonCode_Horizon_È(final double p_75650_1_, final double p_75650_3_, final double p_75650_5_, final float p_75650_7_, final float p_75650_8_) {
        this.Âµá€ = p_75650_1_;
        this.Ó = p_75650_3_;
        this.à = p_75650_5_;
        this.Â = p_75650_7_;
        this.Ý = p_75650_8_;
        this.Ø­áŒŠá = true;
    }
    
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È.áƒ = 0.0f;
        if (this.Ø­áŒŠá) {
            this.Ø­áŒŠá = false;
            final double var1 = this.Âµá€ - this.HorizonCode_Horizon_È.ŒÏ;
            final double var2 = this.Ó - (this.HorizonCode_Horizon_È.Çªà¢ + this.HorizonCode_Horizon_È.Ðƒáƒ());
            final double var3 = this.à - this.HorizonCode_Horizon_È.Ê;
            final double var4 = MathHelper.HorizonCode_Horizon_È(var1 * var1 + var3 * var3);
            final float var5 = (float)(Math.atan2(var3, var1) * 180.0 / 3.141592653589793) - 90.0f;
            final float var6 = (float)(-(Math.atan2(var2, var4) * 180.0 / 3.141592653589793));
            this.HorizonCode_Horizon_È.áƒ = this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.áƒ, var6, this.Ý);
            this.HorizonCode_Horizon_È.ÂµÕ = this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.ÂµÕ, var5, this.Â);
        }
        else {
            this.HorizonCode_Horizon_È.ÂµÕ = this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.ÂµÕ, this.HorizonCode_Horizon_È.¥É, 10.0f);
        }
        final float var7 = MathHelper.à(this.HorizonCode_Horizon_È.ÂµÕ - this.HorizonCode_Horizon_È.¥É);
        if (!this.HorizonCode_Horizon_È.Š().Ó()) {
            if (var7 < -75.0f) {
                this.HorizonCode_Horizon_È.ÂµÕ = this.HorizonCode_Horizon_È.¥É - 75.0f;
            }
            if (var7 > 75.0f) {
                this.HorizonCode_Horizon_È.ÂµÕ = this.HorizonCode_Horizon_È.¥É + 75.0f;
            }
        }
    }
    
    private float HorizonCode_Horizon_È(final float p_75652_1_, final float p_75652_2_, final float p_75652_3_) {
        float var4 = MathHelper.à(p_75652_2_ - p_75652_1_);
        if (var4 > p_75652_3_) {
            var4 = p_75652_3_;
        }
        if (var4 < -p_75652_3_) {
            var4 = -p_75652_3_;
        }
        return p_75652_1_ + var4;
    }
    
    public boolean Â() {
        return this.Ø­áŒŠá;
    }
    
    public double Ý() {
        return this.Âµá€;
    }
    
    public double Ø­áŒŠá() {
        return this.Ó;
    }
    
    public double Âµá€() {
        return this.à;
    }
}
