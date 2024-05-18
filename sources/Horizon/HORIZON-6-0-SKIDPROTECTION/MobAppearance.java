package HORIZON-6-0-SKIDPROTECTION;

public class MobAppearance extends EntityFX
{
    private EntityLivingBase HorizonCode_Horizon_È;
    private static final String ÇŽá = "CL_00002594";
    
    protected MobAppearance(final World worldIn, final double p_i46283_2_, final double p_i46283_4_, final double p_i46283_6_) {
        super(worldIn, p_i46283_2_, p_i46283_4_, p_i46283_6_, 0.0, 0.0, 0.0);
        final float áˆºÑ¢Õ = 1.0f;
        this.á = áˆºÑ¢Õ;
        this.ÂµÈ = áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = áˆºÑ¢Õ;
        final double çžé = 0.0;
        this.ÇŽÕ = çžé;
        this.ˆá = çžé;
        this.ÇŽÉ = çžé;
        this.áŒŠÆ = 0.0f;
        this.à = 30;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 3;
    }
    
    @Override
    public void á() {
        super.á();
        if (this.HorizonCode_Horizon_È == null) {
            final EntityGuardian var1 = new EntityGuardian(this.Ï­Ðƒà);
            var1.ÐƒÇŽà();
            this.HorizonCode_Horizon_È = var1;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        if (this.HorizonCode_Horizon_È != null) {
            final RenderManager var9 = Minecraft.áŒŠà().ÇªÓ();
            var9.HorizonCode_Horizon_È(EntityFX.Å, EntityFX.£à, EntityFX.µà);
            final float var10 = 0.42553192f;
            final float var11 = (this.Ó + p_180434_3_) / this.à;
            GlStateManager.HorizonCode_Horizon_È(true);
            GlStateManager.á();
            GlStateManager.áˆºÑ¢Õ();
            GlStateManager.Â(770, 771);
            final float var12 = 240.0f;
            OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var12, var12);
            GlStateManager.Çªà¢();
            final float var13 = 0.05f + 0.5f * MathHelper.HorizonCode_Horizon_È(var11 * 3.1415927f);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, var13);
            GlStateManager.Â(0.0f, 1.8f, 0.0f);
            GlStateManager.Â(180.0f - p_180434_2_.É, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(60.0f - 150.0f * var11 - p_180434_2_.áƒ, 1.0f, 0.0f, 0.0f);
            GlStateManager.Â(0.0f, -0.4f, -1.5f);
            GlStateManager.HorizonCode_Horizon_È(var10, var10, var10);
            final EntityLivingBase horizonCode_Horizon_È = this.HorizonCode_Horizon_È;
            final EntityLivingBase horizonCode_Horizon_È2 = this.HorizonCode_Horizon_È;
            final float n = 0.0f;
            horizonCode_Horizon_È2.á€ = n;
            horizonCode_Horizon_È.É = n;
            final EntityLivingBase horizonCode_Horizon_È3 = this.HorizonCode_Horizon_È;
            final EntityLivingBase horizonCode_Horizon_È4 = this.HorizonCode_Horizon_È;
            final float n2 = 0.0f;
            horizonCode_Horizon_È4.Š = n2;
            horizonCode_Horizon_È3.ÂµÕ = n2;
            var9.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 0.0, 0.0, 0.0, 0.0f, p_180434_3_);
            GlStateManager.Ê();
            GlStateManager.áˆºÑ¢Õ();
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002593";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new MobAppearance(worldIn, p_178902_3_, p_178902_5_, p_178902_7_);
        }
    }
}
