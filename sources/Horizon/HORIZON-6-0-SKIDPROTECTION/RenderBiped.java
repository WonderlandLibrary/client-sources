package HORIZON-6-0-SKIDPROTECTION;

public class RenderBiped extends RenderLiving
{
    private static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    protected ModelBiped HorizonCode_Horizon_È;
    protected float Âµá€;
    private static final String ÂµÈ = "CL_00001001";
    
    static {
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/entity/steve.png");
    }
    
    public RenderBiped(final RenderManager p_i46168_1_, final ModelBiped p_i46168_2_, final float p_i46168_3_) {
        this(p_i46168_1_, p_i46168_2_, p_i46168_3_, 1.0f);
        this.HorizonCode_Horizon_È(new LayerHeldItem(this));
    }
    
    public RenderBiped(final RenderManager p_i46169_1_, final ModelBiped p_i46169_2_, final float p_i46169_3_, final float p_i46169_4_) {
        super(p_i46169_1_, p_i46169_2_, p_i46169_3_);
        this.HorizonCode_Horizon_È = p_i46169_2_;
        this.Âµá€ = p_i46169_4_;
        this.HorizonCode_Horizon_È(new LayerCustomHead(p_i46169_2_.ÂµÈ));
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityLiving p_110775_1_) {
        return RenderBiped.áˆºÑ¢Õ;
    }
    
    @Override
    public void u_() {
        GlStateManager.Â(0.0f, 0.1875f, 0.0f);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityLiving)p_110775_1_);
    }
}
