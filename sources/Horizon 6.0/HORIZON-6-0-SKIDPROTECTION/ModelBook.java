package HORIZON-6-0-SKIDPROTECTION;

public class ModelBook extends ModelBase
{
    public ModelRenderer HorizonCode_Horizon_È;
    public ModelRenderer Â;
    public ModelRenderer Ý;
    public ModelRenderer Ø­áŒŠá;
    public ModelRenderer ÂµÈ;
    public ModelRenderer á;
    public ModelRenderer ˆÏ­;
    private static final String £á = "CL_00000833";
    
    public ModelBook() {
        this.HorizonCode_Horizon_È = new ModelRenderer(this).HorizonCode_Horizon_È(0, 0).HorizonCode_Horizon_È(-6.0f, -5.0f, 0.0f, 6, 10, 0);
        this.Â = new ModelRenderer(this).HorizonCode_Horizon_È(16, 0).HorizonCode_Horizon_È(0.0f, -5.0f, 0.0f, 6, 10, 0);
        this.Ý = new ModelRenderer(this).HorizonCode_Horizon_È(0, 10).HorizonCode_Horizon_È(0.0f, -4.0f, -0.99f, 5, 8, 1);
        this.Ø­áŒŠá = new ModelRenderer(this).HorizonCode_Horizon_È(12, 10).HorizonCode_Horizon_È(0.0f, -4.0f, -0.01f, 5, 8, 1);
        this.ÂµÈ = new ModelRenderer(this).HorizonCode_Horizon_È(24, 10).HorizonCode_Horizon_È(0.0f, -4.0f, 0.0f, 5, 8, 0);
        this.á = new ModelRenderer(this).HorizonCode_Horizon_È(24, 10).HorizonCode_Horizon_È(0.0f, -4.0f, 0.0f, 5, 8, 0);
        this.ˆÏ­ = new ModelRenderer(this).HorizonCode_Horizon_È(12, 0).HorizonCode_Horizon_È(-1.0f, -5.0f, 0.0f, 2, 10, 0);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0.0f, 0.0f, -1.0f);
        this.Â.HorizonCode_Horizon_È(0.0f, 0.0f, 1.0f);
        this.ˆÏ­.à = 1.5707964f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
        this.Â.HorizonCode_Horizon_È(p_78088_7_);
        this.ˆÏ­.HorizonCode_Horizon_È(p_78088_7_);
        this.Ý.HorizonCode_Horizon_È(p_78088_7_);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
        this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
        this.á.HorizonCode_Horizon_È(p_78088_7_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        final float var8 = (MathHelper.HorizonCode_Horizon_È(p_78087_1_ * 0.02f) * 0.1f + 1.25f) * p_78087_4_;
        this.HorizonCode_Horizon_È.à = 3.1415927f + var8;
        this.Â.à = -var8;
        this.Ý.à = var8;
        this.Ø­áŒŠá.à = -var8;
        this.ÂµÈ.à = var8 - var8 * 2.0f * p_78087_2_;
        this.á.à = var8 - var8 * 2.0f * p_78087_3_;
        this.Ý.Ý = MathHelper.HorizonCode_Horizon_È(var8);
        this.Ø­áŒŠá.Ý = MathHelper.HorizonCode_Horizon_È(var8);
        this.ÂµÈ.Ý = MathHelper.HorizonCode_Horizon_È(var8);
        this.á.Ý = MathHelper.HorizonCode_Horizon_È(var8);
    }
}
