package HORIZON-6-0-SKIDPROTECTION;

public abstract class RenderLiving extends RendererLivingEntity
{
    private static final String HorizonCode_Horizon_È = "CL_00001015";
    
    public RenderLiving(final RenderManager p_i46153_1_, final ModelBase p_i46153_2_, final float p_i46153_3_) {
        super(p_i46153_1_, p_i46153_2_, p_i46153_3_);
    }
    
    protected boolean Â(final EntityLiving targetEntity) {
        return super.HorizonCode_Horizon_È(targetEntity) && (targetEntity.¥Ï() || (targetEntity.j_() && targetEntity == this.Â.à));
    }
    
    public boolean HorizonCode_Horizon_È(final EntityLiving p_177104_1_, final ICamera p_177104_2_, final double p_177104_3_, final double p_177104_5_, final double p_177104_7_) {
        if (super.HorizonCode_Horizon_È(p_177104_1_, p_177104_2_, p_177104_3_, p_177104_5_, p_177104_7_)) {
            return true;
        }
        if (p_177104_1_.ÇŽà() && p_177104_1_.ŠáˆºÂ() != null) {
            final Entity var9 = p_177104_1_.ŠáˆºÂ();
            return p_177104_2_.HorizonCode_Horizon_È(var9.£É());
        }
        return false;
    }
    
    public void HorizonCode_Horizon_È(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        super.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        this.Â(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    public void HorizonCode_Horizon_È(final EntityLiving p_177105_1_, final float p_177105_2_) {
        final int var3 = p_177105_1_.HorizonCode_Horizon_È(p_177105_2_);
        final int var4 = var3 % 65536;
        final int var5 = var3 / 65536;
        OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var4 / 1.0f, var5 / 1.0f);
    }
    
    private double HorizonCode_Horizon_È(final double p_110828_1_, final double p_110828_3_, final double p_110828_5_) {
        return p_110828_1_ + (p_110828_3_ - p_110828_1_) * p_110828_5_;
    }
    
    protected void Â(final EntityLiving p_110827_1_, double p_110827_2_, double p_110827_4_, double p_110827_6_, final float p_110827_8_, final float p_110827_9_) {
        final Entity var10 = p_110827_1_.ŠáˆºÂ();
        if (var10 != null) {
            p_110827_4_ -= (1.6 - p_110827_1_.£ÂµÄ) * 0.5;
            final Tessellator var11 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var12 = var11.Ý();
            final double var13 = this.HorizonCode_Horizon_È(var10.á€, var10.É, (double)(p_110827_9_ * 0.5f)) * 0.01745329238474369;
            final double var14 = this.HorizonCode_Horizon_È(var10.Õ, var10.áƒ, (double)(p_110827_9_ * 0.5f)) * 0.01745329238474369;
            double var15 = Math.cos(var13);
            double var16 = Math.sin(var13);
            double var17 = Math.sin(var14);
            if (var10 instanceof EntityHanging) {
                var15 = 0.0;
                var16 = 0.0;
                var17 = -1.0;
            }
            final double var18 = Math.cos(var14);
            final double var19 = this.HorizonCode_Horizon_È(var10.áŒŠà, var10.ŒÏ, p_110827_9_) - var15 * 0.7 - var16 * 0.5 * var18;
            final double var20 = this.HorizonCode_Horizon_È(var10.ŠÄ + var10.Ðƒáƒ() * 0.7, var10.Çªà¢ + var10.Ðƒáƒ() * 0.7, p_110827_9_) - var17 * 0.5 - 0.25;
            final double var21 = this.HorizonCode_Horizon_È(var10.Ñ¢á, var10.Ê, p_110827_9_) - var16 * 0.7 + var15 * 0.5 * var18;
            final double var22 = this.HorizonCode_Horizon_È(p_110827_1_.£ÇªÓ, p_110827_1_.¥É, (double)p_110827_9_) * 0.01745329238474369 + 1.5707963267948966;
            var15 = Math.cos(var22) * p_110827_1_.áŒŠ * 0.4;
            var16 = Math.sin(var22) * p_110827_1_.áŒŠ * 0.4;
            final double var23 = this.HorizonCode_Horizon_È(p_110827_1_.áŒŠà, p_110827_1_.ŒÏ, p_110827_9_) + var15;
            final double var24 = this.HorizonCode_Horizon_È(p_110827_1_.ŠÄ, p_110827_1_.Çªà¢, p_110827_9_);
            final double var25 = this.HorizonCode_Horizon_È(p_110827_1_.Ñ¢á, p_110827_1_.Ê, p_110827_9_) + var16;
            p_110827_2_ += var15;
            p_110827_6_ += var16;
            final double var26 = (float)(var19 - var23);
            final double var27 = (float)(var20 - var24);
            final double var28 = (float)(var21 - var25);
            GlStateManager.Æ();
            GlStateManager.Ó();
            GlStateManager.£à();
            final boolean var29 = true;
            final double var30 = 0.025;
            var12.HorizonCode_Horizon_È(5);
            for (int var31 = 0; var31 <= 24; ++var31) {
                if (var31 % 2 == 0) {
                    var12.HorizonCode_Horizon_È(0.5f, 0.4f, 0.3f, 1.0f);
                }
                else {
                    var12.HorizonCode_Horizon_È(0.35f, 0.28f, 0.21000001f, 1.0f);
                }
                final float var32 = var31 / 24.0f;
                var12.Â(p_110827_2_ + var26 * var32 + 0.0, p_110827_4_ + var27 * (var32 * var32 + var32) * 0.5 + ((24.0f - var31) / 18.0f + 0.125f), p_110827_6_ + var28 * var32);
                var12.Â(p_110827_2_ + var26 * var32 + 0.025, p_110827_4_ + var27 * (var32 * var32 + var32) * 0.5 + ((24.0f - var31) / 18.0f + 0.125f) + 0.025, p_110827_6_ + var28 * var32);
            }
            var11.Â();
            var12.HorizonCode_Horizon_È(5);
            for (int var31 = 0; var31 <= 24; ++var31) {
                if (var31 % 2 == 0) {
                    var12.HorizonCode_Horizon_È(0.5f, 0.4f, 0.3f, 1.0f);
                }
                else {
                    var12.HorizonCode_Horizon_È(0.35f, 0.28f, 0.21000001f, 1.0f);
                }
                final float var32 = var31 / 24.0f;
                var12.Â(p_110827_2_ + var26 * var32 + 0.0, p_110827_4_ + var27 * (var32 * var32 + var32) * 0.5 + ((24.0f - var31) / 18.0f + 0.125f) + 0.025, p_110827_6_ + var28 * var32);
                var12.Â(p_110827_2_ + var26 * var32 + 0.025, p_110827_4_ + var27 * (var32 * var32 + var32) * 0.5 + ((24.0f - var31) / 18.0f + 0.125f), p_110827_6_ + var28 * var32 + 0.025);
            }
            var11.Â();
            GlStateManager.Âµá€();
            GlStateManager.µÕ();
            GlStateManager.Å();
        }
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(final EntityLivingBase targetEntity) {
        return this.Â((EntityLiving)targetEntity);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityLiving)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected boolean Â(final Entity p_177070_1_) {
        return this.Â((EntityLiving)p_177070_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityLiving)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Entity p_177071_1_, final ICamera p_177071_2_, final double p_177071_3_, final double p_177071_5_, final double p_177071_7_) {
        return this.HorizonCode_Horizon_È((EntityLiving)p_177071_1_, p_177071_2_, p_177071_3_, p_177071_5_, p_177071_7_);
    }
}
