package HORIZON-6-0-SKIDPROTECTION;

public class RenderTntMinecart extends RenderMinecart
{
    private static final String Âµá€ = "CL_00001029";
    
    public RenderTntMinecart(final RenderManager p_i46135_1_) {
        super(p_i46135_1_);
    }
    
    protected void HorizonCode_Horizon_È(final EntityMinecartTNT p_180561_1_, final float p_180561_2_, final IBlockState p_180561_3_) {
        final int var4 = p_180561_1_.ŠÄ();
        if (var4 > -1 && var4 - p_180561_2_ + 1.0f < 10.0f) {
            float var5 = 1.0f - (var4 - p_180561_2_ + 1.0f) / 10.0f;
            var5 = MathHelper.HorizonCode_Horizon_È(var5, 0.0f, 1.0f);
            var5 *= var5;
            var5 *= var5;
            final float var6 = 1.0f + var5 * 0.3f;
            GlStateManager.HorizonCode_Horizon_È(var6, var6, var6);
        }
        super.HorizonCode_Horizon_È(p_180561_1_, p_180561_2_, p_180561_3_);
        if (var4 > -1 && var4 / 5 % 2 == 0) {
            final BlockRendererDispatcher var7 = Minecraft.áŒŠà().Ô();
            GlStateManager.Æ();
            GlStateManager.Ó();
            GlStateManager.á();
            GlStateManager.Â(770, 772);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, (1.0f - (var4 - p_180561_2_ + 1.0f) / 100.0f) * 0.8f);
            GlStateManager.Çªà¢();
            var7.HorizonCode_Horizon_È(Blocks.Ñ¢Â.¥à(), 1.0f);
            GlStateManager.Ê();
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.ÂµÈ();
            GlStateManager.Âµá€();
            GlStateManager.µÕ();
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityMinecart p_180560_1_, final float p_180560_2_, final IBlockState p_180560_3_) {
        this.HorizonCode_Horizon_È((EntityMinecartTNT)p_180560_1_, p_180560_2_, p_180560_3_);
    }
}
