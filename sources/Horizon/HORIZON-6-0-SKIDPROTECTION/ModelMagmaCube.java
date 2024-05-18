package HORIZON-6-0-SKIDPROTECTION;

public class ModelMagmaCube extends ModelBase
{
    ModelRenderer[] HorizonCode_Horizon_È;
    ModelRenderer Â;
    private static final String Ý = "CL_00000842";
    
    public ModelMagmaCube() {
        this.HorizonCode_Horizon_È = new ModelRenderer[8];
        for (int var1 = 0; var1 < this.HorizonCode_Horizon_È.length; ++var1) {
            byte var2 = 0;
            int var3;
            if ((var3 = var1) == 2) {
                var2 = 24;
                var3 = 10;
            }
            else if (var1 == 3) {
                var2 = 24;
                var3 = 19;
            }
            (this.HorizonCode_Horizon_È[var1] = new ModelRenderer(this, var2, var3)).HorizonCode_Horizon_È(-4.0f, 16 + var1, -4.0f, 8, 1, 8);
        }
        (this.Â = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(-2.0f, 18.0f, -2.0f, 4, 4, 4);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_78086_1_, final float p_78086_2_, final float p_78086_3_, final float p_78086_4_) {
        final EntityMagmaCube var5 = (EntityMagmaCube)p_78086_1_;
        float var6 = var5.Ý + (var5.Â - var5.Ý) * p_78086_4_;
        if (var6 < 0.0f) {
            var6 = 0.0f;
        }
        for (int var7 = 0; var7 < this.HorizonCode_Horizon_È.length; ++var7) {
            this.HorizonCode_Horizon_È[var7].Ø­áŒŠá = -(4 - var7) * var6 * 1.7f;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.Â.HorizonCode_Horizon_È(p_78088_7_);
        for (int var8 = 0; var8 < this.HorizonCode_Horizon_È.length; ++var8) {
            this.HorizonCode_Horizon_È[var8].HorizonCode_Horizon_È(p_78088_7_);
        }
    }
}
