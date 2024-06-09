package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;

public class RenderGuardian extends RenderLiving
{
    private static final ResourceLocation_1975012498 Âµá€;
    private static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    private static final ResourceLocation_1975012498 ÂµÈ;
    int HorizonCode_Horizon_È;
    private static final String á = "CL_00002443";
    
    static {
        Âµá€ = new ResourceLocation_1975012498("textures/entity/guardian.png");
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/entity/guardian_elder.png");
        ÂµÈ = new ResourceLocation_1975012498("textures/entity/guardian_beam.png");
    }
    
    public RenderGuardian(final RenderManager p_i46171_1_) {
        super(p_i46171_1_, new ModelGuardian(), 0.5f);
        this.HorizonCode_Horizon_È = ((ModelGuardian)this.Ó).HorizonCode_Horizon_È();
    }
    
    public boolean HorizonCode_Horizon_È(final EntityGuardian p_177113_1_, final ICamera p_177113_2_, final double p_177113_3_, final double p_177113_5_, final double p_177113_7_) {
        if (super.HorizonCode_Horizon_È(p_177113_1_, p_177113_2_, p_177113_3_, p_177113_5_, p_177113_7_)) {
            return true;
        }
        if (p_177113_1_.¥Ê()) {
            final EntityLivingBase var9 = p_177113_1_.ÐƒÓ();
            if (var9 != null) {
                final Vec3 var10 = this.HorizonCode_Horizon_È(var9, var9.£ÂµÄ * 0.5, 1.0f);
                final Vec3 var11 = this.HorizonCode_Horizon_È(p_177113_1_, (double)p_177113_1_.Ðƒáƒ(), 1.0f);
                if (p_177113_2_.HorizonCode_Horizon_È(AxisAlignedBB.HorizonCode_Horizon_È(var11.HorizonCode_Horizon_È, var11.Â, var11.Ý, var10.HorizonCode_Horizon_È, var10.Â, var10.Ý))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private Vec3 HorizonCode_Horizon_È(final EntityLivingBase p_177110_1_, final double p_177110_2_, final float p_177110_4_) {
        final double var5 = p_177110_1_.áˆºáˆºÈ + (p_177110_1_.ŒÏ - p_177110_1_.áˆºáˆºÈ) * p_177110_4_;
        final double var6 = p_177110_2_ + p_177110_1_.ÇŽá€ + (p_177110_1_.Çªà¢ - p_177110_1_.ÇŽá€) * p_177110_4_;
        final double var7 = p_177110_1_.Ï + (p_177110_1_.Ê - p_177110_1_.Ï) * p_177110_4_;
        return new Vec3(var5, var6, var7);
    }
    
    public void HorizonCode_Horizon_È(final EntityGuardian p_177109_1_, final double p_177109_2_, final double p_177109_4_, final double p_177109_6_, final float p_177109_8_, final float p_177109_9_) {
        if (this.HorizonCode_Horizon_È != ((ModelGuardian)this.Ó).HorizonCode_Horizon_È()) {
            this.Ó = new ModelGuardian();
            this.HorizonCode_Horizon_È = ((ModelGuardian)this.Ó).HorizonCode_Horizon_È();
        }
        super.HorizonCode_Horizon_È(p_177109_1_, p_177109_2_, p_177109_4_, p_177109_6_, p_177109_8_, p_177109_9_);
        final EntityLivingBase var10 = p_177109_1_.ÐƒÓ();
        if (var10 != null) {
            final float var11 = p_177109_1_.Å(p_177109_9_);
            final Tessellator var12 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var13 = var12.Ý();
            this.HorizonCode_Horizon_È(RenderGuardian.ÂµÈ);
            GL11.glTexParameterf(3553, 10242, 10497.0f);
            GL11.glTexParameterf(3553, 10243, 10497.0f);
            GlStateManager.Ó();
            GlStateManager.£à();
            GlStateManager.ÂµÈ();
            GlStateManager.HorizonCode_Horizon_È(true);
            final float var14 = 240.0f;
            OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var14, var14);
            GlStateManager.HorizonCode_Horizon_È(770, 1, 1, 0);
            final float var15 = p_177109_1_.Ï­Ðƒà.Šáƒ() + p_177109_9_;
            final float var16 = var15 * 0.5f % 1.0f;
            final float var17 = p_177109_1_.Ðƒáƒ();
            GlStateManager.Çªà¢();
            GlStateManager.Â((float)p_177109_2_, (float)p_177109_4_ + var17, (float)p_177109_6_);
            final Vec3 var18 = this.HorizonCode_Horizon_È(var10, var10.£ÂµÄ * 0.5, p_177109_9_);
            final Vec3 var19 = this.HorizonCode_Horizon_È(p_177109_1_, (double)var17, p_177109_9_);
            Vec3 var20 = var18.Ø­áŒŠá(var19);
            final double var21 = var20.Â() + 1.0;
            var20 = var20.HorizonCode_Horizon_È();
            final float var22 = (float)Math.acos(var20.Â);
            final float var23 = (float)Math.atan2(var20.Ý, var20.HorizonCode_Horizon_È);
            GlStateManager.Â((1.5707964f + -var23) * 57.295776f, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(var22 * 57.295776f, 1.0f, 0.0f, 0.0f);
            final byte var24 = 1;
            final double var25 = var15 * 0.05 * (1.0 - (var24 & 0x1) * 2.5);
            var13.Â();
            final float var26 = var11 * var11;
            var13.Â(64 + (int)(var26 * 240.0f), 32 + (int)(var26 * 192.0f), 128 - (int)(var26 * 64.0f), 255);
            final double var27 = var24 * 0.2;
            final double var28 = var27 * 1.41;
            final double var29 = 0.0 + Math.cos(var25 + 2.356194490192345) * var28;
            final double var30 = 0.0 + Math.sin(var25 + 2.356194490192345) * var28;
            final double var31 = 0.0 + Math.cos(var25 + 0.7853981633974483) * var28;
            final double var32 = 0.0 + Math.sin(var25 + 0.7853981633974483) * var28;
            final double var33 = 0.0 + Math.cos(var25 + 3.9269908169872414) * var28;
            final double var34 = 0.0 + Math.sin(var25 + 3.9269908169872414) * var28;
            final double var35 = 0.0 + Math.cos(var25 + 5.497787143782138) * var28;
            final double var36 = 0.0 + Math.sin(var25 + 5.497787143782138) * var28;
            final double var37 = 0.0 + Math.cos(var25 + 3.141592653589793) * var27;
            final double var38 = 0.0 + Math.sin(var25 + 3.141592653589793) * var27;
            final double var39 = 0.0 + Math.cos(var25 + 0.0) * var27;
            final double var40 = 0.0 + Math.sin(var25 + 0.0) * var27;
            final double var41 = 0.0 + Math.cos(var25 + 1.5707963267948966) * var27;
            final double var42 = 0.0 + Math.sin(var25 + 1.5707963267948966) * var27;
            final double var43 = 0.0 + Math.cos(var25 + 4.71238898038469) * var27;
            final double var44 = 0.0 + Math.sin(var25 + 4.71238898038469) * var27;
            final double var45 = 0.0;
            final double var46 = 0.4999;
            final double var47 = -1.0f + var16;
            final double var48 = var21 * (0.5 / var27) + var47;
            var13.HorizonCode_Horizon_È(var37, var21, var38, var46, var48);
            var13.HorizonCode_Horizon_È(var37, 0.0, var38, var46, var47);
            var13.HorizonCode_Horizon_È(var39, 0.0, var40, var45, var47);
            var13.HorizonCode_Horizon_È(var39, var21, var40, var45, var48);
            var13.HorizonCode_Horizon_È(var41, var21, var42, var46, var48);
            var13.HorizonCode_Horizon_È(var41, 0.0, var42, var46, var47);
            var13.HorizonCode_Horizon_È(var43, 0.0, var44, var45, var47);
            var13.HorizonCode_Horizon_È(var43, var21, var44, var45, var48);
            double var49 = 0.0;
            if (p_177109_1_.Œ % 2 == 0) {
                var49 = 0.5;
            }
            var13.HorizonCode_Horizon_È(var29, var21, var30, 0.5, var49 + 0.5);
            var13.HorizonCode_Horizon_È(var31, var21, var32, 1.0, var49 + 0.5);
            var13.HorizonCode_Horizon_È(var35, var21, var36, 1.0, var49);
            var13.HorizonCode_Horizon_È(var33, var21, var34, 0.5, var49);
            var12.Â();
            GlStateManager.Ê();
        }
    }
    
    protected void HorizonCode_Horizon_È(final EntityGuardian p_177112_1_, final float p_177112_2_) {
        if (p_177112_1_.¥Ðƒá()) {
            GlStateManager.HorizonCode_Horizon_È(2.35f, 2.35f, 2.35f);
        }
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityGuardian p_177111_1_) {
        return p_177111_1_.¥Ðƒá() ? RenderGuardian.áˆºÑ¢Õ : RenderGuardian.Âµá€;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityGuardian)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityLiving p_177104_1_, final ICamera p_177104_2_, final double p_177104_3_, final double p_177104_5_, final double p_177104_7_) {
        return this.HorizonCode_Horizon_È((EntityGuardian)p_177104_1_, p_177104_2_, p_177104_3_, p_177104_5_, p_177104_7_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((EntityGuardian)p_77041_1_, p_77041_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityGuardian)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityGuardian)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityGuardian)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Entity p_177071_1_, final ICamera p_177071_2_, final double p_177071_3_, final double p_177071_5_, final double p_177071_7_) {
        return this.HorizonCode_Horizon_È((EntityGuardian)p_177071_1_, p_177071_2_, p_177071_3_, p_177071_5_, p_177071_7_);
    }
}
