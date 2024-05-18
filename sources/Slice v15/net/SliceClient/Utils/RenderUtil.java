package net.SliceClient.Utils;

import java.awt.Color;
import java.awt.Point;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;

public class RenderUtil
{
  public RenderUtil() {}
  
  public static void scissorBox(int x, int y, int xend, int yend)
  {
    int width = xend - x;
    int height = yend - y;
    Minecraft.getMinecraft();Minecraft.getMinecraft();ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth, Minecraft.displayHeight);
    int factor = sr.getScaleFactor();
    Minecraft.getMinecraft();int bottomY = net.minecraft.client.gui.GuiScreen.height - yend;
    GL11.glScissor(x * factor, bottomY * factor, width * factor, height * factor);
  }
  
  public static void setupLineSmooth()
  {
    GL11.glEnable(3042);
    GL11.glDisable(2896);
    GL11.glDisable(2929);
    GL11.glEnable(2848);
    GL11.glDisable(3553);
    GL11.glHint(3154, 4354);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(32925);
    GL11.glEnable(32926);
    GL11.glShadeModel(7425);
  }
  
  public static void drawLine(double startX, double startY, double startZ, double endX, double endY, double endZ, float thickness)
  {
    GL11.glPushMatrix();
    setupLineSmooth();
    GL11.glLineWidth(thickness);
    GL11.glBegin(1);
    GL11.glVertex3d(startX, startY, startZ);
    GL11.glVertex3d(endX, endY, endZ);
    GL11.glEnd();
    GL11.glDisable(3042);
    GL11.glEnable(3553);
    GL11.glDisable(2848);
    GL11.glEnable(2896);
    GL11.glEnable(2929);
    GL11.glDisable(32925);
    GL11.glDisable(32926);
    GL11.glPopMatrix();
  }
  
  public static void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
  {
    float var7 = 0.00390625F;
    float var8 = 0.00390625F;
    Tessellator var9 = Tessellator.getInstance();
    WorldRenderer var10 = var9.getWorldRenderer();
    var10.startDrawingQuads();
    var10.addVertexWithUV(par1 + 0, par2 + par6, 0.0D, (par3 + 0) * var7, (par4 + par6) * var8);
    var10.addVertexWithUV(par1 + par5, par2 + par6, 0.0D, (par3 + par5) * var7, (par4 + par6) * var8);
    var10.addVertexWithUV(par1 + par5, par2 + 0, 0.0D, (par3 + par5) * var7, (par4 + 0) * var8);
    var10.addVertexWithUV(par1 + 0, par2 + 0, 0.0D, (par3 + 0) * var7, (par4 + 0) * var8);
    var9.draw();
  }
  
  public static void drawTexturedModalRect(int textureId, int posX, int posY, int width, int height)
  {
    double halfHeight = height / 2;
    double halfWidth = width / 2;
    
    GL11.glDisable(2884);
    GL11.glBindTexture(3553, textureId);
    GL11.glPushMatrix();
    GL11.glTranslated(posX + halfWidth, posY + halfHeight, 0.0D);
    GL11.glScalef(width, height, 0.0F);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glBegin(4);
    GL11.glNormal3f(0.0F, 0.0F, 1.0F);
    GL11.glTexCoord2f(1.0F, 1.0F);
    GL11.glVertex2d(1.0D, 1.0D);
    GL11.glTexCoord2f(0.0F, 1.0F);
    GL11.glVertex2d(-1.0D, 1.0D);
    GL11.glTexCoord2f(0.0F, 0.0F);
    GL11.glVertex2d(-1.0D, -1.0D);
    GL11.glTexCoord2f(0.0F, 0.0F);
    GL11.glVertex2d(-1.0D, -1.0D);
    GL11.glTexCoord2f(1.0F, 0.0F);
    GL11.glVertex2d(1.0D, -1.0D);
    GL11.glTexCoord2f(1.0F, 1.0F);
    GL11.glVertex2d(1.0D, 1.0D);
    GL11.glEnd();
    
    GL11.glDisable(3042);
    GL11.glBindTexture(3553, 0);
    GL11.glPopMatrix();
  }
  
  public static int interpolateColor(int rgba1, int rgba2, float percent)
  {
    int r1 = rgba1 & 0xFF;int g1 = rgba1 >> 8 & 0xFF;int b1 = rgba1 >> 16 & 0xFF;int a1 = rgba1 >> 24 & 0xFF;
    int r2 = rgba2 & 0xFF;int g2 = rgba2 >> 8 & 0xFF;int b2 = rgba2 >> 16 & 0xFF;int a2 = rgba2 >> 24 & 0xFF;
    
    int r = (int)(r1 < r2 ? r1 + (r2 - r1) * percent : r2 + (r1 - r2) * percent);
    int g = (int)(g1 < g2 ? g1 + (g2 - g1) * percent : g2 + (g1 - g2) * percent);
    int b = (int)(b1 < b2 ? b1 + (b2 - b1) * percent : b2 + (b1 - b2) * percent);
    int a = (int)(a1 < a2 ? a1 + (a2 - a1) * percent : a2 + (a1 - a2) * percent);
    
    return r | g << 8 | b << 16 | a << 24;
  }
  
  public static void setColor(Color c)
  {
    GL11.glColor4f(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, c.getAlpha() / 255.0F);
  }
  
  public static Color toColor(int rgba)
  {
    int r = rgba & 0xFF;int g = rgba >> 8 & 0xFF;int b = rgba >> 16 & 0xFF;int a = rgba >> 24 & 0xFF;
    return new Color(r, g, b, a);
  }
  
  public static int toRGBA(Color c)
  {
    return c.getRed() | c.getGreen() << 8 | c.getBlue() << 16 | c.getAlpha() << 24;
  }
  
  public static void setColor(int rgba)
  {
    int r = rgba & 0xFF;int g = rgba >> 8 & 0xFF;int b = rgba >> 16 & 0xFF;int a = rgba >> 24 & 0xFF;
    GL11.glColor4b((byte)r, (byte)g, (byte)b, (byte)a);
  }
  
  public static class R3DUtils
  {
    public R3DUtils() {}
    
    public static void startDrawing()
    {
      GL11.glEnable(3042);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      mcentityRenderer.setupCameraTransform(Timer.renderPartialTicks, 0);
    }
    
    public static void stopDrawing()
    {
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
    }
    
    public static Point calculateMouseLocation()
    {
      Minecraft minecraft = Minecraft.getMinecraft();
      int scale = gameSettingsguiScale;
      if (scale == 0) {
        scale = 1000;
      }
      int scaleFactor = 0;
      while ((scaleFactor < scale) && (Minecraft.displayWidth / (scaleFactor + 1) >= 320) && (Minecraft.displayHeight / (scaleFactor + 1) >= 240)) {
        scaleFactor++;
      }
      return new Point(org.lwjgl.input.Mouse.getX() / scaleFactor, Minecraft.displayHeight / scaleFactor - org.lwjgl.input.Mouse.getY() / scaleFactor - 1);
    }
    
    public static void drawOutlinedBox(AxisAlignedBB box)
    {
      if (box == null) {
        return;
      }
      mcentityRenderer.setupCameraTransform(Timer.renderPartialTicks, 0);
      GL11.glBegin(3);
      GL11.glVertex3d(minX, minY, minZ);
      GL11.glVertex3d(maxX, minY, minZ);
      GL11.glVertex3d(maxX, minY, maxZ);
      GL11.glVertex3d(minX, minY, maxZ);
      GL11.glVertex3d(minX, minY, minZ);
      GL11.glEnd();
      GL11.glBegin(3);
      GL11.glVertex3d(minX, maxY, minZ);
      GL11.glVertex3d(maxX, maxY, minZ);
      GL11.glVertex3d(maxX, maxY, maxZ);
      GL11.glVertex3d(minX, maxY, maxZ);
      GL11.glVertex3d(minX, maxY, minZ);
      GL11.glEnd();
      GL11.glBegin(1);
      GL11.glVertex3d(minX, minY, minZ);
      GL11.glVertex3d(minX, maxY, minZ);
      GL11.glVertex3d(maxX, minY, minZ);
      GL11.glVertex3d(maxX, maxY, minZ);
      GL11.glVertex3d(maxX, minY, maxZ);
      GL11.glVertex3d(maxX, maxY, maxZ);
      GL11.glVertex3d(minX, minY, maxZ);
      GL11.glVertex3d(minX, maxY, maxZ);
      GL11.glEnd();
    }
    
    public static void drawBoundingBox(AxisAlignedBB aabb)
    {
      WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
      Tessellator tessellator = Tessellator.getInstance();
      mcentityRenderer.setupCameraTransform(Timer.renderPartialTicks, 0);
      worldRenderer.startDrawingQuads();
      worldRenderer.addVertex(minX, minY, minZ);
      worldRenderer.addVertex(minX, maxY, minZ);
      worldRenderer.addVertex(maxX, minY, minZ);
      worldRenderer.addVertex(maxX, maxY, minZ);
      worldRenderer.addVertex(maxX, minY, maxZ);
      worldRenderer.addVertex(maxX, maxY, maxZ);
      worldRenderer.addVertex(minX, minY, maxZ);
      worldRenderer.addVertex(minX, maxY, maxZ);
      tessellator.draw();
      worldRenderer.startDrawingQuads();
      worldRenderer.addVertex(maxX, maxY, minZ);
      worldRenderer.addVertex(maxX, minY, minZ);
      worldRenderer.addVertex(minX, maxY, minZ);
      worldRenderer.addVertex(minX, minY, minZ);
      worldRenderer.addVertex(minX, maxY, maxZ);
      worldRenderer.addVertex(minX, minY, maxZ);
      worldRenderer.addVertex(maxX, maxY, maxZ);
      worldRenderer.addVertex(maxX, minY, maxZ);
      tessellator.draw();
      worldRenderer.startDrawingQuads();
      worldRenderer.addVertex(minX, maxY, minZ);
      worldRenderer.addVertex(maxX, maxY, minZ);
      worldRenderer.addVertex(maxX, maxY, maxZ);
      worldRenderer.addVertex(minX, maxY, maxZ);
      worldRenderer.addVertex(minX, maxY, minZ);
      worldRenderer.addVertex(minX, maxY, maxZ);
      worldRenderer.addVertex(maxX, maxY, maxZ);
      worldRenderer.addVertex(maxX, maxY, minZ);
      tessellator.draw();
      worldRenderer.startDrawingQuads();
      worldRenderer.addVertex(minX, minY, minZ);
      worldRenderer.addVertex(maxX, minY, minZ);
      worldRenderer.addVertex(maxX, minY, maxZ);
      worldRenderer.addVertex(minX, minY, maxZ);
      worldRenderer.addVertex(minX, minY, minZ);
      worldRenderer.addVertex(minX, minY, maxZ);
      worldRenderer.addVertex(maxX, minY, maxZ);
      worldRenderer.addVertex(maxX, minY, minZ);
      tessellator.draw();
      worldRenderer.startDrawingQuads();
      worldRenderer.addVertex(minX, minY, minZ);
      worldRenderer.addVertex(minX, maxY, minZ);
      worldRenderer.addVertex(minX, minY, maxZ);
      worldRenderer.addVertex(minX, maxY, maxZ);
      worldRenderer.addVertex(maxX, minY, maxZ);
      worldRenderer.addVertex(maxX, maxY, maxZ);
      worldRenderer.addVertex(maxX, minY, minZ);
      worldRenderer.addVertex(maxX, maxY, minZ);
      tessellator.draw();
      worldRenderer.startDrawingQuads();
      worldRenderer.addVertex(minX, maxY, maxZ);
      worldRenderer.addVertex(minX, minY, maxZ);
      worldRenderer.addVertex(minX, maxY, minZ);
      worldRenderer.addVertex(minX, minY, minZ);
      worldRenderer.addVertex(maxX, maxY, minZ);
      worldRenderer.addVertex(maxX, minY, minZ);
      worldRenderer.addVertex(maxX, maxY, maxZ);
      worldRenderer.addVertex(maxX, minY, maxZ);
      tessellator.draw();
    }
  }
  



  public static void drawVerticalLine(int x, int y, int height, int color)
  {
    drawRect(x, y, x + 1, height, color);
  }
  
  public static void drawHorizontalLine(int x, int y, int width, int color)
  {
    drawRect(x, y, width, y + 1, color);
  }
  
  public static void drawBorderedRect(int x, int y, int x1, int y1, int bord, int color)
  {
    drawRect(x + 1, y + 1, x1, y1, color);
    drawVerticalLine(x, y, y1, bord);
    drawVerticalLine(x1, y, y1, bord);
    drawHorizontalLine(x + 1, y, x1, bord);
    drawHorizontalLine(x, y1, x1 + 1, bord);
  }
  
  public static void drawRect(int left, int top, int right, int bottom, int color)
  {
    if (left < right)
    {
      int var5 = left;
      left = right;
      right = var5;
    }
    if (top < bottom)
    {
      int var5 = top;
      top = bottom;
      bottom = var5;
    }
    float var11 = (color >> 24 & 0xFF) / 255.0F;
    float var6 = (color >> 16 & 0xFF) / 255.0F;
    float var7 = (color >> 8 & 0xFF) / 255.0F;
    float var8 = (color & 0xFF) / 255.0F;
    Tessellator var9 = Tessellator.getInstance();
    WorldRenderer var10 = var9.getWorldRenderer();
    GlStateManager.enableBlend();
    GlStateManager.func_179090_x();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    GlStateManager.color(var6, var7, var8, var11);
    var10.startDrawingQuads();
    var10.addVertex(left, bottom, 0.0D);
    var10.addVertex(right, bottom, 0.0D);
    var10.addVertex(right, top, 0.0D);
    var10.addVertex(left, top, 0.0D);
    var9.draw();
    GlStateManager.func_179098_w();
    GlStateManager.disableBlend();
  }
}
