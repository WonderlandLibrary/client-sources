package HORIZON-6-0-SKIDPROTECTION;

public class ModelZombie extends ModelBiped
{
    private static final String HorizonCode_Horizon_È = "CL_00000869";
    
    public ModelZombie() {
        this(0.0f, false);
    }
    
    protected ModelZombie(final float p_i1167_1_, final float p_i1167_2_, final int p_i1167_3_, final int p_i1167_4_) {
        super(p_i1167_1_, p_i1167_2_, p_i1167_3_, p_i1167_4_);
    }
    
    public ModelZombie(final float p_i1168_1_, final boolean p_i1168_2_) {
        super(p_i1168_1_, 0.0f, 64, p_i1168_2_ ? 32 : 64);
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
