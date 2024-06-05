package Focus.Beta.UTILS.render;


import Focus.Beta.UTILS.world.Wrapper;
import com.mojang.authlib.GameProfile;

import Focus.Beta.IMPL.font.CFontRenderer;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
public enum RenderUtil {
   INSTANCE;

   public static Minecraft mc = Minecraft.getMinecraft();
   public static float delta;

    private static int lastWidth;

    private static int lastHeight;

    private static LockedResolution lockedResolution;

    public static final Pattern COLOR_PATTERN = Pattern.compile("(?i)[0-9A-FK-OR]");

   public static int width() {
      return (new ScaledResolution(Minecraft.getMinecraft())).getScaledWidth();
   }

   public static int height() {
      return (new ScaledResolution(Minecraft.getMinecraft())).getScaledHeight();
   }
    public static void drawBorderedRect(final double left, final double top, final double right, final double bottom, final double borderWidth, final int insideColor, final int borderColor, final boolean borderIncludedInBounds) {
        drawRect(left - (borderIncludedInBounds ? 0.0 : borderWidth), top - (borderIncludedInBounds ? 0.0 : borderWidth), right + (borderIncludedInBounds ? 0.0 : borderWidth), bottom + (borderIncludedInBounds ? 0.0 : borderWidth), borderColor);
        drawRect(left + (borderIncludedInBounds ? borderWidth : 0.0), top + (borderIncludedInBounds ? borderWidth : 0.0), right - (borderIncludedInBounds ? borderWidth : 0.0), bottom - (borderIncludedInBounds ? borderWidth : 0.0), insideColor);
    }
    public static void startScissorBox(LockedResolution sr2, int x, int y, int width, int height) {
       ScaledResolution sr = new ScaledResolution(mc);
           int sf = sr.getScaleFactor();
            GL11.glScissor(
                       x * sf, (
                               sr.getScaledHeight() - y + height) * sf,
                         width * sf,
                       height * sf);
         }
    public static float animate(float target, float current, float speed) {
        speed = MathHelper.clamp_float(speed * 0.1F * delta, 0.0F, 1.0F);
        float dif = Math.max(target, current) - Math.min(target, current);
        float factor = dif * speed;
        if (target > current) {
            current += factor;
        } else {
            current -= factor;
        }
        return current;
    }

    public static LockedResolution getLockedResolution() {
        int width = Display.getWidth();
        int height = Display.getHeight();
        if (width != lastWidth || height != lastHeight) {
            lastWidth = width;
            lastHeight = height;
            return lockedResolution = new LockedResolution(width / 2, height / 2);
        }
        return lockedResolution;
    }

    public static void rect(float x, float y, float x2, float y2, Color color) {
        Gui.drawRect(x, y, x2, y2, color.getRGB());
        GlStateManager.resetColor();
    }
   public static int getRainbow(int speed, int offset, float s) {
       float hue = (System.currentTimeMillis() + offset) % speed;
       hue /= speed;
       return Color.getHSBColor(hue, s, 1f).getRGB();

   }
   
   public static void drawDynamicTexture(DynamicTexture texture, float x, float y, float width, float height) {
       drawDynamicTexture(texture, (int)(x), (int)(y), (int)(width), (int)(height), 255);
   }
   
   public static void quickRenderCircle(double x, double y, double start, double end, double w, double h) {
       if (start > end) {
           double temp = end;
           end = start;
           start = temp;
       }

       GL11.glBegin(GL11.GL_TRIANGLE_FAN);
       GL11.glVertex2d(x, y);
       for(double i = end; i >= start; i-=4) {
           double ldx = Math.cos(i * Math.PI / 180.0) * w;
           double ldy = Math.sin(i * Math.PI / 180.0) * h;
           GL11.glVertex2d(x + ldx, y + ldy);
       }
       GL11.glVertex2d(x, y);
       GL11.glEnd();
   }

   
   public static void drawCircleRect(float x, float y, float x1, float y1, float radius, int color){
       GlStateManager.enableBlend();
       GlStateManager.disableTexture2D();
       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
       glColor(color);
       quickRenderCircle(x1-radius,y1-radius,0,90,radius,radius);
       quickRenderCircle(x+radius,y1-radius,90,180,radius,radius);
       quickRenderCircle(x+radius,y+radius,180,270,radius,radius);
       quickRenderCircle(x1-radius,y+radius,270,360,radius,radius);
       quickDrawRect(x+radius,y+radius,x1-radius,y1-radius);
       quickDrawRect(x,y+radius,x+radius,y1-radius);
       quickDrawRect(x1-radius,y+radius,x1,y1-radius);
       quickDrawRect(x+radius,y,x1-radius,y+radius);
       quickDrawRect(x+radius,y1-radius,x1-radius,y1);

       GlStateManager.enableTexture2D();
       GlStateManager.disableBlend();
   }
   
   public static void quickDrawRect(final float x, final float y, final float x2, final float y2) {
       glBegin(GL_QUADS);

       glVertex2d(x2, y);
       glVertex2d(x, y);
       glVertex2d(x, y2);
       glVertex2d(x2, y2);

       glEnd();
   }


   public static void drawDynamicTexture(DynamicTexture texture, int x, int y, int width, int height, float opacity) {
       glPushMatrix();
       glDisable(GL_DEPTH_TEST);
       glEnable(GL_BLEND);
       glDepthMask(false);
       GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
       OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
       glColor4f(1.0f, 1.0f, 1.0f, opacity / 255);
       DynamicTextureUtil.bindTexture(texture);
       glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
       glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
       Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
       glDepthMask(true);
       glDisable(GL_BLEND);
       glEnable(GL_DEPTH_TEST);
       glPopMatrix();
   }
   
   public static double[] convertTo2D(double x, double y, double z) {
       double[] arrd;
       java.nio.FloatBuffer screenCoords = org.lwjgl.BufferUtils.createFloatBuffer((int) 3);
       java.nio.IntBuffer viewport = org.lwjgl.BufferUtils.createIntBuffer((int) 16);
       java.nio.FloatBuffer modelView = org.lwjgl.BufferUtils.createFloatBuffer((int) 16);
       java.nio.FloatBuffer projection = org.lwjgl.BufferUtils.createFloatBuffer((int) 16);
       GL11.glGetFloat((int) 2982, (java.nio.FloatBuffer) modelView);
       GL11.glGetFloat((int) 2983, (java.nio.FloatBuffer) projection);
       GL11.glGetInteger((int) 2978, (java.nio.IntBuffer) viewport);
       boolean result = org.lwjgl.util.glu.GLU.gluProject((float) ((float) x), (float) ((float) y),
               (float) ((float) z), (java.nio.FloatBuffer) modelView, (java.nio.FloatBuffer) projection,
               (java.nio.IntBuffer) viewport, (java.nio.FloatBuffer) screenCoords);
       if (result) {
           double[] arrd2 = new double[3];
           arrd2[0] = screenCoords.get(0);
           arrd2[1] = (float) org.lwjgl.opengl.Display.getHeight() - screenCoords.get(1);
           arrd = arrd2;
           arrd2[2] = screenCoords.get(2);
       } else {
           arrd = null;
       }
       return arrd;
   }
    public static void drawBorderedRect(float var0, float var1, float var2, float var3, float var4, int var5, int var6) {
        drawRect(var0, var1, var2, var3, var6);
        float var7 = (float)(var5 >> 24 & 255) / 255.0F;
        float var8 = (float)(var5 >> 16 & 255) / 255.0F;
        float var9 = (float)(var5 >> 8 & 255) / 255.0F;
        float var10 = (float)(var5 & 255) / 255.0F;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(var8, var9, var10, var7);
        GL11.glLineWidth(var4);
        GL11.glBegin(1);
        GL11.glVertex2d((double)var0, (double)var1);
        GL11.glVertex2d((double)var0, (double)var3);
        GL11.glVertex2d((double)var2, (double)var3);
        GL11.glVertex2d((double)var2, (double)var1);
        GL11.glVertex2d((double)var0, (double)var1);
        GL11.glVertex2d((double)var2, (double)var1);
        GL11.glVertex2d((double)var0, (double)var3);
        GL11.glVertex2d((double)var2, (double)var3);
        GL11.glEnd();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 255.0F);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void drawBorderedRect(double var0, double var2, double var4, double var6, float var8, int var9, int var10) {
        drawRect((float)var0, (float)var2, (float)var4, (float)var6, var10);
        float var11 = (float)(var9 >> 24 & 255) / 255.0F;
        float var12 = (float)(var9 >> 16 & 255) / 255.0F;
        float var13 = (float)(var9 >> 8 & 255) / 255.0F;
        float var14 = (float)(var9 & 255) / 255.0F;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(var12, var13, var14, var11);
        GL11.glLineWidth(var8);
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
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
        GL11.glColor4f(255.0F, 1.0F, 1.0F, 255.0F);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
   public static void enableGL3D(float lineWidth) {
      GL11.glDisable(3008);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glEnable(2884);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
      GL11.glLineWidth(lineWidth);
   }


    public static void drawRoundedRect(double x, double y, double width, double height, double radius, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        double x1 = x + width;
        double y1 = y + height;
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5D, 0.5D, 0.5D);

        x *= 2.0D;
        y *= 2.0D;
        x1 *= 2.0D;
        y1 *= 2.0D;

        GL11.glDisable(3553);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glEnable(2848);

        GL11.glBegin(9);

        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D,
                    y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        }

        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D,
                    y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        }

        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius,
                    y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius);
        }

        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius,
                    y + radius + Math.cos(i * Math.PI / 180.0D) * radius);
        }

        glEnd();

        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(3553);

        GL11.glScaled(2.0D, 2.0D, 2.0D);

        GL11.glPopAttrib();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }


    private static int lastScale;

    private static int lastScaleWidth;

    private static int lastScaleHeight;

    private static Framebuffer buffer;

    private static ShaderGroup blurShader;


    private static final ResourceLocation shader = new ResourceLocation("focus/blur/blur.json");

    public static void initFboAndShader() {
        try {
            blurShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), shader);
            blurShader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            buffer = blurShader.mainFramebuffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void blur(float x, float y, float x2, float y2, ScaledResolution sr){
       int factor  = sr.getScaleFactor();
       int factor2 = sr.getScaledWidth();
       int factor3 = sr.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null || blurShader == null)
            initFboAndShader();
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        GL11.glEnable(3089);
        crop(x, y, x2, y2);
        buffer.framebufferHeight = mc.displayHeight;
        buffer.framebufferWidth = mc.displayWidth;
        GlStateManager.resetColor();
        blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
        buffer.bindFramebuffer(true);
        mc.getFramebuffer().bindFramebuffer(true);
        GL11.glDisable(3089);
    }

    public static void crop(float x, float y, float x2, float y2) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int factor = scaledResolution.getScaleFactor();
        GL11.glScissor((int)(x * factor), (int)((scaledResolution.getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
    }


    public static void blur(float x, float y, float x2, float y2) {
        GlStateManager.disableAlpha();
        blur(x, y, x2, y2, new ScaledResolution(mc));
        GlStateManager.enableAlpha();
    }
    public static void blur2(float x, float y, float x2, float y2, float h, float w) {
        GlStateManager.disableAlpha();
        blur(x, y, x2 + w, y2 + h, new ScaledResolution(mc));
        GlStateManager.enableAlpha();
    }
   public static void drawOutlinedString(CFontRenderer fr, String s, float x, float y, int color, int outlineColor) {
      fr.drawString(stripColor(s), x - 1.0F, y, outlineColor);
      fr.drawString(stripColor(s), x, y - 1.0F, outlineColor);
      fr.drawString(stripColor(s), x + 1.0F, y, outlineColor);
      fr.drawString(stripColor(s), x, y + 1.0F, outlineColor);
      fr.drawString(s, x, y, color);
   }
   public static void drawOutlinedString(String s, float x, float y, int color, int outlineColor) {
	    (Minecraft.getMinecraft()).fontRendererObj.drawString(s, x - 0.5F, y, outlineColor);
	    (Minecraft.getMinecraft()).fontRendererObj.drawString(s, x, y - 0.5F, outlineColor);
	    (Minecraft.getMinecraft()).fontRendererObj.drawString(s, x + 0.5F, y, outlineColor);
	    (Minecraft.getMinecraft()).fontRendererObj.drawString(s, x, y + 0.5F, outlineColor);
	    (Minecraft.getMinecraft()).fontRendererObj.drawString(s, x, y, color);
	  }
   public static String stripColor(String input) {
      return COLOR_PATTERN.matcher(input).replaceAll("");
   }
   public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
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
	      worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
	      worldrenderer.pos(right, top, 0.0D).color(f1, f2, f3, f).endVertex();
	      worldrenderer.pos(left, top, 0.0D).color(f1, f2, f3, f).endVertex();
	      worldrenderer.pos(left, bottom, 0.0D).color(f5, f6, f7, f4).endVertex();
	      worldrenderer.pos(right, bottom, 0.0D).color(f5, f6, f7, f4).endVertex();
	      tessellator.draw();
	      GlStateManager.shadeModel(7424);
	      GlStateManager.disableBlend();
	      GlStateManager.enableAlpha();
	      GlStateManager.enableTexture2D();
	   }
   public static int rainbow(int delay) {
      double rainbow = Math.ceil((double)(System.currentTimeMillis() + (long)delay) / 10.0D);
      return Color.getHSBColor((float)((rainbow %= 360.0D) / 360.0D), 0.5F, 1.0F).getRGB();
   }

   public static int rainbow(int delay, float slowspeed) {
      double rainbow = Math.ceil((double)(System.currentTimeMillis() + (long)delay) / (double)slowspeed);
      return Color.getHSBColor((float)((rainbow %= 360.0D) / 360.0D), 0.5F, 1.0F).getRGB();
   }

   public static void disableGL3D() {
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glEnable(3008);
      GL11.glDepthMask(true);
      GL11.glCullFace(1029);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
   }

   public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int borderColor) {
      R2DUtils.enableGL2D();
      glColor(borderColor);
      R2DUtils.drawRect(x + width, y, x1 - width, y + width);
      R2DUtils.drawRect(x, y, x + width, y1);
      R2DUtils.drawRect(x1 - width, y, x1, y1);
      R2DUtils.drawRect(x + width, y1 - width, x1 - width, y1);
      R2DUtils.disableGL2D();
   }

   public static void drawCircle(double x, double y, double radius, int c) {
      float f2 = (float)(c >> 24 & 255) / 255.0F;
      float f22 = (float)(c >> 16 & 255) / 255.0F;
      float f3 = (float)(c >> 8 & 255) / 255.0F;
      float f4 = (float)(c & 255) / 255.0F;
      GlStateManager.alphaFunc(516, 0.001F);
      GlStateManager.color(f22, f3, f4, f2);
      GlStateManager.enableAlpha();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      Tessellator tes = Tessellator.getInstance();

      for(double i = 0.0D; i < 360.0D; ++i) {
         double f5 = Math.sin(i * Math.PI / 180.0D) * radius;
         double f6 = Math.cos(i * Math.PI / 180.0D) * radius;
         GL11.glVertex2d((double)f3 + x, (double)f4 + y);
      }

      GlStateManager.disableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.enableTexture2D();
      GlStateManager.alphaFunc(516, 0.1F);
   }

   public static void drawFilledCircle(double x, double y, double r, int c, int id) {
      float f = (float)(c >> 24 & 255) / 255.0F;
      float f1 = (float)(c >> 16 & 255) / 255.0F;
      float f2 = (float)(c >> 8 & 255) / 255.0F;
      float f3 = (float)(c & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glColor4f(f1, f2, f3, f);
      GL11.glBegin(9);
      int i;
      double x2;
      double y2;
      if(id == 1) {
         GL11.glVertex2d(x, y);

         for(i = 0; i <= 90; ++i) {
            x2 = Math.sin((double)i * 3.141526D / 180.0D) * r;
            y2 = Math.cos((double)i * 3.141526D / 180.0D) * r;
            GL11.glVertex2d(x - x2, y - y2);
         }
      } else if(id == 2) {
         GL11.glVertex2d(x, y);

         for(i = 90; i <= 180; ++i) {
            x2 = Math.sin((double)i * 3.141526D / 180.0D) * r;
            y2 = Math.cos((double)i * 3.141526D / 180.0D) * r;
            GL11.glVertex2d(x - x2, y - y2);
         }
      } else if(id == 3) {
         GL11.glVertex2d(x, y);

         for(i = 270; i <= 360; ++i) {
            x2 = Math.sin((double)i * 3.141526D / 180.0D) * r;
            y2 = Math.cos((double)i * 3.141526D / 180.0D) * r;
            GL11.glVertex2d(x - x2, y - y2);
         }
      } else if(id == 4) {
         GL11.glVertex2d(x, y);

         for(i = 180; i <= 270; ++i) {
            x2 = Math.sin((double)i * 3.141526D / 180.0D) * r;
            y2 = Math.cos((double)i * 3.141526D / 180.0D) * r;
            GL11.glVertex2d(x - x2, y - y2);
         }
      } else {
         for(i = 0; i <= 360; ++i) {
            x2 = Math.sin((double)i * 3.141526D / 180.0D) * r;
            y2 = Math.cos((double)i * 3.141526D / 180.0D) * r;
            GL11.glVertex2f((float)(x - x2), (float)(y - y2));
         }
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawFullCircle(int cx, int cy, double r, int segments, float lineWidth, int part, int c) {
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      r *= 2.0D;
      cx *= 2;
      cy *= 2;
      float f2 = (float)(c >> 24 & 255) / 255.0F;
      float f22 = (float)(c >> 16 & 255) / 255.0F;
      float f3 = (float)(c >> 8 & 255) / 255.0F;
      float f4 = (float)(c & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glLineWidth(lineWidth);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(f22, f3, f4, f2);
      GL11.glBegin(3);

      for(int i = segments - part; i <= segments; ++i) {
         double x = Math.sin((double)i * Math.PI / 180.0D) * r;
         double y = Math.cos((double)i * Math.PI / 180.0D) * r;
         GL11.glVertex2d((double)cx + x, (double)cy + y);
      }

      GL11.glEnd();
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
   }

  
   public static void glColor(int hex) {
      float alpha = (float)(hex >> 24 & 255) / 255.0F;
      float red = (float)(hex >> 16 & 255) / 255.0F;
      float green = (float)(hex >> 8 & 255) / 255.0F;
      float blue = (float)(hex & 255) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
   }

   public static Color rainbowEffect(int delay) {
      float hue = (float)(System.nanoTime() + (long)delay) / 2.0E10F % 1.0F;
      Color color = new Color((int)Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16));
      return new Color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
   }

   public static void drawFullscreenImage(ResourceLocation image) {
      ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3008);
      Minecraft.getMinecraft().getTextureManager().bindTexture(image);
      Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), (float)scaledResolution.getScaledWidth(), (float)scaledResolution.getScaledHeight());
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glEnable(3008);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawPlayerHead(String playerName, int x, int y, int width, int height) {
      Minecraft.getMinecraft();
      Iterator var6 = Minecraft.getMinecraft().theWorld.getLoadedEntityList().iterator();

      while(var6.hasNext()) {
         Object player = var6.next();
         if(player instanceof EntityPlayer) {
            EntityPlayer ply = (EntityPlayer)player;
            if(playerName.equalsIgnoreCase(ply.getName())) {
               GameProfile profile = new GameProfile(ply.getUniqueID(), ply.getName());
               NetworkPlayerInfo networkplayerinfo1 = new NetworkPlayerInfo(profile);
               new ScaledResolution(Minecraft.getMinecraft());
               GL11.glDisable(2929);
               GL11.glEnable(3042);
               GL11.glDepthMask(false);
               OpenGlHelper.glBlendFunc(770, 771, 1, 0);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
               Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float)width, (float)height);
               GL11.glDepthMask(true);
               GL11.glDisable(3042);
               GL11.glEnable(2929);
            }
         }
      }

   }

   public static double getAnimationState(double animation, double finalState, double speed) {
      float add = (float)(0.01D * speed);
      if(animation < finalState) {
         if(animation + (double)add < finalState) {
            animation += (double)add;
         } else {
            animation = finalState;
         }
      } else if(animation - (double)add > finalState) {
         animation -= (double)add;
      } else {
         animation = finalState;
      }

      return animation;
   }

   public static String getShaderCode(InputStreamReader file) {
      String shaderSource = "";

      try {
         String e;
         BufferedReader reader;
         for(reader = new BufferedReader(file); (e = reader.readLine()) != null; shaderSource = String.valueOf(shaderSource) + e + "\n") {
            ;
         }

         reader.close();
      } catch (IOException var4) {
         var4.printStackTrace();
         System.exit(-1);
      }

      return shaderSource.toString();
   }

   public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
      new ScaledResolution(Minecraft.getMinecraft());
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(image);
      Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float)width, (float)height);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }

    public static void drawImage(float x, float y, float width, float height, float r, float g, float b, ResourceLocation image) {
             Wrapper.getMinecraft().getTextureManager().bindTexture(image);
             float f = 1.0F / width;
             float f1 = 1.0F / height;
             GL11.glColor4f(r, g, b, 1.0F);
            Tessellator tessellator = Tessellator.getInstance();
             WorldRenderer worldrenderer = tessellator.getWorldRenderer();
             worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos(x, (y + height), 0.0D)
      .tex(0.0D, (height * f1))
   .endVertex();
            worldrenderer.pos((x + width), (y + height), 0.0D)
.tex((width * f), (height * f1))
   .endVertex();
     worldrenderer.pos((x + width), y, 0.0D)
       .tex((width * f), 0.0D)
       .endVertex();
   worldrenderer.pos(x, y, 0.0D)
     .tex(0.0D, 0.0D)
       .endVertex();
             tessellator.draw();
          }
   public static void drawImage(ResourceLocation image, int x, int y, float width, float height, float opacity) {
       glPushMatrix();
       glDisable(GL_DEPTH_TEST);
       glEnable(GL_BLEND);
       glDepthMask(false);
       OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
       glColor4f(1.0f, 1.0f, 1.0f, opacity / 255);
       mc.getTextureManager().bindTexture(image);
       glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
       glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
       Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, 
    		   (int)(width), (int)(height), width, height);
       glDepthMask(true);
       glDisable(GL_BLEND);
       glEnable(GL_DEPTH_TEST);
       glPopMatrix();
   }
   public static void drawOutlinedRect(int x, int y, int width, int height, int lineSize, Color lineColor, Color backgroundColor) {
      drawRect((float)x, (float)y, (float)width, (float)height, backgroundColor.getRGB());
      drawRect((float)x, (float)y, (float)width, (float)(y + lineSize), lineColor.getRGB());
      drawRect((float)x, (float)(height - lineSize), (float)width, (float)height, lineColor.getRGB());
      drawRect((float)x, (float)(y + lineSize), (float)(x + lineSize), (float)(height - lineSize), lineColor.getRGB());
      drawRect((float)(width - lineSize), (float)(y + lineSize), (float)width, (float)(height - lineSize), lineColor.getRGB());
   }

   public static void drawImage(ResourceLocation image, int x, int y, int width, int height, Color color) {
      new ScaledResolution(Minecraft.getMinecraft());
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getRed() / 255.0F, 1.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(image);
      Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float)width, (float)height);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }

   public static void doGlScissor(int x, int y, int width, int height) {
      Minecraft mc = Minecraft.getMinecraft();
      int scaleFactor = 1;
      int k = mc.gameSettings.guiScale;
      if(k == 0) {
         k = 1000;
      }

      while(scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240) {
         ++scaleFactor;
      }

      GL11.glScissor(x * scaleFactor, mc.displayHeight - (y + height) * scaleFactor, width * scaleFactor, height * scaleFactor);
   }

   public static void drawblock(double a, double a2, double a3, int a4, int a5, float a6) {
      float a7 = (float)(a4 >> 24 & 255) / 255.0F;
      float a8 = (float)(a4 >> 16 & 255) / 255.0F;
      float a9 = (float)(a4 >> 8 & 255) / 255.0F;
      float a10 = (float)(a4 & 255) / 255.0F;
      float a11 = (float)(a5 >> 24 & 255) / 255.0F;
      float a12 = (float)(a5 >> 16 & 255) / 255.0F;
      float a13 = (float)(a5 >> 8 & 255) / 255.0F;
      float a14 = (float)(a5 & 255) / 255.0F;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(a8, a9, a10, a7);
      drawOutlinedBoundingBox(new AxisAlignedBB(a, a2, a3, a + 1.0D, a2 + 1.0D, a3 + 1.0D));
      GL11.glLineWidth(a6);
      GL11.glColor4f(a12, a13, a14, a11);
      drawOutlinedBoundingBox(new AxisAlignedBB(a, a2, a3, a + 1.0D, a2 + 1.0D, a3 + 1.0D));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawRect(float left, float top, float right, float bottom, int color) {
      float f3;
      if(left < right) {
         f3 = left;
         left = right;
         right = f3;
      }

      if(top < bottom) {
         f3 = top;
         top = bottom;
         bottom = f3;
      }

      f3 = (float)(color >> 24 & 255) / 255.0F;
      float f = (float)(color >> 16 & 255) / 255.0F;
      float f1 = (float)(color >> 8 & 255) / 255.0F;
      float f2 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer WorldRenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(f, f1, f2, f3);
      WorldRenderer.begin(7, DefaultVertexFormats.POSITION);
      WorldRenderer.pos((double)left, (double)bottom, 0.0D).endVertex();
      WorldRenderer.pos((double)right, (double)bottom, 0.0D).endVertex();
      WorldRenderer.pos((double)right, (double)top, 0.0D).endVertex();
      WorldRenderer.pos((double)left, (double)top, 0.0D).endVertex();
      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void color(int color) {
      float f = (float)(color >> 24 & 255) / 255.0F;
      float f1 = (float)(color >> 16 & 255) / 255.0F;
      float f2 = (float)(color >> 8 & 255) / 255.0F;
      float f3 = (float)(color & 255) / 255.0F;
      GL11.glColor4f(f1, f2, f3, f);
   }

   public static int createShader(String shaderCode, int shaderType) throws Exception {
      byte shader = 0;

      int shader1;
      try {
         shader1 = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
         if(shader1 == 0) {
            return 0;
         }
      } catch (Exception var4) {
         ARBShaderObjects.glDeleteObjectARB(shader);
         throw var4;
      }

      ARBShaderObjects.glShaderSourceARB(shader1, shaderCode);
      ARBShaderObjects.glCompileShaderARB(shader1);
      if(ARBShaderObjects.glGetObjectParameteriARB(shader1, '\u8b81') == 0) {
         throw new RuntimeException("Error creating shader:");
      } else {
         return shader1;
      }
   }

   public static void drawBorderRect(double x, double y, double x1, double y1, int color, double lwidth) {
      drawHLine(x, y, x1, y, (float)lwidth, color);
      drawHLine(x1, y, x1, y1, (float)lwidth, color);
      drawHLine(x, y1, x1, y1, (float)lwidth, color);
      drawHLine(x, y1, x, y, (float)lwidth, color);
   }

   public static void drawHLine(double x, double y, double x1, double y1, float width, int color) {
      float var11 = (float)(color >> 24 & 255) / 255.0F;
      float var6 = (float)(color >> 16 & 255) / 255.0F;
      float var7 = (float)(color >> 8 & 255) / 255.0F;
      float var8 = (float)(color & 255) / 255.0F;
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(var6, var7, var8, var11);
      GL11.glPushMatrix();
      GL11.glLineWidth(width);
      GL11.glBegin(3);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x1, y1);
      GL11.glEnd();
      GL11.glLineWidth(1.0F);
      GL11.glPopMatrix();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void drawCircle(int x, int y, float radius, int color) {
      float alpha = (float)(color >> 24 & 255) / 255.0F;
      float red = (float)(color >> 16 & 255) / 255.0F;
      float green = (float)(color >> 8 & 255) / 255.0F;
      float blue = (float)(color & 255) / 255.0F;
      boolean blend = GL11.glIsEnabled(3042);
      boolean line = GL11.glIsEnabled(2848);
      boolean texture = GL11.glIsEnabled(3553);
      if(!blend) {
         GL11.glEnable(3042);
      }

      if(!line) {
         GL11.glEnable(2848);
      }

      if(texture) {
         GL11.glDisable(3553);
      }

      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glBegin(9);

      for(int i = 0; i <= 360; ++i) {
         GL11.glVertex2d((double)x + Math.sin((double)i * 3.141526D / 180.0D) * (double)radius, (double)y + Math.cos((double)i * 3.141526D / 180.0D) * (double)radius);
      }

      GL11.glEnd();
      if(texture) {
         GL11.glEnable(3553);
      }

      if(!line) {
         GL11.glDisable(2848);
      }

      if(!blend) {
         GL11.glDisable(3042);
      }

   }

   public static void renderOne(float width) {
      checkSetupFBO();
      GL11.glPushAttrib(1048575);
      GL11.glDisable(3008);
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(width);
      GL11.glEnable(2848);
      GL11.glEnable(2960);
      GL11.glClear(1024);
      GL11.glClearStencil(15);
      GL11.glStencilFunc(512, 1, 15);
      GL11.glStencilOp(7681, 7681, 7681);
      GL11.glPolygonMode(1032, 6913);
   }

   public static void renderTwo() {
      GL11.glStencilFunc(512, 0, 15);
      GL11.glStencilOp(7681, 7681, 7681);
      GL11.glPolygonMode(1032, 6914);
   }

   public static void renderThree() {
      GL11.glStencilFunc(514, 1, 15);
      GL11.glStencilOp(7680, 7680, 7680);
      GL11.glPolygonMode(1032, 6913);
   }

   public static void renderFour() {
      setColor(new Color(255, 255, 255));
      GL11.glDepthMask(false);
      GL11.glDisable(2929);
      GL11.glEnable(10754);
      GL11.glPolygonOffset(1.0F, -2000000.0F);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
   }

   public static void renderFive() {
      GL11.glPolygonOffset(1.0F, 2000000.0F);
      GL11.glDisable(10754);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(2960);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glEnable(3042);
      GL11.glEnable(2896);
      GL11.glEnable(3553);
      GL11.glEnable(3008);
      GL11.glPopAttrib();
   }

   public static void setColor(Color c) {
      GL11.glColor4d((double)((float)c.getRed() / 255.0F), (double)((float)c.getGreen() / 255.0F), (double)((float)c.getBlue() / 255.0F), (double)((float)c.getAlpha() / 255.0F));
   }

   public static void checkSetupFBO() {
      Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
      if(fbo != null && fbo.depthBuffer > -1) {
         setupFBO(fbo);
         fbo.depthBuffer = -1;
      }

   }

   public static void setupFBO(Framebuffer fbo) {
      EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
      int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
      EXTFramebufferObject.glBindRenderbufferEXT('\u8d41', stencil_depth_buffer_ID);
      EXTFramebufferObject.glRenderbufferStorageEXT('\u8d41', '\u84f9', Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
      EXTFramebufferObject.glFramebufferRenderbufferEXT('\u8d40', '\u8d20', '\u8d41', stencil_depth_buffer_ID);
      EXTFramebufferObject.glFramebufferRenderbufferEXT('\u8d40', '\u8d00', '\u8d41', stencil_depth_buffer_ID);
   }

   public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldRenderer = tessellator.getWorldRenderer();
      worldRenderer.begin(3, DefaultVertexFormats.POSITION);
      worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
      tessellator.draw();
      worldRenderer.begin(3, DefaultVertexFormats.POSITION);
      worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
      tessellator.draw();
      worldRenderer.begin(1, DefaultVertexFormats.POSITION);
      worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
      tessellator.draw();
   }

   public static void drawBox(AxisAlignedBB box) {
      if(box != null) {
         GL11.glBegin(7);
         GL11.glVertex3d(box.minX, box.minY, box.maxZ);
         GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
         GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
         GL11.glVertex3d(box.minX, box.minY, box.maxZ);
         GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(box.minX, box.minY, box.minZ);
         GL11.glVertex3d(box.minX, box.minY, box.maxZ);
         GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
         GL11.glVertex3d(box.minX, box.maxY, box.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(box.minX, box.minY, box.maxZ);
         GL11.glVertex3d(box.minX, box.minY, box.minZ);
         GL11.glVertex3d(box.minX, box.maxY, box.minZ);
         GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
         GL11.glVertex3d(box.maxX, box.minY, box.minZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(box.maxX, box.minY, box.minZ);
         GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(box.minX, box.minY, box.minZ);
         GL11.glVertex3d(box.maxX, box.minY, box.minZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
         GL11.glVertex3d(box.minX, box.maxY, box.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(box.maxX, box.minY, box.minZ);
         GL11.glVertex3d(box.minX, box.minY, box.minZ);
         GL11.glVertex3d(box.minX, box.maxY, box.minZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(box.minX, box.maxY, box.minZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
         GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
         GL11.glVertex3d(box.minX, box.maxY, box.minZ);
         GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(box.minX, box.minY, box.minZ);
         GL11.glVertex3d(box.maxX, box.minY, box.minZ);
         GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
         GL11.glVertex3d(box.minX, box.minY, box.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(box.maxX, box.minY, box.minZ);
         GL11.glVertex3d(box.minX, box.minY, box.minZ);
         GL11.glVertex3d(box.minX, box.minY, box.maxZ);
         GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
         GL11.glEnd();
      }
   }

   public static void drawCompleteBox(AxisAlignedBB axisalignedbb, float width, int insideColor, int borderColor) {
      GL11.glLineWidth(width);
      GL11.glEnable(2848);
      GL11.glEnable(2881);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
      glColor(insideColor);
      drawBox(axisalignedbb);
      glColor(borderColor);
      drawOutlinedBox(axisalignedbb);
      drawCrosses(axisalignedbb);
      GL11.glDisable(2848);
      GL11.glDisable(2881);
   }

   public static void drawCrosses(AxisAlignedBB axisalignedbb, float width, int color) {
      GL11.glLineWidth(width);
      GL11.glEnable(2848);
      GL11.glEnable(2881);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
      glColor(color);
      drawCrosses(axisalignedbb);
      GL11.glDisable(2848);
      GL11.glDisable(2881);
   }

   public static void drawOutlineBox(AxisAlignedBB axisalignedbb, float width, int color) {
      GL11.glLineWidth(width);
      GL11.glEnable(2848);
      GL11.glEnable(2881);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
      glColor(color);
      drawOutlinedBox(axisalignedbb);
      GL11.glDisable(2848);
      GL11.glDisable(2881);
   }

   public static void drawOutlinedBox(AxisAlignedBB box) {
      if(box != null) {
         GL11.glBegin(3);
         GL11.glVertex3d(box.minX, box.minY, box.minZ);
         GL11.glVertex3d(box.maxX, box.minY, box.minZ);
         GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
         GL11.glVertex3d(box.minX, box.minY, box.maxZ);
         GL11.glVertex3d(box.minX, box.minY, box.minZ);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(box.minX, box.maxY, box.minZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
         GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
         GL11.glVertex3d(box.minX, box.maxY, box.minZ);
         GL11.glEnd();
         GL11.glBegin(1);
         GL11.glVertex3d(box.minX, box.minY, box.minZ);
         GL11.glVertex3d(box.minX, box.maxY, box.minZ);
         GL11.glVertex3d(box.maxX, box.minY, box.minZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
         GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
         GL11.glVertex3d(box.minX, box.minY, box.maxZ);
         GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
         GL11.glEnd();
      }
   }

   public static void drawCrosses(AxisAlignedBB box) {
      if(box != null) {
         GL11.glBegin(1);
         GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
         GL11.glVertex3d(box.maxX, box.minY, box.minZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
         GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
         GL11.glVertex3d(box.minX, box.maxY, box.minZ);
         GL11.glVertex3d(box.maxX, box.minY, box.minZ);
         GL11.glVertex3d(box.minX, box.minY, box.maxZ);
         GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
         GL11.glVertex3d(box.minX, box.minY, box.maxZ);
         GL11.glVertex3d(box.minX, box.maxY, box.minZ);
         GL11.glVertex3d(box.minX, box.minY, box.minZ);
         GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
         GL11.glEnd();
      }
   }

   public static void drawBoundingBox(AxisAlignedBB aa) {
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldRenderer = tessellator.getWorldRenderer();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);
      worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
      tessellator.draw();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);
      worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
      worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
      tessellator.draw();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);
      worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
      worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
      tessellator.draw();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);
      worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
      tessellator.draw();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);
      worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
      worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
      tessellator.draw();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);
      worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
      worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
      worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
      tessellator.draw();
   }

   public static void drawOutlinedBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineWidth) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glLineWidth(lineWidth);
      GL11.glColor4f(red, green, blue, alpha);
      drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2896);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(red, green, blue, alpha);
      drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
      GL11.glLineWidth(lineWidth);
      GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
      drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2896);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawSolidBlockESP(double x, double y, double z, float red, float green, float blue, float alpha) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(red, green, blue, alpha);
      drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawOutlinedEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(red, green, blue, alpha);
      drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawSolidEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(red, green, blue, alpha);
      drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(red, green, blue, alpha);
      drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
      GL11.glLineWidth(lineWdith);
      GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
      drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(red, green, blue, alpha);
      drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   private static void glColor(Color color) {
      GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
   }

   public static void drawFilledBox(AxisAlignedBB mask) {
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldRenderer = tessellator.getWorldRenderer();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);
      worldRenderer.pos(mask.minX, mask.minY, mask.minZ).endVertex();
      worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
      worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
      worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
      worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
      worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
      worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
      worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
      tessellator.draw();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);
      worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
      worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
      worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
      worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
      worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
      worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
      worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
      worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
      tessellator.draw();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);
      worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
      worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
      worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
      worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
      worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
      worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
      worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
      worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
      tessellator.draw();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);
      worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
      worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
      worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
      worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
      worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
      worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
      worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
      worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
      tessellator.draw();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);
      worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
      worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
      worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
      worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
      worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
      worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
      worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
      worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
      tessellator.draw();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);
      worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
      worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
      worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
      worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
      worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
      worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
      worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
      worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
      tessellator.draw();
   }

   public static void circle(float x, float y, float radius, int fill) {
      arc(x, y, 0.0F, 360.0F, radius, fill);
   }

   public static void circle(float x, float y, float radius, Color fill) {
      arc(x, y, 0.0F, 360.0F, radius, fill);
   }

   public static void arc(float x, float y, float start, float end, float radius, int color) {
      arcEllipse(x, y, start, end, radius, radius, color);
   }

   public static void arc(float x, float y, float start, float end, float radius, Color color) {
      arcEllipse(x, y, start, end, radius, radius, color);
   }

   public static void arcEllipse(float x, float y, float start, float end, float w, float h, int color) {
      GlStateManager.color(0.0F, 0.0F, 0.0F);
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
      float temp = 0.0F;
      if(start > end) {
         temp = end;
         end = start;
         start = temp;
      }

      float var11 = (float)(color >> 24 & 255) / 255.0F;
      float var12 = (float)(color >> 16 & 255) / 255.0F;
      float var13 = (float)(color >> 8 & 255) / 255.0F;
      float var14 = (float)(color & 255) / 255.0F;
      Tessellator var15 = Tessellator.getInstance();
      WorldRenderer var16 = var15.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(var12, var13, var14, var11);
      float i;
      float ldx;
      float ldy;
      if(var11 > 0.5F) {
         GL11.glEnable(2848);
         GL11.glLineWidth(2.0F);
         GL11.glBegin(3);

         for(i = end; i >= start; i -= 4.0F) {
            ldx = (float)Math.cos((double)i * Math.PI / 180.0D) * w * 1.001F;
            ldy = (float)Math.sin((double)i * Math.PI / 180.0D) * h * 1.001F;
            GL11.glVertex2f(x + ldx, y + ldy);
         }

         GL11.glEnd();
         GL11.glDisable(2848);
      }

      GL11.glBegin(6);

      for(i = end; i >= start; i -= 4.0F) {
         ldx = (float)Math.cos((double)i * Math.PI / 180.0D) * w;
         ldy = (float)Math.sin((double)i * Math.PI / 180.0D) * h;
         GL11.glVertex2f(x + ldx, y + ldy);
      }

      GL11.glEnd();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void arcEllipse(float x, float y, float start, float end, float w, float h, Color color) {
      GlStateManager.color(0.0F, 0.0F, 0.0F);
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
      float temp = 0.0F;
      if(start > end) {
         temp = end;
         end = start;
         start = temp;
      }

      Tessellator var9 = Tessellator.getInstance();
      WorldRenderer var10 = var9.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
      float i;
      float ldx;
      float ldy;
      if((float)color.getAlpha() > 0.5F) {
         GL11.glEnable(2848);
         GL11.glLineWidth(2.0F);
         GL11.glBegin(3);

         for(i = end; i >= start; i -= 4.0F) {
            ldx = (float)Math.cos((double)i * Math.PI / 180.0D) * w * 1.001F;
            ldy = (float)Math.sin((double)i * Math.PI / 180.0D) * h * 1.001F;
            GL11.glVertex2f(x + ldx, y + ldy);
         }

         GL11.glEnd();
         GL11.glDisable(2848);
      }

      GL11.glBegin(6);

      for(i = end; i >= start; i -= 4.0F) {
         ldx = (float)Math.cos((double)i * Math.PI / 180.0D) * w;
         ldy = (float)Math.sin((double)i * Math.PI / 180.0D) * h;
         GL11.glVertex2f(x + ldx, y + ldy);
      }

      GL11.glEnd();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
      float f = (float)(col1 >> 24 & 255) / 255.0F;
      float f1 = (float)(col1 >> 16 & 255) / 255.0F;
      float f2 = (float)(col1 >> 8 & 255) / 255.0F;
      float f3 = (float)(col1 & 255) / 255.0F;
      float f4 = (float)(col2 >> 24 & 255) / 255.0F;
      float f5 = (float)(col2 >> 16 & 255) / 255.0F;
      float f6 = (float)(col2 >> 8 & 255) / 255.0F;
      float f7 = (float)(col2 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushMatrix();
      GL11.glBegin(7);
      GL11.glColor4f(f1, f2, f3, f);
      GL11.glVertex2d(left, top);
      GL11.glVertex2d(left, bottom);
      GL11.glColor4f(f5, f6, f7, f4);
      GL11.glVertex2d(right, bottom);
      GL11.glVertex2d(right, top);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
   }

   public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
      rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(x + width, y, x1 - width, y + width, borderColor);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(x, y, x + width, y1, borderColor);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(x1 - width, y, x1, y1, borderColor);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void rectangle(double left, double top, double right, double bottom, int color) {
      double var11;
      if(left < right) {
         var11 = left;
         left = right;
         right = var11;
      }

      if(top < bottom) {
         var11 = top;
         top = bottom;
         bottom = var11;
      }

      float var111 = (float)(color >> 24 & 255) / 255.0F;
      float var6 = (float)(color >> 16 & 255) / 255.0F;
      float var7 = (float)(color >> 8 & 255) / 255.0F;
      float var8 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldRenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(var6, var7, var8, var111);
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);
      worldRenderer.pos(left, bottom, 0.0D).endVertex();
      worldRenderer.pos(right, bottom, 0.0D).endVertex();
      worldRenderer.pos(right, top, 0.0D).endVertex();
      worldRenderer.pos(left, top, 0.0D).endVertex();
      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }


   public static void drawRect(double d, double e, double g, double h, int color) {
      int f3;
      if(d < g) {
         f3 = (int)d;
         d = g;
         g = (double)f3;
      }

      if(e < h) {
         f3 = (int)e;
         e = h;
         h = (double)f3;
      }

      float f31 = (float)(color >> 24 & 255) / 255.0F;
      float f = (float)(color >> 16 & 255) / 255.0F;
      float f1 = (float)(color >> 8 & 255) / 255.0F;
      float f2 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(f, f1, f2, f31);
      worldrenderer.begin(7, DefaultVertexFormats.POSITION);
      worldrenderer.pos(d, h, 0.0D).endVertex();
      worldrenderer.pos(g, h, 0.0D).endVertex();
      worldrenderer.pos(g, e, 0.0D).endVertex();
      worldrenderer.pos(d, e, 0.0D).endVertex();
      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static Color blend(Color color1, Color color2, double ratio) {
      float r = (float)ratio;
      float ir = 1.0F - r;
      float[] rgb1 = new float[3];
      float[] rgb2 = new float[3];
      color1.getColorComponents(rgb1);
      color2.getColorComponents(rgb2);
      Color color3 = new Color(rgb1[0] * r + rgb2[0] * ir, rgb1[1] * r + rgb2[1] * ir, rgb1[2] * r + rgb2[2] * ir);
      return color3;
   }

   public static void drawEntityOnScreen(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_) {
      GlStateManager.enableColorMaterial();
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)p_147046_0_, (float)p_147046_1_, 40.0F);
      GlStateManager.scale((float)(-p_147046_2_), (float)p_147046_2_, (float)p_147046_2_);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      float var6 = p_147046_5_.renderYawOffset;
      float var7 = p_147046_5_.rotationYaw;
      float var8 = p_147046_5_.rotationPitch;
      float var9 = p_147046_5_.prevRotationYawHead;
      float var10 = p_147046_5_.rotationYawHead;
      GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
      p_147046_5_.renderYawOffset = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * -14.0F;
      p_147046_5_.rotationYaw = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * -14.0F;
      p_147046_5_.rotationPitch = -((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 15.0F;
      p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
      p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
      GlStateManager.translate(0.0F, 0.0F, 0.0F);
      RenderManager var11 = Minecraft.getMinecraft().getRenderManager();
      var11.setPlayerViewY(180.0F);
      var11.setRenderShadow(false);
      var11.renderEntityWithPosYaw(p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
      var11.setRenderShadow(true);
      p_147046_5_.renderYawOffset = var6;
      p_147046_5_.rotationYaw = var7;
      p_147046_5_.rotationPitch = var8;
      p_147046_5_.prevRotationYawHead = var9;
      p_147046_5_.rotationYawHead = var10;
      GlStateManager.popMatrix();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
   }

   public static void drawRoundRect(double d, double e, double g, double h, int color) {
      drawRect(d + 1.0D, e, g - 1.0D, h, color);
      drawRect(d, e + 1.0D, d + 1.0D, h - 1.0D, color);
      drawRect(d + 1.0D, e + 1.0D, d + 0.5D, e + 0.5D, color);
      drawRect(d + 1.0D, e + 1.0D, d + 0.5D, e + 0.5D, color);
      drawRect(g - 1.0D, e + 1.0D, g - 0.5D, e + 0.5D, color);
      drawRect(g - 1.0D, e + 1.0D, g, h - 1.0D, color);
      drawRect(d + 1.0D, h - 1.0D, d + 0.5D, h - 0.5D, color);
      drawRect(g - 1.0D, h - 1.0D, g - 0.5D, h - 0.5D, color);
   }

   public static void pre() {
      GL11.glDisable(2929);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
   }

   public static void post() {
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glColor3d(1.0D, 1.0D, 1.0D);
   }

   public static void drawLine3D(float x, float y, float z, float x1, float y1, float z1, int color) {
       pre3D();
       GL11.glLoadIdentity();
       Minecraft.getMinecraft().entityRenderer.orientCamera(Minecraft.getMinecraft().timer.renderPartialTicks);
       float var11 = (color >> 24 & 0xFF) / 255.0F;
       float var6 = (color >> 16 & 0xFF) / 255.0F;
       float var7 = (color >> 8 & 0xFF) / 255.0F;
       float var8 = (color & 0xFF) / 255.0F;
       GL11.glColor4f(var6, var7, var8, var11);
       GL11.glLineWidth(0.5f);
       GL11.glBegin(GL11.GL_LINE_STRIP);
       GL11.glVertex3d(x, y, z);
       GL11.glVertex3d(x1, y1, z1);
       GL11.glEnd();
       post3D();
   }
   public static void pre3D() {
       GL11.glPushMatrix();
       GL11.glEnable(GL11.GL_BLEND);
       GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
       GL11.glShadeModel(GL11.GL_SMOOTH);
       GL11.glDisable(GL11.GL_TEXTURE_2D);
       GL11.glEnable(GL11.GL_LINE_SMOOTH);
       GL11.glDisable(GL11.GL_DEPTH_TEST);
       GL11.glDisable(GL11.GL_LIGHTING);
       GL11.glDepthMask(false);
       GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
   }

   public static void post3D() {
       GL11.glDepthMask(true);
       GL11.glEnable(GL11.GL_DEPTH_TEST);
       GL11.glDisable(GL11.GL_LINE_SMOOTH);
       GL11.glEnable(GL11.GL_TEXTURE_2D);
       GL11.glDisable(GL11.GL_BLEND);
       GL11.glPopMatrix();
       GL11.glColor4f(1, 1, 1, 1);
   }

   public static void prepareScissorBox(float x, float y, float width, float height) {
       ScaledResolution scaledResolution = new ScaledResolution(mc);
       prepareScissorBox(x, y, width, height, scaledResolution);
   }

   public static void prepareScissorBox(float x, float y, float width, float height, ScaledResolution scaledResolution) {
       int factor = scaledResolution.getScaleFactor();
       GL11.glScissor((int)(x * factor), (int)((scaledResolution.getScaledHeight() - height) * factor), (int)((width - x) * factor), (int)((height - y) * factor));
   }
   

   public static Color brighter(Color color, float factor) {
       int r = color.getRed();
       int g = color.getGreen();
       int b = color.getBlue();
       int alpha = color.getAlpha();
       int i = (int)(1.0/(1.0-factor));
       if (r == 0 && g == 0 && b == 0) return new Color(i, i, i, alpha);
       if (r > 0 && r < i) r = i;
       if (g > 0 && g < i) g = i;
       if (b > 0 && b < i) b = i;
       return new Color(Math.min((int)(r/factor), 255), Math.min((int)(g/factor), 255), Math.min((int)(b/factor), 255), alpha);
   }
   public static boolean isHovered(float x, float y, float w, float h, int mouseX, int mouseY) {
       return (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h);
   }

   public static boolean isHoveredFull(float x, float y, float w, float h, int mouseX, int mouseY) {
       return (mouseX >= x && mouseX <= w && mouseY >= y && mouseY <= h);
   }
}
 class R2DUtils {
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


		   public static void drawRect(double x2, double y2, double x1, double y1, int color) {
		      enableGL2D();
		      glColor(color);
		      drawRect(x2, y2, x1, y1);
		      disableGL2D();
		   }

		   private static void drawRect(double x2, double y2, double x1, double y1) {
		      GL11.glBegin(7);
		      GL11.glVertex2d(x2, y1);
		      GL11.glVertex2d(x1, y1);
		      GL11.glVertex2d(x1, y2);
		      GL11.glVertex2d(x2, y2);
		      GL11.glEnd();
		   }

		   public static void glColor(int hex) {
		      float alpha = (float)(hex >> 24 & 255) / 255.0F;
		      float red = (float)(hex >> 16 & 255) / 255.0F;
		      float green = (float)(hex >> 8 & 255) / 255.0F;
		      float blue = (float)(hex & 255) / 255.0F;
		      GL11.glColor4f(red, green, blue, alpha);
		   }

		   public static void drawRect(float x, float y, float x1, float y1, int color) {
		      enableGL2D();
		      glColor(color);
		      drawRect(x, y, x1, y1);
		      disableGL2D();
		   }

		   public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int borderColor) {
		      enableGL2D();
		      glColor(borderColor);
		      drawRect(x + width, y, x1 - width, y + width);
		      drawRect(x, y, x + width, y1);
		      drawRect(x1 - width, y, x1, y1);
		      drawRect(x + width, y1 - width, x1 - width, y1);
		      disableGL2D();
		   }

		   public static void drawBorderedRect(float x, float y, float x1, float y1, int insideC, int borderC) {
		      enableGL2D();
		      GL11.glScalef(0.5F, 0.5F, 0.5F);
		      drawVLine(x *= 2.0F, y *= 2.0F, y1 *= 2.0F, borderC);
		      drawVLine((x1 *= 2.0F) - 1.0F, y, y1, borderC);
		      drawHLine(x, x1 - 1.0F, y, borderC);
		      drawHLine(x, x1 - 2.0F, y1 - 1.0F, borderC);
		      drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
		      GL11.glScalef(2.0F, 2.0F, 2.0F);
		      disableGL2D();
		   }

		   public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
		      enableGL2D();
		      GL11.glShadeModel(7425);
		      GL11.glBegin(7);
		      glColor(topColor);
		      GL11.glVertex2f(x, y1);
		      GL11.glVertex2f(x1, y1);
		      glColor(bottomColor);
		      GL11.glVertex2f(x1, y);
		      GL11.glVertex2f(x, y);
		      GL11.glEnd();
		      GL11.glShadeModel(7424);
		      disableGL2D();
		   }

		   public static void drawHLine(float x, float y, float x1, int y1) {
		      if(y < x) {
		         float var5 = x;
		         x = y;
		         y = var5;
		      }

		      drawRect(x, x1, y + 1.0F, x1 + 1.0F, y1);
		   }

		   public static void drawVLine(float x, float y, float x1, int y1) {
		      if(x1 < y) {
		         float var5 = y;
		         y = x1;
		         x1 = var5;
		      }

		      drawRect(x, y + 1.0F, x + 1.0F, x1, y1);
		   }

		   public static void drawHLine(float x, float y, float x1, int y1, int y2) {
		      if(y < x) {
		         float var5 = x;
		         x = y;
		         y = var5;
		      }

		      drawGradientRect(x, x1, y + 1.0F, x1 + 1.0F, y1, y2);
		   }

		   public static void drawRect(float x, float y, float x1, float y1) {
		      GL11.glBegin(7);
		      GL11.glVertex2f(x, y1);
		      GL11.glVertex2f(x1, y1);
		      GL11.glVertex2f(x1, y);
		      GL11.glVertex2f(x, y);
		      GL11.glEnd();
		   }
}
