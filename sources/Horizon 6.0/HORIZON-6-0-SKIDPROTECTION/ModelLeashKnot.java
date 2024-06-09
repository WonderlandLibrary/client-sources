package HORIZON-6-0-SKIDPROTECTION;

public class ModelLeashKnot extends ModelBase
{
    public ModelRenderer HorizonCode_Horizon_È;
    private static final String Â = "CL_00000843";
    
    public ModelLeashKnot() {
        this(0, 0, 32, 32);
    }
    
    public ModelLeashKnot(final int p_i46365_1_, final int p_i46365_2_, final int p_i46365_3_, final int p_i46365_4_) {
        this.áŒŠÆ = p_i46365_3_;
        this.áˆºÑ¢Õ = p_i46365_4_;
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, p_i46365_1_, p_i46365_2_)).HorizonCode_Horizon_È(-3.0f, -6.0f, -3.0f, 6, 8, 6, 0.0f);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        super.HorizonCode_Horizon_È(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        this.HorizonCode_Horizon_È.à = p_78087_4_ / 57.295776f;
        this.HorizonCode_Horizon_È.Ó = p_78087_5_ / 57.295776f;
    }
}
