package HORIZON-6-0-SKIDPROTECTION;

public class ModelSkeletonHead extends ModelBase
{
    public ModelRenderer HorizonCode_Horizon_È;
    private static final String Â = "CL_00000856";
    
    public ModelSkeletonHead() {
        this(0, 35, 64, 64);
    }
    
    public ModelSkeletonHead(final int p_i1155_1_, final int p_i1155_2_, final int p_i1155_3_, final int p_i1155_4_) {
        this.áŒŠÆ = p_i1155_3_;
        this.áˆºÑ¢Õ = p_i1155_4_;
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, p_i1155_1_, p_i1155_2_)).HorizonCode_Horizon_È(-4.0f, -8.0f, -4.0f, 8, 8, 8, 0.0f);
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
