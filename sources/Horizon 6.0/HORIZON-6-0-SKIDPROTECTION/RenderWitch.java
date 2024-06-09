package HORIZON-6-0-SKIDPROTECTION;

public class RenderWitch extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00001033";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/witch.png");
    }
    
    public RenderWitch(final RenderManager p_i46131_1_) {
        super(p_i46131_1_, new ModelWitch(0.0f), 0.5f);
        this.HorizonCode_Horizon_È(new LayerHeldItemWitch(this));
    }
    
    public void HorizonCode_Horizon_È(final EntityWitch p_180590_1_, final double p_180590_2_, final double p_180590_4_, final double p_180590_6_, final float p_180590_8_, final float p_180590_9_) {
        ((ModelWitch)this.Ó).ˆÏ­ = (p_180590_1_.Çª() != null);
        super.HorizonCode_Horizon_È(p_180590_1_, p_180590_2_, p_180590_4_, p_180590_6_, p_180590_8_, p_180590_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityWitch p_180589_1_) {
        return RenderWitch.HorizonCode_Horizon_È;
    }
    
    @Override
    public void u_() {
        GlStateManager.Â(0.0f, 0.1875f, 0.0f);
    }
    
    protected void HorizonCode_Horizon_È(final EntityWitch p_77041_1_, final float p_77041_2_) {
        final float var3 = 0.9375f;
        GlStateManager.HorizonCode_Horizon_È(var3, var3, var3);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityWitch)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((EntityWitch)p_77041_1_, p_77041_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityWitch)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityWitch)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityWitch)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
