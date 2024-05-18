package net.SliceClient.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class RenderUtils
{
  public RenderUtils() {}
  
  public static void drawOutlinedBoundingBox(AxisAlignedBB aa)
  {
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldRenderer = tessellator.getWorldRenderer();
    worldRenderer.startDrawing(3);
    worldRenderer.addVertex(minX, minY, minZ);
    worldRenderer.addVertex(maxX, minY, minZ);
    worldRenderer.addVertex(maxX, minY, maxZ);
    worldRenderer.addVertex(minX, minY, maxZ);
    worldRenderer.addVertex(minX, minY, minZ);
    tessellator.draw();
    worldRenderer.startDrawing(3);
    worldRenderer.addVertex(minX, maxY, minZ);
    worldRenderer.addVertex(maxX, maxY, minZ);
    worldRenderer.addVertex(maxX, maxY, maxZ);
    worldRenderer.addVertex(minX, maxY, maxZ);
    worldRenderer.addVertex(minX, maxY, minZ);
    tessellator.draw();
    worldRenderer.startDrawing(1);
    worldRenderer.addVertex(minX, minY, minZ);
    worldRenderer.addVertex(minX, maxY, minZ);
    worldRenderer.addVertex(maxX, minY, minZ);
    worldRenderer.addVertex(maxX, maxY, minZ);
    worldRenderer.addVertex(maxX, minY, maxZ);
    worldRenderer.addVertex(maxX, maxY, maxZ);
    worldRenderer.addVertex(minX, minY, maxZ);
    worldRenderer.addVertex(minX, maxY, maxZ);
    tessellator.draw();
  }
  
  public static void drawBoundingBox(AxisAlignedBB aa) {
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldRenderer = tessellator.getWorldRenderer();
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
  
  public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha, float lineWdith) {
    GL11.glPushMatrix();
    GL11.glEnable(3042);
    GL11.glEnable(2848);
    GL11.glDisable(2929);
    
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(3042);
    GL11.glLineWidth(lineWdith);
    GL11.glColor4f(red, green, blue, alpha);
    GL11.glBegin(2);
    Minecraft.getMinecraft();GL11.glVertex3d(0.0D, 0.0D + Minecraft.thePlayer.getEyeHeight(), 0.0D);
    GL11.glVertex3d(x, y, z);
    GL11.glEnd();
    GL11.glDisable(3042);
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDisable(2848);
    GL11.glDisable(3042);
    
    GL11.glPopMatrix();
  }
  
  public static void drawCircle(int x, int y, double r, int c) {
    float f = (c >> 24 & 0xFF) / 255.0F;
    float f1 = (c >> 16 & 0xFF) / 255.0F;
    float f2 = (c >> 8 & 0xFF) / 255.0F;
    float f3 = (c & 0xFF) / 255.0F;
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glBlendFunc(770, 771);
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glBegin(2);
    
    for (int i = 0; i <= 360; i++)
    {
      double x2 = Math.sin(i * 3.141592653589793D / 180.0D) * r;
      double y2 = Math.cos(i * 3.141592653589793D / 180.0D) * r;
      GL11.glVertex2d(x + x2, y + y2);
    }
    
    GL11.glEnd();
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glDisable(3042);
  }
  
  public static void drawFilledCircle(int x, int y, double r, int c)
  {
    float f = (c >> 24 & 0xFF) / 255.0F;
    float f1 = (c >> 16 & 0xFF) / 255.0F;
    float f2 = (c >> 8 & 0xFF) / 255.0F;
    float f3 = (c & 0xFF) / 255.0F;
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glBlendFunc(770, 771);
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glBegin(6);
    
    for (int i = 0; i <= 360; i++)
    {
      double x2 = Math.sin(i * 3.141592653589793D / 180.0D) * r;
      double y2 = Math.cos(i * 3.141592653589793D / 180.0D) * r;
      GL11.glVertex2d(x + x2, y + y2);
    }
    
    GL11.glEnd();
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glDisable(3042);
  }
  
  public static void dr(double i, double j, double k, double l, int i1)
  {
    if (i < k)
    {
      double j1 = i;
      i = k;
      k = j1;
    }
    
    if (j < l)
    {
      double k1 = j;
      j = l;
      l = k1;
    }
    
    float f = (i1 >> 24 & 0xFF) / 255.0F;
    float f1 = (i1 >> 16 & 0xFF) / 255.0F;
    float f2 = (i1 >> 8 & 0xFF) / 255.0F;
    float f3 = (i1 & 0xFF) / 255.0F;
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldRenderer = tessellator.getWorldRenderer();
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glColor4f(f1, f2, f3, f);
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(i, l, 0.0D);
    worldRenderer.addVertex(k, l, 0.0D);
    worldRenderer.addVertex(k, j, 0.0D);
    worldRenderer.addVertex(i, j, 0.0D);
    tessellator.draw();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
  }
  
  public static void drawRect(int x, int y, int x1, int y1, int color)
  {
    GL11.glDisable(2929);
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glDepthMask(true);
    GL11.glEnable(2848);
    GL11.glHint(3154, 4354);
    GL11.glHint(3155, 4354);
    net.minecraft.client.gui.Gui.drawRect(x, y, x1, y1, color);
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glEnable(2929);
    GL11.glDisable(2848);
    GL11.glHint(3154, 4352);
    GL11.glHint(3155, 4352);
  }
  
  public static void drawHat(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith)
  {
    GL11.glPushMatrix();
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
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
}
