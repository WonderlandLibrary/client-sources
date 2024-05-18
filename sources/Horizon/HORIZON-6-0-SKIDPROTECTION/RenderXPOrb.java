package HORIZON-6-0-SKIDPROTECTION;

public class RenderXPOrb extends Render
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00000993";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/experience_orb.png");
    }
    
    public RenderXPOrb(final RenderManager p_i46178_1_) {
        super(p_i46178_1_);
        this.Ý = 0.15f;
        this.Ø­áŒŠá = 0.75f;
    }
    
    public void HorizonCode_Horizon_È(final EntityXPOrb p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GlStateManager.Çªà¢();
        GlStateManager.Â((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        this.Ý(p_76986_1_);
        final int var10 = p_76986_1_.Ø();
        final float var11 = (var10 % 4 * 16 + 0) / 64.0f;
        final float var12 = (var10 % 4 * 16 + 16) / 64.0f;
        final float var13 = (var10 / 4 * 16 + 0) / 64.0f;
        final float var14 = (var10 / 4 * 16 + 16) / 64.0f;
        final float var15 = 1.0f;
        final float var16 = 0.5f;
        final float var17 = 0.25f;
        final int var18 = p_76986_1_.HorizonCode_Horizon_È(p_76986_9_);
        final int var19 = var18 % 65536;
        int var20 = var18 / 65536;
        OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var19 / 1.0f, var20 / 1.0f);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        final float var21 = 255.0f;
        final float var22 = (p_76986_1_.HorizonCode_Horizon_È + p_76986_9_) / 2.0f;
        var20 = (int)((MathHelper.HorizonCode_Horizon_È(var22 + 0.0f) + 1.0f) * 0.5f * var21);
        final int var23 = (int)var21;
        final int var24 = (int)((MathHelper.HorizonCode_Horizon_È(var22 + 4.1887903f) + 1.0f) * 0.1f * var21);
        final int var25 = var20 << 16 | var23 << 8 | var24;
        GlStateManager.Â(180.0f - RenderManager.Ø, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(-RenderManager.áŒŠÆ, 1.0f, 0.0f, 0.0f);
        final float var26 = 0.3f;
        GlStateManager.HorizonCode_Horizon_È(var26, var26, var26);
        final Tessellator var27 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var28 = var27.Ý();
        var28.Â();
        var28.HorizonCode_Horizon_È(var25, 128);
        var28.Ý(0.0f, 1.0f, 0.0f);
        var28.HorizonCode_Horizon_È(0.0f - var16, 0.0f - var17, 0.0, var11, var14);
        var28.HorizonCode_Horizon_È(var15 - var16, 0.0f - var17, 0.0, var12, var14);
        var28.HorizonCode_Horizon_È(var15 - var16, 1.0f - var17, 0.0, var12, var13);
        var28.HorizonCode_Horizon_È(0.0f - var16, 1.0f - var17, 0.0, var11, var13);
        var27.Â();
        GlStateManager.ÂµÈ();
        GlStateManager.Ñ¢á();
        GlStateManager.Ê();
        super.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityXPOrb p_180555_1_) {
        return RenderXPOrb.HorizonCode_Horizon_È;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityXPOrb)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityXPOrb)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
