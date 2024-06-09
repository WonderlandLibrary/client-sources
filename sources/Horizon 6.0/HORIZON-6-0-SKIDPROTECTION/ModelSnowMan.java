package HORIZON-6-0-SKIDPROTECTION;

public class ModelSnowMan extends ModelBase
{
    public ModelRenderer HorizonCode_Horizon_È;
    public ModelRenderer Â;
    public ModelRenderer Ý;
    public ModelRenderer Ø­áŒŠá;
    public ModelRenderer ÂµÈ;
    private static final String á = "CL_00000859";
    
    public ModelSnowMan() {
        final float var1 = 4.0f;
        final float var2 = 0.0f;
        (this.Ý = new ModelRenderer(this, 0, 0).Â(64, 64)).HorizonCode_Horizon_È(-4.0f, -8.0f, -4.0f, 8, 8, 8, var2 - 0.5f);
        this.Ý.HorizonCode_Horizon_È(0.0f, 0.0f + var1, 0.0f);
        (this.Ø­áŒŠá = new ModelRenderer(this, 32, 0).Â(64, 64)).HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 12, 2, 2, var2 - 0.5f);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(0.0f, 0.0f + var1 + 9.0f - 7.0f, 0.0f);
        (this.ÂµÈ = new ModelRenderer(this, 32, 0).Â(64, 64)).HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 12, 2, 2, var2 - 0.5f);
        this.ÂµÈ.HorizonCode_Horizon_È(0.0f, 0.0f + var1 + 9.0f - 7.0f, 0.0f);
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, 0, 16).Â(64, 64)).HorizonCode_Horizon_È(-5.0f, -10.0f, -5.0f, 10, 10, 10, var2 - 0.5f);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0.0f, 0.0f + var1 + 9.0f, 0.0f);
        (this.Â = new ModelRenderer(this, 0, 36).Â(64, 64)).HorizonCode_Horizon_È(-6.0f, -12.0f, -6.0f, 12, 12, 12, var2 - 0.5f);
        this.Â.HorizonCode_Horizon_È(0.0f, 0.0f + var1 + 20.0f, 0.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        super.HorizonCode_Horizon_È(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        this.Ý.à = p_78087_4_ / 57.295776f;
        this.Ý.Ó = p_78087_5_ / 57.295776f;
        this.HorizonCode_Horizon_È.à = p_78087_4_ / 57.295776f * 0.25f;
        final float var8 = MathHelper.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.à);
        final float var9 = MathHelper.Â(this.HorizonCode_Horizon_È.à);
        this.Ø­áŒŠá.Ø = 1.0f;
        this.ÂµÈ.Ø = -1.0f;
        this.Ø­áŒŠá.à = 0.0f + this.HorizonCode_Horizon_È.à;
        this.ÂµÈ.à = 3.1415927f + this.HorizonCode_Horizon_È.à;
        this.Ø­áŒŠá.Ý = var9 * 5.0f;
        this.Ø­áŒŠá.Âµá€ = -var8 * 5.0f;
        this.ÂµÈ.Ý = -var9 * 5.0f;
        this.ÂµÈ.Âµá€ = var8 * 5.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
        this.Â.HorizonCode_Horizon_È(p_78088_7_);
        this.Ý.HorizonCode_Horizon_È(p_78088_7_);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
        this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
    }
}
