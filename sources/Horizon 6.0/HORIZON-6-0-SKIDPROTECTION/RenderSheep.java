package HORIZON-6-0-SKIDPROTECTION;

public class RenderSheep extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00001021";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/sheep/sheep.png");
    }
    
    public RenderSheep(final RenderManager p_i46145_1_, final ModelBase p_i46145_2_, final float p_i46145_3_) {
        super(p_i46145_1_, p_i46145_2_, p_i46145_3_);
        this.HorizonCode_Horizon_È(new LayerSheepWool(this));
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntitySheep p_110775_1_) {
        return RenderSheep.HorizonCode_Horizon_È;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntitySheep)p_110775_1_);
    }
}
