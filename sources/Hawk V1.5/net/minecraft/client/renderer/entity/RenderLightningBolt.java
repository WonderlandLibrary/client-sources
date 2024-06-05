package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.ResourceLocation;

public class RenderLightningBolt extends Render {
   private static final String __OBFID = "CL_00001011";

   public RenderLightningBolt(RenderManager var1) {
      super(var1);
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.getEntityTexture((EntityLightningBolt)var1);
   }

   public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      this.doRender((EntityLightningBolt)var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation getEntityTexture(EntityLightningBolt var1) {
      return null;
   }

   public void doRender(EntityLightningBolt var1, double var2, double var4, double var6, float var8, float var9) {
      Tessellator var10 = Tessellator.getInstance();
      WorldRenderer var11 = var10.getWorldRenderer();
      GlStateManager.func_179090_x();
      GlStateManager.disableLighting();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 1);
      double[] var12 = new double[8];
      double[] var13 = new double[8];
      double var14 = 0.0D;
      double var16 = 0.0D;
      Random var18 = new Random(var1.boltVertex);

      int var19;
      for(var19 = 7; var19 >= 0; --var19) {
         var12[var19] = var14;
         var13[var19] = var16;
         var14 += (double)(var18.nextInt(11) - 5);
         var16 += (double)(var18.nextInt(11) - 5);
      }

      for(var19 = 0; var19 < 4; ++var19) {
         Random var20 = new Random(var1.boltVertex);

         for(int var21 = 0; var21 < 3; ++var21) {
            int var22 = 7;
            int var23 = 0;
            if (var21 > 0) {
               var22 = 7 - var21;
            }

            if (var21 > 0) {
               var23 = var22 - 2;
            }

            double var24 = var12[var22] - var14;
            double var26 = var13[var22] - var16;

            for(int var28 = var22; var28 >= var23; --var28) {
               double var29 = var24;
               double var31 = var26;
               if (var21 == 0) {
                  var24 += (double)(var20.nextInt(11) - 5);
                  var26 += (double)(var20.nextInt(11) - 5);
               } else {
                  var24 += (double)(var20.nextInt(31) - 15);
                  var26 += (double)(var20.nextInt(31) - 15);
               }

               var11.startDrawing(5);
               float var33 = 0.5F;
               var11.func_178960_a(0.9F * var33, 0.9F * var33, 1.0F * var33, 0.3F);
               double var34 = 0.1D + (double)var19 * 0.2D;
               if (var21 == 0) {
                  var34 *= (double)var28 * 0.1D + 1.0D;
               }

               double var36 = 0.1D + (double)var19 * 0.2D;
               if (var21 == 0) {
                  var36 *= (double)(var28 - 1) * 0.1D + 1.0D;
               }

               for(int var38 = 0; var38 < 5; ++var38) {
                  double var39 = var2 + 0.5D - var34;
                  double var41 = var6 + 0.5D - var34;
                  if (var38 == 1 || var38 == 2) {
                     var39 += var34 * 2.0D;
                  }

                  if (var38 == 2 || var38 == 3) {
                     var41 += var34 * 2.0D;
                  }

                  double var43 = var2 + 0.5D - var36;
                  double var45 = var6 + 0.5D - var36;
                  if (var38 == 1 || var38 == 2) {
                     var43 += var36 * 2.0D;
                  }

                  if (var38 == 2 || var38 == 3) {
                     var45 += var36 * 2.0D;
                  }

                  var11.addVertex(var43 + var24, var4 + (double)(var28 * 16), var45 + var26);
                  var11.addVertex(var39 + var29, var4 + (double)((var28 + 1) * 16), var41 + var31);
               }

               var10.draw();
            }
         }
      }

      GlStateManager.disableBlend();
      GlStateManager.enableLighting();
      GlStateManager.func_179098_w();
   }
}
