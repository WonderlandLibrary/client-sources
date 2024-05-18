package me.hexxed.mercury.modules;

import java.awt.Color;
import java.util.List;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.util.Friends;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;

public class Tracers extends Module
{
  public Tracers()
  {
    super("Tracers", 0, true, ModuleCategory.RENDER);
  }
  
  public void onRender()
  {
    drawTracers();
  }
  
  public void drawTracers() {
    for (int x = 0; x < mc.theWorld.playerEntities.size(); x++)
    {
      EntityPlayer entity = (EntityPlayer)mc.theWorld.playerEntities.get(x);
      
      if (entity != mc.thePlayer)
      {

        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.4F);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        



        mc.getRenderManager();double X = lastTickPosX + (posX - lastTickPosX) * mc.timer.renderPartialTicks - RenderManager.renderPosX;
        mc.getRenderManager();double Y = lastTickPosY + (posY - lastTickPosY) * mc.timer.renderPartialTicks - RenderManager.renderPosY;
        mc.getRenderManager();double Z = lastTickPosZ + (posZ - lastTickPosZ) * mc.timer.renderPartialTicks - RenderManager.renderPosZ;
        GL11.glBegin(1);
        
        float r = 0.0F;float g = 0.0F;float b = 0.0F;
        
        if (mc.thePlayer.getDistanceToEntity(entity) > 25.0F) {
          r = Color.BLUE.brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().getRed();
          g = Color.BLUE.brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().getGreen();
          b = Color.BLUE.brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().getBlue();
        }
        if (mc.thePlayer.getDistanceToEntity(entity) <= 25.0F) {
          r = Color.CYAN.brighter().brighter().brighter().brighter().getRed();
          g = Color.CYAN.brighter().brighter().brighter().brighter().getGreen();
          b = Color.CYAN.brighter().brighter().brighter().brighter().getBlue();
        }
        if (mc.thePlayer.getDistanceToEntity(entity) <= 10.0F) {
          r = Color.WHITE.brighter().brighter().brighter().brighter().getRed();
          g = Color.WHITE.brighter().brighter().brighter().brighter().getGreen();
          b = Color.WHITE.brighter().brighter().brighter().brighter().getBlue();
        }
        if (Friends.isFriend(entity.getName())) {
          r = Color.GREEN.getRed();
          g = Color.GREEN.getGreen();
          b = Color.GREEN.getBlue();
        }
        
        GL11.glColor4f(r, g, b, 1.0F);
        
        GL11.glVertex2d(0.0D, mc.thePlayer.isSneaking() ? 1.54F : 1.62F);
        GL11.glVertex3d(X, Y, Z);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(X, Y, Z);
        GL11.glVertex3d(X, Y + 1.0D, Z);
        GL11.glEnd();
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
      }
    }
  }
  
  public static void drawTracer(double bX, double bY, double bZ, double eX, double eY, double eZ, double r, double g, double b, double alpha) {
    GL11.glPushMatrix();
    

    GL11.glEnable(2848);
    GL11.glColor4d(r, g, b, alpha);
    GL11.glLineWidth(1.0F);
    
    GL11.glBegin(2);
    
    GL11.glVertex3d(bX, bY, bZ);
    GL11.glVertex3d(eX, eY, eZ);
    
    GL11.glEnd();
    

    GL11.glPopMatrix();
  }
}
