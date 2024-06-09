package HORIZON-6-0-SKIDPROTECTION;

public class LayerDeadmau5Head implements LayerRenderer
{
    private final RenderPlayer HorizonCode_Horizon_È;
    private static final String Â = "CL_00002421";
    
    public LayerDeadmau5Head(final RenderPlayer p_i46119_1_) {
        this.HorizonCode_Horizon_È = p_i46119_1_;
    }
    
    public void HorizonCode_Horizon_È(final AbstractClientPlayer p_177207_1_, final float p_177207_2_, final float p_177207_3_, final float p_177207_4_, final float p_177207_5_, final float p_177207_6_, final float p_177207_7_, final float p_177207_8_) {
        if (p_177207_1_.v_().equals("deadmau5") && p_177207_1_.à() && !p_177207_1_.áŒŠÏ()) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_177207_1_.Ø());
            for (int var9 = 0; var9 < 2; ++var9) {
                final float var10 = p_177207_1_.á€ + (p_177207_1_.É - p_177207_1_.á€) * p_177207_4_ - (p_177207_1_.£ÇªÓ + (p_177207_1_.¥É - p_177207_1_.£ÇªÓ) * p_177207_4_);
                final float var11 = p_177207_1_.Õ + (p_177207_1_.áƒ - p_177207_1_.Õ) * p_177207_4_;
                GlStateManager.Çªà¢();
                GlStateManager.Â(var10, 0.0f, 1.0f, 0.0f);
                GlStateManager.Â(var11, 1.0f, 0.0f, 0.0f);
                GlStateManager.Â(0.375f * (var9 * 2 - 1), 0.0f, 0.0f);
                GlStateManager.Â(0.0f, -0.375f, 0.0f);
                GlStateManager.Â(-var11, 1.0f, 0.0f, 0.0f);
                GlStateManager.Â(-var10, 0.0f, 1.0f, 0.0f);
                final float var12 = 1.3333334f;
                GlStateManager.HorizonCode_Horizon_È(var12, var12, var12);
                this.HorizonCode_Horizon_È.Âµá€().Â(0.0625f);
                GlStateManager.Ê();
            }
        }
    }
    
    @Override
    public boolean Â() {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((AbstractClientPlayer)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
