package HORIZON-6-0-SKIDPROTECTION;

public class ModelWither extends ModelBase
{
    private ModelRenderer[] HorizonCode_Horizon_È;
    private ModelRenderer[] Â;
    private static final String Ý = "CL_00000867";
    
    public ModelWither(final float p_i46302_1_) {
        this.áŒŠÆ = 64;
        this.áˆºÑ¢Õ = 64;
        this.HorizonCode_Horizon_È = new ModelRenderer[3];
        (this.HorizonCode_Horizon_È[0] = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(-10.0f, 3.9f, -0.5f, 20, 3, 3, p_i46302_1_);
        (this.HorizonCode_Horizon_È[1] = new ModelRenderer(this).Â(this.áŒŠÆ, this.áˆºÑ¢Õ)).HorizonCode_Horizon_È(-2.0f, 6.9f, -0.5f);
        this.HorizonCode_Horizon_È[1].HorizonCode_Horizon_È(0, 22).HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 3, 10, 3, p_i46302_1_);
        this.HorizonCode_Horizon_È[1].HorizonCode_Horizon_È(24, 22).HorizonCode_Horizon_È(-4.0f, 1.5f, 0.5f, 11, 2, 2, p_i46302_1_);
        this.HorizonCode_Horizon_È[1].HorizonCode_Horizon_È(24, 22).HorizonCode_Horizon_È(-4.0f, 4.0f, 0.5f, 11, 2, 2, p_i46302_1_);
        this.HorizonCode_Horizon_È[1].HorizonCode_Horizon_È(24, 22).HorizonCode_Horizon_È(-4.0f, 6.5f, 0.5f, 11, 2, 2, p_i46302_1_);
        (this.HorizonCode_Horizon_È[2] = new ModelRenderer(this, 12, 22)).HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 3, 6, 3, p_i46302_1_);
        this.Â = new ModelRenderer[3];
        (this.Â[0] = new ModelRenderer(this, 0, 0)).HorizonCode_Horizon_È(-4.0f, -4.0f, -4.0f, 8, 8, 8, p_i46302_1_);
        (this.Â[1] = new ModelRenderer(this, 32, 0)).HorizonCode_Horizon_È(-4.0f, -4.0f, -4.0f, 6, 6, 6, p_i46302_1_);
        this.Â[1].Ý = -8.0f;
        this.Â[1].Ø­áŒŠá = 4.0f;
        (this.Â[2] = new ModelRenderer(this, 32, 0)).HorizonCode_Horizon_È(-4.0f, -4.0f, -4.0f, 6, 6, 6, p_i46302_1_);
        this.Â[2].Ý = 10.0f;
        this.Â[2].Ø­áŒŠá = 4.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        for (final ModelRenderer var11 : this.Â) {
            var11.HorizonCode_Horizon_È(p_78088_7_);
        }
        for (final ModelRenderer var11 : this.HorizonCode_Horizon_È) {
            var11.HorizonCode_Horizon_È(p_78088_7_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        final float var8 = MathHelper.Â(p_78087_3_ * 0.1f);
        this.HorizonCode_Horizon_È[1].Ó = (0.065f + 0.05f * var8) * 3.1415927f;
        this.HorizonCode_Horizon_È[2].HorizonCode_Horizon_È(-2.0f, 6.9f + MathHelper.Â(this.HorizonCode_Horizon_È[1].Ó) * 10.0f, -0.5f + MathHelper.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È[1].Ó) * 10.0f);
        this.HorizonCode_Horizon_È[2].Ó = (0.265f + 0.1f * var8) * 3.1415927f;
        this.Â[0].à = p_78087_4_ / 57.295776f;
        this.Â[0].Ó = p_78087_5_ / 57.295776f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_78086_1_, final float p_78086_2_, final float p_78086_3_, final float p_78086_4_) {
        final EntityWither var5 = (EntityWither)p_78086_1_;
        for (int var6 = 1; var6 < 3; ++var6) {
            this.Â[var6].à = (var5.HorizonCode_Horizon_È(var6 - 1) - p_78086_1_.¥É) / 57.295776f;
            this.Â[var6].Ó = var5.Â(var6 - 1) / 57.295776f;
        }
    }
}
