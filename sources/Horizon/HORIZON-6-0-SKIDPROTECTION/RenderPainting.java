package HORIZON-6-0-SKIDPROTECTION;

public class RenderPainting extends Render
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00001018";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/painting/paintings_kristoffer_zetterstrand.png");
    }
    
    public RenderPainting(final RenderManager p_i46150_1_) {
        super(p_i46150_1_);
    }
    
    public void HorizonCode_Horizon_È(final EntityPainting p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GlStateManager.Çªà¢();
        GlStateManager.Â(p_76986_2_, p_76986_4_, p_76986_6_);
        GlStateManager.Â(180.0f - p_76986_8_, 0.0f, 1.0f, 0.0f);
        GlStateManager.ŠÄ();
        this.Ý(p_76986_1_);
        final EntityPainting.HorizonCode_Horizon_È var10 = p_76986_1_.Ý;
        final float var11 = 0.0625f;
        GlStateManager.HorizonCode_Horizon_È(var11, var11, var11);
        this.HorizonCode_Horizon_È(p_76986_1_, var10.Çªà¢, var10.Ê, var10.ÇŽÉ, var10.ˆá);
        GlStateManager.Ñ¢á();
        GlStateManager.Ê();
        super.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityPainting p_180562_1_) {
        return RenderPainting.HorizonCode_Horizon_È;
    }
    
    private void HorizonCode_Horizon_È(final EntityPainting p_77010_1_, final int p_77010_2_, final int p_77010_3_, final int p_77010_4_, final int p_77010_5_) {
        final float var6 = -p_77010_2_ / 2.0f;
        final float var7 = -p_77010_3_ / 2.0f;
        final float var8 = 0.5f;
        final float var9 = 0.75f;
        final float var10 = 0.8125f;
        final float var11 = 0.0f;
        final float var12 = 0.0625f;
        final float var13 = 0.75f;
        final float var14 = 0.8125f;
        final float var15 = 0.001953125f;
        final float var16 = 0.001953125f;
        final float var17 = 0.7519531f;
        final float var18 = 0.7519531f;
        final float var19 = 0.0f;
        final float var20 = 0.0625f;
        for (int var21 = 0; var21 < p_77010_2_ / 16; ++var21) {
            for (int var22 = 0; var22 < p_77010_3_ / 16; ++var22) {
                final float var23 = var6 + (var21 + 1) * 16;
                final float var24 = var6 + var21 * 16;
                final float var25 = var7 + (var22 + 1) * 16;
                final float var26 = var7 + var22 * 16;
                this.HorizonCode_Horizon_È(p_77010_1_, (var23 + var24) / 2.0f, (var25 + var26) / 2.0f);
                final float var27 = (p_77010_4_ + p_77010_2_ - var21 * 16) / 256.0f;
                final float var28 = (p_77010_4_ + p_77010_2_ - (var21 + 1) * 16) / 256.0f;
                final float var29 = (p_77010_5_ + p_77010_3_ - var22 * 16) / 256.0f;
                final float var30 = (p_77010_5_ + p_77010_3_ - (var22 + 1) * 16) / 256.0f;
                final Tessellator var31 = Tessellator.HorizonCode_Horizon_È();
                final WorldRenderer var32 = var31.Ý();
                var32.Â();
                var32.Ý(0.0f, 0.0f, -1.0f);
                var32.HorizonCode_Horizon_È(var23, var26, -var8, var28, var29);
                var32.HorizonCode_Horizon_È(var24, var26, -var8, var27, var29);
                var32.HorizonCode_Horizon_È(var24, var25, -var8, var27, var30);
                var32.HorizonCode_Horizon_È(var23, var25, -var8, var28, var30);
                var32.Ý(0.0f, 0.0f, 1.0f);
                var32.HorizonCode_Horizon_È(var23, var25, var8, var9, var11);
                var32.HorizonCode_Horizon_È(var24, var25, var8, var10, var11);
                var32.HorizonCode_Horizon_È(var24, var26, var8, var10, var12);
                var32.HorizonCode_Horizon_È(var23, var26, var8, var9, var12);
                var32.Ý(0.0f, 1.0f, 0.0f);
                var32.HorizonCode_Horizon_È(var23, var25, -var8, var13, var15);
                var32.HorizonCode_Horizon_È(var24, var25, -var8, var14, var15);
                var32.HorizonCode_Horizon_È(var24, var25, var8, var14, var16);
                var32.HorizonCode_Horizon_È(var23, var25, var8, var13, var16);
                var32.Ý(0.0f, -1.0f, 0.0f);
                var32.HorizonCode_Horizon_È(var23, var26, var8, var13, var15);
                var32.HorizonCode_Horizon_È(var24, var26, var8, var14, var15);
                var32.HorizonCode_Horizon_È(var24, var26, -var8, var14, var16);
                var32.HorizonCode_Horizon_È(var23, var26, -var8, var13, var16);
                var32.Ý(-1.0f, 0.0f, 0.0f);
                var32.HorizonCode_Horizon_È(var23, var25, var8, var18, var19);
                var32.HorizonCode_Horizon_È(var23, var26, var8, var18, var20);
                var32.HorizonCode_Horizon_È(var23, var26, -var8, var17, var20);
                var32.HorizonCode_Horizon_È(var23, var25, -var8, var17, var19);
                var32.Ý(1.0f, 0.0f, 0.0f);
                var32.HorizonCode_Horizon_È(var24, var25, -var8, var18, var19);
                var32.HorizonCode_Horizon_È(var24, var26, -var8, var18, var20);
                var32.HorizonCode_Horizon_È(var24, var26, var8, var17, var20);
                var32.HorizonCode_Horizon_È(var24, var25, var8, var17, var19);
                var31.Â();
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final EntityPainting p_77008_1_, final float p_77008_2_, final float p_77008_3_) {
        int var4 = MathHelper.Ý(p_77008_1_.ŒÏ);
        final int var5 = MathHelper.Ý(p_77008_1_.Çªà¢ + p_77008_3_ / 16.0f);
        int var6 = MathHelper.Ý(p_77008_1_.Ê);
        final EnumFacing var7 = p_77008_1_.Â;
        if (var7 == EnumFacing.Ý) {
            var4 = MathHelper.Ý(p_77008_1_.ŒÏ + p_77008_2_ / 16.0f);
        }
        if (var7 == EnumFacing.Âµá€) {
            var6 = MathHelper.Ý(p_77008_1_.Ê - p_77008_2_ / 16.0f);
        }
        if (var7 == EnumFacing.Ø­áŒŠá) {
            var4 = MathHelper.Ý(p_77008_1_.ŒÏ - p_77008_2_ / 16.0f);
        }
        if (var7 == EnumFacing.Ó) {
            var6 = MathHelper.Ý(p_77008_1_.Ê + p_77008_2_ / 16.0f);
        }
        final int var8 = this.Â.Âµá€.HorizonCode_Horizon_È(new BlockPos(var4, var5, var6), 0);
        final int var9 = var8 % 65536;
        final int var10 = var8 / 65536;
        OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var9, var10);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityPainting)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityPainting)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
