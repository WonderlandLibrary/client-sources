package HORIZON-6-0-SKIDPROTECTION;

public class RenderVillager extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Âµá€;
    private static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    private static final ResourceLocation_1975012498 ÂµÈ;
    private static final ResourceLocation_1975012498 á;
    private static final ResourceLocation_1975012498 ˆÏ­;
    private static final String £á = "CL_00001032";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/villager/villager.png");
        Âµá€ = new ResourceLocation_1975012498("textures/entity/villager/farmer.png");
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/entity/villager/librarian.png");
        ÂµÈ = new ResourceLocation_1975012498("textures/entity/villager/priest.png");
        á = new ResourceLocation_1975012498("textures/entity/villager/smith.png");
        ˆÏ­ = new ResourceLocation_1975012498("textures/entity/villager/butcher.png");
    }
    
    public RenderVillager(final RenderManager p_i46132_1_) {
        super(p_i46132_1_, new ModelVillager(0.0f), 0.5f);
        this.HorizonCode_Horizon_È(new LayerCustomHead(this.Âµá€().HorizonCode_Horizon_È));
    }
    
    public ModelVillager Âµá€() {
        return (ModelVillager)super.Â();
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityVillager p_110775_1_) {
        switch (p_110775_1_.ÇŽ()) {
            case 0: {
                return RenderVillager.Âµá€;
            }
            case 1: {
                return RenderVillager.áˆºÑ¢Õ;
            }
            case 2: {
                return RenderVillager.ÂµÈ;
            }
            case 3: {
                return RenderVillager.á;
            }
            case 4: {
                return RenderVillager.ˆÏ­;
            }
            default: {
                return RenderVillager.HorizonCode_Horizon_È;
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final EntityVillager p_77041_1_, final float p_77041_2_) {
        float var3 = 0.9375f;
        if (p_77041_1_.à() < 0) {
            var3 *= 0.5;
            this.Ý = 0.25f;
        }
        else {
            this.Ý = 0.5f;
        }
        GlStateManager.HorizonCode_Horizon_È(var3, var3, var3);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((EntityVillager)p_77041_1_, p_77041_2_);
    }
    
    @Override
    public ModelBase Â() {
        return this.Âµá€();
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityVillager)p_110775_1_);
    }
}
