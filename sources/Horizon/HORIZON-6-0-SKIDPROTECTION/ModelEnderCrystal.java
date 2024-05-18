package HORIZON-6-0-SKIDPROTECTION;

public class ModelEnderCrystal extends ModelBase
{
    private ModelRenderer HorizonCode_Horizon_È;
    private ModelRenderer Â;
    private ModelRenderer Ý;
    private static final String Ø­áŒŠá = "CL_00000871";
    
    public ModelEnderCrystal(final float p_i1170_1_, final boolean p_i1170_2_) {
        this.Â = new ModelRenderer(this, "glass");
        this.Â.HorizonCode_Horizon_È(0, 0).HorizonCode_Horizon_È(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.HorizonCode_Horizon_È = new ModelRenderer(this, "cube");
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(32, 0).HorizonCode_Horizon_È(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        if (p_i1170_2_) {
            this.Ý = new ModelRenderer(this, "base");
            this.Ý.HorizonCode_Horizon_È(0, 16).HorizonCode_Horizon_È(-6.0f, 0.0f, -6.0f, 12, 4, 12);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        GlStateManager.Çªà¢();
        GlStateManager.HorizonCode_Horizon_È(2.0f, 2.0f, 2.0f);
        GlStateManager.Â(0.0f, -0.5f, 0.0f);
        if (this.Ý != null) {
            this.Ý.HorizonCode_Horizon_È(p_78088_7_);
        }
        GlStateManager.Â(p_78088_3_, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(0.0f, 0.8f + p_78088_4_, 0.0f);
        GlStateManager.Â(60.0f, 0.7071f, 0.0f, 0.7071f);
        this.Â.HorizonCode_Horizon_È(p_78088_7_);
        final float var8 = 0.875f;
        GlStateManager.HorizonCode_Horizon_È(var8, var8, var8);
        GlStateManager.Â(60.0f, 0.7071f, 0.0f, 0.7071f);
        GlStateManager.Â(p_78088_3_, 0.0f, 1.0f, 0.0f);
        this.Â.HorizonCode_Horizon_È(p_78088_7_);
        GlStateManager.HorizonCode_Horizon_È(var8, var8, var8);
        GlStateManager.Â(60.0f, 0.7071f, 0.0f, 0.7071f);
        GlStateManager.Â(p_78088_3_, 0.0f, 1.0f, 0.0f);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
        GlStateManager.Ê();
    }
}
