package HORIZON-6-0-SKIDPROTECTION;

public class ModelIronGolem extends ModelBase
{
    public ModelRenderer HorizonCode_Horizon_È;
    public ModelRenderer Â;
    public ModelRenderer Ý;
    public ModelRenderer Ø­áŒŠá;
    public ModelRenderer ÂµÈ;
    public ModelRenderer á;
    private static final String ˆÏ­ = "CL_00000863";
    
    public ModelIronGolem() {
        this(0.0f);
    }
    
    public ModelIronGolem(final float p_i1161_1_) {
        this(p_i1161_1_, -7.0f);
    }
    
    public ModelIronGolem(final float p_i46362_1_, final float p_i46362_2_) {
        final short var3 = 128;
        final short var4 = 128;
        (this.HorizonCode_Horizon_È = new ModelRenderer(this).Â(var3, var4)).HorizonCode_Horizon_È(0.0f, 0.0f + p_i46362_2_, -2.0f);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0, 0).HorizonCode_Horizon_È(-4.0f, -12.0f, -5.5f, 8, 10, 8, p_i46362_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(24, 0).HorizonCode_Horizon_È(-1.0f, -5.0f, -7.5f, 2, 4, 2, p_i46362_1_);
        (this.Â = new ModelRenderer(this).Â(var3, var4)).HorizonCode_Horizon_È(0.0f, 0.0f + p_i46362_2_, 0.0f);
        this.Â.HorizonCode_Horizon_È(0, 40).HorizonCode_Horizon_È(-9.0f, -2.0f, -6.0f, 18, 12, 11, p_i46362_1_);
        this.Â.HorizonCode_Horizon_È(0, 70).HorizonCode_Horizon_È(-4.5f, 10.0f, -3.0f, 9, 5, 6, p_i46362_1_ + 0.5f);
        (this.Ý = new ModelRenderer(this).Â(var3, var4)).HorizonCode_Horizon_È(0.0f, -7.0f, 0.0f);
        this.Ý.HorizonCode_Horizon_È(60, 21).HorizonCode_Horizon_È(-13.0f, -2.5f, -3.0f, 4, 30, 6, p_i46362_1_);
        (this.Ø­áŒŠá = new ModelRenderer(this).Â(var3, var4)).HorizonCode_Horizon_È(0.0f, -7.0f, 0.0f);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(60, 58).HorizonCode_Horizon_È(9.0f, -2.5f, -3.0f, 4, 30, 6, p_i46362_1_);
        (this.ÂµÈ = new ModelRenderer(this, 0, 22).Â(var3, var4)).HorizonCode_Horizon_È(-4.0f, 18.0f + p_i46362_2_, 0.0f);
        this.ÂµÈ.HorizonCode_Horizon_È(37, 0).HorizonCode_Horizon_È(-3.5f, -3.0f, -3.0f, 6, 16, 5, p_i46362_1_);
        this.á = new ModelRenderer(this, 0, 22).Â(var3, var4);
        this.á.áŒŠÆ = true;
        this.á.HorizonCode_Horizon_È(60, 0).HorizonCode_Horizon_È(5.0f, 18.0f + p_i46362_2_, 0.0f);
        this.á.HorizonCode_Horizon_È(-3.5f, -3.0f, -3.0f, 6, 16, 5, p_i46362_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
        this.Â.HorizonCode_Horizon_È(p_78088_7_);
        this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
        this.á.HorizonCode_Horizon_È(p_78088_7_);
        this.Ý.HorizonCode_Horizon_È(p_78088_7_);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        this.HorizonCode_Horizon_È.à = p_78087_4_ / 57.295776f;
        this.HorizonCode_Horizon_È.Ó = p_78087_5_ / 57.295776f;
        this.ÂµÈ.Ó = -1.5f * this.HorizonCode_Horizon_È(p_78087_1_, 13.0f) * p_78087_2_;
        this.á.Ó = 1.5f * this.HorizonCode_Horizon_È(p_78087_1_, 13.0f) * p_78087_2_;
        this.ÂµÈ.à = 0.0f;
        this.á.à = 0.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_78086_1_, final float p_78086_2_, final float p_78086_3_, final float p_78086_4_) {
        final EntityIronGolem var5 = (EntityIronGolem)p_78086_1_;
        final int var6 = var5.ÇŽ();
        if (var6 > 0) {
            this.Ý.Ó = -2.0f + 1.5f * this.HorizonCode_Horizon_È(var6 - p_78086_4_, 10.0f);
            this.Ø­áŒŠá.Ó = -2.0f + 1.5f * this.HorizonCode_Horizon_È(var6 - p_78086_4_, 10.0f);
        }
        else {
            final int var7 = var5.ÇŽÅ();
            if (var7 > 0) {
                this.Ý.Ó = -0.8f + 0.025f * this.HorizonCode_Horizon_È(var7, 70.0f);
                this.Ø­áŒŠá.Ó = 0.0f;
            }
            else {
                this.Ý.Ó = (-0.2f + 1.5f * this.HorizonCode_Horizon_È(p_78086_2_, 13.0f)) * p_78086_3_;
                this.Ø­áŒŠá.Ó = (-0.2f - 1.5f * this.HorizonCode_Horizon_È(p_78086_2_, 13.0f)) * p_78086_3_;
            }
        }
    }
    
    private float HorizonCode_Horizon_È(final float p_78172_1_, final float p_78172_2_) {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5f) - p_78172_2_ * 0.25f) / (p_78172_2_ * 0.25f);
    }
}
