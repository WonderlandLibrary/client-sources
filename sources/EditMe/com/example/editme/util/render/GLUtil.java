package com.example.editme.util.render;

import com.example.editme.util.client.ReflectionFields;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class GLUtil {
   public static float playerViewY;
   private static ScaledResolution scaledResolution;
   public static float playerViewX;

   public static void drawGradientHRect(float var0, float var1, float var2, float var3, int var4, int var5) {
      enableGL2D();
      GL11.glShadeModel(7425);
      GL11.glBegin(7);
      glColor((long)var4);
      GL11.glVertex2f(var0, var1);
      GL11.glVertex2f(var0, var3);
      glColor((long)var5);
      GL11.glVertex2f(var2, var3);
      GL11.glVertex2f(var2, var1);
      GL11.glEnd();
      GL11.glShadeModel(7424);
      disableGL2D();
   }

   public static double[] interpolate(Entity var0) {
      double var1 = interpolate(var0.field_70165_t, var0.field_70142_S) - ReflectionFields.getRenderPosX();
      double var3 = interpolate(var0.field_70163_u, var0.field_70137_T) - ReflectionFields.getRenderPosY();
      double var5 = interpolate(var0.field_70161_v, var0.field_70136_U) - ReflectionFields.getRenderPosZ();
      return new double[]{var1, var3, var5};
   }

   public static void drawCircle(float var0, float var1, float var2, int var3, int var4) {
      var2 *= 2.0F;
      var0 *= 2.0F;
      var1 *= 2.0F;
      float var5 = (float)(var4 >> 24 & 255) / 255.0F;
      float var6 = (float)(var4 >> 16 & 255) / 255.0F;
      float var7 = (float)(var4 >> 8 & 255) / 255.0F;
      float var8 = (float)(var4 & 255) / 255.0F;
      float var9 = (float)(6.2831852D / (double)var3);
      float var10 = (float)Math.cos((double)var9);
      float var11 = (float)Math.sin((double)var9);
      float var13 = var2;
      float var14 = 0.0F;
      enableGL2D();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GL11.glColor4f(var6, var7, var8, var5);
      GL11.glBegin(2);

      for(int var15 = 0; var15 < var3; ++var15) {
         GL11.glVertex2f(var13 + var0, var14 + var1);
         float var12 = var13;
         var13 = var10 * var13 - var11 * var14;
         var14 = var11 * var12 + var10 * var14;
      }

      GL11.glEnd();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
   }

   public static int createShader(String var0, int var1) throws Exception {
      byte var2 = 0;

      try {
         int var5 = ARBShaderObjects.glCreateShaderObjectARB(var1);
         if (var5 == 0) {
            return 0;
         } else {
            ARBShaderObjects.glShaderSourceARB(var5, var0);
            ARBShaderObjects.glCompileShaderARB(var5);
            if (ARBShaderObjects.glGetObjectParameteriARB(var5, 35713) == 0) {
               throw new RuntimeException(String.valueOf((new StringBuilder()).append("Error creating shader: ").append(getLogInfo(var5))));
            } else {
               return var5;
            }
         }
      } catch (Exception var4) {
         ARBShaderObjects.glDeleteObjectARB(var2);
         throw var4;
      }
   }

   private GLUtil() {
   }

   public static void drawHLine(float var0, float var1, float var2, int var3, int var4) {
      if (var1 < var0) {
         float var5 = var0;
         var0 = var1;
         var1 = var5;
      }

      drawGradientRect(var0, var2, var1 + 1.0F, var2 + 1.0F, var3, var4);
   }

   public static void renderTexture(int var0, int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      float var10 = var6 / (float)var0;
      float var11 = var7 / (float)var1;
      float var12 = var8 / (float)var0;
      float var13 = var9 / (float)var1;
      boolean var14 = GL11.glGetBoolean(3553);
      boolean var15 = GL11.glGetBoolean(3042);
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3553);
      GL11.glBegin(4);
      GL11.glTexCoord2f(var10 + var12, var11);
      GL11.glVertex2f(var2 + var4, var3);
      GL11.glTexCoord2f(var10, var11);
      GL11.glVertex2f(var2, var3);
      GL11.glTexCoord2f(var10, var11 + var13);
      GL11.glVertex2f(var2, var3 + var5);
      GL11.glTexCoord2f(var10, var11 + var13);
      GL11.glVertex2f(var2, var3 + var5);
      GL11.glTexCoord2f(var10 + var12, var11 + var13);
      GL11.glVertex2f(var2 + var4, var3 + var5);
      GL11.glTexCoord2f(var10 + var12, var11);
      GL11.glVertex2f(var2 + var4, var3);
      GL11.glEnd();
      if (!var14) {
         GL11.glDisable(3553);
      }

      if (!var15) {
         GL11.glDisable(3042);
      }

      GL11.glPopMatrix();
   }

   public static void drawRect(float var0, float var1, float var2, float var3) {
      GL11.glBegin(7);
      GL11.glVertex2f(var0, var3);
      GL11.glVertex2f(var2, var3);
      GL11.glVertex2f(var2, var1);
      GL11.glVertex2f(var0, var1);
      GL11.glEnd();
   }

   public static int applyTexture(int var0, int var1, int var2, ByteBuffer var3, boolean var4, boolean var5) {
      GL11.glBindTexture(3553, var0);
      GL11.glTexParameteri(3553, 10241, var4 ? 9729 : 9728);
      GL11.glTexParameteri(3553, 10240, var4 ? 9729 : 9728);
      GL11.glTexParameteri(3553, 10242, var5 ? 10497 : 10496);
      GL11.glTexParameteri(3553, 10243, var5 ? 10497 : 10496);
      GL11.glPixelStorei(3317, 1);
      GL11.glTexImage2D(3553, 0, 32856, var1, var2, 0, 6408, 5121, var3);
      return var0;
   }

   public static void drawHorizontalLine(float var0, float var1, float var2, int var3) {
      drawRect(var0, var1, var2, var1 + 1.0F, (long)var3);
   }

   public static void drawRect(Rectangle var0, int var1) {
      drawRect((float)var0.x, (float)var0.y, (float)(var0.x + var0.width), (float)(var0.y + var0.height), (long)var1);
   }

   public static void drawGradientBorderedRect(double var0, double var2, double var4, double var6, float var8, int var9, int var10, int var11) {
      enableGL2D();
      GL11.glPushMatrix();
      glColor((long)var9);
      GL11.glLineWidth(1.0F);
      GL11.glBegin(1);
      GL11.glVertex2d(var0, var2);
      GL11.glVertex2d(var0, var6);
      GL11.glVertex2d(var4, var6);
      GL11.glVertex2d(var4, var2);
      GL11.glVertex2d(var0, var2);
      GL11.glVertex2d(var4, var2);
      GL11.glVertex2d(var0, var6);
      GL11.glVertex2d(var4, var6);
      GL11.glEnd();
      GL11.glPopMatrix();
      drawGradientRect(var0, var2, var4, var6, var10, var11);
      disableGL2D();
   }

   public static void enableGL3D(float var0) {
      GL11.glDisable(3553);
      Minecraft.func_71410_x().field_71460_t.func_175072_h();
      GL11.glEnable(2848);
      GL11.glLineWidth(var0);
   }

   public static int applyTexture(int var0, BufferedImage var1, boolean var2, boolean var3) {
      int[] var4 = new int[var1.getWidth() * var1.getHeight()];
      var1.getRGB(0, 0, var1.getWidth(), var1.getHeight(), var4, 0, var1.getWidth());
      ByteBuffer var5 = BufferUtils.createByteBuffer(var1.getWidth() * var1.getHeight() * 4);

      for(int var6 = 0; var6 < var1.getHeight(); ++var6) {
         for(int var7 = 0; var7 < var1.getWidth(); ++var7) {
            int var8 = var4[var6 * var1.getWidth() + var7];
            var5.put((byte)(var8 >> 16 & 255));
            var5.put((byte)(var8 >> 8 & 255));
            var5.put((byte)(var8 & 255));
            var5.put((byte)(var8 >> 24 & 255));
         }
      }

      var5.flip();
      applyTexture(var0, var1.getWidth(), var1.getHeight(), var5, var2, var3);
      return var0;
   }

   public static void prepareScissorBox(float var0, float var1, float var2, float var3) {
      updateScaledResolution();
      int var4 = scaledResolution.func_78325_e();
      GL11.glScissor((int)(var0 * (float)var4), (int)(((float)scaledResolution.func_78328_b() - var3) * (float)var4), (int)((var2 - var0) * (float)var4), (int)((var3 - var1) * (float)var4));
   }

   public static void glColor(long var0) {
      float var2 = (float)(var0 >> 24 & 255L) / 255.0F;
      float var3 = (float)(var0 >> 16 & 255L) / 255.0F;
      float var4 = (float)(var0 >> 8 & 255L) / 255.0F;
      float var5 = (float)(var0 & 255L) / 255.0F;
      GL11.glColor4f(var3, var4, var5, var2);
   }

   public static String getLogInfo(int var0) {
      return ARBShaderObjects.glGetInfoLogARB(var0, ARBShaderObjects.glGetObjectParameteriARB(var0, 35716));
   }

   public static void drawOutlinedBox(Box var0) {
      if (var0 != null) {
         GL11.glBegin(3);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glEnd();
         GL11.glBegin(1);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glEnd();
      }
   }

   public static void drawVLine(float var0, float var1, float var2, int var3) {
      if (var2 < var1) {
         float var4 = var1;
         var1 = var2;
         var2 = var4;
      }

      drawRect(var0, var1 + 1.0F, var0 + 1.0F, var2, (long)var3);
   }

   public static void drawFullCircle(int var0, int var1, double var2, int var4) {
      var2 *= 2.0D;
      var0 *= 2;
      var1 *= 2;
      float var5 = (float)(var4 >> 24 & 255) / 255.0F;
      float var6 = (float)(var4 >> 16 & 255) / 255.0F;
      float var7 = (float)(var4 >> 8 & 255) / 255.0F;
      float var8 = (float)(var4 & 255) / 255.0F;
      enableGL2D();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GL11.glColor4f(var6, var7, var8, var5);
      GL11.glBegin(6);

      for(int var9 = 0; var9 <= 360; ++var9) {
         double var10 = Math.sin((double)var9 * 3.141592653589793D / 180.0D) * var2;
         double var12 = Math.cos((double)var9 * 3.141592653589793D / 180.0D) * var2;
         GL11.glVertex2d((double)var0 + var10, (double)var1 + var12);
      }

      GL11.glEnd();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
   }

   public static void drawVerticalLine(float var0, float var1, float var2, int var3) {
      drawRect(var0, var1, var0 + 1.0F, var2, (long)var3);
   }

   public static void drawGradientRect(float var0, float var1, float var2, float var3, int var4, int var5) {
      enableGL2D();
      GL11.glShadeModel(7425);
      GL11.glBegin(7);
      glColor((long)var4);
      GL11.glVertex2f(var0, var3);
      GL11.glVertex2f(var2, var3);
      glColor((long)var5);
      GL11.glVertex2f(var2, var1);
      GL11.glVertex2f(var0, var1);
      GL11.glEnd();
      GL11.glShadeModel(7424);
      disableGL2D();
   }

   public static Color getRandomColor() {
      return getRandomColor(1000, 0.6F);
   }

   public static int applyTexture(int var0, File var1, boolean var2, boolean var3) throws IOException {
      applyTexture(var0, ImageIO.read(var1), var2, var3);
      return var0;
   }

   public static void drawGradientBorderedRectReliant(float var0, float var1, float var2, float var3, float var4, int var5, int var6, int var7) {
      enableGL2D();
      drawGradientRect(var0, var1, var2, var3, var7, var6);
      glColor((long)var5);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(var4);
      GL11.glBegin(3);
      GL11.glVertex2f(var0, var1);
      GL11.glVertex2f(var0, var3);
      GL11.glVertex2f(var2, var3);
      GL11.glVertex2f(var2, var1);
      GL11.glVertex2f(var0, var1);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      disableGL2D();
   }

   public static Color getRandomColor(int var0, float var1) {
      float var2 = (float)Math.random();
      float var3 = ((float)Math.random() + 1000.0F) / (float)var0 + 1000.0F;
      return Color.getHSBColor(var2, var3, var1);
   }

   public static void disableGL3D() {
      GL11.glEnable(3553);
      GL11.glDisable(2848);
   }

   public static void updateScaledResolution() {
      scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
   }

   public static void renderTexture(float var0, float var1, float var2, float var3) {
      boolean var4 = GL11.glGetBoolean(3553);
      boolean var5 = GL11.glGetBoolean(3042);
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glEnable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      var0 *= 2.0F;
      var1 *= 2.0F;
      var2 *= 2.0F;
      var3 *= 2.0F;
      GL11.glBegin(4);
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2f(var0 + var2, var1);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2f(var0, var1);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2f(var0, var1 + var3);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2f(var0, var1 + var3);
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex2f(var0 + var2, var1 + var3);
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2f(var0 + var2, var1);
      GL11.glEnd();
      if (!var4) {
         GL11.glDisable(3553);
      }

      if (!var5) {
         GL11.glDisable(3042);
      }

      GL11.glPopMatrix();
   }

   public static int genTexture() {
      return GL11.glGenTextures();
   }

   public static void drawStrip(int var0, int var1, float var2, double var3, float var5, float var6, int var7) {
      float var8 = (float)(var7 >> 24 & 255) / 255.0F;
      float var9 = (float)(var7 >> 16 & 255) / 255.0F;
      float var10 = (float)(var7 >> 8 & 255) / 255.0F;
      float var11 = (float)(var7 & 255) / 255.0F;
      GL11.glPushMatrix();
      GL11.glTranslated((double)var0, (double)var1, 0.0D);
      GL11.glColor4f(var9, var10, var11, var8);
      GL11.glLineWidth(var2);
      int var12;
      float var13;
      float var14;
      float var15;
      if (var3 > 0.0D) {
         GL11.glBegin(3);

         for(var12 = 0; (double)var12 < var3; ++var12) {
            var13 = (float)((double)var12 * (var3 * 3.141592653589793D / (double)var5));
            var14 = (float)(Math.cos((double)var13) * (double)var6);
            var15 = (float)(Math.sin((double)var13) * (double)var6);
            GL11.glVertex2f(var14, var15);
         }

         GL11.glEnd();
      }

      if (var3 < 0.0D) {
         GL11.glBegin(3);

         for(var12 = 0; (double)var12 > var3; --var12) {
            var13 = (float)((double)var12 * (var3 * 3.141592653589793D / (double)var5));
            var14 = (float)(Math.cos((double)var13) * (double)(-var6));
            var15 = (float)(Math.sin((double)var13) * (double)(-var6));
            GL11.glVertex2f(var14, var15);
         }

         GL11.glEnd();
      }

      disableGL2D();
      GL11.glDisable(3479);
      GL11.glPopMatrix();
   }

   public static void drawBorderedRect(float var0, float var1, float var2, float var3, int var4, int var5) {
      enableGL2D();
      var0 *= 2.0F;
      var2 *= 2.0F;
      var1 *= 2.0F;
      var3 *= 2.0F;
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      drawRect(var0 + 1.0F, var1 + 1.0F, var2 - 1.0F, var3 - 1.0F, (long)var4);
      drawVerticalLine(var0, var1, var3, var5);
      drawVerticalLine(var2, var1, var3, var5);
      drawHorizontalLine(var0 + 1.0F, var1, var2, var5);
      drawHorizontalLine(var0, var3, var2 + 1.0F, var5);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
   }

   public static void drawBox(Box var0) {
      if (var0 != null) {
         GL11.glBegin(7);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glEnd();
      }
   }

   public static void disableGL2D() {
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawBorderedRect(float var0, float var1, float var2, float var3, float var4, int var5, int var6) {
      enableGL2D();
      glColor((long)var5);
      drawRect(var0 + var4, var1 + var4, var2 - var4, var3 - var4);
      glColor((long)var6);
      drawVerticalLine(var0, var1, var3, var6);
      drawVerticalLine(var2, var1, var3 + 1.0F, var6);
      drawHorizontalLine(var0, var1, var2, var6);
      drawHorizontalLine(var0, var3, var2, var6);
      disableGL2D();
   }

   public static void glColor(float var0, int var1, int var2, int var3) {
      float var4 = 0.003921569F * (float)var1;
      float var5 = 0.003921569F * (float)var2;
      float var6 = 0.003921569F * (float)var3;
      GL11.glColor4f(var4, var5, var6, var0);
   }

   public static void drawGradientRect(double var0, double var2, double var4, double var6, int var8, int var9) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushMatrix();
      GL11.glBegin(7);
      glColor((long)var8);
      GL11.glVertex2d(var4, var2);
      GL11.glVertex2d(var0, var2);
      glColor((long)var9);
      GL11.glVertex2d(var0, var6);
      GL11.glVertex2d(var4, var6);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
   }

   public static void renderTexture(int var0, float var1, float var2, float var3, float var4) {
      GL11.glBindTexture(3553, var0);
      renderTexture(var1, var2, var3, var4);
   }

   public static void drawHLine(float var0, float var1, float var2, int var3) {
      if (var1 < var0) {
         float var4 = var0;
         var0 = var1;
         var1 = var4;
      }

      drawRect(var0, var2, var1 + 1.0F, var2 + 1.0F, (long)var3);
   }

   public static void drawLine(float var0, float var1, float var2, float var3, float var4) {
      GL11.glDisable(3553);
      GL11.glLineWidth(var4);
      GL11.glBegin(1);
      GL11.glVertex2f(var0, var1);
      GL11.glVertex2f(var2, var3);
      GL11.glEnd();
      GL11.glEnable(3553);
   }

   public static void enableGL2D() {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
   }

   public static void drawRect(float var0, float var1, float var2, float var3, long var4) {
      enableGL2D();
      glColor(var4);
      drawRect(var0, var1, var2, var3);
      disableGL2D();
   }

   public static Vec3d to2D(double var0, double var2, double var4) {
      FloatBuffer var6 = BufferUtils.createFloatBuffer(3);
      IntBuffer var7 = BufferUtils.createIntBuffer(16);
      FloatBuffer var8 = BufferUtils.createFloatBuffer(16);
      FloatBuffer var9 = BufferUtils.createFloatBuffer(16);
      GL11.glGetFloat(2982, var8);
      GL11.glGetFloat(2983, var9);
      GL11.glGetInteger(2978, var7);
      boolean var10 = GLU.gluProject((float)var0, (float)var2, (float)var4, var8, var9, var7, var6);
      return var10 ? new Vec3d((double)var6.get(0), (double)((float)Display.getHeight() - var6.get(1)), (double)var6.get(2)) : null;
   }

   public static void drawRoundedRect(float var0, float var1, float var2, float var3, int var4, int var5) {
      enableGL2D();
      var0 *= 2.0F;
      var1 *= 2.0F;
      var2 *= 2.0F;
      var3 *= 2.0F;
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      drawVLine(var0, var1 + 1.0F, var3 - 2.0F, var4);
      drawVLine(var2 - 1.0F, var1 + 1.0F, var3 - 2.0F, var4);
      drawHLine(var0 + 2.0F, var2 - 3.0F, var1, var4);
      drawHLine(var0 + 2.0F, var2 - 3.0F, var3 - 1.0F, var4);
      drawHLine(var0 + 1.0F, var0 + 1.0F, var1 + 1.0F, var4);
      drawHLine(var2 - 2.0F, var2 - 2.0F, var1 + 1.0F, var4);
      drawHLine(var2 - 2.0F, var2 - 2.0F, var3 - 2.0F, var4);
      drawHLine(var0 + 1.0F, var0 + 1.0F, var3 - 2.0F, var4);
      drawRect(var0 + 1.0F, var1 + 1.0F, var2 - 1.0F, var3 - 1.0F, (long)var5);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
   }

   public static void drawRect(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      enableGL2D();
      GL11.glColor4f(var4, var5, var6, var7);
      drawRect(var0, var1, var2, var3);
      disableGL2D();
   }

   public static void glColor(Color var0) {
      GL11.glColor4f((float)var0.getRed() / 255.0F, (float)var0.getGreen() / 255.0F, (float)var0.getBlue() / 255.0F, (float)var0.getAlpha() / 255.0F);
   }

   public static void drawBorderedRectReliant(float var0, float var1, float var2, float var3, float var4, long var5, long var7) {
      enableGL2D();
      drawRect(var0, var1, var2, var3, var5);
      glColor(var7);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(var4);
      GL11.glBegin(3);
      GL11.glVertex2f(var0, var1);
      GL11.glVertex2f(var0, var3);
      GL11.glVertex2f(var2, var3);
      GL11.glVertex2f(var2, var1);
      GL11.glVertex2f(var0, var1);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      disableGL2D();
   }

   public static double interpolate(double var0, double var2) {
      return var2 + (var0 - var2) * (double)ReflectionFields.getTimer().field_194147_b;
   }

   public static void drawBorderedRect(Rectangle var0, float var1, int var2, int var3) {
      float var4 = (float)var0.x;
      float var5 = (float)var0.y;
      float var6 = (float)(var0.x + var0.width);
      float var7 = (float)(var0.y + var0.height);
      enableGL2D();
      glColor((long)var2);
      drawRect(var4 + var1, var5 + var1, var6 - var1, var7 - var1);
      glColor((long)var3);
      drawRect(var4 + 1.0F, var5, var6 - 1.0F, var5 + var1);
      drawRect(var4, var5, var4 + var1, var7);
      drawRect(var6 - var1, var5, var6, var7);
      drawRect(var4 + 1.0F, var7 - var1, var6 - 1.0F, var7);
      disableGL2D();
   }

   public static ScaledResolution getScaledResolution() {
      return scaledResolution;
   }
}
