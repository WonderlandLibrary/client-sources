package HORIZON-6-0-SKIDPROTECTION;

public class RenderEndermite extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00002445";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/endermite.png");
    }
    
    public RenderEndermite(final RenderManager p_i46181_1_) {
        super(p_i46181_1_, new ModelEnderMite(), 0.3f);
    }
    
    protected float HorizonCode_Horizon_È(final EntityEndermite p_177107_1_) {
        return 180.0f;
    }
    
    protected ResourceLocation_1975012498 Â(final EntityEndermite p_177106_1_) {
        return RenderEndermite.HorizonCode_Horizon_È;
    }
    
    @Override
    protected float Â(final EntityLivingBase p_77037_1_) {
        return this.HorizonCode_Horizon_È((EntityEndermite)p_77037_1_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.Â((EntityEndermite)p_110775_1_);
    }
}
