package net.minecraft.util;

import java.io.File;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.lF;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class ScreenShotHelper {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
   private static IntBuffer pixelBuffer;
   private static int[] pixelValues;

   public static void saveScreenshot(File var0, int var1, int var2, Framebuffer var3) {
      saveScreenshot(var0, (String)null, var1, var2, var3);
   }

   public static void saveScreenshot(File var0, String var1, int var2, int var3, Framebuffer var4) {
      try {
         File var5 = new File(var0, "screenshots");
         var5.mkdir();
         if(OpenGlHelper.isFramebufferEnabled()) {
            var2 = var4.framebufferTextureWidth;
            var3 = var4.framebufferTextureHeight;
         }

         int var6 = var2 * var3;
         if(pixelBuffer == null || pixelBuffer.capacity() < var6) {
            pixelBuffer = BufferUtils.createIntBuffer(var6);
            pixelValues = new int[var6];
         }

         GL11.glPixelStorei(3333, 1);
         GL11.glPixelStorei(3317, 1);
         pixelBuffer.clear();
         if(OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture(var4.framebufferTexture);
            GL11.glGetTexImage(3553, 0, '胡', '荧', pixelBuffer);
         } else {
            GL11.glReadPixels(0, 0, var4.framebufferWidth, var4.framebufferHeight, '胡', '荧', pixelBuffer);
         }

         (new lF("ss", var4, var1, var5)).start();
      } catch (Exception var7) {
         LOGGER.warn("Couldn\'t save screenshot", var7);
      }

   }

   private static File getTimestampedPNGFileForDirectory(File var0) {
      String var1 = dateFormat.format(new Date());
      int var2 = 1;

      while(true) {
         File var3 = new File(var0, var1 + (var2 == 1?"":"_" + var2) + ".png");
         if(!var3.exists()) {
            return var3;
         }

         ++var2;
      }
   }

   static int[] access$000() {
      return pixelValues;
   }

   static IntBuffer access$100() {
      return pixelBuffer;
   }

   static File access$200(File var0) {
      return getTimestampedPNGFileForDirectory(var0);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
