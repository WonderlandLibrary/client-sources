package HORIZON-6-0-SKIDPROTECTION;

public class EntityBodyHelper
{
    private EntityLivingBase HorizonCode_Horizon_È;
    private int Â;
    private float Ý;
    private static final String Ø­áŒŠá = "CL_00001570";
    
    public EntityBodyHelper(final EntityLivingBase p_i1611_1_) {
        this.HorizonCode_Horizon_È = p_i1611_1_;
    }
    
    public void HorizonCode_Horizon_È() {
        final double var1 = this.HorizonCode_Horizon_È.ŒÏ - this.HorizonCode_Horizon_È.áŒŠà;
        final double var2 = this.HorizonCode_Horizon_È.Ê - this.HorizonCode_Horizon_È.Ñ¢á;
        if (var1 * var1 + var2 * var2 > 2.500000277905201E-7) {
            this.HorizonCode_Horizon_È.¥É = this.HorizonCode_Horizon_È.É;
            this.HorizonCode_Horizon_È.ÂµÕ = this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.¥É, this.HorizonCode_Horizon_È.ÂµÕ, 75.0f);
            this.Ý = this.HorizonCode_Horizon_È.ÂµÕ;
            this.Â = 0;
        }
        else {
            float var3 = 75.0f;
            if (Math.abs(this.HorizonCode_Horizon_È.ÂµÕ - this.Ý) > 15.0f) {
                this.Â = 0;
                this.Ý = this.HorizonCode_Horizon_È.ÂµÕ;
            }
            else {
                ++this.Â;
                final boolean var4 = true;
                if (this.Â > 10) {
                    var3 = Math.max(1.0f - (this.Â - 10) / 10.0f, 0.0f) * 75.0f;
                }
            }
            this.HorizonCode_Horizon_È.¥É = this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.ÂµÕ, this.HorizonCode_Horizon_È.¥É, var3);
        }
    }
    
    private float HorizonCode_Horizon_È(final float p_75665_1_, final float p_75665_2_, final float p_75665_3_) {
        float var4 = MathHelper.à(p_75665_1_ - p_75665_2_);
        if (var4 < -p_75665_3_) {
            var4 = -p_75665_3_;
        }
        if (var4 >= p_75665_3_) {
            var4 = p_75665_3_;
        }
        return p_75665_1_ - var4;
    }
}
