package HORIZON-6-0-SKIDPROTECTION;

public class ModelChicken extends ModelBase
{
    public ModelRenderer HorizonCode_Horizon_È;
    public ModelRenderer Â;
    public ModelRenderer Ý;
    public ModelRenderer Ø­áŒŠá;
    public ModelRenderer ÂµÈ;
    public ModelRenderer á;
    public ModelRenderer ˆÏ­;
    public ModelRenderer £á;
    private static final String Å = "CL_00000835";
    
    public ModelChicken() {
        final byte var1 = 16;
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, 0, 0)).HorizonCode_Horizon_È(-2.0f, -6.0f, -2.0f, 4, 6, 3, 0.0f);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0.0f, -1 + var1, -4.0f);
        (this.ˆÏ­ = new ModelRenderer(this, 14, 0)).HorizonCode_Horizon_È(-2.0f, -4.0f, -4.0f, 4, 2, 2, 0.0f);
        this.ˆÏ­.HorizonCode_Horizon_È(0.0f, -1 + var1, -4.0f);
        (this.£á = new ModelRenderer(this, 14, 4)).HorizonCode_Horizon_È(-1.0f, -2.0f, -3.0f, 2, 2, 2, 0.0f);
        this.£á.HorizonCode_Horizon_È(0.0f, -1 + var1, -4.0f);
        (this.Â = new ModelRenderer(this, 0, 9)).HorizonCode_Horizon_È(-3.0f, -4.0f, -3.0f, 6, 8, 6, 0.0f);
        this.Â.HorizonCode_Horizon_È(0.0f, var1, 0.0f);
        (this.Ý = new ModelRenderer(this, 26, 0)).HorizonCode_Horizon_È(-1.0f, 0.0f, -3.0f, 3, 5, 3);
        this.Ý.HorizonCode_Horizon_È(-2.0f, 3 + var1, 1.0f);
        (this.Ø­áŒŠá = new ModelRenderer(this, 26, 0)).HorizonCode_Horizon_È(-1.0f, 0.0f, -3.0f, 3, 5, 3);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(1.0f, 3 + var1, 1.0f);
        (this.ÂµÈ = new ModelRenderer(this, 24, 13)).HorizonCode_Horizon_È(0.0f, 0.0f, -3.0f, 1, 4, 6);
        this.ÂµÈ.HorizonCode_Horizon_È(-4.0f, -3 + var1, 0.0f);
        (this.á = new ModelRenderer(this, 24, 13)).HorizonCode_Horizon_È(-1.0f, 0.0f, -3.0f, 1, 4, 6);
        this.á.HorizonCode_Horizon_È(4.0f, -3 + var1, 0.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        if (this.à) {
            final float var8 = 2.0f;
            GlStateManager.Çªà¢();
            GlStateManager.Â(0.0f, 5.0f * p_78088_7_, 2.0f * p_78088_7_);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
            this.ˆÏ­.HorizonCode_Horizon_È(p_78088_7_);
            this.£á.HorizonCode_Horizon_È(p_78088_7_);
            GlStateManager.Ê();
            GlStateManager.Çªà¢();
            GlStateManager.HorizonCode_Horizon_È(1.0f / var8, 1.0f / var8, 1.0f / var8);
            GlStateManager.Â(0.0f, 24.0f * p_78088_7_, 0.0f);
            this.Â.HorizonCode_Horizon_È(p_78088_7_);
            this.Ý.HorizonCode_Horizon_È(p_78088_7_);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
            this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
            this.á.HorizonCode_Horizon_È(p_78088_7_);
            GlStateManager.Ê();
        }
        else {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
            this.ˆÏ­.HorizonCode_Horizon_È(p_78088_7_);
            this.£á.HorizonCode_Horizon_È(p_78088_7_);
            this.Â.HorizonCode_Horizon_È(p_78088_7_);
            this.Ý.HorizonCode_Horizon_È(p_78088_7_);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
            this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
            this.á.HorizonCode_Horizon_È(p_78088_7_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        this.HorizonCode_Horizon_È.Ó = p_78087_5_ / 57.295776f;
        this.HorizonCode_Horizon_È.à = p_78087_4_ / 57.295776f;
        this.ˆÏ­.Ó = this.HorizonCode_Horizon_È.Ó;
        this.ˆÏ­.à = this.HorizonCode_Horizon_È.à;
        this.£á.Ó = this.HorizonCode_Horizon_È.Ó;
        this.£á.à = this.HorizonCode_Horizon_È.à;
        this.Â.Ó = 1.5707964f;
        this.Ý.Ó = MathHelper.Â(p_78087_1_ * 0.6662f) * 1.4f * p_78087_2_;
        this.Ø­áŒŠá.Ó = MathHelper.Â(p_78087_1_ * 0.6662f + 3.1415927f) * 1.4f * p_78087_2_;
        this.ÂµÈ.Ø = p_78087_3_;
        this.á.Ø = -p_78087_3_;
    }
}
