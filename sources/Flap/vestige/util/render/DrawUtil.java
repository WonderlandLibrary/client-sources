package vestige.util.render;

import java.awt.Color;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import vestige.module.impl.visual.ClientTheme;
import vestige.util.IMinecraft;

public class DrawUtil implements IMinecraft {
   private static ClientTheme theme;

   public static void drawGradientVerticalRect(double left, double top, double right, double bottom, int startColor, int endColor) {
      float f = (float)(startColor >> 24 & 255) / 255.0F;
      float f1 = (float)(startColor >> 16 & 255) / 255.0F;
      float f2 = (float)(startColor >> 8 & 255) / 255.0F;
      float f3 = (float)(startColor & 255) / 255.0F;
      float f4 = (float)(endColor >> 24 & 255) / 255.0F;
      float f5 = (float)(endColor >> 16 & 255) / 255.0F;
      float f6 = (float)(endColor >> 8 & 255) / 255.0F;
      float f7 = (float)(endColor & 255) / 255.0F;
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      worldrenderer.func_181662_b(right, top, 0).func_181666_a(f1, f2, f3, f).func_181675_d();
      worldrenderer.func_181662_b(left, top, 0).func_181666_a(f1, f2, f3, f).func_181675_d();
      worldrenderer.func_181662_b(left, bottom, 0).func_181666_a(f5, f6, f7, f4).func_181675_d();
      worldrenderer.func_181662_b(right, bottom, 0).func_181666_a(f5, f6, f7, f4).func_181675_d();
      tessellator.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
   }

   public static void drawGradientSideRect(double left, double top, double right, double bottom, int startColor, int endColor) {
      float f = (float)(startColor >> 24 & 255) / 255.0F;
      float f1 = (float)(startColor >> 16 & 255) / 255.0F;
      float f2 = (float)(startColor >> 8 & 255) / 255.0F;
      float f3 = (float)(startColor & 255) / 255.0F;

      float f4 = (float)(endColor >> 24 & 255) / 255.0F;
      float f5 = (float)(endColor >> 16 & 255) / 255.0F;
      float f6 = (float)(endColor >> 8 & 255) / 255.0F;
      float f7 = (float)(endColor & 255) / 255.0F;

      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      worldrenderer.func_181662_b(right, top, 0).func_181666_a(f5, f6, f7, f4).func_181675_d();
      worldrenderer.func_181662_b(left, top, 0).func_181666_a(f1, f2, f3, f).func_181675_d();
      worldrenderer.func_181662_b(left, bottom, 0).func_181666_a(f1, f2, f3, f).func_181675_d();
      worldrenderer.func_181662_b(right, bottom, 0).func_181666_a(f5, f6, f7, f4).func_181675_d();
      tessellator.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
   }





   public static void drawHead(ResourceLocation skin, int x, int y, int width, int height) {
      try {
         mc.getTextureManager().bindTexture(skin);
         mc.getTextureManager().bindTexture(skin);
         Gui.drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public static void drawHeadRounded(AbstractClientPlayer player, ResourceLocation skin, int x, int y, int width, int height) {
      try {
         mc.getTextureManager().bindTexture(skin);
         GL11.glEnable(2960);
         GL11.glClear(1024);
         GL11.glStencilFunc(519, 1, 255);
         GL11.glStencilOp(7680, 7680, 7681);
         GL11.glColorMask(false, false, false, false);
         GL11.glDepthMask(false);
         RenderUtils2.drawRoundOutline((double)x, (double)y, (double)width, (double)height, 6.0D, 0.0D, Color.BLACK, Color.BLACK);
         GL11.glColorMask(true, true, true, true);
         GL11.glDepthMask(true);
         GL11.glStencilFunc(514, 1, 255);
         GL11.glStencilOp(7680, 7680, 7680);
         double offset = -((double)player.hurtTime * 15.5D);
         Color dynamicColor = new Color(255, (int)(255.0D + offset), (int)(255.0D + offset));
         GlStateManager.color((float)dynamicColor.getRed() / 255.0F, (float)dynamicColor.getGreen() / 255.0F, (float)dynamicColor.getBlue() / 255.0F, (float)dynamicColor.getAlpha() / 255.0F);
         mc.getTextureManager().bindTexture(skin);
         Gui.drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
         GL11.glDisable(2960);
      } catch (Exception var9) {
         var9.printStackTrace();
      }

   }

   public static void drawRoundHead(ResourceLocation skin, float x, float y, float width, float height, float radius) {
      GL11.glPushAttrib(1048575);
      GL11.glPushMatrix();
      GL11.glScaled(0.5D, 0.5D, 0.5D);
      x *= 2.0F;
      y *= 2.0F;
      width *= 2.0F;
      height *= 2.0F;
      radius *= 2.0F;
      GL11.glEnable(2960);
      GL11.glClear(1024);
      GL11.glStencilFunc(519, 1, 255);
      GL11.glStencilOp(7680, 7680, 7681);
      GL11.glStencilMask(255);
      GL11.glColorMask(false, false, false, false);
      GL11.glDepthMask(false);
      GL11.glBegin(9);

      for(int i = 0; i <= 360; ++i) {
         double angle = Math.toRadians((double)i);
         double xOffset = Math.sin(angle) * (double)radius;
         double yOffset = Math.cos(angle) * (double)radius;
         if (i <= 90) {
            GL11.glVertex2d((double)(x + radius) + xOffset, (double)(y + radius) - yOffset);
         } else if (i <= 180) {
            GL11.glVertex2d((double)(x + radius) + xOffset, (double)(y + height - radius) - yOffset);
         } else if (i <= 270) {
            GL11.glVertex2d((double)(x + width - radius) + xOffset, (double)(y + height - radius) - yOffset);
         } else {
            GL11.glVertex2d((double)(x + width - radius) + xOffset, (double)(y + radius) - yOffset);
         }
      }

      GL11.glEnd();
      GL11.glColorMask(true, true, true, true);
      GL11.glDepthMask(true);
      GL11.glStencilFunc(514, 1, 255);
      GL11.glStencilMask(0);
      mc.getTextureManager().bindTexture(skin);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      Gui.drawScaledCustomSizeModalRect((int)x, (int)y, 8.0F, 8.0F, 8, 8, (int)width, (int)height, 64.0F, 64.0F);
      GL11.glDisable(2960);
      GL11.glPopMatrix();
      GL11.glPopAttrib();
   }

   public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
      try {
         mc.getTextureManager().bindTexture(image);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glTexParameteri(3553, 10241, 9987);
         GL11.glTexParameteri(3553, 10240, 9729);
         GL11.glTexParameterf(3553, 34049, -1.0F);
         GL30.glGenerateMipmap(3553);
         Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float)width, (float)height);
         GL11.glDisable(3042);
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public static void renderMainMenuBackground(GuiScreen screen, ScaledResolution sr) {
      int topColor = (new Color(1, 74, 212)).getRGB();
      int bottomColor = (new Color(0, 41, 66)).getRGB();
      screen.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), topColor, bottomColor);
   }

   public static enum DiagonalType {
      LEFT_TOP,
      RIGHT_TOP,
      LEFT_BOTTOM,
      RIGHT_BOTTOM;

      // $FF: synthetic method
      private static DrawUtil.DiagonalType[] $values() {
         return new DrawUtil.DiagonalType[]{LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM};
      }
   }
}
