package HORIZON-6-0-SKIDPROTECTION;

public class RenderSnowMan extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00001025";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/snowman.png");
    }
    
    public RenderSnowMan(final RenderManager p_i46140_1_) {
        super(p_i46140_1_, new ModelSnowMan(), 0.5f);
        this.HorizonCode_Horizon_È(new LayerSnowmanHead(this));
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntitySnowman p_180587_1_) {
        return RenderSnowMan.HorizonCode_Horizon_È;
    }
    
    public ModelSnowMan Âµá€() {
        return (ModelSnowMan)super.Â();
    }
    
    @Override
    public ModelBase Â() {
        return this.Âµá€();
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntitySnowman)p_110775_1_);
    }
}
