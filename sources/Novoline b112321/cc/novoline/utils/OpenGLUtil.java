package cc.novoline.utils;

import cc.novoline.Novoline;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.utils.Timer;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import org.lwjgl.opengl.GL11;

public final class OpenGLUtil {
   private static final Minecraft mc = Minecraft.getInstance();
   private static ShaderGroup blurShader;
   private static Framebuffer blurShaderFramebuffer;
   private static final Object MUTEX = new Object();
   private static int lastDisplayWidth;
   private static int lastDisplayHeight;

   public static void blurScissor(int var0, int var1, int var2, int var3, boolean var4) {
      String[] var5 = Timer.e();
      if(mc.gameSettings.guiScale == 2) {
         GL11.glEnable(3089);
         a(var0, var1, var2, var3);
         GL11.glPushMatrix();
         blurShaderFramebuffer.framebufferRenderExt(Minecraft.getInstance().displayWidth, Minecraft.getInstance().displayHeight, true);
         GL11.glPopMatrix();
         if(var4) {
            Minecraft.getInstance().entityRenderer.setupOverlayRendering();
         }

         GlStateManager.enableDepth();
         GL11.glDisable(3089);
      }
   }

   public static void blurFully(boolean var0) {
      String[] var1 = Timer.e();
      if(mc.gameSettings.guiScale == 2) {
         mc.getFramebuffer().unbindFramebuffer();
         blurShaderFramebuffer.bindFramebuffer(true);
         mc.getFramebuffer().framebufferRenderExt(mc.displayWidth, mc.displayHeight, true);
         if(OpenGlHelper.shadersSupported && blurShader != null) {
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
            GlStateManager.popMatrix();
         }

         blurShaderFramebuffer.unbindFramebuffer();
         mc.getFramebuffer().bindFramebuffer(true);
         if(var0) {
            mc.entityRenderer.setupOverlayRendering();
         }

      }
   }

   public static void bindShader() {
      // $FF: Couldn't be decompiled
   }

   public static void a(int var0, int var1, int var2, int var3) {
      Timer.e();
      var2 = var2 - var0;
      var3 = var3 - var1;
      Minecraft var5 = Minecraft.getInstance();
      ScaledResolution var6 = new ScaledResolution(var5);
      float var7 = 1.0F;
      switch(mc.gameSettings.guiScale) {
      case 0:
         var7 = (float)(0.5D * ((ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class)).s());
      case 1:
         var7 = (float)(2.0D * ((ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class)).s());
      case 3:
         var7 = (float)(0.6666666865348816D * ((ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class)).s());
      case 2:
      default:
         var7 = (float)((ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class)).s();
         GL11.glScissor((int)((float)(var0 * var6.getScaleFactor()) * var7), (int)((float)var5.displayHeight - (float)(var1 * var6.getScaleFactor()) * var7 - (float)(var3 * var6.getScaleFactor()) * var7), (int)((float)(var2 * var6.getScaleFactor()) * var7), (int)((float)(var3 * var6.getScaleFactor()) * var7));
      }
   }

   public static int interpolateColor(Color var0, Color var1, float var2) {
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

   private OpenGLUtil() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
