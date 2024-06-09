package HORIZON-6-0-SKIDPROTECTION;

public class RenderIronGolem extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00001031";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/iron_golem.png");
    }
    
    public RenderIronGolem(final RenderManager p_i46133_1_) {
        super(p_i46133_1_, new ModelIronGolem(), 0.5f);
        this.HorizonCode_Horizon_È(new LayerIronGolemFlower(this));
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityIronGolem p_110775_1_) {
        return RenderIronGolem.HorizonCode_Horizon_È;
    }
    
    protected void HorizonCode_Horizon_È(final EntityIronGolem p_180588_1_, final float p_180588_2_, final float p_180588_3_, final float p_180588_4_) {
        super.HorizonCode_Horizon_È(p_180588_1_, p_180588_2_, p_180588_3_, p_180588_4_);
        if (p_180588_1_.áŒŠá€ >= 0.01) {
            final float var5 = 13.0f;
            final float var6 = p_180588_1_.¥Ï - p_180588_1_.áŒŠá€ * (1.0f - p_180588_4_) + 6.0f;
            final float var7 = (Math.abs(var6 % var5 - var5 * 0.5f) - var5 * 0.25f) / (var5 * 0.25f);
            GlStateManager.Â(6.5f * var7, 0.0f, 0.0f, 1.0f);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        this.HorizonCode_Horizon_È((EntityIronGolem)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityIronGolem)p_110775_1_);
    }
}
