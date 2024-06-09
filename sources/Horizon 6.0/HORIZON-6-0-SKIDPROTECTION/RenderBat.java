package HORIZON-6-0-SKIDPROTECTION;

public class RenderBat extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00000979";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/bat.png");
    }
    
    public RenderBat(final RenderManager p_i46192_1_) {
        super(p_i46192_1_, new ModelBat(), 0.25f);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityBat p_180566_1_) {
        return RenderBat.HorizonCode_Horizon_È;
    }
    
    protected void HorizonCode_Horizon_È(final EntityBat p_180567_1_, final float p_180567_2_) {
        GlStateManager.HorizonCode_Horizon_È(0.35f, 0.35f, 0.35f);
    }
    
    protected void HorizonCode_Horizon_È(final EntityBat p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        if (!p_77043_1_.Ø()) {
            GlStateManager.Â(0.0f, MathHelper.Â(p_77043_2_ * 0.3f) * 0.1f, 0.0f);
        }
        else {
            GlStateManager.Â(0.0f, -0.1f, 0.0f);
        }
        super.HorizonCode_Horizon_È(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((EntityBat)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        this.HorizonCode_Horizon_È((EntityBat)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityBat)p_110775_1_);
    }
}
