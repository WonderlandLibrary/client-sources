package HORIZON-6-0-SKIDPROTECTION;

public class RenderFallingBlock extends Render
{
    private static final String HorizonCode_Horizon_È = "CL_00000994";
    
    public RenderFallingBlock(final RenderManager p_i46177_1_) {
        super(p_i46177_1_);
        this.Ý = 0.5f;
    }
    
    public void HorizonCode_Horizon_È(final EntityFallingBlock p_180557_1_, final double p_180557_2_, final double p_180557_4_, final double p_180557_6_, final float p_180557_8_, final float p_180557_9_) {
        if (p_180557_1_.Ø() != null) {
            this.HorizonCode_Horizon_È(TextureMap.à);
            final IBlockState var10 = p_180557_1_.Ø();
            final Block var11 = var10.Ý();
            final BlockPos var12 = new BlockPos(p_180557_1_);
            final World var13 = p_180557_1_.à();
            if (var10 != var13.Â(var12) && var11.ÂµÈ() != -1 && var11.ÂµÈ() == 3) {
                GlStateManager.Çªà¢();
                GlStateManager.Â((float)p_180557_2_, (float)p_180557_4_, (float)p_180557_6_);
                GlStateManager.Ó();
                final Tessellator var14 = Tessellator.HorizonCode_Horizon_È();
                final WorldRenderer var15 = var14.Ý();
                var15.Â();
                var15.HorizonCode_Horizon_È(DefaultVertexFormats.HorizonCode_Horizon_È);
                final int var16 = var12.HorizonCode_Horizon_È();
                final int var17 = var12.Â();
                final int var18 = var12.Ý();
                var15.Ý(-var16 - 0.5f, -var17, (double)(-var18 - 0.5f));
                final BlockRendererDispatcher var19 = Minecraft.áŒŠà().Ô();
                final IBakedModel var20 = var19.HorizonCode_Horizon_È(var10, var13, null);
                var19.Â().HorizonCode_Horizon_È(var13, var20, var10, var12, var15, false);
                var15.Ý(0.0, 0.0, 0.0);
                var14.Â();
                GlStateManager.Âµá€();
                GlStateManager.Ê();
                super.HorizonCode_Horizon_È(p_180557_1_, p_180557_2_, p_180557_4_, p_180557_6_, p_180557_8_, p_180557_9_);
            }
        }
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityFallingBlock p_110775_1_) {
        return TextureMap.à;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityFallingBlock)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityFallingBlock)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
