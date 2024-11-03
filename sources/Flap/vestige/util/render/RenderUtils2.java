package vestige.util.render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import vestige.Flap;
import vestige.font.VestigeFontRenderer;
import vestige.shaders.GaussianFilter;
import vestige.shaders.ShaderScissor;
import vestige.shaders.ShaderUtil;
import vestige.util.IMinecraft;

public class RenderUtils2 {
   public static final ShaderUtil roundedShader = new ShaderUtil("flap/shader/rrect.frag");
   public static final ShaderUtil roundedOutlineShader = new ShaderUtil("flap/shader/rrectOutline.frag");
   private static final ShaderUtil roundedGradientShader = new ShaderUtil("flap/shader/rrectGradient.frag");
   private static Minecraft mc = Minecraft.getMinecraft();
   public static boolean ring_c = false;
   private static final float renderPartialTicks = 0.0F;
   private static final Map<Integer, Integer> shadowCache = new HashMap(5);
   public static VestigeFontRenderer productSans;
   public static VestigeFontRenderer productSans2;

   public static void scissor(double x, double y, double width, double height) {
      ScaledResolution sr = new ScaledResolution(mc);
      double scale = (double)sr.getScaleFactor();
      y = (double)sr.getScaledHeight() - y;
      x *= scale;
      y *= scale;
      width *= scale;
      height *= scale;
      GL11.glScissor((int)x, (int)(y - height), (int)width, (int)height);
   }

   public static void renderItemIcon(double x, double y, ItemStack itemStack) {
      if (itemStack != null) {
         GlStateManager.pushMatrix();
         GlStateManager.enableRescaleNormal();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         RenderHelper.enableGUIStandardItemLighting();
         mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, (int)x, (int)y);
         GlStateManager.disableRescaleNormal();
         GlStateManager.disableBlend();
         RenderHelper.disableStandardItemLighting();
         GlStateManager.popMatrix();
      }

   }

   public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
      return createFrameBuffer(framebuffer, false);
   }

   static void setupRoundedRectUniforms(double x, double y, double width, double height, double radius, ShaderUtil roundedTexturedShader) {
      ScaledResolution sr = new ScaledResolution(mc);
      roundedTexturedShader.setUniformf("location", (float)(x * (double)sr.getScaleFactor()), (float)((double)Minecraft.getMinecraft().displayHeight - height * (double)sr.getScaleFactor() - y * (double)sr.getScaleFactor()));
      roundedTexturedShader.setUniformf("rectSize", (float)(width * (double)sr.getScaleFactor()), (float)(height * (double)sr.getScaleFactor()));
      roundedTexturedShader.setUniformf("radius", (float)(radius * (double)sr.getScaleFactor()));
   }

   public static void drawRoundOutline(double x, double y, double width, double height, double radius, double outlineThickness, Color color, Color outlineColor) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      roundedOutlineShader.init();
      ScaledResolution sr = new ScaledResolution(mc);
      setupRoundedRectUniforms(x, y, width, height, radius, roundedOutlineShader);
      roundedOutlineShader.setUniformf("outlineThickness", (float)(outlineThickness * (double)sr.getScaleFactor()));
      roundedOutlineShader.setUniformf("color", (float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
      roundedOutlineShader.setUniformf("outlineColor", (float)outlineColor.getRed() / 255.0F, (float)outlineColor.getGreen() / 255.0F, (float)outlineColor.getBlue() / 255.0F, (float)outlineColor.getAlpha() / 255.0F);
      ShaderUtil.drawQuads((float)(x - (2.0D + outlineThickness)), (float)(y - (2.0D + outlineThickness)), (float)(width + 4.0D + outlineThickness * 2.0D), (float)(height + 4.0D + outlineThickness * 2.0D));
      roundedOutlineShader.unload();
      GlStateManager.disableBlend();
   }

   public static void drawSoiledEntityESP(double x, double y, double z, double width, double height, int color) {
      GlStateManager.pushMatrix();
      pre3D();
      IMinecraft.mc.entityRenderer.setupCameraTransform(IMinecraft.mc.timer.renderPartialTicks, 2);
      RenderUtil.glColor(color);
      post3D();
      GlStateManager.popMatrix();
   }

   public static void pre3D() {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glShadeModel(7425);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDisable(2896);
      GL11.glDepthMask(false);
      GL11.glHint(3154, 4354);
   }

   public static void post3D() {
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void renderEntity(Entity e, int type, double expand, double shift, int color, boolean damage) {
      if (e instanceof EntityLivingBase) {
         double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosX;
         double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosY;
         double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosZ;
         float d = (float)expand / 40.0F;
         if (e instanceof EntityPlayer && damage && ((EntityPlayer)e).hurtTime != 0) {
            color = Color.RED.getRGB();
         }

         GlStateManager.pushMatrix();
         int i;
         if (type == 3) {
            GL11.glTranslated(x, y - 0.2D, z);
            GL11.glRotated((double)(-mc.getRenderManager().playerViewY), 0.0D, 1.0D, 0.0D);
            GlStateManager.disableDepth();
            GL11.glScalef(0.03F + d, 0.03F + d, 0.03F + d);
            i = Color.black.getRGB();
            Gui.drawRect(-20, -1, -26, 75, i);
            Gui.drawRect(20, -1, 26, 75, i);
            Gui.drawRect(-20, -1, 21, 5, i);
            Gui.drawRect(-20, 70, 21, 75, i);
            if (color != 0) {
               Gui.drawRect(-21, 0, -25, 74, color);
               Gui.drawRect(21, 0, 25, 74, color);
               Gui.drawRect(-21, 0, 24, 4, color);
               Gui.drawRect(-21, 71, 25, 74, color);
            } else {
               int st = getChroma(2L, 0L);
               int en = getChroma(2L, 1000L);
               dGR(-21, 0, -25, 74, st, en);
               dGR(21, 0, 25, 74, st, en);
               Gui.drawRect(-21, 0, 21, 4, en);
               Gui.drawRect(-21, 71, 21, 74, st);
            }

            GlStateManager.enableDepth();
         } else if (type == 4) {
            EntityLivingBase en = (EntityLivingBase)e;
            double r = (double)limit(en.getHealth() / en.getMaxHealth(), 0.0F, 1.0F);
            int b = (int)(74.0D * r);
            int hc = r < 0.3D ? Color.red.getRGB() : (r < 0.5D ? Color.orange.getRGB() : (r < 0.7D ? Color.yellow.getRGB() : Color.green.getRGB()));
            GL11.glTranslated(x, y - 0.2D, z);
            GL11.glRotated((double)(-mc.getRenderManager().playerViewY), 0.0D, 1.0D, 0.0D);
            GlStateManager.disableDepth();
            GL11.glScalef(0.03F + d, 0.03F + d, 0.03F + d);
            i = (int)(21.0D + shift * 2.0D);
            Gui.drawRect(i, -1, i + 4, 75, Color.black.getRGB());
            Gui.drawRect(i + 1, b, i + 3, 74, Color.darkGray.getRGB());
            Gui.drawRect(i + 1, 0, i + 3, b, hc);
            GlStateManager.enableDepth();
         } else if (type == 6) {
            d3p(x, y, z, 0.3D, 75, 2.0F, color, color == 0);
         } else if (type == 7) {
            d3p(x, y, z, 0.699999988079071D, 20, 2.0F, color, color == 0);
         } else {
            if (color == 0) {
               color = getChroma(2L, 0L);
            }

            float a = (float)(color >> 24 & 255) / 255.0F;
            float r = (float)(color >> 16 & 255) / 255.0F;
            float g = (float)(color >> 8 & 255) / 255.0F;
            float b = (float)(color & 255) / 255.0F;
            AxisAlignedBB bbox = e.getEntityBoundingBox().expand(0.1D + expand, 0.1D + expand, 0.1D + expand);
            AxisAlignedBB axis = new AxisAlignedBB(bbox.minX - e.posX + x, bbox.minY - e.posY + y, bbox.minZ - e.posZ + z, bbox.maxX - e.posX + x, bbox.maxY - e.posY + y, bbox.maxZ - e.posZ + z);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glLineWidth(2.0F);
            GL11.glColor4f(r, g, b, a);
            if (type == 1) {
               RenderGlobal.func_181561_a(axis);
            } else if (type == 2) {
               drawBoundingBox(axis, r, g, b);
            }

            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
         }

         GlStateManager.popMatrix();
      }

   }

   public static void dGR(int left, int top, int right, int bottom, int startColor, int endColor) {
      int j;
      if (left < right) {
         j = left;
         left = right;
         right = j;
      }

      if (top < bottom) {
         j = top;
         top = bottom;
         bottom = j;
      }

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

      // boru fucking nigger
   }

   public static void d3p(double x, double y, double z, double radius, int sides, float lineWidth, int color, boolean chroma) {
      float a = (float)(color >> 24 & 255) / 255.0F;
      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      mc.entityRenderer.disableLightmap();
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2929);
      GL11.glEnable(2848);
      GL11.glDepthMask(false);
      GL11.glLineWidth(lineWidth);
      if (!chroma) {
         GL11.glColor4f(r, g, b, a);
      }

      GL11.glBegin(1);
      long d = 0L;
      long ed = 15000L / (long)sides;
      long hed = ed / 2L;

      for(int i = 0; i < sides * 2; ++i) {
         if (chroma) {
            if (i % 2 != 0) {
               if (i == 47) {
                  d = hed;
               }

               d += ed;
            }

            int c = getChroma(2L, d);
            float r2 = (float)(c >> 16 & 255) / 255.0F;
            float g2 = (float)(c >> 8 & 255) / 255.0F;
            float b2 = (float)(c & 255) / 255.0F;
            GL11.glColor3f(r2, g2, b2);
         }

         double angle = 6.283185307179586D * (double)i / (double)sides + Math.toRadians(180.0D);
         GL11.glVertex3d(x + Math.cos(angle) * radius, y, z + Math.sin(angle) * radius);
      }

      GL11.glEnd();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDepthMask(true);
      GL11.glDisable(2848);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      mc.entityRenderer.enableLightmap();
   }

   public static int getChroma(long speed, long... delay) {
      long time = System.currentTimeMillis() + (delay.length > 0 ? delay[0] : 0L);
      return Color.getHSBColor((float)(time % (15000L / speed)) / (15000.0F / (float)speed), 1.0F, 1.0F).getRGB();
   }

   public static int limit(int value, int min, int max) {
      return Math.max(Math.min(value, max), min);
   }

   public static long limit(long value, long min, long max) {
      return Math.max(Math.min(value, max), min);
   }

   public static float limit(float value, float min, float max) {
      return Math.max(Math.min(value, max), min);
   }

   public static double limit(double value, double min, double max) {
      return Math.max(Math.min(value, max), min);
   }

   public static Timer getTimer() {
      try {
         Field timerField = Minecraft.class.getDeclaredField("timer");
         timerField.setAccessible(true);
         return (Timer)timerField.get(Minecraft.getMinecraft());
      } catch (IllegalAccessException | NoSuchFieldException var1) {
         var1.printStackTrace();
         return null;
      }
   }

   public static Framebuffer createFrameBuffer(Framebuffer framebuffer, boolean depth) {
      if (needsNewFramebuffer(framebuffer)) {
         if (framebuffer != null) {
            framebuffer.deleteFramebuffer();
         }

         return new Framebuffer(mc.displayWidth, mc.displayHeight, depth);
      } else {
         return framebuffer;
      }
   }

   public static boolean needsNewFramebuffer(Framebuffer framebuffer) {
      return framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight;
   }

   public static void drawBloomShadow(float x, float y, float x2, float y2, int blurRadius, int roundRadius, int color, boolean scissor) {
      float width = x2 - x;
      float height = y2 - y;
      width += (float)(blurRadius * 2);
      height += (float)(blurRadius * 2);
      x -= (float)blurRadius - 0.5F;
      y -= (float)blurRadius - 0.5F;
      int identifier = Arrays.deepHashCode(new Object[]{width, height, blurRadius, roundRadius});
      Integer image = (Integer)shadowCache.get(identifier);
      if (image == null) {
         if (width <= 0.0F) {
            width = 1.0F;
         }

         if (height <= 0.0F) {
            height = 1.0F;
         }

         BufferedImage original = new BufferedImage((int)width, (int)height, 3);
         Graphics g = original.getGraphics();
         g.setColor(new Color(color));
         g.fillRoundRect(blurRadius, blurRadius, (int)(width - (float)(blurRadius * 2)), (int)(height - (float)(blurRadius * 2)), roundRadius, roundRadius);
         g.dispose();
         GaussianFilter op = new GaussianFilter((float)blurRadius);
         BufferedImage blurred = op.filter(original, (BufferedImage)null);
         if (scissor) {
            blurred = (new ShaderScissor(blurRadius, blurRadius, (int)(width - (float)(blurRadius * 2)), (int)(height - (float)(blurRadius * 2)), blurred, 1, false, false)).generate();
         }

         image = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), blurred, true, false);
         shadowCache.put(identifier, image);
      }

      drawImage(image, x, y, width, height, color);
   }

   public static void enableGL2D() {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(true);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
   }

   public static void disableGL2D() {
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
   }

   public static void color(int color) {
      float f = (float)(color >> 24 & 255) / 255.0F;
      float f1 = (float)(color >> 16 & 255) / 255.0F;
      float f2 = (float)(color >> 8 & 255) / 255.0F;
      float f3 = (float)(color & 255) / 255.0F;
      GL11.glColor4f(f1, f2, f3, f);
   }

   public static void drawImage(int image, float x, float y, float width, float height, int color) {
      enableGL2D();
      GL11.glPushMatrix();
      GlStateManager.alphaFunc(516, 0.01F);
      GL11.glEnable(3553);
      GL11.glDisable(2884);
      GL11.glEnable(3008);
      GlStateManager.enableBlend();
      GlStateManager.bindTexture(image);
      color(color);
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2f(x, y);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2f(x, y + height);
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex2f(x + width, y + height);
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2f(x + width, y);
      GL11.glEnd();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.resetColor();
      GL11.glEnable(2884);
      GL11.glPopMatrix();
      disableGL2D();
   }

   public static void drawRect(double left, double top, double right, double bottom, int color) {
      float f3 = (float)(color >> 24 & 255) / 255.0F;
      float f = (float)(color >> 16 & 255) / 255.0F;
      float f1 = (float)(color >> 8 & 255) / 255.0F;
      float f2 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(f, f1, f2, f3);
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b((double)left, (double)bottom, 0.0D).func_181675_d();
      worldrenderer.func_181662_b((double)right, (double)bottom, 0.0D).func_181675_d();
      worldrenderer.func_181662_b((double)right, (double)top, 0.0D).func_181675_d();
      worldrenderer.func_181662_b((double)left, (double)top, 0.0D).func_181675_d();
      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void drawProgressBar(int width, int height, float percentage, Color barcolor, int textcolor, boolean percentagetext, int modetext, boolean textenabed, String text, int textextcolor) {
      productSans = Flap.instance.getFontManager().getProductSansBold20();
      productSans2 = Flap.instance.getFontManager().getProductSans();
      ScaledResolution scaledRes = new ScaledResolution(mc);
      int progressWidth = (int)((float)width * percentage / 100.0F);
      double var10000 = (double)(scaledRes.getScaledWidth() / 2);
      double x = (double)((scaledRes.getScaledWidth() - width) / 2);
      double y = (double)((scaledRes.getScaledHeight() - height) / 2 + 45);
      double yText = (double)((scaledRes.getScaledHeight() - height) / 2 + 30);
      drawBloomShadow((float)x, (float)y, (float)(x + (double)width), (float)(y + (double)height), 10, 7, (new Color(0, 0, 0, 70)).getRGB(), false);
      drawRoundOutline(x, y, (double)width, (double)height, 1.0D, 0.0D, new Color(29, 23, 21, 255), new Color(35, 35, 35, 0));
      drawRoundOutline(x, y, (double)progressWidth, (double)height, 1.0D, 0.0D, barcolor, new Color(0, 0, 0, 0));
      if (textenabed) {
         switch(modetext) {
         case 1:
            productSans.drawStringWithShadow(text, x + (double)(width / 2) - productSans.getStringWidth(text) / 2.0D, yText, textextcolor);
            break;
         case 2:
            productSans.drawStringWithShadow(text, x + (double)(width / 2) - productSans.getStringWidth(text) / 2.0D, yText + 30.0D, textextcolor);
         }
      }

      if (percentagetext) {
         switch(modetext) {
         case 1:
            productSans2.drawStringWithShadow(percentage + "%", x + (double)progressWidth, yText + 30.0D, textcolor);
            break;
         case 2:
            productSans2.drawStringWithShadow(percentage + "%", x + (double)progressWidth, yText, textcolor);
         }
      }

   }

   public static void drawText(String text, int color) {
      ScaledResolution scaledResolution = new ScaledResolution(mc);
      mc.fontRendererObj.drawString(text, (float)(scaledResolution.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(text) / 2), (float)(scaledResolution.getScaledHeight() / 2 + 15), color, false);
   }

   public static void callRenderMode(String methodName, Object... args) {
      try {
         Method method = RenderUtils2.class.getDeclaredMethod(methodName, getParameterTypes(args));
         method.invoke((Object)null, args);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private static Class<?>[] getParameterTypes(Object[] args) {
      Class<?>[] parameterTypes = new Class[args.length];

      for(int i = 0; i < args.length; ++i) {
         parameterTypes[i] = args[i].getClass();
      }

      return parameterTypes;
   }

   public static void drawPolygon(double n, double n2, double n3, int n4, int n5) {
      if (n4 >= 3) {
         float n6 = (float)(n5 >> 24 & 255) / 255.0F;
         float n7 = (float)(n5 >> 16 & 255) / 255.0F;
         float n8 = (float)(n5 >> 8 & 255) / 255.0F;
         float n9 = (float)(n5 & 255) / 255.0F;
         Tessellator getInstance = Tessellator.getInstance();
         WorldRenderer getWorldRenderer = getInstance.getWorldRenderer();
         GlStateManager.enableBlend();
         GlStateManager.disableTexture2D();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GL11.glColor4f(n7, n8, n9, n6);
         getWorldRenderer.func_181668_a(6, DefaultVertexFormats.field_181705_e);

         for(int i = 0; i < n4; ++i) {
            double n10 = 6.283185307179586D * (double)i / (double)n4 + Math.toRadians(180.0D);
            getWorldRenderer.func_181662_b(n + Math.sin(n10) * n3, n2 + Math.cos(n10) * n3, 0.0D).func_181675_d();
         }

         getInstance.draw();
         GlStateManager.enableTexture2D();
         GlStateManager.disableBlend();
      }
   }

   public static void drawBoundingBox(AxisAlignedBB abb, float r, float g, float b) {
      drawBoundingBox(abb, r, g, b, 0.25F);
   }

   public static void drawBoundingBox(AxisAlignedBB abb, float r, float g, float b, float a) {
      /*Tessellator ts = Tessellator.getInstance();
      WorldRenderer vb = ts.getWorldRenderer();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      ts.draw();*/
   }

   public static void drawArrow(float x, float y, int color, double width, double length) {
      GL11.glPushMatrix();
      GL11.glEnable(2848);
      GL11.glDisable(3553);
      glColor(color);
      GL11.glLineWidth((float)width);
      float halfWidth = (float)(width / 2.0D);
      float xOffset = halfWidth / 2.0F;
      float yOffset = halfWidth / 2.0F;
      GL11.glBegin(1);
      GL11.glVertex2d((double)(x - xOffset), (double)(y + yOffset));
      GL11.glVertex2d((double)x + length - (double)xOffset, (double)y - length + (double)yOffset);
      GL11.glVertex2d((double)x + length - (double)xOffset, (double)y - length + (double)yOffset);
      GL11.glVertex2d((double)x + 2.0D * length - (double)xOffset, (double)(y + yOffset));
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glPopMatrix();
   }

   public static void glColor(int n) {
      GL11.glColor4f((float)(n >> 16 & 255) / 255.0F, (float)(n >> 8 & 255) / 255.0F, (float)(n & 255) / 255.0F, (float)(n >> 24 & 255) / 255.0F);
   }

   public static void drawRoundedGradientOutlinedRectangle(float n, float n2, float n3, float n4, float n5, int n6, int n7, int n8) {
      n = (float)((double)n * 2.0D);
      n2 = (float)((double)n2 * 2.0D);
      n3 = (float)((double)n3 * 2.0D);
      n4 = (float)((double)n4 * 2.0D);
      GL11.glPushAttrib(1);
      GL11.glScaled(0.5D, 0.5D, 0.5D);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glBegin(9);
      glColor(n6);

      int n19;
      double n20;
      for(n19 = 0; n19 <= 90; n19 += 3) {
         n20 = (double)((float)n19 * 0.017453292F);
         GL11.glVertex2d((double)(n + n5) + Math.sin(n20) * (double)n5 * -1.0D, (double)(n2 + n5) + Math.cos(n20) * (double)n5 * -1.0D);
      }

      for(n19 = 90; n19 <= 180; n19 += 3) {
         n20 = (double)((float)n19 * 0.017453292F);
         GL11.glVertex2d((double)(n + n5) + Math.sin(n20) * (double)n5 * -1.0D, (double)(n4 - n5) + Math.cos(n20) * (double)n5 * -1.0D);
      }

      for(n19 = 0; n19 <= 90; n19 += 3) {
         n20 = (double)((float)n19 * 0.017453292F);
         GL11.glVertex2d((double)(n3 - n5) + Math.sin(n20) * (double)n5, (double)(n4 - n5) + Math.cos(n20) * (double)n5);
      }

      for(n19 = 90; n19 <= 180; n19 += 3) {
         n20 = (double)((float)n19 * 0.017453292F);
         GL11.glVertex2d((double)(n3 - n5) + Math.sin(n20) * (double)n5, (double)(n2 + n5) + Math.cos(n20) * (double)n5);
      }

      GL11.glEnd();
      GL11.glPushMatrix();
      GL11.glShadeModel(7425);
      GL11.glLineWidth(2.0F);
      GL11.glBegin(2);
      if ((long)n7 != 0L) {
         glColor(n7);
      }

      for(n19 = 0; n19 <= 90; n19 += 3) {
         n20 = (double)((float)n19 * 0.017453292F);
         GL11.glVertex2d((double)(n + n5) + Math.sin(n20) * (double)n5 * -1.0D, (double)(n2 + n5) + Math.cos(n20) * (double)n5 * -1.0D);
      }

      for(n19 = 90; n19 <= 180; n19 += 3) {
         n20 = (double)((float)n19 * 0.017453292F);
         GL11.glVertex2d((double)(n + n5) + Math.sin(n20) * (double)n5 * -1.0D, (double)(n4 - n5) + Math.cos(n20) * (double)n5 * -1.0D);
      }

      if (n8 != 0) {
         glColor(n8);
      }

      for(n19 = 0; n19 <= 90; n19 += 3) {
         n20 = (double)((float)n19 * 0.017453292F);
         GL11.glVertex2d((double)(n3 - n5) + Math.sin(n20) * (double)n5, (double)(n4 - n5) + Math.cos(n20) * (double)n5);
      }

      for(n19 = 90; n19 <= 180; n19 += 3) {
         n20 = (double)((float)n19 * 0.017453292F);
         GL11.glVertex2d((double)(n3 - n5) + Math.sin(n20) * (double)n5, (double)(n2 + n5) + Math.cos(n20) * (double)n5);
      }

      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glScaled(2.0D, 2.0D, 2.0D);
      GL11.glPopAttrib();
      GL11.glLineWidth(1.0F);
      GL11.glShadeModel(7424);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawRoundedRectangle(float n, float n2, float n3, float n4, float n5, int n6) {
      n = (float)((double)n * 2.0D);
      n2 = (float)((double)n2 * 2.0D);
      n3 = (float)((double)n3 * 2.0D);
      n4 = (float)((double)n4 * 2.0D);
      GL11.glPushAttrib(0);
      GL11.glScaled(0.5D, 0.5D, 0.5D);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glBegin(9);
      glColor(n6);

      int l;
      double n10;
      for(l = 0; l <= 90; l += 3) {
         n10 = (double)((float)l * 0.017453292F);
         GL11.glVertex2d((double)(n + n5) + Math.sin(n10) * (double)n5 * -1.0D, (double)(n2 + n5) + Math.cos(n10) * (double)n5 * -1.0D);
      }

      for(l = 90; l <= 180; l += 3) {
         n10 = (double)((float)l * 0.017453292F);
         GL11.glVertex2d((double)(n + n5) + Math.sin(n10) * (double)n5 * -1.0D, (double)(n4 - n5) + Math.cos(n10) * (double)n5 * -1.0D);
      }

      for(l = 0; l <= 90; l += 3) {
         n10 = (double)((float)l * 0.017453292F);
         GL11.glVertex2d((double)(n3 - n5) + Math.sin(n10) * (double)n5, (double)(n4 - n5) + Math.cos(n10) * (double)n5);
      }

      for(l = 90; l <= 180; l += 3) {
         n10 = (double)((float)l * 0.017453292F);
         GL11.glVertex2d((double)(n3 - n5) + Math.sin(n10) * (double)n5, (double)(n2 + n5) + Math.cos(n10) * (double)n5);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glScaled(2.0D, 2.0D, 2.0D);
      GL11.glPopAttrib();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawRoundedGradientRect(float x, float y, float x2, float y2, float n5, int n6, int n7, int n8, int n9) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushAttrib(0);
      GL11.glScaled(0.5D, 0.5D, 0.5D);
      x = (float)((double)x * 2.0D);
      y = (float)((double)y * 2.0D);
      x2 = (float)((double)x2 * 2.0D);
      y2 = (float)((double)y2 * 2.0D);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      glColor(n6);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glBegin(9);

      int l;
      double n13;
      for(l = 0; l <= 90; l += 3) {
         n13 = (double)((float)l * 0.017453292F);
         GL11.glVertex2d((double)(x + n5) + Math.sin(n13) * (double)n5 * -1.0D, (double)(y + n5) + Math.cos(n13) * (double)n5 * -1.0D);
      }

      glColor(n7);

      for(l = 90; l <= 180; l += 3) {
         n13 = (double)((float)l * 0.017453292F);
         GL11.glVertex2d((double)(x + n5) + Math.sin(n13) * (double)n5 * -1.0D, (double)(y2 - n5) + Math.cos(n13) * (double)n5 * -1.0D);
      }

      glColor(n8);

      for(l = 0; l <= 90; l += 3) {
         n13 = (double)((float)l * 0.017453292F);
         GL11.glVertex2d((double)(x2 - n5) + Math.sin(n13) * (double)n5, (double)(y2 - n5) + Math.cos(n13) * (double)n5);
      }

      glColor(n9);

      for(l = 90; l <= 180; l += 3) {
         n13 = (double)((float)l * 0.017453292F);
         GL11.glVertex2d((double)(x2 - n5) + Math.sin(n13) * (double)n5, (double)(y + n5) + Math.cos(n13) * (double)n5);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glScaled(2.0D, 2.0D, 2.0D);
      GL11.glPopAttrib();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
   }

   public static void drawImage(ResourceLocation res, float x, float y, float width, float height, Color color) {
      drawImage(res, x, y, width, height, color.getRGB());
   }

   public static void drawImage2(ResourceLocation image, float x, float y, int width, int height, float alpha) {
      GlStateManager.disableAlpha();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableLighting();
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
      Minecraft.getMinecraft().getTextureManager().bindTexture(image);
      Gui.drawModalRectWithCustomSizedTexture((int)x, (int)y, 0.0F, 0.0F, width, height, (float)width, (float)height);
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
   }

   public static void drawImage(ResourceLocation res, float x, float y, float width, float height, int color) {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      GL14.glBlendFuncSeparate(770, 771, 1, 0);
      int red = color >> 16 & 255;
      int green = color >> 8 & 255;
      int blue = color & 255;
      int alpha = color >> 24 & 255;
      GL11.glColor4f((float)red / 255.0F, (float)green / 255.0F, (float)blue / 255.0F, (float)alpha / 255.0F);
      mc.getTextureManager().bindTexture(res);
      drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }

   public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
     Gui.drawModalRectWithCustomSizedTexture((int)x,(int)y,u,v,(int)width,(int)height,textureWidth,textureHeight);
   }

   public static int limit(double i) {
      if (i > 255.0D) {
         return 255;
      } else {
         return i < 0.0D ? 0 : (int)i;
      }
   }
}
