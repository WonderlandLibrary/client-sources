package HORIZON-6-0-SKIDPROTECTION;

import java.nio.FloatBuffer;

public class ClippingHelperImpl extends ClippingHelper
{
    private static ClippingHelperImpl Âµá€;
    private FloatBuffer Ó;
    private FloatBuffer à;
    private FloatBuffer Ø;
    private static final String áŒŠÆ = "CL_00000975";
    
    static {
        ClippingHelperImpl.Âµá€ = new ClippingHelperImpl();
    }
    
    public ClippingHelperImpl() {
        this.Ó = GLAllocation.Âµá€(16);
        this.à = GLAllocation.Âµá€(16);
        this.Ø = GLAllocation.Âµá€(16);
    }
    
    public static ClippingHelper HorizonCode_Horizon_È() {
        ClippingHelperImpl.Âµá€.Â();
        return ClippingHelperImpl.Âµá€;
    }
    
    private void HorizonCode_Horizon_È(final float[] p_180547_1_) {
        final float var2 = MathHelper.Ý(p_180547_1_[0] * p_180547_1_[0] + p_180547_1_[1] * p_180547_1_[1] + p_180547_1_[2] * p_180547_1_[2]);
        final int n = 0;
        p_180547_1_[n] /= var2;
        final int n2 = 1;
        p_180547_1_[n2] /= var2;
        final int n3 = 2;
        p_180547_1_[n3] /= var2;
        final int n4 = 3;
        p_180547_1_[n4] /= var2;
    }
    
    public void Â() {
        this.Ó.clear();
        this.à.clear();
        this.Ø.clear();
        GlStateManager.HorizonCode_Horizon_È(2983, this.Ó);
        GlStateManager.HorizonCode_Horizon_È(2982, this.à);
        final float[] var1 = this.Â;
        final float[] var2 = this.Ý;
        this.Ó.flip().limit(16);
        this.Ó.get(var1);
        this.à.flip().limit(16);
        this.à.get(var2);
        this.Ø­áŒŠá[0] = var2[0] * var1[0] + var2[1] * var1[4] + var2[2] * var1[8] + var2[3] * var1[12];
        this.Ø­áŒŠá[1] = var2[0] * var1[1] + var2[1] * var1[5] + var2[2] * var1[9] + var2[3] * var1[13];
        this.Ø­áŒŠá[2] = var2[0] * var1[2] + var2[1] * var1[6] + var2[2] * var1[10] + var2[3] * var1[14];
        this.Ø­áŒŠá[3] = var2[0] * var1[3] + var2[1] * var1[7] + var2[2] * var1[11] + var2[3] * var1[15];
        this.Ø­áŒŠá[4] = var2[4] * var1[0] + var2[5] * var1[4] + var2[6] * var1[8] + var2[7] * var1[12];
        this.Ø­áŒŠá[5] = var2[4] * var1[1] + var2[5] * var1[5] + var2[6] * var1[9] + var2[7] * var1[13];
        this.Ø­áŒŠá[6] = var2[4] * var1[2] + var2[5] * var1[6] + var2[6] * var1[10] + var2[7] * var1[14];
        this.Ø­áŒŠá[7] = var2[4] * var1[3] + var2[5] * var1[7] + var2[6] * var1[11] + var2[7] * var1[15];
        this.Ø­áŒŠá[8] = var2[8] * var1[0] + var2[9] * var1[4] + var2[10] * var1[8] + var2[11] * var1[12];
        this.Ø­áŒŠá[9] = var2[8] * var1[1] + var2[9] * var1[5] + var2[10] * var1[9] + var2[11] * var1[13];
        this.Ø­áŒŠá[10] = var2[8] * var1[2] + var2[9] * var1[6] + var2[10] * var1[10] + var2[11] * var1[14];
        this.Ø­áŒŠá[11] = var2[8] * var1[3] + var2[9] * var1[7] + var2[10] * var1[11] + var2[11] * var1[15];
        this.Ø­áŒŠá[12] = var2[12] * var1[0] + var2[13] * var1[4] + var2[14] * var1[8] + var2[15] * var1[12];
        this.Ø­áŒŠá[13] = var2[12] * var1[1] + var2[13] * var1[5] + var2[14] * var1[9] + var2[15] * var1[13];
        this.Ø­áŒŠá[14] = var2[12] * var1[2] + var2[13] * var1[6] + var2[14] * var1[10] + var2[15] * var1[14];
        this.Ø­áŒŠá[15] = var2[12] * var1[3] + var2[13] * var1[7] + var2[14] * var1[11] + var2[15] * var1[15];
        final float[] var3 = this.HorizonCode_Horizon_È[0];
        var3[0] = this.Ø­áŒŠá[3] - this.Ø­áŒŠá[0];
        var3[1] = this.Ø­áŒŠá[7] - this.Ø­áŒŠá[4];
        var3[2] = this.Ø­áŒŠá[11] - this.Ø­áŒŠá[8];
        var3[3] = this.Ø­áŒŠá[15] - this.Ø­áŒŠá[12];
        this.HorizonCode_Horizon_È(var3);
        final float[] var4 = this.HorizonCode_Horizon_È[1];
        var4[0] = this.Ø­áŒŠá[3] + this.Ø­áŒŠá[0];
        var4[1] = this.Ø­áŒŠá[7] + this.Ø­áŒŠá[4];
        var4[2] = this.Ø­áŒŠá[11] + this.Ø­áŒŠá[8];
        var4[3] = this.Ø­áŒŠá[15] + this.Ø­áŒŠá[12];
        this.HorizonCode_Horizon_È(var4);
        final float[] var5 = this.HorizonCode_Horizon_È[2];
        var5[0] = this.Ø­áŒŠá[3] + this.Ø­áŒŠá[1];
        var5[1] = this.Ø­áŒŠá[7] + this.Ø­áŒŠá[5];
        var5[2] = this.Ø­áŒŠá[11] + this.Ø­áŒŠá[9];
        var5[3] = this.Ø­áŒŠá[15] + this.Ø­áŒŠá[13];
        this.HorizonCode_Horizon_È(var5);
        final float[] var6 = this.HorizonCode_Horizon_È[3];
        var6[0] = this.Ø­áŒŠá[3] - this.Ø­áŒŠá[1];
        var6[1] = this.Ø­áŒŠá[7] - this.Ø­áŒŠá[5];
        var6[2] = this.Ø­áŒŠá[11] - this.Ø­áŒŠá[9];
        var6[3] = this.Ø­áŒŠá[15] - this.Ø­áŒŠá[13];
        this.HorizonCode_Horizon_È(var6);
        final float[] var7 = this.HorizonCode_Horizon_È[4];
        var7[0] = this.Ø­áŒŠá[3] - this.Ø­áŒŠá[2];
        var7[1] = this.Ø­áŒŠá[7] - this.Ø­áŒŠá[6];
        var7[2] = this.Ø­áŒŠá[11] - this.Ø­áŒŠá[10];
        var7[3] = this.Ø­áŒŠá[15] - this.Ø­áŒŠá[14];
        this.HorizonCode_Horizon_È(var7);
        final float[] var8 = this.HorizonCode_Horizon_È[5];
        var8[0] = this.Ø­áŒŠá[3] + this.Ø­áŒŠá[2];
        var8[1] = this.Ø­áŒŠá[7] + this.Ø­áŒŠá[6];
        var8[2] = this.Ø­áŒŠá[11] + this.Ø­áŒŠá[10];
        var8[3] = this.Ø­áŒŠá[15] + this.Ø­áŒŠá[14];
        this.HorizonCode_Horizon_È(var8);
    }
}
