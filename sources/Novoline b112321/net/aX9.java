package net;

import java.awt.Color;
import net.Qb;
import net.av2;
import net.d3;
import net.gZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

public final class aX9 {
   private static final Minecraft d = Minecraft.getMinecraft();
   private static Qb c;
   private static Framebuffer f;
   private static final Object a = new Object();
   private static int b;
   private static int e;

   public static void a(int var0, int var1, int var2, int var3, boolean var4) {
      String[] var5 = d3.e();
      if(d.gameSettings.guiScale == 2) {
         GL11.glEnable(3089);
         a(var0, var1, var2, var3);
         GL11.glPushMatrix();
         f.framebufferRenderExt(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, true);
         GL11.glPopMatrix();
         if(var4) {
            Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
         }

         GlStateManager.enableDepth();
         GL11.glDisable(3089);
      }
   }

   public static void a(boolean var0) {
      String[] var1 = d3.e();
      if(d.gameSettings.guiScale == 2) {
         d.getFramebuffer().unbindFramebuffer();
         f.bindFramebuffer(true);
         d.getFramebuffer().framebufferRenderExt(d.displayWidth, d.displayHeight, true);
         if(OpenGlHelper.shadersSupported && c != null) {
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            c.a(d.timer.renderPartialTicks);
            GlStateManager.popMatrix();
         }

         f.unbindFramebuffer();
         d.getFramebuffer().bindFramebuffer(true);
         if(var0) {
            d.entityRenderer.setupOverlayRendering();
         }

      }
   }

   public static void a() {
      // $FF: Couldn't be decompiled
   }

   public static void a(int var0, int var1, int var2, int var3) {
      d3.e();
      var2 = var2 - var0;
      var3 = var3 - var1;
      Minecraft var5 = Minecraft.getMinecraft();
      ScaledResolution var6 = new ScaledResolution(var5);
      float var7 = 1.0F;
      switch(d.gameSettings.guiScale) {
      case 0:
         var7 = (float)(0.5D * ((av2)gZ.g().d().b(av2.class)).s());
      case 1:
         var7 = (float)(2.0D * ((av2)gZ.g().d().b(av2.class)).s());
      case 3:
         var7 = (float)(0.6666666865348816D * ((av2)gZ.g().d().b(av2.class)).s());
      case 2:
      default:
         var7 = (float)((av2)gZ.g().d().b(av2.class)).s();
         GL11.glScissor((int)((float)(var0 * var6.getScaleFactor()) * var7), (int)((float)var5.displayHeight - (float)(var1 * var6.getScaleFactor()) * var7 - (float)(var3 * var6.getScaleFactor()) * var7), (int)((float)(var2 * var6.getScaleFactor()) * var7), (int)((float)(var3 * var6.getScaleFactor()) * var7));
      }
   }

   public static int a(Color var0, Color var1, float var2) {
      int var3 = (int)((float)var0.getRed() + (float)(var1.getRed() - var0.getRed()) * var2);
      int var4 = (int)((float)var0.getGreen() + (float)(var1.getGreen() - var0.getGreen()) * var2);
      int var5 = (int)((float)var0.getBlue() + (float)(var1.getBlue() - var0.getBlue()) * var2);
      int var6 = (int)((float)var0.getAlpha() + (float)(var1.getAlpha() - var0.getAlpha()) * var2);

      try {
         return (new Color(var3, var4, var5, var6)).getRGB();
      } catch (Exception var8) {
         return -1;
      }
   }

   private aX9() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
