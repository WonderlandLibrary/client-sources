package HORIZON-6-0-SKIDPROTECTION;

public class RenderSlime extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00001024";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/slime/slime.png");
    }
    
    public RenderSlime(final RenderManager p_i46141_1_, final ModelBase p_i46141_2_, final float p_i46141_3_) {
        super(p_i46141_1_, p_i46141_2_, p_i46141_3_);
        this.HorizonCode_Horizon_È(new LayerSlimeGel(this));
    }
    
    public void HorizonCode_Horizon_È(final EntitySlime p_177124_1_, final double p_177124_2_, final double p_177124_4_, final double p_177124_6_, final float p_177124_8_, final float p_177124_9_) {
        this.Ý = 0.25f * p_177124_1_.ÇŽÅ();
        super.HorizonCode_Horizon_È(p_177124_1_, p_177124_2_, p_177124_4_, p_177124_6_, p_177124_8_, p_177124_9_);
    }
    
    protected void HorizonCode_Horizon_È(final EntitySlime p_77041_1_, final float p_77041_2_) {
        final float var3 = p_77041_1_.ÇŽÅ();
        final float var4 = (p_77041_1_.Ý + (p_77041_1_.Â - p_77041_1_.Ý) * p_77041_2_) / (var3 * 0.5f + 1.0f);
        final float var5 = 1.0f / (var4 + 1.0f);
        GlStateManager.HorizonCode_Horizon_È(var5 * var3, 1.0f / var5 * var3, var5 * var3);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntitySlime p_110775_1_) {
        return RenderSlime.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntitySlime)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((EntitySlime)p_77041_1_, p_77041_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntitySlime)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntitySlime)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntitySlime)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
