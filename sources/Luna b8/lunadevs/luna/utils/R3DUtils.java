package lunadevs.luna.utils;

import java.awt.Color;

import lunadevs.luna.main.Luna;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class R3DUtils
{
  public static void startDrawing()
  {
    GL11.glEnable(3042);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    Luna.mc.entityRenderer.setupCameraTransform(Luna.mc.timer.renderPartialTicks, 0);
  }
  
  public static void stopDrawing()
  {
    GL11.glDisable(3042);
    GL11.glEnable(3553);
    GL11.glDisable(2848);
    GL11.glDisable(3042);
    GL11.glEnable(2929);
  }
  
  public static void drawBoundingBox(AxisAlignedBB aabb)
  {
    WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
    Tessellator tessellator = Tessellator.getInstance();
    Luna.mc.entityRenderer.setupCameraTransform(Luna.mc.timer.renderPartialTicks, 0);
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
    worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
    worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
    worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
    worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
    worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
    worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
    worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
    worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
    worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
    worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
    worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
    worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
    worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
    worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
    worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
    worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
    worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
    worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
    worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
    worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
    worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
    worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
    worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
    worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
    worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
    worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
    worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
    worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
    worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
    worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
    worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
    worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
    worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
    worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
    worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
    worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
    worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
    worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
    worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
    worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
    worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
    worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
    tessellator.draw();
  }
  
  public static void drawOutlinedBox(AxisAlignedBB box)
  {
    if (box == null) {
      return;
    }
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
  
  public static void drawFilledBox(AxisAlignedBB mask)
  {
    WorldRenderer worldRenderer = Tessellator.instance.getWorldRenderer();
    Tessellator tessellator = Tessellator.instance;
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
    worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
    worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
    worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
    worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
    worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
    worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
    worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
    worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
    worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
    worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
    worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
    worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
    worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
    worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
    worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
    worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
    worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
    worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
    worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
    worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
    worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
    worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
    worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
    worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
    worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
    worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
    worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
    worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
    worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
    worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
    worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
    worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
    worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
    worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
    worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
    tessellator.draw();
    worldRenderer.startDrawingQuads();
    worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
    worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
    worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
    worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
    worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
    worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
    worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
    worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
    tessellator.draw();
  }
  
  public static void drawOutlinedBoundingBox(AxisAlignedBB mask)
  {
    WorldRenderer var2 = Tessellator.instance.getWorldRenderer();
    Tessellator var1 = Tessellator.instance;
    var2.startDrawing(3);
    var2.addVertex(mask.minX, mask.minY, mask.minZ);
    var2.addVertex(mask.maxX, mask.minY, mask.minZ);
    var2.addVertex(mask.maxX, mask.minY, mask.maxZ);
    var2.addVertex(mask.minX, mask.minY, mask.maxZ);
    var2.addVertex(mask.minX, mask.minY, mask.minZ);
    var1.draw();
    var2.startDrawing(3);
    var2.addVertex(mask.minX, mask.maxY, mask.minZ);
    var2.addVertex(mask.maxX, mask.maxY, mask.minZ);
    var2.addVertex(mask.maxX, mask.maxY, mask.maxZ);
    var2.addVertex(mask.minX, mask.maxY, mask.maxZ);
    var2.addVertex(mask.minX, mask.maxY, mask.minZ);
    var1.draw();
    var2.startDrawing(1);
    var2.addVertex(mask.minX, mask.minY, mask.minZ);
    var2.addVertex(mask.minX, mask.maxY, mask.minZ);
    var2.addVertex(mask.maxX, mask.minY, mask.minZ);
    var2.addVertex(mask.maxX, mask.maxY, mask.minZ);
    var2.addVertex(mask.maxX, mask.minY, mask.maxZ);
    var2.addVertex(mask.maxX, mask.maxY, mask.maxZ);
    var2.addVertex(mask.minX, mask.minY, mask.maxZ);
    var2.addVertex(mask.minX, mask.maxY, mask.maxZ);
    var1.draw();
  }
  
  public static void drawTracerLine(Entity entity, Color color)
  {
    Minecraft.getMinecraft().getRenderManager();double x = entity.posX - RenderManager.renderPosX;
    Minecraft.getMinecraft().getRenderManager();double y = entity.posY + entity.height / 2.0F - RenderManager.renderPosY;
    Minecraft.getMinecraft().getRenderManager();double z = entity.posZ - RenderManager.renderPosZ;
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(3042);
    GL11.glLineWidth(2.0F);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    glColor(color);
    Vec3 eyes = new Vec3(0.0D, 0.0D, 1.0D).rotatePitch(-(float)Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationPitch)).rotateYaw(-(float)Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw));
    GL11.glBegin(1);
    GL11.glVertex3d(eyes.xCoord, Minecraft.getMinecraft().thePlayer.getEyeHeight() + eyes.yCoord, eyes.zCoord);
    GL11.glVertex3d(x, y, z);
    GL11.glEnd();
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
  }
  
  public static void glColor(Color color)
  {
    GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
  }
}
