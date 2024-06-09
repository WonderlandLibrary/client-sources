package HORIZON-6-0-SKIDPROTECTION;

public class RenderWolf extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Âµá€;
    private static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00001036";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/wolf/wolf.png");
        Âµá€ = new ResourceLocation_1975012498("textures/entity/wolf/wolf_tame.png");
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/entity/wolf/wolf_angry.png");
    }
    
    public RenderWolf(final RenderManager p_i46128_1_, final ModelBase p_i46128_2_, final float p_i46128_3_) {
        super(p_i46128_1_, p_i46128_2_, p_i46128_3_);
        this.HorizonCode_Horizon_È(new LayerWolfCollar(this));
    }
    
    protected float HorizonCode_Horizon_È(final EntityWolf p_180593_1_, final float p_180593_2_) {
        return p_180593_1_.áˆºÉ();
    }
    
    public void HorizonCode_Horizon_È(final EntityWolf p_177135_1_, final double p_177135_2_, final double p_177135_4_, final double p_177135_6_, final float p_177135_8_, final float p_177135_9_) {
        if (p_177135_1_.ÐƒÇŽà()) {
            final float var10 = p_177135_1_.Â(p_177135_9_) * p_177135_1_.£á(p_177135_9_);
            GlStateManager.Ý(var10, var10, var10);
        }
        super.HorizonCode_Horizon_È(p_177135_1_, p_177135_2_, p_177135_4_, p_177135_6_, p_177135_8_, p_177135_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityWolf p_110775_1_) {
        return p_110775_1_.ÐƒÓ() ? RenderWolf.Âµá€ : (p_110775_1_.Ø­È() ? RenderWolf.áˆºÑ¢Õ : RenderWolf.HorizonCode_Horizon_È);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityWolf)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected float Â(final EntityLivingBase p_77044_1_, final float p_77044_2_) {
        return this.HorizonCode_Horizon_È((EntityWolf)p_77044_1_, p_77044_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityWolf)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityWolf)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityWolf)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
