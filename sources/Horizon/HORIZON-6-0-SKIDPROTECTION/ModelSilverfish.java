package HORIZON-6-0-SKIDPROTECTION;

public class ModelSilverfish extends ModelBase
{
    private ModelRenderer[] HorizonCode_Horizon_È;
    private ModelRenderer[] Â;
    private float[] Ý;
    private static final int[][] Ø­áŒŠá;
    private static final int[][] ÂµÈ;
    private static final String á = "CL_00000855";
    
    static {
        Ø­áŒŠá = new int[][] { { 3, 2, 2 }, { 4, 3, 2 }, { 6, 4, 3 }, { 3, 3, 3 }, { 2, 2, 3 }, { 2, 1, 2 }, { 1, 1, 2 } };
        ÂµÈ = new int[][] { new int[2], { 0, 4 }, { 0, 9 }, { 0, 16 }, { 0, 22 }, { 11, 0 }, { 13, 4 } };
    }
    
    public ModelSilverfish() {
        this.HorizonCode_Horizon_È = new ModelRenderer[7];
        this.Ý = new float[7];
        float var1 = -3.5f;
        for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.length; ++var2) {
            (this.HorizonCode_Horizon_È[var2] = new ModelRenderer(this, ModelSilverfish.ÂµÈ[var2][0], ModelSilverfish.ÂµÈ[var2][1])).HorizonCode_Horizon_È(ModelSilverfish.Ø­áŒŠá[var2][0] * -0.5f, 0.0f, ModelSilverfish.Ø­áŒŠá[var2][2] * -0.5f, ModelSilverfish.Ø­áŒŠá[var2][0], ModelSilverfish.Ø­áŒŠá[var2][1], ModelSilverfish.Ø­áŒŠá[var2][2]);
            this.HorizonCode_Horizon_È[var2].HorizonCode_Horizon_È(0.0f, 24 - ModelSilverfish.Ø­áŒŠá[var2][1], var1);
            this.Ý[var2] = var1;
            if (var2 < this.HorizonCode_Horizon_È.length - 1) {
                var1 += (ModelSilverfish.Ø­áŒŠá[var2][2] + ModelSilverfish.Ø­áŒŠá[var2 + 1][2]) * 0.5f;
            }
        }
        this.Â = new ModelRenderer[3];
        (this.Â[0] = new ModelRenderer(this, 20, 0)).HorizonCode_Horizon_È(-5.0f, 0.0f, ModelSilverfish.Ø­áŒŠá[2][2] * -0.5f, 10, 8, ModelSilverfish.Ø­áŒŠá[2][2]);
        this.Â[0].HorizonCode_Horizon_È(0.0f, 16.0f, this.Ý[2]);
        (this.Â[1] = new ModelRenderer(this, 20, 11)).HorizonCode_Horizon_È(-3.0f, 0.0f, ModelSilverfish.Ø­áŒŠá[4][2] * -0.5f, 6, 4, ModelSilverfish.Ø­áŒŠá[4][2]);
        this.Â[1].HorizonCode_Horizon_È(0.0f, 20.0f, this.Ý[4]);
        (this.Â[2] = new ModelRenderer(this, 20, 18)).HorizonCode_Horizon_È(-3.0f, 0.0f, ModelSilverfish.Ø­áŒŠá[4][2] * -0.5f, 6, 5, ModelSilverfish.Ø­áŒŠá[1][2]);
        this.Â[2].HorizonCode_Horizon_È(0.0f, 19.0f, this.Ý[1]);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        for (int var8 = 0; var8 < this.HorizonCode_Horizon_È.length; ++var8) {
            this.HorizonCode_Horizon_È[var8].HorizonCode_Horizon_È(p_78088_7_);
        }
        for (int var8 = 0; var8 < this.Â.length; ++var8) {
            this.Â[var8].HorizonCode_Horizon_È(p_78088_7_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        for (int var8 = 0; var8 < this.HorizonCode_Horizon_È.length; ++var8) {
            this.HorizonCode_Horizon_È[var8].à = MathHelper.Â(p_78087_3_ * 0.9f + var8 * 0.15f * 3.1415927f) * 3.1415927f * 0.05f * (1 + Math.abs(var8 - 2));
            this.HorizonCode_Horizon_È[var8].Ý = MathHelper.HorizonCode_Horizon_È(p_78087_3_ * 0.9f + var8 * 0.15f * 3.1415927f) * 3.1415927f * 0.2f * Math.abs(var8 - 2);
        }
        this.Â[0].à = this.HorizonCode_Horizon_È[2].à;
        this.Â[1].à = this.HorizonCode_Horizon_È[4].à;
        this.Â[1].Ý = this.HorizonCode_Horizon_È[4].Ý;
        this.Â[2].à = this.HorizonCode_Horizon_È[1].à;
        this.Â[2].Ý = this.HorizonCode_Horizon_È[1].Ý;
    }
}
