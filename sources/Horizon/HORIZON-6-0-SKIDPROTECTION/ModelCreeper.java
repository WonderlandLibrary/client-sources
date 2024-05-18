package HORIZON-6-0-SKIDPROTECTION;

public class ModelCreeper extends ModelBase
{
    public ModelRenderer HorizonCode_Horizon_È;
    public ModelRenderer Â;
    public ModelRenderer Ý;
    public ModelRenderer Ø­áŒŠá;
    public ModelRenderer ÂµÈ;
    public ModelRenderer á;
    public ModelRenderer ˆÏ­;
    private static final String £á = "CL_00000837";
    
    public ModelCreeper() {
        this(0.0f);
    }
    
    public ModelCreeper(final float p_i46366_1_) {
        final byte var2 = 6;
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, 0, 0)).HorizonCode_Horizon_È(-4.0f, -8.0f, -4.0f, 8, 8, 8, p_i46366_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0.0f, var2, 0.0f);
        (this.Â = new ModelRenderer(this, 32, 0)).HorizonCode_Horizon_È(-4.0f, -8.0f, -4.0f, 8, 8, 8, p_i46366_1_ + 0.5f);
        this.Â.HorizonCode_Horizon_È(0.0f, var2, 0.0f);
        (this.Ý = new ModelRenderer(this, 16, 16)).HorizonCode_Horizon_È(-4.0f, 0.0f, -2.0f, 8, 12, 4, p_i46366_1_);
        this.Ý.HorizonCode_Horizon_È(0.0f, var2, 0.0f);
        (this.Ø­áŒŠá = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 6, 4, p_i46366_1_);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(-2.0f, 12 + var2, 4.0f);
        (this.ÂµÈ = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 6, 4, p_i46366_1_);
        this.ÂµÈ.HorizonCode_Horizon_È(2.0f, 12 + var2, 4.0f);
        (this.á = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 6, 4, p_i46366_1_);
        this.á.HorizonCode_Horizon_È(-2.0f, 12 + var2, -4.0f);
        (this.ˆÏ­ = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 6, 4, p_i46366_1_);
        this.ˆÏ­.HorizonCode_Horizon_È(2.0f, 12 + var2, -4.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
        this.Ý.HorizonCode_Horizon_È(p_78088_7_);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
        this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
        this.á.HorizonCode_Horizon_È(p_78088_7_);
        this.ˆÏ­.HorizonCode_Horizon_È(p_78088_7_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        this.HorizonCode_Horizon_È.à = p_78087_4_ / 57.295776f;
        this.HorizonCode_Horizon_È.Ó = p_78087_5_ / 57.295776f;
        this.Ø­áŒŠá.Ó = MathHelper.Â(p_78087_1_ * 0.6662f) * 1.4f * p_78087_2_;
        this.ÂµÈ.Ó = MathHelper.Â(p_78087_1_ * 0.6662f + 3.1415927f) * 1.4f * p_78087_2_;
        this.á.Ó = MathHelper.Â(p_78087_1_ * 0.6662f + 3.1415927f) * 1.4f * p_78087_2_;
        this.ˆÏ­.Ó = MathHelper.Â(p_78087_1_ * 0.6662f) * 1.4f * p_78087_2_;
    }
}
