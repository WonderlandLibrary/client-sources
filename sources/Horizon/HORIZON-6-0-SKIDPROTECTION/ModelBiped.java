package HORIZON-6-0-SKIDPROTECTION;

public class ModelBiped extends ModelBase
{
    public ModelRenderer ÂµÈ;
    public ModelRenderer á;
    public ModelRenderer ˆÏ­;
    public ModelRenderer £á;
    public ModelRenderer Å;
    public ModelRenderer £à;
    public ModelRenderer µà;
    public int ˆà;
    public int ¥Æ;
    public boolean Ø­à;
    public boolean µÕ;
    private static final String HorizonCode_Horizon_È = "CL_00000840";
    
    public ModelBiped() {
        this(0.0f);
    }
    
    public ModelBiped(final float p_i1148_1_) {
        this(p_i1148_1_, 0.0f, 64, 32);
    }
    
    public ModelBiped(final float p_i1149_1_, final float p_i1149_2_, final int p_i1149_3_, final int p_i1149_4_) {
        this.áŒŠÆ = p_i1149_3_;
        this.áˆºÑ¢Õ = p_i1149_4_;
        (this.ÂµÈ = new ModelRenderer(this, 0, 0)).HorizonCode_Horizon_È(-4.0f, -8.0f, -4.0f, 8, 8, 8, p_i1149_1_);
        this.ÂµÈ.HorizonCode_Horizon_È(0.0f, 0.0f + p_i1149_2_, 0.0f);
        (this.á = new ModelRenderer(this, 32, 0)).HorizonCode_Horizon_È(-4.0f, -8.0f, -4.0f, 8, 8, 8, p_i1149_1_ + 0.5f);
        this.á.HorizonCode_Horizon_È(0.0f, 0.0f + p_i1149_2_, 0.0f);
        (this.ˆÏ­ = new ModelRenderer(this, 16, 16)).HorizonCode_Horizon_È(-4.0f, 0.0f, -2.0f, 8, 12, 4, p_i1149_1_);
        this.ˆÏ­.HorizonCode_Horizon_È(0.0f, 0.0f + p_i1149_2_, 0.0f);
        (this.£á = new ModelRenderer(this, 40, 16)).HorizonCode_Horizon_È(-3.0f, -2.0f, -2.0f, 4, 12, 4, p_i1149_1_);
        this.£á.HorizonCode_Horizon_È(-5.0f, 2.0f + p_i1149_2_, 0.0f);
        this.Å = new ModelRenderer(this, 40, 16);
        this.Å.áŒŠÆ = true;
        this.Å.HorizonCode_Horizon_È(-1.0f, -2.0f, -2.0f, 4, 12, 4, p_i1149_1_);
        this.Å.HorizonCode_Horizon_È(5.0f, 2.0f + p_i1149_2_, 0.0f);
        (this.£à = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 12, 4, p_i1149_1_);
        this.£à.HorizonCode_Horizon_È(-1.9f, 12.0f + p_i1149_2_, 0.0f);
        this.µà = new ModelRenderer(this, 0, 16);
        this.µà.áŒŠÆ = true;
        this.µà.HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 12, 4, p_i1149_1_);
        this.µà.HorizonCode_Horizon_È(1.9f, 12.0f + p_i1149_2_, 0.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        GlStateManager.Çªà¢();
        if (this.à) {
            final float var8 = 2.0f;
            GlStateManager.HorizonCode_Horizon_È(1.5f / var8, 1.5f / var8, 1.5f / var8);
            GlStateManager.Â(0.0f, 16.0f * p_78088_7_, 0.0f);
            this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
            GlStateManager.Ê();
            GlStateManager.Çªà¢();
            GlStateManager.HorizonCode_Horizon_È(1.0f / var8, 1.0f / var8, 1.0f / var8);
            GlStateManager.Â(0.0f, 24.0f * p_78088_7_, 0.0f);
            this.ˆÏ­.HorizonCode_Horizon_È(p_78088_7_);
            this.£á.HorizonCode_Horizon_È(p_78088_7_);
            this.Å.HorizonCode_Horizon_È(p_78088_7_);
            this.£à.HorizonCode_Horizon_È(p_78088_7_);
            this.µà.HorizonCode_Horizon_È(p_78088_7_);
            this.á.HorizonCode_Horizon_È(p_78088_7_);
        }
        else {
            if (p_78088_1_.Çªà¢()) {
                GlStateManager.Â(0.0f, 0.2f, 0.0f);
            }
            this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
            this.ˆÏ­.HorizonCode_Horizon_È(p_78088_7_);
            this.£á.HorizonCode_Horizon_È(p_78088_7_);
            this.Å.HorizonCode_Horizon_È(p_78088_7_);
            this.£à.HorizonCode_Horizon_È(p_78088_7_);
            this.µà.HorizonCode_Horizon_È(p_78088_7_);
            this.á.HorizonCode_Horizon_È(p_78088_7_);
        }
        GlStateManager.Ê();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        this.ÂµÈ.à = p_78087_4_ / 57.295776f;
        this.ÂµÈ.Ó = p_78087_5_ / 57.295776f;
        this.£á.Ó = MathHelper.Â(p_78087_1_ * 0.6662f + 3.1415927f) * 2.0f * p_78087_2_ * 0.5f;
        this.Å.Ó = MathHelper.Â(p_78087_1_ * 0.6662f) * 2.0f * p_78087_2_ * 0.5f;
        this.£á.Ø = 0.0f;
        this.Å.Ø = 0.0f;
        this.£à.Ó = MathHelper.Â(p_78087_1_ * 0.6662f) * 1.4f * p_78087_2_;
        this.µà.Ó = MathHelper.Â(p_78087_1_ * 0.6662f + 3.1415927f) * 1.4f * p_78087_2_;
        this.£à.à = 0.0f;
        this.µà.à = 0.0f;
        if (this.Ó) {
            final ModelRenderer £á = this.£á;
            £á.Ó -= 0.62831855f;
            final ModelRenderer å = this.Å;
            å.Ó -= 0.62831855f;
            this.£à.Ó = -1.2566371f;
            this.µà.Ó = -1.2566371f;
            this.£à.à = 0.31415927f;
            this.µà.à = -0.31415927f;
        }
        if (this.ˆà != 0) {
            this.Å.Ó = this.Å.Ó * 0.5f - 0.31415927f * this.ˆà;
        }
        this.£á.à = 0.0f;
        this.£á.Ø = 0.0f;
        switch (this.¥Æ) {
            case 1: {
                this.£á.Ó = this.£á.Ó * 0.5f - 0.31415927f * this.¥Æ;
                break;
            }
            case 3: {
                this.£á.Ó = this.£á.Ó * 0.5f - 0.31415927f * this.¥Æ;
                this.£á.à = -0.5235988f;
                break;
            }
        }
        this.Å.à = 0.0f;
        if (this.Âµá€ > -9990.0f) {
            float var8 = this.Âµá€;
            this.ˆÏ­.à = MathHelper.HorizonCode_Horizon_È(MathHelper.Ý(var8) * 3.1415927f * 2.0f) * 0.2f;
            this.£á.Âµá€ = MathHelper.HorizonCode_Horizon_È(this.ˆÏ­.à) * 5.0f;
            this.£á.Ý = -MathHelper.Â(this.ˆÏ­.à) * 5.0f;
            this.Å.Âµá€ = -MathHelper.HorizonCode_Horizon_È(this.ˆÏ­.à) * 5.0f;
            this.Å.Ý = MathHelper.Â(this.ˆÏ­.à) * 5.0f;
            final ModelRenderer £á2 = this.£á;
            £á2.à += this.ˆÏ­.à;
            final ModelRenderer å2 = this.Å;
            å2.à += this.ˆÏ­.à;
            final ModelRenderer å3 = this.Å;
            å3.Ó += this.ˆÏ­.à;
            var8 = 1.0f - this.Âµá€;
            var8 *= var8;
            var8 *= var8;
            var8 = 1.0f - var8;
            final float var9 = MathHelper.HorizonCode_Horizon_È(var8 * 3.1415927f);
            final float var10 = MathHelper.HorizonCode_Horizon_È(this.Âµá€ * 3.1415927f) * -(this.ÂµÈ.Ó - 0.7f) * 0.75f;
            this.£á.Ó -= (float)(var9 * 1.2 + var10);
            final ModelRenderer £á3 = this.£á;
            £á3.à += this.ˆÏ­.à * 2.0f;
            final ModelRenderer £á4 = this.£á;
            £á4.Ø += MathHelper.HorizonCode_Horizon_È(this.Âµá€ * 3.1415927f) * -0.4f;
        }
        if (this.Ø­à) {
            this.ˆÏ­.Ó = 0.5f;
            final ModelRenderer £á5 = this.£á;
            £á5.Ó += 0.4f;
            final ModelRenderer å4 = this.Å;
            å4.Ó += 0.4f;
            this.£à.Âµá€ = 4.0f;
            this.µà.Âµá€ = 4.0f;
            this.£à.Ø­áŒŠá = 9.0f;
            this.µà.Ø­áŒŠá = 9.0f;
            this.ÂµÈ.Ø­áŒŠá = 1.0f;
        }
        else {
            this.ˆÏ­.Ó = 0.0f;
            this.£à.Âµá€ = 0.1f;
            this.µà.Âµá€ = 0.1f;
            this.£à.Ø­áŒŠá = 12.0f;
            this.µà.Ø­áŒŠá = 12.0f;
            this.ÂµÈ.Ø­áŒŠá = 0.0f;
        }
        final ModelRenderer £á6 = this.£á;
        £á6.Ø += MathHelper.Â(p_78087_3_ * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer å5 = this.Å;
        å5.Ø -= MathHelper.Â(p_78087_3_ * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer £á7 = this.£á;
        £á7.Ó += MathHelper.HorizonCode_Horizon_È(p_78087_3_ * 0.067f) * 0.05f;
        final ModelRenderer å6 = this.Å;
        å6.Ó -= MathHelper.HorizonCode_Horizon_È(p_78087_3_ * 0.067f) * 0.05f;
        if (this.µÕ) {
            final float var8 = 0.0f;
            final float var9 = 0.0f;
            this.£á.Ø = 0.0f;
            this.Å.Ø = 0.0f;
            this.£á.à = -(0.1f - var8 * 0.6f) + this.ÂµÈ.à;
            this.Å.à = 0.1f - var8 * 0.6f + this.ÂµÈ.à + 0.4f;
            this.£á.Ó = -1.5707964f + this.ÂµÈ.Ó;
            this.Å.Ó = -1.5707964f + this.ÂµÈ.Ó;
            final ModelRenderer £á8 = this.£á;
            £á8.Ó -= var8 * 1.2f - var9 * 0.4f;
            final ModelRenderer å7 = this.Å;
            å7.Ó -= var8 * 1.2f - var9 * 0.4f;
            final ModelRenderer £á9 = this.£á;
            £á9.Ø += MathHelper.Â(p_78087_3_ * 0.09f) * 0.05f + 0.05f;
            final ModelRenderer å8 = this.Å;
            å8.Ø -= MathHelper.Â(p_78087_3_ * 0.09f) * 0.05f + 0.05f;
            final ModelRenderer £á10 = this.£á;
            £á10.Ó += MathHelper.HorizonCode_Horizon_È(p_78087_3_ * 0.067f) * 0.05f;
            final ModelRenderer å9 = this.Å;
            å9.Ó -= MathHelper.HorizonCode_Horizon_È(p_78087_3_ * 0.067f) * 0.05f;
        }
        ModelBase.HorizonCode_Horizon_È(this.ÂµÈ, this.á);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ModelBase p_178686_1_) {
        super.HorizonCode_Horizon_È(p_178686_1_);
        if (p_178686_1_ instanceof ModelBiped) {
            final ModelBiped var2 = (ModelBiped)p_178686_1_;
            this.ˆà = var2.ˆà;
            this.¥Æ = var2.¥Æ;
            this.Ø­à = var2.Ø­à;
            this.µÕ = var2.µÕ;
        }
    }
    
    public void HorizonCode_Horizon_È(final boolean p_178719_1_) {
        this.ÂµÈ.áˆºÑ¢Õ = p_178719_1_;
        this.á.áˆºÑ¢Õ = p_178719_1_;
        this.ˆÏ­.áˆºÑ¢Õ = p_178719_1_;
        this.£á.áˆºÑ¢Õ = p_178719_1_;
        this.Å.áˆºÑ¢Õ = p_178719_1_;
        this.£à.áˆºÑ¢Õ = p_178719_1_;
        this.µà.áˆºÑ¢Õ = p_178719_1_;
    }
    
    public void HorizonCode_Horizon_È(final float p_178718_1_) {
        this.£á.Ý(p_178718_1_);
    }
}
