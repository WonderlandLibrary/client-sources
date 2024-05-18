package HORIZON-6-0-SKIDPROTECTION;

public class RenderFireball extends Render
{
    private float HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00000995";
    
    public RenderFireball(final RenderManager p_i46176_1_, final float p_i46176_2_) {
        super(p_i46176_1_);
        this.HorizonCode_Horizon_È = p_i46176_2_;
    }
    
    public void HorizonCode_Horizon_È(final EntityFireball p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GlStateManager.Çªà¢();
        this.Ý(p_76986_1_);
        GlStateManager.Â((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GlStateManager.ŠÄ();
        final float var10 = this.HorizonCode_Horizon_È;
        GlStateManager.HorizonCode_Horizon_È(var10 / 1.0f, var10 / 1.0f, var10 / 1.0f);
        final TextureAtlasSprite var11 = Minecraft.áŒŠà().áˆºÏ().HorizonCode_Horizon_È().HorizonCode_Horizon_È(Items.ÇŽØ);
        final Tessellator var12 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var13 = var12.Ý();
        final float var14 = var11.Âµá€();
        final float var15 = var11.Ó();
        final float var16 = var11.à();
        final float var17 = var11.Ø();
        final float var18 = 1.0f;
        final float var19 = 0.5f;
        final float var20 = 0.25f;
        GlStateManager.Â(180.0f - RenderManager.Ø, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(-RenderManager.áŒŠÆ, 1.0f, 0.0f, 0.0f);
        var13.Â();
        var13.Ý(0.0f, 1.0f, 0.0f);
        var13.HorizonCode_Horizon_È(0.0f - var19, 0.0f - var20, 0.0, var14, var17);
        var13.HorizonCode_Horizon_È(var18 - var19, 0.0f - var20, 0.0, var15, var17);
        var13.HorizonCode_Horizon_È(var18 - var19, 1.0f - var20, 0.0, var15, var16);
        var13.HorizonCode_Horizon_È(0.0f - var19, 1.0f - var20, 0.0, var14, var16);
        var12.Â();
        GlStateManager.Ñ¢á();
        GlStateManager.Ê();
        super.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityFireball p_180556_1_) {
        return TextureMap.à;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityFireball)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityFireball)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
