package HORIZON-6-0-SKIDPROTECTION;

public class RenderMooshroom extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00001016";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/cow/mooshroom.png");
    }
    
    public RenderMooshroom(final RenderManager p_i46152_1_, final ModelBase p_i46152_2_, final float p_i46152_3_) {
        super(p_i46152_1_, p_i46152_2_, p_i46152_3_);
        this.HorizonCode_Horizon_È(new LayerMooshroomMushroom(this));
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityMooshroom p_180582_1_) {
        return RenderMooshroom.HorizonCode_Horizon_È;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityMooshroom)p_110775_1_);
    }
}
