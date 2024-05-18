package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class LayerArrow implements LayerRenderer
{
    private final RendererLivingEntity HorizonCode_Horizon_È;
    private static final String Â = "CL_00002426";
    
    public LayerArrow(final RendererLivingEntity p_i46124_1_) {
        this.HorizonCode_Horizon_È = p_i46124_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        final int var9 = p_177141_1_.µ();
        if (var9 > 0) {
            final EntityArrow var10 = new EntityArrow(p_177141_1_.Ï­Ðƒà, p_177141_1_.ŒÏ, p_177141_1_.Çªà¢, p_177141_1_.Ê);
            final Random var11 = new Random(p_177141_1_.ˆá());
            RenderHelper.HorizonCode_Horizon_È();
            for (int var12 = 0; var12 < var9; ++var12) {
                GlStateManager.Çªà¢();
                final ModelRenderer var13 = this.HorizonCode_Horizon_È.Â().HorizonCode_Horizon_È(var11);
                final ModelBox var14 = var13.á.get(var11.nextInt(var13.á.size()));
                var13.Ý(0.0625f);
                float var15 = var11.nextFloat();
                float var16 = var11.nextFloat();
                float var17 = var11.nextFloat();
                final float var18 = (var14.HorizonCode_Horizon_È + (var14.Ø­áŒŠá - var14.HorizonCode_Horizon_È) * var15) / 16.0f;
                final float var19 = (var14.Â + (var14.Âµá€ - var14.Â) * var16) / 16.0f;
                final float var20 = (var14.Ý + (var14.Ó - var14.Ý) * var17) / 16.0f;
                GlStateManager.Â(var18, var19, var20);
                var15 = var15 * 2.0f - 1.0f;
                var16 = var16 * 2.0f - 1.0f;
                var17 = var17 * 2.0f - 1.0f;
                var15 *= -1.0f;
                var16 *= -1.0f;
                var17 *= -1.0f;
                final float var21 = MathHelper.Ý(var15 * var15 + var17 * var17);
                final EntityArrow entityArrow = var10;
                final EntityArrow entityArrow2 = var10;
                final float n = (float)(Math.atan2(var15, var17) * 180.0 / 3.141592653589793);
                entityArrow2.É = n;
                entityArrow.á€ = n;
                final EntityArrow entityArrow3 = var10;
                final EntityArrow entityArrow4 = var10;
                final float n2 = (float)(Math.atan2(var16, var21) * 180.0 / 3.141592653589793);
                entityArrow4.áƒ = n2;
                entityArrow3.Õ = n2;
                final double var22 = 0.0;
                final double var23 = 0.0;
                final double var24 = 0.0;
                this.HorizonCode_Horizon_È.Ø­áŒŠá().HorizonCode_Horizon_È(var10, var22, var23, var24, 0.0f, p_177141_4_);
                GlStateManager.Ê();
            }
            RenderHelper.Â();
        }
    }
    
    @Override
    public boolean Â() {
        return false;
    }
}
