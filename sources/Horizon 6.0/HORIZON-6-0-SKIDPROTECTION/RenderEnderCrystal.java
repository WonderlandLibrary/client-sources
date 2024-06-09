package HORIZON-6-0-SKIDPROTECTION;

public class RenderEnderCrystal extends Render
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private ModelBase Âµá€;
    private static final String Ó = "CL_00000987";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/endercrystal/endercrystal.png");
    }
    
    public RenderEnderCrystal(final RenderManager p_i46184_1_) {
        super(p_i46184_1_);
        this.Âµá€ = new ModelEnderCrystal(0.0f, true);
        this.Ý = 0.5f;
    }
    
    public void HorizonCode_Horizon_È(final EntityEnderCrystal p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        final float var10 = p_76986_1_.HorizonCode_Horizon_È + p_76986_9_;
        GlStateManager.Çªà¢();
        GlStateManager.Â((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        this.HorizonCode_Horizon_È(RenderEnderCrystal.HorizonCode_Horizon_È);
        float var11 = MathHelper.HorizonCode_Horizon_È(var10 * 0.2f) / 2.0f + 0.5f;
        var11 += var11 * var11;
        this.Âµá€.HorizonCode_Horizon_È(p_76986_1_, 0.0f, var10 * 3.0f, var11 * 0.2f, 0.0f, 0.0f, 0.0625f);
        GlStateManager.Ê();
        super.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityEnderCrystal p_180554_1_) {
        return RenderEnderCrystal.HorizonCode_Horizon_È;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityEnderCrystal)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityEnderCrystal)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
