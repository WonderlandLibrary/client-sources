package HORIZON-6-0-SKIDPROTECTION;

public class RenderWither extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Âµá€;
    private static final String áˆºÑ¢Õ = "CL_00001034";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/wither/wither_invulnerable.png");
        Âµá€ = new ResourceLocation_1975012498("textures/entity/wither/wither.png");
    }
    
    public RenderWither(final RenderManager p_i46130_1_) {
        super(p_i46130_1_, new ModelWither(0.0f), 1.0f);
        this.HorizonCode_Horizon_È(new LayerWitherAura(this));
    }
    
    public void HorizonCode_Horizon_È(final EntityWither p_180591_1_, final double p_180591_2_, final double p_180591_4_, final double p_180591_6_, final float p_180591_8_, final float p_180591_9_) {
        BossStatus.HorizonCode_Horizon_È(p_180591_1_, true);
        super.HorizonCode_Horizon_È(p_180591_1_, p_180591_2_, p_180591_4_, p_180591_6_, p_180591_8_, p_180591_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityWither p_110775_1_) {
        final int var2 = p_110775_1_.ÇŽ();
        return (var2 > 0 && (var2 > 80 || var2 / 5 % 2 != 1)) ? RenderWither.HorizonCode_Horizon_È : RenderWither.Âµá€;
    }
    
    protected void HorizonCode_Horizon_È(final EntityWither p_180592_1_, final float p_180592_2_) {
        float var3 = 2.0f;
        final int var4 = p_180592_1_.ÇŽ();
        if (var4 > 0) {
            var3 -= (var4 - p_180592_2_) / 220.0f * 0.5f;
        }
        GlStateManager.HorizonCode_Horizon_È(var3, var3, var3);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityWither)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((EntityWither)p_77041_1_, p_77041_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityWither)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityWither)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityWither)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
