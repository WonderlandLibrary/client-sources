package HORIZON-6-0-SKIDPROTECTION;

public class EntityLargeExplodeFX extends EntityFX
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private int ÇŽá;
    private int Ñ¢à;
    private TextureManager ÇªØ­;
    private float £áŒŠá;
    private static final String áˆº = "CL_00000910";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/explosion.png");
    }
    
    protected EntityLargeExplodeFX(final TextureManager p_i1213_1_, final World worldIn, final double p_i1213_3_, final double p_i1213_5_, final double p_i1213_7_, final double p_i1213_9_, final double p_i1213_11_, final double p_i1213_13_) {
        super(worldIn, p_i1213_3_, p_i1213_5_, p_i1213_7_, 0.0, 0.0, 0.0);
        this.ÇªØ­ = p_i1213_1_;
        this.Ñ¢à = 6 + this.ˆáƒ.nextInt(4);
        final float áˆºÑ¢Õ = this.ˆáƒ.nextFloat() * 0.6f + 0.4f;
        this.á = áˆºÑ¢Õ;
        this.ÂµÈ = áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = áˆºÑ¢Õ;
        this.£áŒŠá = 1.0f - (float)p_i1213_9_ * 0.5f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        final int var9 = (int)((this.ÇŽá + p_180434_3_) * 15.0f / this.Ñ¢à);
        if (var9 <= 15) {
            this.ÇªØ­.HorizonCode_Horizon_È(EntityLargeExplodeFX.HorizonCode_Horizon_È);
            final float var10 = var9 % 4 / 4.0f;
            final float var11 = var10 + 0.24975f;
            final float var12 = var9 / 4 / 4.0f;
            final float var13 = var12 + 0.24975f;
            final float var14 = 2.0f * this.£áŒŠá;
            final float var15 = (float)(this.áŒŠà + (this.ŒÏ - this.áŒŠà) * p_180434_3_ - EntityLargeExplodeFX.Å);
            final float var16 = (float)(this.ŠÄ + (this.Çªà¢ - this.ŠÄ) * p_180434_3_ - EntityLargeExplodeFX.£à);
            final float var17 = (float)(this.Ñ¢á + (this.Ê - this.Ñ¢á) * p_180434_3_ - EntityLargeExplodeFX.µà);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.Ó();
            RenderHelper.HorizonCode_Horizon_È();
            p_180434_1_.Â();
            p_180434_1_.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, this.ÂµÈ, this.á, 1.0f);
            p_180434_1_.Ý(0.0f, 1.0f, 0.0f);
            p_180434_1_.Â(240);
            p_180434_1_.HorizonCode_Horizon_È(var15 - p_180434_4_ * var14 - p_180434_7_ * var14, var16 - p_180434_5_ * var14, var17 - p_180434_6_ * var14 - p_180434_8_ * var14, var11, var13);
            p_180434_1_.HorizonCode_Horizon_È(var15 - p_180434_4_ * var14 + p_180434_7_ * var14, var16 + p_180434_5_ * var14, var17 - p_180434_6_ * var14 + p_180434_8_ * var14, var11, var12);
            p_180434_1_.HorizonCode_Horizon_È(var15 + p_180434_4_ * var14 + p_180434_7_ * var14, var16 + p_180434_5_ * var14, var17 + p_180434_6_ * var14 + p_180434_8_ * var14, var10, var12);
            p_180434_1_.HorizonCode_Horizon_È(var15 + p_180434_4_ * var14 - p_180434_7_ * var14, var16 - p_180434_5_ * var14, var17 + p_180434_6_ * var14 - p_180434_8_ * var14, var10, var13);
            Tessellator.HorizonCode_Horizon_È().Â();
            GlStateManager.HorizonCode_Horizon_È(0.0f, 0.0f);
            GlStateManager.Âµá€();
        }
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float p_70070_1_) {
        return 61680;
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        ++this.ÇŽá;
        if (this.ÇŽá == this.Ñ¢à) {
            this.á€();
        }
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 3;
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002598";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityLargeExplodeFX(Minecraft.áŒŠà().¥à(), worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
}
