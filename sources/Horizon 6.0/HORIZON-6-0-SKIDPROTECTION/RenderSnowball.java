package HORIZON-6-0-SKIDPROTECTION;

public class RenderSnowball extends Render
{
    protected final Item_1028566121 HorizonCode_Horizon_È;
    private final RenderItem Âµá€;
    private static final String Ó = "CL_00001008";
    
    public RenderSnowball(final RenderManager p_i46137_1_, final Item_1028566121 p_i46137_2_, final RenderItem p_i46137_3_) {
        super(p_i46137_1_);
        this.HorizonCode_Horizon_È = p_i46137_2_;
        this.Âµá€ = p_i46137_3_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GlStateManager.Çªà¢();
        GlStateManager.Â((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GlStateManager.ŠÄ();
        GlStateManager.HorizonCode_Horizon_È(0.5f, 0.5f, 0.5f);
        GlStateManager.Â(-RenderManager.Ø, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(RenderManager.áŒŠÆ, 1.0f, 0.0f, 0.0f);
        this.HorizonCode_Horizon_È(TextureMap.à);
        this.Âµá€.Â(this.Ø­áŒŠá(p_76986_1_));
        GlStateManager.Ñ¢á();
        GlStateManager.Ê();
        super.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    public ItemStack Ø­áŒŠá(final Entity p_177082_1_) {
        return new ItemStack(this.HorizonCode_Horizon_È, 1, 0);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return TextureMap.à;
    }
}
