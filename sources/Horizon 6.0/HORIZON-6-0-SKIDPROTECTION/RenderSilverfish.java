package HORIZON-6-0-SKIDPROTECTION;

public class RenderSilverfish extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00001022";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/silverfish.png");
    }
    
    public RenderSilverfish(final RenderManager p_i46144_1_) {
        super(p_i46144_1_, new ModelSilverfish(), 0.3f);
    }
    
    protected float HorizonCode_Horizon_È(final EntitySilverfish p_180584_1_) {
        return 180.0f;
    }
    
    protected ResourceLocation_1975012498 Â(final EntitySilverfish p_110775_1_) {
        return RenderSilverfish.HorizonCode_Horizon_È;
    }
    
    @Override
    protected float Â(final EntityLivingBase p_77037_1_) {
        return this.HorizonCode_Horizon_È((EntitySilverfish)p_77037_1_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.Â((EntitySilverfish)p_110775_1_);
    }
}
