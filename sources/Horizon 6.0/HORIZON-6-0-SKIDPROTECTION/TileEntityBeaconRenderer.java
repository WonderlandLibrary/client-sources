package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import org.lwjgl.opengl.GL11;

public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Ø­áŒŠá = "CL_00000962";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/beacon_beam.png");
    }
    
    public void HorizonCode_Horizon_È(final TileEntityBeacon p_180536_1_, final double p_180536_2_, final double p_180536_4_, final double p_180536_6_, final float p_180536_8_, final int p_180536_9_) {
        final float var10 = p_180536_1_.Æ();
        GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
        if (var10 > 0.0f) {
            final Tessellator var11 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var12 = var11.Ý();
            final List var13 = p_180536_1_.µÕ();
            int var14 = 0;
            for (int var15 = 0; var15 < var13.size(); ++var15) {
                final TileEntityBeacon.HorizonCode_Horizon_È var16 = var13.get(var15);
                final int var17 = var14 + var16.Ý();
                this.HorizonCode_Horizon_È(TileEntityBeaconRenderer.HorizonCode_Horizon_È);
                GL11.glTexParameterf(3553, 10242, 10497.0f);
                GL11.glTexParameterf(3553, 10243, 10497.0f);
                GlStateManager.Ó();
                GlStateManager.£à();
                GlStateManager.ÂµÈ();
                GlStateManager.HorizonCode_Horizon_È(true);
                GlStateManager.HorizonCode_Horizon_È(770, 1, 1, 0);
                final float var18 = p_180536_1_.ÇŽÉ().Šáƒ() + p_180536_8_;
                final float var19 = -var18 * 0.2f - MathHelper.Ø­áŒŠá(-var18 * 0.1f);
                double var20 = var18 * 0.025 * -1.5;
                var12.Â();
                double var21 = 0.2;
                double var22 = 0.5 + Math.cos(var20 + 2.356194490192345) * var21;
                double var23 = 0.5 + Math.sin(var20 + 2.356194490192345) * var21;
                double var24 = 0.5 + Math.cos(var20 + 0.7853981633974483) * var21;
                double var25 = 0.5 + Math.sin(var20 + 0.7853981633974483) * var21;
                double var26 = 0.5 + Math.cos(var20 + 3.9269908169872414) * var21;
                double var27 = 0.5 + Math.sin(var20 + 3.9269908169872414) * var21;
                double var28 = 0.5 + Math.cos(var20 + 5.497787143782138) * var21;
                double var29 = 0.5 + Math.sin(var20 + 5.497787143782138) * var21;
                double var30 = 0.0;
                double var31 = 1.0;
                final double var32 = -1.0f + var19;
                final double var33 = var16.Ý() * var10 * (0.5 / var21) + var32;
                var12.HorizonCode_Horizon_È(var16.Â()[0], var16.Â()[1], var16.Â()[2], 0.125f);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var22, p_180536_4_ + var17, p_180536_6_ + var23, var31, var33);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var22, p_180536_4_ + var14, p_180536_6_ + var23, var31, var32);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var24, p_180536_4_ + var14, p_180536_6_ + var25, var30, var32);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var24, p_180536_4_ + var17, p_180536_6_ + var25, var30, var33);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var28, p_180536_4_ + var17, p_180536_6_ + var29, var31, var33);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var28, p_180536_4_ + var14, p_180536_6_ + var29, var31, var32);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var26, p_180536_4_ + var14, p_180536_6_ + var27, var30, var32);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var26, p_180536_4_ + var17, p_180536_6_ + var27, var30, var33);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var24, p_180536_4_ + var17, p_180536_6_ + var25, var31, var33);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var24, p_180536_4_ + var14, p_180536_6_ + var25, var31, var32);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var28, p_180536_4_ + var14, p_180536_6_ + var29, var30, var32);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var28, p_180536_4_ + var17, p_180536_6_ + var29, var30, var33);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var26, p_180536_4_ + var17, p_180536_6_ + var27, var31, var33);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var26, p_180536_4_ + var14, p_180536_6_ + var27, var31, var32);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var22, p_180536_4_ + var14, p_180536_6_ + var23, var30, var32);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var22, p_180536_4_ + var17, p_180536_6_ + var23, var30, var33);
                var11.Â();
                GlStateManager.á();
                GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
                GlStateManager.HorizonCode_Horizon_È(false);
                var12.Â();
                var12.HorizonCode_Horizon_È(var16.Â()[0], var16.Â()[1], var16.Â()[2], 0.125f);
                var20 = 0.2;
                var21 = 0.2;
                var22 = 0.8;
                var23 = 0.2;
                var24 = 0.2;
                var25 = 0.8;
                var26 = 0.8;
                var27 = 0.8;
                var28 = 0.0;
                var29 = 1.0;
                var30 = -1.0f + var19;
                var31 = var16.Ý() * var10 + var30;
                var12.HorizonCode_Horizon_È(p_180536_2_ + var20, p_180536_4_ + var17, p_180536_6_ + var21, var29, var31);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var20, p_180536_4_ + var14, p_180536_6_ + var21, var29, var30);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var22, p_180536_4_ + var14, p_180536_6_ + var23, var28, var30);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var22, p_180536_4_ + var17, p_180536_6_ + var23, var28, var31);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var26, p_180536_4_ + var17, p_180536_6_ + var27, var29, var31);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var26, p_180536_4_ + var14, p_180536_6_ + var27, var29, var30);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var24, p_180536_4_ + var14, p_180536_6_ + var25, var28, var30);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var24, p_180536_4_ + var17, p_180536_6_ + var25, var28, var31);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var22, p_180536_4_ + var17, p_180536_6_ + var23, var29, var31);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var22, p_180536_4_ + var14, p_180536_6_ + var23, var29, var30);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var26, p_180536_4_ + var14, p_180536_6_ + var27, var28, var30);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var26, p_180536_4_ + var17, p_180536_6_ + var27, var28, var31);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var24, p_180536_4_ + var17, p_180536_6_ + var25, var29, var31);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var24, p_180536_4_ + var14, p_180536_6_ + var25, var29, var30);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var20, p_180536_4_ + var14, p_180536_6_ + var21, var28, var30);
                var12.HorizonCode_Horizon_È(p_180536_2_ + var20, p_180536_4_ + var17, p_180536_6_ + var21, var28, var31);
                var11.Â();
                GlStateManager.Âµá€();
                GlStateManager.µÕ();
                GlStateManager.HorizonCode_Horizon_È(true);
                var14 = var17;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_, final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
        this.HorizonCode_Horizon_È((TileEntityBeacon)p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
    }
}
