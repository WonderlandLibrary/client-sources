package HORIZON-6-0-SKIDPROTECTION;

public class RenderTNTPrimed extends Render
{
    private static final String HorizonCode_Horizon_È = "CL_00001030";
    
    public RenderTNTPrimed(final RenderManager p_i46134_1_) {
        super(p_i46134_1_);
        this.Ý = 0.5f;
    }
    
    public void HorizonCode_Horizon_È(final EntityTNTPrimed p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        final BlockRendererDispatcher var10 = Minecraft.áŒŠà().Ô();
        GlStateManager.Çªà¢();
        GlStateManager.Â((float)p_76986_2_, (float)p_76986_4_ + 0.5f, (float)p_76986_6_);
        if (p_76986_1_.HorizonCode_Horizon_È - p_76986_9_ + 1.0f < 10.0f) {
            float var11 = 1.0f - (p_76986_1_.HorizonCode_Horizon_È - p_76986_9_ + 1.0f) / 10.0f;
            var11 = MathHelper.HorizonCode_Horizon_È(var11, 0.0f, 1.0f);
            var11 *= var11;
            var11 *= var11;
            final float var12 = 1.0f + var11 * 0.3f;
            GlStateManager.HorizonCode_Horizon_È(var12, var12, var12);
        }
        float var11 = (1.0f - (p_76986_1_.HorizonCode_Horizon_È - p_76986_9_ + 1.0f) / 100.0f) * 0.8f;
        this.Ý(p_76986_1_);
        GlStateManager.Â(-0.5f, -0.5f, 0.5f);
        var10.HorizonCode_Horizon_È(Blocks.Ñ¢Â.¥à(), p_76986_1_.Â(p_76986_9_));
        GlStateManager.Â(0.0f, 0.0f, 1.0f);
        if (p_76986_1_.HorizonCode_Horizon_È / 5 % 2 == 0) {
            GlStateManager.Æ();
            GlStateManager.Ó();
            GlStateManager.á();
            GlStateManager.Â(770, 772);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, var11);
            GlStateManager.HorizonCode_Horizon_È(-3.0f, -3.0f);
            GlStateManager.µà();
            var10.HorizonCode_Horizon_È(Blocks.Ñ¢Â.¥à(), 1.0f);
            GlStateManager.HorizonCode_Horizon_È(0.0f, 0.0f);
            GlStateManager.ˆà();
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.ÂµÈ();
            GlStateManager.Âµá€();
            GlStateManager.µÕ();
        }
        GlStateManager.Ê();
        super.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityTNTPrimed p_180563_1_) {
        return TextureMap.à;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityTNTPrimed)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityTNTPrimed)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
