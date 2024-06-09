package HORIZON-6-0-SKIDPROTECTION;

public class LayerSlimeGel implements LayerRenderer
{
    private final RenderSlime HorizonCode_Horizon_È;
    private final ModelBase Â;
    private static final String Ý = "CL_00002412";
    
    public LayerSlimeGel(final RenderSlime p_i46111_1_) {
        this.Â = new ModelSlime(0);
        this.HorizonCode_Horizon_È = p_i46111_1_;
    }
    
    public void HorizonCode_Horizon_È(final EntitySlime p_177159_1_, final float p_177159_2_, final float p_177159_3_, final float p_177159_4_, final float p_177159_5_, final float p_177159_6_, final float p_177159_7_, final float p_177159_8_) {
        if (!p_177159_1_.áŒŠÏ()) {
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.Ï­Ðƒà();
            GlStateManager.á();
            GlStateManager.Â(770, 771);
            this.Â.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Â());
            this.Â.HorizonCode_Horizon_È(p_177159_1_, p_177159_2_, p_177159_3_, p_177159_5_, p_177159_6_, p_177159_7_, p_177159_8_);
            GlStateManager.ÂµÈ();
            GlStateManager.áŒŠà();
        }
    }
    
    @Override
    public boolean Â() {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((EntitySlime)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
