package HORIZON-6-0-SKIDPROTECTION;

public class LayerWitherAura implements LayerRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final RenderWither Â;
    private final ModelWither Ý;
    private static final String Ø­áŒŠá = "CL_00002406";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/wither/wither_armor.png");
    }
    
    public LayerWitherAura(final RenderWither p_i46105_1_) {
        this.Ý = new ModelWither(0.5f);
        this.Â = p_i46105_1_;
    }
    
    public void HorizonCode_Horizon_È(final EntityWither p_177214_1_, final float p_177214_2_, final float p_177214_3_, final float p_177214_4_, final float p_177214_5_, final float p_177214_6_, final float p_177214_7_, final float p_177214_8_) {
        if (p_177214_1_.ÇŽÅ()) {
            GlStateManager.HorizonCode_Horizon_È(!p_177214_1_.áŒŠÏ());
            this.Â.HorizonCode_Horizon_È(LayerWitherAura.HorizonCode_Horizon_È);
            GlStateManager.á(5890);
            GlStateManager.ŒÏ();
            final float var9 = p_177214_1_.Œ + p_177214_4_;
            final float var10 = MathHelper.Â(var9 * 0.02f) * 3.0f;
            final float var11 = var9 * 0.01f;
            GlStateManager.Â(var10, var11, 0.0f);
            GlStateManager.á(5888);
            GlStateManager.á();
            final float var12 = 0.5f;
            GlStateManager.Ý(var12, var12, var12, 1.0f);
            GlStateManager.Ó();
            GlStateManager.Â(1, 1);
            this.Ý.HorizonCode_Horizon_È(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_4_);
            this.Ý.HorizonCode_Horizon_È(this.Â.Â());
            this.Ý.HorizonCode_Horizon_È(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
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
        this.HorizonCode_Horizon_È((EntityWither)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
