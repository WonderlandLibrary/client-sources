package HORIZON-6-0-SKIDPROTECTION;

public class RenderGhast extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Âµá€;
    private static final String áˆºÑ¢Õ = "CL_00000997";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/ghast/ghast.png");
        Âµá€ = new ResourceLocation_1975012498("textures/entity/ghast/ghast_shooting.png");
    }
    
    public RenderGhast(final RenderManager p_i46174_1_) {
        super(p_i46174_1_, new ModelGhast(), 0.5f);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityGhast p_180576_1_) {
        return p_180576_1_.Ø() ? RenderGhast.Âµá€ : RenderGhast.HorizonCode_Horizon_È;
    }
    
    protected void HorizonCode_Horizon_È(final EntityGhast p_77041_1_, final float p_77041_2_) {
        final float var3 = 1.0f;
        final float var4 = (8.0f + var3) / 2.0f;
        final float var5 = (8.0f + 1.0f / var3) / 2.0f;
        GlStateManager.HorizonCode_Horizon_È(var5, var4, var5);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((EntityGhast)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityGhast)p_110775_1_);
    }
}
