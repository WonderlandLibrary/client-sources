package com.darkcart.xdolf.util;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderUtils2
{
  private static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
  
  public static void nukerBox(BlockPos blockPos, float damage)
  {
    double x = blockPos.getX() - 
      Minecraft.getMinecraft().getRenderManager().renderPosX;
    double y = blockPos.getY() - 
      Minecraft.getMinecraft().getRenderManager().renderPosY;
    double z = blockPos.getZ() - 
      Minecraft.getMinecraft().getRenderManager().renderPosZ;
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(3042);
    GL11.glLineWidth(1.0F);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    GL11.glColor4f(damage, 1.0F - damage, 0.0F, 0.15F);
    drawColorBox(
      new AxisAlignedBB(x + 0.5D - damage / 2.0F, y + 0.5D - damage / 2.0F, 
      z + 0.5D - damage / 2.0F, x + 0.5D + damage / 2.0F, 
      y + 0.5D + damage / 2.0F, z + 0.5D + damage / 2.0F), 
      damage, 1.0F - damage, 0.0F, 0.15F);
    GL11.glColor4d(0.0D, 0.0D, 0.0D, 0.5D);
    drawSelectionBoundingBox(new AxisAlignedBB(x + 0.5D - damage / 2.0F, 
      y + 0.5D - damage / 2.0F, z + 0.5D - damage / 2.0F, x + 0.5D + damage / 2.0F, 
      y + 0.5D + damage / 2.0F, z + 0.5D + damage / 2.0F));
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
  }
  
  public static void searchBox(BlockPos blockPos)
  {
    double x = blockPos.getX() - 
      Minecraft.getMinecraft().getRenderManager().renderPosX;
    double y = blockPos.getY() - 
      Minecraft.getMinecraft().getRenderManager().renderPosY;
    double z = blockPos.getZ() - 
      Minecraft.getMinecraft().getRenderManager().renderPosZ;
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(3042);
    GL11.glLineWidth(1.0F);
    float sinus = 
      1.0F - MathHelper.abs(MathHelper.sin(
      (float)(Minecraft.getSystemTime() % 10000L) / 10000.0F * 3.1415927F * 4.0F) * 1.0F);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    GL11.glColor4f(1.0F - sinus, sinus, 0.0F, 0.15F);
    drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 
      1.0F - sinus, sinus, 0.0F, 0.15F);
    GL11.glColor4d(0.0D, 0.0D, 0.0D, 0.5D);
    drawSelectionBoundingBox(
      new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
  }
  
  public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha)
  {
    Tessellator ts = Tessellator.getInstance();
    VertexBuffer vb = ts.getBuffer();
    vb.begin(7, DefaultVertexFormats.POSITION_TEX);
    vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    ts.draw();
    vb.begin(7, DefaultVertexFormats.POSITION_TEX);
    vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    ts.draw();
    vb.begin(7, DefaultVertexFormats.POSITION_TEX);
    vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    ts.draw();
    vb.begin(7, DefaultVertexFormats.POSITION_TEX);
    vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    ts.draw();
    vb.begin(7, DefaultVertexFormats.POSITION_TEX);
    vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    ts.draw();
    vb.begin(7, DefaultVertexFormats.POSITION_TEX);
    vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
      .color(red, green, blue, alpha).endVertex();
    ts.draw();
  }
  
  public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox)
  {
    Tessellator tessellator = Tessellator.getInstance();
    VertexBuffer vertexbuffer = tessellator.getBuffer();
    vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
    vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ)
      .endVertex();
    tessellator.draw();
    vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
    vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ)
      .endVertex();
    tessellator.draw();
    vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
    vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ)
      .endVertex();
    vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ)
      .endVertex();
    tessellator.draw();
  }
  
  public static void scissorBox(int x, int y, int xend, int yend)
  {
    int width = xend - x;
    int height = yend - y;
    ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    int factor = sr.getScaleFactor();
    int bottomY = Minecraft.getMinecraft().currentScreen.height - yend;
    GL11.glScissor(x * factor, bottomY * factor, width * factor, 
      height * factor);
  }
  
  public static void setColor(Color c)
  {
    GL11.glColor4f(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, 
      c.getAlpha() / 255.0F);
  }
  
  public static void drawSolidBox()
  {
    drawSolidBox(DEFAULT_AABB);
  }
  
  public static void drawSolidBox(AxisAlignedBB bb)
  {
    GL11.glBegin(7);
    
    GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
    GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
    GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
    GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
    
    GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
    GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
    
    GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
    GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
    GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
    
    GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
    GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
    
    GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
    GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
    GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
    
    GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
    GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
    GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
    GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
    
    GL11.glEnd();
  }
  
  public static void drawOutlinedBox()
  {
    drawOutlinedBox(DEFAULT_AABB);
  }
  
  public static void drawOutlinedBox(AxisAlignedBB bb)
  {
    GL11.glBegin(1);
    
    GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
    GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
    
    GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
    GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
    
    GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
    GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
    
    GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
    GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
    
    GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
    GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
    
    GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
    
    GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
    
    GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
    GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
    
    GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
    
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
    
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
    GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
    
    GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
    GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
    
    GL11.glEnd();
  }
  
  public static void drawCrossBox()
  {
    drawOutlinedBox(DEFAULT_AABB);
  }
  
  public static void drawCrossBox(AxisAlignedBB bb)
  {
    GL11.glBegin(1);
    
    GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
    
    GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
    
    GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
    GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
    
    GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
    GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
    
    GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
    GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
    
    GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
    
    GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
    
    GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
    GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
    
    GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
    
    GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
    GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
    
    GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
    GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
    
    GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
    GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
    
    GL11.glEnd();
  }
}
