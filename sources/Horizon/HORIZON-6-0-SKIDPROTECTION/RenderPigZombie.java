package HORIZON-6-0-SKIDPROTECTION;

public class RenderPigZombie extends RenderBiped
{
    private static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00002434";
    
    static {
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/entity/zombie_pigman.png");
    }
    
    public RenderPigZombie(final RenderManager p_i46148_1_) {
        super(p_i46148_1_, new ModelZombie(), 0.5f, 1.0f);
        this.HorizonCode_Horizon_È(new LayerHeldItem(this));
        this.HorizonCode_Horizon_È(new LayerBipedArmor(this) {
            private static final String Âµá€ = "CL_00002433";
            
            @Override
            protected void HorizonCode_Horizon_È() {
                this.Ý = new ModelZombie(0.5f, true);
                this.Ø­áŒŠá = new ModelZombie(1.0f, true);
            }
        });
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityPigZombie p_177119_1_) {
        return RenderPigZombie.áˆºÑ¢Õ;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityLiving p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityPigZombie)p_110775_1_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityPigZombie)p_110775_1_);
    }
}
