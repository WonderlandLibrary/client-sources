package HORIZON-6-0-SKIDPROTECTION;

public class ModelEnderMite extends ModelBase
{
    private static final int[][] HorizonCode_Horizon_È;
    private static final int[][] Â;
    private static final int Ý;
    private final ModelRenderer[] Ø­áŒŠá;
    private static final String ÂµÈ = "CL_00002629";
    
    static {
        HorizonCode_Horizon_È = new int[][] { { 4, 3, 2 }, { 6, 4, 5 }, { 3, 3, 1 }, { 1, 2, 1 } };
        Â = new int[][] { new int[2], { 0, 5 }, { 0, 14 }, { 0, 18 } };
        Ý = ModelEnderMite.HorizonCode_Horizon_È.length;
    }
    
    public ModelEnderMite() {
        this.Ø­áŒŠá = new ModelRenderer[ModelEnderMite.Ý];
        float var1 = -3.5f;
        for (int var2 = 0; var2 < this.Ø­áŒŠá.length; ++var2) {
            (this.Ø­áŒŠá[var2] = new ModelRenderer(this, ModelEnderMite.Â[var2][0], ModelEnderMite.Â[var2][1])).HorizonCode_Horizon_È(ModelEnderMite.HorizonCode_Horizon_È[var2][0] * -0.5f, 0.0f, ModelEnderMite.HorizonCode_Horizon_È[var2][2] * -0.5f, ModelEnderMite.HorizonCode_Horizon_È[var2][0], ModelEnderMite.HorizonCode_Horizon_È[var2][1], ModelEnderMite.HorizonCode_Horizon_È[var2][2]);
            this.Ø­áŒŠá[var2].HorizonCode_Horizon_È(0.0f, 24 - ModelEnderMite.HorizonCode_Horizon_È[var2][1], var1);
            if (var2 < this.Ø­áŒŠá.length - 1) {
                var1 += (ModelEnderMite.HorizonCode_Horizon_È[var2][2] + ModelEnderMite.HorizonCode_Horizon_È[var2 + 1][2]) * 0.5f;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        for (int var8 = 0; var8 < this.Ø­áŒŠá.length; ++var8) {
            this.Ø­áŒŠá[var8].HorizonCode_Horizon_È(p_78088_7_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        for (int var8 = 0; var8 < this.Ø­áŒŠá.length; ++var8) {
            this.Ø­áŒŠá[var8].à = MathHelper.Â(p_78087_3_ * 0.9f + var8 * 0.15f * 3.1415927f) * 3.1415927f * 0.01f * (1 + Math.abs(var8 - 2));
            this.Ø­áŒŠá[var8].Ý = MathHelper.HorizonCode_Horizon_È(p_78087_3_ * 0.9f + var8 * 0.15f * 3.1415927f) * 3.1415927f * 0.1f * Math.abs(var8 - 2);
        }
    }
}
