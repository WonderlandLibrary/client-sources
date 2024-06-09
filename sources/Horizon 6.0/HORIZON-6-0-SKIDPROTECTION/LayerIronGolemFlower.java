package HORIZON-6-0-SKIDPROTECTION;

public class LayerIronGolemFlower implements LayerRenderer
{
    private final RenderIronGolem HorizonCode_Horizon_È;
    private static final String Â = "CL_00002408";
    
    public LayerIronGolemFlower(final RenderIronGolem p_i46107_1_) {
        this.HorizonCode_Horizon_È = p_i46107_1_;
    }
    
    public void HorizonCode_Horizon_È(final EntityIronGolem p_177153_1_, final float p_177153_2_, final float p_177153_3_, final float p_177153_4_, final float p_177153_5_, final float p_177153_6_, final float p_177153_7_, final float p_177153_8_) {
        if (p_177153_1_.ÇŽÅ() != 0) {
            final BlockRendererDispatcher var9 = Minecraft.áŒŠà().Ô();
            GlStateManager.ŠÄ();
            GlStateManager.Çªà¢();
            GlStateManager.Â(5.0f + 180.0f * ((ModelIronGolem)this.HorizonCode_Horizon_È.Â()).Ý.Ó / 3.1415927f, 1.0f, 0.0f, 0.0f);
            GlStateManager.Â(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.Â(-0.9375f, -0.625f, -0.9375f);
            final float var10 = 0.5f;
            GlStateManager.HorizonCode_Horizon_È(var10, -var10, var10);
            final int var11 = p_177153_1_.HorizonCode_Horizon_È(p_177153_4_);
            final int var12 = var11 % 65536;
            final int var13 = var11 / 65536;
            OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var12 / 1.0f, var13 / 1.0f);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(TextureMap.à);
            var9.HorizonCode_Horizon_È(Blocks.Ç.¥à(), 1.0f);
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
        this.HorizonCode_Horizon_È((EntityIronGolem)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
