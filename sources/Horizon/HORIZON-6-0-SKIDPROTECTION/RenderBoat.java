package HORIZON-6-0-SKIDPROTECTION;

public class RenderBoat extends Render
{
    private static final ResourceLocation_1975012498 Âµá€;
    protected ModelBase HorizonCode_Horizon_È;
    private static final String Ó = "CL_00000981";
    
    static {
        Âµá€ = new ResourceLocation_1975012498("textures/entity/boat.png");
    }
    
    public RenderBoat(final RenderManager p_i46190_1_) {
        super(p_i46190_1_);
        this.HorizonCode_Horizon_È = new ModelBoat();
        this.Ý = 0.5f;
    }
    
    public void HorizonCode_Horizon_È(final EntityBoat p_180552_1_, final double p_180552_2_, final double p_180552_4_, final double p_180552_6_, final float p_180552_8_, final float p_180552_9_) {
        GlStateManager.Çªà¢();
        GlStateManager.Â((float)p_180552_2_, (float)p_180552_4_ + 0.25f, (float)p_180552_6_);
        GlStateManager.Â(180.0f - p_180552_8_, 0.0f, 1.0f, 0.0f);
        final float var10 = p_180552_1_.Ø() - p_180552_9_;
        float var11 = p_180552_1_.à() - p_180552_9_;
        if (var11 < 0.0f) {
            var11 = 0.0f;
        }
        if (var10 > 0.0f) {
            GlStateManager.Â(MathHelper.HorizonCode_Horizon_È(var10) * var10 * var11 / 10.0f * p_180552_1_.áŒŠÆ(), 1.0f, 0.0f, 0.0f);
        }
        final float var12 = 0.75f;
        GlStateManager.HorizonCode_Horizon_È(var12, var12, var12);
        GlStateManager.HorizonCode_Horizon_È(1.0f / var12, 1.0f / var12, 1.0f / var12);
        this.Ý(p_180552_1_);
        GlStateManager.HorizonCode_Horizon_È(-1.0f, -1.0f, 1.0f);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_180552_1_, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GlStateManager.Ê();
        super.HorizonCode_Horizon_È(p_180552_1_, p_180552_2_, p_180552_4_, p_180552_6_, p_180552_8_, p_180552_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityBoat p_180553_1_) {
        return RenderBoat.Âµá€;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityBoat)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityBoat)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
