package HORIZON-6-0-SKIDPROTECTION;

public class LayerCape implements LayerRenderer
{
    private final RenderPlayer HorizonCode_Horizon_È;
    private static final String Â = "CL_00002425";
    
    public LayerCape(final RenderPlayer p_i46123_1_) {
        this.HorizonCode_Horizon_È = p_i46123_1_;
    }
    
    public void HorizonCode_Horizon_È(final AbstractClientPlayer p_177166_1_, final float p_177166_2_, final float p_177166_3_, final float p_177166_4_, final float p_177166_5_, final float p_177166_6_, final float p_177166_7_, final float p_177166_8_) {
        if (p_177166_1_.d_() && !p_177166_1_.áŒŠÏ() && p_177166_1_.HorizonCode_Horizon_È(EnumPlayerModelParts.HorizonCode_Horizon_È) && p_177166_1_.áŒŠÆ() != null) {
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_177166_1_.áŒŠÆ());
            GlStateManager.Çªà¢();
            GlStateManager.Â(0.0f, 0.0f, 0.125f);
            final double var9 = p_177166_1_.Œá + (p_177166_1_.ÇªÂ - p_177166_1_.Œá) * p_177166_4_ - (p_177166_1_.áŒŠà + (p_177166_1_.ŒÏ - p_177166_1_.áŒŠà) * p_177166_4_);
            final double var10 = p_177166_1_.µÂ + (p_177166_1_.ÂµáˆºÂ - p_177166_1_.µÂ) * p_177166_4_ - (p_177166_1_.ŠÄ + (p_177166_1_.Çªà¢ - p_177166_1_.ŠÄ) * p_177166_4_);
            final double var11 = p_177166_1_.Ñ¢ÇŽÏ + (p_177166_1_.¥Âµá€ - p_177166_1_.Ñ¢ÇŽÏ) * p_177166_4_ - (p_177166_1_.Ñ¢á + (p_177166_1_.Ê - p_177166_1_.Ñ¢á) * p_177166_4_);
            final float var12 = p_177166_1_.£ÇªÓ + (p_177166_1_.¥É - p_177166_1_.£ÇªÓ) * p_177166_4_;
            final double var13 = MathHelper.HorizonCode_Horizon_È(var12 * 3.1415927f / 180.0f);
            final double var14 = -MathHelper.Â(var12 * 3.1415927f / 180.0f);
            float var15 = (float)var10 * 10.0f;
            var15 = MathHelper.HorizonCode_Horizon_È(var15, -6.0f, 32.0f);
            float var16 = (float)(var9 * var13 + var11 * var14) * 100.0f;
            final float var17 = (float)(var9 * var14 - var11 * var13) * 100.0f;
            if (var16 < 0.0f) {
                var16 = 0.0f;
            }
            if (var16 > 165.0f) {
                var16 = 165.0f;
            }
            final float var18 = p_177166_1_.Çªà + (p_177166_1_.¥Å - p_177166_1_.Çªà) * p_177166_4_;
            var15 += MathHelper.HorizonCode_Horizon_È((p_177166_1_.Ø­Âµ + (p_177166_1_.Ä - p_177166_1_.Ø­Âµ) * p_177166_4_) * 6.0f) * 32.0f * var18;
            if (p_177166_1_.Çªà¢()) {
                var15 += 25.0f;
                GlStateManager.Â(0.0f, 0.142f, -0.0178f);
            }
            GlStateManager.Â(6.0f + var16 / 2.0f + var15, 1.0f, 0.0f, 0.0f);
            GlStateManager.Â(var17 / 2.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.Â(-var17 / 2.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(180.0f, 0.0f, 1.0f, 0.0f);
            this.HorizonCode_Horizon_È.Âµá€().Ý(0.0625f);
            GlStateManager.Ê();
        }
    }
    
    @Override
    public boolean Â() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((AbstractClientPlayer)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
