package HORIZON-6-0-SKIDPROTECTION;

public class ModelZombieVillager extends ModelBiped
{
    private static final String HorizonCode_Horizon_È = "CL_00000865";
    
    public ModelZombieVillager() {
        this(0.0f, 0.0f, false);
    }
    
    public ModelZombieVillager(final float p_i1165_1_, final float p_i1165_2_, final boolean p_i1165_3_) {
        super(p_i1165_1_, 0.0f, 64, p_i1165_3_ ? 32 : 64);
        if (p_i1165_3_) {
            (this.ÂµÈ = new ModelRenderer(this, 0, 0)).HorizonCode_Horizon_È(-4.0f, -10.0f, -4.0f, 8, 8, 8, p_i1165_1_);
            this.ÂµÈ.HorizonCode_Horizon_È(0.0f, 0.0f + p_i1165_2_, 0.0f);
        }
        else {
            (this.ÂµÈ = new ModelRenderer(this)).HorizonCode_Horizon_È(0.0f, 0.0f + p_i1165_2_, 0.0f);
            this.ÂµÈ.HorizonCode_Horizon_È(0, 32).HorizonCode_Horizon_È(-4.0f, -10.0f, -4.0f, 8, 10, 8, p_i1165_1_);
            this.ÂµÈ.HorizonCode_Horizon_È(24, 32).HorizonCode_Horizon_È(-1.0f, -3.0f, -6.0f, 2, 4, 2, p_i1165_1_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        super.HorizonCode_Horizon_È(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        final float var8 = MathHelper.HorizonCode_Horizon_È(this.Âµá€ * 3.1415927f);
        final float var9 = MathHelper.HorizonCode_Horizon_È((1.0f - (1.0f - this.Âµá€) * (1.0f - this.Âµá€)) * 3.1415927f);
        this.£á.Ø = 0.0f;
        this.Å.Ø = 0.0f;
        this.£á.à = -(0.1f - var8 * 0.6f);
        this.Å.à = 0.1f - var8 * 0.6f;
        this.£á.Ó = -1.5707964f;
        this.Å.Ó = -1.5707964f;
        final ModelRenderer £á = this.£á;
        £á.Ó -= var8 * 1.2f - var9 * 0.4f;
        final ModelRenderer å = this.Å;
        å.Ó -= var8 * 1.2f - var9 * 0.4f;
        final ModelRenderer £á2 = this.£á;
        £á2.Ø += MathHelper.Â(p_78087_3_ * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer å2 = this.Å;
        å2.Ø -= MathHelper.Â(p_78087_3_ * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer £á3 = this.£á;
        £á3.Ó += MathHelper.HorizonCode_Horizon_È(p_78087_3_ * 0.067f) * 0.05f;
        final ModelRenderer å3 = this.Å;
        å3.Ó -= MathHelper.HorizonCode_Horizon_È(p_78087_3_ * 0.067f) * 0.05f;
    }
}
