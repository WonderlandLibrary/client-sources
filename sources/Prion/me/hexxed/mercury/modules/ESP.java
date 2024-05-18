package me.hexxed.mercury.modules;

import java.util.Iterator;
import java.util.List;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Timer;


public class ESP
  extends Module
{
  public ESP() { super("ESP", 0, true, ModuleCategory.RENDER); }
  
  public void onRender() {
    double d1;
    label169:
    for (Iterator localIterator = mc.theWorld.playerEntities.iterator(); localIterator.hasNext(); 
        



        d1 = lastTickPosZ + (posZ - lastTickPosZ) * mc.timer.renderPartialTicks - RenderManager.renderPosZ)
    {
      Object o = localIterator.next();
      EntityPlayer p = (EntityPlayer)o;
      if ((p == mc.func_175606_aa()) || (!p.isEntityAlive())) break label169;
      mc.getRenderManager();double pX = lastTickPosX + (posX - lastTickPosX) * mc.timer.renderPartialTicks - RenderManager.renderPosX;
      mc.getRenderManager();double pY = lastTickPosY + (posY - lastTickPosY) * mc.timer.renderPartialTicks - RenderManager.renderPosY;
      mc.getRenderManager();
    }
  }
  











  private void renderBox(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    throw new Error("Unresolved compilation problem: \n\tThe method drawOutlinedBoundingBox(AxisAlignedBB) is undefined for the type RenderHelper\n");
  }
}
