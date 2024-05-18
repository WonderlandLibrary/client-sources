package net.minecraft.src;

public class ClippingHelper
{
    public float[][] frustum;
    public float[] projectionMatrix;
    public float[] modelviewMatrix;
    public float[] clippingMatrix;
    
    public ClippingHelper() {
        this.frustum = new float[16][16];
        this.projectionMatrix = new float[16];
        this.modelviewMatrix = new float[16];
        this.clippingMatrix = new float[16];
    }
    
    public boolean isBoxInFrustum(final double par1, final double par3, final double par5, final double par7, final double par9, final double par11) {
        for (int var13 = 0; var13 < 6; ++var13) {
            final float var14 = (float)par1;
            final float var15 = (float)par3;
            final float var16 = (float)par5;
            final float var17 = (float)par7;
            final float var18 = (float)par9;
            final float var19 = (float)par11;
            if (this.frustum[var13][0] * var14 + this.frustum[var13][1] * var15 + this.frustum[var13][2] * var16 + this.frustum[var13][3] <= 0.0f && this.frustum[var13][0] * var17 + this.frustum[var13][1] * var15 + this.frustum[var13][2] * var16 + this.frustum[var13][3] <= 0.0f && this.frustum[var13][0] * var14 + this.frustum[var13][1] * var18 + this.frustum[var13][2] * var16 + this.frustum[var13][3] <= 0.0f && this.frustum[var13][0] * var17 + this.frustum[var13][1] * var18 + this.frustum[var13][2] * var16 + this.frustum[var13][3] <= 0.0f && this.frustum[var13][0] * var14 + this.frustum[var13][1] * var15 + this.frustum[var13][2] * var19 + this.frustum[var13][3] <= 0.0f && this.frustum[var13][0] * var17 + this.frustum[var13][1] * var15 + this.frustum[var13][2] * var19 + this.frustum[var13][3] <= 0.0f && this.frustum[var13][0] * var14 + this.frustum[var13][1] * var18 + this.frustum[var13][2] * var19 + this.frustum[var13][3] <= 0.0f && this.frustum[var13][0] * var17 + this.frustum[var13][1] * var18 + this.frustum[var13][2] * var19 + this.frustum[var13][3] <= 0.0f) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isBoxInFrustumFully(final double var1, final double var3, final double var5, final double var7, final double var9, final double var11) {
        for (int var12 = 0; var12 < 6; ++var12) {
            final float var13 = (float)var1;
            final float var14 = (float)var3;
            final float var15 = (float)var5;
            final float var16 = (float)var7;
            final float var17 = (float)var9;
            final float var18 = (float)var11;
            if (var12 < 4) {
                if (this.frustum[var12][0] * var13 + this.frustum[var12][1] * var14 + this.frustum[var12][2] * var15 + this.frustum[var12][3] <= 0.0f || this.frustum[var12][0] * var16 + this.frustum[var12][1] * var14 + this.frustum[var12][2] * var15 + this.frustum[var12][3] <= 0.0f || this.frustum[var12][0] * var13 + this.frustum[var12][1] * var17 + this.frustum[var12][2] * var15 + this.frustum[var12][3] <= 0.0f || this.frustum[var12][0] * var16 + this.frustum[var12][1] * var17 + this.frustum[var12][2] * var15 + this.frustum[var12][3] <= 0.0f || this.frustum[var12][0] * var13 + this.frustum[var12][1] * var14 + this.frustum[var12][2] * var18 + this.frustum[var12][3] <= 0.0f || this.frustum[var12][0] * var16 + this.frustum[var12][1] * var14 + this.frustum[var12][2] * var18 + this.frustum[var12][3] <= 0.0f || this.frustum[var12][0] * var13 + this.frustum[var12][1] * var17 + this.frustum[var12][2] * var18 + this.frustum[var12][3] <= 0.0f || this.frustum[var12][0] * var16 + this.frustum[var12][1] * var17 + this.frustum[var12][2] * var18 + this.frustum[var12][3] <= 0.0f) {
                    return false;
                }
            }
            else if (this.frustum[var12][0] * var13 + this.frustum[var12][1] * var14 + this.frustum[var12][2] * var15 + this.frustum[var12][3] <= 0.0f && this.frustum[var12][0] * var16 + this.frustum[var12][1] * var14 + this.frustum[var12][2] * var15 + this.frustum[var12][3] <= 0.0f && this.frustum[var12][0] * var13 + this.frustum[var12][1] * var17 + this.frustum[var12][2] * var15 + this.frustum[var12][3] <= 0.0f && this.frustum[var12][0] * var16 + this.frustum[var12][1] * var17 + this.frustum[var12][2] * var15 + this.frustum[var12][3] <= 0.0f && this.frustum[var12][0] * var13 + this.frustum[var12][1] * var14 + this.frustum[var12][2] * var18 + this.frustum[var12][3] <= 0.0f && this.frustum[var12][0] * var16 + this.frustum[var12][1] * var14 + this.frustum[var12][2] * var18 + this.frustum[var12][3] <= 0.0f && this.frustum[var12][0] * var13 + this.frustum[var12][1] * var17 + this.frustum[var12][2] * var18 + this.frustum[var12][3] <= 0.0f && this.frustum[var12][0] * var16 + this.frustum[var12][1] * var17 + this.frustum[var12][2] * var18 + this.frustum[var12][3] <= 0.0f) {
                return false;
            }
        }
        return true;
    }
}
