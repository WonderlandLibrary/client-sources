package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;

public class RenderArrow extends Render
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00000978";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/arrow.png");
    }
    
    public RenderArrow(final RenderManager p_i46193_1_) {
        super(p_i46193_1_);
    }
    
    public void HorizonCode_Horizon_È(final EntityArrow p_180551_1_, final double p_180551_2_, final double p_180551_4_, final double p_180551_6_, final float p_180551_8_, final float p_180551_9_) {
        this.Ý(p_180551_1_);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.Çªà¢();
        GlStateManager.Â((float)p_180551_2_, (float)p_180551_4_, (float)p_180551_6_);
        GlStateManager.Â(p_180551_1_.á€ + (p_180551_1_.É - p_180551_1_.á€) * p_180551_9_ - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(p_180551_1_.Õ + (p_180551_1_.áƒ - p_180551_1_.Õ) * p_180551_9_, 0.0f, 0.0f, 1.0f);
        final Tessellator var10 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var11 = var10.Ý();
        final byte var12 = 0;
        final float var13 = 0.0f;
        final float var14 = 0.5f;
        final float var15 = (0 + var12 * 10) / 32.0f;
        final float var16 = (5 + var12 * 10) / 32.0f;
        final float var17 = 0.0f;
        final float var18 = 0.15625f;
        final float var19 = (5 + var12 * 10) / 32.0f;
        final float var20 = (10 + var12 * 10) / 32.0f;
        final float var21 = 0.05625f;
        GlStateManager.ŠÄ();
        final float var22 = p_180551_1_.Â - p_180551_9_;
        if (var22 > 0.0f) {
            final float var23 = -MathHelper.HorizonCode_Horizon_È(var22 * 3.0f) * var22;
            GlStateManager.Â(var23, 0.0f, 0.0f, 1.0f);
        }
        GlStateManager.Â(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.HorizonCode_Horizon_È(var21, var21, var21);
        GlStateManager.Â(-4.0f, 0.0f, 0.0f);
        GL11.glNormal3f(var21, 0.0f, 0.0f);
        var11.Â();
        var11.HorizonCode_Horizon_È(-7.0, -2.0, -2.0, var17, var19);
        var11.HorizonCode_Horizon_È(-7.0, -2.0, 2.0, var18, var19);
        var11.HorizonCode_Horizon_È(-7.0, 2.0, 2.0, var18, var20);
        var11.HorizonCode_Horizon_È(-7.0, 2.0, -2.0, var17, var20);
        var10.Â();
        GL11.glNormal3f(-var21, 0.0f, 0.0f);
        var11.Â();
        var11.HorizonCode_Horizon_È(-7.0, 2.0, -2.0, var17, var19);
        var11.HorizonCode_Horizon_È(-7.0, 2.0, 2.0, var18, var19);
        var11.HorizonCode_Horizon_È(-7.0, -2.0, 2.0, var18, var20);
        var11.HorizonCode_Horizon_È(-7.0, -2.0, -2.0, var17, var20);
        var10.Â();
        for (int var24 = 0; var24 < 4; ++var24) {
            GlStateManager.Â(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, var21);
            var11.Â();
            var11.HorizonCode_Horizon_È(-8.0, -2.0, 0.0, var13, var15);
            var11.HorizonCode_Horizon_È(8.0, -2.0, 0.0, var14, var15);
            var11.HorizonCode_Horizon_È(8.0, 2.0, 0.0, var14, var16);
            var11.HorizonCode_Horizon_È(-8.0, 2.0, 0.0, var13, var16);
            var10.Â();
        }
        GlStateManager.Ñ¢á();
        GlStateManager.Ê();
        super.HorizonCode_Horizon_È(p_180551_1_, p_180551_2_, p_180551_4_, p_180551_6_, p_180551_8_, p_180551_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityArrow p_180550_1_) {
        return RenderArrow.HorizonCode_Horizon_È;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityArrow)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityArrow)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
