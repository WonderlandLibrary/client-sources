package HORIZON-6-0-SKIDPROTECTION;

public class ModelWolf extends ModelBase
{
    public ModelRenderer HorizonCode_Horizon_È;
    public ModelRenderer Â;
    public ModelRenderer Ý;
    public ModelRenderer Ø­áŒŠá;
    public ModelRenderer ÂµÈ;
    public ModelRenderer á;
    ModelRenderer ˆÏ­;
    ModelRenderer £á;
    private static final String Å = "CL_00000868";
    
    public ModelWolf() {
        final float var1 = 0.0f;
        final float var2 = 13.5f;
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, 0, 0)).HorizonCode_Horizon_È(-3.0f, -3.0f, -2.0f, 6, 6, 4, var1);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(-1.0f, var2, -7.0f);
        (this.Â = new ModelRenderer(this, 18, 14)).HorizonCode_Horizon_È(-4.0f, -2.0f, -3.0f, 6, 9, 6, var1);
        this.Â.HorizonCode_Horizon_È(0.0f, 14.0f, 2.0f);
        (this.£á = new ModelRenderer(this, 21, 0)).HorizonCode_Horizon_È(-4.0f, -3.0f, -3.0f, 8, 6, 7, var1);
        this.£á.HorizonCode_Horizon_È(-1.0f, 14.0f, 2.0f);
        (this.Ý = new ModelRenderer(this, 0, 18)).HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.Ý.HorizonCode_Horizon_È(-2.5f, 16.0f, 7.0f);
        (this.Ø­áŒŠá = new ModelRenderer(this, 0, 18)).HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(0.5f, 16.0f, 7.0f);
        (this.ÂµÈ = new ModelRenderer(this, 0, 18)).HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.ÂµÈ.HorizonCode_Horizon_È(-2.5f, 16.0f, -4.0f);
        (this.á = new ModelRenderer(this, 0, 18)).HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.á.HorizonCode_Horizon_È(0.5f, 16.0f, -4.0f);
        (this.ˆÏ­ = new ModelRenderer(this, 9, 18)).HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.ˆÏ­.HorizonCode_Horizon_È(-1.0f, 12.0f, 8.0f);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(16, 14).HorizonCode_Horizon_È(-3.0f, -5.0f, 0.0f, 2, 2, 1, var1);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(16, 14).HorizonCode_Horizon_È(1.0f, -5.0f, 0.0f, 2, 2, 1, var1);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0, 10).HorizonCode_Horizon_È(-1.5f, 0.0f, -5.0f, 3, 3, 4, var1);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        super.HorizonCode_Horizon_È(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        if (this.à) {
            final float var8 = 2.0f;
            GlStateManager.Çªà¢();
            GlStateManager.Â(0.0f, 5.0f * p_78088_7_, 2.0f * p_78088_7_);
            this.HorizonCode_Horizon_È.Â(p_78088_7_);
            GlStateManager.Ê();
            GlStateManager.Çªà¢();
            GlStateManager.HorizonCode_Horizon_È(1.0f / var8, 1.0f / var8, 1.0f / var8);
            GlStateManager.Â(0.0f, 24.0f * p_78088_7_, 0.0f);
            this.Â.HorizonCode_Horizon_È(p_78088_7_);
            this.Ý.HorizonCode_Horizon_È(p_78088_7_);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
            this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
            this.á.HorizonCode_Horizon_È(p_78088_7_);
            this.ˆÏ­.Â(p_78088_7_);
            this.£á.HorizonCode_Horizon_È(p_78088_7_);
            GlStateManager.Ê();
        }
        else {
            this.HorizonCode_Horizon_È.Â(p_78088_7_);
            this.Â.HorizonCode_Horizon_È(p_78088_7_);
            this.Ý.HorizonCode_Horizon_È(p_78088_7_);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
            this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
            this.á.HorizonCode_Horizon_È(p_78088_7_);
            this.ˆÏ­.Â(p_78088_7_);
            this.£á.HorizonCode_Horizon_È(p_78088_7_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_78086_1_, final float p_78086_2_, final float p_78086_3_, final float p_78086_4_) {
        final EntityWolf var5 = (EntityWolf)p_78086_1_;
        if (var5.Ø­È()) {
            this.ˆÏ­.à = 0.0f;
        }
        else {
            this.ˆÏ­.à = MathHelper.Â(p_78086_2_ * 0.6662f) * 1.4f * p_78086_3_;
        }
        if (var5.áˆºÕ()) {
            this.£á.HorizonCode_Horizon_È(-1.0f, 16.0f, -3.0f);
            this.£á.Ó = 1.2566371f;
            this.£á.à = 0.0f;
            this.Â.HorizonCode_Horizon_È(0.0f, 18.0f, 0.0f);
            this.Â.Ó = 0.7853982f;
            this.ˆÏ­.HorizonCode_Horizon_È(-1.0f, 21.0f, 6.0f);
            this.Ý.HorizonCode_Horizon_È(-2.5f, 22.0f, 2.0f);
            this.Ý.Ó = 4.712389f;
            this.Ø­áŒŠá.HorizonCode_Horizon_È(0.5f, 22.0f, 2.0f);
            this.Ø­áŒŠá.Ó = 4.712389f;
            this.ÂµÈ.Ó = 5.811947f;
            this.ÂµÈ.HorizonCode_Horizon_È(-2.49f, 17.0f, -4.0f);
            this.á.Ó = 5.811947f;
            this.á.HorizonCode_Horizon_È(0.51f, 17.0f, -4.0f);
        }
        else {
            this.Â.HorizonCode_Horizon_È(0.0f, 14.0f, 2.0f);
            this.Â.Ó = 1.5707964f;
            this.£á.HorizonCode_Horizon_È(-1.0f, 14.0f, -3.0f);
            this.£á.Ó = this.Â.Ó;
            this.ˆÏ­.HorizonCode_Horizon_È(-1.0f, 12.0f, 8.0f);
            this.Ý.HorizonCode_Horizon_È(-2.5f, 16.0f, 7.0f);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(0.5f, 16.0f, 7.0f);
            this.ÂµÈ.HorizonCode_Horizon_È(-2.5f, 16.0f, -4.0f);
            this.á.HorizonCode_Horizon_È(0.5f, 16.0f, -4.0f);
            this.Ý.Ó = MathHelper.Â(p_78086_2_ * 0.6662f) * 1.4f * p_78086_3_;
            this.Ø­áŒŠá.Ó = MathHelper.Â(p_78086_2_ * 0.6662f + 3.1415927f) * 1.4f * p_78086_3_;
            this.ÂµÈ.Ó = MathHelper.Â(p_78086_2_ * 0.6662f + 3.1415927f) * 1.4f * p_78086_3_;
            this.á.Ó = MathHelper.Â(p_78086_2_ * 0.6662f) * 1.4f * p_78086_3_;
        }
        this.HorizonCode_Horizon_È.Ø = var5.Å(p_78086_4_) + var5.Ø(p_78086_4_, 0.0f);
        this.£á.Ø = var5.Ø(p_78086_4_, -0.08f);
        this.Â.Ø = var5.Ø(p_78086_4_, -0.16f);
        this.ˆÏ­.Ø = var5.Ø(p_78086_4_, -0.2f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        super.HorizonCode_Horizon_È(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        this.HorizonCode_Horizon_È.Ó = p_78087_5_ / 57.295776f;
        this.HorizonCode_Horizon_È.à = p_78087_4_ / 57.295776f;
        this.ˆÏ­.Ó = p_78087_3_;
    }
}
