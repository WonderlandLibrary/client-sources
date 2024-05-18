package net.SliceClient.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class GuiUtils extends net.minecraft.client.gui.Gui
{
  public GuiUtils() {}
  
  public void drawOutlineRect(float drawX, float drawY, float drawWidth, float drawHeight, int color)
  {
    drawRect(drawX, drawY, drawWidth, drawY + 0.5F, color);
    drawRect(drawX, drawY + 0.5F, drawX + 0.5F, drawHeight, color);
    drawRect(drawWidth, drawY, drawWidth + 0.5F, drawHeight, color);
    drawRect(drawX + 0.5F, drawHeight - 0.5F, drawWidth, drawHeight, color);
  }
  

  public static void drawRoundedRect(float x, float y, float maxX, float maxY, int color)
  {
    drawRect(x + 0.5F, y, maxX - 0.5F, y + 0.5F, color);
    drawRect(x + 0.5F, maxY - 0.5F, maxX - 0.5F, maxY, color);
    drawRect(x, y + 0.5F, maxX, maxY - 0.5F, color);
  }
  
  public int getFadeHex(int hex1, int hex2, double ratio)
  {
    int r = hex1 >> 16;
    int g = hex1 >> 8 & 0xFF;
    int b = hex1 & 0xFF;
    r = (int)(r + ((hex2 >> 16) - r) * ratio);
    g = (int)(g + ((hex2 >> 8 & 0xFF) - g) * ratio);
    b = (int)(b + ((hex2 & 0xFF) - b) * ratio);
    return r << 16 | g << 8 | b;
  }
  
  public static void start3DGLConstants()
  {
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glDisable(2896);
    GL11.glDisable(2929);
    GL11.glDisable(3553);
    GL11.glDepthMask(false);
  }
  
  public static void finish3DGLConstants()
  {
    GL11.glEnable(2929);
    GL11.glEnable(3553);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
  }
  
  public static void drawRect(double d, double e, double f2, double f3, int paramColor)
  {
    float alpha = (paramColor >> 24 & 0xFF) / 255.0F;
    float red = (paramColor >> 16 & 0xFF) / 255.0F;
    float green = (paramColor >> 8 & 0xFF) / 255.0F;
    float blue = (paramColor & 0xFF) / 255.0F;
    
    GlStateManager.enableBlend();
    GlStateManager.func_179090_x();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    GL11.glPushMatrix();
    GL11.glColor4f(red, green, blue, alpha);
    GL11.glBegin(7);
    GL11.glVertex2d(f2, e);
    GL11.glVertex2d(d, e);
    GL11.glVertex2d(d, f3);
    GL11.glVertex2d(f2, f3);
    GL11.glEnd();
    GlStateManager.func_179098_w();
    GlStateManager.disableBlend();
    GL11.glPopMatrix();
  }
  
  public int fadeColor(int color1, int color2)
  {
    float alpha = (color1 >> 24 & 0xFF) / 255.0F;
    float red = (color1 >> 16 & 0xFF) / 255.0F;
    float green = (color1 >> 8 & 0xFF) / 255.0F;
    float blue = (color1 & 0xFF) / 255.0F;
    
    float alpha2 = (color2 >> 24 & 0xFF) / 255.0F;
    float red2 = (color2 >> 16 & 0xFF) / 255.0F;
    float green2 = (color2 >> 8 & 0xFF) / 255.0F;
    float blue2 = (color2 & 0xFF) / 255.0F;
    alpha += 0.001F;
    if (alpha >= 1.0F) {
      alpha = 1.0F;
    }
    return 0;
  }
  
  public void drawSideGradientRect(float i, float j, float k, float l, int i1, int j1)
  {
    float f = (i1 >> 24 & 0xFF) / 255.0F;
    float f1 = (i1 >> 16 & 0xFF) / 255.0F;
    float f2 = (i1 >> 8 & 0xFF) / 255.0F;
    float f3 = (i1 & 0xFF) / 255.0F;
    
    float f4 = (j1 >> 24 & 0xFF) / 255.0F;
    float f5 = (j1 >> 16 & 0xFF) / 255.0F;
    float f6 = (j1 >> 8 & 0xFF) / 255.0F;
    float f7 = (j1 & 0xFF) / 255.0F;
    GL11.glDisable(3553);
    GL11.glEnable(3042);
    GL11.glDisable(3008);
    GL11.glBlendFunc(770, 771);
    GL11.glShadeModel(7425);
    Tessellator tessellator = Tessellator.instance;
    WorldRenderer worldRenderer = tessellator.getWorldRenderer();
    worldRenderer.startDrawingQuads();
    GL11.glColor4f(f1, f2, f3, f);
    worldRenderer.addVertex(k, j, zLevel);
    GL11.glColor4f(f5, f6, f7, f4);
    worldRenderer.addVertex(i, j, zLevel);
    worldRenderer.addVertex(i, l, zLevel);
    GL11.glColor4f(f1, f2, f3, f);
    worldRenderer.addVertex(k, l, zLevel);
    worldRenderer.draw();
    GL11.glShadeModel(7424);
    GL11.glDisable(3042);
    GL11.glEnable(3008);
    GL11.glEnable(3553);
  }
  
  public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2)
  {
    float f = (col1 >> 24 & 0xFF) / 255.0F;
    float f1 = (col1 >> 16 & 0xFF) / 255.0F;
    float f2 = (col1 >> 8 & 0xFF) / 255.0F;
    float f3 = (col1 & 0xFF) / 255.0F;
    
    float f4 = (col2 >> 24 & 0xFF) / 255.0F;
    float f5 = (col2 >> 16 & 0xFF) / 255.0F;
    float f6 = (col2 >> 8 & 0xFF) / 255.0F;
    float f7 = (col2 & 0xFF) / 255.0F;
    
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    GL11.glShadeModel(7425);
    
    GL11.glPushMatrix();
    GL11.glBegin(7);
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glVertex2d(x2, y);
    GL11.glVertex2d(x, y);
    
    GL11.glColor4f(f5, f6, f7, f4);
    GL11.glVertex2d(x, y2);
    GL11.glVertex2d(x2, y2);
    GL11.glEnd();
    GL11.glPopMatrix();
    
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
    GL11.glShadeModel(7424);
  }
  
  public static void drawFilledBBESP(AxisAlignedBB axisalignedbb, int color)
  {
    GL11.glPushMatrix();
    float red = (color >> 24 & 0xFF) / 255.0F;
    float green = (color >> 16 & 0xFF) / 255.0F;
    float blue = (color >> 8 & 0xFF) / 255.0F;
    float alpha = (color & 0xFF) / 255.0F;
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glDisable(2896);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    GL11.glColor4f(red, green, blue, alpha);
    drawFilledBox(axisalignedbb);
    
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glEnable(2896);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
    GL11.glPopMatrix();
  }
  
  public void drawFilledBoxForEntity(Entity entity, AxisAlignedBB axisalignedbb, int color)
  {
    GL11.glPushMatrix();
    float red = (color >> 24 & 0xFF) / 255.0F;
    float green = (color >> 16 & 0xFF) / 255.0F;
    float blue = (color >> 8 & 0xFF) / 255.0F;
    float alpha = (color & 0xFF) / 255.0F;
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glDisable(2896);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    GL11.glColor4f(red, green, blue, alpha);
    drawFilledBox(axisalignedbb);
    
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glEnable(2896);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
    GL11.glPopMatrix();
  }
  
  public static void startSmooth()
  {
    GL11.glEnable(2848);
    GL11.glEnable(2881);
    GL11.glEnable(2832);
    GL11.glEnable(3042);
  }
  
  public static void endSmooth()
  {
    GL11.glDisable(2848);
    GL11.glDisable(2881);
    GL11.glDisable(2832);
    GL11.glDisable(3042);
  }
  
  public static void drawSCBox(double x, double y, double z, double x2, double y2, double z2)
  {
    x -= RenderManager.renderPosX;x2 -= RenderManager.renderPosX;
    y -= RenderManager.renderPosY;y2 -= RenderManager.renderPosY;
    z -= RenderManager.renderPosZ;z2 -= RenderManager.renderPosZ;
    
    GL11.glBegin(7);
    GL11.glVertex3d(x, y, z);
    GL11.glVertex3d(x, y2, z);
    GL11.glVertex3d(x2, y, z);
    GL11.glVertex3d(x2, y2, z);
    GL11.glVertex3d(x2, y, z2);
    GL11.glVertex3d(x2, y2, z2);
    GL11.glVertex3d(x, y, z2);
    GL11.glVertex3d(x, y2, z2);
    
    GL11.glVertex3d(x2, y2, z);
    GL11.glVertex3d(x2, y, z);
    GL11.glVertex3d(x, y2, z);
    GL11.glVertex3d(x, y, z);
    GL11.glVertex3d(x, y2, z2);
    GL11.glVertex3d(x, y, z2);
    GL11.glVertex3d(x2, y2, z2);
    GL11.glVertex3d(x2, y, z2);
    
    GL11.glVertex3d(x, y2, z);
    GL11.glVertex3d(x2, y2, z);
    GL11.glVertex3d(x2, y2, z2);
    GL11.glVertex3d(x, y2, z2);
    GL11.glVertex3d(x, y2, z);
    GL11.glVertex3d(x, y2, z2);
    GL11.glVertex3d(x2, y2, z2);
    GL11.glVertex3d(x2, y2, z);
    
    GL11.glVertex3d(x, y, z);
    GL11.glVertex3d(x2, y, z);
    GL11.glVertex3d(x2, y, z2);
    GL11.glVertex3d(x, y, z2);
    GL11.glVertex3d(x, y, z);
    GL11.glVertex3d(x, y, z2);
    GL11.glVertex3d(x2, y, z2);
    GL11.glVertex3d(x2, y, z);
    
    GL11.glVertex3d(x, y, z);
    GL11.glVertex3d(x, y2, z);
    GL11.glVertex3d(x, y, z2);
    GL11.glVertex3d(x, y2, z2);
    GL11.glVertex3d(x2, y, z2);
    GL11.glVertex3d(x2, y2, z2);
    GL11.glVertex3d(x2, y, z);
    GL11.glVertex3d(x2, y2, z);
    
    GL11.glVertex3d(x, y2, z2);
    GL11.glVertex3d(x, y, z2);
    GL11.glVertex3d(x, y2, z);
    GL11.glVertex3d(x, y, z);
    GL11.glVertex3d(x2, y2, z);
    GL11.glVertex3d(x2, y, z);
    GL11.glVertex3d(x2, y2, z2);
    GL11.glVertex3d(x2, y, z2);
    GL11.glEnd();
  }
  
  public static void drawBoundingBoxESP(AxisAlignedBB axisalignedbb, float width, int color)
  {
    GL11.glPushMatrix();
    float red = (color >> 24 & 0xFF) / 255.0F;
    float green = (color >> 16 & 0xFF) / 255.0F;
    float blue = (color >> 8 & 0xFF) / 255.0F;
    float alpha = (color & 0xFF) / 255.0F;
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glDisable(2896);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    GL11.glLineWidth(width);
    GL11.glColor4f(red, green, blue, alpha);
    drawOutlinedBox(axisalignedbb);
    GL11.glLineWidth(1.0F);
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glEnable(2896);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
    GL11.glPopMatrix();
  }
  
  public void draw2DPlayerESP(EntityPlayer ep, double d, double d1, double d2)
  {
    Minecraft.getMinecraft();float distance = Minecraft.thePlayer.getDistanceToEntity(ep);
    Minecraft.getMinecraft();float scale = (float)(0.09D + Minecraft.thePlayer.getDistance(posX, posY, posZ) / 10000.0D);
    
    GL11.glPushMatrix();
    GL11.glTranslatef((float)d, (float)d1, (float)d2);
    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
    GL11.glRotatef(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
    
    GL11.glScalef(-scale, -scale, scale);
    GL11.glDisable(2896);
    GL11.glDisable(2929);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glScaled(0.5D, 0.5D, 0.5D);
    drawOutlineRect(-13.0F, -45.0F, 13.0F, 5.0F, -65536);
    GL11.glScaled(2.0D, 2.0D, 2.0D);
    GL11.glDisable(3042);
    GL11.glEnable(2929);
    GL11.glEnable(2896);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glPopMatrix();
  }
  
  public static void drawOutlineForEntity(Entity e, AxisAlignedBB axisalignedbb, float width, float red, float green, float blue, float alpha)
  {
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glDisable(2896);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    GL11.glLineWidth(width);
    GL11.glColor4f(red, green, blue, alpha);
    drawOutlinedBox(axisalignedbb);
    GL11.glLineWidth(1.0F);
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glEnable(2896);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
  }
  
  public void drawOutlineBox(AxisAlignedBB axisalignedbb, float width, int color)
  {
    GL11.glPushMatrix();
    float red = (color >> 24 & 0xFF) / 255.0F;
    float green = (color >> 16 & 0xFF) / 255.0F;
    float blue = (color >> 8 & 0xFF) / 255.0F;
    float alpha = (color & 0xFF) / 255.0F;
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glDisable(2896);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    GL11.glLineWidth(width);
    GL11.glColor4f(red, green, blue, alpha);
    drawOutlinedBox(axisalignedbb);
    GL11.glLineWidth(1.0F);
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glEnable(2896);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
    GL11.glPopMatrix();
  }
  
  public static void drawFilledBox(AxisAlignedBB boundingBox)
  {
    if (boundingBox == null) {
      return;
    }
    GL11.glBegin(7);
    GL11.glVertex3d(minX, minY, minZ);
    GL11.glVertex3d(minX, maxY, minZ);
    GL11.glVertex3d(minX, minY, minZ);
    GL11.glVertex3d(maxX, maxY, minZ);
    
    GL11.glVertex3d(maxX, minY, maxZ);
    GL11.glVertex3d(maxX, maxY, maxZ);
    GL11.glVertex3d(minX, minY, maxZ);
    GL11.glVertex3d(minX, maxY, maxZ);
    GL11.glEnd();
    
    GL11.glBegin(7);
    GL11.glVertex3d(maxX, maxY, minZ);
    GL11.glVertex3d(maxX, minY, minZ);
    GL11.glVertex3d(minX, maxY, minZ);
    GL11.glVertex3d(minX, minY, minZ);
    
    GL11.glVertex3d(minX, maxY, maxZ);
    GL11.glVertex3d(minX, minY, maxZ);
    GL11.glVertex3d(maxX, maxY, maxZ);
    GL11.glVertex3d(maxX, minY, maxZ);
    GL11.glEnd();
    
    GL11.glBegin(7);
    GL11.glVertex3d(minX, minY, minZ);
    GL11.glVertex3d(minX, maxY, minZ);
    GL11.glVertex3d(maxX, minY, minZ);
    GL11.glVertex3d(maxX, maxY, minZ);
    GL11.glEnd();
    
    GL11.glBegin(7);
    GL11.glVertex3d(minX, maxY, minZ);
    GL11.glVertex3d(maxX, maxY, minZ);
    GL11.glVertex3d(maxX, maxY, maxZ);
    GL11.glVertex3d(minX, maxY, maxZ);
    
    GL11.glVertex3d(minX, maxY, minZ);
    GL11.glVertex3d(minX, maxY, maxZ);
    GL11.glVertex3d(maxX, maxY, maxZ);
    GL11.glVertex3d(maxX, maxY, minZ);
    GL11.glEnd();
    
    GL11.glBegin(7);
    GL11.glVertex3d(minX, minY, minZ);
    GL11.glVertex3d(maxX, minY, minZ);
    GL11.glVertex3d(maxX, minY, maxZ);
    GL11.glVertex3d(minX, minY, maxZ);
    GL11.glVertex3d(minX, minY, minZ);
    GL11.glVertex3d(minX, minY, maxZ);
    GL11.glVertex3d(maxX, minY, maxZ);
    GL11.glVertex3d(maxX, minY, minZ);
    GL11.glEnd();
    
    GL11.glBegin(7);
    GL11.glVertex3d(minX, minY, minZ);
    GL11.glVertex3d(minX, maxY, minZ);
    GL11.glVertex3d(minX, minY, maxZ);
    GL11.glVertex3d(minX, maxY, maxZ);
    GL11.glVertex3d(maxX, minY, maxZ);
    GL11.glVertex3d(maxX, maxY, maxZ);
    GL11.glVertex3d(maxX, minY, minZ);
    GL11.glVertex3d(maxX, maxY, minZ);
    GL11.glEnd();
    
    GL11.glBegin(7);
    GL11.glVertex3d(minX, maxY, maxZ);
    GL11.glVertex3d(minX, minY, maxZ);
    GL11.glVertex3d(minX, maxY, minZ);
    GL11.glVertex3d(minX, minY, minZ);
    GL11.glVertex3d(maxX, maxY, minZ);
    GL11.glVertex3d(maxX, minY, minZ);
    GL11.glVertex3d(maxX, maxY, maxZ);
    GL11.glVertex3d(maxX, minY, maxZ);
    GL11.glEnd();
  }
  
  public static void drawOutlinedBox(AxisAlignedBB boundingBox)
  {
    if (boundingBox == null) {
      return;
    }
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
  
  public void drawBoundingBox(AxisAlignedBB axisalignedbb)
  {
    Tessellator tessellator = Tessellator.instance;
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
    worldRenderer.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(maxX, maxY, minZ);
    worldRenderer.addVertex(maxX, minY, minZ);
    worldRenderer.addVertex(minX, maxY, minZ);
    worldRenderer.addVertex(minX, minY, minZ);
    worldRenderer.addVertex(minX, maxY, maxZ);
    worldRenderer.addVertex(minX, minY, maxZ);
    worldRenderer.addVertex(maxX, maxY, maxZ);
    worldRenderer.addVertex(maxX, minY, maxZ);
    worldRenderer.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(minX, maxY, minZ);
    worldRenderer.addVertex(maxX, maxY, minZ);
    worldRenderer.addVertex(maxX, maxY, maxZ);
    worldRenderer.addVertex(minX, maxY, maxZ);
    worldRenderer.addVertex(minX, maxY, minZ);
    worldRenderer.addVertex(minX, maxY, maxZ);
    worldRenderer.addVertex(maxX, maxY, maxZ);
    worldRenderer.addVertex(maxX, maxY, minZ);
    worldRenderer.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(minX, minY, minZ);
    worldRenderer.addVertex(maxX, minY, minZ);
    worldRenderer.addVertex(maxX, minY, maxZ);
    worldRenderer.addVertex(minX, minY, maxZ);
    worldRenderer.addVertex(minX, minY, minZ);
    worldRenderer.addVertex(minX, minY, maxZ);
    worldRenderer.addVertex(maxX, minY, maxZ);
    worldRenderer.addVertex(maxX, minY, minZ);
    worldRenderer.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(minX, minY, minZ);
    worldRenderer.addVertex(minX, maxY, minZ);
    worldRenderer.addVertex(minX, minY, maxZ);
    worldRenderer.addVertex(minX, maxY, maxZ);
    worldRenderer.addVertex(maxX, minY, maxZ);
    worldRenderer.addVertex(maxX, maxY, maxZ);
    worldRenderer.addVertex(maxX, minY, minZ);
    worldRenderer.addVertex(maxX, maxY, minZ);
    worldRenderer.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(minX, maxY, maxZ);
    worldRenderer.addVertex(minX, minY, maxZ);
    worldRenderer.addVertex(minX, maxY, minZ);
    worldRenderer.addVertex(minX, minY, minZ);
    worldRenderer.addVertex(maxX, maxY, minZ);
    worldRenderer.addVertex(maxX, minY, minZ);
    worldRenderer.addVertex(maxX, maxY, maxZ);
    worldRenderer.addVertex(maxX, minY, maxZ);
    worldRenderer.draw();
  }
  

  public static void drawBorderedCircle(int x, int y, float radius, int outsideC, int insideC)
  {
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    GL11.glPushMatrix();
    float scale = 0.1F;
    GL11.glScalef(scale, scale, scale);
    x = (int)(x * (1.0F / scale));
    y = (int)(y * (1.0F / scale));
    radius *= 1.0F / scale;
    drawCircle(x, y, radius, insideC);
    drawUnfilledCircle(x, y, radius, 1.0F, outsideC);
    GL11.glScalef(1.0F / scale, 1.0F / scale, 1.0F / scale);
    GL11.glPopMatrix();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
  }
  
  public static void drawUnfilledCircle(int x, int y, float radius, float lineWidth, int color)
  {
    float alpha = (color >> 24 & 0xFF) / 255.0F;
    float red = (color >> 16 & 0xFF) / 255.0F;
    float green = (color >> 8 & 0xFF) / 255.0F;
    float blue = (color & 0xFF) / 255.0F;
    GL11.glColor4f(red, green, blue, alpha);
    GL11.glLineWidth(lineWidth);
    GL11.glEnable(2848);
    GL11.glBegin(2);
    for (int i = 0; i <= 360; i++) {
      GL11.glVertex2d(x + Math.sin(i * 3.141526D / 180.0D) * radius, y + Math.cos(i * 3.141526D / 180.0D) * radius);
    }
    GL11.glEnd();
    GL11.glDisable(2848);
  }
  
  public static void drawCircle(int x, int y, float radius, int color)
  {
    float alpha = (color >> 24 & 0xFF) / 255.0F;
    float red = (color >> 16 & 0xFF) / 255.0F;
    float green = (color >> 8 & 0xFF) / 255.0F;
    float blue = (color & 0xFF) / 255.0F;
    GL11.glColor4f(red, green, blue, alpha);
    GL11.glBegin(9);
    for (int i = 0; i <= 360; i++) {
      GL11.glVertex2d(x + Math.sin(i * 3.141526D / 180.0D) * radius, y + Math.cos(i * 3.141526D / 180.0D) * radius);
    }
    GL11.glEnd();
  }
}
