package HORIZON-6-0-SKIDPROTECTION;

public class RenderCow extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00000984";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/cow/cow.png");
    }
    
    public RenderCow(final RenderManager p_i46187_1_, final ModelBase p_i46187_2_, final float p_i46187_3_) {
        super(p_i46187_1_, p_i46187_2_, p_i46187_3_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityCow p_180572_1_) {
        return RenderCow.HorizonCode_Horizon_È;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityCow)p_110775_1_);
    }
}
