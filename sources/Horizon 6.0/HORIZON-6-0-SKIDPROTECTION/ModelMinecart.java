package HORIZON-6-0-SKIDPROTECTION;

public class ModelMinecart extends ModelBase
{
    public ModelRenderer[] HorizonCode_Horizon_È;
    private static final String Â = "CL_00000844";
    
    public ModelMinecart() {
        (this.HorizonCode_Horizon_È = new ModelRenderer[7])[0] = new ModelRenderer(this, 0, 10);
        this.HorizonCode_Horizon_È[1] = new ModelRenderer(this, 0, 0);
        this.HorizonCode_Horizon_È[2] = new ModelRenderer(this, 0, 0);
        this.HorizonCode_Horizon_È[3] = new ModelRenderer(this, 0, 0);
        this.HorizonCode_Horizon_È[4] = new ModelRenderer(this, 0, 0);
        this.HorizonCode_Horizon_È[5] = new ModelRenderer(this, 44, 10);
        final byte var1 = 20;
        final byte var2 = 8;
        final byte var3 = 16;
        final byte var4 = 4;
        this.HorizonCode_Horizon_È[0].HorizonCode_Horizon_È(-var1 / 2, -var3 / 2, -1.0f, var1, var3, 2, 0.0f);
        this.HorizonCode_Horizon_È[0].HorizonCode_Horizon_È(0.0f, var4, 0.0f);
        this.HorizonCode_Horizon_È[5].HorizonCode_Horizon_È(-var1 / 2 + 1, -var3 / 2 + 1, -1.0f, var1 - 2, var3 - 2, 1, 0.0f);
        this.HorizonCode_Horizon_È[5].HorizonCode_Horizon_È(0.0f, var4, 0.0f);
        this.HorizonCode_Horizon_È[1].HorizonCode_Horizon_È(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.HorizonCode_Horizon_È[1].HorizonCode_Horizon_È(-var1 / 2 + 1, var4, 0.0f);
        this.HorizonCode_Horizon_È[2].HorizonCode_Horizon_È(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.HorizonCode_Horizon_È[2].HorizonCode_Horizon_È(var1 / 2 - 1, var4, 0.0f);
        this.HorizonCode_Horizon_È[3].HorizonCode_Horizon_È(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.HorizonCode_Horizon_È[3].HorizonCode_Horizon_È(0.0f, var4, -var3 / 2 + 1);
        this.HorizonCode_Horizon_È[4].HorizonCode_Horizon_È(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.HorizonCode_Horizon_È[4].HorizonCode_Horizon_È(0.0f, var4, var3 / 2 - 1);
        this.HorizonCode_Horizon_È[0].Ó = 1.5707964f;
        this.HorizonCode_Horizon_È[1].à = 4.712389f;
        this.HorizonCode_Horizon_È[2].à = 1.5707964f;
        this.HorizonCode_Horizon_È[3].à = 3.1415927f;
        this.HorizonCode_Horizon_È[5].Ó = -1.5707964f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È[5].Ø­áŒŠá = 4.0f - p_78088_4_;
        for (int var8 = 0; var8 < 6; ++var8) {
            this.HorizonCode_Horizon_È[var8].HorizonCode_Horizon_È(p_78088_7_);
        }
    }
}
