package HORIZON-6-0-SKIDPROTECTION;

public class ModelBlaze extends ModelBase
{
    private ModelRenderer[] HorizonCode_Horizon_È;
    private ModelRenderer Â;
    private static final String Ý = "CL_00000831";
    
    public ModelBlaze() {
        this.HorizonCode_Horizon_È = new ModelRenderer[12];
        for (int var1 = 0; var1 < this.HorizonCode_Horizon_È.length; ++var1) {
            (this.HorizonCode_Horizon_È[var1] = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 2, 8, 2);
        }
        (this.Â = new ModelRenderer(this, 0, 0)).HorizonCode_Horizon_È(-4.0f, -4.0f, -4.0f, 8, 8, 8);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.Â.HorizonCode_Horizon_È(p_78088_7_);
        for (int var8 = 0; var8 < this.HorizonCode_Horizon_È.length; ++var8) {
            this.HorizonCode_Horizon_È[var8].HorizonCode_Horizon_È(p_78088_7_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        float var8 = p_78087_3_ * 3.1415927f * -0.1f;
        for (int var9 = 0; var9 < 4; ++var9) {
            this.HorizonCode_Horizon_È[var9].Ø­áŒŠá = -2.0f + MathHelper.Â((var9 * 2 + p_78087_3_) * 0.25f);
            this.HorizonCode_Horizon_È[var9].Ý = MathHelper.Â(var8) * 9.0f;
            this.HorizonCode_Horizon_È[var9].Âµá€ = MathHelper.HorizonCode_Horizon_È(var8) * 9.0f;
            ++var8;
        }
        var8 = 0.7853982f + p_78087_3_ * 3.1415927f * 0.03f;
        for (int var9 = 4; var9 < 8; ++var9) {
            this.HorizonCode_Horizon_È[var9].Ø­áŒŠá = 2.0f + MathHelper.Â((var9 * 2 + p_78087_3_) * 0.25f);
            this.HorizonCode_Horizon_È[var9].Ý = MathHelper.Â(var8) * 7.0f;
            this.HorizonCode_Horizon_È[var9].Âµá€ = MathHelper.HorizonCode_Horizon_È(var8) * 7.0f;
            ++var8;
        }
        var8 = 0.47123894f + p_78087_3_ * 3.1415927f * -0.05f;
        for (int var9 = 8; var9 < 12; ++var9) {
            this.HorizonCode_Horizon_È[var9].Ø­áŒŠá = 11.0f + MathHelper.Â((var9 * 1.5f + p_78087_3_) * 0.5f);
            this.HorizonCode_Horizon_È[var9].Ý = MathHelper.Â(var8) * 5.0f;
            this.HorizonCode_Horizon_È[var9].Âµá€ = MathHelper.HorizonCode_Horizon_È(var8) * 5.0f;
            ++var8;
        }
        this.Â.à = p_78087_4_ / 57.295776f;
        this.Â.Ó = p_78087_5_ / 57.295776f;
    }
}
