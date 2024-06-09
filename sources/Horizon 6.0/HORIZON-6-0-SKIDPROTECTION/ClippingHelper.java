package HORIZON-6-0-SKIDPROTECTION;

public class ClippingHelper
{
    public float[][] HorizonCode_Horizon_È;
    public float[] Â;
    public float[] Ý;
    public float[] Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000977";
    
    public ClippingHelper() {
        this.HorizonCode_Horizon_È = new float[6][4];
        this.Â = new float[16];
        this.Ý = new float[16];
        this.Ø­áŒŠá = new float[16];
    }
    
    private float HorizonCode_Horizon_È(final float[] p_178624_1_, final float p_178624_2_, final float p_178624_4_, final float p_178624_6_) {
        return p_178624_1_[0] * p_178624_2_ + p_178624_1_[1] * p_178624_4_ + p_178624_1_[2] * p_178624_6_ + p_178624_1_[3];
    }
    
    public boolean HorizonCode_Horizon_È(final double p_78553_1_, final double p_78553_3_, final double p_78553_5_, final double p_78553_7_, final double p_78553_9_, final double p_78553_11_) {
        final float minXf = (float)p_78553_1_;
        final float minYf = (float)p_78553_3_;
        final float minZf = (float)p_78553_5_;
        final float maxXf = (float)p_78553_7_;
        final float maxYf = (float)p_78553_9_;
        final float maxZf = (float)p_78553_11_;
        for (int var13 = 0; var13 < 6; ++var13) {
            final float[] var14 = this.HorizonCode_Horizon_È[var13];
            if (this.HorizonCode_Horizon_È(var14, minXf, minYf, minZf) <= 0.0f && this.HorizonCode_Horizon_È(var14, maxXf, minYf, minZf) <= 0.0f && this.HorizonCode_Horizon_È(var14, minXf, maxYf, minZf) <= 0.0f && this.HorizonCode_Horizon_È(var14, maxXf, maxYf, minZf) <= 0.0f && this.HorizonCode_Horizon_È(var14, minXf, minYf, maxZf) <= 0.0f && this.HorizonCode_Horizon_È(var14, maxXf, minYf, maxZf) <= 0.0f && this.HorizonCode_Horizon_È(var14, minXf, maxYf, maxZf) <= 0.0f && this.HorizonCode_Horizon_È(var14, maxXf, maxYf, maxZf) <= 0.0f) {
                return false;
            }
        }
        return true;
    }
}
