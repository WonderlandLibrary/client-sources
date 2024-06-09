package HORIZON-6-0-SKIDPROTECTION;

public class LayerCreeperCharge implements LayerRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final RenderCreeper Â;
    private final ModelCreeper Ý;
    private static final String Ø­áŒŠá = "CL_00002423";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/creeper/creeper_armor.png");
    }
    
    public LayerCreeperCharge(final RenderCreeper p_i46121_1_) {
        this.Ý = new ModelCreeper(2.0f);
        this.Â = p_i46121_1_;
    }
    
    public void HorizonCode_Horizon_È(final EntityCreeper p_177169_1_, final float p_177169_2_, final float p_177169_3_, final float p_177169_4_, final float p_177169_5_, final float p_177169_6_, final float p_177169_7_, final float p_177169_8_) {
        if (p_177169_1_.Ø()) {
            GlStateManager.HorizonCode_Horizon_È(!p_177169_1_.áŒŠÏ());
            this.Â.HorizonCode_Horizon_È(LayerCreeperCharge.HorizonCode_Horizon_È);
            GlStateManager.á(5890);
            GlStateManager.ŒÏ();
            final float var9 = p_177169_1_.Œ + p_177169_4_;
            GlStateManager.Â(var9 * 0.01f, var9 * 0.01f, 0.0f);
            GlStateManager.á(5888);
            GlStateManager.á();
            final float var10 = 0.5f;
            GlStateManager.Ý(var10, var10, var10, 1.0f);
            GlStateManager.Ó();
            GlStateManager.Â(1, 1);
            this.Ý.HorizonCode_Horizon_È(this.Â.Â());
            this.Ý.HorizonCode_Horizon_È(p_177169_1_, p_177169_2_, p_177169_3_, p_177169_5_, p_177169_6_, p_177169_7_, p_177169_8_);
            GlStateManager.á(5890);
            GlStateManager.ŒÏ();
            GlStateManager.á(5888);
            GlStateManager.Âµá€();
            GlStateManager.ÂµÈ();
        }
    }
    
    @Override
    public boolean Â() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((EntityCreeper)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
