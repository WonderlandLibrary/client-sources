package HORIZON-6-0-SKIDPROTECTION;

public class ModelSquid extends ModelBase
{
    ModelRenderer HorizonCode_Horizon_È;
    ModelRenderer[] Â;
    private static final String Ý = "CL_00000861";
    
    public ModelSquid() {
        this.Â = new ModelRenderer[8];
        final byte var1 = -16;
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, 0, 0)).HorizonCode_Horizon_È(-6.0f, -8.0f, -6.0f, 12, 16, 12);
        final ModelRenderer horizonCode_Horizon_È = this.HorizonCode_Horizon_È;
        horizonCode_Horizon_È.Ø­áŒŠá += 24 + var1;
        for (int var2 = 0; var2 < this.Â.length; ++var2) {
            this.Â[var2] = new ModelRenderer(this, 48, 0);
            double var3 = var2 * 3.141592653589793 * 2.0 / this.Â.length;
            final float var4 = (float)Math.cos(var3) * 5.0f;
            final float var5 = (float)Math.sin(var3) * 5.0f;
            this.Â[var2].HorizonCode_Horizon_È(-1.0f, 0.0f, -1.0f, 2, 18, 2);
            this.Â[var2].Ý = var4;
            this.Â[var2].Âµá€ = var5;
            this.Â[var2].Ø­áŒŠá = 31 + var1;
            var3 = var2 * 3.141592653589793 * -2.0 / this.Â.length + 1.5707963267948966;
            this.Â[var2].à = (float)var3;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        for (final ModelRenderer var11 : this.Â) {
            var11.Ó = p_78087_3_;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
        for (int var8 = 0; var8 < this.Â.length; ++var8) {
            this.Â[var8].HorizonCode_Horizon_È(p_78088_7_);
        }
    }
}
