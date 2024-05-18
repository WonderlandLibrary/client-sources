package HORIZON-6-0-SKIDPROTECTION;

public class LayerSnowmanHead implements LayerRenderer
{
    private final RenderSnowMan HorizonCode_Horizon_È;
    private static final String Â = "CL_00002411";
    
    public LayerSnowmanHead(final RenderSnowMan p_i46110_1_) {
        this.HorizonCode_Horizon_È = p_i46110_1_;
    }
    
    public void HorizonCode_Horizon_È(final EntitySnowman p_177151_1_, final float p_177151_2_, final float p_177151_3_, final float p_177151_4_, final float p_177151_5_, final float p_177151_6_, final float p_177151_7_, final float p_177151_8_) {
        if (!p_177151_1_.áŒŠÏ()) {
            GlStateManager.Çªà¢();
            this.HorizonCode_Horizon_È.Âµá€().Ý.Ý(0.0625f);
            final float var9 = 0.625f;
            GlStateManager.Â(0.0f, -0.34375f, 0.0f);
            GlStateManager.Â(180.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.HorizonCode_Horizon_È(var9, -var9, -var9);
            Minecraft.áŒŠà().ˆáƒ().HorizonCode_Horizon_È(p_177151_1_, new ItemStack(Blocks.Ø­Æ, 1), ItemCameraTransforms.Â.Ø­áŒŠá);
            GlStateManager.Ê();
        }
    }
    
    @Override
    public boolean Â() {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((EntitySnowman)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
