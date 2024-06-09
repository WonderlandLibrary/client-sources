package HORIZON-6-0-SKIDPROTECTION;

public class ModelSlime extends ModelBase
{
    ModelRenderer HorizonCode_Horizon_È;
    ModelRenderer Â;
    ModelRenderer Ý;
    ModelRenderer Ø­áŒŠá;
    private static final String ÂµÈ = "CL_00000858";
    
    public ModelSlime(final int p_i1157_1_) {
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, 0, p_i1157_1_)).HorizonCode_Horizon_È(-4.0f, 16.0f, -4.0f, 8, 8, 8);
        if (p_i1157_1_ > 0) {
            (this.HorizonCode_Horizon_È = new ModelRenderer(this, 0, p_i1157_1_)).HorizonCode_Horizon_È(-3.0f, 17.0f, -3.0f, 6, 6, 6);
            (this.Â = new ModelRenderer(this, 32, 0)).HorizonCode_Horizon_È(-3.25f, 18.0f, -3.5f, 2, 2, 2);
            (this.Ý = new ModelRenderer(this, 32, 4)).HorizonCode_Horizon_È(1.25f, 18.0f, -3.5f, 2, 2, 2);
            (this.Ø­áŒŠá = new ModelRenderer(this, 32, 8)).HorizonCode_Horizon_È(0.0f, 21.0f, -3.5f, 1, 1, 1);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
        if (this.Â != null) {
            this.Â.HorizonCode_Horizon_È(p_78088_7_);
            this.Ý.HorizonCode_Horizon_È(p_78088_7_);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
        }
    }
}
