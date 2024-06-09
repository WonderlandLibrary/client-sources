package HORIZON-6-0-SKIDPROTECTION;

public class ModelWitch extends ModelVillager
{
    public boolean ˆÏ­;
    private ModelRenderer £á;
    private ModelRenderer Å;
    private static final String £à = "CL_00000866";
    
    public ModelWitch(final float p_i46361_1_) {
        super(p_i46361_1_, 0.0f, 64, 128);
        (this.£á = new ModelRenderer(this).Â(64, 128)).HorizonCode_Horizon_È(0.0f, -2.0f, 0.0f);
        this.£á.HorizonCode_Horizon_È(0, 0).HorizonCode_Horizon_È(0.0f, 3.0f, -6.75f, 1, 1, 1, -0.25f);
        this.á.HorizonCode_Horizon_È(this.£á);
        (this.Å = new ModelRenderer(this).Â(64, 128)).HorizonCode_Horizon_È(-5.0f, -10.03125f, -5.0f);
        this.Å.HorizonCode_Horizon_È(0, 64).HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 10, 2, 10);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Å);
        final ModelRenderer var2 = new ModelRenderer(this).Â(64, 128);
        var2.HorizonCode_Horizon_È(1.75f, -4.0f, 2.0f);
        var2.HorizonCode_Horizon_È(0, 76).HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 7, 4, 7);
        var2.Ó = -0.05235988f;
        var2.Ø = 0.02617994f;
        this.Å.HorizonCode_Horizon_È(var2);
        final ModelRenderer var3 = new ModelRenderer(this).Â(64, 128);
        var3.HorizonCode_Horizon_È(1.75f, -4.0f, 2.0f);
        var3.HorizonCode_Horizon_È(0, 87).HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 4, 4, 4);
        var3.Ó = -0.10471976f;
        var3.Ø = 0.05235988f;
        var2.HorizonCode_Horizon_È(var3);
        final ModelRenderer var4 = new ModelRenderer(this).Â(64, 128);
        var4.HorizonCode_Horizon_È(1.75f, -2.0f, 2.0f);
        var4.HorizonCode_Horizon_È(0, 95).HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1, 2, 1, 0.25f);
        var4.Ó = -0.20943952f;
        var4.Ø = 0.10471976f;
        var3.HorizonCode_Horizon_È(var4);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        super.HorizonCode_Horizon_È(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        final ModelRenderer á = this.á;
        final ModelRenderer á2 = this.á;
        final ModelRenderer á3 = this.á;
        final float å = 0.0f;
        á3.µà = å;
        á2.£à = å;
        á.Å = å;
        final float var8 = 0.01f * (p_78087_7_.ˆá() % 10);
        this.á.Ó = MathHelper.HorizonCode_Horizon_È(p_78087_7_.Œ * var8) * 4.5f * 3.1415927f / 180.0f;
        this.á.à = 0.0f;
        this.á.Ø = MathHelper.Â(p_78087_7_.Œ * var8) * 2.5f * 3.1415927f / 180.0f;
        if (this.ˆÏ­) {
            this.á.Ó = -0.9f;
            this.á.µà = -0.09375f;
            this.á.£à = 0.1875f;
        }
    }
}
