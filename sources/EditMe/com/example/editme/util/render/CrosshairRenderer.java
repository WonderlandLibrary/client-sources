package com.example.editme.util.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class CrosshairRenderer {
   public static int getTextWidth(String var0) {
      return Minecraft.func_71410_x().field_71466_p.func_78256_a(var0);
   }

   public static void postRender() {
      GL11.glEnable(3553);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawRectangle(float var0, float var1, float var2, float var3, float var4, Color var5, boolean var6) {
      drawLines(new float[]{var0, var1, var2, var1, var2, var1, var2, var3, var0, var3, var2, var3, var0, var1, var0, var3}, var4, var5, var6);
   }

   public static void drawCircle(float var0, float var1, float var2, float var3, Color var4, boolean var5) {
      drawPartialCircle(var0, var1, var2, 0, 360, var3, var4, var5);
   }

   public static void drawFilledShape(float[] var0, Color var1, boolean var2) {
      preRender();
      if (var2) {
         GL11.glEnable(2848);
      } else {
         GL11.glDisable(2848);
      }

      GL11.glColor4f((float)var1.getRed() / 255.0F, (float)var1.getGreen() / 255.0F, (float)var1.getBlue() / 255.0F, (float)var1.getAlpha() / 255.0F);
      GL11.glBegin(9);

      for(int var3 = 0; var3 < var0.length; var3 += 2) {
         GL11.glVertex2f(var0[var3], var0[var3 + 1]);
      }

      GL11.glEnd();
      postRender();
   }

   public static void drawPartialCircle(float var0, float var1, float var2, int var3, int var4, float var5, Color var6, boolean var7) {
      preRender();
      if (var3 > var4) {
         int var8 = var3;
         var3 = var4;
         var4 = var8;
      }

      if (var3 < 0) {
         var3 = 0;
      }

      if (var4 > 360) {
         var4 = 360;
      }

      if (var7) {
         GL11.glEnable(2848);
      } else {
         GL11.glDisable(2848);
      }

      GL11.glLineWidth(var5);
      GL11.glColor4f((float)var6.getRed() / 255.0F, (float)var6.getGreen() / 255.0F, (float)var6.getBlue() / 255.0F, (float)var6.getAlpha() / 255.0F);
      GL11.glBegin(3);
      float var11 = 0.01745328F;

      for(int var9 = var3; var9 <= var4; ++var9) {
         float var10 = (float)(var9 - 90) * var11;
         GL11.glVertex2f(var0 + (float)Math.cos((double)var10) * var2, var1 + (float)Math.sin((double)var10) * var2);
      }

      GL11.glEnd();
      postRender();
   }

   public static void drawFilledRectangle(float var0, float var1, float var2, float var3, Color var4, boolean var5) {
      drawFilledShape(new float[]{var0, var1, var0, var3, var2, var3, var2, var1}, var4, var5);
   }

   public static void drawLine(float var0, float var1, float var2, float var3, float var4, Color var5, boolean var6) {
      drawLines(new float[]{var0, var1, var2, var3}, var4, var5, var6);
   }

   public static void drawBorderedRectangle(float var0, float var1, float var2, float var3, float var4, Color var5, Color var6, boolean var7) {
      drawFilledRectangle(var0, var1, var2, var3, var6, var7);
      drawRectangle(var0, var1, var2, var3, var4, var5, var7);
   }

   public static void preRender() {
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
   }

   public static void drawLines(float[] var0, float var1, Color var2, boolean var3) {
      preRender();
      if (var3) {
         GL11.glEnable(2848);
      } else {
         GL11.glDisable(2848);
      }

      GL11.glLineWidth(var1);
      GL11.glColor4f((float)var2.getRed() / 255.0F, (float)var2.getGreen() / 255.0F, (float)var2.getBlue() / 255.0F, (float)var2.getAlpha() / 255.0F);
      GL11.glBegin(1);

      for(int var4 = 0; var4 < var0.length; var4 += 2) {
         GL11.glVertex2f(var0[var4], var0[var4 + 1]);
      }

      GL11.glEnd();
      postRender();
   }

   public static void drawString(String var0, int var1, int var2, Color var3) {
      GL11.glColor4f((float)var3.getRed() / 255.0F, (float)var3.getGreen() / 255.0F, (float)var3.getBlue() / 255.0F, (float)var3.getAlpha() / 255.0F);
      Minecraft.func_71410_x().field_71466_p.func_78276_b(var0, var1, var2, 0);
   }
}
