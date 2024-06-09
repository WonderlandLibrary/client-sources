package HORIZON-6-0-SKIDPROTECTION;

public class TexturedQuad
{
    public PositionTextureVertex[] HorizonCode_Horizon_È;
    public int Â;
    private boolean Ý;
    private static final String Ø­áŒŠá = "CL_00000850";
    
    public TexturedQuad(final PositionTextureVertex[] p_i46364_1_) {
        this.HorizonCode_Horizon_È = p_i46364_1_;
        this.Â = p_i46364_1_.length;
    }
    
    public TexturedQuad(final PositionTextureVertex[] p_i1153_1_, final int p_i1153_2_, final int p_i1153_3_, final int p_i1153_4_, final int p_i1153_5_, final float p_i1153_6_, final float p_i1153_7_) {
        this(p_i1153_1_);
        final float var8 = 0.0f / p_i1153_6_;
        final float var9 = 0.0f / p_i1153_7_;
        p_i1153_1_[0] = p_i1153_1_[0].HorizonCode_Horizon_È(p_i1153_4_ / p_i1153_6_ - var8, p_i1153_3_ / p_i1153_7_ + var9);
        p_i1153_1_[1] = p_i1153_1_[1].HorizonCode_Horizon_È(p_i1153_2_ / p_i1153_6_ + var8, p_i1153_3_ / p_i1153_7_ + var9);
        p_i1153_1_[2] = p_i1153_1_[2].HorizonCode_Horizon_È(p_i1153_2_ / p_i1153_6_ + var8, p_i1153_5_ / p_i1153_7_ - var9);
        p_i1153_1_[3] = p_i1153_1_[3].HorizonCode_Horizon_È(p_i1153_4_ / p_i1153_6_ - var8, p_i1153_5_ / p_i1153_7_ - var9);
    }
    
    public void HorizonCode_Horizon_È() {
        final PositionTextureVertex[] var1 = new PositionTextureVertex[this.HorizonCode_Horizon_È.length];
        for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.length; ++var2) {
            var1[var2] = this.HorizonCode_Horizon_È[this.HorizonCode_Horizon_È.length - var2 - 1];
        }
        this.HorizonCode_Horizon_È = var1;
    }
    
    public void HorizonCode_Horizon_È(final WorldRenderer p_178765_1_, final float p_178765_2_) {
        final Vec3 var3 = this.HorizonCode_Horizon_È[1].HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È[0].HorizonCode_Horizon_È);
        final Vec3 var4 = this.HorizonCode_Horizon_È[1].HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È[2].HorizonCode_Horizon_È);
        final Vec3 var5 = var4.Ý(var3).HorizonCode_Horizon_È();
        p_178765_1_.Â();
        if (this.Ý) {
            p_178765_1_.Ý(-(float)var5.HorizonCode_Horizon_È, -(float)var5.Â, -(float)var5.Ý);
        }
        else {
            p_178765_1_.Ý((float)var5.HorizonCode_Horizon_È, (float)var5.Â, (float)var5.Ý);
        }
        for (int var6 = 0; var6 < 4; ++var6) {
            final PositionTextureVertex var7 = this.HorizonCode_Horizon_È[var6];
            p_178765_1_.HorizonCode_Horizon_È(var7.HorizonCode_Horizon_È.HorizonCode_Horizon_È * p_178765_2_, var7.HorizonCode_Horizon_È.Â * p_178765_2_, var7.HorizonCode_Horizon_È.Ý * p_178765_2_, var7.Â, var7.Ý);
        }
        Tessellator.HorizonCode_Horizon_È().Â();
    }
}
