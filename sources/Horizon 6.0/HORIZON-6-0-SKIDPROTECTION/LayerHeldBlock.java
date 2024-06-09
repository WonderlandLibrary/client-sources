package HORIZON-6-0-SKIDPROTECTION;

public class LayerHeldBlock implements LayerRenderer
{
    private final RenderEnderman HorizonCode_Horizon_È;
    private static final String Â = "CL_00002424";
    
    public LayerHeldBlock(final RenderEnderman p_i46122_1_) {
        this.HorizonCode_Horizon_È = p_i46122_1_;
    }
    
    public void HorizonCode_Horizon_È(final EntityEnderman p_177173_1_, final float p_177173_2_, final float p_177173_3_, final float p_177173_4_, final float p_177173_5_, final float p_177173_6_, final float p_177173_7_, final float p_177173_8_) {
        final IBlockState var9 = p_177173_1_.ÇŽÅ();
        if (var9.Ý().Ó() != Material.HorizonCode_Horizon_È) {
            final BlockRendererDispatcher var10 = Minecraft.áŒŠà().Ô();
            GlStateManager.ŠÄ();
            GlStateManager.Çªà¢();
            GlStateManager.Â(0.0f, 0.6875f, -0.75f);
            GlStateManager.Â(20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.Â(45.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(0.25f, 0.1875f, 0.25f);
            final float var11 = 0.5f;
            GlStateManager.HorizonCode_Horizon_È(-var11, -var11, var11);
            final int var12 = p_177173_1_.HorizonCode_Horizon_È(p_177173_4_);
            final int var13 = var12 % 65536;
            final int var14 = var12 / 65536;
            OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var13 / 1.0f, var14 / 1.0f);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(TextureMap.à);
            var10.HorizonCode_Horizon_È(var9, 1.0f);
            GlStateManager.Ê();
            GlStateManager.Ñ¢á();
        }
    }
    
    @Override
    public boolean Â() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((EntityEnderman)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
