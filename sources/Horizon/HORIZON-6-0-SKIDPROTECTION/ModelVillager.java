package HORIZON-6-0-SKIDPROTECTION;

public class ModelVillager extends ModelBase
{
    public ModelRenderer HorizonCode_Horizon_È;
    public ModelRenderer Â;
    public ModelRenderer Ý;
    public ModelRenderer Ø­áŒŠá;
    public ModelRenderer ÂµÈ;
    public ModelRenderer á;
    private static final String ˆÏ­ = "CL_00000864";
    
    public ModelVillager(final float p_i1163_1_) {
        this(p_i1163_1_, 0.0f, 64, 64);
    }
    
    public ModelVillager(final float p_i1164_1_, final float p_i1164_2_, final int p_i1164_3_, final int p_i1164_4_) {
        (this.HorizonCode_Horizon_È = new ModelRenderer(this).Â(p_i1164_3_, p_i1164_4_)).HorizonCode_Horizon_È(0.0f, 0.0f + p_i1164_2_, 0.0f);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0, 0).HorizonCode_Horizon_È(-4.0f, -10.0f, -4.0f, 8, 10, 8, p_i1164_1_);
        (this.á = new ModelRenderer(this).Â(p_i1164_3_, p_i1164_4_)).HorizonCode_Horizon_È(0.0f, p_i1164_2_ - 2.0f, 0.0f);
        this.á.HorizonCode_Horizon_È(24, 0).HorizonCode_Horizon_È(-1.0f, -1.0f, -6.0f, 2, 4, 2, p_i1164_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.á);
        (this.Â = new ModelRenderer(this).Â(p_i1164_3_, p_i1164_4_)).HorizonCode_Horizon_È(0.0f, 0.0f + p_i1164_2_, 0.0f);
        this.Â.HorizonCode_Horizon_È(16, 20).HorizonCode_Horizon_È(-4.0f, 0.0f, -3.0f, 8, 12, 6, p_i1164_1_);
        this.Â.HorizonCode_Horizon_È(0, 38).HorizonCode_Horizon_È(-4.0f, 0.0f, -3.0f, 8, 18, 6, p_i1164_1_ + 0.5f);
        (this.Ý = new ModelRenderer(this).Â(p_i1164_3_, p_i1164_4_)).HorizonCode_Horizon_È(0.0f, 0.0f + p_i1164_2_ + 2.0f, 0.0f);
        this.Ý.HorizonCode_Horizon_È(44, 22).HorizonCode_Horizon_È(-8.0f, -2.0f, -2.0f, 4, 8, 4, p_i1164_1_);
        this.Ý.HorizonCode_Horizon_È(44, 22).HorizonCode_Horizon_È(4.0f, -2.0f, -2.0f, 4, 8, 4, p_i1164_1_);
        this.Ý.HorizonCode_Horizon_È(40, 38).HorizonCode_Horizon_È(-4.0f, 2.0f, -2.0f, 8, 4, 4, p_i1164_1_);
        (this.Ø­áŒŠá = new ModelRenderer(this, 0, 22).Â(p_i1164_3_, p_i1164_4_)).HorizonCode_Horizon_È(-2.0f, 12.0f + p_i1164_2_, 0.0f);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 12, 4, p_i1164_1_);
        this.ÂµÈ = new ModelRenderer(this, 0, 22).Â(p_i1164_3_, p_i1164_4_);
        this.ÂµÈ.áŒŠÆ = true;
        this.ÂµÈ.HorizonCode_Horizon_È(2.0f, 12.0f + p_i1164_2_, 0.0f);
        this.ÂµÈ.HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 12, 4, p_i1164_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
        this.Â.HorizonCode_Horizon_È(p_78088_7_);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
        this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
        this.Ý.HorizonCode_Horizon_È(p_78088_7_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        this.HorizonCode_Horizon_È.à = p_78087_4_ / 57.295776f;
        this.HorizonCode_Horizon_È.Ó = p_78087_5_ / 57.295776f;
        this.Ý.Ø­áŒŠá = 3.0f;
        this.Ý.Âµá€ = -1.0f;
        this.Ý.Ó = -0.75f;
        this.Ø­áŒŠá.Ó = MathHelper.Â(p_78087_1_ * 0.6662f) * 1.4f * p_78087_2_ * 0.5f;
        this.ÂµÈ.Ó = MathHelper.Â(p_78087_1_ * 0.6662f + 3.1415927f) * 1.4f * p_78087_2_ * 0.5f;
        this.Ø­áŒŠá.à = 0.0f;
        this.ÂµÈ.à = 0.0f;
    }
}
