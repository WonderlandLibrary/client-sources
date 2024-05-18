package HORIZON-6-0-SKIDPROTECTION;

public class ModelPlayer extends ModelBiped
{
    public ModelRenderer HorizonCode_Horizon_È;
    public ModelRenderer Â;
    public ModelRenderer Ý;
    public ModelRenderer Ø­áŒŠá;
    public ModelRenderer Æ;
    private ModelRenderer Šáƒ;
    private ModelRenderer Ï­Ðƒà;
    private boolean áŒŠà;
    private static final String ŠÄ = "CL_00002626";
    
    public ModelPlayer(final float p_i46304_1_, final boolean p_i46304_2_) {
        super(p_i46304_1_, 0.0f, 64, 64);
        this.áŒŠà = p_i46304_2_;
        (this.Ï­Ðƒà = new ModelRenderer(this, 24, 0)).HorizonCode_Horizon_È(-3.0f, -6.0f, -1.0f, 6, 6, 1, p_i46304_1_);
        (this.Šáƒ = new ModelRenderer(this, 0, 0)).Â(64, 32);
        this.Šáƒ.HorizonCode_Horizon_È(-5.0f, 0.0f, -1.0f, 10, 16, 1, p_i46304_1_);
        if (p_i46304_2_) {
            (this.Å = new ModelRenderer(this, 32, 48)).HorizonCode_Horizon_È(-1.0f, -2.0f, -2.0f, 3, 12, 4, p_i46304_1_);
            this.Å.HorizonCode_Horizon_È(5.0f, 2.5f, 0.0f);
            (this.£á = new ModelRenderer(this, 40, 16)).HorizonCode_Horizon_È(-2.0f, -2.0f, -2.0f, 3, 12, 4, p_i46304_1_);
            this.£á.HorizonCode_Horizon_È(-5.0f, 2.5f, 0.0f);
            (this.HorizonCode_Horizon_È = new ModelRenderer(this, 48, 48)).HorizonCode_Horizon_È(-1.0f, -2.0f, -2.0f, 3, 12, 4, p_i46304_1_ + 0.25f);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(5.0f, 2.5f, 0.0f);
            (this.Â = new ModelRenderer(this, 40, 32)).HorizonCode_Horizon_È(-2.0f, -2.0f, -2.0f, 3, 12, 4, p_i46304_1_ + 0.25f);
            this.Â.HorizonCode_Horizon_È(-5.0f, 2.5f, 10.0f);
        }
        else {
            (this.Å = new ModelRenderer(this, 32, 48)).HorizonCode_Horizon_È(-1.0f, -2.0f, -2.0f, 4, 12, 4, p_i46304_1_);
            this.Å.HorizonCode_Horizon_È(5.0f, 2.0f, 0.0f);
            (this.HorizonCode_Horizon_È = new ModelRenderer(this, 48, 48)).HorizonCode_Horizon_È(-1.0f, -2.0f, -2.0f, 4, 12, 4, p_i46304_1_ + 0.25f);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(5.0f, 2.0f, 0.0f);
            (this.Â = new ModelRenderer(this, 40, 32)).HorizonCode_Horizon_È(-3.0f, -2.0f, -2.0f, 4, 12, 4, p_i46304_1_ + 0.25f);
            this.Â.HorizonCode_Horizon_È(-5.0f, 2.0f, 10.0f);
        }
        (this.µà = new ModelRenderer(this, 16, 48)).HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 12, 4, p_i46304_1_);
        this.µà.HorizonCode_Horizon_È(1.9f, 12.0f, 0.0f);
        (this.Ý = new ModelRenderer(this, 0, 48)).HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 12, 4, p_i46304_1_ + 0.25f);
        this.Ý.HorizonCode_Horizon_È(1.9f, 12.0f, 0.0f);
        (this.Ø­áŒŠá = new ModelRenderer(this, 0, 32)).HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 12, 4, p_i46304_1_ + 0.25f);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(-1.9f, 12.0f, 0.0f);
        (this.Æ = new ModelRenderer(this, 16, 32)).HorizonCode_Horizon_È(-4.0f, 0.0f, -2.0f, 8, 12, 4, p_i46304_1_ + 0.25f);
        this.Æ.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        super.HorizonCode_Horizon_È(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
        GlStateManager.Çªà¢();
        if (this.à) {
            final float var8 = 2.0f;
            GlStateManager.HorizonCode_Horizon_È(1.0f / var8, 1.0f / var8, 1.0f / var8);
            GlStateManager.Â(0.0f, 24.0f * p_78088_7_, 0.0f);
            this.Ý.HorizonCode_Horizon_È(p_78088_7_);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
            this.Â.HorizonCode_Horizon_È(p_78088_7_);
            this.Æ.HorizonCode_Horizon_È(p_78088_7_);
        }
        else {
            if (p_78088_1_.Çªà¢()) {
                GlStateManager.Â(0.0f, 0.2f, 0.0f);
            }
            this.Ý.HorizonCode_Horizon_È(p_78088_7_);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
            this.Â.HorizonCode_Horizon_È(p_78088_7_);
            this.Æ.HorizonCode_Horizon_È(p_78088_7_);
        }
        GlStateManager.Ê();
    }
    
    public void Â(final float p_178727_1_) {
        ModelBase.HorizonCode_Horizon_È(this.ÂµÈ, this.Ï­Ðƒà);
        this.Ï­Ðƒà.Ý = 0.0f;
        this.Ï­Ðƒà.Ø­áŒŠá = 0.0f;
        this.Ï­Ðƒà.HorizonCode_Horizon_È(p_178727_1_);
    }
    
    public void Ý(final float p_178728_1_) {
        this.Šáƒ.HorizonCode_Horizon_È(p_178728_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        super.HorizonCode_Horizon_È(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        ModelBase.HorizonCode_Horizon_È(this.µà, this.Ý);
        ModelBase.HorizonCode_Horizon_È(this.£à, this.Ø­áŒŠá);
        ModelBase.HorizonCode_Horizon_È(this.Å, this.HorizonCode_Horizon_È);
        ModelBase.HorizonCode_Horizon_È(this.£á, this.Â);
        ModelBase.HorizonCode_Horizon_È(this.ˆÏ­, this.Æ);
    }
    
    public void HorizonCode_Horizon_È() {
        this.£á.HorizonCode_Horizon_È(0.0625f);
        this.Â.HorizonCode_Horizon_È(0.0625f);
    }
    
    public void Â() {
        this.Å.HorizonCode_Horizon_È(0.0625f);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0.0625f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean p_178719_1_) {
        super.HorizonCode_Horizon_È(p_178719_1_);
        this.HorizonCode_Horizon_È.áˆºÑ¢Õ = p_178719_1_;
        this.Â.áˆºÑ¢Õ = p_178719_1_;
        this.Ý.áˆºÑ¢Õ = p_178719_1_;
        this.Ø­áŒŠá.áˆºÑ¢Õ = p_178719_1_;
        this.Æ.áˆºÑ¢Õ = p_178719_1_;
        this.Šáƒ.áˆºÑ¢Õ = p_178719_1_;
        this.Ï­Ðƒà.áˆºÑ¢Õ = p_178719_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_178718_1_) {
        if (this.áŒŠà) {
            final ModelRenderer £á = this.£á;
            ++£á.Ý;
            this.£á.Ý(p_178718_1_);
            final ModelRenderer £á2 = this.£á;
            --£á2.Ý;
        }
        else {
            this.£á.Ý(p_178718_1_);
        }
    }
}
