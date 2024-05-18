package HORIZON-6-0-SKIDPROTECTION;

public class ArmorStandRenderer extends RendererLivingEntity
{
    public static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00002447";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/armorstand/wood.png");
    }
    
    public ArmorStandRenderer(final RenderManager p_i46195_1_) {
        super(p_i46195_1_, new ModelArmorStand(), 0.0f);
        final LayerBipedArmor var2 = new LayerBipedArmor(this) {
            private static final String Âµá€ = "CL_00002446";
            
            @Override
            protected void HorizonCode_Horizon_È() {
                this.Ý = new ModelArmorStandArmor(0.5f);
                this.Ø­áŒŠá = new ModelArmorStandArmor(1.0f);
            }
        };
        this.HorizonCode_Horizon_È(var2);
        this.HorizonCode_Horizon_È(new LayerHeldItem(this));
        this.HorizonCode_Horizon_È(new LayerCustomHead(this.HorizonCode_Horizon_È().ÂµÈ));
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityArmorStand p_177102_1_) {
        return ArmorStandRenderer.HorizonCode_Horizon_È;
    }
    
    public ModelArmorStand HorizonCode_Horizon_È() {
        return (ModelArmorStand)super.Â();
    }
    
    protected void HorizonCode_Horizon_È(final EntityArmorStand p_177101_1_, final float p_177101_2_, final float p_177101_3_, final float p_177101_4_) {
        GlStateManager.Â(180.0f - p_177101_3_, 0.0f, 1.0f, 0.0f);
    }
    
    protected boolean Â(final EntityArmorStand p_177099_1_) {
        return p_177099_1_.áŒŠá€();
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(final EntityLivingBase targetEntity) {
        return this.Â((EntityArmorStand)targetEntity);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        this.HorizonCode_Horizon_È((EntityArmorStand)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    public ModelBase Â() {
        return this.HorizonCode_Horizon_È();
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityArmorStand)p_110775_1_);
    }
    
    @Override
    protected boolean Â(final Entity p_177070_1_) {
        return this.Â((EntityArmorStand)p_177070_1_);
    }
}
