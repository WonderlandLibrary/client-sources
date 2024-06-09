package HORIZON-6-0-SKIDPROTECTION;

public class RenderFish extends Render
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00000996";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/particle/particles.png");
    }
    
    public RenderFish(final RenderManager p_i46175_1_) {
        super(p_i46175_1_);
    }
    
    public void HorizonCode_Horizon_È(final EntityFishHook p_180558_1_, final double p_180558_2_, final double p_180558_4_, final double p_180558_6_, final float p_180558_8_, final float p_180558_9_) {
        GlStateManager.Çªà¢();
        GlStateManager.Â((float)p_180558_2_, (float)p_180558_4_, (float)p_180558_6_);
        GlStateManager.ŠÄ();
        GlStateManager.HorizonCode_Horizon_È(0.5f, 0.5f, 0.5f);
        this.Ý(p_180558_1_);
        final Tessellator var10 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var11 = var10.Ý();
        final byte var12 = 1;
        final byte var13 = 2;
        final float var14 = (var12 * 8 + 0) / 128.0f;
        final float var15 = (var12 * 8 + 8) / 128.0f;
        final float var16 = (var13 * 8 + 0) / 128.0f;
        final float var17 = (var13 * 8 + 8) / 128.0f;
        final float var18 = 1.0f;
        final float var19 = 0.5f;
        final float var20 = 0.5f;
        GlStateManager.Â(180.0f - RenderManager.Ø, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(-RenderManager.áŒŠÆ, 1.0f, 0.0f, 0.0f);
        var11.Â();
        var11.Ý(0.0f, 1.0f, 0.0f);
        var11.HorizonCode_Horizon_È(0.0f - var19, 0.0f - var20, 0.0, var14, var17);
        var11.HorizonCode_Horizon_È(var18 - var19, 0.0f - var20, 0.0, var15, var17);
        var11.HorizonCode_Horizon_È(var18 - var19, 1.0f - var20, 0.0, var15, var16);
        var11.HorizonCode_Horizon_È(0.0f - var19, 1.0f - var20, 0.0, var14, var16);
        var10.Â();
        GlStateManager.Ñ¢á();
        GlStateManager.Ê();
        if (p_180558_1_.Â != null) {
            final float var21 = p_180558_1_.Â.á(p_180558_9_);
            final float var22 = MathHelper.HorizonCode_Horizon_È(MathHelper.Ý(var21) * 3.1415927f);
            Vec3 var23 = new Vec3(-0.36, 0.03, 0.35);
            var23 = var23.HorizonCode_Horizon_È(-(p_180558_1_.Â.Õ + (p_180558_1_.Â.áƒ - p_180558_1_.Â.Õ) * p_180558_9_) * 3.1415927f / 180.0f);
            var23 = var23.Â(-(p_180558_1_.Â.á€ + (p_180558_1_.Â.É - p_180558_1_.Â.á€) * p_180558_9_) * 3.1415927f / 180.0f);
            var23 = var23.Â(var22 * 0.5f);
            var23 = var23.HorizonCode_Horizon_È(-var22 * 0.7f);
            double var24 = p_180558_1_.Â.áŒŠà + (p_180558_1_.Â.ŒÏ - p_180558_1_.Â.áŒŠà) * p_180558_9_ + var23.HorizonCode_Horizon_È;
            double var25 = p_180558_1_.Â.ŠÄ + (p_180558_1_.Â.Çªà¢ - p_180558_1_.Â.ŠÄ) * p_180558_9_ + var23.Â;
            double var26 = p_180558_1_.Â.Ñ¢á + (p_180558_1_.Â.Ê - p_180558_1_.Â.Ñ¢á) * p_180558_9_ + var23.Ý;
            double var27 = p_180558_1_.Â.Ðƒáƒ();
            if ((this.Â.áˆºÑ¢Õ != null && this.Â.áˆºÑ¢Õ.µÏ > 0) || p_180558_1_.Â != Minecraft.áŒŠà().á) {
                final float var28 = (p_180558_1_.Â.£ÇªÓ + (p_180558_1_.Â.¥É - p_180558_1_.Â.£ÇªÓ) * p_180558_9_) * 3.1415927f / 180.0f;
                final double var29 = MathHelper.HorizonCode_Horizon_È(var28);
                final double var30 = MathHelper.Â(var28);
                final double var31 = 0.35;
                final double var32 = 0.8;
                var24 = p_180558_1_.Â.áŒŠà + (p_180558_1_.Â.ŒÏ - p_180558_1_.Â.áŒŠà) * p_180558_9_ - var30 * 0.35 - var29 * 0.8;
                var25 = p_180558_1_.Â.ŠÄ + var27 + (p_180558_1_.Â.Çªà¢ - p_180558_1_.Â.ŠÄ) * p_180558_9_ - 0.45;
                var26 = p_180558_1_.Â.Ñ¢á + (p_180558_1_.Â.Ê - p_180558_1_.Â.Ñ¢á) * p_180558_9_ - var29 * 0.35 + var30 * 0.8;
                var27 = (p_180558_1_.Â.Çªà¢() ? -0.1875 : 0.0);
            }
            final double var33 = p_180558_1_.áŒŠà + (p_180558_1_.ŒÏ - p_180558_1_.áŒŠà) * p_180558_9_;
            final double var34 = p_180558_1_.ŠÄ + (p_180558_1_.Çªà¢ - p_180558_1_.ŠÄ) * p_180558_9_ + 0.25;
            final double var35 = p_180558_1_.Ñ¢á + (p_180558_1_.Ê - p_180558_1_.Ñ¢á) * p_180558_9_;
            final double var36 = (float)(var24 - var33);
            final double var37 = (float)(var25 - var34) + var27;
            final double var38 = (float)(var26 - var35);
            GlStateManager.Æ();
            GlStateManager.Ó();
            var11.HorizonCode_Horizon_È(3);
            var11.Ý(0);
            final byte var39 = 16;
            for (int var40 = 0; var40 <= var39; ++var40) {
                final float var41 = var40 / var39;
                var11.Â(p_180558_2_ + var36 * var41, p_180558_4_ + var37 * (var41 * var41 + var41) * 0.5 + 0.25, p_180558_6_ + var38 * var41);
            }
            var10.Â();
            GlStateManager.Âµá€();
            GlStateManager.µÕ();
            super.HorizonCode_Horizon_È(p_180558_1_, p_180558_2_, p_180558_4_, p_180558_6_, p_180558_8_, p_180558_9_);
        }
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityFishHook p_110775_1_) {
        return RenderFish.HorizonCode_Horizon_È;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityFishHook)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityFishHook)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
