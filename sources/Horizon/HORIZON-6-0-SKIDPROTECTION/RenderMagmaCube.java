package HORIZON-6-0-SKIDPROTECTION;

public class RenderMagmaCube extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00001009";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/slime/magmacube.png");
    }
    
    public RenderMagmaCube(final RenderManager p_i46159_1_) {
        super(p_i46159_1_, new ModelMagmaCube(), 0.25f);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityMagmaCube p_110775_1_) {
        return RenderMagmaCube.HorizonCode_Horizon_È;
    }
    
    protected void HorizonCode_Horizon_È(final EntityMagmaCube p_77041_1_, final float p_77041_2_) {
        final int var3 = p_77041_1_.ÇŽÅ();
        final float var4 = (p_77041_1_.Ý + (p_77041_1_.Â - p_77041_1_.Ý) * p_77041_2_) / (var3 * 0.5f + 1.0f);
        final float var5 = 1.0f / (var4 + 1.0f);
        final float var6 = var3;
        GlStateManager.HorizonCode_Horizon_È(var5 * var6, 1.0f / var5 * var6, var5 * var6);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((EntityMagmaCube)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityMagmaCube)p_110775_1_);
    }
}
