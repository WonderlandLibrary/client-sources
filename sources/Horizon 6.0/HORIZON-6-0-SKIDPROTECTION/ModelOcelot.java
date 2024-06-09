package HORIZON-6-0-SKIDPROTECTION;

public class ModelOcelot extends ModelBase
{
    ModelRenderer HorizonCode_Horizon_È;
    ModelRenderer Â;
    ModelRenderer Ý;
    ModelRenderer Ø­áŒŠá;
    ModelRenderer ÂµÈ;
    ModelRenderer á;
    ModelRenderer ˆÏ­;
    ModelRenderer £á;
    int Å;
    private static final String £à = "CL_00000848";
    
    public ModelOcelot() {
        this.Å = 1;
        this.HorizonCode_Horizon_È("head.main", 0, 0);
        this.HorizonCode_Horizon_È("head.nose", 0, 24);
        this.HorizonCode_Horizon_È("head.ear1", 0, 10);
        this.HorizonCode_Horizon_È("head.ear2", 6, 10);
        (this.ˆÏ­ = new ModelRenderer(this, "head")).HorizonCode_Horizon_È("main", -2.5f, -2.0f, -3.0f, 5, 4, 5);
        this.ˆÏ­.HorizonCode_Horizon_È("nose", -1.5f, 0.0f, -4.0f, 3, 2, 2);
        this.ˆÏ­.HorizonCode_Horizon_È("ear1", -2.0f, -3.0f, 0.0f, 1, 1, 2);
        this.ˆÏ­.HorizonCode_Horizon_È("ear2", 1.0f, -3.0f, 0.0f, 1, 1, 2);
        this.ˆÏ­.HorizonCode_Horizon_È(0.0f, 15.0f, -9.0f);
        (this.£á = new ModelRenderer(this, 20, 0)).HorizonCode_Horizon_È(-2.0f, 3.0f, -8.0f, 4, 16, 6, 0.0f);
        this.£á.HorizonCode_Horizon_È(0.0f, 12.0f, -10.0f);
        (this.ÂµÈ = new ModelRenderer(this, 0, 15)).HorizonCode_Horizon_È(-0.5f, 0.0f, 0.0f, 1, 8, 1);
        this.ÂµÈ.Ó = 0.9f;
        this.ÂµÈ.HorizonCode_Horizon_È(0.0f, 15.0f, 8.0f);
        (this.á = new ModelRenderer(this, 4, 15)).HorizonCode_Horizon_È(-0.5f, 0.0f, 0.0f, 1, 8, 1);
        this.á.HorizonCode_Horizon_È(0.0f, 20.0f, 14.0f);
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, 8, 13)).HorizonCode_Horizon_È(-1.0f, 0.0f, 1.0f, 2, 6, 2);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(1.1f, 18.0f, 5.0f);
        (this.Â = new ModelRenderer(this, 8, 13)).HorizonCode_Horizon_È(-1.0f, 0.0f, 1.0f, 2, 6, 2);
        this.Â.HorizonCode_Horizon_È(-1.1f, 18.0f, 5.0f);
        (this.Ý = new ModelRenderer(this, 40, 0)).HorizonCode_Horizon_È(-1.0f, 0.0f, 0.0f, 2, 10, 2);
        this.Ý.HorizonCode_Horizon_È(1.2f, 13.8f, -5.0f);
        (this.Ø­áŒŠá = new ModelRenderer(this, 40, 0)).HorizonCode_Horizon_È(-1.0f, 0.0f, 0.0f, 2, 10, 2);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(-1.2f, 13.8f, -5.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        if (this.à) {
            final float var8 = 2.0f;
            GlStateManager.Çªà¢();
            GlStateManager.HorizonCode_Horizon_È(1.5f / var8, 1.5f / var8, 1.5f / var8);
            GlStateManager.Â(0.0f, 10.0f * p_78088_7_, 4.0f * p_78088_7_);
            this.ˆÏ­.HorizonCode_Horizon_È(p_78088_7_);
            GlStateManager.Ê();
            GlStateManager.Çªà¢();
            GlStateManager.HorizonCode_Horizon_È(1.0f / var8, 1.0f / var8, 1.0f / var8);
            GlStateManager.Â(0.0f, 24.0f * p_78088_7_, 0.0f);
            this.£á.HorizonCode_Horizon_È(p_78088_7_);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
            this.Â.HorizonCode_Horizon_È(p_78088_7_);
            this.Ý.HorizonCode_Horizon_È(p_78088_7_);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
            this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
            this.á.HorizonCode_Horizon_È(p_78088_7_);
            GlStateManager.Ê();
        }
        else {
            this.ˆÏ­.HorizonCode_Horizon_È(p_78088_7_);
            this.£á.HorizonCode_Horizon_È(p_78088_7_);
            this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
            this.á.HorizonCode_Horizon_È(p_78088_7_);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
            this.Â.HorizonCode_Horizon_È(p_78088_7_);
            this.Ý.HorizonCode_Horizon_È(p_78088_7_);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        this.ˆÏ­.Ó = p_78087_5_ / 57.295776f;
        this.ˆÏ­.à = p_78087_4_ / 57.295776f;
        if (this.Å != 3) {
            this.£á.Ó = 1.5707964f;
            if (this.Å == 2) {
                this.HorizonCode_Horizon_È.Ó = MathHelper.Â(p_78087_1_ * 0.6662f) * 1.0f * p_78087_2_;
                this.Â.Ó = MathHelper.Â(p_78087_1_ * 0.6662f + 0.3f) * 1.0f * p_78087_2_;
                this.Ý.Ó = MathHelper.Â(p_78087_1_ * 0.6662f + 3.1415927f + 0.3f) * 1.0f * p_78087_2_;
                this.Ø­áŒŠá.Ó = MathHelper.Â(p_78087_1_ * 0.6662f + 3.1415927f) * 1.0f * p_78087_2_;
                this.á.Ó = 1.7278761f + 0.31415927f * MathHelper.Â(p_78087_1_) * p_78087_2_;
            }
            else {
                this.HorizonCode_Horizon_È.Ó = MathHelper.Â(p_78087_1_ * 0.6662f) * 1.0f * p_78087_2_;
                this.Â.Ó = MathHelper.Â(p_78087_1_ * 0.6662f + 3.1415927f) * 1.0f * p_78087_2_;
                this.Ý.Ó = MathHelper.Â(p_78087_1_ * 0.6662f + 3.1415927f) * 1.0f * p_78087_2_;
                this.Ø­áŒŠá.Ó = MathHelper.Â(p_78087_1_ * 0.6662f) * 1.0f * p_78087_2_;
                if (this.Å == 1) {
                    this.á.Ó = 1.7278761f + 0.7853982f * MathHelper.Â(p_78087_1_) * p_78087_2_;
                }
                else {
                    this.á.Ó = 1.7278761f + 0.47123894f * MathHelper.Â(p_78087_1_) * p_78087_2_;
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_78086_1_, final float p_78086_2_, final float p_78086_3_, final float p_78086_4_) {
        final EntityOcelot var5 = (EntityOcelot)p_78086_1_;
        this.£á.Ø­áŒŠá = 12.0f;
        this.£á.Âµá€ = -10.0f;
        this.ˆÏ­.Ø­áŒŠá = 15.0f;
        this.ˆÏ­.Âµá€ = -9.0f;
        this.ÂµÈ.Ø­áŒŠá = 15.0f;
        this.ÂµÈ.Âµá€ = 8.0f;
        this.á.Ø­áŒŠá = 20.0f;
        this.á.Âµá€ = 14.0f;
        final ModelRenderer ý = this.Ý;
        final ModelRenderer ø­áŒŠá = this.Ø­áŒŠá;
        final float n = 13.8f;
        ø­áŒŠá.Ø­áŒŠá = n;
        ý.Ø­áŒŠá = n;
        final ModelRenderer ý2 = this.Ý;
        final ModelRenderer ø­áŒŠá2 = this.Ø­áŒŠá;
        final float n2 = -5.0f;
        ø­áŒŠá2.Âµá€ = n2;
        ý2.Âµá€ = n2;
        final ModelRenderer horizonCode_Horizon_È = this.HorizonCode_Horizon_È;
        final ModelRenderer â = this.Â;
        final float n3 = 18.0f;
        â.Ø­áŒŠá = n3;
        horizonCode_Horizon_È.Ø­áŒŠá = n3;
        final ModelRenderer horizonCode_Horizon_È2 = this.HorizonCode_Horizon_È;
        final ModelRenderer â2 = this.Â;
        final float n4 = 5.0f;
        â2.Âµá€ = n4;
        horizonCode_Horizon_È2.Âµá€ = n4;
        this.ÂµÈ.Ó = 0.9f;
        if (var5.Çªà¢()) {
            final ModelRenderer £á = this.£á;
            ++£á.Ø­áŒŠá;
            final ModelRenderer ˆï­ = this.ˆÏ­;
            ˆï­.Ø­áŒŠá += 2.0f;
            final ModelRenderer âµÈ = this.ÂµÈ;
            ++âµÈ.Ø­áŒŠá;
            final ModelRenderer á = this.á;
            á.Ø­áŒŠá -= 4.0f;
            final ModelRenderer á2 = this.á;
            á2.Âµá€ += 2.0f;
            this.ÂµÈ.Ó = 1.5707964f;
            this.á.Ó = 1.5707964f;
            this.Å = 0;
        }
        else if (var5.ÇªÂµÕ()) {
            this.á.Ø­áŒŠá = this.ÂµÈ.Ø­áŒŠá;
            final ModelRenderer á3 = this.á;
            á3.Âµá€ += 2.0f;
            this.ÂµÈ.Ó = 1.5707964f;
            this.á.Ó = 1.5707964f;
            this.Å = 2;
        }
        else if (var5.áˆºÕ()) {
            this.£á.Ó = 0.7853982f;
            final ModelRenderer £á2 = this.£á;
            £á2.Ø­áŒŠá -= 4.0f;
            final ModelRenderer £á3 = this.£á;
            £á3.Âµá€ += 5.0f;
            final ModelRenderer ˆï­2 = this.ˆÏ­;
            ˆï­2.Ø­áŒŠá -= 3.3f;
            final ModelRenderer ˆï­3 = this.ˆÏ­;
            ++ˆï­3.Âµá€;
            final ModelRenderer âµÈ2 = this.ÂµÈ;
            âµÈ2.Ø­áŒŠá += 8.0f;
            final ModelRenderer âµÈ3 = this.ÂµÈ;
            âµÈ3.Âµá€ -= 2.0f;
            final ModelRenderer á4 = this.á;
            á4.Ø­áŒŠá += 2.0f;
            final ModelRenderer á5 = this.á;
            á5.Âµá€ -= 0.8f;
            this.ÂµÈ.Ó = 1.7278761f;
            this.á.Ó = 2.670354f;
            final ModelRenderer ý3 = this.Ý;
            final ModelRenderer ø­áŒŠá3 = this.Ø­áŒŠá;
            final float n5 = -0.15707964f;
            ø­áŒŠá3.Ó = n5;
            ý3.Ó = n5;
            final ModelRenderer ý4 = this.Ý;
            final ModelRenderer ø­áŒŠá4 = this.Ø­áŒŠá;
            final float n6 = 15.8f;
            ø­áŒŠá4.Ø­áŒŠá = n6;
            ý4.Ø­áŒŠá = n6;
            final ModelRenderer ý5 = this.Ý;
            final ModelRenderer ø­áŒŠá5 = this.Ø­áŒŠá;
            final float n7 = -7.0f;
            ø­áŒŠá5.Âµá€ = n7;
            ý5.Âµá€ = n7;
            final ModelRenderer horizonCode_Horizon_È3 = this.HorizonCode_Horizon_È;
            final ModelRenderer â3 = this.Â;
            final float n8 = -1.5707964f;
            â3.Ó = n8;
            horizonCode_Horizon_È3.Ó = n8;
            final ModelRenderer horizonCode_Horizon_È4 = this.HorizonCode_Horizon_È;
            final ModelRenderer â4 = this.Â;
            final float n9 = 21.0f;
            â4.Ø­áŒŠá = n9;
            horizonCode_Horizon_È4.Ø­áŒŠá = n9;
            final ModelRenderer horizonCode_Horizon_È5 = this.HorizonCode_Horizon_È;
            final ModelRenderer â5 = this.Â;
            final float n10 = 1.0f;
            â5.Âµá€ = n10;
            horizonCode_Horizon_È5.Âµá€ = n10;
            this.Å = 3;
        }
        else {
            this.Å = 1;
        }
    }
}
