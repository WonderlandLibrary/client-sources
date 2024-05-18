package net.SliceClient.modules;

import com.darkmagician6.eventapi.EventManager;
import net.SliceClient.Utils.RenderUtils;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.Timer;
import org.lwjgl.util.Color;

public class ItemESP extends Module
{
  public ItemESP()
  {
    super("ItemESP", Category.RENDER, 16376546);
  }
  

  public void onEnable()
  {
    EventManager.register(this);
    super.onEnable();
  }
  





  public void onDisable()
  {
    EventManager.unregister(this);
    super.onDisable();
  }
  





  public void onRender()
  {
    if (!getState()) {
      return;
    }
    for (Object o : theWorldloadedEntityList) {
      if ((o instanceof EntityItem))
      {
        EntityItem e = (EntityItem)o;
        passive(e);
      }
    }
  }
  



  public void passive(EntityItem entitiy)
  {
    Color color = (Color)Color.CYAN;
    mc.getRenderManager();double x = lastTickPosX + (posX - lastTickPosX) * Timer.renderPartialTicks - RenderManager.renderPosX;
    mc.getRenderManager();double y = lastTickPosY + (posY - lastTickPosY) * Timer.renderPartialTicks - RenderManager.renderPosY;
    mc.getRenderManager();double z = lastTickPosZ + (posZ - lastTickPosZ) * Timer.renderPartialTicks - RenderManager.renderPosZ;
    render(color, x, y, z, width);
  }
  
  public void render(Color color, double x, double y, double z, float widht)
  {
    RenderUtils.drawOutlinedEntityESP(x, y, z, widht, 0.5D, 1.2F, widht, widht, 0.0F);
  }
}
