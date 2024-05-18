package HORIZON-6-0-SKIDPROTECTION;

public class RenderMinecart extends Render
{
    private static final ResourceLocation_1975012498 Âµá€;
    protected ModelBase HorizonCode_Horizon_È;
    private static final String Ó = "CL_00001013";
    
    static {
        Âµá€ = new ResourceLocation_1975012498("textures/entity/minecart.png");
    }
    
    public RenderMinecart(final RenderManager p_i46155_1_) {
        super(p_i46155_1_);
        this.HorizonCode_Horizon_È = new ModelMinecart();
        this.Ý = 0.5f;
    }
    
    public void HorizonCode_Horizon_È(final EntityMinecart p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, final float p_76986_9_) {
        GlStateManager.Çªà¢();
        this.Ý(p_76986_1_);
        long var10 = p_76986_1_.ˆá() * 493286711L;
        var10 = var10 * var10 * 4392167121L + var10 * 98761L;
        final float var11 = (((var10 >> 16 & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        final float var12 = (((var10 >> 20 & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        final float var13 = (((var10 >> 24 & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        GlStateManager.Â(var11, var12, var13);
        final double var14 = p_76986_1_.áˆºáˆºÈ + (p_76986_1_.ŒÏ - p_76986_1_.áˆºáˆºÈ) * p_76986_9_;
        final double var15 = p_76986_1_.ÇŽá€ + (p_76986_1_.Çªà¢ - p_76986_1_.ÇŽá€) * p_76986_9_;
        final double var16 = p_76986_1_.Ï + (p_76986_1_.Ê - p_76986_1_.Ï) * p_76986_9_;
        final double var17 = 0.30000001192092896;
        final Vec3 var18 = p_76986_1_.ÂµÈ(var14, var15, var16);
        float var19 = p_76986_1_.Õ + (p_76986_1_.áƒ - p_76986_1_.Õ) * p_76986_9_;
        if (var18 != null) {
            Vec3 var20 = p_76986_1_.HorizonCode_Horizon_È(var14, var15, var16, var17);
            Vec3 var21 = p_76986_1_.HorizonCode_Horizon_È(var14, var15, var16, -var17);
            if (var20 == null) {
                var20 = var18;
            }
            if (var21 == null) {
                var21 = var18;
            }
            p_76986_2_ += var18.HorizonCode_Horizon_È - var14;
            p_76986_4_ += (var20.Â + var21.Â) / 2.0 - var15;
            p_76986_6_ += var18.Ý - var16;
            Vec3 var22 = var21.Â(-var20.HorizonCode_Horizon_È, -var20.Â, -var20.Ý);
            if (var22.Â() != 0.0) {
                var22 = var22.HorizonCode_Horizon_È();
                p_76986_8_ = (float)(Math.atan2(var22.Ý, var22.HorizonCode_Horizon_È) * 180.0 / 3.141592653589793);
                var19 = (float)(Math.atan(var22.Â) * 73.0);
            }
        }
        GlStateManager.Â((float)p_76986_2_, (float)p_76986_4_ + 0.375f, (float)p_76986_6_);
        GlStateManager.Â(180.0f - p_76986_8_, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(-var19, 0.0f, 0.0f, 1.0f);
        final float var23 = p_76986_1_.Ø­à() - p_76986_9_;
        float var24 = p_76986_1_.¥Æ() - p_76986_9_;
        if (var24 < 0.0f) {
            var24 = 0.0f;
        }
        if (var23 > 0.0f) {
            GlStateManager.Â(MathHelper.HorizonCode_Horizon_È(var23) * var23 * var24 / 10.0f * p_76986_1_.µÕ(), 1.0f, 0.0f, 0.0f);
        }
        final int var25 = p_76986_1_.Šáƒ();
        final IBlockState var26 = p_76986_1_.Æ();
        if (var26.Ý().ÂµÈ() != -1) {
            GlStateManager.Çªà¢();
            this.HorizonCode_Horizon_È(TextureMap.à);
            final float var27 = 0.75f;
            GlStateManager.HorizonCode_Horizon_È(var27, var27, var27);
            GlStateManager.Â(-0.5f, (var25 - 8) / 16.0f, 0.5f);
            this.HorizonCode_Horizon_È(p_76986_1_, p_76986_9_, var26);
            GlStateManager.Ê();
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            this.Ý(p_76986_1_);
        }
        GlStateManager.HorizonCode_Horizon_È(-1.0f, -1.0f, 1.0f);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_76986_1_, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GlStateManager.Ê();
        super.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityMinecart p_110775_1_) {
        return RenderMinecart.Âµá€;
    }
    
    protected void HorizonCode_Horizon_È(final EntityMinecart p_180560_1_, final float p_180560_2_, final IBlockState p_180560_3_) {
        GlStateManager.Çªà¢();
        Minecraft.áŒŠà().Ô().HorizonCode_Horizon_È(p_180560_3_, p_180560_1_.Â(p_180560_2_));
        GlStateManager.Ê();
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityMinecart)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityMinecart)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
