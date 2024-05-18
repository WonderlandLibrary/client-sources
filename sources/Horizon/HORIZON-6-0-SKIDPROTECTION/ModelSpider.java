package HORIZON-6-0-SKIDPROTECTION;

public class ModelSpider extends ModelBase
{
    public ModelRenderer HorizonCode_Horizon_È;
    public ModelRenderer Â;
    public ModelRenderer Ý;
    public ModelRenderer Ø­áŒŠá;
    public ModelRenderer ÂµÈ;
    public ModelRenderer á;
    public ModelRenderer ˆÏ­;
    public ModelRenderer £á;
    public ModelRenderer Å;
    public ModelRenderer £à;
    public ModelRenderer µà;
    private static final String ˆà = "CL_00000860";
    
    public ModelSpider() {
        final float var1 = 0.0f;
        final byte var2 = 15;
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, 32, 4)).HorizonCode_Horizon_È(-4.0f, -4.0f, -8.0f, 8, 8, 8, var1);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0.0f, var2, -3.0f);
        (this.Â = new ModelRenderer(this, 0, 0)).HorizonCode_Horizon_È(-3.0f, -3.0f, -3.0f, 6, 6, 6, var1);
        this.Â.HorizonCode_Horizon_È(0.0f, var2, 0.0f);
        (this.Ý = new ModelRenderer(this, 0, 12)).HorizonCode_Horizon_È(-5.0f, -4.0f, -6.0f, 10, 8, 12, var1);
        this.Ý.HorizonCode_Horizon_È(0.0f, var2, 9.0f);
        (this.Ø­áŒŠá = new ModelRenderer(this, 18, 0)).HorizonCode_Horizon_È(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(-4.0f, var2, 2.0f);
        (this.ÂµÈ = new ModelRenderer(this, 18, 0)).HorizonCode_Horizon_È(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.ÂµÈ.HorizonCode_Horizon_È(4.0f, var2, 2.0f);
        (this.á = new ModelRenderer(this, 18, 0)).HorizonCode_Horizon_È(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.á.HorizonCode_Horizon_È(-4.0f, var2, 1.0f);
        (this.ˆÏ­ = new ModelRenderer(this, 18, 0)).HorizonCode_Horizon_È(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.ˆÏ­.HorizonCode_Horizon_È(4.0f, var2, 1.0f);
        (this.£á = new ModelRenderer(this, 18, 0)).HorizonCode_Horizon_È(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.£á.HorizonCode_Horizon_È(-4.0f, var2, 0.0f);
        (this.Å = new ModelRenderer(this, 18, 0)).HorizonCode_Horizon_È(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.Å.HorizonCode_Horizon_È(4.0f, var2, 0.0f);
        (this.£à = new ModelRenderer(this, 18, 0)).HorizonCode_Horizon_È(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.£à.HorizonCode_Horizon_È(-4.0f, var2, -1.0f);
        (this.µà = new ModelRenderer(this, 18, 0)).HorizonCode_Horizon_È(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.µà.HorizonCode_Horizon_È(4.0f, var2, -1.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
        this.Â.HorizonCode_Horizon_È(p_78088_7_);
        this.Ý.HorizonCode_Horizon_È(p_78088_7_);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
        this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
        this.á.HorizonCode_Horizon_È(p_78088_7_);
        this.ˆÏ­.HorizonCode_Horizon_È(p_78088_7_);
        this.£á.HorizonCode_Horizon_È(p_78088_7_);
        this.Å.HorizonCode_Horizon_È(p_78088_7_);
        this.£à.HorizonCode_Horizon_È(p_78088_7_);
        this.µà.HorizonCode_Horizon_È(p_78088_7_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        this.HorizonCode_Horizon_È.à = p_78087_4_ / 57.295776f;
        this.HorizonCode_Horizon_È.Ó = p_78087_5_ / 57.295776f;
        final float var8 = 0.7853982f;
        this.Ø­áŒŠá.Ø = -var8;
        this.ÂµÈ.Ø = var8;
        this.á.Ø = -var8 * 0.74f;
        this.ˆÏ­.Ø = var8 * 0.74f;
        this.£á.Ø = -var8 * 0.74f;
        this.Å.Ø = var8 * 0.74f;
        this.£à.Ø = -var8;
        this.µà.Ø = var8;
        final float var9 = -0.0f;
        final float var10 = 0.3926991f;
        this.Ø­áŒŠá.à = var10 * 2.0f + var9;
        this.ÂµÈ.à = -var10 * 2.0f - var9;
        this.á.à = var10 * 1.0f + var9;
        this.ˆÏ­.à = -var10 * 1.0f - var9;
        this.£á.à = -var10 * 1.0f + var9;
        this.Å.à = var10 * 1.0f - var9;
        this.£à.à = -var10 * 2.0f + var9;
        this.µà.à = var10 * 2.0f - var9;
        final float var11 = -(MathHelper.Â(p_78087_1_ * 0.6662f * 2.0f + 0.0f) * 0.4f) * p_78087_2_;
        final float var12 = -(MathHelper.Â(p_78087_1_ * 0.6662f * 2.0f + 3.1415927f) * 0.4f) * p_78087_2_;
        final float var13 = -(MathHelper.Â(p_78087_1_ * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * p_78087_2_;
        final float var14 = -(MathHelper.Â(p_78087_1_ * 0.6662f * 2.0f + 4.712389f) * 0.4f) * p_78087_2_;
        final float var15 = Math.abs(MathHelper.HorizonCode_Horizon_È(p_78087_1_ * 0.6662f + 0.0f) * 0.4f) * p_78087_2_;
        final float var16 = Math.abs(MathHelper.HorizonCode_Horizon_È(p_78087_1_ * 0.6662f + 3.1415927f) * 0.4f) * p_78087_2_;
        final float var17 = Math.abs(MathHelper.HorizonCode_Horizon_È(p_78087_1_ * 0.6662f + 1.5707964f) * 0.4f) * p_78087_2_;
        final float var18 = Math.abs(MathHelper.HorizonCode_Horizon_È(p_78087_1_ * 0.6662f + 4.712389f) * 0.4f) * p_78087_2_;
        final ModelRenderer ø­áŒŠá = this.Ø­áŒŠá;
        ø­áŒŠá.à += var11;
        final ModelRenderer âµÈ = this.ÂµÈ;
        âµÈ.à += -var11;
        final ModelRenderer á = this.á;
        á.à += var12;
        final ModelRenderer ˆï­ = this.ˆÏ­;
        ˆï­.à += -var12;
        final ModelRenderer £á = this.£á;
        £á.à += var13;
        final ModelRenderer å = this.Å;
        å.à += -var13;
        final ModelRenderer £à = this.£à;
        £à.à += var14;
        final ModelRenderer µà = this.µà;
        µà.à += -var14;
        final ModelRenderer ø­áŒŠá2 = this.Ø­áŒŠá;
        ø­áŒŠá2.Ø += var15;
        final ModelRenderer âµÈ2 = this.ÂµÈ;
        âµÈ2.Ø += -var15;
        final ModelRenderer á2 = this.á;
        á2.Ø += var16;
        final ModelRenderer ˆï­2 = this.ˆÏ­;
        ˆï­2.Ø += -var16;
        final ModelRenderer £á2 = this.£á;
        £á2.Ø += var17;
        final ModelRenderer å2 = this.Å;
        å2.Ø += -var17;
        final ModelRenderer £à2 = this.£à;
        £à2.Ø += var18;
        final ModelRenderer µà2 = this.µà;
        µà2.Ø += -var18;
    }
}
