package HORIZON-6-0-SKIDPROTECTION;

public class TileEntityPistonRenderer extends TileEntitySpecialRenderer
{
    private final BlockRendererDispatcher HorizonCode_Horizon_È;
    private static final String Ø­áŒŠá = "CL_00002469";
    
    public TileEntityPistonRenderer() {
        this.HorizonCode_Horizon_È = Minecraft.áŒŠà().Ô();
    }
    
    public void HorizonCode_Horizon_È(final TileEntityPiston p_178461_1_, final double p_178461_2_, final double p_178461_4_, final double p_178461_6_, final float p_178461_8_, final int p_178461_9_) {
        final BlockPos var10 = p_178461_1_.á();
        IBlockState var11 = p_178461_1_.Â();
        final Block var12 = var11.Ý();
        if (var12.Ó() != Material.HorizonCode_Horizon_È && p_178461_1_.HorizonCode_Horizon_È(p_178461_8_) < 1.0f) {
            final Tessellator var13 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var14 = var13.Ý();
            this.HorizonCode_Horizon_È(TextureMap.à);
            RenderHelper.HorizonCode_Horizon_È();
            GlStateManager.Â(770, 771);
            GlStateManager.á();
            GlStateManager.£à();
            if (Minecraft.Ï­Ðƒà()) {
                GlStateManager.áˆºÑ¢Õ(7425);
            }
            else {
                GlStateManager.áˆºÑ¢Õ(7424);
            }
            var14.Â();
            var14.HorizonCode_Horizon_È(DefaultVertexFormats.HorizonCode_Horizon_È);
            var14.Ý((float)p_178461_2_ - var10.HorizonCode_Horizon_È() + p_178461_1_.Â(p_178461_8_), (float)p_178461_4_ - var10.Â() + p_178461_1_.Ý(p_178461_8_), (double)((float)p_178461_6_ - var10.Ý() + p_178461_1_.Ø­áŒŠá(p_178461_8_)));
            var14.Â(1.0f, 1.0f, 1.0f);
            final World var15 = this.HorizonCode_Horizon_È();
            if (var12 == Blocks.à¢ && p_178461_1_.HorizonCode_Horizon_È(p_178461_8_) < 0.5f) {
                var11 = var11.HorizonCode_Horizon_È(BlockPistonExtension.ŠÂµà, true);
                this.HorizonCode_Horizon_È.Â().HorizonCode_Horizon_È(var15, this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var11, var15, var10), var11, var10, var14, true);
            }
            else if (p_178461_1_.Âµá€() && !p_178461_1_.Ý()) {
                final BlockPistonExtension.HorizonCode_Horizon_È var16 = (var12 == Blocks.ÇŽÕ) ? BlockPistonExtension.HorizonCode_Horizon_È.Â : BlockPistonExtension.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
                IBlockState var17 = Blocks.à¢.¥à().HorizonCode_Horizon_È(BlockPistonExtension.à¢, var16).HorizonCode_Horizon_È(BlockPistonExtension.Õ, var11.HorizonCode_Horizon_È(BlockPistonBase.Õ));
                var17 = var17.HorizonCode_Horizon_È(BlockPistonExtension.ŠÂµà, p_178461_1_.HorizonCode_Horizon_È(p_178461_8_) >= 0.5f);
                this.HorizonCode_Horizon_È.Â().HorizonCode_Horizon_È(var15, this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var17, var15, var10), var17, var10, var14, true);
                var14.Ý((float)p_178461_2_ - var10.HorizonCode_Horizon_È(), (float)p_178461_4_ - var10.Â(), (double)((float)p_178461_6_ - var10.Ý()));
                var11.HorizonCode_Horizon_È(BlockPistonBase.à¢, true);
                this.HorizonCode_Horizon_È.Â().HorizonCode_Horizon_È(var15, this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var11, var15, var10), var11, var10, var14, true);
            }
            else {
                this.HorizonCode_Horizon_È.Â().HorizonCode_Horizon_È(var15, this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var11, var15, var10), var11, var10, var14, false);
            }
            var14.Ý(0.0, 0.0, 0.0);
            var13.Â();
            RenderHelper.Â();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_, final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
        this.HorizonCode_Horizon_È((TileEntityPiston)p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
    }
}
