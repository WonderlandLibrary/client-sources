package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class RenderEnderman extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private ModelEnderman Âµá€;
    private Random áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00000989";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/enderman/enderman.png");
    }
    
    public RenderEnderman(final RenderManager p_i46182_1_) {
        super(p_i46182_1_, new ModelEnderman(0.0f), 0.5f);
        this.áˆºÑ¢Õ = new Random();
        this.Âµá€ = (ModelEnderman)super.Ó;
        this.HorizonCode_Horizon_È(new LayerEndermanEyes(this));
        this.HorizonCode_Horizon_È(new LayerHeldBlock(this));
    }
    
    public void HorizonCode_Horizon_È(final EntityEnderman p_76986_1_, double p_76986_2_, final double p_76986_4_, double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.Âµá€.HorizonCode_Horizon_È = (p_76986_1_.ÇŽÅ().Ý().Ó() != Material.HorizonCode_Horizon_È);
        this.Âµá€.Â = p_76986_1_.¥Ðƒá();
        if (p_76986_1_.¥Ðƒá()) {
            final double var10 = 0.02;
            p_76986_2_ += this.áˆºÑ¢Õ.nextGaussian() * var10;
            p_76986_6_ += this.áˆºÑ¢Õ.nextGaussian() * var10;
        }
        super.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityEnderman p_180573_1_) {
        return RenderEnderman.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityEnderman)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityEnderman)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityEnderman)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityEnderman)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
