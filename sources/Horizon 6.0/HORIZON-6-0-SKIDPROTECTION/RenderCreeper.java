package HORIZON-6-0-SKIDPROTECTION;

public class RenderCreeper extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00000985";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/creeper/creeper.png");
    }
    
    public RenderCreeper(final RenderManager p_i46186_1_) {
        super(p_i46186_1_, new ModelCreeper(), 0.5f);
        this.HorizonCode_Horizon_È(new LayerCreeperCharge(this));
    }
    
    protected void HorizonCode_Horizon_È(final EntityCreeper p_180570_1_, final float p_180570_2_) {
        float var3 = p_180570_1_.Ý(p_180570_2_);
        final float var4 = 1.0f + MathHelper.HorizonCode_Horizon_È(var3 * 100.0f) * var3 * 0.01f;
        var3 = MathHelper.HorizonCode_Horizon_È(var3, 0.0f, 1.0f);
        var3 *= var3;
        var3 *= var3;
        final float var5 = (1.0f + var3 * 0.4f) * var4;
        final float var6 = (1.0f + var3 * 0.1f) / var4;
        GlStateManager.HorizonCode_Horizon_È(var5, var6, var5);
    }
    
    protected int HorizonCode_Horizon_È(final EntityCreeper p_180571_1_, final float p_180571_2_, final float p_180571_3_) {
        final float var4 = p_180571_1_.Ý(p_180571_3_);
        if ((int)(var4 * 10.0f) % 2 == 0) {
            return 0;
        }
        int var5 = (int)(var4 * 0.2f * 255.0f);
        var5 = MathHelper.HorizonCode_Horizon_È(var5, 0, 255);
        return var5 << 24 | 0xFFFFFF;
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityCreeper p_110775_1_) {
        return RenderCreeper.HorizonCode_Horizon_È;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((EntityCreeper)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected int HorizonCode_Horizon_È(final EntityLivingBase p_77030_1_, final float p_77030_2_, final float p_77030_3_) {
        return this.HorizonCode_Horizon_È((EntityCreeper)p_77030_1_, p_77030_2_, p_77030_3_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityCreeper)p_110775_1_);
    }
}
