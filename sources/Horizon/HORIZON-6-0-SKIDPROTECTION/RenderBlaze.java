package HORIZON-6-0-SKIDPROTECTION;

public class RenderBlaze extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00000980";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/blaze.png");
    }
    
    public RenderBlaze(final RenderManager p_i46191_1_) {
        super(p_i46191_1_, new ModelBlaze(), 0.5f);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityBlaze p_110775_1_) {
        return RenderBlaze.HorizonCode_Horizon_È;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityBlaze)p_110775_1_);
    }
}
