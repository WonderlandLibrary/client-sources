package HORIZON-6-0-SKIDPROTECTION;

public class ModelEnderman extends ModelBiped
{
    public boolean HorizonCode_Horizon_È;
    public boolean Â;
    private static final String Ý = "CL_00000838";
    
    public ModelEnderman(final float p_i46305_1_) {
        super(0.0f, -14.0f, 64, 32);
        final float var2 = -14.0f;
        (this.á = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(-4.0f, -8.0f, -4.0f, 8, 8, 8, p_i46305_1_ - 0.5f);
        this.á.HorizonCode_Horizon_È(0.0f, 0.0f + var2, 0.0f);
        (this.ˆÏ­ = new ModelRenderer(this, 32, 16)).HorizonCode_Horizon_È(-4.0f, 0.0f, -2.0f, 8, 12, 4, p_i46305_1_);
        this.ˆÏ­.HorizonCode_Horizon_È(0.0f, 0.0f + var2, 0.0f);
        (this.£á = new ModelRenderer(this, 56, 0)).HorizonCode_Horizon_È(-1.0f, -2.0f, -1.0f, 2, 30, 2, p_i46305_1_);
        this.£á.HorizonCode_Horizon_È(-3.0f, 2.0f + var2, 0.0f);
        this.Å = new ModelRenderer(this, 56, 0);
        this.Å.áŒŠÆ = true;
        this.Å.HorizonCode_Horizon_È(-1.0f, -2.0f, -1.0f, 2, 30, 2, p_i46305_1_);
        this.Å.HorizonCode_Horizon_È(5.0f, 2.0f + var2, 0.0f);
        (this.£à = new ModelRenderer(this, 56, 0)).HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 2, 30, 2, p_i46305_1_);
        this.£à.HorizonCode_Horizon_È(-2.0f, 12.0f + var2, 0.0f);
        this.µà = new ModelRenderer(this, 56, 0);
        this.µà.áŒŠÆ = true;
        this.µà.HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 2, 30, 2, p_i46305_1_);
        this.µà.HorizonCode_Horizon_È(2.0f, 12.0f + var2, 0.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        super.HorizonCode_Horizon_È(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        this.ÂµÈ.áˆºÑ¢Õ = true;
        final float var8 = -14.0f;
        this.ˆÏ­.Ó = 0.0f;
        this.ˆÏ­.Ø­áŒŠá = var8;
        this.ˆÏ­.Âµá€ = -0.0f;
        final ModelRenderer £à = this.£à;
        £à.Ó -= 0.0f;
        final ModelRenderer µà = this.µà;
        µà.Ó -= 0.0f;
        this.£á.Ó *= 0.5;
        this.Å.Ó *= 0.5;
        this.£à.Ó *= 0.5;
        this.µà.Ó *= 0.5;
        final float var9 = 0.4f;
        if (this.£á.Ó > var9) {
            this.£á.Ó = var9;
        }
        if (this.Å.Ó > var9) {
            this.Å.Ó = var9;
        }
        if (this.£á.Ó < -var9) {
            this.£á.Ó = -var9;
        }
        if (this.Å.Ó < -var9) {
            this.Å.Ó = -var9;
        }
        if (this.£à.Ó > var9) {
            this.£à.Ó = var9;
        }
        if (this.µà.Ó > var9) {
            this.µà.Ó = var9;
        }
        if (this.£à.Ó < -var9) {
            this.£à.Ó = -var9;
        }
        if (this.µà.Ó < -var9) {
            this.µà.Ó = -var9;
        }
        if (this.HorizonCode_Horizon_È) {
            this.£á.Ó = -0.5f;
            this.Å.Ó = -0.5f;
            this.£á.Ø = 0.05f;
            this.Å.Ø = -0.05f;
        }
        this.£á.Âµá€ = 0.0f;
        this.Å.Âµá€ = 0.0f;
        this.£à.Âµá€ = 0.0f;
        this.µà.Âµá€ = 0.0f;
        this.£à.Ø­áŒŠá = 9.0f + var8;
        this.µà.Ø­áŒŠá = 9.0f + var8;
        this.ÂµÈ.Âµá€ = -0.0f;
        this.ÂµÈ.Ø­áŒŠá = var8 + 1.0f;
        this.á.Ý = this.ÂµÈ.Ý;
        this.á.Ø­áŒŠá = this.ÂµÈ.Ø­áŒŠá;
        this.á.Âµá€ = this.ÂµÈ.Âµá€;
        this.á.Ó = this.ÂµÈ.Ó;
        this.á.à = this.ÂµÈ.à;
        this.á.Ø = this.ÂµÈ.Ø;
        if (this.Â) {
            final float var10 = 1.0f;
            final ModelRenderer âµÈ = this.ÂµÈ;
            âµÈ.Ø­áŒŠá -= var10 * 5.0f;
        }
    }
}
