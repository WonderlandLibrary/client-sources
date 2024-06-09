package HORIZON-6-0-SKIDPROTECTION;

public class ModelSprite
{
    private ModelRenderer HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private float Ø­áŒŠá;
    private float Âµá€;
    private float Ó;
    private int à;
    private int Ø;
    private int áŒŠÆ;
    private float áˆºÑ¢Õ;
    private float ÂµÈ;
    private float á;
    private float ˆÏ­;
    private float £á;
    
    public ModelSprite(final ModelRenderer modelRenderer, final int textureOffsetX, final int textureOffsetY, final float posX, final float posY, final float posZ, final int sizeX, final int sizeY, final int sizeZ, final float sizeAdd) {
        this.HorizonCode_Horizon_È = null;
        this.Â = 0;
        this.Ý = 0;
        this.Ø­áŒŠá = 0.0f;
        this.Âµá€ = 0.0f;
        this.Ó = 0.0f;
        this.à = 0;
        this.Ø = 0;
        this.áŒŠÆ = 0;
        this.áˆºÑ¢Õ = 0.0f;
        this.ÂµÈ = 0.0f;
        this.á = 0.0f;
        this.ˆÏ­ = 0.0f;
        this.£á = 0.0f;
        this.HorizonCode_Horizon_È = modelRenderer;
        this.Â = textureOffsetX;
        this.Ý = textureOffsetY;
        this.Ø­áŒŠá = posX;
        this.Âµá€ = posY;
        this.Ó = posZ;
        this.à = sizeX;
        this.Ø = sizeY;
        this.áŒŠÆ = sizeZ;
        this.áˆºÑ¢Õ = sizeAdd;
        this.ÂµÈ = textureOffsetX / modelRenderer.HorizonCode_Horizon_È;
        this.á = textureOffsetY / modelRenderer.Â;
        this.ˆÏ­ = (textureOffsetX + sizeX) / modelRenderer.HorizonCode_Horizon_È;
        this.£á = (textureOffsetY + sizeY) / modelRenderer.Â;
    }
    
    public void HorizonCode_Horizon_È(final Tessellator tessellator, final float scale) {
        GlStateManager.Â(this.Ø­áŒŠá * scale, this.Âµá€ * scale, this.Ó * scale);
        float rMinU = this.ÂµÈ;
        float rMaxU = this.ˆÏ­;
        float rMinV = this.á;
        float rMaxV = this.£á;
        if (this.HorizonCode_Horizon_È.áŒŠÆ) {
            rMinU = this.ˆÏ­;
            rMaxU = this.ÂµÈ;
        }
        if (this.HorizonCode_Horizon_È.¥Æ) {
            rMinV = this.£á;
            rMaxV = this.á;
        }
        HorizonCode_Horizon_È(tessellator, rMinU, rMinV, rMaxU, rMaxV, this.à, this.Ø, scale * this.áŒŠÆ, this.HorizonCode_Horizon_È.HorizonCode_Horizon_È, this.HorizonCode_Horizon_È.Â);
        GlStateManager.Â(-this.Ø­áŒŠá * scale, -this.Âµá€ * scale, -this.Ó * scale);
    }
    
    public static void HorizonCode_Horizon_È(final Tessellator tess, final float minU, final float minV, final float maxU, final float maxV, final int sizeX, final int sizeY, float width, final float texWidth, final float texHeight) {
        if (width < 6.25E-4f) {
            width = 6.25E-4f;
        }
        final float dU = maxU - minU;
        final float dV = maxV - minV;
        final double dimX = MathHelper.Âµá€(dU) * (texWidth / 16.0f);
        final double dimY = MathHelper.Âµá€(dV) * (texHeight / 16.0f);
        final WorldRenderer tessellator = tess.Ý();
        tessellator.Â();
        tessellator.Ý(0.0f, 0.0f, -1.0f);
        tessellator.HorizonCode_Horizon_È(0.0, 0.0, 0.0, minU, minV);
        tessellator.HorizonCode_Horizon_È(dimX, 0.0, 0.0, maxU, minV);
        tessellator.HorizonCode_Horizon_È(dimX, dimY, 0.0, maxU, maxV);
        tessellator.HorizonCode_Horizon_È(0.0, dimY, 0.0, minU, maxV);
        tess.Â();
        tessellator.Â();
        tessellator.Ý(0.0f, 0.0f, 1.0f);
        tessellator.HorizonCode_Horizon_È(0.0, dimY, width, minU, maxV);
        tessellator.HorizonCode_Horizon_È(dimX, dimY, width, maxU, maxV);
        tessellator.HorizonCode_Horizon_È(dimX, 0.0, width, maxU, minV);
        tessellator.HorizonCode_Horizon_È(0.0, 0.0, width, minU, minV);
        tess.Â();
        final float var8 = 0.5f * dU / sizeX;
        final float var9 = 0.5f * dV / sizeY;
        tessellator.Â();
        tessellator.Ý(-1.0f, 0.0f, 0.0f);
        for (int var10 = 0; var10 < sizeX; ++var10) {
            final float var11 = var10 / sizeX;
            final float var12 = minU + dU * var11 + var8;
            tessellator.HorizonCode_Horizon_È(var11 * dimX, 0.0, width, var12, minV);
            tessellator.HorizonCode_Horizon_È(var11 * dimX, 0.0, 0.0, var12, minV);
            tessellator.HorizonCode_Horizon_È(var11 * dimX, dimY, 0.0, var12, maxV);
            tessellator.HorizonCode_Horizon_È(var11 * dimX, dimY, width, var12, maxV);
        }
        tess.Â();
        tessellator.Â();
        tessellator.Ý(1.0f, 0.0f, 0.0f);
        for (int var10 = 0; var10 < sizeX; ++var10) {
            final float var11 = var10 / sizeX;
            final float var12 = minU + dU * var11 + var8;
            final float var13 = var11 + 1.0f / sizeX;
            tessellator.HorizonCode_Horizon_È(var13 * dimX, dimY, width, var12, maxV);
            tessellator.HorizonCode_Horizon_È(var13 * dimX, dimY, 0.0, var12, maxV);
            tessellator.HorizonCode_Horizon_È(var13 * dimX, 0.0, 0.0, var12, minV);
            tessellator.HorizonCode_Horizon_È(var13 * dimX, 0.0, width, var12, minV);
        }
        tess.Â();
        tessellator.Â();
        tessellator.Ý(0.0f, 1.0f, 0.0f);
        for (int var10 = 0; var10 < sizeY; ++var10) {
            final float var11 = var10 / sizeY;
            final float var12 = minV + dV * var11 + var9;
            final float var13 = var11 + 1.0f / sizeY;
            tessellator.HorizonCode_Horizon_È(0.0, var13 * dimY, 0.0, minU, var12);
            tessellator.HorizonCode_Horizon_È(dimX, var13 * dimY, 0.0, maxU, var12);
            tessellator.HorizonCode_Horizon_È(dimX, var13 * dimY, width, maxU, var12);
            tessellator.HorizonCode_Horizon_È(0.0, var13 * dimY, width, minU, var12);
        }
        tess.Â();
        tessellator.Â();
        tessellator.Ý(0.0f, -1.0f, 0.0f);
        for (int var10 = 0; var10 < sizeY; ++var10) {
            final float var11 = var10 / sizeY;
            final float var12 = minV + dV * var11 + var9;
            tessellator.HorizonCode_Horizon_È(dimX, var11 * dimY, 0.0, maxU, var12);
            tessellator.HorizonCode_Horizon_È(0.0, var11 * dimY, 0.0, minU, var12);
            tessellator.HorizonCode_Horizon_È(0.0, var11 * dimY, width, minU, var12);
            tessellator.HorizonCode_Horizon_È(dimX, var11 * dimY, width, maxU, var12);
        }
        tess.Â();
    }
}
