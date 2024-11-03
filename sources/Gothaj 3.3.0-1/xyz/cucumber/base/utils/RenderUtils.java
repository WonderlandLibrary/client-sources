package xyz.cucumber.base.utils;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.RoundedUtils;
import xyz.cucumber.base.utils.render.shaders.Shaders;

public class RenderUtils {
   private static final Frustum frustrum = new Frustum();

   public static void start2D() {
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      GlStateManager.disableTexture2D();
      GlStateManager.disableCull();
      GlStateManager.disableAlpha();
      GlStateManager.disableDepth();
   }

   public static void drawCustomRect(double x, double y, double x2, double y2, int color, float rounding, boolean rounded) {
      if (rounded) {
         drawRoundedRect(x, y, x2, y2, color, rounding);
      } else {
         drawRect(x, y, x2, y2, color);
      }
   }

   public static void drawGradientRectSideways(double x, double y, double x1, double y1, int color, int color2) {
      Gui.drawGradientRectSideways(x, y, x1, y1, color, color2);
   }

   public static boolean isInViewFrustrum(Entity entity) {
      return isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
   }

   private static boolean isInViewFrustrum(AxisAlignedBB bb) {
      Entity current = Minecraft.getMinecraft().getRenderViewEntity();
      frustrum.setPosition(current.posX, current.posY, current.posZ);
      return frustrum.isBoundingBoxInFrustum(bb);
   }

   public static void start3D() {
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      GlStateManager.disableCull();
   }

   public static void cross(double x, double y, double size, double linewidth, int color) {
      GL11.glPushMatrix();
      start2D();
      GL11.glLineWidth((float)linewidth);
      color(color);
      GL11.glBegin(2);
      GL11.glVertex2d(x - size, y - size);
      GL11.glVertex2d(x + size, y + size);
      GL11.glEnd();
      GL11.glBegin(2);
      GL11.glVertex2d(x + size, y - size);
      GL11.glVertex2d(x - size, y + size);
      GL11.glEnd();
      stop2D();
      GL11.glPopMatrix();
   }

   public static void renderHitbox(AxisAlignedBB bb, int type) {
      GL11.glBegin(type);
      GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
      GL11.glEnd();
      GL11.glBegin(type);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
      GL11.glEnd();
      GL11.glBegin(type);
      GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
      GL11.glEnd();
      GL11.glBegin(type);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
      GL11.glEnd();
      GL11.glBegin(type);
      GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
      GL11.glEnd();
      GL11.glBegin(type);
      GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
      GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
      GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
      GL11.glEnd();
   }

   public static void stop3D() {
      GlStateManager.enableCull();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
   }

   public static void stop2D() {
      GlStateManager.enableDepth();
      GlStateManager.enableAlpha();
      GlStateManager.enableCull();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.resetColor();
   }

   public static void enableScisor() {
      GL11.glEnable(3089);
   }

   public static void disableScisor() {
      GL11.glDisable(3089);
   }

   public static void scissor(ScaledResolution scaledResolution, double x, double y, double width, double height) {
      int scaleFactor = scaledResolution.getScaleFactor();
      GL11.glScissor(
         (int)Math.round(x * (double)scaleFactor),
         (int)Math.round(((double)scaledResolution.getScaledHeight() - (y + height)) * (double)scaleFactor),
         (int)Math.round(width * (double)scaleFactor),
         (int)Math.round(height * (double)scaleFactor)
      );
   }

   public static void drawRect(double x, double y, double x1, double y1, int color) {
      if (x1 < x) {
         double temp = x;
         x = x1;
         x1 = temp;
      }

      if (y1 < y) {
         double temp = y;
         y = y1;
         y1 = temp;
      }

      GL11.glPushMatrix();
      start2D();
      color(color);
      GL11.glBegin(7);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x1, y);
      GL11.glVertex2d(x1, y1);
      GL11.glVertex2d(x, y1);
      GL11.glEnd();
      stop2D();
      GL11.glPopMatrix();
   }

   public static void drawColorPicker(double x, double y, double width, double height, float hue) {
      GL11.glPushMatrix();
      GL11.glPushAttrib(1048575);
      Shaders.colorPicker.startProgram();
      GL20.glUniform2f(Shaders.colorPicker.getUniformLoc("u_resolution"), (float)width, (float)height);
      GL20.glUniform1f(Shaders.colorPicker.getUniformLoc("hue"), hue);
      Shaders.colorPicker.renderShader(x, y, width, height);
      Shaders.colorPicker.stopProgram();
      GL11.glPopAttrib();
      GL11.glPopMatrix();
   }

   public static void drawColorSlider(double x, double y, double width, double height) {
      GL11.glPushMatrix();
      GL11.glPushAttrib(1048575);
      Shaders.colorSlider.startProgram();
      GL20.glUniform2f(Shaders.colorSlider.getUniformLoc("u_resolution"), (float)width, (float)height);
      Shaders.colorSlider.renderShader(x, y, width, height);
      Shaders.colorSlider.stopProgram();
      GL11.glPopAttrib();
      GL11.glPopMatrix();
   }

   public static void drawCircle(double x, double y, double radius, int color, double step) {
      GL11.glPushMatrix();
      start2D();
      color(color);
      GL11.glPointSize((float)radius * 4.0F);
      GL11.glEnable(2832);
      GL11.glBegin(0);
      GL11.glVertex2d(x, y);
      GL11.glEnd();
      stop2D();
      GL11.glPopMatrix();
   }

   public static void drawArrowForClickGui(double x, double y, double size, int color, double angle) {
      GL11.glPushMatrix();
      start2D();
      GL11.glTranslated(x, y, 0.0);
      GL11.glRotated(angle, 0.0, 0.0, 1.0);
      GL11.glTranslated(-x, -y, 0.0);
      color(color);
      GL11.glBegin(9);
      GL11.glVertex2d(x - size / 2.0, y + size / 2.0);
      GL11.glVertex2d(x, y - size / 2.0);
      GL11.glVertex2d(x + size / 2.0, y + size / 2.0);
      GL11.glEnd();
      GL11.glRotated(0.0, 0.0, 0.0, 0.0);
      stop2D();
      GL11.glPopMatrix();
   }

   public static void drawOutlinedRect(double x, double y, double x1, double y1, int color, float size) {
      if (x1 < x) {
         double temp = x;
         x = x1;
         x1 = temp;
      }

      if (y1 < y) {
         double temp = y;
         y = y1;
         y1 = temp;
      }

      GL11.glPushMatrix();
      start2D();
      color(color);
      GL11.glLineWidth(size);
      GL11.glBegin(2);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x1, y);
      GL11.glVertex2d(x1, y1);
      GL11.glVertex2d(x, y1);
      GL11.glEnd();
      stop2D();
      GL11.glPopMatrix();
   }

   public static void drawPoint(double x, double y, int color, float size) {
      GL11.glPushMatrix();
      start2D();
      GL11.glPointSize(size);
      GL11.glBegin(0);
      color(color);
      GL11.glVertex2d(x, y);
      GL11.glEnd();
      stop2D();
      GL11.glPopMatrix();
   }

   public static void drawOtherBackground(double x, double y, double width, double height, float time) {
      GL11.glPushMatrix();
      GL11.glPushAttrib(1048575);
      Shaders.testShader.startProgram();
      GL20.glUniform1f(Shaders.testShader.getUniformLoc("iTime"), time);
      Shaders.testShader.renderShader(x, y, width, height);
      Shaders.testShader.stopProgram();
      GL11.glPopAttrib();
      GL11.glPopMatrix();
   }

   public static void drawBlurTest(double x, double y, double width, double height, float radius) {
      try {
         GL11.glPushMatrix();
         GL11.glPushAttrib(1048575);
         Shaders.testShader.startProgram();
         GL20.glUniform2f(Shaders.testShader.getUniformLoc("screenSize"), (float)width, (float)height);
         GL20.glUniform1f(Shaders.testShader.getUniformLoc("radius"), radius);
         GL20.glUniform1f(Shaders.testShader.getUniformLoc("texture0"), 0.0F);
         Shaders.testShader.renderShader(x, y, width, height);
         Shaders.testShader.stopProgram();
         GL11.glPopAttrib();
         GL11.glPopMatrix();
      } catch (Exception var10) {
      }
   }

   public static void drawImageWithAlpha(double x, double y, double width, double height, int color, ResourceLocation location, int alpha) {
      GL11.glPushMatrix();
      GL11.glPushAttrib(1048575);
      Shaders.alphaImage.startProgram();
      GL20.glUniform1f(Shaders.alphaImage.getUniformLoc("texture"), 0.0F);
      GL20.glUniform1f(Shaders.alphaImage.getUniformLoc("alpha"), (float)alpha / 255.0F);
      drawImage(x, y, width, height, location, color);
      Shaders.alphaImage.stopProgram();
      GL11.glPopAttrib();
      GL11.glPopMatrix();
   }

   public static void drawRoundedRect(double x, double y, double x1, double y1, int color, float rounding) {
      if (x1 < x) {
         double temp = x;
         x = x1;
         x1 = temp;
      }

      if (y1 < y) {
         double temp = y;
         y = y1;
         y1 = temp;
      }

      float rectWidth = (float)(x1 - x);
      float rectHeight = (float)(y1 - y);
      RoundedUtils.drawRoundedRect(x, y, (double)rectWidth, (double)rectHeight, color, rounding);
   }

   public static void drawLine(double x, double y, double x1, double y1, int color, float size) {
      GL11.glPushMatrix();
      start2D();
      GL11.glLineWidth(size);
      color(color);
      GL11.glBegin(2);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x1, y1);
      GL11.glEnd();
      stop2D();
      GL11.glPopMatrix();
   }

   public static void color(int color) {
      float f3 = (float)(color >> 24 & 0xFF) / 255.0F;
      float f = (float)(color >> 16 & 0xFF) / 255.0F;
      float f1 = (float)(color >> 8 & 0xFF) / 255.0F;
      float f2 = (float)(color & 0xFF) / 255.0F;
      GlStateManager.color(f, f1, f2, f3);
   }

   public static void color(int color, float a) {
      float f3 = (float)(color >> 24 & 0xFF) / 255.0F;
      float f = (float)(color >> 16 & 0xFF) / 255.0F;
      float f1 = (float)(color >> 8 & 0xFF) / 255.0F;
      float f2 = (float)(color & 0xFF) / 255.0F;
      GlStateManager.color(f, f1, f2, a);
   }

   public static void drawImage(double x, double y, double width, double height, ResourceLocation image, int color) {
      GL11.glDisable(2929);
      GlStateManager.enableBlend();
      GL11.glDepthMask(false);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      color(color);
      Minecraft.getMinecraft().getTextureManager().bindTexture(image);
      Gui.drawModalRectWithCustomSizedTexture((float)x, (float)y, 0.0F, 0.0F, width, height, width, height);
      GlStateManager.resetColor();
      GL11.glDepthMask(true);
      GlStateManager.disableBlend();
      GL11.glEnable(2929);
   }

   private static void drawCirclePart(double x, double y, double radius, double from, double to, int color) {
      GL11.glPushMatrix();
      start2D();
      color(color);
      GL11.glBegin(6);
      double step = 2.0;
      GL11.glVertex2d(x, y);

      for (double i = from; i <= to; i += step) {
         GL11.glVertex2d(x + Math.sin(i * Math.PI / 180.0) * radius, y - Math.cos(i * Math.PI / 180.0) * radius);
      }

      GL11.glEnd();
      stop2D();
      GL11.glPopMatrix();
   }

   public static void drawRoundedRectWithCorners(
      double x, double y, double x1, double y1, int color, double radius, boolean leftTop, boolean rightTop, boolean leftBot, boolean rightBot
   ) {
      GL11.glPushMatrix();
      drawRect(x + radius, y, x1 - radius, y1, color);
      drawRect(x, y + (leftTop ? radius : 0.0), x + radius, y1 - (leftBot ? radius : 0.0), color);
      drawRect(x1 - radius, y + (rightTop ? radius : 0.0), x1, y1 - (rightBot ? radius : 0.0), color);
      if (leftTop) {
         drawCirclePart(x + radius, y + radius, radius, 270.0, 360.0, color);
      }

      if (rightTop) {
         drawCirclePart(x1 - radius, y + radius, radius, 0.0, 90.0, color);
      }

      if (leftBot) {
         drawCirclePart(x + radius, y1 - radius, radius, 180.0, 270.0, color);
      }

      if (rightBot) {
         drawCirclePart(x1 - radius, y1 - radius, radius, 90.0, 180.0, color);
      }

      GL11.glPopMatrix();
   }

   public static void drawRoundedRect(double x, double y, double x1, double y1, int color, double radius) {
      if (x1 < x) {
         double temp = x;
         x = x1;
         x1 = temp;
      }

      if (y1 < y) {
         double temp = y;
         y = y1;
         y1 = temp;
      }

      radius *= 2.0;
      start2D();
      GL11.glShadeModel(7425);
      GL11.glBegin(6);
      double xs = x + radius;
      double ys = y + radius;
      color(color);

      for (double i = 270.0; i < 360.0; i++) {
         GL11.glVertex2d(xs + Math.sin(i * Math.PI / 180.0) * radius, ys - Math.cos(i * Math.PI / 180.0) * radius);
      }

      xs = x1 - radius;
      ys = y + radius;

      for (double i = 0.0; i < 90.0; i++) {
         GL11.glVertex2d(xs + Math.sin(i * Math.PI / 180.0) * radius, ys - Math.cos(i * Math.PI / 180.0) * radius);
      }

      xs = x1 - radius;
      ys = y1 - radius;

      for (double i = 90.0; i < 180.0; i++) {
         GL11.glVertex2d(xs + Math.sin(i * Math.PI / 180.0) * radius, ys - Math.cos(i * Math.PI / 180.0) * radius);
      }

      xs = x + radius;
      ys = y1 - radius;

      for (double i = 180.0; i < 270.0; i++) {
         GL11.glVertex2d(xs + Math.sin(i * Math.PI / 180.0) * radius, ys - Math.cos(i * Math.PI / 180.0) * radius);
      }

      GL11.glEnd();
      GL11.glShadeModel(7424);
      stop2D();
   }

   public static void drawOutlinedRoundedRect(double x, double y, double x1, double y1, int color, double radius, double linew) {
      if (x1 < x) {
         double temp = x;
         x = x1;
         x1 = temp;
      }

      if (y1 < y) {
         double temp = y;
         y = y1;
         y1 = temp;
      }

      float f3 = (float)(color >> 24 & 0xFF) / 255.0F;
      float f = (float)(color >> 16 & 0xFF) / 255.0F;
      float f1 = (float)(color >> 8 & 0xFF) / 255.0F;
      float f2 = (float)(color & 0xFF) / 255.0F;
      RoundedUtils.drawRoundOutline((float)x, (float)y, (float)(x1 - x), (float)(y1 - y), (float)radius, (float)linew / 3.0F, new Color(f, f1, f2, f3));
      GlStateManager.resetColor();
   }

   public static void drawMixedRoundedRect(double x, double y, double x1, double y1, int color1, int color2, double radius) {
      if (x1 < x) {
         double temp = x;
         x = x1;
         x1 = temp;
      }

      if (y1 < y) {
         double temp = y;
         y = y1;
         y1 = temp;
      }

      float alpha = (float)(color1 >> 24 & 0xFF) / 255.0F;
      start2D();
      GL11.glShadeModel(7425);
      GL11.glBegin(6);
      double xs = x + radius;
      double ys = y + radius;
      color(
         ColorUtils.getAlphaColor(
            ColorUtils.mix(color1, color2, Math.sin(Math.toRadians((double)(System.nanoTime() / 1000000L / 7L + 0L))) + 1.0, 2.0), (int)(alpha * 100.0F)
         )
      );

      for (double i = 270.0; i < 360.0; i += 2.0) {
         GL11.glVertex2d(xs + Math.sin(i * Math.PI / 180.0) * radius, ys - Math.cos(i * Math.PI / 180.0) * radius);
      }

      color(
         ColorUtils.getAlphaColor(
            ColorUtils.mix(color1, color2, Math.sin(Math.toRadians((double)(System.nanoTime() / 1000000L / 7L + 90L))) + 1.0, 2.0), (int)(alpha * 100.0F)
         )
      );
      xs = x1 - radius;
      ys = y + radius;

      for (double i = 0.0; i < 90.0; i += 2.0) {
         GL11.glVertex2d(xs + Math.sin(i * Math.PI / 180.0) * radius, ys - Math.cos(i * Math.PI / 180.0) * radius);
      }

      xs = x1 - radius;
      ys = y1 - radius;
      color(
         ColorUtils.getAlphaColor(
            ColorUtils.mix(color1, color2, Math.sin(Math.toRadians((double)(System.nanoTime() / 1000000L / 7L + 180L))) + 1.0, 2.0), (int)(alpha * 100.0F)
         )
      );

      for (double i = 90.0; i < 180.0; i += 2.0) {
         GL11.glVertex2d(xs + Math.sin(i * Math.PI / 180.0) * radius, ys - Math.cos(i * Math.PI / 180.0) * radius);
      }

      color(
         ColorUtils.getAlphaColor(
            ColorUtils.mix(color1, color2, Math.sin(Math.toRadians((double)(System.nanoTime() / 1000000L / 7L + 260L))) + 1.0, 2.0), (int)(alpha * 100.0F)
         )
      );
      xs = x + radius;
      ys = y1 - radius;

      for (double i = 180.0; i < 270.0; i += 2.0) {
         GL11.glVertex2d(xs + Math.sin(i * Math.PI / 180.0) * radius, ys - Math.cos(i * Math.PI / 180.0) * radius);
      }

      GL11.glEnd();
      GL11.glShadeModel(7424);
      stop2D();
   }

   public static void drawMixedRect(double x, double y, double x1, double y1, int color1, int color2) {
      if (x1 < x) {
         double temp = x;
         x = x1;
         x1 = temp;
      }

      if (y1 < y) {
         double temp = y;
         y = y1;
         y1 = temp;
      }

      float alpha = (float)(color1 >> 24 & 0xFF);
      start2D();
      GL11.glShadeModel(7425);
      GL11.glBegin(7);
      color(
         ColorUtils.getAlphaColor(ColorUtils.mix(color1, color2, Math.sin(Math.toRadians((double)(System.nanoTime() / 1000000L / 10L + 0L))) + 1.0, 2.0), 100)
      );
      GL11.glVertex2d(x, y);
      color(
         ColorUtils.getAlphaColor(ColorUtils.mix(color1, color2, Math.sin(Math.toRadians((double)(System.nanoTime() / 1000000L / 10L + 90L))) + 1.0, 2.0), 100)
      );
      GL11.glVertex2d(x1, y);
      color(
         ColorUtils.getAlphaColor(ColorUtils.mix(color1, color2, Math.sin(Math.toRadians((double)(System.nanoTime() / 1000000L / 10L + 180L))) + 1.0, 2.0), 100)
      );
      GL11.glVertex2d(x1, y1);
      color(
         ColorUtils.getAlphaColor(ColorUtils.mix(color1, color2, Math.sin(Math.toRadians((double)(System.nanoTime() / 1000000L / 10L + 260L))) + 1.0, 2.0), 100)
      );
      GL11.glVertex2d(x, y1);
      GL11.glEnd();
      stop2D();
   }
}
