package HORIZON-6-0-SKIDPROTECTION;

public class RenderDragon extends RenderLiving
{
    private static final ResourceLocation_1975012498 Âµá€;
    private static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    private static final ResourceLocation_1975012498 ÂµÈ;
    protected ModelDragon HorizonCode_Horizon_È;
    private static final String á = "CL_00000988";
    
    static {
        Âµá€ = new ResourceLocation_1975012498("textures/entity/endercrystal/endercrystal_beam.png");
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/entity/enderdragon/dragon_exploding.png");
        ÂµÈ = new ResourceLocation_1975012498("textures/entity/enderdragon/dragon.png");
    }
    
    public RenderDragon(final RenderManager p_i46183_1_) {
        super(p_i46183_1_, new ModelDragon(0.0f), 0.5f);
        this.HorizonCode_Horizon_È = (ModelDragon)this.Ó;
        this.HorizonCode_Horizon_È(new LayerEnderDragonEyes(this));
        this.HorizonCode_Horizon_È(new LayerEnderDragonDeath());
    }
    
    protected void HorizonCode_Horizon_È(final EntityDragon p_180575_1_, final float p_180575_2_, final float p_180575_3_, final float p_180575_4_) {
        final float var5 = (float)p_180575_1_.Â(7, p_180575_4_)[0];
        final float var6 = (float)(p_180575_1_.Â(5, p_180575_4_)[1] - p_180575_1_.Â(10, p_180575_4_)[1]);
        GlStateManager.Â(-var5, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(var6 * 10.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.Â(0.0f, 0.0f, 1.0f);
        if (p_180575_1_.ÇªØ­ > 0) {
            float var7 = (p_180575_1_.ÇªØ­ + p_180575_4_ - 1.0f) / 20.0f * 1.6f;
            var7 = MathHelper.Ý(var7);
            if (var7 > 1.0f) {
                var7 = 1.0f;
            }
            GlStateManager.Â(var7 * this.Â(p_180575_1_), 0.0f, 0.0f, 1.0f);
        }
    }
    
    protected void HorizonCode_Horizon_È(final EntityDragon p_77036_1_, final float p_77036_2_, final float p_77036_3_, final float p_77036_4_, final float p_77036_5_, final float p_77036_6_, final float p_77036_7_) {
        if (p_77036_1_.ÂµáˆºÂ > 0) {
            final float var8 = p_77036_1_.ÂµáˆºÂ / 200.0f;
            GlStateManager.Ý(515);
            GlStateManager.Ø­áŒŠá();
            GlStateManager.HorizonCode_Horizon_È(516, var8);
            this.HorizonCode_Horizon_È(RenderDragon.áˆºÑ¢Õ);
            this.Ó.HorizonCode_Horizon_È(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
            GlStateManager.Ý(514);
        }
        this.Ý(p_77036_1_);
        this.Ó.HorizonCode_Horizon_È(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
        if (p_77036_1_.µà > 0) {
            GlStateManager.Ý(514);
            GlStateManager.Æ();
            GlStateManager.á();
            GlStateManager.Â(770, 771);
            GlStateManager.Ý(1.0f, 0.0f, 0.0f, 0.5f);
            this.Ó.HorizonCode_Horizon_È(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            GlStateManager.µÕ();
            GlStateManager.ÂµÈ();
            GlStateManager.Ý(515);
        }
    }
    
    public void HorizonCode_Horizon_È(final EntityDragon p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        BossStatus.HorizonCode_Horizon_È(p_76986_1_, false);
        super.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        if (p_76986_1_.¥Âµá€ != null) {
            this.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_9_);
        }
    }
    
    protected void HorizonCode_Horizon_È(final EntityDragon p_180574_1_, final double p_180574_2_, final double p_180574_4_, final double p_180574_6_, final float p_180574_8_) {
        final float var9 = p_180574_1_.¥Âµá€.HorizonCode_Horizon_È + p_180574_8_;
        float var10 = MathHelper.HorizonCode_Horizon_È(var9 * 0.2f) / 2.0f + 0.5f;
        var10 = (var10 * var10 + var10) * 0.2f;
        final float var11 = (float)(p_180574_1_.¥Âµá€.ŒÏ - p_180574_1_.ŒÏ - (p_180574_1_.áŒŠà - p_180574_1_.ŒÏ) * (1.0f - p_180574_8_));
        final float var12 = (float)(var10 + p_180574_1_.¥Âµá€.Çªà¢ - 1.0 - p_180574_1_.Çªà¢ - (p_180574_1_.ŠÄ - p_180574_1_.Çªà¢) * (1.0f - p_180574_8_));
        final float var13 = (float)(p_180574_1_.¥Âµá€.Ê - p_180574_1_.Ê - (p_180574_1_.Ñ¢á - p_180574_1_.Ê) * (1.0f - p_180574_8_));
        final float var14 = MathHelper.Ý(var11 * var11 + var13 * var13);
        final float var15 = MathHelper.Ý(var11 * var11 + var12 * var12 + var13 * var13);
        GlStateManager.Çªà¢();
        GlStateManager.Â((float)p_180574_2_, (float)p_180574_4_ + 2.0f, (float)p_180574_6_);
        GlStateManager.Â((float)(-Math.atan2(var13, var11)) * 180.0f / 3.1415927f - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â((float)(-Math.atan2(var14, var12)) * 180.0f / 3.1415927f - 90.0f, 1.0f, 0.0f, 0.0f);
        final Tessellator var16 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var17 = var16.Ý();
        RenderHelper.HorizonCode_Horizon_È();
        GlStateManager.£à();
        this.HorizonCode_Horizon_È(RenderDragon.Âµá€);
        GlStateManager.áˆºÑ¢Õ(7425);
        final float var18 = 0.0f - (p_180574_1_.Œ + p_180574_8_) * 0.01f;
        final float var19 = MathHelper.Ý(var11 * var11 + var12 * var12 + var13 * var13) / 32.0f - (p_180574_1_.Œ + p_180574_8_) * 0.01f;
        var17.HorizonCode_Horizon_È(5);
        final byte var20 = 8;
        for (int var21 = 0; var21 <= var20; ++var21) {
            final float var22 = MathHelper.HorizonCode_Horizon_È(var21 % var20 * 3.1415927f * 2.0f / var20) * 0.75f;
            final float var23 = MathHelper.Â(var21 % var20 * 3.1415927f * 2.0f / var20) * 0.75f;
            final float var24 = var21 % var20 * 1.0f / var20;
            var17.Ý(0);
            var17.HorizonCode_Horizon_È(var22 * 0.2f, var23 * 0.2f, 0.0, var24, var19);
            var17.Ý(16777215);
            var17.HorizonCode_Horizon_È(var22, var23, var15, var24, var18);
        }
        var16.Â();
        GlStateManager.Å();
        GlStateManager.áˆºÑ¢Õ(7424);
        RenderHelper.Â();
        GlStateManager.Ê();
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityDragon p_110775_1_) {
        return RenderDragon.ÂµÈ;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityDragon)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        this.HorizonCode_Horizon_È((EntityDragon)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77036_1_, final float p_77036_2_, final float p_77036_3_, final float p_77036_4_, final float p_77036_5_, final float p_77036_6_, final float p_77036_7_) {
        this.HorizonCode_Horizon_È((EntityDragon)p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityDragon)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityDragon)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityDragon)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
