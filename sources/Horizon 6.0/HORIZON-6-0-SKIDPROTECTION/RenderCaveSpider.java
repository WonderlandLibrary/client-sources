package HORIZON-6-0-SKIDPROTECTION;

public class RenderCaveSpider extends RenderSpider
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00000982";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/spider/cave_spider.png");
    }
    
    public RenderCaveSpider(final RenderManager p_i46189_1_) {
        super(p_i46189_1_);
        this.Ý *= 0.7f;
    }
    
    protected void HorizonCode_Horizon_È(final EntityCaveSpider p_180585_1_, final float p_180585_2_) {
        GlStateManager.HorizonCode_Horizon_È(0.7f, 0.7f, 0.7f);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityCaveSpider p_180586_1_) {
        return RenderCaveSpider.HorizonCode_Horizon_È;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntitySpider p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityCaveSpider)p_110775_1_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((EntityCaveSpider)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityCaveSpider)p_110775_1_);
    }
}
