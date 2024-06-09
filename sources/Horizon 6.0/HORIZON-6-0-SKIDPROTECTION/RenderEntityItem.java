package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class RenderEntityItem extends Render
{
    private final RenderItem HorizonCode_Horizon_È;
    private Random Âµá€;
    private static final String Ó = "CL_00002442";
    
    public RenderEntityItem(final RenderManager p_i46167_1_, final RenderItem p_i46167_2_) {
        super(p_i46167_1_);
        this.Âµá€ = new Random();
        this.HorizonCode_Horizon_È = p_i46167_2_;
        this.Ý = 0.15f;
        this.Ø­áŒŠá = 0.75f;
    }
    
    private int HorizonCode_Horizon_È(final EntityItem p_177077_1_, final double p_177077_2_, final double p_177077_4_, final double p_177077_6_, final float p_177077_8_, final IBakedModel p_177077_9_) {
        final ItemStack var10 = p_177077_1_.Ø();
        final Item_1028566121 var11 = var10.HorizonCode_Horizon_È();
        if (var11 == null) {
            return 0;
        }
        final boolean var12 = p_177077_9_.Ý();
        final int var13 = this.HorizonCode_Horizon_È(var10);
        final float var14 = 0.25f;
        final float var15 = MathHelper.HorizonCode_Horizon_È((p_177077_1_.µà() + p_177077_8_) / 10.0f + p_177077_1_.HorizonCode_Horizon_È) * 0.1f + 0.1f;
        GlStateManager.Â((float)p_177077_2_, (float)p_177077_4_ + var15 + 0.25f, (float)p_177077_6_);
        if (var12 || (this.Â.áˆºÑ¢Õ != null && this.Â.áˆºÑ¢Õ.Û)) {
            final float var16 = ((p_177077_1_.µà() + p_177077_8_) / 20.0f + p_177077_1_.HorizonCode_Horizon_È) * 57.295776f;
            GlStateManager.Â(var16, 0.0f, 1.0f, 0.0f);
        }
        if (!var12) {
            final float var16 = -0.0f * (var13 - 1) * 0.5f;
            final float var17 = -0.0f * (var13 - 1) * 0.5f;
            final float var18 = -0.046875f * (var13 - 1) * 0.5f;
            GlStateManager.Â(var16, var17, var18);
        }
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        return var13;
    }
    
    private int HorizonCode_Horizon_È(final ItemStack p_177078_1_) {
        byte var2 = 1;
        if (p_177078_1_.Â > 48) {
            var2 = 5;
        }
        else if (p_177078_1_.Â > 32) {
            var2 = 4;
        }
        else if (p_177078_1_.Â > 16) {
            var2 = 3;
        }
        else if (p_177078_1_.Â > 1) {
            var2 = 2;
        }
        return var2;
    }
    
    public void HorizonCode_Horizon_È(final EntityItem p_177075_1_, final double p_177075_2_, final double p_177075_4_, final double p_177075_6_, final float p_177075_8_, final float p_177075_9_) {
        final ItemStack var10 = p_177075_1_.Ø();
        this.Âµá€.setSeed(187L);
        boolean var11 = false;
        if (this.Ý(p_177075_1_)) {
            this.Â.Ø­áŒŠá.Â(this.HorizonCode_Horizon_È(p_177075_1_)).Â(false, false);
            var11 = true;
        }
        GlStateManager.ŠÄ();
        GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
        GlStateManager.á();
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
        GlStateManager.Çªà¢();
        final IBakedModel var12 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È().HorizonCode_Horizon_È(var10);
        for (int var13 = this.HorizonCode_Horizon_È(p_177075_1_, p_177075_2_, p_177075_4_, p_177075_6_, p_177075_9_, var12), var14 = 0; var14 < var13; ++var14) {
            if (var12.Ý()) {
                GlStateManager.Çªà¢();
                if (var14 > 0) {
                    final float var15 = (this.Âµá€.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    final float var16 = (this.Âµá€.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    final float var17 = (this.Âµá€.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    GlStateManager.Â(var15, var16, var17);
                }
                GlStateManager.HorizonCode_Horizon_È(0.5f, 0.5f, 0.5f);
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var10, var12);
                GlStateManager.Ê();
            }
            else {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var10, var12);
                GlStateManager.Â(0.0f, 0.0f, 0.046875f);
            }
        }
        GlStateManager.Ê();
        GlStateManager.Ñ¢á();
        GlStateManager.ÂµÈ();
        this.Ý(p_177075_1_);
        if (var11) {
            this.Â.Ø­áŒŠá.Â(this.HorizonCode_Horizon_È(p_177075_1_)).Ø­áŒŠá();
        }
        super.HorizonCode_Horizon_È(p_177075_1_, p_177075_2_, p_177075_4_, p_177075_6_, p_177075_8_, p_177075_9_);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityItem p_177076_1_) {
        return TextureMap.à;
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityItem)p_110775_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityItem)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
