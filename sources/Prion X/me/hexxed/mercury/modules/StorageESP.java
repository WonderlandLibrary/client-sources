package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class StorageESP
  extends Module
{
  public StorageESP()
  {
    super("StorageESP", 0, true, ModuleCategory.RENDER);
  }
  
  public void onRender()
  {
    GL11.glPushMatrix();
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDisable(2896);
    GL11.glEnable(2848);
    for (Object tileEntity : mc.theWorld.loadedTileEntityList)
      if (tileEntity != null)
      {

        if (((tileEntity instanceof TileEntityChest)) || ((tileEntity instanceof TileEntityEnderChest)) || ((tileEntity instanceof TileEntityHopper)) || ((tileEntity instanceof TileEntityDispenser)))
        {

          TileEntity tileEntityChest = (TileEntity)tileEntity;
          double renderX = pos.x - RenderManager.renderPosX;
          double renderY = pos.y - RenderManager.renderPosY;
          double renderZ = pos.z - RenderManager.renderPosZ;
          GL11.glTranslated(renderX, renderY, renderZ);
          chestESP(0.0D, 0.0D, 0.0D);
          GL11.glTranslated(-renderX, -renderY, -renderZ);
        } }
    GL11.glDisable(3042);
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glEnable(2896);
    GL11.glDisable(2848);
    GL11.glPopMatrix();
  }
  
  public void chestESP(double x, double y, double z) {
    load();
    GL11.glBlendFunc(770, 771);
    
    GL11.glColor4f(0.0F, 50.0F, 30.0F, 0.2F);
    GL11.glDisable(2884);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDisable(2896);
    GL11.glDepthMask(false);
    GL11.glLineWidth(1.5F);
    GL11.glBegin(7);
    GL11.glVertex3d(x, y, z);
    GL11.glVertex3d(x, y + 1.0D, z);
    GL11.glVertex3d(x + 1.0D, y + 1.0D, z);
    GL11.glVertex3d(x + 1.0D, y, z);
    GL11.glVertex3d(x + 1.0D, y, z);
    GL11.glVertex3d(x + 1.0D, y + 1.0D, z);
    GL11.glVertex3d(x + 1.0D, y + 1.0D, z + 1.0D);
    GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
    GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
    GL11.glVertex3d(x + 1.0D, y + 1.0D, z + 1.0D);
    GL11.glVertex3d(x, y + 1.0D, z + 1.0D);
    GL11.glVertex3d(x, y, z + 1.0D);
    GL11.glVertex3d(x, y, z + 1.0D);
    GL11.glVertex3d(x, y + 1.0D, z + 1.0D);
    GL11.glVertex3d(x, y + 1.0D, z);
    GL11.glVertex3d(x, y, z);
    GL11.glVertex3d(x, y, z);
    GL11.glVertex3d(x + 1.0D, y, z);
    GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
    GL11.glVertex3d(x, y, z + 1.0D);
    GL11.glVertex3d(x, y + 1.0D, z);
    GL11.glVertex3d(x + 1.0D, y + 1.0D, z);
    GL11.glVertex3d(x + 1.0D, y + 1.0D, z + 1.0D);
    GL11.glVertex3d(x, y + 1.0D, z + 1.0D);
    GL11.glEnd();
    
    GL11.glColor4f(0.0F, 40.0F, 80.0F, 0.2F);
    GL11.glLineWidth(1.5F);
    GL11.glBegin(1);
    GL11.glVertex3d(x, y, z);
    GL11.glVertex3d(x, y + 1.0D, z);
    GL11.glVertex3d(x, y + 1.0D, z);
    GL11.glVertex3d(x + 1.0D, y + 1.0D, z);
    GL11.glVertex3d(x + 1.0D, y + 1.0D, z);
    GL11.glVertex3d(x + 1.0D, y, z);
    GL11.glVertex3d(x + 1.0D, y, z);
    GL11.glVertex3d(x, y, z);
    GL11.glVertex3d(x + 1.0D, y, z);
    GL11.glVertex3d(x + 1.0D, y + 1.0D, z);
    GL11.glVertex3d(x + 1.0D, y + 1.0D, z);
    GL11.glVertex3d(x + 1.0D, y + 1.0D, z + 1.0D);
    GL11.glVertex3d(x + 1.0D, y + 1.0D, z + 1.0D);
    GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
    GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
    GL11.glVertex3d(x + 1.0D, y, z);
    GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
    GL11.glVertex3d(x + 1.0D, y + 1.0D, z + 1.0D);
    GL11.glVertex3d(x + 1.0D, y + 1.0D, z + 1.0D);
    GL11.glVertex3d(x, y + 1.0D, z + 1.0D);
    GL11.glVertex3d(x, y + 1.0D, z + 1.0D);
    GL11.glVertex3d(x, y, z + 1.0D);
    GL11.glVertex3d(x, y, z + 1.0D);
    GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
    GL11.glVertex3d(x, y, z + 1.0D);
    GL11.glVertex3d(x, y + 1.0D, z + 1.0D);
    GL11.glVertex3d(x, y + 1.0D, z + 1.0D);
    GL11.glVertex3d(x, y + 1.0D, z);
    GL11.glVertex3d(x, y + 1.0D, z);
    GL11.glVertex3d(x, y, z);
    GL11.glVertex3d(x, y, z);
    GL11.glVertex3d(x, y, z + 1.0D);
    GL11.glEnd();
    float var13 = 1.6F;
    float var2 = 0.026688F;
    GL11.glPushMatrix();
    GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
    

    GL11.glScalef(-0.026688F, -0.026688F, 0.026688F);
    GL11.glEnable(3553);
    GL11.glPopMatrix();
    GL11.glDepthMask(true);
    GL11.glEnable(2884);
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glEnable(2896);
    reset();
  }
  
  public static void load() {
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
  }
  
  public static void reset() {
    GL11.glEnable(3553);
    GL11.glDisable(3042);
  }
}
