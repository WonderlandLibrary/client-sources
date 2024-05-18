package HORIZON-6-0-SKIDPROTECTION;

public class ModelHumanoidHead extends ModelSkeletonHead
{
    private final ModelRenderer Â;
    private static final String Ý = "CL_00002627";
    
    public ModelHumanoidHead() {
        super(0, 0, 64, 64);
        (this.Â = new ModelRenderer(this, 32, 0)).HorizonCode_Horizon_È(-4.0f, -8.0f, -4.0f, 8, 8, 8, 0.25f);
        this.Â.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        super.HorizonCode_Horizon_È(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
        this.Â.HorizonCode_Horizon_È(p_78088_7_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        super.HorizonCode_Horizon_È(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        this.Â.à = this.HorizonCode_Horizon_È.à;
        this.Â.Ó = this.HorizonCode_Horizon_È.Ó;
    }
}
