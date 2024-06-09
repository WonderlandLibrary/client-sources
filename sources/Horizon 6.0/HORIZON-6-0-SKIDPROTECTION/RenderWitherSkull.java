package HORIZON-6-0-SKIDPROTECTION;

public class RenderWitherSkull extends Render
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Âµá€;
    private final ModelSkeletonHead Ó;
    private static final String à = "CL_00001035";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/wither/wither_invulnerable.png");
        Âµá€ = new ResourceLocation_1975012498("textures/entity/wither/wither.png");
    }
    
    public RenderWitherSkull(final RenderManager p_i46129_1_) {
        super(p_i46129_1_);
        this.Ó = new ModelSkeletonHead();
    }
    
    private float HorizonCode_Horizon_È(final float p_82400_1_, final float p_82400_2_, final float p_82400_3_) {
        float var4;
        for (var4 = p_82400_2_ - p_82400_1_; var4 < -180.0f; var4 += 360.0f) {}
        while (var4 >= 180.0f) {
            var4 -= 360.0f;
        }
        return p_82400_1_ + p_82400_3_ * var4;
    }
    
    public void HorizonCode_Horizon_È(final EntityWitherSkull p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GlStateManager.Çªà¢();
        GlStateManager.£à();
        final float var10 = this.HorizonCode_Horizon_È(p_76986_1_.á€, p_76986_1_.É, p_76986_9_);
        final float var11 = p_76986_1_.Õ + (p_76986_1_.áƒ - p_76986_1_.Õ) * p_76986_9_;
        GlStateManager.Â((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        final float var12 = 0.0625f;
        GlStateManager.ŠÄ();
        GlStateManager.HorizonCode_Horizon_È(-1.0f, -1.0f, 1.0f);
        GlStateManager.Ø­áŒŠá();
        this.Ý(p_76986_1_);
        this.Ó.HorizonCode_Horizon_È(p_76986_1_, 0.0f, 0.0f, 0.0f, var10, var11, var12);
        GlStateManager.Ê();
        super.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityWitherSkull p_180564_1_) {
        return p_180564_1_.Ø() ? RenderWitherSkull.HorizonCode_Horizon_È : RenderWitherSkull.Âµá€;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityWitherSkull)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityWitherSkull)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
