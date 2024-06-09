package HORIZON-6-0-SKIDPROTECTION;

public class ModelBat extends ModelBase
{
    private ModelRenderer HorizonCode_Horizon_È;
    private ModelRenderer Â;
    private ModelRenderer Ý;
    private ModelRenderer Ø­áŒŠá;
    private ModelRenderer ÂµÈ;
    private ModelRenderer á;
    private static final String ˆÏ­ = "CL_00000830";
    
    public ModelBat() {
        this.áŒŠÆ = 64;
        this.áˆºÑ¢Õ = 64;
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, 0, 0)).HorizonCode_Horizon_È(-3.0f, -3.0f, -3.0f, 6, 6, 6);
        final ModelRenderer var1 = new ModelRenderer(this, 24, 0);
        var1.HorizonCode_Horizon_È(-4.0f, -6.0f, -2.0f, 3, 4, 1);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var1);
        final ModelRenderer var2 = new ModelRenderer(this, 24, 0);
        var2.áŒŠÆ = true;
        var2.HorizonCode_Horizon_È(1.0f, -6.0f, -2.0f, 3, 4, 1);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var2);
        (this.Â = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(-3.0f, 4.0f, -3.0f, 6, 12, 6);
        this.Â.HorizonCode_Horizon_È(0, 34).HorizonCode_Horizon_È(-5.0f, 16.0f, 0.0f, 10, 6, 1);
        (this.Ý = new ModelRenderer(this, 42, 0)).HorizonCode_Horizon_È(-12.0f, 1.0f, 1.5f, 10, 16, 1);
        (this.ÂµÈ = new ModelRenderer(this, 24, 16)).HorizonCode_Horizon_È(-12.0f, 1.0f, 1.5f);
        this.ÂµÈ.HorizonCode_Horizon_È(-8.0f, 1.0f, 0.0f, 8, 12, 1);
        this.Ø­áŒŠá = new ModelRenderer(this, 42, 0);
        this.Ø­áŒŠá.áŒŠÆ = true;
        this.Ø­áŒŠá.HorizonCode_Horizon_È(2.0f, 1.0f, 1.5f, 10, 16, 1);
        this.á = new ModelRenderer(this, 24, 16);
        this.á.áŒŠÆ = true;
        this.á.HorizonCode_Horizon_È(12.0f, 1.0f, 1.5f);
        this.á.HorizonCode_Horizon_È(0.0f, 1.0f, 0.0f, 8, 12, 1);
        this.Â.HorizonCode_Horizon_È(this.Ý);
        this.Â.HorizonCode_Horizon_È(this.Ø­áŒŠá);
        this.Ý.HorizonCode_Horizon_È(this.ÂµÈ);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(this.á);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
        this.Â.HorizonCode_Horizon_È(p_78088_7_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        if (((EntityBat)p_78087_7_).Ø()) {
            final float var8 = 57.295776f;
            this.HorizonCode_Horizon_È.Ó = p_78087_5_ / 57.295776f;
            this.HorizonCode_Horizon_È.à = 3.1415927f - p_78087_4_ / 57.295776f;
            this.HorizonCode_Horizon_È.Ø = 3.1415927f;
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0.0f, -2.0f, 0.0f);
            this.Ý.HorizonCode_Horizon_È(-3.0f, 0.0f, 3.0f);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(3.0f, 0.0f, 3.0f);
            this.Â.Ó = 3.1415927f;
            this.Ý.Ó = -0.15707964f;
            this.Ý.à = -1.2566371f;
            this.ÂµÈ.à = -1.7278761f;
            this.Ø­áŒŠá.Ó = this.Ý.Ó;
            this.Ø­áŒŠá.à = -this.Ý.à;
            this.á.à = -this.ÂµÈ.à;
        }
        else {
            final float var8 = 57.295776f;
            this.HorizonCode_Horizon_È.Ó = p_78087_5_ / 57.295776f;
            this.HorizonCode_Horizon_È.à = p_78087_4_ / 57.295776f;
            this.HorizonCode_Horizon_È.Ø = 0.0f;
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f);
            this.Ý.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f);
            this.Â.Ó = 0.7853982f + MathHelper.Â(p_78087_3_ * 0.1f) * 0.15f;
            this.Â.à = 0.0f;
            this.Ý.à = MathHelper.Â(p_78087_3_ * 1.3f) * 3.1415927f * 0.25f;
            this.Ø­áŒŠá.à = -this.Ý.à;
            this.ÂµÈ.à = this.Ý.à * 0.5f;
            this.á.à = -this.Ý.à * 0.5f;
        }
    }
}
