package HORIZON-6-0-SKIDPROTECTION;

public class RenderPig extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00001019";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/pig/pig.png");
    }
    
    public RenderPig(final RenderManager p_i46149_1_, final ModelBase p_i46149_2_, final float p_i46149_3_) {
        super(p_i46149_1_, p_i46149_2_, p_i46149_3_);
        this.HorizonCode_Horizon_È(new LayerSaddle(this));
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityPig p_180583_1_) {
        return RenderPig.HorizonCode_Horizon_È;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityPig)p_110775_1_);
    }
}
