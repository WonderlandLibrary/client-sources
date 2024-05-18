package net.SliceClient.Gui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class RenderHelper
{
  static
  {
    Minecraft.getMinecraft();
    Minecraft.getMinecraft();
  }
  
  private static final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth, 
    Minecraft.displayHeight);
  private static final Vec3 field_82885_c = new Vec3(-0.20000000298023224D, 1.0D, 0.699999988079071D).normalize();
  private static final Vec3 field_82884_b = new Vec3(0.20000000298023224D, 1.0D, -0.699999988079071D).normalize();
  
  public RenderHelper() {}
  
  public static final ScaledResolution getScaledRes() {
    Minecraft.getMinecraft();Minecraft.getMinecraft();ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth, Minecraft.displayHeight);
    return scaledRes;
  }
  
  public static void drawTexturedRect(int x, int y, int width, int height, GuiIngame screen, ResourceLocation texture)
  {
    GlStateManager.pushMatrix();
    
    GlStateManager.enableBlend();
    
    Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    
    screen.drawTexturedModalRect(x, y, 0, 0, width, height);
    
    GlStateManager.disableBlend();
    
    GlStateManager.popMatrix();
  }
  
  public static void drawHollowRect(float posX, float posY, float posX2, float posY2, float width, int color, boolean center)
  {
    float corners = width / 2.0F;
    float side = width / 2.0F;
    if (center)
    {
      drawRect(posX - side, posY - corners, posX + side, posY2 + corners, color);
      drawRect(posX2 - side, posY - corners, posX2 + side, posY2 + corners, color);
      drawRect(posX - corners, posY - side, posX2 + corners, posY + side, color);
      drawRect(posX - corners, posY2 - side, posX2 + corners, posY2 + side, color);
    }
    else
    {
      drawRect(posX - width, posY - corners, posX, posY2 + corners, color);
      drawRect(posX2, posY - corners, posX2 + width, posY2 + corners, color);
      drawRect(posX - corners, posY - width, posX2 + corners, posY, color);
      drawRect(posX - corners, posY2, posX2 + corners, posY2 + width, color);
    }
  }
  
  public static void drawGradientBorderedRect(float posX, float posY, float posX2, float posY2, float width, int color, int startColor, int endColor, boolean center)
  {
    drawGradientRect(posX, posY, posX2, posY2, startColor, endColor);
    drawHollowRect(posX, posY, posX2, posY2, width, color, center);
  }
  
  public static void drawCoolLines(AxisAlignedBB mask)
  {
    GL11.glPushMatrix();
    GL11.glBegin(2);
    GL11.glVertex3d(minX, minY, 
      minZ);
    GL11.glVertex3d(minX, maxY, 
      maxZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(maxX, minY, 
      minZ);
    GL11.glVertex3d(maxX, maxY, 
      maxZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(maxX, minY, 
      maxZ);
    GL11.glVertex3d(minX, maxY, 
      maxZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(maxX, minY, 
      minZ);
    GL11.glVertex3d(minX, maxY, 
      minZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(maxX, minY, 
      minZ);
    GL11.glVertex3d(minX, minY, 
      maxZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(maxX, maxY, 
      minZ);
    GL11.glVertex3d(minX, maxY, 
      maxZ);
    GL11.glEnd();
    GL11.glPopMatrix();
  }
  
  public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2)
  {
    drawRect(x, y, x2, y2, col2);
    
    float f = (col1 >> 24 & 0xFF) / 255.0F;
    float f1 = (col1 >> 16 & 0xFF) / 255.0F;
    float f2 = (col1 >> 8 & 0xFF) / 255.0F;
    float f3 = (col1 & 0xFF) / 255.0F;
    
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    
    GL11.glPushMatrix();
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glLineWidth(l1);
    GL11.glBegin(1);
    GL11.glVertex2d(x, y);
    GL11.glVertex2d(x, y2);
    GL11.glVertex2d(x2, y2);
    GL11.glVertex2d(x2, y);
    GL11.glVertex2d(x, y);
    GL11.glVertex2d(x2, y);
    GL11.glVertex2d(x, y2);
    GL11.glVertex2d(x2, y2);
    GL11.glEnd();
    GL11.glPopMatrix();
    
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
  }
  
  public static void drawBorderedCorneredRect(float x, float y, float x2, float y2, float lineWidth, int lineColor, int bgColor)
  {
    drawRect(x, y, x2, y2, bgColor);
    
    float f = (lineColor >> 24 & 0xFF) / 255.0F;
    float f1 = (lineColor >> 16 & 0xFF) / 255.0F;
    float f2 = (lineColor >> 8 & 0xFF) / 255.0F;
    float f3 = (lineColor & 0xFF) / 255.0F;
    
    GL11.glEnable(3042);
    GL11.glEnable(3553);
    drawRect(x - 1.0F, y, x2 + 1.0F, y - lineWidth, lineColor);
    drawRect(x, y, x - lineWidth, y2, lineColor);
    drawRect(x - 1.0F, y2, x2 + 1.0F, y2 + lineWidth, lineColor);
    drawRect(x2, y, x2 + lineWidth, y2, lineColor);
    GL11.glDisable(3553);
    GL11.glDisable(3042);
  }
  
  public static double interp(double from, double to, double pct)
  {
    return from + (to - from) * pct;
  }
  
  public static double interpPlayerX()
  {
    Minecraft.getMinecraft();Minecraft.getMinecraft();return interp(thePlayerlastTickPosX, thePlayerposX, Timer.renderPartialTicks);
  }
  
  public static double interpPlayerY()
  {
    Minecraft.getMinecraft();Minecraft.getMinecraft();return interp(thePlayerlastTickPosY, thePlayerposY, Timer.renderPartialTicks);
  }
  
  public static double interpPlayerZ()
  {
    Minecraft.getMinecraft();Minecraft.getMinecraft();return interp(thePlayerlastTickPosZ, thePlayerposZ, Timer.renderPartialTicks);
  }
  
  public static void drawFilledBox(AxisAlignedBB mask)
  {
    WorldRenderer worldRenderer = Tessellator.instance.getWorldRenderer();
    Tessellator tessellator = Tessellator.instance;
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(minX, minY, 
      minZ);
    worldRenderer.addVertex(minX, maxY, 
      minZ);
    worldRenderer.addVertex(maxX, minY, 
      minZ);
    worldRenderer.addVertex(maxX, maxY, 
      minZ);
    worldRenderer.addVertex(maxX, minY, 
      maxZ);
    worldRenderer.addVertex(maxX, maxY, 
      maxZ);
    worldRenderer.addVertex(minX, minY, 
      maxZ);
    worldRenderer.addVertex(minX, maxY, 
      maxZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(maxX, maxY, 
      minZ);
    worldRenderer.addVertex(maxX, minY, 
      minZ);
    worldRenderer.addVertex(minX, maxY, 
      minZ);
    worldRenderer.addVertex(minX, minY, 
      minZ);
    worldRenderer.addVertex(minX, maxY, 
      maxZ);
    worldRenderer.addVertex(minX, minY, 
      maxZ);
    worldRenderer.addVertex(maxX, maxY, 
      maxZ);
    worldRenderer.addVertex(maxX, minY, 
      maxZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(minX, maxY, 
      minZ);
    worldRenderer.addVertex(maxX, maxY, 
      minZ);
    worldRenderer.addVertex(maxX, maxY, 
      maxZ);
    worldRenderer.addVertex(minX, maxY, 
      maxZ);
    worldRenderer.addVertex(minX, maxY, 
      minZ);
    worldRenderer.addVertex(minX, maxY, 
      maxZ);
    worldRenderer.addVertex(maxX, maxY, 
      maxZ);
    worldRenderer.addVertex(maxX, maxY, 
      minZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(minX, minY, 
      minZ);
    worldRenderer.addVertex(maxX, minY, 
      minZ);
    worldRenderer.addVertex(maxX, minY, 
      maxZ);
    worldRenderer.addVertex(minX, minY, 
      maxZ);
    worldRenderer.addVertex(minX, minY, 
      minZ);
    worldRenderer.addVertex(minX, minY, 
      maxZ);
    worldRenderer.addVertex(maxX, minY, 
      maxZ);
    worldRenderer.addVertex(maxX, minY, 
      minZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(minX, minY, 
      minZ);
    worldRenderer.addVertex(minX, maxY, 
      minZ);
    worldRenderer.addVertex(minX, minY, 
      maxZ);
    worldRenderer.addVertex(minX, maxY, 
      maxZ);
    worldRenderer.addVertex(maxX, minY, 
      maxZ);
    worldRenderer.addVertex(maxX, maxY, 
      maxZ);
    worldRenderer.addVertex(maxX, minY, 
      minZ);
    worldRenderer.addVertex(maxX, maxY, 
      minZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(minX, maxY, 
      maxZ);
    worldRenderer.addVertex(minX, minY, 
      maxZ);
    worldRenderer.addVertex(minX, maxY, 
      minZ);
    worldRenderer.addVertex(minX, minY, 
      minZ);
    worldRenderer.addVertex(maxX, maxY, 
      minZ);
    worldRenderer.addVertex(maxX, minY, 
      minZ);
    worldRenderer.addVertex(maxX, maxY, 
      maxZ);
    worldRenderer.addVertex(maxX, minY, 
      maxZ);
    tessellator.draw();
  }
  
  public static void glColor(Color color)
  {
    GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
  }
  
  public static void glColor(int hex)
  {
    float alpha = (hex >> 24 & 0xFF) / 255.0F;
    float red = (hex >> 16 & 0xFF) / 255.0F;
    float green = (hex >> 8 & 0xFF) / 255.0F;
    float blue = (hex & 0xFF) / 255.0F;
    GL11.glColor4f(red, green, blue, alpha);
  }
  
  public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor)
  {
    GL11.glEnable(1536);
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
    GL11.glDisable(1536);
  }
  
  public static void drawLines(AxisAlignedBB mask)
  {
    GL11.glPushMatrix();
    GL11.glBegin(2);
    GL11.glVertex3d(minX, minY, 
      minZ);
    GL11.glVertex3d(minX, maxY, 
      maxZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(minX, maxY, 
      minZ);
    GL11.glVertex3d(minX, minY, 
      maxZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(maxX, minY, 
      minZ);
    GL11.glVertex3d(maxX, maxY, 
      maxZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(maxX, maxY, 
      minZ);
    GL11.glVertex3d(maxX, minY, 
      maxZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(maxX, minY, 
      maxZ);
    GL11.glVertex3d(minX, maxY, 
      maxZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(maxX, maxY, 
      maxZ);
    GL11.glVertex3d(minX, minY, 
      maxZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(maxX, minY, 
      minZ);
    GL11.glVertex3d(minX, maxY, 
      minZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(maxX, maxY, 
      minZ);
    GL11.glVertex3d(minX, minY, 
      minZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(minX, maxY, 
      minZ);
    GL11.glVertex3d(maxX, maxY, 
      maxZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(maxX, maxY, 
      minZ);
    GL11.glVertex3d(minX, maxY, 
      maxZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(minX, minY, 
      minZ);
    GL11.glVertex3d(maxX, minY, 
      maxZ);
    GL11.glEnd();
    GL11.glBegin(2);
    GL11.glVertex3d(maxX, minY, 
      minZ);
    GL11.glVertex3d(minX, minY, 
      maxZ);
    GL11.glEnd();
    GL11.glPopMatrix();
  }
  
  public static void drawOutlinedBoundingBox(AxisAlignedBB mask)
  {
    WorldRenderer var2 = Tessellator.instance.getWorldRenderer();
    Tessellator var1 = Tessellator.instance;
    var2.startDrawing(3);
    var2.addVertex(minX, minY, 
      minZ);
    var2.addVertex(maxX, minY, 
      minZ);
    var2.addVertex(maxX, minY, 
      maxZ);
    var2.addVertex(minX, minY, 
      maxZ);
    var2.addVertex(minX, minY, 
      minZ);
    var1.draw();
    var2.startDrawing(3);
    var2.addVertex(minX, maxY, 
      minZ);
    var2.addVertex(maxX, maxY, 
      minZ);
    var2.addVertex(maxX, maxY, 
      maxZ);
    var2.addVertex(minX, maxY, 
      maxZ);
    var2.addVertex(minX, maxY, 
      minZ);
    var1.draw();
    var2.startDrawing(1);
    var2.addVertex(minX, minY, 
      minZ);
    var2.addVertex(minX, maxY, 
      minZ);
    var2.addVertex(maxX, minY, 
      minZ);
    var2.addVertex(maxX, maxY, 
      minZ);
    var2.addVertex(maxX, minY, 
      maxZ);
    var2.addVertex(maxX, maxY, 
      maxZ);
    var2.addVertex(minX, minY, 
      maxZ);
    var2.addVertex(minX, maxY, 
      maxZ);
    var1.draw();
  }
  
  public static void drawRect(float g, float h, float i, float j, int col1)
  {
    float f = (col1 >> 24 & 0xFF) / 255.0F;
    float f1 = (col1 >> 16 & 0xFF) / 255.0F;
    float f2 = (col1 >> 8 & 0xFF) / 255.0F;
    float f3 = (col1 & 0xFF) / 255.0F;
    
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    
    GL11.glPushMatrix();
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glBegin(7);
    GL11.glVertex2d(i, h);
    GL11.glVertex2d(g, h);
    GL11.glVertex2d(g, j);
    GL11.glVertex2d(i, j);
    GL11.glEnd();
    GL11.glPopMatrix();
    
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
  }
  
  public static void drawRect(float g, float h, float i, float j, Color col2)
  {
    ColorUtil.setColor(col2);
    
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    
    GL11.glPushMatrix();
    GL11.glBegin(7);
    GL11.glVertex2d(i, h);
    GL11.glVertex2d(g, h);
    GL11.glVertex2d(g, j);
    GL11.glVertex2d(i, j);
    GL11.glEnd();
    GL11.glPopMatrix();
    
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
  }
  


  public static void drawButtonBorderedRect(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Color innerColor, Color outerColor, float lineWidhth)
  {
    drawButtonRect(x1, y1, x2, y2, x3, y3, x4, y4, innerColor);
    
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    
    GL11.glPushMatrix();
    ColorUtil.setColor(outerColor);
    GL11.glLineWidth(lineWidhth);
    GL11.glBegin(2);
    GL11.glVertex2d(x1, y1);
    GL11.glVertex2d(x3, y3);
    GL11.glVertex2d(x4, y4);
    GL11.glVertex2d(x2, y2);
    GL11.glEnd();
    GL11.glPopMatrix();
    
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
    GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
  }
  
  public static void drawButtonRect(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Color col)
  {
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    
    GL11.glPushMatrix();
    ColorUtil.setColor(col);
    GL11.glBegin(7);
    GL11.glVertex2d(x4, y4);
    GL11.glVertex2d(x3, y3);
    GL11.glVertex2d(x2, y2);
    GL11.glVertex2d(x1, y1);
    GL11.glEnd();
    
    GL11.glBegin(7);
    GL11.glVertex2d(x1, y1);
    GL11.glVertex2d(x2, y2);
    GL11.glVertex2d(x3, y3);
    GL11.glVertex2d(x4, y4);
    GL11.glEnd();
    GL11.glPopMatrix();
    
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
  }
  
  public static void enableStandardItemLighting() {
    GlStateManager.enableLighting();
    GlStateManager.enableBooleanStateAt(0);
    GlStateManager.enableBooleanStateAt(1);
    GlStateManager.enableColorMaterial();
    GlStateManager.colorMaterial(1032, 5634);
    float var0 = 0.4F;
    float var1 = 0.6F;
    float var2 = 0.0F;
    GL11.glLight(16384, 4611, setColorBuffer(field_82884_bxCoord, field_82884_byCoord, field_82884_bzCoord, 0.0D));
    GL11.glLight(16384, 4609, setColorBuffer(var1, var1, var1, 1.0D));
    GL11.glLight(16384, 4608, setColorBuffer(0.0D, 0.0D, 0.0D, 1.0D));
    GL11.glLight(16384, 4610, setColorBuffer(var2, var2, var2, 1.0D));
    GL11.glLight(16385, 4611, setColorBuffer(field_82885_cxCoord, field_82885_cyCoord, field_82885_czCoord, 0.0D));
    GL11.glLight(16385, 4609, setColorBuffer(var1, var1, var1, 1.0D));
    GL11.glLight(16385, 4608, setColorBuffer(0.0D, 0.0D, 0.0D, 1.0D));
    GL11.glLight(16385, 4610, setColorBuffer(var2, var2, var2, 1.0D));
    GlStateManager.shadeModel(7424);
    GL11.glLightModel(2899, setColorBuffer(var0, var0, var0, 1.0D));
  }
  
  private static java.nio.FloatBuffer setColorBuffer(double p_74517_0_, double p_74517_2_, double p_74517_4_, double p_74517_6_)
  {
    return setColorBuffer((float)p_74517_0_, (float)p_74517_2_, (float)p_74517_4_, (float)p_74517_6_);
  }
  
  public static void disableStandardItemLighting()
  {
    GlStateManager.disableLighting();
    GlStateManager.disableBooleanStateAt(0);
    GlStateManager.disableBooleanStateAt(1);
    GlStateManager.disableColorMaterial();
  }
}
