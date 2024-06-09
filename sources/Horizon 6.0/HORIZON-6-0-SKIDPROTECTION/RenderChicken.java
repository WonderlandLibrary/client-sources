package HORIZON-6-0-SKIDPROTECTION;

public class RenderChicken extends RenderLiving
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00000983";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/chicken.png");
    }
    
    public RenderChicken(final RenderManager p_i46188_1_, final ModelBase p_i46188_2_, final float p_i46188_3_) {
        super(p_i46188_1_, p_i46188_2_, p_i46188_3_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityChicken p_180568_1_) {
        return RenderChicken.HorizonCode_Horizon_È;
    }
    
    protected float HorizonCode_Horizon_È(final EntityChicken p_180569_1_, final float p_180569_2_) {
        final float var3 = p_180569_1_.ˆÐƒØ + (p_180569_1_.ŒÂ - p_180569_1_.ˆÐƒØ) * p_180569_2_;
        final float var4 = p_180569_1_.ŠØ + (p_180569_1_.Ï­Ï - p_180569_1_.ŠØ) * p_180569_2_;
        return (MathHelper.HorizonCode_Horizon_È(var3) + 1.0f) * var4;
    }
    
    @Override
    protected float Â(final EntityLivingBase p_77044_1_, final float p_77044_2_) {
        return this.HorizonCode_Horizon_È((EntityChicken)p_77044_1_, p_77044_2_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityChicken)p_110775_1_);
    }
}
