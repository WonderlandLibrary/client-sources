package HORIZON-6-0-SKIDPROTECTION;

public class RenderGiantZombie extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private float Âµá€;
    private static final String áˆºÑ¢Õ = "CL_00000998";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/zombie/zombie.png");
    }
    
    public RenderGiantZombie(final RenderManager p_i46173_1_, final ModelBase p_i46173_2_, final float p_i46173_3_, final float p_i46173_4_) {
        super(p_i46173_1_, p_i46173_2_, p_i46173_3_ * p_i46173_4_);
        this.Âµá€ = p_i46173_4_;
        this.HorizonCode_Horizon_È(new LayerHeldItem(this));
        this.HorizonCode_Horizon_È(new LayerBipedArmor(this) {
            private static final String Âµá€ = "CL_00002444";
            
            @Override
            protected void HorizonCode_Horizon_È() {
                this.Ý = new ModelZombie(0.5f, true);
                this.Ø­áŒŠá = new ModelZombie(1.0f, true);
            }
        });
    }
    
    @Override
    public void u_() {
        GlStateManager.Â(0.0f, 0.1875f, 0.0f);
    }
    
    protected void HorizonCode_Horizon_È(final EntityGiantZombie p_77041_1_, final float p_77041_2_) {
        GlStateManager.HorizonCode_Horizon_È(this.Âµá€, this.Âµá€, this.Âµá€);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityGiantZombie p_110775_1_) {
        return RenderGiantZombie.HorizonCode_Horizon_È;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((EntityGiantZombie)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityGiantZombie)p_110775_1_);
    }
}
