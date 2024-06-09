package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class ModelGhast extends ModelBase
{
    ModelRenderer HorizonCode_Horizon_È;
    ModelRenderer[] Â;
    private static final String Ý = "CL_00000839";
    
    public ModelGhast() {
        this.Â = new ModelRenderer[9];
        final byte var1 = -16;
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, 0, 0)).HorizonCode_Horizon_È(-8.0f, -8.0f, -8.0f, 16, 16, 16);
        final ModelRenderer horizonCode_Horizon_È = this.HorizonCode_Horizon_È;
        horizonCode_Horizon_È.Ø­áŒŠá += 24 + var1;
        final Random var2 = new Random(1660L);
        for (int var3 = 0; var3 < this.Â.length; ++var3) {
            this.Â[var3] = new ModelRenderer(this, 0, 0);
            final float var4 = ((var3 % 3 - var3 / 3 % 2 * 0.5f + 0.25f) / 2.0f * 2.0f - 1.0f) * 5.0f;
            final float var5 = (var3 / 3 / 2.0f * 2.0f - 1.0f) * 5.0f;
            final int var6 = var2.nextInt(7) + 8;
            this.Â[var3].HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 2, var6, 2);
            this.Â[var3].Ý = var4;
            this.Â[var3].Âµá€ = var5;
            this.Â[var3].Ø­áŒŠá = 31 + var1;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        for (int var8 = 0; var8 < this.Â.length; ++var8) {
            this.Â[var8].Ó = 0.2f * MathHelper.HorizonCode_Horizon_È(p_78087_3_ * 0.3f + var8) + 0.4f;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        GlStateManager.Çªà¢();
        GlStateManager.Â(0.0f, 0.6f, 0.0f);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
        for (final ModelRenderer var11 : this.Â) {
            var11.HorizonCode_Horizon_È(p_78088_7_);
        }
        GlStateManager.Ê();
    }
}
