package HORIZON-6-0-SKIDPROTECTION;

public class LayerMooshroomMushroom implements LayerRenderer
{
    private final RenderMooshroom HorizonCode_Horizon_È;
    private static final String Â = "CL_00002415";
    
    public LayerMooshroomMushroom(final RenderMooshroom p_i46114_1_) {
        this.HorizonCode_Horizon_È = p_i46114_1_;
    }
    
    public void HorizonCode_Horizon_È(final EntityMooshroom p_177204_1_, final float p_177204_2_, final float p_177204_3_, final float p_177204_4_, final float p_177204_5_, final float p_177204_6_, final float p_177204_7_, final float p_177204_8_) {
        if (!p_177204_1_.h_() && !p_177204_1_.áŒŠÏ()) {
            final BlockRendererDispatcher var9 = Minecraft.áŒŠà().Ô();
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(TextureMap.à);
            GlStateManager.Å();
            GlStateManager.Çªà¢();
            GlStateManager.HorizonCode_Horizon_È(1.0f, -1.0f, 1.0f);
            GlStateManager.Â(0.2f, 0.35f, 0.5f);
            GlStateManager.Â(42.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.Çªà¢();
            GlStateManager.Â(-0.5f, -0.5f, 0.5f);
            var9.HorizonCode_Horizon_È(Blocks.áŠ.¥à(), 1.0f);
            GlStateManager.Ê();
            GlStateManager.Çªà¢();
            GlStateManager.Â(0.1f, 0.0f, -0.6f);
            GlStateManager.Â(42.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(-0.5f, -0.5f, 0.5f);
            var9.HorizonCode_Horizon_È(Blocks.áŠ.¥à(), 1.0f);
            GlStateManager.Ê();
            GlStateManager.Ê();
            GlStateManager.Çªà¢();
            ((ModelQuadruped)this.HorizonCode_Horizon_È.Â()).HorizonCode_Horizon_È.Ý(0.0625f);
            GlStateManager.HorizonCode_Horizon_È(1.0f, -1.0f, 1.0f);
            GlStateManager.Â(0.0f, 0.7f, -0.2f);
            GlStateManager.Â(12.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(-0.5f, -0.5f, 0.5f);
            var9.HorizonCode_Horizon_È(Blocks.áŠ.¥à(), 1.0f);
            GlStateManager.Ê();
            GlStateManager.£à();
        }
    }
    
    @Override
    public boolean Â() {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((EntityMooshroom)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
