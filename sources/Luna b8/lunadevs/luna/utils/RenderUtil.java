package lunadevs.luna.utils;

import java.awt.Color;
import java.io.PrintStream;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class RenderUtil
{
  protected static int glTextureId = -1;
  public static void setColor(Color c)
  {
    GL11.glColor4d(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, c.getAlpha() / 255.0F);
  }
  
  public static void drawTri(double x1, double y1, double x2, double y2, double x3, double y3, double width, Color c)
  {
    GlStateManager.pushMatrix();
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glBlendFunc(770, 771);
    setColor(c);
    GL11.glLineWidth((float)width);
    GL11.glBegin(3);
    GL11.glVertex2d(x1, y1);
    GL11.glVertex2d(x2, y2);
    GL11.glVertex2d(x3, y3);
    GL11.glEnd();
    GlStateManager.popMatrix();
  }
  
  private static int getGlTextureId()
  {
    if (glTextureId == -1) {
      glTextureId = TextureUtil.glGenTextures();
    }
    return glTextureId;
  }
  
  public static void drawCircle(int x, int y, double radius, Color c)
  {
    setColor(c);
    GlStateManager.alphaFunc(516, 0.001F);
    GlStateManager.enableAlpha();
    GlStateManager.enableBlend();
    GlStateManager.disableCull();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    Tessellator tess = Tessellator.getInstance();
    WorldRenderer render = tess.getWorldRenderer();
    for (double i = 0.0D; i < 360.0D; i += 1.0D)
    {
      double cs = i * 3.141592653589793D / 180.0D;
      double ps = (i - 1.0D) * 3.141592653589793D / 180.0D;
      double[] outer = { Math.cos(cs) * radius, -Math.sin(cs) * radius, Math.cos(ps) * radius, -Math.sin(ps) * radius };
      render.startDrawing(6);
      render.addVertex(x + outer[2], y + outer[3], 0.0D);
      render.addVertex(x + outer[0], y + outer[1], 0.0D);
      render.addVertex(x, y, 0.0D);
      tess.draw();
    }
    GlStateManager.disableBlend();
    GlStateManager.disableAlpha();
    GlStateManager.enableTexture();
    GlStateManager.alphaFunc(516, 0.1F);
  }
  
  public static void drawBorderedRect(float x, float y, float x1, float y1, float size, Color mainColor, Color borderColor)
  {
    drawRect(x, y, x1, y1, mainColor);
    drawRect(x - size, y - size, x1, y, borderColor);
    drawRect(x, y, x - size, y1, borderColor);
    drawRect(x1, y1, x1 + size, y - size, borderColor);
    drawRect(x - size, y1 + size, x1 + size, y1, borderColor);
  }
  
  public static void drawRect(float left, float top, float right, float bottom, Color color)
  {
    if (left < right)
    {
      float var5 = left;
      left = right;
      right = var5;
    }
    if (top < bottom)
    {
      float var5 = top;
      top = bottom;
      bottom = var5;
    }
    Tessellator var6 = Tessellator.getInstance();
    WorldRenderer var7 = var6.getWorldRenderer();
    GlStateManager.enableBlend();
    GlStateManager.func_179090_x();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    setColor(color);
    var7.startDrawingQuads();
    var7.addVertex(left, bottom, 0.0D);
    var7.addVertex(right, bottom, 0.0D);
    var7.addVertex(right, top, 0.0D);
    var7.addVertex(left, top, 0.0D);
    var6.draw();
    GlStateManager.func_179098_w();
    GlStateManager.disableBlend();
    setColor(Color.WHITE);
  }
}