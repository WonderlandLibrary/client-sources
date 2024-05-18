package HORIZON-6-0-SKIDPROTECTION;

public class ModelArmorStand extends ModelArmorStandArmor
{
    public ModelRenderer HorizonCode_Horizon_È;
    public ModelRenderer Â;
    public ModelRenderer Ý;
    public ModelRenderer Ø­áŒŠá;
    private static final String Æ = "CL_00002631";
    
    public ModelArmorStand() {
        this(0.0f);
    }
    
    public ModelArmorStand(final float p_i46306_1_) {
        super(p_i46306_1_, 64, 64);
        (this.ÂµÈ = new ModelRenderer(this, 0, 0)).HorizonCode_Horizon_È(-1.0f, -7.0f, -1.0f, 2, 7, 2, p_i46306_1_);
        this.ÂµÈ.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f);
        (this.ˆÏ­ = new ModelRenderer(this, 0, 26)).HorizonCode_Horizon_È(-6.0f, 0.0f, -1.5f, 12, 3, 3, p_i46306_1_);
        this.ˆÏ­.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f);
        (this.£á = new ModelRenderer(this, 24, 0)).HorizonCode_Horizon_È(-2.0f, -2.0f, -1.0f, 2, 12, 2, p_i46306_1_);
        this.£á.HorizonCode_Horizon_È(-5.0f, 2.0f, 0.0f);
        this.Å = new ModelRenderer(this, 32, 16);
        this.Å.áŒŠÆ = true;
        this.Å.HorizonCode_Horizon_È(0.0f, -2.0f, -1.0f, 2, 12, 2, p_i46306_1_);
        this.Å.HorizonCode_Horizon_È(5.0f, 2.0f, 0.0f);
        (this.£à = new ModelRenderer(this, 8, 0)).HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 2, 11, 2, p_i46306_1_);
        this.£à.HorizonCode_Horizon_È(-1.9f, 12.0f, 0.0f);
        this.µà = new ModelRenderer(this, 40, 16);
        this.µà.áŒŠÆ = true;
        this.µà.HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 2, 11, 2, p_i46306_1_);
        this.µà.HorizonCode_Horizon_È(1.9f, 12.0f, 0.0f);
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, 16, 0)).HorizonCode_Horizon_È(-3.0f, 3.0f, -1.0f, 2, 7, 2, p_i46306_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f);
        this.HorizonCode_Horizon_È.áˆºÑ¢Õ = true;
        (this.Â = new ModelRenderer(this, 48, 16)).HorizonCode_Horizon_È(1.0f, 3.0f, -1.0f, 2, 7, 2, p_i46306_1_);
        this.Â.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f);
        (this.Ý = new ModelRenderer(this, 0, 48)).HorizonCode_Horizon_È(-4.0f, 10.0f, -1.0f, 8, 2, 2, p_i46306_1_);
        this.Ý.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f);
        (this.Ø­áŒŠá = new ModelRenderer(this, 0, 32)).HorizonCode_Horizon_È(-6.0f, 11.0f, -6.0f, 12, 1, 12, p_i46306_1_);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(0.0f, 12.0f, 0.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        super.HorizonCode_Horizon_È(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        if (p_78087_7_ instanceof EntityArmorStand) {
            final EntityArmorStand var8 = (EntityArmorStand)p_78087_7_;
            this.Å.áˆºÑ¢Õ = var8.ˆà();
            this.£á.áˆºÑ¢Õ = var8.ˆà();
            this.Ø­áŒŠá.áˆºÑ¢Õ = !var8.¥Æ();
            this.µà.HorizonCode_Horizon_È(1.9f, 12.0f, 0.0f);
            this.£à.HorizonCode_Horizon_È(-1.9f, 12.0f, 0.0f);
            this.HorizonCode_Horizon_È.Ó = 0.017453292f * var8.µÕ().Â();
            this.HorizonCode_Horizon_È.à = 0.017453292f * var8.µÕ().Ý();
            this.HorizonCode_Horizon_È.Ø = 0.017453292f * var8.µÕ().Ø­áŒŠá();
            this.Â.Ó = 0.017453292f * var8.µÕ().Â();
            this.Â.à = 0.017453292f * var8.µÕ().Ý();
            this.Â.Ø = 0.017453292f * var8.µÕ().Ø­áŒŠá();
            this.Ý.Ó = 0.017453292f * var8.µÕ().Â();
            this.Ý.à = 0.017453292f * var8.µÕ().Ý();
            this.Ý.Ø = 0.017453292f * var8.µÕ().Ø­áŒŠá();
            final float var9 = (var8.Ï­Ðƒà().Â() + var8.Ñ¢á().Â()) / 2.0f;
            final float var10 = (var8.Ï­Ðƒà().Ý() + var8.Ñ¢á().Ý()) / 2.0f;
            final float var11 = (var8.Ï­Ðƒà().Ø­áŒŠá() + var8.Ñ¢á().Ø­áŒŠá()) / 2.0f;
            this.Ø­áŒŠá.Ó = 0.0f;
            this.Ø­áŒŠá.à = 0.017453292f * -p_78087_7_.É;
            this.Ø­áŒŠá.Ø = 0.0f;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        super.HorizonCode_Horizon_È(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
        GlStateManager.Çªà¢();
        if (this.à) {
            final float var8 = 2.0f;
            GlStateManager.HorizonCode_Horizon_È(1.0f / var8, 1.0f / var8, 1.0f / var8);
            GlStateManager.Â(0.0f, 24.0f * p_78088_7_, 0.0f);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
            this.Â.HorizonCode_Horizon_È(p_78088_7_);
            this.Ý.HorizonCode_Horizon_È(p_78088_7_);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
        }
        else {
            if (p_78088_1_.Çªà¢()) {
                GlStateManager.Â(0.0f, 0.2f, 0.0f);
            }
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
            this.Â.HorizonCode_Horizon_È(p_78088_7_);
            this.Ý.HorizonCode_Horizon_È(p_78088_7_);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
        }
        GlStateManager.Ê();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_178718_1_) {
        final boolean var2 = this.£á.áˆºÑ¢Õ;
        this.£á.áˆºÑ¢Õ = true;
        super.HorizonCode_Horizon_È(p_178718_1_);
        this.£á.áˆºÑ¢Õ = var2;
    }
}
