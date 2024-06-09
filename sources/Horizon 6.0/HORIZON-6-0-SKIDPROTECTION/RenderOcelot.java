package HORIZON-6-0-SKIDPROTECTION;

public class RenderOcelot extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Âµá€;
    private static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    private static final ResourceLocation_1975012498 ÂµÈ;
    private static final String á = "CL_00001017";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/cat/black.png");
        Âµá€ = new ResourceLocation_1975012498("textures/entity/cat/ocelot.png");
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/entity/cat/red.png");
        ÂµÈ = new ResourceLocation_1975012498("textures/entity/cat/siamese.png");
    }
    
    public RenderOcelot(final RenderManager p_i46151_1_, final ModelBase p_i46151_2_, final float p_i46151_3_) {
        super(p_i46151_1_, p_i46151_2_, p_i46151_3_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityOcelot p_110775_1_) {
        switch (p_110775_1_.ÐƒÇŽà()) {
            default: {
                return RenderOcelot.Âµá€;
            }
            case 1: {
                return RenderOcelot.HorizonCode_Horizon_È;
            }
            case 2: {
                return RenderOcelot.áˆºÑ¢Õ;
            }
            case 3: {
                return RenderOcelot.ÂµÈ;
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final EntityOcelot p_77041_1_, final float p_77041_2_) {
        super.HorizonCode_Horizon_È(p_77041_1_, p_77041_2_);
        if (p_77041_1_.ÐƒÓ()) {
            GlStateManager.HorizonCode_Horizon_È(0.8f, 0.8f, 0.8f);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((EntityOcelot)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityOcelot)p_110775_1_);
    }
}
