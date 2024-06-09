package HORIZON-6-0-SKIDPROTECTION;

public class RenderEntity extends Render
{
    private static final String HorizonCode_Horizon_È = "CL_00000986";
    
    public RenderEntity(final RenderManager p_i46185_1_) {
        super(p_i46185_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GlStateManager.Çªà¢();
        Render.HorizonCode_Horizon_È(p_76986_1_.£É(), p_76986_2_ - p_76986_1_.áˆºáˆºÈ, p_76986_4_ - p_76986_1_.ÇŽá€, p_76986_6_ - p_76986_1_.Ï);
        GlStateManager.Ê();
        super.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return null;
    }
}
