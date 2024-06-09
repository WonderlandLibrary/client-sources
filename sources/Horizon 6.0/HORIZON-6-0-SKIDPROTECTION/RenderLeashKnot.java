package HORIZON-6-0-SKIDPROTECTION;

public class RenderLeashKnot extends Render
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private ModelLeashKnot Âµá€;
    private static final String Ó = "CL_00001010";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/lead_knot.png");
    }
    
    public RenderLeashKnot(final RenderManager p_i46158_1_) {
        super(p_i46158_1_);
        this.Âµá€ = new ModelLeashKnot();
    }
    
    public void HorizonCode_Horizon_È(final EntityLeashKnot p_180559_1_, final double p_180559_2_, final double p_180559_4_, final double p_180559_6_, final float p_180559_8_, final float p_180559_9_) {
        GlStateManager.Çªà¢();
        GlStateManager.£à();
        GlStateManager.Â((float)p_180559_2_, (float)p_180559_4_, (float)p_180559_6_);
        final float var10 = 0.0625f;
        GlStateManager.ŠÄ();
        GlStateManager.HorizonCode_Horizon_È(-1.0f, -1.0f, 1.0f);
        GlStateManager.Ø­áŒŠá();
        this.Ý(p_180559_1_);
        this.Âµá€.HorizonCode_Horizon_È(p_180559_1_, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, var10);
        GlStateManager.Ê();
        super.HorizonCode_Horizon_È(p_180559_1_, p_180559_2_, p_180559_4_, p_180559_6_, p_180559_8_, p_180559_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityLeashKnot p_110775_1_) {
        return RenderLeashKnot.HorizonCode_Horizon_È;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityLeashKnot)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityLeashKnot)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
