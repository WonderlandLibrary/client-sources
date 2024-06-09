package HORIZON-6-0-SKIDPROTECTION;

public class RenderSpider extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00001027";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/spider/spider.png");
    }
    
    public RenderSpider(final RenderManager p_i46139_1_) {
        super(p_i46139_1_, new ModelSpider(), 1.0f);
        this.HorizonCode_Horizon_È(new LayerSpiderEyes(this));
    }
    
    protected float Â(final EntitySpider p_77037_1_) {
        return 180.0f;
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntitySpider p_110775_1_) {
        return RenderSpider.HorizonCode_Horizon_È;
    }
    
    @Override
    protected float Â(final EntityLivingBase p_77037_1_) {
        return this.Â((EntitySpider)p_77037_1_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntitySpider)p_110775_1_);
    }
}
