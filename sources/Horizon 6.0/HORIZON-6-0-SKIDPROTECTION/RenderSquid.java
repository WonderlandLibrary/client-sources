package HORIZON-6-0-SKIDPROTECTION;

public class RenderSquid extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00001028";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/squid.png");
    }
    
    public RenderSquid(final RenderManager p_i46138_1_, final ModelBase p_i46138_2_, final float p_i46138_3_) {
        super(p_i46138_1_, p_i46138_2_, p_i46138_3_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntitySquid p_110775_1_) {
        return RenderSquid.HorizonCode_Horizon_È;
    }
    
    protected void HorizonCode_Horizon_È(final EntitySquid p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        final float var5 = p_77043_1_.Â + (p_77043_1_.HorizonCode_Horizon_È - p_77043_1_.Â) * p_77043_4_;
        final float var6 = p_77043_1_.Ø­áŒŠá + (p_77043_1_.Ý - p_77043_1_.Ø­áŒŠá) * p_77043_4_;
        GlStateManager.Â(0.0f, 0.5f, 0.0f);
        GlStateManager.Â(180.0f - p_77043_3_, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(var5, 1.0f, 0.0f, 0.0f);
        GlStateManager.Â(var6, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(0.0f, -1.2f, 0.0f);
    }
    
    protected float HorizonCode_Horizon_È(final EntitySquid p_77044_1_, final float p_77044_2_) {
        return p_77044_1_.Ï­Ï + (p_77044_1_.ŒÂ - p_77044_1_.Ï­Ï) * p_77044_2_;
    }
    
    @Override
    protected float Â(final EntityLivingBase p_77044_1_, final float p_77044_2_) {
        return this.HorizonCode_Horizon_È((EntitySquid)p_77044_1_, p_77044_2_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        this.HorizonCode_Horizon_È((EntitySquid)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntitySquid)p_110775_1_);
    }
}
