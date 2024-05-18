package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class LayerEnderDragonDeath implements LayerRenderer
{
    private static final String HorizonCode_Horizon_È = "CL_00002420";
    
    public void HorizonCode_Horizon_È(final EntityDragon p_177213_1_, final float p_177213_2_, final float p_177213_3_, final float p_177213_4_, final float p_177213_5_, final float p_177213_6_, final float p_177213_7_, final float p_177213_8_) {
        if (p_177213_1_.ÂµáˆºÂ > 0) {
            final Tessellator var9 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var10 = var9.Ý();
            RenderHelper.HorizonCode_Horizon_È();
            final float var11 = (p_177213_1_.ÂµáˆºÂ + p_177213_4_) / 200.0f;
            float var12 = 0.0f;
            if (var11 > 0.8f) {
                var12 = (var11 - 0.8f) / 0.2f;
            }
            final Random var13 = new Random(432L);
            GlStateManager.Æ();
            GlStateManager.áˆºÑ¢Õ(7425);
            GlStateManager.á();
            GlStateManager.Â(770, 1);
            GlStateManager.Ý();
            GlStateManager.Å();
            GlStateManager.HorizonCode_Horizon_È(false);
            GlStateManager.Çªà¢();
            GlStateManager.Â(0.0f, -1.0f, -2.0f);
            for (int var14 = 0; var14 < (var11 + var11 * var11) / 2.0f * 60.0f; ++var14) {
                GlStateManager.Â(var13.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.Â(var13.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.Â(var13.nextFloat() * 360.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.Â(var13.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.Â(var13.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.Â(var13.nextFloat() * 360.0f + var11 * 90.0f, 0.0f, 0.0f, 1.0f);
                var10.HorizonCode_Horizon_È(6);
                final float var15 = var13.nextFloat() * 20.0f + 5.0f + var12 * 10.0f;
                final float var16 = var13.nextFloat() * 2.0f + 1.0f + var12 * 2.0f;
                var10.HorizonCode_Horizon_È(16777215, (int)(255.0f * (1.0f - var12)));
                var10.Â(0.0, 0.0, 0.0);
                var10.HorizonCode_Horizon_È(16711935, 0);
                var10.Â(-0.866 * var16, var15, -0.5f * var16);
                var10.Â(0.866 * var16, var15, -0.5f * var16);
                var10.Â(0.0, var15, 1.0f * var16);
                var10.Â(-0.866 * var16, var15, -0.5f * var16);
                var9.Â();
            }
            GlStateManager.Ê();
            GlStateManager.HorizonCode_Horizon_È(true);
            GlStateManager.£à();
            GlStateManager.ÂµÈ();
            GlStateManager.áˆºÑ¢Õ(7424);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.µÕ();
            GlStateManager.Ø­áŒŠá();
            RenderHelper.Â();
        }
    }
    
    @Override
    public boolean Â() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((EntityDragon)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
