package net.minecraft.src;

import org.lwjgl.opengl.*;
import java.util.*;

public class RenderLightningBolt extends Render
{
    public void doRenderLightningBolt(final EntityLightningBolt par1EntityLightningBolt, final double par2, final double par4, final double par6, final float par8, final float par9) {
        final Tessellator var10 = Tessellator.instance;
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        final double[] var11 = new double[8];
        final double[] var12 = new double[8];
        double var13 = 0.0;
        double var14 = 0.0;
        final Random var15 = new Random(par1EntityLightningBolt.boltVertex);
        for (int var16 = 7; var16 >= 0; --var16) {
            var11[var16] = var13;
            var12[var16] = var14;
            var13 += var15.nextInt(11) - 5;
            var14 += var15.nextInt(11) - 5;
        }
        for (int var16 = 0; var16 < 4; ++var16) {
            final Random var17 = new Random(par1EntityLightningBolt.boltVertex);
            for (int var18 = 0; var18 < 3; ++var18) {
                int var19 = 7;
                int var20 = 0;
                if (var18 > 0) {
                    var19 = 7 - var18;
                }
                if (var18 > 0) {
                    var20 = var19 - 2;
                }
                double var21 = var11[var19] - var13;
                double var22 = var12[var19] - var14;
                for (int var23 = var19; var23 >= var20; --var23) {
                    final double var24 = var21;
                    final double var25 = var22;
                    if (var18 == 0) {
                        var21 += var17.nextInt(11) - 5;
                        var22 += var17.nextInt(11) - 5;
                    }
                    else {
                        var21 += var17.nextInt(31) - 15;
                        var22 += var17.nextInt(31) - 15;
                    }
                    var10.startDrawing(5);
                    final float var26 = 0.5f;
                    var10.setColorRGBA_F(0.9f * var26, 0.9f * var26, 1.0f * var26, 0.3f);
                    double var27 = 0.1 + var16 * 0.2;
                    if (var18 == 0) {
                        var27 *= var23 * 0.1 + 1.0;
                    }
                    double var28 = 0.1 + var16 * 0.2;
                    if (var18 == 0) {
                        var28 *= (var23 - 1) * 0.1 + 1.0;
                    }
                    for (int var29 = 0; var29 < 5; ++var29) {
                        double var30 = par2 + 0.5 - var27;
                        double var31 = par6 + 0.5 - var27;
                        if (var29 == 1 || var29 == 2) {
                            var30 += var27 * 2.0;
                        }
                        if (var29 == 2 || var29 == 3) {
                            var31 += var27 * 2.0;
                        }
                        double var32 = par2 + 0.5 - var28;
                        double var33 = par6 + 0.5 - var28;
                        if (var29 == 1 || var29 == 2) {
                            var32 += var28 * 2.0;
                        }
                        if (var29 == 2 || var29 == 3) {
                            var33 += var28 * 2.0;
                        }
                        var10.addVertex(var32 + var21, par4 + var23 * 16, var33 + var22);
                        var10.addVertex(var30 + var24, par4 + (var23 + 1) * 16, var31 + var25);
                    }
                    var10.draw();
                }
            }
        }
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.doRenderLightningBolt((EntityLightningBolt)par1Entity, par2, par4, par6, par8, par9);
    }
}
