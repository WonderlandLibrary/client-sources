package net.SliceClient.modules;

import java.util.List;
import net.SliceClient.Utils.Wrapper;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class ESP extends Module
{
  public ESP()
  {
    super("ESP", Category.RENDER, 16376546);
  }
  
  public void onRender()
  {
    if (!getState()) {
      return;
    }
    GL11.glDisable(3553);
    GL11.glDisable(2884);
    GL11.glDisable(2929);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    GL11.glLineWidth(2.0F);
    List<?> players = theWorldplayerEntities;
    for (int i = 0; i < players.size(); i++)
    {
      Entity entity = (Entity)players.get(i);
      if (((entity instanceof EntityPlayer)) && (entity != Minecraft.thePlayer)) {
        drawTracer(entity);
      }
    }
    GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
    GL11.glLineWidth(1.0F);
    GL11.glDisable(2848);
    GL11.glDisable(3042);
    GL11.glEnable(2929);
    GL11.glEnable(2884);
    GL11.glEnable(3553);
  }
  
  private void drawTracer(Entity entity)
  {
    double health = Math.ceil(((EntityLivingBase)entity).getHealth()) / 2.0D;
    double distance = entity.getDistanceToEntity(Minecraft.thePlayer);
    double colorDistance = 70.0D;
    double d = distance / 70.0D;
    if (d < 0.0D) {
      d = 0.0D;
    }
    if (d > 1.0D) {
      d = 1.0D;
    }
    GL11.glColor3d(1.0D - d, d, 0.0D);
    RenderManager rm = Wrapper.mc.getRenderManager();
    double x = posX - RenderManager.renderPosX;
    double y = posY - RenderManager.renderPosY;
    double z = posZ - RenderManager.renderPosZ;
    GL11.glColor4f(100.0F, 100.0F, 100.0F, 0.2F);
    GL11.glBegin(1);
    GL11.glVertex3d(0.0D, Minecraft.thePlayer.getEyeHeight(), 0.0D);
    
    GL11.glColor4f(100.0F, 100.0F, 100.0F, 0.2F);
    GL11.glEnd();
    if (health < 10.0D) {
      GL11.glColor4f(0.0F, 255.0F, 0.0F, 1.0F);
    }
    if (health < 5.0D) {
      GL11.glColor4f(1.0F, 0.5F, 0.0F, 1.0F);
    }
    if (health < 2.0D) {
      GL11.glColor4f(50.0F, 0.0F, 0.0F, 1.0F);
    }
    drawBox2(new AxisAlignedBB(x - 0.6D, y + 2.0D, z - 0.6D, x + 0.6D, y + 2.0D, z + 0.6D));
    
    GL11.glColor4f(100.0F, 100.0F, 100.0F, 0.2F);
    drawBox2(new AxisAlignedBB(x - 0.6D, y + 0.0D, z - 0.6D, x + 0.6D, y + 2.0D, z + 0.6D));
    
    drawBox(entity, x, y, z);
  }
  
  public static void drawOutlinedBox(AxisAlignedBB boundingBox)
  {
    if (boundingBox == null) {
      return;
    }
    enableGL2D();
    GL11.glLineWidth(0.5F);
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
    disableGL2D();
  }
  
  public static void enableGL2D()
  {
    GL11.glDisable(2929);
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glDepthMask(true);
  }
  
  public static void disableGL2D()
  {
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glEnable(2929);
  }
  
  private void drawBox(Entity entity, double x, double y, double z)
  {
    double xAdd = 0.0D;
    double yAdd = 0.2D;
    
    double minX = x - width - xAdd;
    double minY = y;
    double minZ = z - width - xAdd;
    
    double maxX = x + width + xAdd;
    double maxY = y + height + yAdd;
    double maxZ = z + width + xAdd;
    for (int i = 0; i < 2; i++)
    {
      double yPos = i == 0 ? minY : maxY;
      GL11.glColor4f(100.0F, 100.0F, 100.0F, 0.2F);
      GL11.glBegin(2);
      
      GL11.glVertex3d(minX, yPos, minZ);
      GL11.glVertex3d(maxX, yPos, minZ);
      GL11.glVertex3d(maxX, yPos, maxZ);
      GL11.glVertex3d(minX, yPos, maxZ);
      GL11.glColor4f(100.0F, 100.0F, 100.0F, 0.2F);
      
      GL11.glEnd();
    }
    GL11.glBegin(1);
    for (int i = 0; i < 20; i++) {
      for (int j = 0; j < 2; j++) {
        GL11.glVertex3d(i % 2 == 0 ? minX : maxX, j == 0 ? minY : maxY, i < 2 ? minZ : maxZ);
      }
    }
    GL11.glEnd();
  }
  
  public static void drawBox2(AxisAlignedBB boundingBox)
  {
    if (boundingBox == null) {
      return;
    }
    GL11.glBegin(7);
    GL11.glVertex3d(minX, minY, maxZ);
    GL11.glVertex3d(maxX, minY, maxZ);
    GL11.glVertex3d(maxX, maxY, maxZ);
    GL11.glVertex3d(minX, maxY, maxZ);
    GL11.glEnd();
    GL11.glBegin(7);
    GL11.glVertex3d(maxX, minY, maxZ);
    GL11.glVertex3d(minX, minY, maxZ);
    GL11.glVertex3d(minX, maxY, maxZ);
    GL11.glVertex3d(maxX, maxY, maxZ);
    GL11.glEnd();
    GL11.glBegin(7);
    GL11.glVertex3d(minX, minY, minZ);
    GL11.glVertex3d(minX, minY, maxZ);
    GL11.glVertex3d(minX, maxY, maxZ);
    GL11.glVertex3d(minX, maxY, minZ);
    GL11.glEnd();
    GL11.glBegin(7);
    GL11.glVertex3d(minX, minY, maxZ);
    GL11.glVertex3d(minX, minY, minZ);
    GL11.glVertex3d(minX, maxY, minZ);
    GL11.glVertex3d(minX, maxY, maxZ);
    GL11.glEnd();
    GL11.glBegin(7);
    GL11.glVertex3d(maxX, minY, maxZ);
    GL11.glVertex3d(maxX, minY, minZ);
    GL11.glVertex3d(maxX, maxY, minZ);
    GL11.glVertex3d(maxX, maxY, maxZ);
    GL11.glEnd();
    GL11.glBegin(7);
    GL11.glVertex3d(maxX, minY, minZ);
    GL11.glVertex3d(maxX, minY, maxZ);
    GL11.glVertex3d(maxX, maxY, maxZ);
    GL11.glVertex3d(maxX, maxY, minZ);
    GL11.glEnd();
    GL11.glBegin(7);
    GL11.glVertex3d(minX, minY, minZ);
    GL11.glVertex3d(maxX, minY, minZ);
    GL11.glVertex3d(maxX, maxY, minZ);
    GL11.glVertex3d(minX, maxY, minZ);
    GL11.glEnd();
    GL11.glBegin(7);
    GL11.glVertex3d(maxX, minY, minZ);
    GL11.glVertex3d(minX, minY, minZ);
    GL11.glVertex3d(minX, maxY, minZ);
    GL11.glVertex3d(maxX, maxY, minZ);
    GL11.glEnd();
    GL11.glBegin(7);
    GL11.glVertex3d(minX, maxY, minZ);
    GL11.glVertex3d(maxX, maxY, minZ);
    GL11.glVertex3d(maxX, maxY, maxZ);
    GL11.glVertex3d(minX, maxY, maxZ);
    GL11.glEnd();
    GL11.glBegin(7);
    GL11.glVertex3d(maxX, maxY, minZ);
    GL11.glVertex3d(minX, maxY, minZ);
    GL11.glVertex3d(minX, maxY, maxZ);
    GL11.glVertex3d(maxX, maxY, maxZ);
    GL11.glEnd();
    GL11.glBegin(7);
    GL11.glVertex3d(minX, minY, minZ);
    GL11.glVertex3d(maxX, minY, minZ);
    GL11.glVertex3d(maxX, minY, maxZ);
    GL11.glVertex3d(minX, minY, maxZ);
    GL11.glEnd();
    GL11.glBegin(7);
    GL11.glVertex3d(maxX, minY, minZ);
    GL11.glVertex3d(minX, minY, minZ);
    GL11.glVertex3d(minX, minY, maxZ);
    GL11.glVertex3d(maxX, minY, maxZ);
    GL11.glEnd();
  }
}
