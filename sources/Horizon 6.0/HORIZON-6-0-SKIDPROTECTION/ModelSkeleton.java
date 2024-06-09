package HORIZON-6-0-SKIDPROTECTION;

public class ModelSkeleton extends ModelZombie
{
    private static final String HorizonCode_Horizon_È = "CL_00000857";
    
    public ModelSkeleton() {
        this(0.0f, false);
    }
    
    public ModelSkeleton(final float p_i46303_1_, final boolean p_i46303_2_) {
        super(p_i46303_1_, 0.0f, 64, 32);
        if (!p_i46303_2_) {
            (this.£á = new ModelRenderer(this, 40, 16)).HorizonCode_Horizon_È(-1.0f, -2.0f, -1.0f, 2, 12, 2, p_i46303_1_);
            this.£á.HorizonCode_Horizon_È(-5.0f, 2.0f, 0.0f);
            this.Å = new ModelRenderer(this, 40, 16);
            this.Å.áŒŠÆ = true;
            this.Å.HorizonCode_Horizon_È(-1.0f, -2.0f, -1.0f, 2, 12, 2, p_i46303_1_);
            this.Å.HorizonCode_Horizon_È(5.0f, 2.0f, 0.0f);
            (this.£à = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 2, 12, 2, p_i46303_1_);
            this.£à.HorizonCode_Horizon_È(-2.0f, 12.0f, 0.0f);
            this.µà = new ModelRenderer(this, 0, 16);
            this.µà.áŒŠÆ = true;
            this.µà.HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 2, 12, 2, p_i46303_1_);
            this.µà.HorizonCode_Horizon_È(2.0f, 12.0f, 0.0f);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_78086_1_, final float p_78086_2_, final float p_78086_3_, final float p_78086_4_) {
        this.µÕ = (((EntitySkeleton)p_78086_1_).ÇŽÅ() == 1);
        super.HorizonCode_Horizon_È(p_78086_1_, p_78086_2_, p_78086_3_, p_78086_4_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        super.HorizonCode_Horizon_È(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
    }
}
