package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.ResourceLocation;

public class RenderLightningBolt extends Render {
   public RenderLightningBolt(RenderManager var1) {
      super(var1);
   }

   public void doRender(EntityLightningBolt var1, double var2, double var4, double var6, float var8, float var9) {
      Tessellator var10 = Tessellator.getInstance();
      WorldRenderer var11 = var10.getWorldRenderer();
      GlStateManager.disableTexture2D();
      GlStateManager.disableLighting();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 1);
      double[] var12 = new double[8];
      double[] var13 = new double[8];
      double var14 = 0.0D;
      double var16 = 0.0D;
      Random var18 = new Random(var1.boltVertex);
      int var19 = 7;

      while(true) {
         var12[var19] = var14;
         var13[var19] = var16;
         var14 += (double)(var18.nextInt(11) - 5);
         var16 += (double)(var18.nextInt(11) - 5);
         --var19;
      }
   }

   protected ResourceLocation getEntityTexture(EntityLightningBolt var1) {
      return null;
   }
}
