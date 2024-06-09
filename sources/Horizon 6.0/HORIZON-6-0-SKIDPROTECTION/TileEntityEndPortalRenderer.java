package HORIZON-6-0-SKIDPROTECTION;

import java.nio.FloatBuffer;
import java.util.Random;

public class TileEntityEndPortalRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation_1975012498 Ø­áŒŠá;
    private static final ResourceLocation_1975012498 Âµá€;
    private static final Random Ó;
    FloatBuffer HorizonCode_Horizon_È;
    private static final String à = "CL_00002467";
    
    static {
        Ø­áŒŠá = new ResourceLocation_1975012498("textures/environment/end_sky.png");
        Âµá€ = new ResourceLocation_1975012498("textures/entity/end_portal.png");
        Ó = new Random(31100L);
    }
    
    public TileEntityEndPortalRenderer() {
        this.HorizonCode_Horizon_È = GLAllocation.Âµá€(16);
    }
    
    public void HorizonCode_Horizon_È(final TileEntityEndPortal p_180544_1_, final double p_180544_2_, final double p_180544_4_, final double p_180544_6_, final float p_180544_8_, final int p_180544_9_) {
        final float var10 = (float)this.Ý.áˆºÑ¢Õ;
        final float var11 = (float)this.Ý.ÂµÈ;
        final float var12 = (float)this.Ý.á;
        GlStateManager.Ó();
        TileEntityEndPortalRenderer.Ó.setSeed(31100L);
        final float var13 = 0.75f;
        for (int var14 = 0; var14 < 16; ++var14) {
            GlStateManager.Çªà¢();
            float var15 = 16 - var14;
            float var16 = 0.0625f;
            float var17 = 1.0f / (var15 + 1.0f);
            if (var14 == 0) {
                this.HorizonCode_Horizon_È(TileEntityEndPortalRenderer.Ø­áŒŠá);
                var17 = 0.1f;
                var15 = 65.0f;
                var16 = 0.125f;
                GlStateManager.á();
                GlStateManager.Â(770, 771);
            }
            if (var14 >= 1) {
                this.HorizonCode_Horizon_È(TileEntityEndPortalRenderer.Âµá€);
            }
            if (var14 == 1) {
                GlStateManager.á();
                GlStateManager.Â(1, 1);
                var16 = 0.5f;
            }
            final float var18 = (float)(-(p_180544_4_ + var13));
            float var19 = var18 + (float)ActiveRenderInfo.HorizonCode_Horizon_È().Â;
            final float var20 = var18 + var15 + (float)ActiveRenderInfo.HorizonCode_Horizon_È().Â;
            float var21 = var19 / var20;
            var21 += (float)(p_180544_4_ + var13);
            GlStateManager.Â(var10, var21, var12);
            GlStateManager.HorizonCode_Horizon_È(GlStateManager.£à.HorizonCode_Horizon_È, 9217);
            GlStateManager.HorizonCode_Horizon_È(GlStateManager.£à.Â, 9217);
            GlStateManager.HorizonCode_Horizon_È(GlStateManager.£à.Ý, 9217);
            GlStateManager.HorizonCode_Horizon_È(GlStateManager.£à.Ø­áŒŠá, 9216);
            GlStateManager.HorizonCode_Horizon_È(GlStateManager.£à.HorizonCode_Horizon_È, 9473, this.HorizonCode_Horizon_È(1.0f, 0.0f, 0.0f, 0.0f));
            GlStateManager.HorizonCode_Horizon_È(GlStateManager.£à.Â, 9473, this.HorizonCode_Horizon_È(0.0f, 0.0f, 1.0f, 0.0f));
            GlStateManager.HorizonCode_Horizon_È(GlStateManager.£à.Ý, 9473, this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f));
            GlStateManager.HorizonCode_Horizon_È(GlStateManager.£à.Ø­áŒŠá, 9474, this.HorizonCode_Horizon_È(0.0f, 1.0f, 0.0f, 0.0f));
            GlStateManager.HorizonCode_Horizon_È(GlStateManager.£à.HorizonCode_Horizon_È);
            GlStateManager.HorizonCode_Horizon_È(GlStateManager.£à.Â);
            GlStateManager.HorizonCode_Horizon_È(GlStateManager.£à.Ý);
            GlStateManager.HorizonCode_Horizon_È(GlStateManager.£à.Ø­áŒŠá);
            GlStateManager.Ê();
            GlStateManager.á(5890);
            GlStateManager.Çªà¢();
            GlStateManager.ŒÏ();
            GlStateManager.Â(0.0f, Minecraft.áƒ() % 700000L / 700000.0f, 0.0f);
            GlStateManager.HorizonCode_Horizon_È(var16, var16, var16);
            GlStateManager.Â(0.5f, 0.5f, 0.0f);
            GlStateManager.Â((var14 * var14 * 4321 + var14 * 9) * 2.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.Â(-0.5f, -0.5f, 0.0f);
            GlStateManager.Â(-var10, -var12, -var11);
            var19 = var18 + (float)ActiveRenderInfo.HorizonCode_Horizon_È().Â;
            GlStateManager.Â((float)ActiveRenderInfo.HorizonCode_Horizon_È().HorizonCode_Horizon_È * var15 / var19, (float)ActiveRenderInfo.HorizonCode_Horizon_È().Ý * var15 / var19, -var11);
            final Tessellator var22 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var23 = var22.Ý();
            var23.Â();
            float var24 = TileEntityEndPortalRenderer.Ó.nextFloat() * 0.5f + 0.1f;
            float var25 = TileEntityEndPortalRenderer.Ó.nextFloat() * 0.5f + 0.4f;
            float var26 = TileEntityEndPortalRenderer.Ó.nextFloat() * 0.5f + 0.5f;
            if (var14 == 0) {
                var26 = 1.0f;
                var25 = 1.0f;
                var24 = 1.0f;
            }
            var23.HorizonCode_Horizon_È(var24 * var17, var25 * var17, var26 * var17, 1.0f);
            var23.Â(p_180544_2_, p_180544_4_ + var13, p_180544_6_);
            var23.Â(p_180544_2_, p_180544_4_ + var13, p_180544_6_ + 1.0);
            var23.Â(p_180544_2_ + 1.0, p_180544_4_ + var13, p_180544_6_ + 1.0);
            var23.Â(p_180544_2_ + 1.0, p_180544_4_ + var13, p_180544_6_);
            var22.Â();
            GlStateManager.Ê();
            GlStateManager.á(5888);
        }
        GlStateManager.ÂµÈ();
        GlStateManager.Â(GlStateManager.£à.HorizonCode_Horizon_È);
        GlStateManager.Â(GlStateManager.£à.Â);
        GlStateManager.Â(GlStateManager.£à.Ý);
        GlStateManager.Â(GlStateManager.£à.Ø­áŒŠá);
        GlStateManager.Âµá€();
    }
    
    private FloatBuffer HorizonCode_Horizon_È(final float p_147525_1_, final float p_147525_2_, final float p_147525_3_, final float p_147525_4_) {
        this.HorizonCode_Horizon_È.clear();
        this.HorizonCode_Horizon_È.put(p_147525_1_).put(p_147525_2_).put(p_147525_3_).put(p_147525_4_);
        this.HorizonCode_Horizon_È.flip();
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_, final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
        this.HorizonCode_Horizon_È((TileEntityEndPortal)p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
    }
}
