package HORIZON-6-0-SKIDPROTECTION;

public class RenderSkeleton extends RenderBiped
{
    private static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    private static final ResourceLocation_1975012498 ÂµÈ;
    private static final String á = "CL_00001023";
    
    static {
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/entity/skeleton/skeleton.png");
        ÂµÈ = new ResourceLocation_1975012498("textures/entity/skeleton/wither_skeleton.png");
    }
    
    public RenderSkeleton(final RenderManager p_i46143_1_) {
        super(p_i46143_1_, new ModelSkeleton(), 0.5f);
        this.HorizonCode_Horizon_È(new LayerHeldItem(this));
        this.HorizonCode_Horizon_È(new LayerBipedArmor(this) {
            private static final String Âµá€ = "CL_00002431";
            
            @Override
            protected void HorizonCode_Horizon_È() {
                this.Ý = new ModelSkeleton(0.5f, true);
                this.Ø­áŒŠá = new ModelSkeleton(1.0f, true);
            }
        });
    }
    
    protected void HorizonCode_Horizon_È(final EntitySkeleton p_77041_1_, final float p_77041_2_) {
        if (p_77041_1_.ÇŽÅ() == 1) {
            GlStateManager.HorizonCode_Horizon_È(1.2f, 1.2f, 1.2f);
        }
    }
    
    @Override
    public void u_() {
        GlStateManager.Â(0.09375f, 0.1875f, 0.0f);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntitySkeleton p_180577_1_) {
        return (p_180577_1_.ÇŽÅ() == 1) ? RenderSkeleton.ÂµÈ : RenderSkeleton.áˆºÑ¢Õ;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityLiving p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntitySkeleton)p_110775_1_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((EntitySkeleton)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntitySkeleton)p_110775_1_);
    }
}
