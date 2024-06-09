package lunadevs.luna.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiUtils
  extends Gui
{
  public void drawOutlineRect(float drawX, float drawY, float drawWidth, float drawHeight, int color)
  {
    drawRect(drawX, drawY, drawWidth, drawY + 0.5F, color);
    drawRect(drawX, drawY + 0.5F, drawX + 0.5F, drawHeight, color);
    drawRect(drawWidth - 0.5F, drawY + 0.5F, drawWidth, drawHeight - 0.5F, color);
    drawRect(drawX + 0.5F, drawHeight - 0.5F, drawWidth, drawHeight, color);
  }
  
  public void drawOutlinedRect(float drawX, float drawY, float drawWidth, float drawHeight, float alpha, float red, float green, float blue)
  {
    drawRectColor(drawX, drawY, drawWidth, drawY + 0.5F, alpha, red, green, blue);
    drawRectColor(drawX, drawY + 0.5F, drawX + 0.5F, drawHeight, alpha, red, green, blue);
    drawRectColor(drawWidth - 0.5F, drawY + 0.5F, drawWidth, drawHeight - 0.5F, alpha, red, green, blue);
    drawRectColor(drawX + 0.5F, drawHeight - 0.5F, drawWidth, drawHeight, alpha, red, green, blue);
  }
  

  
  public static void drawRoundedRect(float x, float y, float maxX, float maxY, int color)
  {
    drawRect(x + 0.5F, y, maxX - 0.5F, y + 0.5F, color);
    drawRect(x + 0.5F, maxY - 0.5F, maxX - 0.5F, maxY, color);
    drawRect(x, y + 0.5F, maxX, maxY - 0.5F, color);
  }
  
  public void drawTexturedRectangle(ResourceLocation texture, float x, float y, float width, float height, float u, float v, float uWidth, float vHeight)
  {
    GL11.glEnable(3042);
    Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    float scale = 0.00390625F;
    Tessellator tessellator = Tessellator.instance;
    WorldRenderer worldRenderer = tessellator.getWorldRenderer();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertexWithUV(x, y + height, 0.0D, u * scale, (v + vHeight) * scale);
    worldRenderer.addVertexWithUV(x + width, y + height, 0.0D, (u + uWidth) * scale, (v + vHeight) * scale);
    worldRenderer.addVertexWithUV(x + width, y, 0.0D, (u + uWidth) * scale, v * scale);
    worldRenderer.addVertexWithUV(x, y, 0.0D, u * scale, v * scale);
    worldRenderer.draw();
    GL11.glDisable(3042);
  }
  
  public static void scissorBox(int x, int y, int xend, int yend)
  {
    int width = xend - x;
    int height = yend - y;
    ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
    int factor = sr.getScaleFactor();
    int bottomY = Minecraft.getMinecraft().currentScreen.height - yend;
    GL11.glScissor(x * factor, bottomY * factor, width * factor, height * factor);
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
  
  public static void drawTriangle(double cx, double cy, float g, float theta, float h, int c)
  {
    GL11.glTranslated(cx, cy, 0.0D);
    GL11.glRotatef(180.0F + theta, 0.0F, 0.0F, 1.0F);
    float f = (c >> 24 & 0xFF) / 255.0F;
    float f1 = (c >> 16 & 0xFF) / 255.0F;
    float f2 = (c >> 8 & 0xFF) / 255.0F;
    float f3 = (c & 0xFF) / 255.0F;
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glBlendFunc(770, 771);
    GL11.glLineWidth(h);
    GL11.glBegin(6);
    
    GL11.glVertex2d(0.0D, 1.0F * g);
    GL11.glVertex2d(1.0F * g, -(1.0F * g));
    GL11.glVertex2d(-(1.0F * g), -(1.0F * g));
    
    GL11.glEnd();
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glRotatef(-180.0F - theta, 0.0F, 0.0F, 1.0F);
    GL11.glTranslated(-cx, -cy, 0.0D);
  }
  
  public static void drawTextureID(float g, float y, float width, float height, int id)
  {
    GL11.glPushMatrix();
    GL11.glBindTexture(3553, id);
    GL11.glTranslatef(g, y, 0.0F);
    Tessellator tessellator = Tessellator.instance;
    WorldRenderer worldRenderer = tessellator.getWorldRenderer();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertexWithUV(0.0D, height, 0.0D, 0.0D, 1.0D);
    worldRenderer.addVertexWithUV(width, height, 0.0D, 1.0D, 1.0D);
    worldRenderer.addVertexWithUV(width, 0.0D, 0.0D, 1.0D, 0.0D);
    worldRenderer.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    worldRenderer.draw();
    GL11.glPopMatrix();
  }
  
  public static void drawRect(double d, double e, double f2, double f3, float red, float green, float blue, float alpha)
  {
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
  
  public static void drawRectColor(double d, double e, double f2, double f3, float alpha, float red, float green, float blue)
  {
    GlStateManager.enableBlend();
    GlStateManager.func_179090_x();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    GL11.glPushMatrix();
    GL11.glColor4f(alpha, red, green, blue);
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
  
  public static void drawSideGradientRect(float i, float j, float k, float l, int i1, int j1)
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
  
  public static void drawGradient(double x, double y, double x2, double y2, int col1, int col2)
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
  
  public void drawFilledBBESP(AxisAlignedBB axisalignedbb, int color)
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
  
  public void drawBoundingBoxESP(AxisAlignedBB axisalignedbb, float width, int color)
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
    float distance = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(ep);
    float scale = (float)(0.09D + Minecraft.getMinecraft().thePlayer.getDistance(ep.posX, ep.posY, ep.posZ) / 10000.0D);
    
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
  
  public void drawOutlineForEntity(Entity e, AxisAlignedBB axisalignedbb, float width, float red, float green, float blue, float alpha)
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
  
  public static void drawLines(AxisAlignedBB bb, float width, float red, float green, float blue, float alpha)
  {
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glDisable(2896);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    GL11.glLineWidth(width);
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldRenderer = tessellator.getWorldRenderer();
    worldRenderer.startDrawing(2);
    worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
    worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
    tessellator.draw();
    worldRenderer.startDrawing(2);
    worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
    worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
    tessellator.draw();
    worldRenderer.startDrawing(2);
    worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
    worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
    tessellator.draw();
    worldRenderer.startDrawing(2);
    worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
    worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
    tessellator.draw();
    worldRenderer.startDrawing(2);
    worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
    worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
    tessellator.draw();
    worldRenderer.startDrawing(2);
    worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
    worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
    tessellator.draw();
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
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
    
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glEnd();
    
    GL11.glBegin(7);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
    
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
    GL11.glEnd();
    
    GL11.glBegin(7);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
    GL11.glEnd();
    
    GL11.glBegin(7);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
    
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
    GL11.glEnd();
    
    GL11.glBegin(7);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
    GL11.glEnd();
    
    GL11.glBegin(7);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
    GL11.glEnd();
    
    GL11.glBegin(7);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
    GL11.glEnd();
  }
  
  public static void drawOutlinedBox(AxisAlignedBB boundingBox)
  {
    if (boundingBox == null) {
      return;
    }
    GL11.glBegin(3);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
    GL11.glEnd();
    GL11.glBegin(3);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
    GL11.glEnd();
    GL11.glBegin(1);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
    GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
    GL11.glEnd();
  }
  
  public void drawBoundingBox(AxisAlignedBB axisalignedbb)
  {
    Tessellator tessellator = Tessellator.instance;
    WorldRenderer worldRenderer = tessellator.getWorldRenderer();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
    worldRenderer.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
    worldRenderer.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
    worldRenderer.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
    worldRenderer.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
    worldRenderer.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
    worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
    worldRenderer.draw();
  }
}
